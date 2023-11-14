package com.binnavigator.be.Member;

import com.binnavigator.be.Member.Data.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public LoginResponse login(LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        Member loginMember = memberRepository.findByUsername(username).orElseThrow();
        if(password.equals(loginMember.getPassword())) {
            return LoginResponse.builder()
                    .username(loginMember.getUsername())
                    .userId(loginMember.getId())
                    .build();
        } else {
            throw new IllegalArgumentException("wrong password");
        }
    }

    public Boolean add(AddRequest addRequest) {
        Member newMember = Member.builder()
                .username(addRequest.getUsername())
                .password(addRequest.getPassword())
                .email(addRequest.getEmail())
                .build();
        memberRepository.save(newMember);
        return true;
    }

    public Boolean delete(Long userId) {
        Member deletedMember = memberRepository.findById(userId).orElseThrow();
        memberRepository.delete(deletedMember);
        return true;
    }

    public Integer distance(DistanceRequest distanceRequest) {
        Member member = memberRepository.findById(distanceRequest.getUserId()).orElseThrow();
        int distance = member.addDistance(distanceRequest.getDistance());
        memberRepository.save(member);
        return distance;
    }

    public UserGetResponse get(Long userId) {
        Member member = memberRepository.findById(userId).orElseThrow();
        return UserGetResponse.builder()
                .numOfBins(member.getBinList().size())
                .distance(member.getDistance())
                .build();
    }
}
