package com.binnavigator.be.Member;

import com.binnavigator.be.Bin.Bin;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String username;

    private String password;

    private int reported;

    private int distance;

    private String email;

    @OneToMany
    private List<Bin> binList;

    @Builder
    public Member(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.reported = 0;
        this.binList = new ArrayList<>();
        this.distance = 0;
        this.email = email;
    }

    public void addBin(Bin bin) {
        this.binList.add(bin);
    }

    public int addDistance(int distance) {
        return this.distance += distance;
    }
}
