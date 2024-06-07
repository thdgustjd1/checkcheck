package com.example.checkcheck.dto.res;

import com.example.checkcheck.dto.QuestionInfoDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class GetAllQuestionResDTO {
    List<QuestionInfoDTO> questionList;
}
