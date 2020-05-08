package com.smbms.service;

import com.github.pagehelper.PageInfo;
import com.smbms.pojo.User;
import com.smbms.pojo.vo.UserVo;

import java.util.List;

/**
 * description:
 * Created by Ray on 2020-04-30
 */
public interface UserService {
    /**
     * 根据id查询用户详情
     * @param id
     * @return
     */
    User getUserById(Long id)throws Exception;

    /**
     * 登录业务
     * @param userCode
     * @param password
     * @return
     * @throws Exception
     */
    User login(String userCode,String password)throws Exception;
    //获取用户列表
    PageInfo<UserVo> getUserList(int pageNum, int pageSize, String queryUserName, Long queryUserRole) throws Exception;
}
