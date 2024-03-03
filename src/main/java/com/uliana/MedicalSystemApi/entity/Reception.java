package com.uliana.MedicalSystemApi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@Accessors(chain = true)
public class Reception {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    private String medicines;

    @ManyToOne(fetch = FetchType.LAZY,cascade = {
           CascadeType.PERSIST,
           CascadeType.REFRESH,
           CascadeType.MERGE,
           CascadeType.DETACH
    })
    @JoinColumn
    private Doctor doctor;

    @ManyToMany(fetch = FetchType.LAZY,cascade = {
            CascadeType.PERSIST,
            CascadeType.REFRESH,
            CascadeType.MERGE,
            CascadeType.DETACH
    })
    private Set<Patient> patient;
}
