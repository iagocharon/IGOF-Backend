package com.iagocharon.IGOF.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iagocharon.IGOF.Dto.Projections.AppointmentProjection;
import com.iagocharon.IGOF.Entity.Appointment;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {

        Optional<Appointment> findById(UUID id);

        boolean existsById(UUID id);

        @Query("SELECT a FROM Appointment a WHERE a.doctor.id = :doctorId AND a.start >= :startDate AND a.end <= :endDate AND a.status <> 'CANCELLED' ORDER BY a.start ASC")
        List<AppointmentProjection> findAllByDoctorAndDateRange(@Param("doctorId") UUID doctorId,
                        @Param("startDate") ZonedDateTime startDate,
                        @Param("endDate") ZonedDateTime endDate);

        @Query("SELECT a FROM Appointment a WHERE a.patient.id = :patientId AND a.start >= :startDate AND a.end <= :endDate ORDER BY a.start ASC")
        List<AppointmentProjection> findAllByPatientAndDateRange(@Param("patientId") UUID patientId,
                        @Param("startDate") ZonedDateTime startDate,
                        @Param("endDate") ZonedDateTime endDate);
}
