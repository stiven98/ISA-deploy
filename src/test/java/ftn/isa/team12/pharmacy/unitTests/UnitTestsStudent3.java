package ftn.isa.team12.pharmacy.unitTests;

import ftn.isa.team12.pharmacy.domain.drugs.ERecipe;
import ftn.isa.team12.pharmacy.domain.users.*;
import ftn.isa.team12.pharmacy.repository.DermatologistRepository;
import ftn.isa.team12.pharmacy.repository.ERecipeRepository;
import ftn.isa.team12.pharmacy.repository.MedicalStuffRepository;
import ftn.isa.team12.pharmacy.repository.PharmacistRepository;
import ftn.isa.team12.pharmacy.service.impl.DermatologistServiceImpl;
import ftn.isa.team12.pharmacy.service.impl.ERecipeServiceImpl;
import ftn.isa.team12.pharmacy.service.impl.MedicalStuffServiceImpl;
import ftn.isa.team12.pharmacy.service.impl.PharmacistServiceImpl;
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
public class UnitTestsStudent3 {


    @Mock
    private MedicalStuffRepository medicalStuffRepository;

    @Mock
    private MedicalStuff medicalStuff;

    @InjectMocks
    private MedicalStuffServiceImpl medicalStuffService;

    @Mock
    private PharmacistRepository pharmacistRepository;

    @Mock
    private Pharmacist pharmacist;

    @InjectMocks
    private PharmacistServiceImpl pharmacistService;

    @Mock
    private DermatologistRepository dermatologistRepository;

    @Mock
    private ERecipeRepository eRecipeRepository;

    @Mock
    private Dermatologist dermatologist;

    @Mock
    private Patient patient;

    @Mock
    private ERecipe eRecipe;

    @InjectMocks
    private DermatologistServiceImpl dermatologistService;

    @InjectMocks
    private ERecipeServiceImpl eRecipeService;

    @Test
    @Transactional
    @Rollback(true)
    public void testFindAllMedicalStuff() {
        Dermatologist dermatologist = new Dermatologist();
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setEmail("dermatolog@derm.com");
        loginInfo.setPassword("dermatolog");
        dermatologist.setLoginInfo(loginInfo);
        when(this.medicalStuffRepository.findAll()).thenReturn(Arrays.asList(dermatologist));
        List<MedicalStuff> medicalStuffs = medicalStuffService.findAll();
        assertThat(medicalStuffs).hasSize(1);
        verify(medicalStuffRepository, times(1)).findAll();
        verifyNoMoreInteractions(medicalStuffRepository);
    }

    @Test
    @Transactional
    public void testAddMedicalStuff() {
        when(medicalStuffRepository.save(medicalStuff)).thenReturn(medicalStuff);
        MedicalStuff m  = medicalStuffService.save(medicalStuff);
        Assert.assertThat(m, is(equalTo(medicalStuff)));
    }

    @Test
    public void testFindOnePharmacist() {
        Pharmacist pharm = new Pharmacist();
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setEmail("farmaceut@farmaceut.com");
        loginInfo.setPassword("farmaceut");
        UUID id = UUID.randomUUID();
        pharm.setUserId(id);
        pharm.setLoginInfo(loginInfo);

        when(this.pharmacistRepository.findById(id)).thenReturn(Optional.of(pharm));

        Pharmacist p   = pharmacistService.findById(id);
        assertEquals(pharm, p);
        verify(pharmacistRepository, times(1)).findById(id);
        verifyNoMoreInteractions(pharmacistRepository);
    }

    @Test
    public void testFindOneDermatologist() {
        Dermatologist derm = new Dermatologist();
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setEmail("dermatolog@dermatolog.com");
        loginInfo.setPassword("dermatolog");
        UUID id = UUID.randomUUID();
        derm.setUserId(id);
        derm.setLoginInfo(loginInfo);

        when(this.dermatologistRepository.findById(id)).thenReturn(Optional.of(derm));

        Dermatologist d   = dermatologistService.findById(id);
        assertEquals(derm, d);
        verify(dermatologistRepository, times(1)).findById(id);
        verifyNoMoreInteractions(dermatologistRepository);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testUpdatePharmacist() {
        Pharmacist pharm = new Pharmacist();
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setEmail("farmaceut@farmaceut.com");
        loginInfo.setPassword("farmaceut");
        UUID id = UUID.randomUUID();
        pharm.setUserId(id);
        pharm.setLoginInfo(loginInfo);
        when(pharmacistRepository.findById(id)).thenReturn(Optional.of(pharm));
        Pharmacist p = pharmacistService.findById(id);
        p.getLoginInfo().setEmail("farmaceut@f.com");
        p.getLoginInfo().setPassword("f");
        when(pharmacistRepository.save(p)).thenReturn(p);
        p = pharmacistService.save(p);
        assertThat(p).isNotNull();
        p = pharmacistService.findById(id);
        assertThat(p.getLoginInfo().getEmail()).isEqualTo("farmaceut@f.com");
        assertThat(p.getLoginInfo().getPassword()).isEqualTo("f");
        verify(pharmacistRepository, times(2)).findById(id);
        verify(pharmacistRepository, times(1)).save(p);
        verifyNoMoreInteractions(pharmacistRepository);
    }

}
