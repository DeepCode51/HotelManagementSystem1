package cn.mafangui.hotel.controller.serviceStatistics;


import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.mafangui.hotel.entity.Order;
import cn.mafangui.hotel.entity.Room;
import cn.mafangui.hotel.entity.RoomType;
import cn.mafangui.hotel.enums.OrderStatus;
import cn.mafangui.hotel.mapper.StatisticsMapper;
import cn.mafangui.hotel.response.AjaxResult;
import cn.mafangui.hotel.response.ResponseTool;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/service/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsMapper statisticsMapper;

    @RequestMapping(value = "/roomTypeDtatistics")
    public AjaxResult roomTypeDtatistics(){
        List<RoomType> roomTypes = statisticsMapper.getRoomType();
        List<Room> room = statisticsMapper.getAllRoom();

        List<Map<String,Object>> result = new ArrayList<>();
        for(RoomType roomType : roomTypes){
            Map<String,Object> cell = new HashMap<>();
            cell.put("name",roomType.getRoomType());
            cell.put("value",room.stream().filter(o->o.getTypeId().equals(roomType.getTypeId())).count());
            cell.put("unit","间");
            result.add(cell);
        }
        return ResponseTool.success(result);
    }


    @RequestMapping(value = "/orderVolumeStatistics")
    public AjaxResult orderVolumeStatistics(){
        Map<String,Object> result = new HashMap<>();
        List<Map<String,Object>> list  = statisticsMapper.orderVolumeStatistics();
        List<String> days = dateArray(new Date(),6);
        List<String> orderNum = fetchOrderNum(days, list, "orderNum");
        Collections.reverse(days);
        Collections.reverse(orderNum);
        result.put("days",days);
        result.put("orderNum",orderNum);
        return ResponseTool.success(result);
    }


    @RequestMapping(value = "/roomTypeOrderStatistics")
    public AjaxResult roomTypeOrderStatistics(){
        List<RoomType> roomTypes = statisticsMapper.getRoomType();
        List<Order> orderList = statisticsMapper.orderAll();

        List<Map<String,Object>> result = new ArrayList<>();
        for(RoomType roomType : roomTypes){
            Map<String,Object> cell = new HashMap<>();
            cell.put("name",roomType.getRoomType());
            cell.put("value",orderList.stream().filter(o->o.getRoomTypeId().equals(roomType.getTypeId())).count());
            cell.put("unit","单");
            result.add(cell);
        }
        return ResponseTool.success(result);
    }


    @RequestMapping(value = "/roomAccommodationDtatistics")
    public AjaxResult roomAccommodationDtatistics(){
        Map<String,Object> result = new HashMap<>();
        List<Room> room = statisticsMapper.getAllRoom();
        List<Map<String,Object>> list  = statisticsMapper.roomAccommodationDtatistics();
        List<String> months = dateArray(new Date(),11);
        List<String> checkinNum = fetchOrderNumV2(months, list, "checkinNum",room);
        Collections.reverse(months);
        Collections.reverse(checkinNum);
        result.put("days",months);
        result.put("checkinNum",checkinNum);
        return ResponseTool.success(result);
    }

    private List<String> fetchOrderNumV2(List<String> days,List<Map<String, Object>> listMaps,String tag,List<Room> room){
        List<String> result = new ArrayList<>();
        for (String day:days){
            boolean in = false;
            for (Map<String,Object> map:listMaps) {
                if(day.equals(map.get("days"))){
                    in = true;
                    long count = room.stream().filter(o -> o.getCreateTime().before(DateUtil.parse(day, "yyyy-MM").offset(DateField.MONTH, 1)))
                            .count();
                    if(count != 0 ){
                        BigDecimal multiply = new BigDecimal(String.valueOf(map.get(tag))).divide(new BigDecimal(count*30),5, RoundingMode.HALF_UP)
                                .multiply(new BigDecimal(100)).setScale(2,RoundingMode.HALF_UP);
                        result.add(multiply.toString());
                    }else{
                        result.add("0");
                    }
                    break;
                }
            }
            if(!in){
                result.add("0");
            }
        }
        return result;
    }


    public static void main(String[] args) {
        BigDecimal multiply = new BigDecimal(String.valueOf("1")).divide(new BigDecimal(5), 2,RoundingMode.HALF_UP)
                .multiply(new BigDecimal(100));
        System.out.println(multiply);
    }
    private List<String> fetchOrderNum(List<String> days,List<Map<String, Object>> listMaps,String tag){
        List<String> result = new ArrayList<>();
        for (String day:days){
            boolean in = false;
            for (Map<String,Object> map:listMaps) {
                if(day.equals(map.get("days"))){
                    in = true;
                    result.add(String.valueOf(map.get(tag)));
                    break;
                }
            }
            if(!in){
                result.add("0");
            }
        }
        return result;
    }


    private List<String> dateArray(Date dateAfterSeven,int index){
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= index; i++) {
            list.add(DateUtil.offsetMonth(dateAfterSeven,-i).toString("yyyy-MM"));
        }

        return list;
    }


}
