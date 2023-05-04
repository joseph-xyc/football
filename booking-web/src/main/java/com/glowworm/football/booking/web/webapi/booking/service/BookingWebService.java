package com.glowworm.football.booking.web.webapi.booking.service;

import com.glowworm.football.booking.domain.booking.vo.BookingVo;
import com.glowworm.football.booking.domain.team.vo.TeamSimpleVo;
import com.glowworm.football.booking.service.team.ITeamService;
import com.glowworm.football.booking.service.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xuyongchang
 * @date 2023-05-04 20:07
 */
@Slf4j
@Service
public class BookingWebService {

    @Autowired
    private ITeamService teamService;

    public List<BookingVo> enhanceTeamSimpleInfo (List<BookingVo> bookingList) {

        if (CollectionUtils.isEmpty(bookingList)) {
            return Collections.emptyList();
        }

        List<Long> teamIds = bookingList.stream().map(BookingVo::getTeamId)
                .distinct()
                .collect(Collectors.toList());

        // 查随机队伍信息
        List<Long> randomTeamIds = teamIds.stream().filter(id -> !Utils.isPositive(id)).collect(Collectors.toList());
        Map<Long, TeamSimpleVo> randomTeamMap = teamService.queryRandomTeam(randomTeamIds);

        // 查真实队伍信息

        // enhance
        bookingList.forEach(item -> item.setTeamSimpleVo(randomTeamMap.get(item.getTeamId())));

        return bookingList;
    }
}
