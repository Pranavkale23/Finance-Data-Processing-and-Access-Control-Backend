package com.finance.dashboard.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.finance.dashboard.model.FinancialRecord;
import com.finance.dashboard.repository.FinancialRecordRepository;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/records")
public class RecordController {
    
    @Autowired
    FinancialRecordRepository recordRepository;

    @GetMapping
    @PreAuthorize("hasRole('VIEWER') or hasRole('ANALYST') or hasRole('ADMIN')")
    public List<FinancialRecord> getAllRecords() {
        return recordRepository.findAll();
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('VIEWER') or hasRole('ANALYST') or hasRole('ADMIN')")
    public ResponseEntity<FinancialRecord> getRecordById(@PathVariable Long id) {
        return recordRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public FinancialRecord createRecord(@RequestBody FinancialRecord record) {
        return recordRepository.save(record);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FinancialRecord> updateRecord(@PathVariable Long id, @RequestBody FinancialRecord recordDetails) {
        return recordRepository.findById(id).map(record -> {
            record.setAmount(recordDetails.getAmount());
            record.setType(recordDetails.getType());
            record.setCategory(recordDetails.getCategory());
            record.setDate(recordDetails.getDate());
            record.setNotes(recordDetails.getNotes());
            FinancialRecord updated = recordRepository.save(record);
            return ResponseEntity.ok(updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteRecord(@PathVariable Long id) {
        return recordRepository.findById(id).map(record -> {
            recordRepository.delete(record);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
