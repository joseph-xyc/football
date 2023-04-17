package com.glowworm.football.booking.service.stadium;

import com.glowworm.football.booking.domain.stadium.StadiumScheduleBean;
import com.glowworm.football.booking.domain.stadium.query.QuerySchedule;

import java.util.List;

/**
 * @author xuyongchang
 * @date 2023-04-12 15:22
 * 球场-场地-时刻表服务
 */
public interface IStadiumScheduleService {

    List<StadiumScheduleBean> querySchedule (QuerySchedule query);

    StadiumScheduleBean getSchedule (Long id);
}
