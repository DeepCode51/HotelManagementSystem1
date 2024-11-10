package cn.mafangui.hotel.controller.common;

import cn.mafangui.hotel.response.AjaxResult;
import cn.mafangui.hotel.response.ResponseTool;
import cn.mafangui.hotel.service.HotelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("酒店信息控制器")
@RestController
@RequestMapping(value = "/hotel")
public class HotelController {
    @Autowired
    private HotelService hotelService;

    @ApiOperation("获取酒店信息")
    @RequestMapping(value = "")
    public AjaxResult getAllHotel(){
        return ResponseTool.success(hotelService.selectAll());
    }
}
