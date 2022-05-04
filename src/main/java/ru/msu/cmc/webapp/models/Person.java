package ru.msu.cmc.webapp.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "person")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Person implements CommonEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id")
    private Long id;

    @Column(nullable = false, name = "first_name")
    @NonNull
    private String first_name;

    @Column(nullable = false, name = "last_name")
    @NonNull
    private String last_name;

    @Column(nullable = false, name = "sur_name")
    @NonNull
    private String sur_name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "education_id")
    @ToString.Exclude
    @NonNull
    private Education education_id;

    @Column(nullable = false, name = "home_address")
    @NonNull
    private String home_address;

    @Column(nullable = false, name = "search_status")
    @NonNull
    private String search_status;
}