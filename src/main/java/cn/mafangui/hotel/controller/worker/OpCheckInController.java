package cn.mafangui.hotel.controller.worker;

import cn.mafangui.hotel.entity.CheckIn;
import cn.mafangui.hotel.entity.Order;
import cn.mafangui.hotel.entity.Room;
import cn.mafangui.hotel.enums.OrderStatus;
import cn.mafangui.hotel.response.AjaxResult;
import cn.mafangui.hotel.response.ResponseTool;
import cn.mafangui.hotel.service.CheckInService;
import cn.mafangui.hotel.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Objects;

@Api("订单检查控制器")
@RestController
@RequestMapping(value = "/op/checkIn")
public class OpCheckInController {

    @Autowired
    private CheckInService checkInService;

    @Autowired
    private OrderService orderService;

    @ApiOperation("入驻")
    @RequestMapping(value = "/in")
    public AjaxResult addCheckIn(int orderId){
        return checkInService.checkIn(orderId);
    }

    @ApiOperation("退订")
    @RequestMapping(value = "/out")
    public AjaxResult checkOut(int orderId) {
        return checkInService.checkOut(orderId);
    }


}
