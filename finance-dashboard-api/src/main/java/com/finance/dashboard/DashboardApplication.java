package com.finance.dashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import com.finance.dashboard.model.ERole;
import com.finance.dashboard.model.Role;
import com.finance.dashboard.model.FinancialRecord;
import com.finance.dashboard.model.RecordType;
import com.finance.dashboard.repository.RoleRepository;
import com.finance.dashboard.repository.FinancialRecordRepository;
import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootApplication
public class DashboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(DashboardApplication.class, args);
	}

	@Bean
	CommandLineRunner initData(RoleRepository roleRepository, FinancialRecordRepository recordRepository) {
		return args -> {
			if(roleRepository.count() == 0) {
				Role viewer = new Role();
				viewer.setName(ERole.ROLE_VIEWER);
				roleRepository.save(viewer);

				Role analyst = new Role();
				analyst.setName(ERole.ROLE_ANALYST);
				roleRepository.save(analyst);

				Role admin = new Role();
				admin.setName(ERole.ROLE_ADMIN);
				roleRepository.save(admin);
			}

			if(recordRepository.count() == 0) {
				FinancialRecord rec1 = new FinancialRecord();
				rec1.setAmount(new BigDecimal("5000.00"));
				rec1.setType(RecordType.INCOME);
				rec1.setCategory("Salary");
				rec1.setDate(LocalDate.now().minusDays(5));
				rec1.setNotes("Monthly Salary");
				recordRepository.save(rec1);

				FinancialRecord rec2 = new FinancialRecord();
				rec2.setAmount(new BigDecimal("150.00"));
				rec2.setType(RecordType.EXPENSE);
				rec2.setCategory("Groceries");
				rec2.setDate(LocalDate.now().minusDays(2));
				rec2.setNotes("Supermarket");
				recordRepository.save(rec2);

				FinancialRecord rec3 = new FinancialRecord();
				rec3.setAmount(new BigDecimal("60.00"));
				rec3.setType(RecordType.EXPENSE);
				rec3.setCategory("Utilities");
				rec3.setDate(LocalDate.now().minusDays(1));
				rec3.setNotes("Internet Bill");
				recordRepository.save(rec3);
			}
		};
	}
}
