package com.binnavigator.be.Bin;

import com.binnavigator.be.Member.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;


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

    private UUID image;

    private int reported = 0;

    private BinType type;

    @Column(nullable = false)
    private boolean full;

    @ManyToOne
    private Member owner;

    @Builder
    public Bin(float latitude, float longitude, String information, UUID image, Member owner,BinType type) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.information = information;
        this.image = image;
        this.reported = 0;
        this.type = type;
        this.full = false;
        this.owner = owner;
        owner.addBin(this);
    }

    public void reported() {
        this.reported++;
    }

    public boolean haveToDeleted() {
        return this.reported >= 5;
    }

    public void changeIsFull() {
        full = !this.full;
    }
}
