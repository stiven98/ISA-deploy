package ftn.isa.team12.pharmacy.unitTests;

import ftn.isa.team12.pharmacy.domain.drugs.Drug;
import ftn.isa.team12.pharmacy.domain.enums.FormOfDrug;
import ftn.isa.team12.pharmacy.domain.enums.TypeOfDrug;
import ftn.isa.team12.pharmacy.repository.DrugRepository;
import ftn.isa.team12.pharmacy.service.impl.DrugServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UnitTestsStudent2 {

    @Mock
    private DrugRepository drugRepositoryMock;

    @Mock
    private Drug drugMock;

    @InjectMocks
    private DrugServiceImpl drugService;

    @Test
    public void testFindDrugById() {
        Drug drug = new Drug();
        drug.setName("Paracetamol");
        drug.setNote("Pazljivo!");
        drug.setTypeOfDrug(TypeOfDrug.Anesthetic);
        drug.setFormOfDrug(FormOfDrug.Capsule);
        drug.setAverageMark(9.0);
        drug.setCode("PAR#");
        UUID id = UUID.randomUUID();
        drug.setDrugId(id);

        when(this.drugRepositoryMock.findById(id)).thenReturn(Optional.of(drug));

        Drug drugRet = drugService.findById(id);
        assertEquals(drugRet, drug);
        verify(drugRepositoryMock, times(1)).findById(id);
        verifyNoMoreInteractions(drugRepositoryMock);
    }




    @Test
    public void findDrugByName() {
        Drug drug = new Drug();
        drug.setName("Paracetamol");
        drug.setNote("Pazljivo!");
        drug.setTypeOfDrug(TypeOfDrug.Anesthetic);
        drug.setFormOfDrug(FormOfDrug.Capsule);
        drug.setAverageMark(9.0);
        drug.setCode("PAR#");
        when(this.drugRepositoryMock.findDrugByName("Paracetamol")).thenReturn((drug));

        Drug drugRet = this.drugService.findDrugByName("Paracetamol");

        Assert.assertThat(drugRet,is(equalTo(drug)));
    }


    @Test
    @Transactional
    public void testAddDrug() {
        when(drugRepositoryMock.save(drugMock)).thenReturn(drugMock);
        Drug drug = drugService.save(drugMock);
        Assert.assertThat(drug, is(equalTo(drugMock)));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testFindAllDrugs() {
        Drug drug = new Drug();
        drug.setName("Paracetamol");
        drug.setNote("Pazljivo!");
        drug.setTypeOfDrug(TypeOfDrug.Anesthetic);
        drug.setFormOfDrug(FormOfDrug.Capsule);
        drug.setAverageMark(9.0);
        drug.setCode("PAR#");

        when(this.drugRepositoryMock.findAll()).thenReturn(Arrays.asList(drug));
        List<Drug> drugs = drugService.findAll();
        assertThat(drugs).hasSize(1);
        verify(drugRepositoryMock, times(1)).findAll();
        verifyNoMoreInteractions(drugRepositoryMock);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testUpdateDrug() {
        Drug drug = new Drug();
        drug.setName("Paracetamol");
        drug.setNote("Pazljivo!");
        drug.setTypeOfDrug(TypeOfDrug.Anesthetic);
        drug.setFormOfDrug(FormOfDrug.Capsule);
        drug.setAverageMark(9.0);
        drug.setCode("PAR#");
        UUID id = UUID.randomUUID();
        drug.setDrugId(id);
        when(drugRepositoryMock.findById(id)).thenReturn(Optional.of(drug));

        Drug drugRet = drugService.findById(id);
        drugRet.setAverageMark(10.0);

        when(drugRepositoryMock.save(drugRet)).thenReturn(drugRet);

        drugRet = drugService.save(drugRet);
        assertThat(drugRet).isNotNull();

        drugRet = drugService.findById(id);
        assertThat(drugRet.getAverageMark()).isEqualTo(10.0);

        verify(drugRepositoryMock, times(2)).findById(id);
        verify(drugRepositoryMock, times(1)).save(drugRet);
        verifyNoMoreInteractions(drugRepositoryMock);
    }



}