package com.example.checkcheck.controller;

import com.example.checkcheck.dto.JoinDTO;
import com.example.checkcheck.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequiredArgsConstructor
public class JoinController {
    private final JoinService joinService;

    @PostMapping("/join")
    public ResponseEntity<String> joinProcess(JoinDTO joinDTO){
        joinService.joinProcess(joinDTO);
        return ResponseEntity.ok("회원가입 완료");
    }

    @PostMapping("/accesstoken")
    public ResponseEntity<HttpHeaders> genNewAccessToken(@RequestHeader(value = "Refresh-Token") String token){
        return ResponseEntity.ok(joinService.genNewAccessToken(token));
    }
}
