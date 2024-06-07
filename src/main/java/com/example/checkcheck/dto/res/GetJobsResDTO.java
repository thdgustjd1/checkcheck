package com.example.checkcheck.dto.res;

import com.example.checkcheck.entity.Job;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class GetJobsResDTO {
    List<String> jobNameList;
}
