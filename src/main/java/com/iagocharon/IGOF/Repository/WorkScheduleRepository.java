package com.iagocharon.IGOF.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iagocharon.IGOF.Entity.WorkSchedule;

@Repository
public interface WorkScheduleRepository extends JpaRepository<WorkSchedule, UUID> {

        Optional<WorkSchedule> findById(UUID id);

        boolean existsById(UUID id);

        List<WorkSchedule> findByDoctorIdAndDate(UUID doctorId, LocalDate date);

        List<WorkSchedule> findByDoctorIdAndDateBetween(UUID doctorId, LocalDate startDate,
                        LocalDate endDate);

}
