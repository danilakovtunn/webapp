package ru.msu.cmc.webapp.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "resume")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Resume implements CommonEntity<Long> {

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

    @Column(nullable = false, name = "desired_salary")
    @NonNull
    private Long desired_salary;

    @Column(nullable = false, name = "experience")
    @NonNull
    private Float experience;
}