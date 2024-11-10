package cn.mafangui.hotel.controller.worker;

import cn.mafangui.hotel.entity.Worker;
import cn.mafangui.hotel.enums.Role;
import cn.mafangui.hotel.response.AjaxResult;
import cn.mafangui.hotel.response.ResponseTool;
import cn.mafangui.hotel.service.WorkerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("员工信息控制器")
@RestController
@RequestMapping(value = "/admin/operator")
public class OperatorController {
    @Autowired
    private WorkerService workerService;

    @ApiOperation("删除")
    @RequestMapping(method = RequestMethod.POST,value = "/delete/{workerId}")
    public AjaxResult deleteOperator(@PathVariable Integer workerId){
        int re = workerService.delete(workerId);
        if(re!=1) ResponseTool.failed();
        return ResponseTool.success("删除成功");
    }

    @ApiOperation("查询")
    @RequestMapping(value = "")
    public AjaxResult getAllOperator(@RequestParam(value = "searchData",required = false) String searchData,
                                     @RequestParam(value = "role",required = true) String role){
        if (!StringUtils.hasLength(Role.getRoleByName(role))){
            return ResponseTool.failed("角色传值不对");
        }
        return ResponseTool.success(workerService.selectByRole(role,searchData));
    }

    @ApiOperation("通过id查询")
    @RequestMapping(method = RequestMethod.POST,value = "/{workerId}")
    public AjaxResult getOperator(@PathVariable Integer workerId){
        return ResponseTool.success(workerService.selectById(workerId));
    }

    @ApiOperation("新增")
    @RequestMapping(method = RequestMethod.POST,value = "/add")
    public AjaxResult addOperator(String username,String password,String name,String gender,String phone,
                                  String email,String address,String role,Integer roleId,Integer departmentId){
        if (!StringUtils.hasLength(Role.getRoleByName(role))){
            return ResponseTool.failed("角色传值不对");
        }
        Worker worker = new Worker(username,password,name,gender,phone,email,address);
        worker.setRole(role);
        worker.setRoleId(roleId);
        worker.setDepartmentId(departmentId);
        int re = workerService.insert(worker);
        if(re!=1) return ResponseTool.failed();
        return ResponseTool.success("添加成功");
    }

    @ApiOperation("修改")
    @RequestMapping(method = RequestMethod.POST,value = "/update")
    public AjaxResult updateOperator(int workerId,String name,String gender,String phone,String email,
                                     String address,Integer roleId,Integer departmentId){
        Worker worker = new Worker();
        worker.setWorkerId(workerId);
        worker.setName(name);
        worker.setGender(gender);
        worker.setPhone(phone);
        worker.setEmail(email);
        worker.setAddress(address);
        worker.setRoleId(roleId);
        worker.setDepartmentId(departmentId);
        int re =  workerService.updateById(worker);
        if(re!=1) return ResponseTool.failed();
        return ResponseTool.success("更新成功");
    }

}
