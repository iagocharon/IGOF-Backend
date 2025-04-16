package com.iagocharon.IGOF.Service;

import com.iagocharon.IGOF.Entity.Doctor;
import com.iagocharon.IGOF.Entity.UltrasoundDoctor;
import com.iagocharon.IGOF.Entity.WorkSchedule;
import com.iagocharon.IGOF.Repository.DoctorRepository;
import com.iagocharon.IGOF.Repository.UltrasoundDoctorRepository;
import com.iagocharon.IGOF.Repository.WorkScheduleRepository;
import jakarta.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class WorkScheduleService {

  @Autowired
  private WorkScheduleRepository workScheduleRepository;

  @Autowired
  private DoctorRepository doctorRepository;

  @Autowired
  private UltrasoundDoctorRepository ultrasoundDoctorRepository;

  public WorkSchedule save(WorkSchedule workSchedule) {
    return workScheduleRepository.save(workSchedule);
  }

  public Optional<WorkSchedule> getById(UUID id) {
    return workScheduleRepository.findById(id);
  }

  public boolean existsById(UUID id) {
    return workScheduleRepository.existsById(id);
  }

  public void delete(WorkSchedule workSchedule) {
    Doctor doctor = workSchedule.getDoctor();
    if (doctor != null) {
      doctor.removeWorkSchedule(workSchedule);
      doctorRepository.save(doctor);
    } else {
      UltrasoundDoctor ultrasoundDoctor = workSchedule.getUltrasoundDoctor();
      if (ultrasoundDoctor != null) {
        ultrasoundDoctor.removeWorkSchedule(workSchedule);
        ultrasoundDoctorRepository.save(ultrasoundDoctor);
      }
    }

    workScheduleRepository.delete(workSchedule);
  }

  public void deleteById(UUID id) {
    WorkSchedule workSchedule = workScheduleRepository.findById(id).get();
    Doctor doctor = workSchedule.getDoctor();
    if (doctor != null) {
      doctor.removeWorkSchedule(workSchedule);
      doctorRepository.save(doctor);
    } else {
      UltrasoundDoctor ultrasoundDoctor = workSchedule.getUltrasoundDoctor();
      if (ultrasoundDoctor != null) {
        ultrasoundDoctor.removeWorkSchedule(workSchedule);
        ultrasoundDoctorRepository.save(ultrasoundDoctor);
      }
    }
    workScheduleRepository.deleteById(id);
  }

  public List<WorkSchedule> findByDoctorIdAndDate(
    UUID doctorId,
    LocalDate date
  ) {
    return workScheduleRepository.findByDoctorIdAndDate(doctorId, date);
  }

  public List<WorkSchedule> findDoctorBetweenDates(
    UUID doctorId,
    LocalDate startDate,
    LocalDate endDate
  ) {
    return workScheduleRepository.findByDoctorIdAndDateBetween(
      doctorId,
      startDate,
      endDate
    );
  }

  public List<WorkSchedule> findByUltrasoundDoctorIdAndDate(
    UUID ultrasoundDoctorId,
    LocalDate date
  ) {
    return workScheduleRepository.findByUltrasoundDoctorIdAndDate(
      ultrasoundDoctorId,
      date
    );
  }

  public List<WorkSchedule> findUltrasoundDoctorBetweenDates(
    UUID ultrasoundDoctorId,
    LocalDate startDate,
    LocalDate endDate
  ) {
    return workScheduleRepository.findByUltrasoundDoctorIdAndDateBetween(
      ultrasoundDoctorId,
      startDate,
      endDate
    );
  }

  public List<WorkSchedule> createSchedulesInInterval(
    LocalDate startDate,
    LocalDate endDate,
    DayOfWeek dayOfWeek,
    WorkSchedule workScheduleTemplate
  ) {
    List<WorkSchedule> schedules = new ArrayList<>();
    LocalDate date = startDate;
    while (!date.isAfter(endDate)) {
      if (date.getDayOfWeek() == dayOfWeek) {
        WorkSchedule newSchedule = new WorkSchedule();
        newSchedule.setDate(date);
        newSchedule.setStart(workScheduleTemplate.getStart());
        newSchedule.setEnd(workScheduleTemplate.getEnd());
        newSchedule.setDoctor(workScheduleTemplate.getDoctor());
        schedules.add(workScheduleRepository.save(newSchedule));
      }
      date = date.plusDays(1);
    }
    return schedules;
  }
}
