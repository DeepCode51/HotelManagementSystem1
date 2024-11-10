package cn.mafangui.hotel.service;

import cn.mafangui.hotel.entity.RoomType;

import java.util.List;

public interface RoomTypeService {

    int insert(RoomType roomType);

    int delete(int typeId);

    int update(RoomType roomType);

    RoomType selectByName(String roomType);

    RoomType selectById(int typeId);

    List<RoomType> findAllType(String searchData);

    int updateRest(int typeId,int num);

    int addRest(int typeId);

    int minusRest(int typeId);

    List<RoomType> findAllRestType();
}
