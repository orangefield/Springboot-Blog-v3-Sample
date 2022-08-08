package site.orangefield.blogsample.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.orangefield.blogsample.domain.user.User;
import site.orangefield.blogsample.domain.user.UserRepository;
import site.orangefield.blogsample.domain.visit.Visit;
import site.orangefield.blogsample.domain.visit.VisitRepository;
import site.orangefield.blogsample.handler.ex.CustomException;
import site.orangefield.blogsample.util.email.EmailUtil;
import site.orangefield.blogsample.web.dto.user.PasswordResetReqDto;

@RequiredArgsConstructor
@Service
public class UserService {

    // DI
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailUtil emailUtil;
    private final VisitRepository visitRepository;

    public boolean 유저네임중복체크(String username) {
        Optional<User> userOp = userRepository.findByUsername(username);

        if (userOp.isPresent()) {
            return false;
        } else {
            return true;
        }
    }

    @Transactional
    public void 회원가입(User user) {

        // 1. save 한 번
        String rawPassword = user.getPassword(); // 1234
        String encPassword = bCryptPasswordEncoder.encode(rawPassword); // hash 알고리즘
        user.setPassword(encPassword);

        User userEntity = userRepository.save(user);

        // 2. save 두 번
        Visit visit = new Visit();
        visit.setTotalCount(0L);
        visit.setUser(userEntity);
        visitRepository.save(visit);
    }

    @Transactional
    public void 패스워드초기화(PasswordResetReqDto passwordResetReqDto) {
        String randomPassword = "";

        // 1. username, email이 같은 것이 있는지 체크(DB)
        Optional<User> userOp = userRepository.findByUsernameAndEmail(passwordResetReqDto.getUsername(),
                passwordResetReqDto.getEmail());

        // 2. 같은 것이 있다면 DB의 password 초기화 - BCrypt 해시 - update하기(DB)
        if (userOp.isPresent()) {
            User userEntity = userOp.get(); // 영속화

            // 6자의 난수 생성 후 임시 비밀번호로 지정
            Integer randomNum = (int) (Math.random() * (999999 - 100000 + 1) + 100000);
            randomPassword = randomNum.toString();

            String encPassword = bCryptPasswordEncoder.encode(randomPassword);
            userEntity.setPassword(encPassword);

        } else {
            throw new CustomException("해당 이메일이 존재하지 않습니다");
        }

        // 3. 초기화된 비밀번호 이메일로 전송 (220530부터 불가능)
        // emailUtil.sendMail("toAddress", "블로그의 비밀번호가 초기화되었습니다", "초기화된 비밀번호 : " +
        // randomPassword);
    } // 더티체킹 (update)
}
