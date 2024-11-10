package cn.mafangui.hotel.controller.worker;

import cn.mafangui.hotel.entity.RoomType;
import cn.mafangui.hotel.response.AjaxResult;
import cn.mafangui.hotel.response.ResponseTool;
import cn.mafangui.hotel.service.RoomTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("房间类型控制器")
@RestController
@RequestMapping(value = "/op/room-type")
public class OpRoomTypeController {

    @Autowired
    private RoomTypeService roomTypeService;


    /**
     * 所有房型
     * @return
     */
    @ApiOperation("所有房型")
    @RequestMapping(value = "")
    public AjaxResult getAllRoomType(@RequestParam(value = "searchData",required = false) String searchData){
        List<RoomType> rooms = roomTypeService.findAllType(searchData);
        return ResponseTool.success(rooms);
    }

    /**
     * 查找有余量的房型
     * @return
     */
    @ApiOperation("查找有余量的房型")
    @RequestMapping(value = "/rest")
    public AjaxResult findAllRestRoomType(){
        return ResponseTool.success(roomTypeService.findAllRestType());
    }

    /**
     * 根据id查找房型
     * @param typeId
     * @return
     */
    @ApiOperation("根据id查找房型")
    @RequestMapping(value = "/{typeId}")
    public AjaxResult getById(@PathVariable int typeId){
        return ResponseTool.success(roomTypeService.selectById(typeId));
    }


    /**
     * 添加房型
     * @param roomType
     * @param price
     * @param discount
     * @param area
     * @param bedNum
     * @param bedSize
     * @param window
     * @param remark
     * @return
     */
    @ApiOperation("添加房型")
    @RequestMapping(method = RequestMethod.POST,value = "/add")
    public AjaxResult addRoomType(String roomType,Double price,Double discount,Integer area,
                           Integer bedNum,String bedSize,Integer window,String remark,Integer rest){
        RoomType rt = new RoomType(roomType,remark,price,discount,area,bedNum,bedSize,window);
        rt.setRest(rest);
        int result = roomTypeService.insert(rt);
        if(result!=1) return ResponseTool.failed("添加失败");
        return ResponseTool.success("添加成功");
    }

    /**
     * 修改房型
     * @param typeId
     * @param roomType
     * @param price
     * @param discount
     * @param area
     * @param bedNum
     * @param bedSize
     * @param window
     * @param rest
     * @param remark
     * @return
     */
    @ApiOperation("修改房型")
    @RequestMapping(method = RequestMethod.POST,value = "/update")
    public AjaxResult updateRoomType(Integer typeId,String roomType,Double price,Double discount,Integer area,
                                     Integer bedNum,String bedSize,Integer window,Integer rest,String remark){
        RoomType rt = new RoomType(roomType,remark,price,discount,area,bedNum,bedSize,window);
        rt.setTypeId(typeId);
        rt.setRest(rest);
        int result = roomTypeService.update(rt);
        if(result!=1) return ResponseTool.failed("修改失败");
        return ResponseTool.success("修改成功");
    }

    /**
     * 删除房型
     * @param typeId
     * @return
     */
    @ApiOperation("删除房型")
    @RequestMapping(method = RequestMethod.POST,value = "/delete/{typeId}")
    public AjaxResult deleteRoomType(@PathVariable Integer typeId){
        int result = roomTypeService.delete(typeId);
        if(result!=1) return ResponseTool.failed("删除失败");
        return ResponseTool.success("删除成功");
    }



}
