package com.example.be_kwangwoon.domain.user.service;

import com.example.be_kwangwoon.domain.department.domain.Department;
import com.example.be_kwangwoon.domain.department.repository.DepartmentRepository;
import com.example.be_kwangwoon.domain.user.domain.request.UserProfileCreateRequest;
import com.example.be_kwangwoon.domain.user.domain.request.UserProfileUpdateRequest;
import com.example.be_kwangwoon.domain.user.domain.User;
import com.example.be_kwangwoon.domain.user.domain.request.UserSignUpRequest;
import com.example.be_kwangwoon.domain.user.repository.UserRepository;
import com.example.be_kwangwoon.global.common.exception.CustomException;
import com.example.be_kwangwoon.global.common.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.be_kwangwoon.global.common.exception.ExceptionCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void signUp(UserSignUpRequest userSignUpRequest) {
        Optional<User> email = userRepository.findByEmail(userSignUpRequest.getEmail());
        if (email.isPresent()) {
            throw new CustomException(ExceptionCode.USER_ALREADY_EXISTS);
        }
        User user = User.from(userSignUpRequest, bCryptPasswordEncoder);
        userRepository.save(user);
    }


    @Transactional
    public void createProfile(Long userId, UserProfileCreateRequest userProfileCreateRequest) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        Department department = departmentRepository.findById(userProfileCreateRequest.getDepartmentId()).orElseThrow(() -> new CustomException(ExceptionCode.DEPARTMENT_NOT_FOUND));
        user.createProfile(userProfileCreateRequest, department);
    }

    @Transactional
    public void updateProfile(Long userId, UserProfileUpdateRequest userProfileUpdateRequest) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        Department department = departmentRepository.findById(userProfileUpdateRequest.getDepartmentId()).orElseThrow(() -> new CustomException(ExceptionCode.DEPARTMENT_NOT_FOUND));
        user.updateProfile(userProfileUpdateRequest, department);
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new CustomException(USER_NOT_FOUND));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new CustomException(USER_NOT_FOUND));
    }
}
