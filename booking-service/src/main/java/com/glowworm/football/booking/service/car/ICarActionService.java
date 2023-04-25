package com.glowworm.football.booking.service.car;

import com.glowworm.football.booking.domain.car.vo.GetOnFormVo;
import com.glowworm.football.booking.domain.car.vo.LaunchCarFormVo;
import com.glowworm.football.booking.domain.user.UserBean;

/**
 * @author xuyongchang
 * @date 2023-04-20 17:09
 * 发车相关的action
 */
public interface ICarActionService {

    void launch (UserBean user, LaunchCarFormVo launchCarFormVo);

    void getOn (UserBean user, GetOnFormVo getOnFormVo);

    void getOff (UserBean user, Long carId);

    void dismiss (UserBean user, Long carId);
}
