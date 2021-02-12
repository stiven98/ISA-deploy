package ftn.isa.team12.pharmacy.domain.drugs;
import ftn.isa.team12.pharmacy.domain.common.*;
import ftn.isa.team12.pharmacy.domain.enums.*;
import ftn.isa.team12.pharmacy.domain.marks.DrugMarks;
import ftn.isa.team12.pharmacy.domain.pharmacy.Examination;
import ftn.isa.team12.pharmacy.domain.pharmacy.ExaminationPrice;
import ftn.isa.team12.pharmacy.domain.pharmacy.Pharmacy;
import ftn.isa.team12.pharmacy.domain.users.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Test {



    public static void main(String[] args) throws Exception {
        Logger.getLogger("").setLevel(Level.OFF);
        final EntityManagerFactory factory =
                Persistence.createEntityManagerFactory("PharmacyDB");
        EntityManager em = factory.createEntityManager();

        LoyaltyProgram loyaltyProgram = new LoyaltyProgram();
        loyaltyProgram.setMinRegular(200);
        loyaltyProgram.setMinSilver(400);
        loyaltyProgram.setMinGold(700);
        loyaltyProgram.setDiscountForRegular(5);
        loyaltyProgram.setDiscountForSilver(8);
        loyaltyProgram.setDiscountForGold(13);
        loyaltyProgram.setPointsPerExamination(5);
        loyaltyProgram.setPointsPerCounseling(5);


        Authority a = new Authority();
        a.setRole("ROLE_PH_ADMIN");

        Authority role_pharmacist = new Authority();
        role_pharmacist.setRole("ROLE_PHARMACIST");

        Authority derm = new Authority();
        derm.setRole("ROLE_DERMATOLOGIST");

        Authority pa = new Authority();
        pa.setRole("ROLE_PATIENT");

        Authority sa = new Authority();
        sa.setRole("ROLE_SYSTEM_ADMINISTRATOR");

        Authority su = new Authority();
        su.setRole("ROLE_SUPPLIER");

        List<Authority> authorities = new ArrayList<Authority>();
        List<Authority> authorities2 = new ArrayList<Authority>();
        List<Authority> authoritiesDerm = new ArrayList<>();
        List<Authority> authoritiesSysAdmin = new ArrayList<>();

        List<Authority> authoritiesPatient = new ArrayList<>();

        List<Authority> supsss = new ArrayList<>();

        List<Authority> authoritiPharmacist = new ArrayList<>();

        authorities.add(pa);
        authorities2.add(a);
        authoritiesDerm.add(derm);
        authoritiesSysAdmin.add(sa);
        supsss.add(su);

        authoritiesPatient.add(su);
        authoritiPharmacist.add(role_pharmacist);

        Address addressSysAdmin = new Address();
        addressSysAdmin.setStreet("Bulevar Despota Stefana");
        addressSysAdmin.setNumber(7);


        Address address = new Address();
        address.setNumber(10);
        address.setStreet("Karadjordjeva");

        Address address1 = new Address();
        address1.setNumber(10);
        address1.setStreet("Masarikova");

        Address address2 = new Address();
        address2.setNumber(20);
        address2.setStreet("Kisacka");

        Country country = new Country();
        country.setName("Srbija");

        City city  = new City();
        city.setName("Novi Sad");
        city.setCountry(country);
        city.setZipCode(21000);

        City city1  = new City();
        city1.setName("Beograd");
        city1.setCountry(country);
        city1.setZipCode(11000);

        Location locationSysAdmin = new Location();
        locationSysAdmin.setCity(city);
        locationSysAdmin.setAddress(addressSysAdmin);

        Location location = new Location();
        location.setAddress(address);
        location.setCity(city);

        Location location1 = new Location();
        location1.setAddress(address1);
        location1.setCity(city1);

        Location location2 = new Location();
        location2.setAddress(address2);
        location2.setCity(city);

        AccountInfo accountInfo4 = new AccountInfo();
        accountInfo4.setActive(true);
        accountInfo4.setFirstLogin(false);
        accountInfo4.setName("Marko");
        accountInfo4.setLastName("Markovic");
        accountInfo4.setPhoneNumber("000330303032");

        LoginInfo loginInfo4 = new LoginInfo();
        loginInfo4.setEmail("marko@gmail.com");
        //marko
        loginInfo4.setPassword("$2y$10$Z5f1FLfPOnoUy30IFf45f.HI.hJFejU3oHGB0xd2ol5pjhBdllfZa");

        AccountInfo accountInfoSysAdmin = new AccountInfo();
        accountInfoSysAdmin.setActive(true);
        accountInfoSysAdmin.setFirstLogin(false);
        accountInfoSysAdmin.setName("Petar");
        accountInfoSysAdmin.setLastName("Petrovic");
        accountInfoSysAdmin.setPhoneNumber("+381-64-333-21-12");

        LoginInfo loginInfoSysAdmin = new LoginInfo();
        loginInfoSysAdmin.setEmail("petar.petrovic@gmail.com");
        loginInfoSysAdmin.setPassword("$2a$10$FCQuRUBQvmjK9JatjRzbaO8ZgmCX/lnr2ycU0ge/r9eT.dZbTcQl6");



        SystemAdministrator systemAdministrator = new SystemAdministrator();
        systemAdministrator.setAccountInfo(accountInfoSysAdmin);
        systemAdministrator.setLocation(locationSysAdmin);

        systemAdministrator.setLoginInfo(loginInfoSysAdmin);
        systemAdministrator.setAuthorities(authoritiesSysAdmin);




        PharmacyAdministrator pharmacyAdministrator = new PharmacyAdministrator();
        pharmacyAdministrator.setAccountInfo(accountInfo4);
        pharmacyAdministrator.setLocation(location1);
        pharmacyAdministrator.setLoginInfo(loginInfo4);


        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Bayer");

        Ingredient drugIngreditent = new Ingredient();
        drugIngreditent.setName("Sastojak01");

        Contraindication contraindication1 = new Contraindication();
        contraindication1.setName("Headache");
        Contraindication contraindication2 = new Contraindication();
        contraindication2.setName("Nausea");

        Drug drug = new Drug();
        drug.setName("Aspirin");
        drug.setCode("ASP123XXX123");
        drug.setTypeOfDrug(TypeOfDrug.HerbalMedicine);
        drug.setFormOfDrug(FormOfDrug.Pill);
        drug.setManufacturer(manufacturer);
        drug.setNote("This is note");
        drug.setIssuanceRegime(IssuanceRegime.withoutRecipe);
        drug.setAverageMark(0.0);
        drug.setPoints(6);



        Drug drug1 = new Drug();
        drug1.setName("Brufen");
        drug1.setCode("BRUF123XXX123");
        drug1.setTypeOfDrug(TypeOfDrug.Antihistamine);
        drug1.setFormOfDrug(FormOfDrug.Capsule);
        drug1.setManufacturer(manufacturer);
        drug1.setIssuanceRegime(IssuanceRegime.withoutRecipe);
        drug1.getContraindications().add(contraindication1);
        drug1.getContraindications().add(contraindication2);
        drug1.setAverageMark(5.0);
        drug1.setPoints(3);


        Drug drug2 = new Drug();
        drug2.setName("Paracetamol");
        drug2.setCode("PAR123XXX123");
        drug2.setTypeOfDrug(TypeOfDrug.Anesthetic);
        drug2.setFormOfDrug(FormOfDrug.Powder);
        drug2.setManufacturer(manufacturer);
        drug2.setIssuanceRegime(IssuanceRegime.withoutRecipe);
        drug2.setAverageMark(10.0);
        drug2.setPoints(3);


        Drug drug3 = new Drug();
        drug3.setName("Andol");
        drug3.setCode("AND123XXX123");
        drug3.setTypeOfDrug(TypeOfDrug.Anesthetic);
        drug3.setFormOfDrug(FormOfDrug.Pill);
        drug3.setManufacturer(manufacturer);
        drug3.setIssuanceRegime(IssuanceRegime.withoutRecipe);
        drug3.setAverageMark(3.5);
        drug3.setPoints(1);


        Drug drug4 = new Drug();
        drug4.setName("Panadol");
        drug4.setCode("PAND123XXX123");
        drug4.setTypeOfDrug(TypeOfDrug.Antihistamine);
        drug4.setFormOfDrug(FormOfDrug.Capsule);
        drug4.setManufacturer(manufacturer);
        drug4.setIssuanceRegime(IssuanceRegime.withoutRecipe);
        drug4.setAverageMark(3.1);
        drug4.setPoints(2);


        Drug drug5 = new Drug();
        drug5.setName("Febricet");
        drug5.setCode("FEB123XXX123");
        drug5.setTypeOfDrug(TypeOfDrug.Anesthetic);
        drug5.setFormOfDrug(FormOfDrug.Powder);
        drug5.setManufacturer(manufacturer);
        drug5.setIssuanceRegime(IssuanceRegime.withoutRecipe);
        drug5.setAverageMark(6.0);
        drug5.setPoints(2);


        Drug drug6 = new Drug();
        drug6.setName("Biofrezee");
        drug6.setCode("BF123XXX123");
        drug6.setTypeOfDrug(TypeOfDrug.Antihistamine);
        drug6.setFormOfDrug(FormOfDrug.Cream);
        drug6.setManufacturer(manufacturer);
        drug6.setIssuanceRegime(IssuanceRegime.withoutRecipe);
        drug6.setAverageMark(7.9);
        drug6.setPoints(2);

        Drug drug7 = new Drug();
        drug7.setName("Pantenol");
        drug7.setCode("PAN123XXX123");
        drug7.setTypeOfDrug(TypeOfDrug.Antibiotic);
        drug7.setFormOfDrug(FormOfDrug.Cream);
        drug7.setManufacturer(manufacturer);
        drug7.setIssuanceRegime(IssuanceRegime.withoutRecipe);
        drug7.setAverageMark(9.0);
        drug7.setPoints(2);

        drug7.getSubstituteDrugs().add(drug2);


        drug1.setCode("321DROGA321");
        drug1.setTypeOfDrug(TypeOfDrug.Anesthetic);
        drug1.setFormOfDrug(FormOfDrug.Capsule);
        drug1.setManufacturer(manufacturer);
        drug1.setNote("This is note 2");
        drug1.setIssuanceRegime(IssuanceRegime.withRecipe);
        drug1.setAverageMark(10.0);


        Ingredient ingredient1 = new Ingredient();
        ingredient1.setName("Silicijum-dioksid");
        Ingredient ingredient2 = new Ingredient();
        ingredient2.setName("Dihidrat");
        Ingredient ingredient3 = new Ingredient();
        ingredient3.setName("Kalijum-hidrogen fosfat");

        drug1.getIngredients().add(ingredient1);
        drug1.getIngredients().add(ingredient2);
        drug2.getIngredients().add(ingredient3);

        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setName("Aca");
        accountInfo.setLastName("Simic");
        accountInfo.setActive(true);
        accountInfo.setPhoneNumber("06344346");
        accountInfo.setFirstLogin(false);

        AccountInfo accountInfo1 = new AccountInfo();
        accountInfo1.setName("Aleksandar");
        accountInfo1.setLastName("Stevanović");
        accountInfo1.setActive(true);
        accountInfo1.setPhoneNumber("06125446");
        accountInfo1.setFirstLogin(false);

        AccountInfo accountInfo2 = new AccountInfo();
        accountInfo2.setName("Djordjije");
        accountInfo2.setLastName("Kundacina");
        accountInfo2.setActive(true);
        accountInfo2.setPhoneNumber("06685446");
        accountInfo2.setFirstLogin(false);

        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setEmail("aca@faca.com");
        //acafaca
        loginInfo.setPassword("$2a$10$PLALH3vbrAY7mZKmndud4.NdDd2eV9TDbreXZV0kOamJA0/MYEMhS");

        LoginInfo loginInfo1 = new LoginInfo();
        loginInfo1.setEmail("maca@faca.com");
        //macafaca
        loginInfo1.setPassword("$2y$10$1fIqu1TMa1nSN40huVlHsePYVeXDIcb.5/lqNLzvSq0nM4p3ZNmIS");

        LoginInfo loginInfo2 = new LoginInfo();
        loginInfo2.setEmail("a@faca.com");
        //afaca
        loginInfo2.setPassword("$2a$10$zPMA0uyLu3cxTy4ko7KKr.IP3iXIG8ZR3GvMQdWELnpwCdfv5Ml5G");

        AccountInfo accountInfo3 = new AccountInfo();
        accountInfo3.setName("Jovan");
        accountInfo3.setLastName("Bosnic");
        accountInfo3.setFirstLogin(false);
        accountInfo3.setPhoneNumber("06134562");

        LoginInfo loginInfo3 = new LoginInfo();
        loginInfo3.setEmail("jovan@gmail.com");
        //jovan
        loginInfo3.setPassword("$2y$10$GDTQteWFlMDi.Oed2hujL.hJwnf.PfZMcHevDtiahWOtIpKcMTTX6 ");

        AccountInfo dermatologistAccountInfoPharmacy1 = new AccountInfo();
        dermatologistAccountInfoPharmacy1.setPhoneNumber("0622221133");
        dermatologistAccountInfoPharmacy1.setName("Jovica");
        dermatologistAccountInfoPharmacy1.setLastName("Jovicic");
        dermatologistAccountInfoPharmacy1.setActive(false);

        LoginInfo derLogInfPharmacy1 = new LoginInfo();
        derLogInfPharmacy1.setEmail("jovica@gmail.com");
        //jovica
        derLogInfPharmacy1.setPassword("$2y$10$whzNUDqaYDVydkQUYs0QXOUA9ORGd1G7d/Cp4laDVww5y6Lzyhi/K ");


        Dermatologist dermatologistPharmacy1 = new Dermatologist();
        dermatologistPharmacy1.setAverageMark(5.0);
        dermatologistPharmacy1.setLocation(location2);
        dermatologistPharmacy1.setAccountInfo(dermatologistAccountInfoPharmacy1);
        dermatologistPharmacy1.setLoginInfo(derLogInfPharmacy1);


        AccountInfo accInfoDer = new AccountInfo();
        accInfoDer.setLastName("Slavic");
        accInfoDer.setName("Slavica");
        accInfoDer.setActive(false);
        accInfoDer.setPhoneNumber("0611113485");

        LoginInfo logInfDer = new LoginInfo();
        logInfDer.setEmail("slavica@gmail.com");
        logInfDer.setPassword("$2y$10$7ItedZVTHIPALTm/poyI.eKQErkFJJbxOd5WcSkKFIZ9QsyN06iFu ");

        Dermatologist der = new Dermatologist();
        der.setAverageMark(4.0);
        der.setLocation(location1);
        der.setAccountInfo(accInfoDer);
        der.setLoginInfo(logInfDer);



        Dermatologist dermatologist = new Dermatologist();
        dermatologist.setAverageMark(3.0);
        dermatologist.setLocation(location2);
        dermatologist.setAccountInfo(accountInfo);
        dermatologist.setLoginInfo(loginInfo);

        Pharmacist pharmacist = new Pharmacist();
        pharmacist.setAverageMark(3.0);
        pharmacist.setLocation(location1);
        pharmacist.setLoginInfo(loginInfo2);
        pharmacist.setAccountInfo(accountInfo2);

        Pharmacist pharmacistA = new Pharmacist();
        pharmacistA.setAverageMark(5.0);
        pharmacistA.setLocation(location);
        LoginInfo l = new LoginInfo();
        l.setEmail("d@gmail.com");
        //b
        l.setPassword("$2y$10$eGbL004kbDrfj4GPoK7yQO2yYRYqfyFLHS7UjPmH7tp1ub7fl0vom ");
        pharmacistA.setLoginInfo(l);
        AccountInfo g = new AccountInfo();
        g.setPhoneNumber("0666666666");
        g.setName("Paja");
        g.setLastName("Pajic");
        g.setActive(true);
        g.setFirstLogin(false);
        pharmacistA.setAccountInfo(g);

        SimpleDateFormat sdfa = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = new Date();
        Date endDate = new Date(2021,01,25);
        DateRange dateRange = new DateRange();
        dateRange.setStartDate(sdfa.parse("2021-02-01"));
        dateRange.setEndDate(sdfa.parse("2021-03-01"));





        Pharmacy pharmacy = new Pharmacy();
        pharmacy.setName("Apoteka 1");
        pharmacy.setLocation(location1);
        pharmacy.setDescription("Dobra apoteka");
        pharmacy.setAverageMark(8.9);


        Pharmacy pharmacy2 = new Pharmacy();
        pharmacy2.setName("Apoteka 2");
        pharmacy2.setLocation(location2);
        pharmacy2.setDescription("Nasa apoteka");
        pharmacy2.setAverageMark(8.6);

        Pharmacy pharmacy3 = new Pharmacy();
        pharmacy3.setName("Apoteka 3");
        pharmacy3.setLocation(location);
        pharmacy3.setDescription("Kul apoteka");
        pharmacy3.setAverageMark(9.6);



        Pharmacy pharmacy4 = new Pharmacy();
        pharmacy4.setName("Apoteka 4");
        pharmacy4.setLocation(location);
        pharmacy4.setDescription("Naj apoteka");
        pharmacy4.setAverageMark(9.1);

        Pharmacy pharmacy5 = new Pharmacy();
        pharmacy5.setName("Apoteka 5");
        pharmacy5.setLocation(location);
        pharmacy5.setDescription("Nova apoteka");
        pharmacy5.setAverageMark(8.1);

        Pharmacy pharmacy6 = new Pharmacy();
        pharmacy6.setName("Apoteka 6");
        pharmacy6.setLocation(location);
        pharmacy6.setDescription("Ful apoteka");
        pharmacy6.setAverageMark(8.7);



        Pharmacy pharmacy7 = new Pharmacy();
        pharmacy7.setName("Apoteka 7");
        pharmacy7.setLocation(location2);
        pharmacy7.setDescription("Mnogo dobra apoteka");
        pharmacy7.setAverageMark(9.2);


        Pharmacy pharmacy8 = new Pharmacy();
        pharmacy8.setName("Apoteka 8");
        pharmacy8.setLocation(location1);
        pharmacy8.setDescription("Nasa najbolja apoteka");
        pharmacy8.setAverageMark(9.8);

        pharmacy.getDermatologists().add(dermatologist);
        pharmacy.getDermatologists().add(dermatologistPharmacy1);
        pharmacy.getPharmacists().add(pharmacistA);
        pharmacy3.getDermatologists().add(der);
        pharmacy8.getDermatologists().add(dermatologistPharmacy1);
        pharmacist.setPharmacy(pharmacy);
        pharmacistA.setPharmacy(pharmacy);

        DrugInPharmacy drugInPharmacy6 = new DrugInPharmacy();
        drugInPharmacy6.setPharmacy(pharmacy2);
        drugInPharmacy6.setDrug(drug);
        drugInPharmacy6.setQuantity(10);

        DrugInPharmacy drugInPharmacy7 = new DrugInPharmacy();
        drugInPharmacy7.setPharmacy(pharmacy3);
        drugInPharmacy7.setDrug(drug);
        drugInPharmacy7.setQuantity(10);


        DrugInPharmacy drugInPharmacy = new DrugInPharmacy();
        drugInPharmacy.setPharmacy(pharmacy);
        drugInPharmacy.setDrug(drug);
        drugInPharmacy.setQuantity(10);
        pharmacy.getDrugs().add(drugInPharmacy);

        DrugPrice drugPrice = new DrugPrice();
        drugPrice.setPrice(560.0);
        drugPrice.setDrug(drug);
        drugPrice.setPharmacy(pharmacy);
        drugPrice.setValidityPeriod(dateRange);


        DrugPrice drugPrice2 = new DrugPrice();
        drugPrice2.setPrice(100);
        drugPrice2.setDrug(drug);
        drugPrice2.setPharmacy(pharmacy2);
        drugPrice2.setValidityPeriod(dateRange);

        DrugPrice drugPrice3 = new DrugPrice();
        drugPrice3.setPrice(130);
        drugPrice3.setDrug(drug);
        drugPrice3.setPharmacy(pharmacy3);
        drugPrice3.setValidityPeriod(dateRange);

        DrugPrice drugPrice4 = new DrugPrice();
        drugPrice4.setPrice(120);
        drugPrice4.setDrug(drug);
        drugPrice4.setPharmacy(pharmacy4);
        drugPrice4.setValidityPeriod(dateRange);

        AccountCategory accounCategory = new AccountCategory();
        accounCategory.setCategory(UserCategory.gold);
        accounCategory.setPoints(12);


        Patient patient = new Patient();
        patient.setLocation(location1);
        patient.setLoginInfo(loginInfo1);
        patient.setAccountInfo(accountInfo1);
        patient.setCategory(accounCategory);
        patient.getAllergies().add(drug);


        patient.getSubscribedPharmacies().add(pharmacy);
        patient.getSubscribedPharmacies().add(pharmacy3);

        DrugMarks drugMarks = new DrugMarks();
        drugMarks.setDrug(drug);
        drugMarks.setMark(8.1);
        drugMarks.setPatient(patient);


        ERecipe eRecipe = new ERecipe();
        eRecipe.setPatient(patient);
        eRecipe.setDateOfIssuing(startDate);
        eRecipe.setCode("123456675");
        eRecipe.setERecipeStatus(ERecipeStatus.newErecipe);
        eRecipe.setPharmacy(pharmacy);


        ERecipe eRecipe1 = new ERecipe();
        eRecipe1.setPatient(patient);
        eRecipe1.setDateOfIssuing(endDate);
        eRecipe1.setCode("1234566752");
        eRecipe1.setERecipeStatus(ERecipeStatus.processed);
        eRecipe1.setPharmacy(pharmacy4);

        ERecipe eRecipe2 = new ERecipe();
        eRecipe2.setPatient(patient);
        eRecipe2.setDateOfIssuing(endDate);
        eRecipe2.setCode("123456675452");
        eRecipe2.setERecipeStatus(ERecipeStatus.declined);
        eRecipe2.setPharmacy(pharmacy6);

        ERecipeItem eRecipeItem = new ERecipeItem();
        eRecipeItem.setQuantity(12);
        eRecipeItem.setDrug(drug);
        eRecipeItem.setERecipe(eRecipe);

        ERecipeItem eRecipeItem3 = new ERecipeItem();
        eRecipeItem3.setQuantity(2);
        eRecipeItem3.setDrug(drug5);
        eRecipeItem3.setERecipe(eRecipe2);

        ERecipeItem eRecipeItem1 = new ERecipeItem();
        eRecipeItem1.setQuantity(14);
        eRecipeItem1.setDrug(drug1);
        eRecipeItem1.setERecipe(eRecipe);

        ERecipeItem eRecipeItem2 = new ERecipeItem();
        eRecipeItem2.setQuantity(4);
        eRecipeItem2.setDrug(drug3);
        eRecipeItem2.setERecipe(eRecipe1);


        eRecipe.getERecipeItems().add(eRecipeItem);
        eRecipe.getERecipeItems().add(eRecipeItem1);
        eRecipe1.getERecipeItems().add(eRecipeItem2);
        eRecipe2.getERecipeItems().add(eRecipeItem3);



        DrugReservation drugReservation = new DrugReservation();
        drugReservation.setCode("Rezervacija123");
        drugReservation.setPatient(patient);
        drugReservation.setPharmacy(pharmacy);
        drugReservation.setQuantity(5);
        drugReservation.setReservationStatus(ReservationStatus.created);
        drugReservation.setReservationDateRange(dateRange);
        drugReservation.setDrug(drug);

        LocalTime t =  LocalTime.of(8,0);
        LocalTime t1 = LocalTime.of(16,0);

        TimeRange timeRange = new TimeRange();
        timeRange.setStartTime(t);
        timeRange.setEndTime(t1);


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        WorkTime workTime = new WorkTime();
        Date date2 = sdf.parse("2021-03-05");
        workTime.setDate(date2);
        workTime.setStartTime( LocalTime.of(8,0,0));
        workTime.setEndTime( LocalTime.of(16,0,0));
        workTime.setPharmacy(pharmacy);
        workTime.setEmployee(dermatologist);
        dermatologist.getWorkTime().add(workTime);


        WorkTime workTime1 = new WorkTime();
        Date date1 = sdf.parse("2021-03-03");
        workTime1.setDate(date1);
        workTime1.setEndTime( LocalTime.of(10,0,0));
        workTime1.setStartTime( LocalTime.of(14,0,0));
        workTime1.setPharmacy(pharmacy);
        workTime1.setEmployee(pharmacist);
        pharmacist.getWorkTime().add(workTime1);

        pharmacyAdministrator.setPharmacy(pharmacy);
        DrugOrder drugOrder = new DrugOrder();
        drugOrder.setDrugOrderStatus(DrugOrderStatus.waitingForOffers);
        drugOrder.setPharmacy(pharmacy);
        drugOrder.setDeadline(new Date());
        drugOrder.setPharmacyAdministrator(pharmacyAdministrator);

        DrugOrder drugOrder1 = new DrugOrder();
        drugOrder1.setDrugOrderStatus(DrugOrderStatus.waitingForOffers);
        drugOrder1.setPharmacy(pharmacy);
        drugOrder1.setDeadline(new Date());
        drugOrder1.setPharmacyAdministrator(pharmacyAdministrator);

        DrugOrderItem drugOrderItem = new DrugOrderItem();
        drugOrderItem.setQuantity(5);
        drugOrderItem.setDrug(drug);
        drugOrderItem.setDrugOrder(drugOrder);

        DrugOrderItem drugOrderItem1 = new DrugOrderItem();
        drugOrderItem1.setQuantity(10);
        drugOrderItem1.setDrug(drug);
        drugOrderItem1.setDrugOrder(drugOrder);

        drugOrder.getDrugOrderItems().add(drugOrderItem);
        drugOrder.getDrugOrderItems().add(drugOrderItem1);

        DrugOrderItem drugOrderItem2 = new DrugOrderItem();
        drugOrderItem2.setQuantity(20);
        drugOrderItem2.setDrug(drug);
        drugOrderItem2.setDrugOrder(drugOrder1);

        drugOrder1.getDrugOrderItems().add(drugOrderItem2);

        LoginInfo suplierInfo = new LoginInfo();
        suplierInfo.setEmail("sup@sup.com");
        suplierInfo.setPassword("$2y$12$PiwaNad5sOeF2Rv8GbGIB.iSHoDKfoWqYnkCG7RBEfMmViHY/2.bu");


        Supplier supplier = new Supplier();
        supplier.setLocation(location1);
        supplier.setAccountInfo(accountInfo4);
        supplier.setLoginInfo(suplierInfo);
        supplier.getAvailableDrugs().add(drug);
        supplier.setAuthorities(authoritiesPatient);

        Offer offer = new Offer();
        offer.setStatus(OfferStatus.accepted);
        offer.setDeadline(new Date());
        offer.setPrice(10);
        offer.setSupplier(supplier);
        offer.setDrugOrder(drugOrder);

        ExaminationPrice examinationPrice = new ExaminationPrice();
        examinationPrice.setPrice(2500);
        examinationPrice.setExaminationType(ExaminationType.pharmacistConsultations);
        examinationPrice.setPharmacy(pharmacy);
        examinationPrice.setDateOfValidity(dateRange);

        ExaminationPrice examinationPrice2 = new ExaminationPrice();
        examinationPrice2.setPrice(3000.0);
        examinationPrice2.setExaminationType(ExaminationType.dermatologistExamination);
        examinationPrice2.setPharmacy(pharmacy);
        DateRange d = new DateRange();
        d.setStartDate(sdf.parse("2021-10-01"));
        d.setEndDate(sdf.parse("2021-11-01"));
        examinationPrice2.setDateOfValidity(dateRange);

        ExaminationPrice examinationPrice3 = new ExaminationPrice();
        examinationPrice3.setPrice(3500.0);
        examinationPrice3.setExaminationType(ExaminationType.dermatologistExamination);
        examinationPrice3.setPharmacy(pharmacy4);
        examinationPrice3.setDateOfValidity(dateRange);


        ExaminationPrice examinationPrice4 = new ExaminationPrice();
        examinationPrice4.setPrice(2700.0);
        examinationPrice4.setExaminationType(ExaminationType.pharmacistConsultations);
        examinationPrice4.setPharmacy(pharmacy4);
        examinationPrice4.setDateOfValidity(dateRange);

        ExaminationPrice examinationPrice5 = new ExaminationPrice();
        examinationPrice5.setPrice(2550.0);
        examinationPrice5.setExaminationType(ExaminationType.pharmacistConsultations);
        examinationPrice5.setPharmacy(pharmacy5);
        examinationPrice5.setDateOfValidity(dateRange);

        ExaminationPrice examinationPrice6 = new ExaminationPrice();
        examinationPrice6.setPrice(3100.0);
        examinationPrice6.setExaminationType(ExaminationType.dermatologistExamination);
        examinationPrice6.setPharmacy(pharmacy5);
        examinationPrice6.setDateOfValidity(dateRange);

        Examination examination = new Examination();
        examination.setEmployee(pharmacist);
        //examination.setPatient(patient);
        //examination.setExaminationPrice(examinationPrice);
        //examination.setDateOfExamination(new Date());
        examination.setExaminationType(ExaminationType.pharmacistConsultations);
        examination.setExaminationPrice(examinationPrice);
        examination.setDateOfExamination(sdf.parse("2021-05-05"));
        examination.setTimeOfExamination(LocalTime.of(13,45));
        examination.setDuration(45);
        examination.setPharmacy(pharmacy);

        Examination examinationDerm = new Examination();
        examinationDerm.setEmployee(dermatologistPharmacy1);
        //examination.setPatient(patient);
        //examination.setExaminationPrice(examinationPrice);
        //examination.setDateOfExamination(new Date());
        examinationDerm.setExaminationPrice(examinationPrice2);
        examinationDerm.setDateOfExamination(sdf.parse("2021-05-05"));
        examinationDerm.setExaminationType(ExaminationType.dermatologistExamination);
        examinationDerm.setTimeOfExamination(LocalTime.of(11,45));
        examinationDerm.setDuration(45);
        examinationDerm.setPharmacy(pharmacy);

        Examination examinationDerm1 = new Examination();
        examinationDerm1.setEmployee(dermatologist);
        //examination.setPatient(patient);
        //examination.setExaminationPrice(examinationPrice);
        //examination.setDateOfExamination(new Date());
        examinationDerm1.setExaminationPrice(examinationPrice2);
        examinationDerm1.setExaminationType(ExaminationType.dermatologistExamination);
        examinationDerm1.setDateOfExamination(sdf.parse("2021-05-05"));
        examinationDerm1.setTimeOfExamination(LocalTime.of(12,45));
        examinationDerm1.setDuration(45);
        examinationDerm1.setPharmacy(pharmacy);

        Examination examination2 = new Examination();
        examination2.setEmployee(pharmacistA);
        examination2.setExaminationType(ExaminationType.pharmacistConsultations);
        examination2.setExaminationPrice(examinationPrice2);
        examination2.setDateOfExamination(sdf.parse("2021-05-05"));
        examination2.setTimeOfExamination(LocalTime.of(13,45));
        examination2.setDuration(45);
        examination2.setPharmacy(pharmacy);

        Examination examination3 = new Examination();
        examination3.setEmployee(pharmacistA);
        examination3.setExaminationType(ExaminationType.pharmacistConsultations);
        examination3.setExaminationPrice(examinationPrice5);
        examination3.setDateOfExamination(sdf.parse("2021-02-25"));
        examination3.setTimeOfExamination(LocalTime.of(10,30));
        examination3.setDuration(45);
        examination3.setPharmacy(pharmacy5);

        Examination examination4 = new Examination();
        examination4.setEmployee(pharmacist);
        examination4.setExaminationType(ExaminationType.pharmacistConsultations);
        examination4.setExaminationPrice(examinationPrice4);
        examination4.setDateOfExamination(sdf.parse("2021-02-15"));
        examination4.setTimeOfExamination(LocalTime.of(10,30));
        examination4.setDuration(35);
        examination4.setPharmacy(pharmacy4);



        pharmacy.getExaminationPriceList().add(examinationPrice2);
        pharmacy.getExaminationPriceList().add(examinationPrice);
        pharmacy.getExaminations().add(examination);
        pharmacy.getExaminations().add(examination2);
        pharmacy.getExaminations().add(examinationDerm);
        pharmacy.getExaminations().add(examinationDerm1);
        pharmacy4.getExaminationPriceList().add(examinationPrice3);
        pharmacy4.getExaminationPriceList().add(examinationPrice4);
        pharmacy4.getExaminations().add(examination4);
        pharmacy5.getExaminationPriceList().add(examinationPrice5);
        pharmacy5.getExaminationPriceList().add(examinationPrice6);
        pharmacy5.getExaminations().add(examination3);


        Vacation vacation = new Vacation();
        vacation.setEmployee(pharmacist);
        vacation.setDateRange(dateRange);
        vacation.setPharmacy(pharmacist.getPharmacy());
        pharmacist.getVacations().add(vacation);

        DrugInPharmacy drugInPharmacy1 = new DrugInPharmacy();
        drugInPharmacy1.setPharmacy(pharmacy2);
        drugInPharmacy1.setDrug(drug1);
        drugInPharmacy1.setQuantity(12);
        pharmacy2.getDrugs().add(drugInPharmacy1);

        DrugInPharmacy drugInPharmacy3 = new DrugInPharmacy();
        drugInPharmacy3.setPharmacy(pharmacy);
        drugInPharmacy3.setDrug(drug3);
        drugInPharmacy3.setQuantity(78);
        pharmacy.getDrugs().add(drugInPharmacy3);


        DrugInPharmacy drugInPharmacy4 = new DrugInPharmacy();
        drugInPharmacy4.setPharmacy(pharmacy4);
        drugInPharmacy4.setDrug(drug);
        drugInPharmacy4.setQuantity(10);
        pharmacy4.getDrugs().add(drugInPharmacy4);


        patient.setAuthorities(authorities);
        pharmacyAdministrator.setAuthorities(authorities2);
        dermatologist.setAuthorities(authoritiesDerm);
        dermatologistPharmacy1.setAuthorities(authoritiesDerm);
        pharmacist.setAuthorities(authoritiPharmacist);
        pharmacistA.setAuthorities(authoritiPharmacist);
        supplier.setAuthorities(supsss);

        Complaint complaint = new Complaint();
        complaint.setPatient(patient);
        complaint.setContent("Very bad man!");
        complaint.setStatusOfComplaint(StatusOfComplaint.no_answered);
        complaint.setPharmacy(pharmacy);
        complaint.setMedicalStuff(null);


        em.getTransaction().begin();
        em.persist(loyaltyProgram);
        em.persist(a);
        em.persist(pa);
        em.persist(derm);
        em.persist(sa);
        em.persist(su);
        em.persist(role_pharmacist);
        em.persist(country);
        em.persist(city);
        em.persist(city1);
        em.persist(location);
        em.persist(location1);
        em.persist(location2);
        em.persist(contraindication1);
        em.persist(contraindication2);
        em.persist(ingredient1);
        em.persist(ingredient2);
        em.persist(ingredient3);
        em.persist(locationSysAdmin);
        em.persist(manufacturer);
        em.persist(drugIngreditent);
        em.persist(drug);
        em.persist(drug1);
        em.persist(drug3);
        em.persist(drug5);
        em.persist(drug6);
        em.persist(examinationPrice);
        em.persist(pharmacy);
        em.persist(pharmacy3);
        em.persist(drugInPharmacy);
        em.persist(dermatologist);
        em.persist(dermatologistPharmacy1);
        em.persist(der);
        em.persist(systemAdministrator);
        em.persist(drugPrice);

        em.persist(patient);
        em.persist(drugMarks);
        em.persist(pharmacy2);
        em.persist(pharmacy4);
        em.persist(pharmacy5);
        em.persist(pharmacy6);
        em.persist(pharmacy7);
        em.persist(pharmacy8);
        em.persist(drugInPharmacy6);
        em.persist(drugInPharmacy7);
        em.persist(drugPrice2);
        em.persist(drugPrice3);
        em.persist(drugPrice4);
        em.persist(eRecipe);
        em.persist(eRecipe1);
        em.persist(eRecipe2);
        em.persist(drugReservation);
       // em.persist(workTime);
        em.persist(pharmacistA);
        em.persist(pharmacist);
       // em.persist(workTime1);
        em.persist(pharmacyAdministrator);
        em.persist(drugOrder);
        em.persist(drugOrder1);
        em.persist(supplier);
        em.persist(offer);
        em.persist(drug2);
        em.persist(drug4);

        em.persist(drug7);
        em.persist(drugInPharmacy1);
        em.persist(drugInPharmacy4);
        em.persist(drugInPharmacy3);
        em.persist(complaint);

        em.getTransaction().commit();
        em.close();

        System.exit(0);
    }
}


