package com.example.be_kwangwoon.domain.user.controller;


import com.example.be_kwangwoon.domain.user.controller.response.TokenRefreshResponse;
import com.example.be_kwangwoon.domain.user.controller.response.UserProfileResponse;
import com.example.be_kwangwoon.domain.user.domain.User;
import com.example.be_kwangwoon.domain.user.domain.request.UserProfileCreateRequest;
import com.example.be_kwangwoon.domain.user.domain.request.UserProfileUpdateRequest;
import com.example.be_kwangwoon.domain.user.domain.request.UserSignUpRequest;
import com.example.be_kwangwoon.domain.user.service.UserService;
import com.example.be_kwangwoon.global.common.exception.CustomException;
import com.example.be_kwangwoon.global.common.jwt.JwtProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.example.be_kwangwoon.global.common.exception.ExceptionCode.REFRESH_TOKEN_VALIDATION_FAILED;

@Tag(name = "User", description = "유저 API")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/users")
public class UserController {

    private final JwtProvider jwtProvider;
    private final UserService userService;
    @Operation(summary = "회원가입")
    @ApiResponse(responseCode = "204", description = "회원가입 성공")
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody UserSignUpRequest userSignUpRequest) {
        userService.signUp(userSignUpRequest);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "프로필 조회")
    @ApiResponse(responseCode = "200", description = "프로필 조회 성공", content = @Content(schema = @Schema(implementation = UserProfileResponse.class)))
    @GetMapping("/profile/{userId}")
    public ResponseEntity<UserProfileResponse> profile(@PathVariable Long userId) {
        return ResponseEntity.ok(UserProfileResponse.from(userService.findById(userId)));
    }

    @Operation(summary = "프로필 작성")
    @ApiResponse(responseCode = "204", description = "프로필 작성 성공")
    @PostMapping("/profile")
    public ResponseEntity<Void> createProfile(@AuthenticationPrincipal User user, @RequestBody UserProfileCreateRequest userProfileCreateRequest) {
        userService.createProfile(user.getId(), userProfileCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "프로필 수정")
    @ApiResponse(responseCode = "204", description = "프로필 수정 성공")
    @PutMapping("/profile")
    public ResponseEntity<Void> updateProfile(@AuthenticationPrincipal User user, @RequestBody UserProfileUpdateRequest userProfileUpdateRequest) {
        userService.updateProfile(user.getId(), userProfileUpdateRequest);
        return ResponseEntity.noContent().build();
    }

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

    @Operation(summary = "테스트용 엑세스토큰 반환 userId = 1")
    @GetMapping("/test/token/1")
    public ResponseEntity<String> testAccessToken1() {
        String testAccessToken = jwtProvider.createTestAccessToken(1L);
        return ResponseEntity.ok(testAccessToken);
    }
}