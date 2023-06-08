package com.glowworm.football.booking.service.publish_price.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.glowworm.football.booking.dao.mapper.FtPublishPriceMapper;
import com.glowworm.football.booking.dao.po.publish_price.FtPublishPricePo;
import com.glowworm.football.booking.domain.publish_price.query.QueryPublishPrice;
import com.glowworm.football.booking.service.publish_price.IPublishPriceService;
import com.glowworm.football.booking.service.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author xuyongchang
 * @date 2023-05-08 18:31
 */
@Slf4j
@Service
public class PublishPriceServiceImpl implements IPublishPriceService {

    @Autowired
    private FtPublishPriceMapper publishPriceMapper;

    @Override
    public List<FtPublishPricePo> queryPublishPrice(QueryPublishPrice query) {

        LambdaQueryWrapper<FtPublishPricePo> wrapper = Wrappers.lambdaQuery(FtPublishPricePo.class)
                .in(!CollectionUtils.isEmpty(query.getStadiumIds()), FtPublishPricePo::getStadiumId, query.getStadiumIds())
                .eq(Objects.nonNull(query.getBlockId()), FtPublishPricePo::getBlockId, query.getBlockId())
                .eq(Objects.nonNull(query.getWeek()), FtPublishPricePo::getWeek, query.getWeek())
                .in(!CollectionUtils.isEmpty(query.getWeeks()), FtPublishPricePo::getWeek, query.getWeeks())
                .eq(Objects.nonNull(query.getClockBegin()), FtPublishPricePo::getClockBegin, query.getClockBegin());

        return publishPriceMapper.selectList(wrapper);
    }

    @Override
    public FtPublishPricePo getPublishPrice(QueryPublishPrice query) {

        List<FtPublishPricePo> priceList = queryPublishPrice(query);

        Utils.isTrue(!CollectionUtils.isEmpty(priceList), "请先维护好此球场的刊例价表信息");

        return priceList.get(0);
    }

    @Override
    public Map<Long, BigDecimal> getAveragePrice(QueryPublishPrice query) {

        // 查价格
        List<FtPublishPricePo> priceList = queryPublishPrice(query);
        if (CollectionUtils.isEmpty(priceList)) {
            return Collections.emptyMap();
        }

        // 转map
        Map<Long, List<FtPublishPricePo>> priceMap = priceList.stream()
                .collect(Collectors.groupingBy(FtPublishPricePo::getStadiumId));
        // 取平均数
        return priceMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> {
            List<Integer> allPrice = entry.getValue().stream()
                    .map(FtPublishPricePo::getPrice)
                    .collect(Collectors.toList());
            // 加和
            Integer sum = allPrice.stream().reduce(0, Integer::sum);
            // 总数
            int size = allPrice.size();
            // 平均数
            return Utils.divide(BigDecimal.valueOf(sum), BigDecimal.valueOf(size));
        }));
    }
}
