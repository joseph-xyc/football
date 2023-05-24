package com.glowworm.football.booking.service.stadium;

import com.glowworm.football.booking.dao.po.stadium.FtStadiumTagPo;

import java.util.List;
import java.util.Map;

/**
 * @author xuyongchang
 * @date 2023-05-24 15:25
 */
public interface IStadiumTagService {

    List<FtStadiumTagPo> queryTags (Long stadiumId);

    Map<Long, List<FtStadiumTagPo>> queryTagMap (List<Long> stadiumIds);
}
