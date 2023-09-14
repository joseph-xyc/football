package com.glowworm.football.booking.service.msg.impl;

import com.glowworm.football.booking.domain.stadium.enums.ScheduleClock;
import com.glowworm.football.booking.service.util.DateUtils;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * @author xuyongchang
 * @date 2023-09-12 10:58
 */
@Data
@Component
public class MsgConfig {

    @Value("您已进入匹配池, 请耐心等待队长们的认领\r\n%s \r\n%s")
    private String matchingTemp;

    @Value("匹配成功! 推荐您尽快联系队长或球场客服, 确认好人员安排及费用 \r\n球场:%s \r\n场次:%s")
    private String matchedTemp;

    @Value("您已取消匹配, 很遗憾未能给您及时完成匹配~")
    private String matchingCancelTemp;

    @Value("您已申请预订, 请耐心等待球场客服确认,\r\n球场:%s\r\n客服电话:%s\r\n场次:%s")
    private String doBookingTemp;

    @Value("预订成功! 您的预订已被客服确认，建议您与客服联系并完成支付\r\n球场:%s\r\n客服电话:%s\r\n场次:%s")
    private String bookingConfirmTemp;

    @Value("您已主动取消球场预订, 订单id: %s \r\n%s\r\n%s")
    private String bookingCancelTemp;

    public String renderBlockStr (String stadiumName, String blockName) {
        return String.format("%s %s", stadiumName, blockName);
    }

    public String renderTimeStr (Timestamp scheduleDate, ScheduleClock clockBegin, ScheduleClock clockEnd) {
        return String.format("%s %s", DateUtils.getTimestamp2String(scheduleDate), clockBegin.getDesc() + " ~ " + clockEnd.getDesc());
    }

    public String renderDoBookingTemp (String blockName, String tel, String time) {

        return String.format(this.doBookingTemp, blockName, tel, time);
    }

    public String renderBookingConfirmTemp (String blockName, String tel, String time) {

        return String.format(this.bookingConfirmTemp, blockName, tel, time);
    }

}
