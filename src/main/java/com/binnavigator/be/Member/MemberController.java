package com.binnavigator.be.Member;

import com.binnavigator.be.Member.Data.*;
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

    @GetMapping("/get")
    public ResponseEntity<UserGetResponse> get(@RequestParam Long userId) {
        return ResponseEntity.ok(memberService.get(userId));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(memberService.login(loginRequest));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> delete(@RequestParam Long userId) {
        return ResponseEntity.ok(memberService.delete(userId));
    }

    @PutMapping("/distance")
    public ResponseEntity<Integer> distance(@RequestBody DistanceRequest distanceRequest) {
        return ResponseEntity.ok(memberService.distance(distanceRequest));
    }
}
