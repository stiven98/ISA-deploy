package ftn.isa.team12.pharmacy.service.impl;

import ftn.isa.team12.pharmacy.domain.common.WorkTime;
import ftn.isa.team12.pharmacy.repository.WorkTimeRepository;
import ftn.isa.team12.pharmacy.service.WorkTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = false)
public class WorkTimeServiceimpl implements WorkTimeService {

    @Autowired
    private WorkTimeRepository workTimeRepository;


    @Override
    public List<Date> getWorkDayForDermatologist(String email) {
        List<Date> list = new ArrayList<>();
        for (WorkTime workTime: workTimeRepository.findAllByEmployeeLoginInfoEmail(email)) {
            list.add(workTime.getDate());
        }


        return list;
    }
}
