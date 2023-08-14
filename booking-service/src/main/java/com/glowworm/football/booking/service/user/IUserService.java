package com.glowworm.football.booking.service.user;

import com.glowworm.football.booking.dao.po.user.FtUserPo;
import com.glowworm.football.booking.domain.user.UserBean;
import com.glowworm.football.booking.domain.common.context.WxContext;
import com.glowworm.football.booking.domain.user.vo.CreateUserFormVo;

import java.util.List;
import java.util.Map;


/**
 * @author xuyongchang
 * @date 2023/3/20
 */
public interface IUserService {

    List<FtUserPo> queryUser (List<Long> ids);

    Map<Long, FtUserPo> queryUserMap (List<Long> ids);

    UserBean userInfo (String openId);

    void registerUser (WxContext ctx, CreateUserFormVo formVo);

    void updateUser (WxContext ctx, CreateUserFormVo formVo);

}
