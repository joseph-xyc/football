package com.glowworm.football.booking.service.publish_price;

import com.glowworm.football.booking.dao.po.publish_price.FtPublishPricePo;
import com.glowworm.football.booking.domain.publish_price.query.QueryPublishPrice;

/**
 * @author xuyongchang
 * @date 2023-05-08 18:25
 */
public interface IPublishPriceService {

    FtPublishPricePo getPublishPrice (QueryPublishPrice query);
}
