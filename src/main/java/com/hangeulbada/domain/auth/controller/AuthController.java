package com.hangeulbada.domain.auth.controller;

import com.hangeulbada.domain.auth.component.UnsecuredAPI;
import com.hangeulbada.domain.auth.dto.LoginResponse;
import com.hangeulbada.domain.auth.dto.SignupResponse;
import com.hangeulbada.domain.auth.service.GoogleService;
import com.hangeulbada.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Google OAuth2", description = "구글 로그인/회원가입 API")
public class AuthController {

    private final GoogleService googleService;
    private final UserService userService;

    @ResponseBody
    @Operation(summary = "구글 로그인/회원가입", description = "구글 로그인 시 response 했던 정보에서 role 선택 후 회원가입")
    @PostMapping("/login/oauth2/code/google/signup")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "201", description = "회원가입 성공")

    })
    @UnsecuredAPI
    public ResponseEntity<LoginResponse> googleSignup(@RequestBody SignupResponse signupResponse) {
        if(googleService.isRegisteredUser(signupResponse.getUid())){
            //로그인
            return new ResponseEntity<>(googleService.googleSignin(signupResponse), HttpStatus.OK);
        }
        //회원가입
        return new ResponseEntity<>(googleService.googleSignup(signupResponse), HttpStatus.CREATED);
    }

    @ResponseBody
    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴")
    @DeleteMapping("/user")
    public void deleteUser(Principal principal){
        userService.deleteUser(principal.getName());
    }

}