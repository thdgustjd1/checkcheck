package com.example.checkcheck.dto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class GetAllCompanyResDTO {
    List<String> companyNameList;
}
