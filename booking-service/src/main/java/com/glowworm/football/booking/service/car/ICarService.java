package com.glowworm.football.booking.service.car;

import com.glowworm.football.booking.dao.po.car.FtCarPo;
import com.glowworm.football.booking.domain.car.query.QueryCar;

import java.util.List;

/**
 * @author xuyongchang
 * @date 2023-04-20 17:20
 * 发车查询服务
 */
public interface ICarService {

    List<FtCarPo> queryCar (QueryCar query);
}
