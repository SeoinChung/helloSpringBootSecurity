package kr.ac.hansung.cse.hellospringbootsecurity.service;

import kr.ac.hansung.cse.hellospringbootsecurity.entity.MyUser;
import kr.ac.hansung.cse.hellospringbootsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserService { // 모든 사용자 정보 갖고 오기
    @Autowired
    private UserRepository userRepository;

    public List<MyUser> getAllUsers() {
        return userRepository.findAll();
    }
}
