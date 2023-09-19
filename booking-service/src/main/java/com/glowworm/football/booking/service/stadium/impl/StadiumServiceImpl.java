package com.glowworm.football.booking.service.stadium.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.glowworm.football.booking.dao.mapper.FtStadiumBlockMapper;
import com.glowworm.football.booking.dao.mapper.FtStadiumImageMapper;
import com.glowworm.football.booking.dao.mapper.FtStadiumMapper;
import com.glowworm.football.booking.dao.po.matching.FtMatchingPo;
import com.glowworm.football.booking.dao.po.stadium.FtStadiumBlockPo;
import com.glowworm.football.booking.dao.po.stadium.FtStadiumImagePo;
import com.glowworm.football.booking.dao.po.stadium.FtStadiumPo;
import com.glowworm.football.booking.dao.po.stadium.FtStadiumSchedulePo;
import com.glowworm.football.booking.domain.booking.query.QueryBooking;
import com.glowworm.football.booking.domain.common.enums.TrueFalse;
import com.glowworm.football.booking.domain.matching.query.QueryMatching;
import com.glowworm.football.booking.domain.publish_price.enums.Week;
import com.glowworm.football.booking.domain.publish_price.query.QueryPublishPrice;
import com.glowworm.football.booking.domain.stadium.*;
import com.glowworm.football.booking.domain.stadium.enums.*;
import com.glowworm.football.booking.domain.stadium.query.QuerySchedule;
import com.glowworm.football.booking.domain.stadium.query.QueryStadium;
import com.glowworm.football.booking.domain.stadium.vo.StadiumBlockVo;
import com.glowworm.football.booking.domain.stadium.vo.StadiumInfoVo;
import com.glowworm.football.booking.service.booking.IBookingService;
import com.glowworm.football.booking.service.matching.IMatchingService;
import com.glowworm.football.booking.service.publish_price.IPublishPriceService;
import com.glowworm.football.booking.service.stadium.IStadiumScheduleService;
import com.glowworm.football.booking.service.stadium.IStadiumService;
import com.glowworm.football.booking.service.util.DateUtils;
import com.glowworm.football.booking.service.util.Utils;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;
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
    private FtStadiumBlockMapper blockMapper;
    @Autowired
    private FtStadiumImageMapper ftStadiumImageMapper;
    @Autowired
    private IPublishPriceService priceService;
    @Autowired
    private IMatchingService matchingService;
    @Autowired
    private IStadiumScheduleService scheduleService;

    @Override
    public List<StadiumBean> queryList(QueryStadium query) {

        LambdaQueryWrapper<FtStadiumPo> queryWrapper = buildQueryWrapper(query);
        List<FtStadiumPo> stadiumList = stadiumMapper.selectList(queryWrapper);

        if (CollectionUtils.isEmpty(stadiumList)) {
            return Collections.emptyList();
        }

        Map<Long, FtStadiumPo> stadiumMap = stadiumList.stream().collect(Collectors.toMap(FtStadiumPo::getId, Function.identity()));

        // 查询所有球场的场地list
        List<Long> stadiumIds = stadiumList.stream().map(FtStadiumPo::getId).collect(Collectors.toList());

        List<FtStadiumBlockPo> stadiumBlockList = blockMapper.selectList(Wrappers.lambdaQuery(FtStadiumBlockPo.class)
                .in(FtStadiumBlockPo::getStadiumId, stadiumIds)
                .eq(Objects.nonNull(query.getBlockId()), FtStadiumBlockPo::getId, query.getBlockId())
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
            StadiumBean stadiumBean = Utils.copy(entry.getValue(), StadiumBean.class);
            List<FtStadiumBlockPo> blocks = stadiumId2BlockMap.getOrDefault(entry.getKey(), Collections.emptyList());
            stadiumBean.setBlockList(Utils.copy(blocks, StadiumBlockBean.class));

            // 首图
            String imageUrl = stadiumId2ImageUrlMap.getOrDefault(entry.getKey(), Strings.EMPTY);
            stadiumBean.setMainImageUrl(imageUrl);

            return stadiumBean;
        }).collect(Collectors.toList());

        // 填充距离
        enhanceDistance(result, query);

        // 排序
        return doSort(result, query);
    }

    private LambdaQueryWrapper<FtStadiumPo> buildQueryWrapper (QueryStadium query) {

        LambdaQueryWrapper<FtStadiumPo> queryWrapper = Wrappers.lambdaQuery(FtStadiumPo.class)
                .eq(Objects.nonNull(query.getId()), FtStadiumPo::getId, query.getId())
                .eq(FtStadiumPo::getStadiumStatus, StadiumStatus.ENABLE.getCode())
                .in(!CollectionUtils.isEmpty(query.getDistrict()), FtStadiumPo::getDistrict, query.getDistrict())
                .like(Objects.nonNull(query.getStadiumName()), FtStadiumPo::getStadiumName, query.getStadiumName());

        // 处理scale筛选条件
        if (!Utils.isPositive(enhanceScaleType(query, queryWrapper))) {
            return queryWrapper;
        }

        return queryWrapper;
    }

    private List<StadiumBean> doSort (List<StadiumBean> stadiumList, QueryStadium query) {

        if (CollectionUtils.isEmpty(stadiumList) || Objects.isNull(query.getSort())) {
            return stadiumList;
        }

        StadiumSort sort = StadiumSort.get(query.getSort());
        if (Objects.isNull(sort)) {
            return stadiumList;
        }

        switch (sort) {
            // 距离排序
            case DISTANCE_FIRST:
                return doDistanceSort(stadiumList, query);
            // 按价格排序, 以周末(6,7)均价为准
            case LOW_PRICE_FIRST:
            case HIGH_PRICE_FIRST:
                return doPriceSort(stadiumList, sort);
            default:
                return stadiumList;
        }
    }

    private List<StadiumBean> doDistanceSort(List<StadiumBean> stadiumList, QueryStadium query) {

        if (Objects.isNull(query.getUserLat()) || Objects.isNull(query.getUserLon())) {
            return stadiumList;
        }

        // 按距离升序
        return stadiumList.stream()
                .sorted(Comparator.comparing(StadiumBean::getDistance))
                .collect(Collectors.toList());
    }

    private void enhanceDistance (List<StadiumBean> stadiumList, QueryStadium query) {

        if (Objects.isNull(query.getUserLat()) || Objects.isNull(query.getUserLon())) {
            return;
        }

        // 计算距离并赋值
        stadiumList.forEach(stadium -> {
            double distance = Utils.getDistance(query.getUserLon(), query.getUserLat(), stadium.getLon().doubleValue(), stadium.getLat().doubleValue());
            stadium.setDistance(BigDecimal.valueOf(distance));
        });
    }

    private List<StadiumBean> doPriceSort (List<StadiumBean> stadiumList, StadiumSort sort) {

        List<Long> stadiumIds = stadiumList.stream().map(StadiumBean::getId).collect(Collectors.toList());

        // 查询这些球场的周末均价
        Map<Long, BigDecimal> averagePriceMap = priceService.getAveragePrice(QueryPublishPrice.builder()
                .stadiumIds(stadiumIds)
                .weeks(Week.getWeekend())
                .build());

        // 赋值均价
        stadiumList.forEach(stadium -> {
            BigDecimal averagePrice = averagePriceMap.getOrDefault(stadium.getId(), BigDecimal.ZERO);
            stadium.setAveragePrice(averagePrice);
        });

        // 价格升序
        if (sort.equals(StadiumSort.LOW_PRICE_FIRST)) {
            return stadiumList.stream()
                    .sorted(Comparator.comparing(StadiumBean::getAveragePrice))
                    .collect(Collectors.toList());
        }

        // 价格降序
        if (sort.equals(StadiumSort.HIGH_PRICE_FIRST)) {
            return stadiumList.stream()
                    .sorted(Comparator.comparing(StadiumBean::getAveragePrice).reversed())
                    .collect(Collectors.toList());
        }

        return stadiumList;
    }

    private Integer enhanceScaleType (QueryStadium query, LambdaQueryWrapper<FtStadiumPo> queryWrapper) {

        // 处理场地规格筛选条件
        if (CollectionUtils.isEmpty(query.getScale())) {
            return TrueFalse.TRUE.getCode();
        }

        List<ScaleType> scaleTypes = ScaleType.get(query.getScale());
        List<FtStadiumBlockPo> blockList = blockMapper.selectList(Wrappers.lambdaQuery(FtStadiumBlockPo.class)
                .in(!CollectionUtils.isEmpty(scaleTypes), FtStadiumBlockPo::getScaleType, scaleTypes)
                .eq(FtStadiumBlockPo::getBlockStatus, StadiumBlockStatus.ENABLE.getCode()));

        // 无数据时,强制使sql失效
        if (CollectionUtils.isEmpty(blockList)) {
            Utils.disableQuery(queryWrapper);
            return TrueFalse.FALSE.getCode();
        }

        List<Long> stadiumIds = blockList.stream().map(FtStadiumBlockPo::getStadiumId).distinct().collect(Collectors.toList());
        queryWrapper.in(FtStadiumPo::getId, stadiumIds);

        return TrueFalse.TRUE.getCode();
    }

    @Override
    public StadiumInfoVo getDetail(Long id) {

        // 球场
        FtStadiumPo stadiumPo = stadiumMapper.selectById(id);

        // 场地
        List<FtStadiumBlockPo> blockList = blockMapper.selectList(Wrappers.lambdaQuery(FtStadiumBlockPo.class)
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

        StadiumInfoVo stadiumVo = Utils.copy(stadiumPo, StadiumInfoVo.class);
        stadiumVo.setBlockList(blockVoList);
        stadiumVo.setImages(images);

        // 球场统计信息
        stadiumVo.setBlockCount(blockList.size());

        // 本周起止时间
        Timestamp begin = DateUtils.getBeginDayOfWeek(DateUtils.getNow());
        Timestamp end = DateUtils.getEndDayOfWeek(DateUtils.getNow());

        // 本周匹配人数
        List<FtMatchingPo> matchingCount = matchingService.queryMatchingList(QueryMatching.builder()
                .scheduleDateBegin(begin)
                .scheduleDateEnd(end)
                .build());
        stadiumVo.setMatchingCountInWeek(matchingCount.size());

        // 本周预定场次
        List<FtStadiumSchedulePo> scheduleCount = scheduleService.querySchedule(QuerySchedule.builder()
                .dateBeginTimestamp(begin)
                .dateEndTimestamp(end)
                .status(Lists.newArrayList(ScheduleStatus.HALF_BOOKED, ScheduleStatus.BOOKED))
                .build());
        stadiumVo.setBookedCountInWeek(scheduleCount.size());

        return stadiumVo;
    }

    @Override
    public FtStadiumPo getStadium(Long id) {
        return stadiumMapper.selectById(id);
    }

    @Override
    public List<FtStadiumPo> queryStadium(List<Long> ids) {

        return stadiumMapper.selectBatchIds(ids);
    }

    @Override
    public FtStadiumBlockPo getBlock(Long id) {
        return blockMapper.selectById(id);
    }

    @Override
    public List<FtStadiumBlockPo> queryBlock(List<Long> ids) {

        return blockMapper.selectBatchIds(ids);
    }
}
