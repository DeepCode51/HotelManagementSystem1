package cn.mafangui.hotel.controller.worker;

import cn.mafangui.hotel.entity.Room;
import cn.mafangui.hotel.entity.RoomType;
import cn.mafangui.hotel.response.AjaxResult;
import cn.mafangui.hotel.response.ResponseTool;
import cn.mafangui.hotel.service.RoomService;
import cn.mafangui.hotel.service.RoomTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api("房间信息控制器")
@RestController
@RequestMapping(value = "/op/room")
public class OpRoomController {

    @Autowired
    private RoomService roomService;
    @Autowired
    private RoomTypeService roomTypeService;

    @ApiOperation("新增")
    @RequestMapping(value = "/add")
    public AjaxResult addRoom(String roomNumber, Integer typeId, String roomType, double roomPrice, double roomDiscount, String remark){
        if(roomService.selectByNumber(roomNumber)!=null) {
            return ResponseTool.failed("房间号码重复");
        }
        Room room = new Room(roomNumber,typeId,roomType,roomPrice,roomDiscount,remark);
        RoomType rt = new RoomType();
        if (roomService.insert(room) == 1){
            rt.setTypeId(typeId);
            rt.setRest(roomTypeService.selectById(typeId).getRest() + 1);
            if(roomTypeService.update(rt)!=1) {
                return ResponseTool.failed("添加失败");
            }
        }else {
            return ResponseTool.failed("添加失败");
        }
        return ResponseTool.success("添加成功");
    }

    @ApiOperation("删除")
    @RequestMapping(method = RequestMethod.POST,value = "/delete/{roomId}")
    public AjaxResult deleteRoom(@PathVariable Integer roomId){
        if(roomService.delete(roomId)!=1) {
            return ResponseTool.failed("删除失败");
        }
        return ResponseTool.success("删除成功");
    }

    @ApiOperation("修改")
    @RequestMapping(value = "/update")
    public AjaxResult updateRoom(Integer roomId,String roomNumber,Integer typeId,
                          String roomType,double roomPrice,double roomDiscount,String remark){
        Room room = new Room(roomNumber,typeId,roomType,roomPrice,roomDiscount,remark);
        room.setRoomId(roomId);
        if(roomService.update(room)!=1) {
            return ResponseTool.failed("更新失败");
        }
        return ResponseTool.success("更新成功");
    }

    @ApiOperation("通过id查询")
    @RequestMapping(value = "/{id}")
    public AjaxResult getById(@PathVariable Integer id){
        return ResponseTool.success(roomService.selectById(id));
    }

    @ApiOperation("通过房间类型查询")
    @RequestMapping(value = "/type/{typeId}")
    public AjaxResult getByType(@PathVariable Integer typeId){
        return ResponseTool.success(roomService.selectByType(typeId));
    }

    @ApiOperation("通过状态查询")
    @RequestMapping(value = "/status/{statusId}")
    public AjaxResult getByStatus(@PathVariable Integer statusId){
        return ResponseTool.success(roomService.selectByStatus(statusId));
    }

    @ApiOperation("通过条件查询")
    @RequestMapping(value = "")
    public AjaxResult getAll(@RequestParam(value = "searchData",required = false) String searchData){
        return ResponseTool.success(roomService.selectAll(searchData));
    }

}
