package com.binnavigator.be.Member;

import com.binnavigator.be.Member.Data.AddRequest;
import com.binnavigator.be.Member.Data.LoginRequest;
import com.binnavigator.be.Member.Data.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    @PostMapping("/user/add")
    public ResponseEntity<Boolean> add(@RequestBody AddRequest addRequest) {
        return ResponseEntity.ok(memberService.add(addRequest));
    }

    @PostMapping("/user/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(memberService.login(loginRequest));
    }

    @DeleteMapping("/user/delete")
    public ResponseEntity<Boolean> delete(@RequestParam Long userId) {
        return ResponseEntity.ok(memberService.delete(userId));
    }
}
