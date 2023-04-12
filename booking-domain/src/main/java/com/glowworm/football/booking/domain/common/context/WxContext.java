package com.glowworm.football.booking.domain.common.context;

import lombok.Builder;
import lombok.Data;

/**
 * @author xuyongchang
 * @date 2023/3/27
 * 微信小程序的用户信息所封装的用户上下文ctx
 */
@Data
@Builder
public class WxContext {

    /**
     * 用户的openId
     * 当前小程序下，用户唯一标识
     */
    private String openId;

    /**
     * 用户的unionId
     * 当前开放平台旗下所有应用，用户唯一标识
     */
    private String unionId;

    /**
     * 调用来源（本次运行是被什么触发）
     */
    private String wxSource;
}
