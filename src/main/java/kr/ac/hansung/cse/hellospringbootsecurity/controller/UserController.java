package kr.ac.hansung.cse.hellospringbootsecurity.controller;

import kr.ac.hansung.cse.hellospringbootsecurity.entity.MyUser;
import kr.ac.hansung.cse.hellospringbootsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Secured("ROLE_ADMIN") // 관리자만 접근 가능
    @GetMapping("/users")
    public String listUsers(Model model) {
        List<MyUser> users = userService.getAllUsers();
        model.addAttribute("userList", users);
        return "user_list";
    }
}
