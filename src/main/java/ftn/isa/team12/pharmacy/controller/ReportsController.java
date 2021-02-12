package ftn.isa.team12.pharmacy.controller;


import ftn.isa.team12.pharmacy.dto.PeriodeReportsDTO;
import ftn.isa.team12.pharmacy.dto.ReportsAverageMarksDTO;
import ftn.isa.team12.pharmacy.dto.ReportsMonthlyDTO;
import ftn.isa.team12.pharmacy.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/report", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReportsController {

    @Autowired
    ReportService reportService;



    @PreAuthorize("hasAnyRole('ROLE_PH_ADMIN')")
    @GetMapping("/marks")
    public ResponseEntity<?> reportMark() {
        Map<String, String> result = new HashMap<>();
        ReportsAverageMarksDTO reportsAverageMarksDTO = reportService.averageMarks();
        if(reportsAverageMarksDTO == null) {
            result.put("result","Can't count average mark");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(reportsAverageMarksDTO, HttpStatus.OK);
    }


    @PreAuthorize("hasAnyRole('ROLE_PH_ADMIN')")
    @GetMapping("/yearsReportExamination")
    public ResponseEntity<?> report() throws ParseException {
        return new ResponseEntity<>(reportService.yearsReportsExamination(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_PH_ADMIN')")
    @GetMapping("/monthly/{month}")
    public ResponseEntity<ReportsMonthlyDTO> report(@PathVariable Integer month) throws ParseException {
        return new ResponseEntity<>(reportService.monthlyReportExamination(month), HttpStatus.OK);
    }


    @PreAuthorize("hasAnyRole('ROLE_PH_ADMIN')")
    @GetMapping("/yearsReportDrug")
    public ResponseEntity<?> reportDrugs() throws ParseException {

        return new ResponseEntity<>(reportService.yearsReportDrug(), HttpStatus.OK);
    }



    @PreAuthorize("hasAnyRole('ROLE_PH_ADMIN')")
    @GetMapping("/monthlyDrug/{month}")
    public ResponseEntity<ReportsMonthlyDTO> reportMonthlyDrug(@PathVariable Integer month) throws ParseException {
        return new ResponseEntity<>(reportService.monthlyReportDrug(month), HttpStatus.OK);
    }


    @PreAuthorize("hasAnyRole('ROLE_PH_ADMIN')")
    @PostMapping("/income")
    public ResponseEntity<ReportsMonthlyDTO> reportMonthlyDrug(@RequestBody PeriodeReportsDTO periode) throws ParseException {

        return new ResponseEntity<>(reportService.reportIncome(periode), HttpStatus.OK);
    }

}
