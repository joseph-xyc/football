package com.glowworm.football.booking.web.webapi.car;

import com.glowworm.football.booking.dao.po.car.FtCarPo;
import com.glowworm.football.booking.domain.car.query.QueryCar;
import com.glowworm.football.booking.domain.car.vo.CarSimpleVo;
import com.glowworm.football.booking.domain.car.vo.GetOnFormVo;
import com.glowworm.football.booking.domain.car.vo.LaunchCarFormVo;
import com.glowworm.football.booking.domain.common.context.WxContext;
import com.glowworm.football.booking.domain.common.response.Response;
import com.glowworm.football.booking.service.car.ICarActionService;
import com.glowworm.football.booking.service.car.ICarService;
import com.glowworm.football.booking.web.webapi.base.BaseController;
import com.glowworm.football.booking.web.webapi.car.service.CarWebService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xuyongchang
 * @date 2023-04-20 16:46
 * 发车相关的接口
 */
@RestController
@RequestMapping("/api/web_api/car")
public class CarController extends BaseController {

    @Autowired
    private ICarActionService carActionService;
    @Autowired
    private ICarService carService;
    @Autowired
    private CarWebService carWebService;

    @GetMapping(value = "/list")
    public Response<List<CarSimpleVo>> queryList (WxContext ctx, QueryCar query) {

        List<FtCarPo> carPos = carService.queryCar(getUser(ctx), query);
        return Response.success(carWebService.po2vo(getUser(ctx), carPos));
    }

    @GetMapping(value = "/list_in_schedule")
    public Response<List<CarSimpleVo>> queryListInSchedule (WxContext ctx, QueryCar query) {

        List<FtCarPo> carPos = carService.queryCar(getUser(ctx), query);
        return Response.success(carWebService.listInSchedule(getUser(ctx), carPos));
    }

    @PostMapping(value = "/launch")
    public Response<String> launch (WxContext ctx, @RequestBody LaunchCarFormVo launchCarFormVo) {

        carActionService.launch(getUser(ctx), launchCarFormVo);
        return Response.success(Strings.EMPTY);
    }

    @PostMapping(value = "/get_on")
    public Response<String> getOn (WxContext ctx, @RequestBody GetOnFormVo getOnFormVo) {

        carActionService.getOn(getUser(ctx), getOnFormVo);
        return Response.success(Strings.EMPTY);
    }

    @PostMapping(value = "/get_off/{carId}")
    public Response<String> getOff (WxContext ctx, @PathVariable(value = "carId") Long carId) {

        carActionService.getOff(getUser(ctx), carId);
        return Response.success(Strings.EMPTY);
    }
}
