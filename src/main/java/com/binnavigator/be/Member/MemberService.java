package com.binnavigator.be.Member;

import com.binnavigator.be.Member.Data.AddRequest;
import com.binnavigator.be.Member.Data.LoginRequest;
import com.binnavigator.be.Member.Data.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

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
                .reported(0)
                .binList(new ArrayList<>())
                .build();
        memberRepository.save(newMember);
        return true;
    }

    public Boolean delete(Long userId) {
        Member deletedMember = memberRepository.findById(userId).orElseThrow();
        memberRepository.delete(deletedMember);
        return true;
    }
}
