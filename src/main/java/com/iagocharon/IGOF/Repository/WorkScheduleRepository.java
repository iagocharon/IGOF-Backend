package com.iagocharon.IGOF.Repository;

import com.iagocharon.IGOF.Entity.WorkSchedule;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkScheduleRepository
  extends JpaRepository<WorkSchedule, UUID> {
  Optional<WorkSchedule> findById(UUID id);

  boolean existsById(UUID id);

  List<WorkSchedule> findByDoctorIdAndDate(UUID doctorId, LocalDate date);

  List<WorkSchedule> findByDoctorIdAndDateBetween(
    UUID doctorId,
    LocalDate startDate,
    LocalDate endDate
  );

  List<WorkSchedule> findByUltrasoundDoctorIdAndDateBetween(
    UUID ultrasoundDoctorId,
    LocalDate startDate,
    LocalDate endDate
  );
}
