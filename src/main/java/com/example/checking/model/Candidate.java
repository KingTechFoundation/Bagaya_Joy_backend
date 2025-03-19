package com.example.checking.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int votes = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "poll_id", nullable = false)
    @JsonBackReference  
    private Poll poll;
}
