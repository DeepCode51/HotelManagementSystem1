package cn.mafangui.hotel.controller.user;

import cn.mafangui.hotel.entity.Order;
import cn.mafangui.hotel.entity.Room;
import cn.mafangui.hotel.entity.User;
import cn.mafangui.hotel.enums.OrderStatus;
import cn.mafangui.hotel.response.AjaxResult;
import cn.mafangui.hotel.response.MsgType;
import cn.mafangui.hotel.response.ResponseTool;
import cn.mafangui.hotel.service.OrderService;
import cn.mafangui.hotel.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * 订单接口
 */
@Api("用户订单信息控制器")
@RestController
@RequestMapping(value = "/user/order")
public class UserOrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    /**
     * 添加预订
     * 订单状态默认为未付款状态
     * @param orderTypeId
     * @param orderType
     * @param userId
     * @param name
     * @param phone
     * @param roomTypeId
     * @param roomType
     * @param orderDate
     * @param orderDays
     * @param orderCost
     * @return
     */
    @ApiOperation("添加订单")
    @RequestMapping(value = "/add")
    public AjaxResult addOrder(int orderTypeId,String orderType, int userId,String name, String phone,int roomTypeId, String roomType,
                        @DateTimeFormat(pattern = "yyyy-MM-dd") Date orderDate, Integer orderDays, Double orderCost){
        Order order = new Order(orderTypeId,orderType,userId,name,phone,roomTypeId,
                roomType,orderDate,orderDays, OrderStatus.UNPAID.getCode(),orderCost);
        int re = orderService.addOrder(order);
        if(re!=1) {
            ResponseTool.failed(MsgType.FAILED);
        }
        return ResponseTool.success(MsgType.SUCCESS);
    }


    /**
     * 删除订单
     * @param orderId
     * @return
     */
    @ApiOperation("删除订单")
    @RequestMapping(value = "/delete")
    public AjaxResult deleteOrderByUser(int orderId){
        Order order = new Order(orderId,OrderStatus.WAS_DELETED.getCode());
        int re =  orderService.update(order);
        if(re!=1) ResponseTool.failed(MsgType.FAILED);
        return ResponseTool.success(MsgType.SUCCESS);
    }


    /**
     * 订单支付
     * @param orderId
     * @return
     */
    @ApiOperation("订单支付")
    @RequestMapping(method = RequestMethod.POST,value = "/pay")
    public AjaxResult payOrder(Integer orderId,String username,String password){
        User user = userService.selectByUsernameAndPassword(username,password);
        if(user==null) return ResponseTool.failed("密码错误");
        int re = orderService.payOrder(orderId);
        if(re!=1) ResponseTool.failed(MsgType.FAILED);
        return ResponseTool.success(MsgType.SUCCESS);
    }

    /**
     * 取消订单
     * @param orderId
     * @return
     */
    @ApiOperation("取消订单")
    @RequestMapping(value = "/cancel")
    public AjaxResult cancelOrder(int orderId){
        int re = orderService.cancelOrder(orderId);
        if(re!=1) ResponseTool.failed(MsgType.FAILED);
        return ResponseTool.success(MsgType.SUCCESS);
    }


    /**
     * 客户查询个人所有订单（不包括被自己删除的）
     * @param userId
     * @return
     */
    @ApiOperation("客户查询个人所有订单")
    @RequestMapping(value = "")
    public AjaxResult getAllByUser(int userId){
        return ResponseTool.success(orderService.UsersAllOrders(userId));
    }

    /**
     * 根据订单号查询订单
     * @param orderId
     * @return
     */
    @ApiOperation("根据订单号查询订单")
    @RequestMapping(value = "/{orderId}")
    public AjaxResult getById(@PathVariable int orderId){
        return ResponseTool.success(orderService.selectById(orderId));
    }

}
