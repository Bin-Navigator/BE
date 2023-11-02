package com.binnavigator.be.Bin;

import com.binnavigator.be.Bin.Data.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bin")
public class BinController {
    private final BinService binService;

    @PostMapping("/add")
    public ResponseEntity<Long> add(@RequestBody BinAddRequest binAddRequest) {
        return ResponseEntity.ok(binService.add(binAddRequest));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> delete(@RequestBody BinDeleteRequest binDeleteRequest) {
        return ResponseEntity.ok(binService.delete(binDeleteRequest));
    }

    @PutMapping("/update")
    public void update(@RequestBody BinUpdateRequest binUpdateRequest) {
        binService.update(binUpdateRequest);
    }

    @PutMapping("/report")
    public void report(@RequestParam long binId) {
        binService.report(binId);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<GetResponse>> getAll() {
        return ResponseEntity.ok(binService.getAll());
    }

    @GetMapping("/get-by-user")
    public ResponseEntity<List<GetResponse>> getByUserId(@RequestParam long userId) {
        return ResponseEntity.ok(binService.getByUserId(userId));
    }

    @GetMapping("/get-by-bin")
    public ResponseEntity<GetByBinResponse> getByBinId(@RequestParam long binId) {
        return ResponseEntity.ok(binService.getByBinId(binId));
    }

    @PutMapping("/full")
    public ResponseEntity<GetResponse> full(@RequestParam long binId) {
        return ResponseEntity.ok(binService.full(binId));
    }
}
