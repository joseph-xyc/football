package com.glowworm.football.booking.service.matching;

import com.glowworm.football.booking.domain.matching.vo.MatchingFormVo;
import com.glowworm.football.booking.domain.user.UserBean;

/**
 * @author xuyongchang
 * @date 2023-07-25 17:11
 * 散人匹配的action接口
 */
public interface IMatchingActionService {

    void doMatch (UserBean user, MatchingFormVo formVo);

    void undoMatch (UserBean user, MatchingFormVo formVo);
}
