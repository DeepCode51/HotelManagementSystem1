package cn.mafangui.hotel.service;

import cn.mafangui.hotel.entity.Department;

import java.util.List;

/**
 * @author
 * @version 1.0.0
 * @ClassName Department
 * @Description TODO
 * @Date 2023-3-18 23:14
 */
public interface DepartmentService {

    Department getById(Integer id);

    List<Department> selectAll(String searchData);

    void insert(Department department);

    void update(Department department);

    void delete(Integer id);

}
