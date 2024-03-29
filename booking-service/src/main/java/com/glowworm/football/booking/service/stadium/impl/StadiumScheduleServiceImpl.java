package com.glowworm.football.booking.service.stadium.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.glowworm.football.booking.dao.mapper.FtStadiumScheduleMapper;
import com.glowworm.football.booking.dao.po.stadium.FtStadiumPo;
import com.glowworm.football.booking.dao.po.stadium.FtStadiumSchedulePo;
import com.glowworm.football.booking.domain.stadium.StadiumScheduleBean;
import com.glowworm.football.booking.domain.stadium.enums.ScheduleClock;
import com.glowworm.football.booking.domain.stadium.query.QuerySchedule;
import com.glowworm.football.booking.service.stadium.IStadiumScheduleService;
import com.glowworm.football.booking.service.util.DateUtils;
import com.glowworm.football.booking.service.util.Utils;
import com.glowworm.football.booking.service.util.MybatisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

/**
 * @author xuyongchang
 * @date 2023-04-12 15:43
 */
@Slf4j
@Service
public class StadiumScheduleServiceImpl implements IStadiumScheduleService {

    @Autowired
    private FtStadiumScheduleMapper scheduleMapper;

    @Override
    public List<FtStadiumSchedulePo> querySchedule(QuerySchedule query) {

        List<FtStadiumSchedulePo> schedulePos = scheduleMapper.selectList(Wrappers.lambdaQuery(FtStadiumSchedulePo.class)
                .in(!CollectionUtils.isEmpty(query.getIds()), FtStadiumSchedulePo::getId, query.getIds())
                .eq(Objects.nonNull(query.getStadiumId()), FtStadiumSchedulePo::getStadiumId, query.getStadiumId())
                .eq(Objects.nonNull(query.getBlockId()), FtStadiumSchedulePo::getBlockId, query.getBlockId())
                .eq(Objects.nonNull(query.getDate()), FtStadiumSchedulePo::getDate, Objects.isNull(query.getDate()) ? null : new Timestamp(query.getDate()))
                .ge(Objects.nonNull(query.getDateBegin()), FtStadiumSchedulePo::getDate, MybatisUtils.notNull(query.getDateBegin(), DateUtils::getTimestamp))
                .le(Objects.nonNull(query.getDateEnd()), FtStadiumSchedulePo::getDate, MybatisUtils.notNull(query.getDateEnd(), DateUtils::getTimestamp))
                .ge(Objects.nonNull(query.getDateBeginTimestamp()), FtStadiumSchedulePo::getDate, query.getDateBeginTimestamp())
                .le(Objects.nonNull(query.getDateEndTimestamp()), FtStadiumSchedulePo::getDate, query.getDateEndTimestamp())
                .in(!CollectionUtils.isEmpty(query.getStatus()), FtStadiumSchedulePo::getStatus, query.getStatus())
                .in(Utils.isPositive(query.getIsAfternoon()), FtStadiumSchedulePo::getClockBegin, ScheduleClock.getAfternoonClock()));

        return schedulePos;
    }

    @Override
    public FtStadiumSchedulePo getSchedule(Long id) {

        return scheduleMapper.selectById(id);
    }

}
