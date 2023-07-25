package com.glowworm.football.booking.service.matching.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.glowworm.football.booking.dao.mapper.FtMatchingMapper;
import com.glowworm.football.booking.dao.po.matching.FtMatchingPo;
import com.glowworm.football.booking.dao.po.passenger.FtPassengerPo;
import com.glowworm.football.booking.domain.matching.query.QueryMatching;
import com.glowworm.football.booking.service.matching.IMatchingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public Map<Long, List<FtMatchingPo>> queryMatching(QueryMatching query) {

        List<FtMatchingPo> result = matchingMapper.selectList(Wrappers.lambdaQuery(FtMatchingPo.class)
                .in(Objects.nonNull(query.getScheduleIds()), FtMatchingPo::getScheduleId, query.getScheduleIds())
                .eq(Objects.nonNull(query.getMatchingStatus()), FtMatchingPo::getMatchingStatus, query.getMatchingStatus()));
        return result.stream().collect(Collectors.groupingBy(FtMatchingPo::getScheduleId));
    }
}
