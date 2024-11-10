package cn.mafangui.hotel.service.impl;

import cn.mafangui.hotel.entity.CheckIn;
import cn.mafangui.hotel.entity.Order;
import cn.mafangui.hotel.entity.Room;
import cn.mafangui.hotel.entity.RoomType;
import cn.mafangui.hotel.enums.OrderStatus;
import cn.mafangui.hotel.enums.RoomStatus;
import cn.mafangui.hotel.mapper.CheckInMapper;
import cn.mafangui.hotel.response.AjaxResult;
import cn.mafangui.hotel.response.ResponseTool;
import cn.mafangui.hotel.service.CheckInService;
import cn.mafangui.hotel.service.OrderService;
import cn.mafangui.hotel.service.RoomService;
import cn.mafangui.hotel.service.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class CheckInServiceImpl implements CheckInService {
    @Autowired
    private CheckInMapper checkInMapper;
    @Autowired
    private OrderService orderService;
    @Autowired
    private RoomTypeService roomTypeService;

    @Autowired
    private RoomService roomService;

    /**
     * 入住登记
     * @param orderId
     * @return
     */
    @Override
    @Transactional
    public AjaxResult checkIn(int orderId) {
        //修改订单状态为已入住
        Order order = orderService.selectById(orderId);
        if (Objects.isNull(order)){
            return ResponseTool.failed("该号对应的订单不存在");
        }
        order.setOrderStatus(OrderStatus.CHECK_IN.getCode());
        order.setUpdateTime(new Date());
        orderService.update(order);

        Room room = roomService.selectById(order.getRoomId());
        room.setRoomStatus(-1);
        roomService.update(room);

        CheckIn checkIn = new CheckIn();
        checkIn.setOrderId(orderId);
        checkIn.setRoomNumber(room.getRoomNumber());
        checkIn.setRoomId(room.getRoomId());
        checkIn.setCheckInTime(new Date());
        checkInMapper.insert(checkIn);

        return ResponseTool.success("入住成功");
    }

    /**
     * 退房登记
     * @param orderId
     * @return
     */
    @Override
    public AjaxResult checkOut(int  orderId){
        //修改订单状态为已入住
        Order order = orderService.selectById(orderId);
        if (Objects.isNull(order)){
            return ResponseTool.failed("该号对应的订单不存在");
        }
        order.setOrderStatus(OrderStatus.CHECK_OUT.getCode());
        order.setUpdateTime(new Date());
        orderService.update(order);
        //修改剩余房间数+1
        RoomType roomType = roomTypeService.selectById(order.getRoomTypeId());
        roomType.setRest(roomType.getRest()+1);
        roomType.setUpdateTime(new Date());
        roomTypeService.update(roomType);

        Room room = roomService.selectById(order.getRoomId());
        room.setRoomStatus(1);
        roomService.update(room);
        return ResponseTool.success("退房成功");
    }
}
