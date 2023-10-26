package com.binnavigator.be.Member;

import com.binnavigator.be.Member.Data.AddRequest;
import com.binnavigator.be.Member.Data.LoginRequest;
import com.binnavigator.be.Member.Data.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class MemberController {
    private final MemberService memberService;
    @PostMapping("/add")
    public ResponseEntity<Boolean> add(@RequestBody AddRequest addRequest) {
        return ResponseEntity.ok(memberService.add(addRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(memberService.login(loginRequest));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> delete(@RequestParam Long userId) {
        return ResponseEntity.ok(memberService.delete(userId));
    }
}
