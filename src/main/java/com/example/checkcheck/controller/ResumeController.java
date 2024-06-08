package com.example.checkcheck.controller;

import com.example.checkcheck.dto.*;
import com.example.checkcheck.dto.req.AddResumeContentReqDTO;
import com.example.checkcheck.dto.req.CreateResumeReqDTO;
import com.example.checkcheck.dto.req.SaveResumeContentReqDTO;
import com.example.checkcheck.dto.req.SaveResumeReqDTO;
import com.example.checkcheck.dto.res.*;
import com.example.checkcheck.service.ResumeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "이력서")
@RestController
@RequestMapping("/resume")
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;


    //회사 목록 가져오기
    @Operation(summary = "회사 목록 가져오기")
    @GetMapping("/companies")
    public ResponseEntity<GetAllCompanyResDTO> getAllCompanies(){
        return ResponseEntity.ok(resumeService.getAllCompanies());
    }

    //직무 목록 가져오기
    @Operation(summary = "직무 목록 가져오기")
    @GetMapping("/jobs/{company-name}")
    public ResponseEntity<GetJobsResDTO> getJobs(@PathVariable(value = "company-name")String companyName){
        return ResponseEntity.ok(resumeService.getJobs(companyName));
    }

    //회사+직무의 질문 목록 가져오기
    @Operation(summary = "회사+직무의 질문목록 가져오기")
    @GetMapping("/companies/{company-name}/jobs/{job-name}")
    public ResponseEntity<GetQuestionResDTO>getQuestions(@PathVariable(value = "company-name")String companyName
            , @PathVariable(value = "job-name")String jobName){
        return ResponseEntity.ok(resumeService.getQuestions(companyName, jobName));
    }
/*
    //전체 질문리스트 가져오기
    @GetMapping("/questions")
    public ResponseEntity<GetAllQuestionResDTO>getAllQuestions(){
        GetAllQuestionResDTO getAllQuestionResDTO = resumeService.getAllQuestions();
        return ResponseEntity.ok(getAllQuestionResDTO);
    }
*/
    //저장된 나의 자기소개서 전부 불러오기 (문항, 메모도 불러와야한다)
    @Operation(summary = "저장된 나의 모든 자기소개서 전부 불러오기")
    @GetMapping
    public ResponseEntity<GetMyResumeDTO> getMyResume(){
        GetMyResumeDTO getMyResumeDTO = resumeService.getMyResume();
        return ResponseEntity.ok(getMyResumeDTO);
    }

    //자기소개서 하나 불러오기
    @Operation(summary = "자기소개서 자세히보기")
    @GetMapping("/{resume-id}")
    public ResponseEntity<GetResumeResDTO> getResume(@PathVariable(value = "resume-id")Long resumeId){
        GetResumeResDTO getResumeResDTO = resumeService.getResume(resumeId);
        return ResponseEntity.ok(getResumeResDTO);
    }

    //새 자기소개서 생성
    @Operation(summary = "새 자기소개서 추가")
    @PostMapping("/new-resume")
    public ResponseEntity<CreateResumeResDTO>createResume(@RequestBody CreateResumeReqDTO createResumeReqDTO){
        CreateResumeResDTO createResumeResDTO = resumeService.createResume(createResumeReqDTO);
        return ResponseEntity.ok(createResumeResDTO);
    }


    //자기소개서 저장 (이미 존재하는 자기소개서면 컨텐츠만 저장, 아니면 자기소개서 생성) -> 어떻게?
    @Operation(summary = "자기소개서 저장")
    @PostMapping
    public ResponseEntity<String> saveResume(@RequestBody SaveResumeReqDTO saveResumeReqDTO){
        resumeService.saveResume(saveResumeReqDTO);
        return ResponseEntity.ok("save resume");
    }

    //자기소개서 삭제
    @Operation(summary = "자기소개서 삭제")
    @DeleteMapping("/{resume-id}")
    public ResponseEntity<String> deleteResume(@PathVariable(value="resume-id")Long resumeId){
        resumeService.deleteResume(resumeId);
        return ResponseEntity.ok("delete resume");
    }

    //새 문항 추가
    @Operation(summary = "새 문항 추가")
    @PostMapping("/resume-content/new")
    public ResponseEntity<AddResumeContentResDTO> addResumeContent(@RequestBody AddResumeContentReqDTO addQuestionReqDTO){
        AddResumeContentResDTO addQuestionResDTO = resumeService.addResumeContent(addQuestionReqDTO);
        return ResponseEntity.ok(addQuestionResDTO);
    }

    //문항 삭제
    @Operation(summary = "문항 삭제")
    @DeleteMapping("/resume-content/{resume-content-id}")
    public ResponseEntity<String>deleteResumeContent(@PathVariable(value="resume-content-id")Long resumeContentId){
        resumeService.deleteResumeContent(resumeContentId);
        return ResponseEntity.ok("delete resume content");
    }

    //답변 저장
    @Operation(summary = "답변 저장")
    @PostMapping("/resume-content/save")
    public ResponseEntity<String> saveResumeContent(@RequestBody SaveResumeContentReqDTO saveResumeContentReqDTO){
        resumeService.saveResumeContent(saveResumeContentReqDTO);
        return ResponseEntity.ok("save resume content");
    }

    //통계 가져오기
    @Operation(summary = "통계 가져오기")
    @GetMapping("/statics/{resume-id}")
    public ResponseEntity<StaticsResDTO> getStatics(@PathVariable(value="resume-id")Long resumeId){
        StaticsResDTO staticsResDTO = resumeService.getStatics(resumeId);
        return ResponseEntity.ok(staticsResDTO);
    }

}
