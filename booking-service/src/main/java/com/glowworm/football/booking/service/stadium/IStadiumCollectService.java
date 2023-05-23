package com.glowworm.football.booking.service.stadium;

import com.glowworm.football.booking.dao.po.stadium.FtStadiumCollectPo;
import com.glowworm.football.booking.domain.user.UserBean;

import java.util.List;

/**
 * @author xuyongchang
 * @date 2023-05-23 16:14
 * 球场收藏相关服务
 */
public interface IStadiumCollectService {

    List<FtStadiumCollectPo> queryStadiumCollect (UserBean user);

    void collectStadium (UserBean user, Long stadiumId);

    void unCollectStadium (UserBean user, Long stadiumId);
}
