package com.finance.dashboard.payload.response;
import lombok.Data;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.util.Map;
import com.finance.dashboard.model.FinancialRecord;
import java.util.List;
@Data
@AllArgsConstructor
public class DashboardSummary {
    private BigDecimal totalIncome;
    private BigDecimal totalExpenses;
    private BigDecimal netBalance;
    private Map<String, BigDecimal> categoryWiseTotals;
    private List<FinancialRecord> recentActivity;
}
