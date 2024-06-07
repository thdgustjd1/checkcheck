package com.example.checkcheck.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddResumeContentReqDTO {
    Long resumeId;
    String type;
    String question;
    String answer;
}
