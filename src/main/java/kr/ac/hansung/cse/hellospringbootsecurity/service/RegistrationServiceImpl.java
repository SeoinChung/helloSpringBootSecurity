package kr.ac.hansung.cse.hellospringbootsecurity.service;

import kr.ac.hansung.cse.hellospringbootsecurity.entity.MyRole;
import kr.ac.hansung.cse.hellospringbootsecurity.entity.MyUser;
import kr.ac.hansung.cse.hellospringbootsecurity.repository.RoleRepository;
import kr.ac.hansung.cse.hellospringbootsecurity.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional  // 트랜잭션 단위로 처리됨 (실패 시 롤백됨)
public class RegistrationServiceImpl implements RegistrationService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass()); // 로깅용 객체

    @Autowired
    private UserRepository userRepository; // 사용자 DB 접근 객체

    @Autowired
    private RoleRepository roleRepository; // 역할(권한) DB 접근 객체

    @Autowired
    private PasswordEncoder passwordEncoder; // 비밀번호 암호화 도구

    /**
     * 새로운 사용자를 생성하고 DB에 저장하는 메서드
     * - 역할이 DB에 없으면 먼저 저장
     * - 비밀번호는 암호화해서 저장
     * @param user 사용자 정보
     * @param userRoles 부여할 역할 리스트
     */
    public MyUser createUser(MyUser user, List<MyRole> userRoles) {

        // 역할이 DB에 없으면 저장
        for (MyRole ur : userRoles) {
            if (roleRepository.findByRolename(ur.getRolename()).isEmpty()) {
                roleRepository.save(ur);
            }
        }

        // 비밀번호를 BCrypt로 암호화하여 저장
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        // 사용자에 역할 리스트 설정
        user.setRoles(userRoles);

        // 사용자 DB에 저장
        MyUser newUser = userRepository.save(user);

        return newUser;
    }

    /**
     * 이메일 중복 여부 확인하는 메서드
     * @return 이미 존재하면 true, 아니면 false
     */
    public boolean checkEmailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    /**
     * 역할 이름으로 MyRole 객체를 찾는 메서드
     * - 없으면 새로 만듦
     * @return 역할 객체
     */
    public MyRole findByRolename(String rolename) {
        Optional<MyRole> role = roleRepository.findByRolename(rolename);
        return role.orElseGet(() -> new MyRole(rolename));
    }
}