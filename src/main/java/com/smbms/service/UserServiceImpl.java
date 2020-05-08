package com.smbms.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smbms.dao.RoleMapper;
import com.smbms.dao.UserMapper;
import com.smbms.exception.UserException;
import com.smbms.pojo.Role;
import com.smbms.pojo.User;
import com.smbms.pojo.UserExample;
import com.smbms.pojo.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * description:
 * Created by Ray on 2020-04-30
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
//    @Qualifier("roleMapper")
    private RoleMapper roleMapper;

    @Override
    @Transactional(readOnly = true)//事务管理
    public User getUserById(Long id) throws Exception {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public User login(String userCode, String password) throws Exception {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        //查询条件
        criteria.andUserCodeEqualTo(userCode);
        criteria.andUserPasswordEqualTo(password);
        List<User> list = userMapper.selectByExample(example);
        if(list.size()<1){
//            throw new UserException("用户code或密码错误！");
            return null;
        }
        return list.get(0);
    }

    @Override
    public PageInfo<UserVo> getUserList(int pageNum,int pageSize,String queryUserName,Long queryUserRole) throws Exception {
        PageHelper.startPage(pageNum, pageSize);
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        if(queryUserName!=null) {
            criteria.andUserNameLike("%" + queryUserName + "%");
        }
        if(queryUserRole!=null&&queryUserRole>0) {
            criteria.andUserRoleEqualTo(queryUserRole);
        }
        //分页查询用户User列表
        List<User> list = userMapper.selectByExample(example);
        //封装UserVo列表用于返回前端
        List<UserVo> userVoList = new ArrayList<>();
        UserVo userVo;
        for (User user : list) {
            userVo = new UserVo();
            //把user对象的属性copy到userVo中，相同的属性直接copy，不同属性不copy
            BeanUtils.copyProperties(user, userVo);
            //封装其他属性
            userVo.setAge(new Date().getYear()-user.getBirthday().getYear());
            //根据角色id查询角色信息
            Role role = roleMapper.selectByPrimaryKey(user.getUserRole());
            userVo.setRole(role);
            userVo.setUserRoleName(role.getRoleName());
            //把每个userVo添加到新的list
            userVoList.add(userVo);
        }
        PageInfo<UserVo> userPageInfo = new PageInfo<>(userVoList);
        return userPageInfo;
    }
}
