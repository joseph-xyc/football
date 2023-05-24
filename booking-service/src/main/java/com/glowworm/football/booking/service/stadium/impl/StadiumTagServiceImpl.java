package com.glowworm.football.booking.service.stadium.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.glowworm.football.booking.dao.mapper.FtStadiumTagMapper;
import com.glowworm.football.booking.dao.po.stadium.FtStadiumTagPo;
import com.glowworm.football.booking.service.stadium.IStadiumTagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xuyongchang
 * @date 2023-05-24 15:37
 */
@Slf4j
@Service
public class StadiumTagServiceImpl implements IStadiumTagService {

    @Autowired
    private FtStadiumTagMapper stadiumTagMapper;

    @Override
    public List<FtStadiumTagPo> queryTags(Long stadiumId) {

        return stadiumTagMapper.selectList(Wrappers.lambdaQuery(FtStadiumTagPo.class)
                .eq(FtStadiumTagPo::getStadiumId, stadiumId));
    }

    @Override
    public Map<Long, List<FtStadiumTagPo>> queryTagMap(List<Long> stadiumIds) {

        if (CollectionUtils.isEmpty(stadiumIds)) {
            return Collections.emptyMap();
        }

        List<FtStadiumTagPo> tags = stadiumTagMapper.selectList(Wrappers.lambdaQuery(FtStadiumTagPo.class)
                .in(FtStadiumTagPo::getStadiumId, stadiumIds));

        return tags.stream().collect(Collectors.groupingBy(FtStadiumTagPo::getStadiumId));
    }
}
