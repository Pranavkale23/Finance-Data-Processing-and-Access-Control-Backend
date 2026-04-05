package com.finance.dashboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.finance.dashboard.model.RecordType;
import com.finance.dashboard.model.FinancialRecord;
import com.finance.dashboard.repository.FinancialRecordRepository;
import com.finance.dashboard.payload.response.DashboardSummary;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    FinancialRecordRepository recordRepository;

    @GetMapping("/summary")
    @PreAuthorize("hasRole('ANALYST') or hasRole('ADMIN')")
    public DashboardSummary getDashboardSummary() {
        BigDecimal totalIncome = recordRepository.sumAmountByType(RecordType.INCOME);
        if(totalIncome == null) totalIncome = BigDecimal.ZERO;
        
        BigDecimal totalExpenses = recordRepository.sumAmountByType(RecordType.EXPENSE);
        if(totalExpenses == null) totalExpenses = BigDecimal.ZERO;
        
        BigDecimal netBalance = totalIncome.subtract(totalExpenses);

        List<Object[]> categorySums = recordRepository.sumAmountByCategoryAndType(RecordType.EXPENSE);
        Map<String, BigDecimal> categoryWiseTotals = new HashMap<>();
        for(Object[] result : categorySums) {
            categoryWiseTotals.put((String) result[0], (BigDecimal) result[1]);
        }

        List<FinancialRecord> recentActivity = recordRepository.findAll(
                PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "date"))).getContent();

        return new DashboardSummary(totalIncome, totalExpenses, netBalance, categoryWiseTotals, recentActivity);
    }
}
