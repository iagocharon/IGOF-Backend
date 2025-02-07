package com.iagocharon.IGOF.Repository;

import com.iagocharon.IGOF.Dto.Projections.UltrasoundAppointmentProjection;
import com.iagocharon.IGOF.Entity.UltrasoundAppointment;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UltrasoundAppointmentRepository
  extends JpaRepository<UltrasoundAppointment, UUID> {
  Optional<UltrasoundAppointment> findById(UUID id);
  boolean existsById(UUID id);

  @Query(
    "SELECT a FROM Appointment a WHERE a.ultrasoundDoctor.id = :doctorId AND a.start >= :startDate AND a.end <= :endDate AND a.status <> 'CANCELLED' ORDER BY a.start ASC"
  )
  List<UltrasoundAppointmentProjection> findAllByDoctorAndDateRange(
    UUID doctorId,
    ZonedDateTime startDate,
    ZonedDateTime endDate
  );

  @Query(
    "SELECT a FROM Appointment a WHERE a.patient.id = :patientId AND a.start >= :startDate AND a.end <= :endDate ORDER BY a.start ASC"
  )
  List<UltrasoundAppointmentProjection> findAllByPatientAndDateRange(
    @Param("patientId") UUID patientId,
    @Param("startDate") ZonedDateTime startDate,
    @Param("endDate") ZonedDateTime endDate
  );
}
