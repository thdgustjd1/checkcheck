package com.example.checkcheck.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResumeContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Resume resume;

    @ManyToOne
    @JoinColumn
    private Question question;

    String content;
    String answer;
    String resumeType;
}
