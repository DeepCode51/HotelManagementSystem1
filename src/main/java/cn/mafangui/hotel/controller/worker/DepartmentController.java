package cn.mafangui.hotel.controller.worker;

import cn.mafangui.hotel.entity.Department;
import cn.mafangui.hotel.response.AjaxResult;
import cn.mafangui.hotel.response.ResponseTool;
import cn.mafangui.hotel.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author
 * @version 1.0
 * @ClassName DepartmentController
 * @Description TODO
 * @createTime 2023-3-19 10:20
 */
@RestController
@RequestMapping(value = "/op/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/getById")
    public AjaxResult getById(Integer id){
        return ResponseTool.success(departmentService.getById(id));
    }

    @RequestMapping("/selectAll")
    public AjaxResult selectAll(@RequestParam(value = "searchData",required = false) String searchData){
        return ResponseTool.success(departmentService.selectAll(searchData));
    }

    @PostMapping("/insert")
    public AjaxResult insert(String name,String remark){
        Department department=new Department();
        department.setName(name);
        department.setRemark(remark);
        departmentService.insert(department);
        return ResponseTool.success("新增成功");
    }

    @PostMapping("/update")
    public AjaxResult update(Integer id,String name,String remark){
        Department department=new Department();
        department.setName(name);
        department.setRemark(remark);
        department.setId(id);
        departmentService.update(department);
        return  ResponseTool.success("更新成功");
    }

    @PostMapping("/delete")
    public AjaxResult delete(Integer id){
        departmentService.delete(id);
        return ResponseTool.success("删除成功");
    }
}
