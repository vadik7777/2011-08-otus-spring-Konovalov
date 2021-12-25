package ru.otus.homework15.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "matryoshka")
public class Matryoshka {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "step")
    private int step;

    @Column(name = "max_steps")
    private int maxSteps;

    @OneToOne
    @JoinColumn(name = "matryoshka_id")
    private Matryoshka matryoshka;
}