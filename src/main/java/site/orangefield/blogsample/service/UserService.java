package site.orangefield.blogsample.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.orangefield.blogsample.domain.user.User;
import site.orangefield.blogsample.domain.user.UserRepository;

@RequiredArgsConstructor
@Service
public class UserService {

    // DI
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void 회원가입(User user) {

        String rawPassword = user.getPassword(); // 1234
        String encPassword = bCryptPasswordEncoder.encode(rawPassword); // hash 알고리즘
        user.setPassword(encPassword);

        userRepository.save(user);
    }
}
