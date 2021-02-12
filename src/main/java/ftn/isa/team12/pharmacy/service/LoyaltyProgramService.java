package ftn.isa.team12.pharmacy.service;

import ftn.isa.team12.pharmacy.domain.common.LoyaltyProgram;

public interface LoyaltyProgramService {

    LoyaltyProgram saveAndFlush(LoyaltyProgram loyaltyProgram);
    LoyaltyProgram getLoyaltyProgram();
}
