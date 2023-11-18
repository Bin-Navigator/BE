package com.binnavigator.be.Mail;

import com.binnavigator.be.Member.Member;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    private JavaMailSender javaMailSender;

    public void mailSendAddBin(Member member){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(member.getEmail());
        message.setSubject(member.getUsername()+"님 Bin_navigator 이용 감사드립니다.");
        message.setText("환경을 위해 노력해주셔서 감사합니다. 10개의 쓰레기통을 등록하셨습니다.");
        javaMailSender.send(message);
    }
    public void mailSendDistance(Member member){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(member.getEmail());
        message.setSubject(member.getUsername()+"님 Bin_navigator 이용 감사드립니다.");
        message.setText("환경을 위해 노력해주셔서 감사합니다. "+member.getDistance()+"m를 이동하셨습니다.");
        javaMailSender.send(message);
    }
}
