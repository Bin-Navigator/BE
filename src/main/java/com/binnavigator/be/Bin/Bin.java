package com.binnavigator.be.Bin;

import com.binnavigator.be.Member.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Bin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private float latitude;

    @Column(nullable = false)
    private float longitude;

    private String information;

    private String image;

    private int reported = 0;

    @Column(nullable = false)
    private boolean isFull;

    @ManyToOne
    private Member owner;

    @Builder
    public Bin(float latitude, float longitude, String information, String image, int reported, Member owner, boolean isFull) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.information = information;
        this.image = image;
        this.reported = reported;
        this.isFull = isFull;
        this.owner = owner;
    }

    public void reported() {
        this.reported++;
    }

    public boolean haveToDeleted() {
        return this.reported >= 5;
    }

    public void changeIsFull() {
        isFull = !this.isFull;
    }
}
