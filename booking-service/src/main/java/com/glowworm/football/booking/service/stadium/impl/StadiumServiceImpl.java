package com.glowworm.football.booking.service.stadium.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.glowworm.football.booking.dao.mapper.FtStadiumMapper;
import com.glowworm.football.booking.dao.po.stadium.FtStadiumBlockPo;
import com.glowworm.football.booking.dao.po.stadium.FtStadiumPo;
import com.glowworm.football.booking.domain.context.WxContext;
import com.glowworm.football.booking.domain.stadium.QueryStadiumVo;
import com.glowworm.football.booking.domain.stadium.StadiumBean;
import com.glowworm.football.booking.domain.stadium.StadiumBlockBean;
import com.glowworm.football.booking.domain.stadium.enums.StadiumBlockStatus;
import com.glowworm.football.booking.domain.stadium.enums.StadiumStatus;
import com.glowworm.football.booking.service.stadium.IStadiumBlockService;
import com.glowworm.football.booking.service.stadium.IStadiumService;
import com.glowworm.football.booking.service.util.FtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author xuyongchang
 * @date 2023-04-08 14:51
 */
@Slf4j
@Service
public class StadiumServiceImpl extends ServiceImpl<FtStadiumMapper, FtStadiumPo> implements IStadiumService {

    @Autowired
    private IStadiumBlockService stadiumBlockService;

    @Override
    public List<StadiumBean> queryList(WxContext ctx, QueryStadiumVo query) {

        List<FtStadiumPo> stadiumList = this.list(Wrappers.lambdaQuery(FtStadiumPo.class)
                .eq(FtStadiumPo::getStadiumStatus, StadiumStatus.ENABLE.getCode())
                .like(Objects.nonNull(query.getStadiumName()), FtStadiumPo::getStadiumName, query.getStadiumName()));

        if (CollectionUtils.isEmpty(stadiumList)) {
            return Collections.emptyList();
        }

        Map<Long, FtStadiumPo> stadiumMap = stadiumList.stream().collect(Collectors.toMap(FtStadiumPo::getId, Function.identity()));

        // 查询所有球场的场地list
        List<Long> stadiumIds = stadiumList.stream().map(FtStadiumPo::getId).collect(Collectors.toList());

        List<FtStadiumBlockPo> stadiumBlockList = stadiumBlockService.list(Wrappers.lambdaQuery(FtStadiumBlockPo.class)
                .in(FtStadiumBlockPo::getStadiumId, stadiumIds)
                .eq(FtStadiumBlockPo::getBlockStatus, StadiumBlockStatus.ENABLE.getCode()));

        // 按球场ID聚合场地list
        Map<Long, List<FtStadiumBlockPo>> stadiumId2BlockMap = stadiumBlockList.stream().collect(Collectors.groupingBy(FtStadiumBlockPo::getStadiumId));

        // 封装
        List<StadiumBean> result = stadiumMap.entrySet().stream().map(entry -> {
            StadiumBean stadiumBean = FtUtil.copy(entry.getValue(), StadiumBean.class);
            List<FtStadiumBlockPo> blocks = stadiumId2BlockMap.getOrDefault(entry.getKey(), Collections.emptyList());
            stadiumBean.setBlockList(FtUtil.copy(blocks, StadiumBlockBean.class));
            return stadiumBean;
        }).collect(Collectors.toList());

        return result;
    }
}
