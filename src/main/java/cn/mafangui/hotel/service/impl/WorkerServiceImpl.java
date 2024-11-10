package cn.mafangui.hotel.service.impl;

import cn.mafangui.hotel.entity.Department;
import cn.mafangui.hotel.entity.WorkRole;
import cn.mafangui.hotel.entity.Worker;
import cn.mafangui.hotel.mapper.DepartmentMapper;
import cn.mafangui.hotel.mapper.WorkRoleMapper;
import cn.mafangui.hotel.mapper.WorkerMapper;
import cn.mafangui.hotel.service.WorkerService;
import cn.mafangui.hotel.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkerServiceImpl implements WorkerService {

    @Autowired
    private WorkerMapper workerMapper;

    @Autowired
    private WorkRoleMapper workRoleMapper;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public int insert(Worker worker) {
        worker.setPassword(MD5Utils.MD5Encode(worker.getPassword()));
        return workerMapper.insertSelective(worker);
    }

    @Override
    public int delete(int workerId) {
        return workerMapper.deleteByPrimaryKey(workerId);
    }

    @Override
    public int updateById(Worker worker) {
        return workerMapper.updateByPrimaryKeySelective(worker);
    }

    @Override
    public Worker selectById(int workerId) {
        Worker worker = workerMapper.selectByPrimaryKey(workerId);
        return buildData(worker);
    }

    @Override
    public Worker selectByUsername(String username) {
        Worker worker = workerMapper.selectByUsername(username);
        return buildData(worker);
    }

    @Override
    public List<Worker> findAll() {
        List<Worker> workers = workerMapper.selectAll();
        return buildData(workers);
    }

    @Override
    public List<Worker> selectByRole(String role,String searchData) {
        List<Worker> workers = workerMapper.selectByRole(role, searchData);
        return buildData(workers);
    }

    @Override
    public Worker login(String username, String password,String role) {
        String pass = MD5Utils.MD5Encode(password);
        Worker worker = workerMapper.selectByUsernameAndPassword(username, pass, role);
        return buildData(worker);
    }

    @Override
    public Worker login(String username, String password) {
        String pass = MD5Utils.MD5Encode(password);
        Worker worker = workerMapper.selectByUsernamePassword(username, pass);
        return buildData(worker);
    }

    private Worker buildData(Worker worker){
        WorkRole workRole = workRoleMapper.selectById(worker.getRoleId());
        worker.setWorkRole(workRole);
        Department department = departmentMapper.selectById(worker.getDepartmentId());
        worker.setDepartment(department);
        return worker;
    }

    private List<Worker> buildData(List<Worker> workers){
        for (Worker worker:workers){
            buildData(worker);
        }
        return workers;
    }
}
