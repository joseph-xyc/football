package com.glowworm.football.booking.web.webapi.dictionary;

import com.glowworm.football.booking.domain.common.context.WxContext;
import com.glowworm.football.booking.domain.common.response.Response;
import com.glowworm.football.booking.domain.stadium.enums.District;
import com.glowworm.football.booking.domain.stadium.enums.ScaleType;
import com.glowworm.football.booking.domain.stadium.enums.StadiumSort;
import com.glowworm.football.booking.web.webapi.base.BaseController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xuyongchang
 * @date 2023-06-06 20:35
 * 通用字典api
 */
@RestController
@RequestMapping("/api/web_api/dictionary")
public class DictionaryController extends BaseController {

    @GetMapping(value = "/district")
    public Response<List<District>> district (WxContext ctx) {

        return Response.success(Arrays.stream(District.values()).collect(Collectors.toList()));
    }

    @GetMapping(value = "/scale_type")
    public Response<List<ScaleType>> scaleType (WxContext ctx) {

        return Response.success(Arrays.stream(ScaleType.values()).collect(Collectors.toList()));
    }

    @GetMapping(value = "/stadiumSort")
    public Response<List<StadiumSort>> stadiumSort (WxContext ctx) {

        return Response.success(Arrays.stream(StadiumSort.values()).collect(Collectors.toList()));
    }
}
