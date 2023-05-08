package com.glowworm.football.booking.web.webapi.publish_price;

import com.glowworm.football.booking.domain.common.context.WxContext;
import com.glowworm.football.booking.domain.common.demo.query.QueryDemo;
import com.glowworm.football.booking.domain.common.demo.vo.DemoVo;
import com.glowworm.football.booking.domain.common.response.Response;
import com.glowworm.football.booking.domain.publish_price.vo.PublishPriceFormVo;
import com.glowworm.football.booking.service.publish_price.IPublishPriceActionService;
import com.glowworm.football.booking.web.webapi.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xuyongchang
 * @date 2023-05-08 14:35
 * 刊例价管理
 */
@RestController
@RequestMapping("/api/web_api/publish_price")
public class PublishPriceController extends BaseController {

    @Autowired
    private IPublishPriceActionService publishPriceActionService;

    @GetMapping(value = "/list")
    public Response<List<DemoVo>> queryList (WxContext ctx, QueryDemo query) {

        return Response.success(null);
    }

    @PostMapping(value = "/init")
    public Response<String> init (WxContext ctx, @RequestBody PublishPriceFormVo formVo) {

        publishPriceActionService.init(formVo);
        return Response.success(null);
    }
}
