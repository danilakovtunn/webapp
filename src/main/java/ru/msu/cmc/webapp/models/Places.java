package ru.msu.cmc.webapp.models;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "places_of_work")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Places {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id")
    @ToString.Exclude
    @NonNull
    private Person person_id;

    @Column(nullable = false, name = "company")
    @NonNull
    private String company;

    @Column(nullable = false, name = "position")
    @NonNull
    private String position;

    @Column(nullable = false, name = "salary")
    @NonNull
    private Long salary;

    @Column(nullable = false, name = "start_date")
    @NonNull
    private Date start_date;

    @Column(nullable = false, name = "finish_day")
    @NonNull
    private Date finish_day;
}