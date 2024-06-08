package com.example.checkcheck.service;

import com.example.checkcheck.dto.*;
import com.example.checkcheck.dto.req.AddResumeContentReqDTO;
import com.example.checkcheck.dto.req.CreateResumeReqDTO;
import com.example.checkcheck.dto.req.SaveResumeContentReqDTO;
import com.example.checkcheck.dto.req.SaveResumeReqDTO;
import com.example.checkcheck.dto.res.*;
import com.example.checkcheck.entity.*;
import com.example.checkcheck.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResumeService {
    private final CompanyRepository companyRepository;
    private final CompanyJobRepository companyJobRepository;
    private final JobRepository jobRepository;
    private final QuestionTypeRepository questionTypeRepository;
    private final ResumeContentRepository resumeContentRepository;
    private final ResumeRepository resumeRepository;
    private final StaticsRepository staticsRepository;
    private final UserRepository userRepository;


    //회사 목록 가져오기
    public GetAllCompanyResDTO getAllCompanies() {
        return GetAllCompanyResDTO.builder().companyNameList(companyRepository.findAll().stream()
                .map(Company::getCompanyName)
                .collect(Collectors.toList())).build();
    }

    //회사 내 직무 목록 가져오기
    public GetJobsResDTO getJobs(String companyName) {
        Optional<Company> company = companyRepository.findByCompanyName(companyName);
        List<Job> jobs = companyJobRepository.findByCompany(company.get()).stream()
                .map(CompanyJob::getJob)
                .collect(Collectors.toList());
        return GetJobsResDTO.builder().jobNameList(jobs.stream().map(Job::getJobName)
                .collect(Collectors.toList())).build();
    }

    //회사+직무의 질문 목록 가져오기
    public GetQuestionResDTO getQuestions(String companyName, String jobName) {
        Optional<Company> company = companyRepository.findByCompanyName(companyName);
        Optional<Job> job = jobRepository.findByJobName(jobName);
        Optional<CompanyJob> companyJob = companyJobRepository.findCompanyJobByCompanyAndJob(company.get(),job.get());
        List<QuestionType> questions = questionTypeRepository.findQuestionTypesByCompanyJob(companyJob.get());


        return GetQuestionResDTO.builder().questionList(questions.stream()
                .map(question -> new QuestionInfoDTO(question.getId(), question.getType()))
                .collect(Collectors.toList())).build();
    }

    /*
    //전체 질문리스트 가져오기 (겹치는 질문이면 어떻게해야하는지? question id 어떻게 반환?) -> out
    public GetAllQuestionResDTO getAllQuestions() {
        List<QuestionType>questions = questionTypeRepository.findAll();
        return GetAllQuestionResDTO.builder().questionList(questions.stream()
                .map(question -> new QuestionInfoDTO(question.getId(), question.getType()))
                .collect(Collectors.toList())).build();
    }
*/
    //나의 이력서 리스트 반환 (회사명, 직무명, resumeid 반환필요)
    public GetMyResumeDTO getMyResume() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByUsername(username);
        List<Resume> myResumes = resumeRepository.findResumesByUserEntity(user);
        List<ResumeDTO> dtos = myResumes.stream()
                .map(dto -> ResumeDTO.builder().resumeId(dto.getId())
                                .companyName(dto.getCompanyJob().getCompany().getCompanyName())
                                .jobName(dto.getCompanyJob().getJob().getJobName()).build()).collect(Collectors.toList());

        return GetMyResumeDTO.builder().resumeDTOList(dtos).build();
    }

    //자기소개서 하나 불러오기 (회사명, 직무명, resumeId, list<resumecontentid, resumecontent> 필요
    public GetResumeResDTO getResume(Long resumeId) {
        Optional<Resume> resume = resumeRepository.findById(resumeId);
        List<ResumeContentDTO> resumeContentDTOS = resumeContentRepository.findResumeContentsByResume(resume.get())
                .stream().map(resumeContent -> ResumeContentDTO.builder()
                        .question(resumeContent.getContent())
                        .resumeContentId(resumeContent.getId())
                        .answer(resumeContent.getAnswer()).build()).collect(Collectors.toList());

        GetResumeResDTO getResumeResDTO = GetResumeResDTO.builder()
                .resumeInfo(
                        ResumeDTO.builder()
                                .resumeId(resumeId)
                                .companyName(resume.get().getCompanyJob().getCompany().getCompanyName())
                                .jobName(resume.get().getCompanyJob().getJob().getJobName()).build()
                                )
                .resumeContentDTOList(resumeContentDTOS).build();

        return getResumeResDTO;
    }

    //새 자기소개서 생성

    public CreateResumeResDTO createResume(CreateResumeReqDTO createResumeReqDTO){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByUsername(username);
        Optional<Company> company = companyRepository.findByCompanyName(createResumeReqDTO.getCompanyName());
        Optional<Job> job = jobRepository.findByJobName(createResumeReqDTO.getJobName());
        Optional<CompanyJob> companyJob = companyJobRepository.findCompanyJobByCompanyAndJob(company.get(),job.get());

        Resume resume = resumeRepository.save(Resume.builder().userEntity(user).companyJob(companyJob.get()).build());

        return CreateResumeResDTO.builder().resumeId(resume.getId()).build();
    }

    //자기소개서 수정내용 전체 저장 (resumeContent 질문,답변,문제유형 전부 저장)
    public void saveResume(SaveResumeReqDTO saveResumeReqDTO) {
        List<ResumeContentDTO> resumeContentDTO = saveResumeReqDTO.getResumeContentDTOS();
        for (ResumeContentDTO r :resumeContentDTO) {
            Optional<ResumeContent> resumeContent = resumeContentRepository.findById(r.getResumeContentId());
            resumeContent.get().setContent(r.getQuestion());
            resumeContent.get().setAnswer(r.getAnswer());
            resumeContentRepository.save(resumeContent.get());
        }
    }

    //자기소개서 삭제
    public void deleteResume(Long resumeId) {
        Optional<Resume> resume = resumeRepository.findById(resumeId);
        List<ResumeContent> resumeContents = resumeContentRepository.findResumeContentsByResume(resume.get());
        for(ResumeContent r : resumeContents){
            resumeContentRepository.delete(r);
        }
        resumeRepository.delete(resume.get());
    }

    //새 문항 추가
    public AddResumeContentResDTO addResumeContent(AddResumeContentReqDTO addResumeContentReqDTO) {
        Optional<Resume> resume = resumeRepository.findById(addResumeContentReqDTO.getResumeId());
        Optional<QuestionType> questionType
                = questionTypeRepository.findQuestionTypeByCompanyJobAndType(resume.get().getCompanyJob(), addResumeContentReqDTO.getType());
        ResumeContent resumeContent = ResumeContent.builder()
                .resume(resume.get())
                .questionType(questionType.get())
                .content(addResumeContentReqDTO.getQuestion())
                .answer(addResumeContentReqDTO.getAnswer()).build();
        ResumeContent persistResumeContent = resumeContentRepository.save(resumeContent);

        return AddResumeContentResDTO.builder().resumeContentId(persistResumeContent.getId()).build();
    }

    //문항 삭제
    public void deleteResumeContent(Long resumeContentId) {
        Optional<ResumeContent>resumeContent = resumeContentRepository.findById(resumeContentId);
        resumeContentRepository.delete(resumeContent.get());
    }

    //답변 저장
    public void saveResumeContent(SaveResumeContentReqDTO saveResumeContentReqDTO) {
        Optional<ResumeContent>resumeContent = resumeContentRepository.findById(saveResumeContentReqDTO.getResumeContentId());
        resumeContent.get().setContent(saveResumeContentReqDTO.getQuestion());
        resumeContent.get().setAnswer(saveResumeContentReqDTO.getAnswer());
        resumeContentRepository.save(resumeContent.get());
    }

    //통계 가져오기
    public StaticsResDTO getStatics(Long resumeId) {
        List<ResumeContent>resumeContents = resumeContentRepository.findResumeContentsByResumeId(resumeId);
        List<StaticsDTO> staticsDTOList = new ArrayList<>();
        for(ResumeContent r : resumeContents){
            List<StaticsInfo> staticsInfoList = staticsRepository.findStaticsByQuestionType(r.getQuestionType()).stream()
                    .map(statics -> StaticsInfo.builder()
                            .subject(statics.getSubject())
                            .value(statics.getValue())
                            .build()).collect(Collectors.toList());

            StaticsDTO staticsDTO = StaticsDTO.builder().userAnswer(r.getAnswer()).staticsInfoList(staticsInfoList).build();
            staticsDTOList.add(staticsDTO);
        }
        return StaticsResDTO.builder().staticsDTOList(staticsDTOList).build();
    }

}
