package cn.mafangui.hotel.service.impl;

import cn.mafangui.hotel.entity.Order;
import cn.mafangui.hotel.entity.Room;
import cn.mafangui.hotel.entity.RoomType;
import cn.mafangui.hotel.enums.OrderStatus;
import cn.mafangui.hotel.mapper.OrderMapper;
import cn.mafangui.hotel.mapper.RoomMapper;
import cn.mafangui.hotel.mapper.RoomTypeMapper;
import cn.mafangui.hotel.service.OrderService;
import cn.mafangui.hotel.service.RoomService;
import cn.mafangui.hotel.service.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private RoomTypeService roomTypeService;
    @Autowired
    private RoomService roomService;

    @Override
    public int insert(Order order) {
        return orderMapper.insert(order);
    }

    @Override
    @Transactional
    public int addOrder(Order order) {
        if (roomTypeService.updateRest(order.getRoomTypeId(),-1) != 1){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return -2;
        }
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());
        List<Room> rooms = roomService.selectByType(order.getRoomTypeId());
        Room room = rooms.stream().filter(o -> o.getRoomStatus().equals(1)).findFirst().get();
        order.setRoomId(room.getRoomId());
        room.setRoomStatus(-2);
        roomService.update(room);
        return orderMapper.insertSelective(order);
    }

    @Override
    public int delete(Integer orderId) {
        return orderMapper.deleteByPrimaryKey(orderId);
    }

    @Override
    public Order selectById(Integer orderId) {
        return orderMapper.selectByPrimaryKey(orderId);
    }

    @Override
    public List<Order> selectByNameAndPhone(String name, String phone) {
        Order order = new Order();
        order.setName(name);
        order.setPhone(phone);
        order.setOrderStatus(OrderStatus.PAID.getCode());
        return orderMapper.selectByNameAndPhone(order);
    }

    @Override
    @Transactional
    public int update(Order order) {
        Order order2 = orderMapper.selectByPrimaryKey(order.getOrderId());
        Room room = roomService.selectById(order2.getRoomId());
        room.setRoomStatus(1);
        roomService.update(room);
        return orderMapper.updateByPrimaryKeySelective(order);
    }

    /**
     * 订单支付
     *  1.更改订单状态 -3
     *  2.修改房型余量 -2
     *  3.修改房间状态 -1
     * @param orderId
     * @return
     */
    @Override
    @Transactional
    public int payOrder(int orderId) {
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if (order == null | order.getOrderStatus() != OrderStatus.UNPAID.getCode()) {
            return -3;
        }
//        if (roomTypeService.updateRest(order.getRoomTypeId(),-1) != 1){
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//            return -2;
//        }
        order.setOrderStatus(OrderStatus.PAID.getCode());
        if (orderMapper.updateByPrimaryKeySelective(order) != 1){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 0;
        }
        Room room = roomService.selectById(order.getRoomId());
        room.setRoomStatus(0);
        roomService.update(room);
        return 1;
    }

    /**
     * 取消订单
     * 1. 更改订单状态 -3
     * 2. 修改房型余量（已付款）-2
     * @param orderId
     * @return
     */
    @Override
    public int cancelOrder(int orderId) {
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if (order == null ) return -3;
        order.setOrderStatus(OrderStatus.WAS_CANCELED.getCode());
        if (roomTypeService.updateRest(order.getRoomTypeId(),1) != 1){
            return -2;
        }
        Room room = roomService.selectById(order.getRoomId());
        room.setRoomStatus(1);
        roomService.update(room);
        return orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public Integer getOrderCount() {
        return orderMapper.getOrderCount();
    }

    @Override
    public List<Order> selectByUserId(int userId) {
        return orderMapper.selectByUserId(userId);
    }

    @Override
    public List<Order> AllOrders(String searchData,String status) {
        return orderMapper.selectAll(searchData,status);
    }

    @Override
    public List<Order> UsersAllOrders(int userId) {
        return orderMapper.selectAllByUser(userId, OrderStatus.WAS_DELETED.getCode());
    }


}
