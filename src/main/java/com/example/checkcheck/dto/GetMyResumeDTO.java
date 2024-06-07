package com.example.checkcheck.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class GetMyResumeDTO {
    List<ResumeDTO> resumeDTOList;
}
