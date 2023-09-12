package com.glowworm.football.booking.service.msg.impl;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author xuyongchang
 * @date 2023-09-12 10:58
 */
@Data
@Component
public class MsgConfig {

    @Value("您已进入匹配池, 请耐心等待队长们的认领\r\n%s \r\n%s")
    private String matchingTemp;

    @Value("匹配成功! 推荐您尽快联系队长或球场客服, 确认好人员安排及费用 \r\n球场信息:%s \r\n时间:%s")
    private String matchedTemp;

    @Value("您已取消匹配, 很遗憾未能给您及时完成匹配~")
    private String matchingCancelTemp;

    @Value("您已成功预定, 请耐心等待球场客服确认,客服电话: %s\r\n%s\r\n%s")
    private String doBookingTemp;

    @Value("您已主动取消球场预订, 订单id: %s \r\n%s\r\n%s")
    private String bookingCancelTemp;
}
