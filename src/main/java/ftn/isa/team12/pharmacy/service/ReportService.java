package ftn.isa.team12.pharmacy.service;

import ftn.isa.team12.pharmacy.dto.PeriodeReportsDTO;
import ftn.isa.team12.pharmacy.dto.ReportsAverageMarksDTO;
import ftn.isa.team12.pharmacy.dto.ReportsMonthlyDTO;

import java.text.ParseException;
import java.util.List;

public interface ReportService {


    ReportsAverageMarksDTO averageMarks();


    List<Integer> yearsReportsExamination() throws ParseException;

    ReportsMonthlyDTO monthlyReportExamination(Integer month);



    List<Integer> yearsReportDrug() throws ParseException;

    ReportsMonthlyDTO monthlyReportDrug(Integer month);


    ReportsMonthlyDTO reportIncome(PeriodeReportsDTO dto);

}
