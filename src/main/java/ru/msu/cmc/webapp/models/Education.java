package ru.msu.cmc.webapp.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "education")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id")
    private Long id;

    @Column(nullable = false, name = "educ_name")
    @NonNull
    private String educ_name;
}