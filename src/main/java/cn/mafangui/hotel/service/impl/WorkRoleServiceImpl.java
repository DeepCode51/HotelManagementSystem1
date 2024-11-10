package cn.mafangui.hotel.service.impl;

import cn.mafangui.hotel.entity.WorkRole;
import cn.mafangui.hotel.mapper.WorkRoleMapper;
import cn.mafangui.hotel.service.WorkRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @author
 * @version 1.0
 * @ClassName WorkRoleServiceImpl
 * @Description TODO
 * @createTime 2023-3-18 23:14
 */
@Service
public class WorkRoleServiceImpl implements WorkRoleService {

    @Autowired
    private WorkRoleMapper workRoleMapper;

    @Override
    public WorkRole getById(Integer id) {
        return workRoleMapper.selectById(id);
    }

    @Override
    public List<WorkRole> selectAll(String searchData) {
        QueryWrapper<WorkRole> queryWrapper=new QueryWrapper<>();
        if (StringUtils.hasLength(searchData)){
            queryWrapper.like("role_name",searchData);
        }
        return workRoleMapper.selectList(queryWrapper);
    }

    @Override
    public void insert(WorkRole workRole) {
        workRole.setCreateTime(new Date());
        workRole.setUpdateTime(new Date());
        workRoleMapper.insert(workRole);
    }

    @Override
    public void update(WorkRole workRole) {
        workRole.setUpdateTime(new Date());
        workRoleMapper.updateById(workRole);
    }

    @Override
    public void delete(Integer id) {
        workRoleMapper.deleteById(id);
    }
}
