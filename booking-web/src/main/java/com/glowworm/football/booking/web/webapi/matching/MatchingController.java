package com.glowworm.football.booking.web.webapi.matching;

import com.glowworm.football.booking.domain.common.context.WxContext;
import com.glowworm.football.booking.domain.common.response.Response;
import com.glowworm.football.booking.domain.matching.vo.MatchingFormVo;
import com.glowworm.football.booking.service.matching.IMatchingActionService;
import com.glowworm.football.booking.web.webapi.base.BaseController;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xuyongchang
 * @date 2023-07-25 11:59
 * 散人匹配接口
 */
@RestController
@RequestMapping("/api/web_api/matching")
public class MatchingController extends BaseController {

    @Autowired
    private IMatchingActionService matchingActionService;

    @PostMapping(value = "/do")
    public Response<String> doMatch (WxContext ctx, @RequestBody MatchingFormVo formVo) {

        matchingActionService.doMatch(getUser(ctx), formVo);
        return Response.success(Strings.EMPTY);
    }

    @PostMapping(value = "/undo")
    public Response<String> undoMatch (WxContext ctx, @RequestBody MatchingFormVo formVo) {

        matchingActionService.undoMatch(getUser(ctx), formVo);
        return Response.success(Strings.EMPTY);
    }
}
