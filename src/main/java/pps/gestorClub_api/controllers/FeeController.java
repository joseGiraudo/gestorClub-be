package pps.gestorClub_api.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pps.gestorClub_api.models.Fee;
import pps.gestorClub_api.services.FeeService;
import pps.gestorClub_api.services.FeeService;

import java.util.List;

@RestController
@RequestMapping("/fees")
public class FeeController {

    @Autowired
    private FeeService feeService;

    @GetMapping("")
    public ResponseEntity<List<Fee>> getAllFees() {
        List<Fee> fees = feeService.getAll();
        return ResponseEntity.ok(fees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fee> getFeeById(@PathVariable Long id) {
        Fee fee = feeService.getById(id);
        return ResponseEntity.ok(fee);
    }

    @PostMapping("")
    public ResponseEntity<Fee> createFee(@Valid @RequestBody Fee fee) {
        Fee response = feeService.create(fee);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteFee(@PathVariable("id") Long feeId) {
//        feeService.delete(feeId);
//        return ResponseEntity.noContent().build();
//    }

    @PutMapping("/{id}")
    public ResponseEntity<Fee> createFee(
            @PathVariable("id") Long id,
            @Valid @RequestBody Fee fee) {
        Fee response = feeService.update(id, fee);

        return ResponseEntity.ok(response);
    }
}
