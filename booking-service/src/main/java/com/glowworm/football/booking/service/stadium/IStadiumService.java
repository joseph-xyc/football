package com.glowworm.football.booking.service.stadium;


import com.glowworm.football.booking.dao.po.stadium.FtStadiumBlockPo;
import com.glowworm.football.booking.dao.po.stadium.FtStadiumCollectPo;
import com.glowworm.football.booking.dao.po.stadium.FtStadiumPo;
import com.glowworm.football.booking.domain.stadium.query.QueryStadium;
import com.glowworm.football.booking.domain.stadium.StadiumBean;
import com.glowworm.football.booking.domain.stadium.vo.StadiumInfoVo;

import java.util.List;

/**
 * @author xuyongchang
 * @date 2023-04-08 14:47
 */
public interface IStadiumService {

    List<StadiumBean> queryList (QueryStadium query);

    StadiumInfoVo getDetail (Long id);

    FtStadiumPo getStadium (Long id);

    FtStadiumBlockPo getBlock (Long id);

    List<FtStadiumBlockPo> queryBlock (List<Long> ids);

}
