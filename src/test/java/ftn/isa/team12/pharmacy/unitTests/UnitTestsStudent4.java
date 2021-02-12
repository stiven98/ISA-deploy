package ftn.isa.team12.pharmacy.unitTests;

import ftn.isa.team12.pharmacy.domain.pharmacy.Pharmacy;
import ftn.isa.team12.pharmacy.repository.PharmacyRepository;
import ftn.isa.team12.pharmacy.service.impl.PharmacyServiceImpl;
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
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UnitTestsStudent4 {
    @Mock
    private PharmacyRepository pharmacyRepositoryMock;

    @Mock
    private Pharmacy pharmacyMock;

    @InjectMocks
    private PharmacyServiceImpl pharmacyService;

    @Test
    @Transactional
    @Rollback(true)
    public void testFindAllPharmacies() {
        Pharmacy pharmacy = new Pharmacy();
        pharmacy.setName("Jankovic Apoteka");
        pharmacy.setDescription("Najbolja apoteka");
        pharmacy.setAverageMark(5.5);
        pharmacy.setConsulationPrice(10.0);
        pharmacy.setExaminationPrice(11.0);


        when(this.pharmacyRepositoryMock.findAll()).thenReturn(Arrays.asList(pharmacy));
        List<Pharmacy> pharmacies = pharmacyService.findAll();
        assertThat(pharmacies).hasSize(1);
        verify(pharmacyRepositoryMock, times(1)).findAll();
        verifyNoMoreInteractions(pharmacyRepositoryMock);
    }

    @Test
    @Transactional
    public void testAddPharmacy() {
        when(pharmacyRepositoryMock.save(pharmacyMock)).thenReturn(pharmacyMock);
        Pharmacy p = pharmacyRepositoryMock.save(pharmacyMock);
        Assert.assertThat(p, is(equalTo(pharmacyMock)));
    }

    @Test
    @Transactional
    public void findPharmacyById() {
        Pharmacy pharmacy = new Pharmacy();
        pharmacy.setName("Jankovic Apoteka");
        pharmacy.setDescription("Najbolja apoteka");
        pharmacy.setAverageMark(5.5);
        pharmacy.setConsulationPrice(10.0);
        pharmacy.setExaminationPrice(11.0);
        UUID id = UUID.randomUUID();
        pharmacy.setId(id);

        when(this.pharmacyRepositoryMock.findPharmacyById(id)).thenReturn(pharmacy);

        Pharmacy pharmacyOrg = pharmacyService.findPharmacyById(id);
        assertEquals(pharmacy, pharmacyOrg);
        verify(pharmacyRepositoryMock, times(1)).findPharmacyById(id);
        verifyNoMoreInteractions(pharmacyRepositoryMock);

    }

    @Test
    @Transactional
    public void findPharmacyByName() {
        Pharmacy pharmacy = new Pharmacy();
        String name = "Jankovic";
        pharmacy.setName(name);
        pharmacy.setDescription("Najbolja apoteka");
        pharmacy.setAverageMark(5.5);
        pharmacy.setConsulationPrice(10.0);
        pharmacy.setExaminationPrice(11.0);
        pharmacy.setId(UUID.randomUUID());

        when(this.pharmacyRepositoryMock.findPharmacyByName(name)).thenReturn(pharmacy);

        Pharmacy pharmacyOrg = pharmacyService.findPharmacyByName(name);
        assertEquals(pharmacy, pharmacyOrg);
        verify(pharmacyRepositoryMock, times(1)).findPharmacyByName(name);
        verifyNoMoreInteractions(pharmacyRepositoryMock);

    }

    @Test
    @Transactional
    public void updatePharmacy() {
        Pharmacy pharmacy = new Pharmacy();
        pharmacy.setName("Jankovic Apoteka");
        pharmacy.setDescription("Najbolja apoteka");
        pharmacy.setAverageMark(5.5);
        pharmacy.setConsulationPrice(10.0);
        pharmacy.setExaminationPrice(11.0);
        UUID id = UUID.randomUUID();
        when(pharmacyRepositoryMock.findPharmacyById(id)).thenReturn(pharmacy);

        Pharmacy pharmacyRet = pharmacyService.findPharmacyById(id);
        pharmacyRet.setAverageMark(6.5);


        when(pharmacyRepositoryMock.save(pharmacyRet)).thenReturn(pharmacyRet);

        pharmacyRet = pharmacyService.save(pharmacyRet);
        assertThat(pharmacyRet).isNotNull();

        pharmacyRet = pharmacyRepositoryMock.findPharmacyById(id);
        assertThat(pharmacy.getAverageMark()).isEqualTo(6.5);

        verify(pharmacyRepositoryMock, times(2)).findPharmacyById(id);
        verify(pharmacyRepositoryMock, times(1)).save(pharmacyRet);
        verifyNoMoreInteractions(pharmacyRepositoryMock);


    }
}
