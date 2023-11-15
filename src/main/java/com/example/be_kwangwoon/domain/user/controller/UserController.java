package com.example.be_kwangwoon.domain.user.controller;


import com.example.be_kwangwoon.domain.user.controller.response.EmailVerifyResponse;
import com.example.be_kwangwoon.domain.user.controller.response.TokenRefreshResponse;
import com.example.be_kwangwoon.domain.user.controller.response.UserProfileResponse;
import com.example.be_kwangwoon.domain.user.domain.QUser;
import com.example.be_kwangwoon.domain.user.domain.User;
import com.example.be_kwangwoon.domain.user.domain.request.*;
import com.example.be_kwangwoon.domain.user.service.UserService;
import com.example.be_kwangwoon.global.common.exception.CustomException;
import com.example.be_kwangwoon.global.common.jwt.JwtProvider;
import com.univcert.api.UnivCert;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

import static com.example.be_kwangwoon.domain.user.domain.QUser.user;
import static com.example.be_kwangwoon.global.common.exception.ExceptionCode.REFRESH_TOKEN_VALIDATION_FAILED;

@Tag(name = "User", description = "유저 API")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/users")
public class UserController {

    private final JwtProvider jwtProvider;
    private final UserService userService;
    @CrossOrigin
    @Operation(summary = "로그인")
    @ApiResponse(responseCode = "200", description = "로그인")
    @PostMapping("/login")
    public ResponseEntity<Void> signUp(@RequestBody UserLoginRequest userLoginRequest) {
        User user = userService.login(userLoginRequest);
        HttpHeaders headers = new HttpHeaders();
        String accessToken = jwtProvider.createAccessToken(user);
        headers.add(HttpHeaders.AUTHORIZATION, accessToken);
        return ResponseEntity.ok().headers(headers).build();
    }

    @CrossOrigin
    @Operation(summary = "회원가입")
    @ApiResponse(responseCode = "204", description = "회원가입 성공")
    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@RequestBody UserSignUpRequest userSignUpRequest) {
        userService.signUp(userSignUpRequest);



        return ResponseEntity.noContent().build();
    }

    @CrossOrigin
    @Operation(summary = "프로필 조회")
    @ApiResponse(responseCode = "200", description = "프로필 조회 성공", content = @Content(schema = @Schema(implementation = UserProfileResponse.class)))
    @GetMapping("/profile/{userId}")
    public ResponseEntity<UserProfileResponse> profile(@PathVariable Long userId) {
        return ResponseEntity.ok(UserProfileResponse.from(userService.findById(userId)));
    }

    @CrossOrigin
    @Operation(summary = "프로필 작성")
    @ApiResponse(responseCode = "204", description = "프로필 작성 성공")
    @PostMapping("/profile")
    public ResponseEntity<Void> createProfile(@AuthenticationPrincipal User user, @RequestBody UserProfileCreateRequest userProfileCreateRequest) {
        userService.createProfile(user.getId(), userProfileCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @CrossOrigin
    @Operation(summary = "프로필 수정")
    @ApiResponse(responseCode = "204", description = "프로필 수정 성공")
    @PutMapping("/profile")
    public ResponseEntity<Void> updateProfile(@AuthenticationPrincipal User user, @RequestBody UserProfileUpdateRequest userProfileUpdateRequest) {
        userService.updateProfile(user.getId(), userProfileUpdateRequest);
        return ResponseEntity.noContent().build();
    }

    @CrossOrigin
    @Operation(summary = "엑세스 토큰/리프레시 토큰 재발급")
    @ApiResponse(responseCode = "200", description = "엑세스 토큰/리프레시 토큰 재발급 성공", content = @Content(schema = @Schema(implementation = TokenRefreshResponse.class)))
    @PostMapping("/refresh")
    public ResponseEntity<TokenRefreshResponse> getRefreshToken(String refreshToken) {
        log.info("토큰 refresh 요청 = {}", refreshToken);
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            User user = jwtProvider.refreshTokenVerify(refreshToken);
            String accessToken = jwtProvider.createAccessToken(user);
            refreshToken = jwtProvider.createRefreshToken(user);
            return ResponseEntity.ok().body(TokenRefreshResponse.from(accessToken, refreshToken));
        }
        throw new CustomException(REFRESH_TOKEN_VALIDATION_FAILED);
    }

    @CrossOrigin
    @Operation(summary = "테스트용 엑세스토큰 반환 userId = 1")
    @GetMapping("/test/token/1")
    public ResponseEntity<String> testAccessToken1() {
        String testAccessToken = jwtProvider.createTestAccessToken(1L);
        return ResponseEntity.ok(testAccessToken);
    }

    @CrossOrigin
    @Operation(summary = "이메일 인증 요청")
    @ApiResponse(responseCode = "200", description = "이메일 인증 요청 성공", content = @Content(schema = @Schema(implementation = EmailVerifyResponse.class)))
    @PostMapping("/email/cert")
    public ResponseEntity<EmailVerifyResponse> emailRequest(@RequestBody EmailCheckRequest emailCheckRequest) throws IOException {
        Map<String, Object> certify = UnivCert.certify("93944d88-416c-4843-a93a-c824b37f2f01", emailCheckRequest.getEmail(), emailCheckRequest.getUnivName(), true);
        Boolean success = (Boolean) certify.get("success");
        if(success){
            return ResponseEntity.ok(EmailVerifyResponse.from(true));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(EmailVerifyResponse.from(false));
    }


    @CrossOrigin
    @Operation(summary = "이메일 인증 확인")
    @ApiResponse(responseCode = "200", description = "이메일 인증 확인 성공", content = @Content(schema = @Schema(implementation = EmailVerifyResponse.class)))
    @PostMapping("/email/verify")
    public ResponseEntity<EmailVerifyResponse> emailVerify(@RequestBody EmailVerifyRequest emailVerifyRequest) throws IOException {
        Map<String, Object> certifyCode = UnivCert.certifyCode("93944d88-416c-4843-a93a-c824b37f2f01", emailVerifyRequest.getEmail(), emailVerifyRequest.getUnivName(), emailVerifyRequest.getCode());
        Boolean success = (Boolean) certifyCode.get("success");
        if(success){
            return ResponseEntity.ok(EmailVerifyResponse.from(true));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(EmailVerifyResponse.from(false));
    }

}