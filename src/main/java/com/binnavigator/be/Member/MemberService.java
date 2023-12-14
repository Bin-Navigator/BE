package com.binnavigator.be.Member;

import com.binnavigator.be.Mail.MailService;
import com.binnavigator.be.Member.Data.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final MailService mailService;
    public LoginResponse login(LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = hashPassword(loginRequest.getPassword());
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
                .password(hashPassword(addRequest.getPassword()))
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
        int distance = member.addDistance((distanceRequest.getDistance()/10)*10);
        if(distance >= 1000 && distance % 10 == 0){
            member.addDistance(1);
            mailService.mailSendDistance(member);
        }
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

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            //16진수 문자열로 변환
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password");
        }
    }
}
