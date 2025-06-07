package kr.ac.hansung.cse.hellospringbootsecurity.controller;

import jakarta.validation.Valid;
import kr.ac.hansung.cse.hellospringbootsecurity.entity.MyRole;
import kr.ac.hansung.cse.hellospringbootsecurity.entity.MyUser;
import kr.ac.hansung.cse.hellospringbootsecurity.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(Model model) {

        MyUser user = new MyUser();
        model.addAttribute("user", user);

        return "signup";
    }

    // 회원가입 처리
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signupPost(@Valid @ModelAttribute("user") MyUser user,
                             BindingResult bindingResult,
                             Model model) {
        // 유효성 검사 오류가 있는 경우 다시 회원가입 폼으로
        if (bindingResult.hasErrors()) {
            return "signup";
        }
        // 이메일 중복 확인
        if (registrationService.checkEmailExists(user.getEmail())) {
            model.addAttribute("emailExists", true);
            return "signup";
        }
        // 기본 사용자 권한 부여
        List<MyRole> userRoles = new ArrayList<>();
        MyRole role = registrationService.findByRolename("ROLE_USER");
        userRoles.add(role);
        // 관리자로 등록할 이메일인 경우 ROLE_ADMIN 추가
        if ("admin@hansung.ac.kr".equals(user.getEmail())) {
            MyRole roleAdmin = registrationService.findByRolename("ROLE_ADMIN");
            userRoles.add(roleAdmin);
        }
        // 사용자 생성
        registrationService.createUser(user, userRoles);

        return "redirect:/";
    }
}