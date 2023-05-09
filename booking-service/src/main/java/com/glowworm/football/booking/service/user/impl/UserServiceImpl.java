package com.glowworm.football.booking.service.user.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.glowworm.football.booking.dao.mapper.FtUserMapper;
import com.glowworm.football.booking.dao.po.user.FtUserPo;
import com.glowworm.football.booking.domain.user.UserBean;
import com.glowworm.football.booking.domain.common.context.WxContext;
import com.glowworm.football.booking.domain.user.enums.Position;
import com.glowworm.football.booking.domain.user.enums.Style;
import com.glowworm.football.booking.domain.user.enums.UserType;
import com.glowworm.football.booking.domain.user.vo.CreateUserFormVo;
import com.glowworm.football.booking.service.user.IUserService;
import com.glowworm.football.booking.service.user.config.UserConfig;
import com.glowworm.football.booking.service.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        UserBean userBean = Utils.copy(user, UserBean.class);
        userBean.setPos(Position.getPositions(user.getPosition()));
        userBean.setStyles(Style.getStyles(user.getStyle()));

        return userBean;
    }

    private UserBean visitorUser () {

        return UserBean.builder()
                .id(userConfig.getVisitorUserId())
                .openId(userConfig.getVisitorOpenId())
                .username(userConfig.getVisitorName())
                .userType(UserType.VISITOR)
                .build();
    }

    @Transactional
    @Override
    public void registerUser(WxContext ctx, CreateUserFormVo formVo) {

        if (Strings.isEmpty(ctx.getOpenId())) {
            return;
        }

        FtUserPo user = FtUserPo.builder()
                .openId(ctx.getOpenId())
                .username(formVo.getUsername())
                .nickname(formVo.getNickname())
                .userType(UserType.ORDINARY)
                .avatar(formVo.getAvatar())
                .sex(formVo.getSex())
                .position(Position.getStr(formVo.getPos()))
                .style(Style.getStr(formVo.getStyles()))
                .build();

        userMapper.insert(user);
    }

    @Override
    public void updateUser(WxContext ctx, CreateUserFormVo formVo) {

        UserBean userBean = userInfo(ctx.getOpenId());
        Utils.throwError(userBean.getUserType().equals(UserType.VISITOR), "请您先登录");

        FtUserPo user = FtUserPo.builder()
                .id(userBean.getId())
                .username(formVo.getUsername())
                .nickname(formVo.getNickname())
                .avatar(formVo.getAvatar())
                .sex(formVo.getSex())
                .position(Position.getStr(formVo.getPos()))
                .style(Style.getStr(formVo.getStyles()))
                .build();
        userMapper.updateById(user);
    }

}
