package ru.msu.cmc.webapp.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "vacancies")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Vacancy implements CommonEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id")
    @ToString.Exclude
    @NonNull
    private Person person_id;

    @Column(nullable = false, name = "position")
    @NonNull
    private String position;

    @Column(nullable = false, name = "offered_salary")
    @NonNull
    private Long offered_salary;

    @Column(nullable = false, name = "experience")
    @NonNull
    private Float experience;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "required_education")
    @ToString.Exclude
    @NonNull
    private Education required_education;
}