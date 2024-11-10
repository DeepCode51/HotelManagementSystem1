package cn.mafangui.hotel.controller.worker;


import cn.mafangui.hotel.entity.WorkRole;
import cn.mafangui.hotel.response.AjaxResult;
import cn.mafangui.hotel.response.ResponseTool;
import cn.mafangui.hotel.service.WorkRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author
 * @version 1.0
 * @ClassName WorkRoleController
 * @Description TODO
 * @createTime 2023-3-19 10:19
 */
@RestController
@RequestMapping(value = "/op/workRole")
public class WorkRoleController {

    @Autowired
    private WorkRoleService workRoleService;

    @GetMapping("/getById")
    public AjaxResult getById(Integer id){
        return ResponseTool.success(workRoleService.getById(id));
    }

    @RequestMapping("/selectAll")
    public AjaxResult selectAll(@RequestParam(value = "searchData",required = false) String searchData){
        return ResponseTool.success(workRoleService.selectAll(searchData));
    }

    @PostMapping("/insert")
    public AjaxResult insert(String roleName){
        WorkRole workRole=new WorkRole();
        workRole.setRoleName(roleName);
        workRoleService.insert(workRole);
        return ResponseTool.success("新增成功");
    }

    @PostMapping("/update")
    public AjaxResult update(Integer id,String roleName){
        WorkRole workRole=new WorkRole();
        workRole.setRoleName(roleName);
        workRole.setId(id);
        workRoleService.update(workRole);
        return  ResponseTool.success("更新成功");
    }

    @PostMapping("/delete")
    public AjaxResult delete(Integer id){
        workRoleService.delete(id);
        return ResponseTool.success("删除成功");
    }

}
