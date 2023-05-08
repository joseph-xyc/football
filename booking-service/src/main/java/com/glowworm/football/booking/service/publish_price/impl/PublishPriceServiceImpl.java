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
    public FtPublishPricePo getPublishPrice(QueryPublishPrice query) {

        LambdaQueryWrapper<FtPublishPricePo> wrapper = Wrappers.lambdaQuery(FtPublishPricePo.class)
                .eq(FtPublishPricePo::getBlockId, query.getBlockId())
                .eq(FtPublishPricePo::getWeek, query.getWeek())
                .eq(FtPublishPricePo::getClockBegin, query.getClockBegin());

        boolean exists = publishPriceMapper.exists(wrapper);

        Utils.isTrue(exists, "请先维护好此球场的刊例价表信息");

        return publishPriceMapper.selectOne(wrapper);
    }
}
