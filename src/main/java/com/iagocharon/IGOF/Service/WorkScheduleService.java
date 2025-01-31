package com.iagocharon.IGOF.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iagocharon.IGOF.Entity.Doctor;
import com.iagocharon.IGOF.Entity.WorkSchedule;
import com.iagocharon.IGOF.Repository.DoctorRepository;
import com.iagocharon.IGOF.Repository.WorkScheduleRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class WorkScheduleService {

  @Autowired
  private WorkScheduleRepository workScheduleRepository;

  @Autowired
  private DoctorRepository doctorRepository;

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
    workScheduleRepository.delete(workSchedule);
  }

  public void deleteById(UUID id) {
    WorkSchedule workSchedule = workScheduleRepository.findById(id).get();
    Doctor doctor = workSchedule.getDoctor();
    doctor.removeWorkSchedule(workSchedule);
    doctorRepository.save(doctor);
    workScheduleRepository.deleteById(id);
  }

  public List<WorkSchedule> findBetweenDates(UUID doctorId, LocalDate startDate,
      LocalDate endDate) {
    return workScheduleRepository.findByDoctorIdAndDateBetween(doctorId, startDate, endDate);
  }

  public List<WorkSchedule> createSchedulesInInterval(LocalDate startDate, LocalDate endDate,
      DayOfWeek dayOfWeek, WorkSchedule workScheduleTemplate) {
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
