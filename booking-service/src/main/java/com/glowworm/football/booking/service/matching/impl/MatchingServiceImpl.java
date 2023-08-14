package com.glowworm.football.booking.service.matching.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.glowworm.football.booking.dao.mapper.FtMatchingMapper;
import com.glowworm.football.booking.dao.po.matching.FtMatchingPo;
import com.glowworm.football.booking.dao.po.user.FtUserPo;
import com.glowworm.football.booking.domain.matching.query.QueryMatching;
import com.glowworm.football.booking.domain.matching.vo.MatchingVo;
import com.glowworm.football.booking.service.matching.IMatchingService;
import com.glowworm.football.booking.service.user.IUserService;
import com.glowworm.football.booking.service.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author xuyongchang
 * @date 2023-07-20 20:53
 */
@Slf4j
@Service
public class MatchingServiceImpl implements IMatchingService {

    @Autowired
    private FtMatchingMapper matchingMapper;
    @Autowired
    private IUserService userService;


    @Override
    public Map<Long, List<FtMatchingPo>> queryMatching(QueryMatching query) {

        List<FtMatchingPo> result = matchingMapper.selectList(Wrappers.lambdaQuery(FtMatchingPo.class)
                .eq(Objects.nonNull(query.getScheduleId()), FtMatchingPo::getScheduleId, query.getScheduleId())
                .in(Objects.nonNull(query.getScheduleIds()), FtMatchingPo::getScheduleId, query.getScheduleIds())
                .eq(Objects.nonNull(query.getMatchingStatus()), FtMatchingPo::getMatchingStatus, query.getMatchingStatus()));
        return result.stream().collect(Collectors.groupingBy(FtMatchingPo::getScheduleId));
    }

    @Override
    public List<MatchingVo> queryMatchingVo(Long scheduleId) {

        if (Objects.isNull(scheduleId)) {
            return Collections.emptyList();
        }

        List<FtMatchingPo> matchingPo = matchingMapper.selectList(Wrappers.lambdaQuery(FtMatchingPo.class)
                .eq(FtMatchingPo::getScheduleId, scheduleId));

        if (CollectionUtils.isEmpty(matchingPo)) {
            return Collections.emptyList();
        }

        // 查询user
        List<Long> userIds = matchingPo.stream().map(FtMatchingPo::getUserId).collect(Collectors.toList());
        Map<Long, FtUserPo> userMap = userService.queryUserMap(userIds);

        // enhance
        List<MatchingVo> matching = Utils.copy(matchingPo, MatchingVo.class);
        matching.forEach(item -> {
            FtUserPo user = userMap.getOrDefault(item.getUserId(), FtUserPo.builder().build());
            item.setUsername(user.getUsername());
            item.setAvatar(user.getAvatar());
        });

        return matching;
    }
}
