package com.binnavigator.be.Bin;

import com.binnavigator.be.Member.Member;
import jakarta.persistence.*;

@Entity
public class Bin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private float latitude;

    @Column(nullable = false)
    private float longitude;

    private String information;

    private int reported = 0;

    @ManyToOne
    private Member Owner;
}
