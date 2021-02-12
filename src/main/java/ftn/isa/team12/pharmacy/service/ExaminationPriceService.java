package ftn.isa.team12.pharmacy.service;

import ftn.isa.team12.pharmacy.domain.pharmacy.ExaminationPrice;
import ftn.isa.team12.pharmacy.dto.ExaminationPriceDTO;

import java.util.List;

public interface ExaminationPriceService {


    List<ExaminationPrice> getAllByValideDate();


    ExaminationPrice createExaminationPrice(ExaminationPriceDTO dto);
    void validationExaminationPrice(ExaminationPriceDTO dto);

    ExaminationPrice changeExaminationPrice(ExaminationPriceDTO dto);



    List<ExaminationPriceDTO> getAllForChane();
}
