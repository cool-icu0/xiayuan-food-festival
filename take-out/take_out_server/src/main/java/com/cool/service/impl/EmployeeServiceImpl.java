package com.cool.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.cool.constant.MessageConstant;
import com.cool.constant.PasswordConstant;
import com.cool.constant.StatusConstant;
import com.cool.dto.EmployeeDTO;
import com.cool.dto.EmployeeLoginDTO;
import com.cool.dto.EmployeePageQueryDTO;
import com.cool.entity.Employee;
import com.cool.exception.AccountLockedException;
import com.cool.exception.AccountNotFoundException;
import com.cool.exception.PasswordErrorException;
import com.cool.mapper.EmployeeMapper;
import com.cool.result.PageResult;
import com.cool.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        // 对前端传过来的md5密码进行md5加密
         password = DigestUtils.md5DigestAsHex(password.getBytes());
        // 输出md5的加密数值
         System.out.println(password);
        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }

    /**
     * 新增员工
     *
     * @param employeeDTO
     */
    public void save(EmployeeDTO employeeDTO) {
        // System.out.println("当前线程的id"+Thread.currentThread().getId());
        // 验证当前线程是不是和EmploController以及JwtTokenAdminInterceptor属于同一个线程且每次请求为单独线程
        Employee employee = new Employee();

        //对象属性拷贝
        BeanUtils.copyProperties(employeeDTO, employee);

        //设置账号的状态，默认正常状态 1表示正常 0表示锁定
        employee.setStatus(StatusConstant.ENABLE);

        //设置密码，默认密码123456
        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));

        //这里使用aop来赋值了，现在就不用进行赋值了
            //设置当前记录的创建时间和修改时间
            // employee.setCreateTime(LocalDateTime.now());
            // employee.setUpdateTime(LocalDateTime.now());

            //设置当前记录创建人id和修改人id
            // employee.setCreateUser(BaseContext.getCurrentId());//目前写个假数据，后期修改
            // employee.setUpdateUser(BaseContext.getCurrentId());


        employeeMapper.insert(employee);//后续步骤定义
    }

    /**
     * 分页查询
     * @param employeePageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO) {
        //select * from employee limit 0,10
        //开始分页查询
        PageHelper.startPage(employeePageQueryDTO.getPage(),employeePageQueryDTO.getPageSize());
        Page<Employee> page =  employeeMapper.pageQuery(employeePageQueryDTO);
        long total = page.getTotal();
        List<Employee> records = page.getResult();

        return new PageResult(total,records);
    }

    @Override
    public void startOrStop(Integer status, Long id) {
        // 构建器
        Employee employee = Employee.builder()
                .status(status)
                .id(id)
                .build();
        employeeMapper.update(employee);
    }

    /**
     * 根据id查询员工
     * @param id
     * @return
     */
    @Override
    public Employee getById(Long id) {
        Employee employee = employeeMapper.getById(id);
        //因为查出来的密码会被前端知道，所以这里设置密码为****
        employee.setPassword("****");
        return employee;
    }

    /**
     * 编辑员工信息
     * @param employeeDTO
     * @return
     */
    @Override
    public void update(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);

        //这里使用aop来赋值了，现在就不用进行赋值了
            // employee.setUpdateTime(LocalDateTime.now());
            // employee.setUpdateUser(BaseContext.getCurrentId());

        employeeMapper.update(employee);
    }

}
