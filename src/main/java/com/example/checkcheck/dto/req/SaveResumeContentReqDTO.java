package com.example.checkcheck.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveResumeContentReqDTO {
    Long resumeContentId;
    String question;
    String answer;
}
