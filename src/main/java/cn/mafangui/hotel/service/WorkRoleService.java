package cn.mafangui.hotel.service;

import cn.mafangui.hotel.entity.WorkRole;

import java.util.List;

/**
 * @author
 * @version 1.0.0
 * @ClassName WorkRoleService
 * @Description TODO
 * @Date 2023-3-18 23:14
 */
public interface WorkRoleService {

    WorkRole getById(Integer id);

    List<WorkRole> selectAll(String searchData);

    void insert(WorkRole workRole);

    void update(WorkRole workRole);

    void delete(Integer id);

}
