package com.example.checkcheck.controller;

import com.example.checkcheck.dto.LoginDTO;
import com.example.checkcheck.dto.req.JoinDTO;
import com.example.checkcheck.repository.JobRepository;
import com.example.checkcheck.service.JoinService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Tag(name = "login")
@RestController
@RequiredArgsConstructor
public class JoinController {
    private final JoinService joinService;


    @Operation(summary = "로그인")
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO){
        System.out.println("=======실행===========");
        return ResponseEntity.ok("로그인 완료");
    }

    @Operation(summary = "회원가입")
    @PostMapping("/join")
    public ResponseEntity<String> joinProcess(@RequestBody JoinDTO joinDTO){
        joinService.joinProcess(joinDTO);
        return ResponseEntity.ok("회원가입 완료");
    }


    @Operation(summary = "엑세스토큰 재발급")
    @PostMapping("/accesstoken")
    public ResponseEntity<HttpHeaders> genNewAccessToken(@RequestHeader(value = "Refresh-Token") String token){
        return ResponseEntity.ok(joinService.genNewAccessToken(token));
    }
}
