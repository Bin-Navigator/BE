package com.binnavigator.be.Member;

import com.binnavigator.be.Bin.Bin;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @OneToMany
    private List<Bin> binList;

    @Builder
    public Member(String username, String password, int reported, List<Bin> binList) {
        this.username = username;
        this.password = password;
        this.reported = reported;
        this.binList = binList;
    }
}
