package com.finance.dashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.finance.dashboard.model.FinancialRecord;
import com.finance.dashboard.model.RecordType;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.math.BigDecimal;

@Repository
public interface FinancialRecordRepository extends JpaRepository<FinancialRecord, Long> {
    List<FinancialRecord> findByCategory(String category);
    List<FinancialRecord> findByType(RecordType type);
    List<FinancialRecord> findByDateBetween(LocalDate startDate, LocalDate endDate);
    
    @Query("SELECT r.category, SUM(r.amount) FROM FinancialRecord r WHERE r.type = :type GROUP BY r.category")
    List<Object[]> sumAmountByCategoryAndType(@Param("type") RecordType type);

    @Query("SELECT SUM(r.amount) FROM FinancialRecord r WHERE r.type = :type")
    BigDecimal sumAmountByType(@Param("type") RecordType type);
}
