package com.glowworm.football.booking.service.user;

import com.glowworm.football.booking.domain.user.UserBean;
import com.glowworm.football.booking.domain.common.context.WxContext;

import java.util.List;

/**
 * @author xuyongchang
 * @date 2023/3/20
 */
public interface IUserService {

    UserBean userInfo (String openId);

    void registerUser (WxContext ctx, UserBean userBean);

}
