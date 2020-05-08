package com.smbms.controller;

import com.github.pagehelper.PageInfo;
import com.smbms.exception.UserException;
import com.smbms.pojo.User;
import com.smbms.pojo.vo.UserVo;
import com.smbms.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * description:
 * Created by Ray on 2020-04-30
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 根据用户id查询用户详情
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping("/detail/{id}")
    public String getUser(@PathVariable Long id, Model model)throws Exception{
        User user = userService.getUserById(id);
        if(user==null){
            throw new UserException("用户不存在！");
        }
        model.addAttribute("user", user);
        return "userDetail";
    }
    @RequestMapping("/list")
    public String getUsers(Integer pageIndex,String queryUserName,Long queryUserRole, Model model) throws Exception {
        if(pageIndex==null){
            pageIndex=1;
        }
        PageInfo<UserVo> pageInfo = userService.getUserList(pageIndex, 5,queryUserName,queryUserRole);
        model.addAttribute("pageInfo", pageInfo);
        return "userlist";
    }
}
