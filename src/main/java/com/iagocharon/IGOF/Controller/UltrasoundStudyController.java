package com.iagocharon.IGOF.Controller;

import com.iagocharon.IGOF.Dto.Message;
import com.iagocharon.IGOF.Dto.Projections.DoctorProjection;
import com.iagocharon.IGOF.Dto.Projections.UltrasoundDoctorProjection;
import com.iagocharon.IGOF.Dto.Projections.UltrasoundStudyProjection;
import com.iagocharon.IGOF.Dto.UltrasoundStudyDto;
import com.iagocharon.IGOF.Entity.UltrasoundStudy;
import com.iagocharon.IGOF.Service.UltrasoundStudyService;
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

@RestController
@RequestMapping("/api/ultrasound-study")
public class UltrasoundStudyController {

  @Autowired
  UltrasoundStudyService ultrasoundStudyService;

  @GetMapping(value = "list")
  public ResponseEntity<?> list() {
    List<UltrasoundStudyProjection> studies = ultrasoundStudyService.getAll();
    return new ResponseEntity<>(studies, HttpStatus.OK);
  }

  @GetMapping(value = "")
  public ResponseEntity<?> get(@RequestParam String id) {
    if (!ultrasoundStudyService.existsById(UUID.fromString(id))) {
      return new ResponseEntity<>(
        new Message("No ultrasound study found."),
        HttpStatus.NOT_FOUND
      );
    }
    UltrasoundStudy study = ultrasoundStudyService
      .getById(UUID.fromString(id))
      .get();
    return new ResponseEntity<>(study, HttpStatus.OK);
  }

  @GetMapping(value = "doctors")
  public ResponseEntity<?> getDoctors(@RequestParam String id) {
    if (!ultrasoundStudyService.existsById(UUID.fromString(id))) {
      return new ResponseEntity<>(
        new Message("No ultrasound study found."),
        HttpStatus.NOT_FOUND
      );
    }
    List<UltrasoundDoctorProjection> doctors =
      ultrasoundStudyService.getDoctors(UUID.fromString(id));
    return new ResponseEntity<>(doctors, HttpStatus.OK);
  }

  @PostMapping(value = "create")
  public ResponseEntity<?> create(
    @RequestBody UltrasoundStudyDto ultrasoundStudyDto
  ) {
    if (ultrasoundStudyService.existsByName(ultrasoundStudyDto.getName())) {
      return new ResponseEntity<>(
        new Message("Ultrasound study already exists."),
        HttpStatus.BAD_REQUEST
      );
    }
    UltrasoundStudy study = new UltrasoundStudy();
    study.setName(ultrasoundStudyDto.getName());
    study = ultrasoundStudyService.save(study);
    return new ResponseEntity<>(study, HttpStatus.CREATED);
  }

  @PutMapping(value = "update")
  public ResponseEntity<?> update(
    @RequestParam String id,
    @RequestBody UltrasoundStudyDto ultrasoundStudyDto
  ) {
    if (!ultrasoundStudyService.existsById(UUID.fromString(id))) {
      return new ResponseEntity<>(
        new Message("No ultrasound study found."),
        HttpStatus.NOT_FOUND
      );
    }
    UltrasoundStudy study = ultrasoundStudyService
      .getById(UUID.fromString(id))
      .get();
    study.setName(ultrasoundStudyDto.getName());
    study = ultrasoundStudyService.save(study);
    return new ResponseEntity<>(study, HttpStatus.OK);
  }

  @DeleteMapping(value = "delete")
  public ResponseEntity<?> delete(@RequestParam String id) {
    if (!ultrasoundStudyService.existsById(UUID.fromString(id))) {
      return new ResponseEntity<>(
        new Message("No ultrasound study found."),
        HttpStatus.NOT_FOUND
      );
    }
    UltrasoundStudy study = ultrasoundStudyService
      .getById(UUID.fromString(id))
      .get();
    ultrasoundStudyService.delete(study);
    return new ResponseEntity<>(
      new Message("Ultrasound study deleted."),
      HttpStatus.OK
    );
  }
}
