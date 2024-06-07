package com.example.checkcheck.dto.req;


import com.example.checkcheck.dto.ResumeContentDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SaveResumeReqDTO {
    List<ResumeContentDTO> resumeContentDTOS;
}
