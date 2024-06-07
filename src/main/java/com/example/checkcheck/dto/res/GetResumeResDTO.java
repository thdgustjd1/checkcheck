package com.example.checkcheck.dto.res;

import com.example.checkcheck.dto.ResumeContentDTO;
import com.example.checkcheck.dto.ResumeDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class GetResumeResDTO {
    ResumeDTO resumeInfo;
    List<ResumeContentDTO> resumeContentDTOList;
}
