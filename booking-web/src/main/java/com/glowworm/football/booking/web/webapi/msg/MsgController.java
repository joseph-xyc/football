package com.glowworm.football.booking.web.webapi.msg;

import com.glowworm.football.booking.dao.po.msg.FtMsgPo;
import com.glowworm.football.booking.domain.common.context.WxContext;
import com.glowworm.football.booking.domain.common.response.Response;
import com.glowworm.football.booking.domain.msg.MsgBean;
import com.glowworm.football.booking.domain.msg.vo.MsgVo;
import com.glowworm.football.booking.service.msg.IMsgActionService;
import com.glowworm.football.booking.service.msg.IMsgService;
import com.glowworm.football.booking.service.util.Utils;
import com.glowworm.football.booking.web.webapi.base.BaseController;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xuyongchang
 * @date 2023-09-07 10:54
 * 消息管理
 */
@RestController
@RequestMapping("/api/web_api/msg")
public class MsgController extends BaseController {

    @Autowired
    private IMsgService msgService;
    @Autowired
    private IMsgActionService msgActionService;

    @GetMapping(value = "/list")
    public Response<List<MsgVo>> getMsgList (WxContext ctx) {

        List<FtMsgPo> msgList = msgService.getMsgList(getUser(ctx).getId());
        List<MsgVo> result = Utils.copy(msgList, MsgVo.class);
        return Response.success(result);
    }

    @PostMapping(value = "/new")
    public Response<String> newMsg (WxContext ctx, @RequestBody MsgBean msg) {

        msgActionService.newMsg(msg);
        return Response.success(Strings.EMPTY);
    }

    @GetMapping(value = "/read_simplify")
    public Response<String> readMsgSimplify (WxContext ctx) {

        msgActionService.readMsgSimplify(getUser(ctx).getId());
        return Response.success(Strings.EMPTY);
    }
}

