package com.iagocharon.IGOF.Controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iagocharon.IGOF.Dto.Message;
import com.iagocharon.IGOF.Dto.Projections.DoctorProjection;
import com.iagocharon.IGOF.Dto.Projections.SpecialtyProjection;
import com.iagocharon.IGOF.Dto.SpecialtyDto;
import com.iagocharon.IGOF.Entity.Specialty;
import com.iagocharon.IGOF.Service.SpecialtyService;

@RestController
@RequestMapping("/api/specialty")
public class SpecialtyController {

  @Autowired
  SpecialtyService specialtyService;

  @GetMapping(value = "list")
  public ResponseEntity<?> list() {
    List<SpecialtyProjection> specialties = specialtyService.getAll();
    return new ResponseEntity<>(specialties, HttpStatus.OK);
  }

  @GetMapping(value = "")
  public ResponseEntity<?> get(@RequestParam String id) {
    if (!specialtyService.existsById(UUID.fromString(id))) {
      return new ResponseEntity<>(new Message("No specialty found."), HttpStatus.NOT_FOUND);
    }
    Specialty specialty = specialtyService.getById(UUID.fromString(id)).get();
    return new ResponseEntity<>(specialty, HttpStatus.OK);
  }

  @GetMapping(value = "doctors")
  public ResponseEntity<?> getDoctors(@RequestParam String id) {
    if (!specialtyService.existsById(UUID.fromString(id))) {
      return new ResponseEntity<>(new Message("No specialty found."), HttpStatus.NOT_FOUND);
    }
    List<DoctorProjection> doctors = specialtyService.getDoctors(UUID.fromString(id));
    return new ResponseEntity<>(doctors, HttpStatus.OK);
  }

  @PostMapping(value = "create")
  public ResponseEntity<?> create(@RequestBody SpecialtyDto specialtyDto) {
    if (specialtyService.existsByName(specialtyDto.getName())) {
      return new ResponseEntity<>(new Message("Specialty already exists."), HttpStatus.BAD_REQUEST);
    }
    Specialty specialty = new Specialty();
    specialty.setName(specialtyDto.getName());
    specialty = specialtyService.save(specialty);
    return new ResponseEntity<>(specialty, HttpStatus.CREATED);
  }

  @PutMapping(value = "update")
  public ResponseEntity<?> update(@RequestParam String id, @RequestBody SpecialtyDto specialtyDto) {
    if (!specialtyService.existsById(UUID.fromString(id))) {
      return new ResponseEntity<>(new Message("No specialty found."), HttpStatus.NOT_FOUND);
    }
    Specialty specialty = specialtyService.getById(UUID.fromString(id)).get();
    specialty.setName(specialtyDto.getName());
    specialty = specialtyService.save(specialty);
    return new ResponseEntity<>(specialty, HttpStatus.OK);
  }

  @DeleteMapping(value = "delete")
  public ResponseEntity<?> delete(@RequestParam String id) {
    if (!specialtyService.existsById(UUID.fromString(id))) {
      return new ResponseEntity<>(new Message("No specialty found."), HttpStatus.NOT_FOUND);
    }
    Specialty specialty = specialtyService.getById(UUID.fromString(id)).get();
    specialtyService.delete(specialty);
    return new ResponseEntity<>(new Message("Specialty deleted."), HttpStatus.OK);
  }


}
