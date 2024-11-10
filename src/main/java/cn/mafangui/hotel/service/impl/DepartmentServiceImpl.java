package cn.mafangui.hotel.service.impl;

import cn.mafangui.hotel.entity.Department;
import cn.mafangui.hotel.mapper.DepartmentMapper;
import cn.mafangui.hotel.service.DepartmentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @author
 * @version 1.0
 * @ClassName DepartmentServiceImpl
 * @Description TODO
 * @createTime 2023-3-18 23:15
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public Department getById(Integer id) {
        return departmentMapper.selectById(id);
    }

    @Override
    public List<Department> selectAll(String searchData) {
        QueryWrapper<Department> queryWrapper=new QueryWrapper<>();
        if (StringUtils.hasLength(searchData)){
            queryWrapper.like("name",searchData);
        }
        return departmentMapper.selectList(queryWrapper);
    }

    @Override
    public void insert(Department department) {
        department.setCreateTime(new Date());
        department.setUpdateTime(new Date());
        departmentMapper.insert(department);
    }

    @Override
    public void update(Department department) {
        department.setUpdateTime(new Date());
        departmentMapper.updateById(department);
    }

    @Override
    public void delete(Integer id) {
        departmentMapper.deleteById(id);
    }
}
