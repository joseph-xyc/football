package com.glowworm.football.booking.service.publish_price.impl;

import com.glowworm.football.booking.dao.mapper.FtPublishPriceMapper;
import com.glowworm.football.booking.dao.po.publish_price.FtPublishPricePo;
import com.glowworm.football.booking.domain.publish_price.enums.Week;
import com.glowworm.football.booking.domain.publish_price.vo.PublishPriceFormVo;
import com.glowworm.football.booking.domain.publish_price.vo.PublishPriceItemVo;
import com.glowworm.football.booking.service.publish_price.IPublishPriceActionService;
import com.glowworm.football.booking.service.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author xuyongchang
 * @date 2023-05-08 15:10
 */
@Slf4j
@Service
public class PublishPriceActionServiceImpl implements IPublishPriceActionService {

    @Autowired
    private FtPublishPriceMapper publishPriceMapper;

    @Override
    @Transactional
    public void init(PublishPriceFormVo formVo) {

        Utils.isTrue(Objects.nonNull(formVo), "刊例价表单不能为空");

        List<FtPublishPricePo> allPriceList = formVo.getPriceMap().entrySet().stream().map(entry -> {
            Week week = entry.getKey();
            List<PublishPriceItemVo> priceList = entry.getValue();

            return priceList.stream()
                    .map(item -> FtPublishPricePo.builder()
                            .stadiumId(formVo.getStadiumId())
                            .blockId(formVo.getBlockId())
                            .week(week)
                            .clockBegin(item.getClockBegin())
                            .clockEnd(item.getClockEnd())
                            .price(item.getPrice())
                            .build())
                    .collect(Collectors.toList());
        }).flatMap(Collection::stream).collect(Collectors.toList());

        allPriceList.forEach(publishPriceMapper::insert);
    }
}
