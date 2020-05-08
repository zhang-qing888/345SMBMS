package com.smbms.controller;

import com.smbms.pojo.User;
import com.smbms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * description:
 * Created by Ray on 2020-05-04
 */
@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    /**
     * 登录功能
     * @param userCode
     * @param userPassword
     * @param session
     * @return
     * @throws Exception
     */
    @PostMapping("/login")
    public String doLogin(String userCode, String userPassword,Model model, HttpSession session)throws Exception{
        User user = userService.login(userCode, userPassword);
        if(user==null){//登录失败
            model.addAttribute("error", "用户名或密码错误！");
            return "forward:login.jsp";
        }
        session.setAttribute("loginUser",user);
//        model.addAttribute("loginUser", user);//请求域
        return "frame";
    }

    /**
     * 注销登录
     * @param session
     * @return
     * @throws Exception
     */
    @GetMapping("/logout.do")
    public String doLogout( HttpSession session)throws Exception{
        session.removeAttribute("lognUser");
        return "redirect:/login.jsp";
    }
}
