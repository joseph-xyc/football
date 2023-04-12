package com.glowworm.football.booking.service.stadium;


import com.glowworm.football.booking.domain.common.context.WxContext;
import com.glowworm.football.booking.domain.stadium.QueryStadiumVo;
import com.glowworm.football.booking.domain.stadium.StadiumBean;
import com.glowworm.football.booking.domain.stadium.StadiumInfoVo;

import java.util.List;

/**
 * @author xuyongchang
 * @date 2023-04-08 14:47
 */
public interface IStadiumService {

    List<StadiumBean> queryList (WxContext ctx, QueryStadiumVo query);

    StadiumInfoVo getDetail (WxContext ctx, Long id);

}
