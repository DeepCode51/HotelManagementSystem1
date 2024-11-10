package cn.mafangui.hotel.mapper;

import cn.mafangui.hotel.entity.Order;
import cn.mafangui.hotel.entity.Room;
import cn.mafangui.hotel.entity.RoomType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface StatisticsMapper {

    List<RoomType> getRoomType();

    List<Room> getAllRoom();

    List<Map<String, Object>> orderVolumeStatistics();

    List<Order> orderAll();

    List<Map<String, Object>> roomAccommodationDtatistics();
}
