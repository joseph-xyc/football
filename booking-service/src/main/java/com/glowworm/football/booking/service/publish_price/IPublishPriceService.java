package com.glowworm.football.booking.service.publish_price;

import com.glowworm.football.booking.dao.po.publish_price.FtPublishPricePo;
import com.glowworm.football.booking.domain.publish_price.query.QueryPublishPrice;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author xuyongchang
 * @date 2023-05-08 18:25
 */
public interface IPublishPriceService {

    List<FtPublishPricePo> queryPublishPrice (QueryPublishPrice query);

    FtPublishPricePo getPublishPrice (QueryPublishPrice query);

    Map<Long, BigDecimal> getAveragePrice (QueryPublishPrice query);
}
