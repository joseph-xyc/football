package com.glowworm.football.booking.service.matching.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.glowworm.football.booking.dao.mapper.FtMatchingMapper;
import com.glowworm.football.booking.dao.po.matching.FtMatchingPo;
import com.glowworm.football.booking.dao.po.stadium.FtStadiumSchedulePo;
import com.glowworm.football.booking.domain.matching.enums.MatchingStatus;
import com.glowworm.football.booking.domain.matching.vo.MatchingFormVo;
import com.glowworm.football.booking.domain.stadium.StadiumScheduleBean;
import com.glowworm.football.booking.domain.user.UserBean;
import com.glowworm.football.booking.domain.user.enums.UserType;
import com.glowworm.football.booking.service.matching.IMatchingActionService;
import com.glowworm.football.booking.service.stadium.IStadiumScheduleService;
import com.glowworm.football.booking.service.util.DateUtils;
import com.glowworm.football.booking.service.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * @author xuyongchang
 * @date 2023-07-25 17:20
 */
@Slf4j
@Service
public class MatchingActionServiceImpl implements IMatchingActionService {

    @Autowired
    private FtMatchingMapper matchingMapper;
    @Autowired
    private IStadiumScheduleService scheduleService;

    @Override
    @Transactional
    public void doMatch(UserBean user, MatchingFormVo formVo) {

        this.valid(user, formVo);

        // 先查询是否存在记录
        List<FtMatchingPo> matchingList = matchingMapper.selectList(Wrappers.lambdaQuery(FtMatchingPo.class)
                .eq(FtMatchingPo::getUserId, user.getId())
                .eq(FtMatchingPo::getScheduleId, formVo.getScheduleId()));

        // 保存匹配信息
        if (CollectionUtils.isEmpty(matchingList)) {

            FtStadiumSchedulePo schedule = scheduleService.getSchedule(formVo.getScheduleId());

            matchingMapper.insert(FtMatchingPo.builder()
                    .matchingStatus(MatchingStatus.MATCHING)
                    .stadiumId(schedule.getStadiumId())
                    .blockId(schedule.getBlockId())
                    .userId(user.getId())
                    .scheduleId(formVo.getScheduleId())
                    .build());
            return;
        }

        // 更新匹配状态为 MATCHING
        FtMatchingPo matchingRecord = matchingList.get(0);
        matchingMapper.updateById(FtMatchingPo.builder()
                .id(matchingRecord.getId())
                .matchingStatus(MatchingStatus.MATCHING)
                .matchingTime(DateUtils.getNow())
                .build());
    }

    @Override
    @Transactional
    public void undoMatch(UserBean user, MatchingFormVo formVo) {

        this.valid(user, formVo);

        // 先查询是否存在记录
        List<FtMatchingPo> matchingList = matchingMapper.selectList(Wrappers.lambdaQuery(FtMatchingPo.class)
                .eq(FtMatchingPo::getUserId, user.getId())
                .eq(FtMatchingPo::getScheduleId, formVo.getScheduleId()));

        Utils.throwError(CollectionUtils.isEmpty(matchingList), "不存在过往的匹配记录, 无法取消匹配");

        // 更新
        FtMatchingPo matchingRecord = matchingList.get(0);
        matchingMapper.updateById(FtMatchingPo.builder()
                .id(matchingRecord.getId())
                .matchingStatus(MatchingStatus.CANCEL)
                .build());
    }

    private void valid (UserBean user, MatchingFormVo formVo) {

        Utils.isTrue(user.getUserType().equals(UserType.ORDINARY), "只有登录才能进行匹配");
        Utils.throwError(Objects.isNull(formVo), "匹配场次等信息不能为空");
    }
}
