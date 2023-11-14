package com.binnavigator.be.Mail;

import com.binnavigator.be.Member.Member;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    private JavaMailSender javaMailSender;
    public void mailSend(Member member){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(member.getEmail());
        message.setSubject("Bin_navigator test 제목");
        message.setText("Bin_navigator test 내용");
        javaMailSender.send(message);
    }
}
