package com.glowworm.football.booking.service.user.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.glowworm.football.booking.dao.mapper.FtUserMapper;
import com.glowworm.football.booking.dao.po.user.FtUserPo;
import com.glowworm.football.booking.domain.user.UserBean;
import com.glowworm.football.booking.domain.common.context.WxContext;
import com.glowworm.football.booking.service.user.IUserService;
import com.glowworm.football.booking.service.user.config.UserConfig;
import com.glowworm.football.booking.service.util.FtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author xuyongchang
 * @date 2023/3/20
 */
@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private FtUserMapper userMapper;
    @Autowired
    private UserConfig userConfig;

    @Override
    public UserBean userInfo(String openId) {

        if (Strings.isEmpty(openId)) {
            return visitorUser();
        }

        FtUserPo user = userMapper.selectOne(Wrappers.lambdaQuery(FtUserPo.class).eq(FtUserPo::getOpenId, openId));
        if (Objects.isNull(user)) {
            return visitorUser();
        }

        return FtUtil.copy(user, UserBean.class);
    }

    private UserBean visitorUser () {

        return UserBean.builder()
                .id(userConfig.getVisitorUserId())
                .openId(userConfig.getVisitorOpenId())
                .username(userConfig.getVisitorName())
                .build();
    }

    @Override
    public void registerUser(WxContext ctx, UserBean userBean) {

        if (Strings.isEmpty(ctx.getOpenId())) {
            return;
        }

        FtUserPo user = FtUserPo.builder()
                .openId(ctx.getOpenId())
                .sourceFrom(ctx.getWxSource())
                .username(userBean.getUsername())
                .avatar(userBean.getAvatar())
                .sex(userBean.getSex())
                .build();

        userMapper.insert(user);
    }

}
