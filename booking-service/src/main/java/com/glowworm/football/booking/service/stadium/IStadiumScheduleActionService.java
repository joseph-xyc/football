package com.glowworm.football.booking.service.stadium;

/**
 * @author xuyongchang
 * @date 2023-04-12 15:53
 * 球场-场地-时刻表action接口
 */
public interface IStadiumScheduleActionService {

    void appendSchedule (String endDate, Long stadiumId, Long blockId);

}
