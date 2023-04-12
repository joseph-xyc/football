package com.glowworm.football.booking.service.stadium.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.glowworm.football.booking.dao.mapper.FtStadiumBlockMapper;
import com.glowworm.football.booking.dao.mapper.FtStadiumImageMapper;
import com.glowworm.football.booking.dao.mapper.FtStadiumMapper;
import com.glowworm.football.booking.dao.po.stadium.FtStadiumBlockPo;
import com.glowworm.football.booking.dao.po.stadium.FtStadiumImagePo;
import com.glowworm.football.booking.dao.po.stadium.FtStadiumPo;
import com.glowworm.football.booking.domain.common.context.WxContext;
import com.glowworm.football.booking.domain.stadium.*;
import com.glowworm.football.booking.domain.stadium.enums.StadiumBlockStatus;
import com.glowworm.football.booking.domain.stadium.enums.StadiumImageType;
import com.glowworm.football.booking.domain.stadium.enums.StadiumStatus;
import com.glowworm.football.booking.service.stadium.IStadiumService;
import com.glowworm.football.booking.service.util.FtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
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
public class StadiumServiceImpl implements IStadiumService {

    @Autowired
    private FtStadiumMapper stadiumMapper;

    @Autowired
    private FtStadiumBlockMapper ftStadiumBlockMapper;

    @Autowired
    private FtStadiumImageMapper ftStadiumImageMapper;

    @Override
    public List<StadiumBean> queryList(WxContext ctx, QueryStadiumVo query) {

        List<FtStadiumPo> stadiumList = stadiumMapper.selectList(Wrappers.lambdaQuery(FtStadiumPo.class)
                .eq(FtStadiumPo::getStadiumStatus, StadiumStatus.ENABLE.getCode())
                .like(Objects.nonNull(query.getStadiumName()), FtStadiumPo::getStadiumName, query.getStadiumName()));

        if (CollectionUtils.isEmpty(stadiumList)) {
            return Collections.emptyList();
        }

        Map<Long, FtStadiumPo> stadiumMap = stadiumList.stream().collect(Collectors.toMap(FtStadiumPo::getId, Function.identity()));

        // 查询所有球场的场地list
        List<Long> stadiumIds = stadiumList.stream().map(FtStadiumPo::getId).collect(Collectors.toList());

        List<FtStadiumBlockPo> stadiumBlockList = ftStadiumBlockMapper.selectList(Wrappers.lambdaQuery(FtStadiumBlockPo.class)
                .in(FtStadiumBlockPo::getStadiumId, stadiumIds)
                .eq(FtStadiumBlockPo::getBlockStatus, StadiumBlockStatus.ENABLE.getCode()));

        // 按球场ID聚合场地list
        Map<Long, List<FtStadiumBlockPo>> stadiumId2BlockMap = stadiumBlockList.stream().collect(Collectors.groupingBy(FtStadiumBlockPo::getStadiumId));

        // 查询图片
        List<FtStadiumImagePo> ftStadiumImageList = ftStadiumImageMapper.selectList(Wrappers.lambdaQuery(FtStadiumImagePo.class)
                .in(FtStadiumImagePo::getStadiumId, stadiumIds)
                .eq(FtStadiumImagePo::getImageType, StadiumImageType.MAIN.getCode()));

        Map<Long, String> stadiumId2ImageUrlMap = ftStadiumImageList.stream()
                .collect(Collectors.toMap(FtStadiumImagePo::getStadiumId, FtStadiumImagePo::getUrl, (a, b) -> b));


        // 封装
        List<StadiumBean> result = stadiumMap.entrySet().stream().map(entry -> {
            StadiumBean stadiumBean = FtUtil.copy(entry.getValue(), StadiumBean.class);
            List<FtStadiumBlockPo> blocks = stadiumId2BlockMap.getOrDefault(entry.getKey(), Collections.emptyList());
            stadiumBean.setBlockList(FtUtil.copy(blocks, StadiumBlockBean.class));

            // 首图
            String imageUrl = stadiumId2ImageUrlMap.getOrDefault(entry.getKey(), Strings.EMPTY);
            stadiumBean.setMainImageUrl(imageUrl);

            return stadiumBean;
        }).collect(Collectors.toList());

        return result;
    }

    @Override
    public StadiumInfoVo getDetail(WxContext ctx, Long id) {

        // 球场
        FtStadiumPo stadiumPo = stadiumMapper.selectById(id);

        // 场地
        List<FtStadiumBlockPo> blockList = ftStadiumBlockMapper.selectList(Wrappers.lambdaQuery(FtStadiumBlockPo.class)
                .eq(FtStadiumBlockPo::getStadiumId, id)
                .eq(FtStadiumBlockPo::getBlockStatus, StadiumBlockStatus.ENABLE.getCode()));
        List<StadiumBlockVo> blockVoList = blockList.stream().map(item -> StadiumBlockVo.builder()
                        .id(item.getId())
                        .stadiumId(item.getStadiumId())
                        .blockName(item.getBlockName())
                        .scaleType(item.getScaleType().getCode())
                        .scaleTypeDesc(item.getScaleType().getDesc())
                        .swardType(item.getSwardType().getCode())
                        .swardTypeDesc(item.getSwardType().getDesc())
                        .build())
                .collect(Collectors.toList());


        // 球场图片
        List<FtStadiumImagePo> imageList = ftStadiumImageMapper.selectList(Wrappers.lambdaQuery(FtStadiumImagePo.class)
                .eq(FtStadiumImagePo::getStadiumId, id)
                .orderByAsc(FtStadiumImagePo::getImageType)
                .orderByDesc(FtStadiumImagePo::getMtime));
        List<String> images = imageList.stream().map(FtStadiumImagePo::getUrl).collect(Collectors.toList());

        StadiumInfoVo stadiumVo = FtUtil.copy(stadiumPo, StadiumInfoVo.class);
        stadiumVo.setBlockList(blockVoList);
        stadiumVo.setImages(images);

        return stadiumVo;
    }
}
