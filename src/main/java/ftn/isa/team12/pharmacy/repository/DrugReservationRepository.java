package ftn.isa.team12.pharmacy.repository;

import ftn.isa.team12.pharmacy.domain.drugs.Drug;
import ftn.isa.team12.pharmacy.domain.drugs.DrugReservation;
import ftn.isa.team12.pharmacy.domain.enums.ReservationStatus;
import ftn.isa.team12.pharmacy.domain.pharmacy.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface DrugReservationRepository extends JpaRepository<DrugReservation, UUID> {

    List<DrugReservation> findAllByPatientUserId(UUID userId);

    @Query("select reservation from DrugReservation reservation where reservation.drug_reservation_id = ?1 and reservation.pharmacy.id = ?2")
    DrugReservation findDrugReservationByIdAndPharmacyId(UUID id, UUID pharmacyId);

    @Query("select reservation from DrugReservation  reservation where reservation.drug_reservation_id = ?1")
    DrugReservation findDrugReservationByDrug_reservation_id(UUID drug_reservation_id);

    @Query("select reservation.pharmacy from DrugReservation reservation where reservation.patient.userId=?1 and reservation.reservationStatus = 2")
    List<Pharmacy> findPharmaciesWherePatientReservedDrugs(UUID patientId);

    @Query("select reservation.drug from DrugReservation reservation where reservation.patient.userId=?1 and reservation.reservationStatus = 2")
    List<Drug> findDrugsPatientReserved(UUID patientId);

    @Query("select reservation from DrugReservation reservation where reservation.drug_reservation_id = ?1")
    DrugReservation findDrugReservationById(UUID id);

    List<DrugReservation> findAllByDrugDrugIdAndPharmacyIdAndReservationStatus(UUID drugId, UUID pharmacyId, ReservationStatus reservationStatus);

    @Query("select reservation from DrugReservation reservation where reservation.pharmacy=?1 and reservation.reservationDateRange.endDate > ?2 and reservation.reservationDateRange.endDate <=?3    and reservation.reservationStatus = 2")
    List<DrugReservation> getAllForReports(Pharmacy pharmacy, Date start, Date end);


    @Query("select ex from DrugReservation  ex where ex.pharmacy = ?1 and ex.reservationDateRange.endDate = ?2 and ex.reservationStatus = 2")
    List<DrugReservation> getALlDrugReservationPerDay(Pharmacy pharmacy, Date start);


    @Query("select reservation from DrugReservation reservation where reservation.reservationDateRange.endDate < ?1 and reservation.reservationStatus = 0")
    List<DrugReservation> findAllBefore(Date today);

}
