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

import com.iagocharon.IGOF.Dto.InsuranceDto;
import com.iagocharon.IGOF.Dto.Message;
import com.iagocharon.IGOF.Entity.Insurance;
import com.iagocharon.IGOF.Service.InsuranceService;
import com.iagocharon.IGOF.Service.PatientService;

@RestController
@RequestMapping("/api/insurance")
public class InsuranceController {

  @Autowired
  InsuranceService insuranceService;

  @Autowired
  PatientService patientService;

  @GetMapping(value = "list")
  public ResponseEntity<?> list() {
    List<Insurance> insurances = insuranceService.getAll();
    return new ResponseEntity<>(insurances, HttpStatus.OK);
  }

  @GetMapping(value = "")
  public ResponseEntity<?> get(@RequestParam String id) {
    if (!insuranceService.existsById(UUID.fromString(id))) {
      return new ResponseEntity<>(
        new Message("No insurance found."),
        HttpStatus.NOT_FOUND
      );
    }
    Insurance insurance = insuranceService.getById(UUID.fromString(id)).get();
    return new ResponseEntity<>(insurance, HttpStatus.OK);
  }

  @PostMapping(value = "create")
  public ResponseEntity<?> create(@RequestBody InsuranceDto insuranceDto) {
    if (insuranceService.existsByName(insuranceDto.getName())) {
      return new ResponseEntity<>(
        new Message("Insurance already exists."),
        HttpStatus.BAD_REQUEST
      );
    }
    Insurance insurance = new Insurance();
    insurance.setName(insuranceDto.getName());
    insurance.setDailyLimit(insuranceDto.getDailyLimit());
    insurance = insuranceService.save(insurance);
    return new ResponseEntity<>(insurance, HttpStatus.OK);
  }

  @PutMapping(value = "update")
  public ResponseEntity<?> update(
    @RequestParam String id,
    @RequestBody InsuranceDto insuranceDto
  ) {
    if (!insuranceService.existsById(UUID.fromString(id))) {
      return new ResponseEntity<>(
        new Message("No insurance found."),
        HttpStatus.NOT_FOUND
      );
    }
    Insurance insurance = insuranceService.getById(UUID.fromString(id)).get();
    insurance.setName(insuranceDto.getName());
    insurance.setDailyLimit(insuranceDto.getDailyLimit());
    insurance = insuranceService.save(insurance);
    return new ResponseEntity<>(insurance, HttpStatus.OK);
  }

  @DeleteMapping(value = "delete")
  public ResponseEntity<?> delete(@RequestParam String id) {
    if (!insuranceService.existsById(UUID.fromString(id))) {
      return new ResponseEntity<>(
        new Message("No insurance found."),
        HttpStatus.NOT_FOUND
      );
    }
    Insurance insurance = insuranceService.getById(UUID.fromString(id)).get();
    insuranceService.delete(insurance);
    return new ResponseEntity<>(
      new Message("Insurance deleted."),
      HttpStatus.OK
    );
  }
}
