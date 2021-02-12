package ftn.isa.team12.pharmacy.service;

import ftn.isa.team12.pharmacy.domain.common.Complaint;
import ftn.isa.team12.pharmacy.dto.AnswerDTO;
import ftn.isa.team12.pharmacy.dto.ComplaintDTO;

import java.util.List;

public interface ComplaintService {

    Complaint saveAndFlush(ComplaintDTO complaintRequest);

    List<Complaint> findAll();

    void sendAnswer(AnswerDTO answerRequest);
}
