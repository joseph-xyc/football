package com.glowworm.football.booking.service.matching;

import com.glowworm.football.booking.dao.po.matching.FtMatchingPo;
import com.glowworm.football.booking.domain.matching.query.QueryMatching;
import com.glowworm.football.booking.domain.matching.vo.MatchingVo;

import java.util.List;
import java.util.Map;

/**
 * @author xuyongchang
 * @date 2023-07-20 20:48
 */
public interface IMatchingService {

    Map<Long, List<FtMatchingPo>> queryMatching (QueryMatching query);

    List<FtMatchingPo> queryMatchingList (QueryMatching query);

    List<MatchingVo> queryMatchingVo (QueryMatching query);
}
