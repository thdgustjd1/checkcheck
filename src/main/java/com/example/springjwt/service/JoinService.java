package com.example.springjwt.service;

import com.example.springjwt.dto.JoinDTO;
import com.example.springjwt.entity.RefreshToken;
import com.example.springjwt.entity.UserEntity;
import com.example.springjwt.jwt.JWTUtil;
import com.example.springjwt.repository.RefreshTokenRepository;
import com.example.springjwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JoinService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JWTUtil jwtUtil;
    public void joinProcess(JoinDTO joinDTO){
        String username = joinDTO.getUsername();
        String userPassword = joinDTO.getPassword();

        Boolean isExist = userRepository.existsByUsername(username);
        if(isExist){
            return;
        }

        UserEntity data = UserEntity.builder()
                .username(username)
                .password(bCryptPasswordEncoder.encode(userPassword))
                .role("ROLE_ADMIN")
                .build();

        userRepository.save(data);
    }

    public HttpHeaders genNewAccessToken(String token) {
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findById(token);
        if(refreshToken.isPresent()){
            HttpHeaders headers = new HttpHeaders();
            UserEntity userEntity = userRepository.findByUsername(refreshToken.get().getUsername()); //exception 필요
            String newAccessToken = jwtUtil.createJwt(userEntity.getUsername(), userEntity.getRole(), 60*60*100L);
            headers.add("Authorization","Bearer "+newAccessToken);
            return headers;
        }
        else{
            throw new IllegalStateException(); //custom Exception 필요
        }
    }
}
