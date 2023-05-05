package com.glowworm.football.booking.service.passenger;

import com.glowworm.football.booking.dao.po.passenger.FtPassengerPo;

import java.util.List;
import java.util.Map;

/**
 * @author xuyongchang
 * @date 2023-04-27 16:17
 * 乘客相关接口
 */
public interface IPassengerService {

    List<FtPassengerPo> queryPassengerOnBoard (Long carId);

    Map<Long, List<FtPassengerPo>> queryPassengerOnBoard (List<Long> carIds);


}
