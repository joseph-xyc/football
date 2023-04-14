package com.glowworm.football.booking.service.stadium.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.glowworm.football.booking.dao.mapper.FtStadiumScheduleMapper;
import com.glowworm.football.booking.dao.po.stadium.FtStadiumSchedulePo;
import com.glowworm.football.booking.domain.stadium.StadiumBean;
import com.glowworm.football.booking.domain.stadium.StadiumBlockBean;
import com.glowworm.football.booking.domain.stadium.enums.ScheduleClock;
import com.glowworm.football.booking.domain.stadium.enums.ScheduleStatus;
import com.glowworm.football.booking.domain.stadium.query.QueryStadium;
import com.glowworm.football.booking.service.stadium.IStadiumScheduleActionService;
import com.glowworm.football.booking.service.stadium.IStadiumService;
import com.glowworm.football.booking.service.util.DateUtils;
import com.glowworm.football.booking.service.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author xuyongchang
 * @date 2023-04-12 16:01
 */
@Slf4j
@Service
public class StadiumScheduleActionServiceImpl implements IStadiumScheduleActionService {

    @Autowired
    private FtStadiumScheduleMapper scheduleMapper;

    @Autowired
    private IStadiumService stadiumService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void appendSchedule(String endDate, Long stadiumId, Long blockId) {

        /**
         * 生成所有球场之后N天（days）的时刻表
         * 1. 查球场及其场地
         * 2. 批量生成球场下所有场地的每天的时刻记录
         */

        List<StadiumBean> stadiumBeans = stadiumService.queryList(QueryStadium.builder()
                .id(stadiumId)
                .blockId(blockId)
                .build());

        if (CollectionUtils.isEmpty(stadiumBeans)) {
            return;
        }

        // 所有球场
        stadiumBeans.forEach(stadium -> {

            // 球场下-所有场地
            List<StadiumBlockBean> blocks = stadium.getBlockList();
            blocks.forEach(block -> {

                // 场地下-所有日期
                List<Timestamp> dates = getDates(block.getStadiumId(), block.getId(), endDate);
                dates.forEach(date -> {
                    // 日期下-所有时刻
                    for (int i = 0; i < ScheduleClock.values().length - 1; i++) {
                        ScheduleClock begin = ScheduleClock.values()[i];
                        ScheduleClock end = ScheduleClock.values()[i + 1];
                        FtStadiumSchedulePo schedule = FtStadiumSchedulePo.builder()
                                .stadiumId(block.getStadiumId())
                                .blockId(block.getId())
                                .date(date)
                                .clockBegin(begin)
                                .clockEnd(end)
                                .status(ScheduleStatus.ENABLE)
                                .build();
                        scheduleMapper.insert(schedule);
                    }
                });
            });
        });

    }

    private List<Timestamp> getDates (Long stadiumId, Long blockId, String endDateStr) {

        // 查询最新一条schedule，以此schedule的date + 1为基准

        List<FtStadiumSchedulePo> schedules = scheduleMapper.selectList(Wrappers.lambdaQuery(FtStadiumSchedulePo.class)
                .eq(FtStadiumSchedulePo::getStadiumId, stadiumId)
                .eq(FtStadiumSchedulePo::getBlockId, blockId).orderByDesc(FtStadiumSchedulePo::getDate));

        Timestamp endDate = DateUtils.getTimestamp(endDateStr);

        // 如果此球场此场地无时刻记录，则从当天开始初始化
        if (CollectionUtils.isEmpty(schedules)) {
            return DateUtils.getEachDays(DateUtils.getToday(), endDate);
        }

        // 如果已经存在时刻记录，则按时间倒序取最新的一条数据(date + 1)作为日期基准来append时刻
        Timestamp startDate = DateUtils.getSomeDayAfter(schedules.get(0).getDate(), 1);

        return DateUtils.getEachDays(startDate, endDate);
    }
}
