package ftn.isa.team12.pharmacy.unitTests;

import ftn.isa.team12.pharmacy.domain.users.AccountInfo;
import ftn.isa.team12.pharmacy.domain.users.LoginInfo;
import ftn.isa.team12.pharmacy.domain.users.Patient;
import ftn.isa.team12.pharmacy.domain.users.Pharmacist;
import ftn.isa.team12.pharmacy.repository.PatientRepository;
import ftn.isa.team12.pharmacy.repository.PharmacistRepository;
import ftn.isa.team12.pharmacy.service.PatientService;
import ftn.isa.team12.pharmacy.service.impl.PatientServiceImpl;
import ftn.isa.team12.pharmacy.service.impl.PharmacistServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UnitTestsStudent1 {

    @Mock
    private PatientRepository patientRepositoryMock;

    @Mock
    private Patient patientMock;

    @InjectMocks
    private PatientServiceImpl patientService;
    @Mock
    private PharmacistRepository pharmacistRepository;
    @Mock
    private Pharmacist pharmacist;

    @InjectMocks
    private PharmacistServiceImpl pharmacistService;

    @Test
    @Transactional
    @Rollback(true)
    public void testFindAllPatients() {
        Patient patient = new Patient();
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setEmail("pacijent@pacijent.com");
        loginInfo.setPassword("pacijent");
        patient.setLoginInfo(loginInfo);
        when(this.patientRepositoryMock.findAll()).thenReturn(Arrays.asList(patient));
        List<Patient> patients = patientService.findAll();
        assertThat(patients).hasSize(1);
        verify(patientRepositoryMock, times(1)).findAll();
        verifyNoMoreInteractions(patientRepositoryMock);
    }

    @Test
    public void testFindOnePatient() {
        Patient patient = new Patient();
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setEmail("pacijent@pacijent.com");
        loginInfo.setPassword("pacijent");
        UUID id = UUID.randomUUID();
        patient.setUserId(id);
        patient.setLoginInfo(loginInfo);

        when(this.patientRepositoryMock.findById(id)).thenReturn(Optional.of(patient));

        Patient p = patientService.findById(id);
        assertEquals(patient, p);
        verify(patientRepositoryMock, times(1)).findById(id);
        verifyNoMoreInteractions(patientRepositoryMock);
    }

    @Test
    @Transactional
    public void testAddPatient() {
        when(patientRepositoryMock.save(patientMock)).thenReturn(patientMock);
        Patient p = patientService.save(patientMock);
        assertThat(p, is(equalTo(patientMock)));
    }

    @Test
    public void findPatientByEmail() {
        Patient patient = new Patient();
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setEmail("pacijent@pacijent.com");
        loginInfo.setPassword("pacijent");
        UUID id = UUID.randomUUID();
        patient.setUserId(id);
        patient.setLoginInfo(loginInfo);
        when(this.patientRepositoryMock.findByEmail("pacijent@pacijent.com")).thenReturn((patient));

        Patient p = this.patientService.findByEmail("pacijent@pacijent.com");

        assertThat(p,is(equalTo(patient)));
    }
    @Test
    @Transactional
    @Rollback(true)
    public void testUpdatePatient() {
        Patient patient = new Patient();
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setEmail("pacijent@pacijent.com");
        loginInfo.setPassword("pacijent");
        UUID id = UUID.randomUUID();
        patient.setUserId(id);
        patient.setLoginInfo(loginInfo);
        when(patientRepositoryMock.findById(id)).thenReturn(Optional.of(patient));

        Patient p = patientService.findById(id);
        p.getLoginInfo().setEmail("pacijent@p.com");
        p.getLoginInfo().setPassword("p");

        when(patientRepositoryMock.save(p)).thenReturn(p);

        p = patientService.save(p);

        assertThat(p).isNotNull();

        p = patientService.findById(id);
        assertThat(p.getLoginInfo().getEmail()).isEqualTo("pacijent@p.com");
        assertThat(p.getLoginInfo().getPassword()).isEqualTo("p");

        verify(patientRepositoryMock, times(2)).findById(id);
        verify(patientRepositoryMock, times(1)).save(p);
        verifyNoMoreInteractions(patientRepositoryMock);
    }

}

