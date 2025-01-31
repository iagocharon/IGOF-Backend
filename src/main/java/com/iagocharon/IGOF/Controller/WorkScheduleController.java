package com.iagocharon.IGOF.Controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iagocharon.IGOF.Dto.Message;
import com.iagocharon.IGOF.Dto.WorkScheduleDto;
import com.iagocharon.IGOF.Entity.Doctor;
import com.iagocharon.IGOF.Entity.WorkSchedule;
import com.iagocharon.IGOF.Service.DoctorService;
import com.iagocharon.IGOF.Service.WorkScheduleService;

@RestController
@RequestMapping("/api/workschedule")
public class WorkScheduleController {

  @Autowired
  WorkScheduleService workScheduleService;

  @Autowired
  DoctorService doctorService;

  @GetMapping("/list")
  public ResponseEntity<?> getWorkSchedulesBetweenDates(@RequestParam String doctorId,
      @RequestParam String from, @RequestParam String to) {
    DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    LocalDate startDate = LocalDate.parse(from, formatter);
    LocalDate endDate = LocalDate.parse(to, formatter);

    if (!doctorService.existsById(UUID.fromString(doctorId))) {
      return new ResponseEntity<>(new Message("Doctor not found."), HttpStatus.NOT_FOUND);
    }
    List<WorkSchedule> schedules =
        workScheduleService.findBetweenDates(UUID.fromString(doctorId), startDate, endDate);
    return ResponseEntity.ok(schedules);
  }

  @PostMapping("/create")
  public ResponseEntity<?> addWorkSchedule(@RequestBody WorkScheduleDto workScheduleDto) {
    UUID doctorId = UUID.fromString(workScheduleDto.getDoctorId());
    if (!doctorService.existsById(doctorId)) {
      return new ResponseEntity<>(new Message("Doctor not found."), HttpStatus.NOT_FOUND);
    }

    Doctor doctor = doctorService.getById(doctorId).orElse(null);
    if (doctor == null) {
      return new ResponseEntity<>(new Message("Doctor not found."), HttpStatus.NOT_FOUND);
    }

    DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    LocalTime start = LocalTime.parse(workScheduleDto.getStart(), formatter);
    LocalTime end = LocalTime.parse(workScheduleDto.getEnd(), formatter);
    LocalDate date = LocalDate.parse(workScheduleDto.getDate(), formatter);


    WorkSchedule newSchedule = new WorkSchedule();
    newSchedule.setDate(date);
    newSchedule.setStart(start);
    newSchedule.setEnd(end);
    newSchedule.setDoctor(doctor);

    WorkSchedule savedSchedule = workScheduleService.save(newSchedule);

    return new ResponseEntity<>(new Message("Work schedule added successfully."), HttpStatus.OK);
  }

  @PostMapping("/create-multiple")
  public ResponseEntity<?> addMultipleWorkSchedules(@RequestParam String doctorId,
      @RequestParam String from, @RequestParam String to, @RequestParam String start,
      @RequestParam String end, @RequestParam(required = false) DayOfWeek dayOfWeek) {
    DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    LocalDate startDate = LocalDate.parse(from, formatter);
    LocalDate endDate = LocalDate.parse(to, formatter);
    LocalTime startTime = LocalTime.parse(start, formatter);
    LocalTime endTime = LocalTime.parse(end, formatter);

    if (!doctorService.existsById(UUID.fromString(doctorId))) {
      return new ResponseEntity<>(new Message("Doctor not found."), HttpStatus.NOT_FOUND);
    }

    Doctor doctor = doctorService.getById(UUID.fromString(doctorId)).orElse(null);
    if (doctor == null) {
      return new ResponseEntity<>(new Message("Doctor not found."), HttpStatus.NOT_FOUND);
    }

    for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
      if (dayOfWeek == null || date.getDayOfWeek() == dayOfWeek) {
        WorkSchedule newSchedule = new WorkSchedule();
        newSchedule.setDate(date);
        newSchedule.setStart(startTime);
        newSchedule.setEnd(endTime);
        newSchedule.setDoctor(doctor);
        workScheduleService.save(newSchedule);
      }
    }

    return new ResponseEntity<>(new Message("Work schedules added successfully."), HttpStatus.OK);
  }

  @PostMapping("/interval")
  public ResponseEntity<?> createWorkSchedulesInInterval(@RequestParam UUID doctorId,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
      @RequestParam DayOfWeek dayOfWeek, @RequestBody WorkScheduleDto workScheduleDto) {
    if (!doctorService.existsById(doctorId)) {
      return new ResponseEntity<>(new Message("Doctor not found."), HttpStatus.NOT_FOUND);
    }

    Doctor doctor = doctorService.getById(doctorId).orElse(null);
    if (doctor == null) {
      return new ResponseEntity<>(new Message("Doctor not found."), HttpStatus.NOT_FOUND);
    }

    WorkSchedule workScheduleTemplate = new WorkSchedule();
    workScheduleTemplate.setStart(LocalTime.parse(workScheduleDto.getStart()));
    workScheduleTemplate.setEnd(LocalTime.parse(workScheduleDto.getEnd()));
    workScheduleTemplate.setDoctor(doctor);

    List<WorkSchedule> schedules = workScheduleService.createSchedulesInInterval(startDate, endDate,
        dayOfWeek, workScheduleTemplate);
    return ResponseEntity.ok(schedules);
  }

  @DeleteMapping(value = "delete")
  public ResponseEntity<?> delete(@RequestParam String id) {
    if (!workScheduleService.existsById(UUID.fromString(id))) {
      return new ResponseEntity<>(new Message("No work schedule found."), HttpStatus.NOT_FOUND);
    }
    workScheduleService.deleteById(UUID.fromString(id));
    return new ResponseEntity<>(new Message("Work schedule deleted."), HttpStatus.OK);
  }

}
