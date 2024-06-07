package com.example.checkcheck.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResumeContentDTO {
    Long resumeContentId;
    String question;
    String content;
}
