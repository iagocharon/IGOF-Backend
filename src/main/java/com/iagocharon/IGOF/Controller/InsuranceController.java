package com.iagocharon.IGOF.Controller;

import com.iagocharon.IGOF.Dto.InsuranceDto;
import com.iagocharon.IGOF.Dto.InsuranceParentDto;
import com.iagocharon.IGOF.Dto.Message;
import com.iagocharon.IGOF.Dto.Projections.InsuranceParentProjection;
import com.iagocharon.IGOF.Dto.Projections.InsuranceProjection;
import com.iagocharon.IGOF.Entity.Insurance;
import com.iagocharon.IGOF.Entity.InsuranceParent;
import com.iagocharon.IGOF.Service.InsuranceParentService;
import com.iagocharon.IGOF.Service.InsuranceService;
import com.iagocharon.IGOF.Service.PatientService;
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
@RequestMapping("/api/insurance")
public class InsuranceController {

  @Autowired
  InsuranceService insuranceService;

  @Autowired
  PatientService patientService;

  @Autowired
  InsuranceParentService insuranceParentService;

  @GetMapping(value = "list-parents")
  public ResponseEntity<?> listParents() {
    List<InsuranceParentProjection> insurances =
      insuranceParentService.getAll();
    return new ResponseEntity<>(insurances, HttpStatus.OK);
  }

  @GetMapping(value = "list-insurances")
  public ResponseEntity<?> list() {
    List<InsuranceProjection> insurances = insuranceService.getAll();
    return new ResponseEntity<>(insurances, HttpStatus.OK);
  }

  @GetMapping(value = "parent")
  public ResponseEntity<?> getParent(@RequestParam String id) {
    if (!insuranceParentService.existsById(UUID.fromString(id))) {
      return new ResponseEntity<>(
        new Message("No insurance found."),
        HttpStatus.NOT_FOUND
      );
    }
    InsuranceParent insurance = insuranceParentService
      .getById(UUID.fromString(id))
      .get();
    return new ResponseEntity<>(insurance, HttpStatus.OK);
  }

  @GetMapping(value = "insurance")
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

  @PostMapping(value = "create-parent")
  public ResponseEntity<?> createParent(
    @RequestBody InsuranceParentDto insuranceDto
  ) {
    if (insuranceParentService.existsByName(insuranceDto.getName())) {
      return new ResponseEntity<>(
        new Message("Insurance already exists."),
        HttpStatus.BAD_REQUEST
      );
    }
    InsuranceParent insurance = new InsuranceParent();
    insurance.setName(insuranceDto.getName());
    insurance.setDailyLimit(insuranceDto.getDailyLimit());
    insurance = insuranceParentService.save(insurance);
    return new ResponseEntity<>(insurance, HttpStatus.OK);
  }

  @PostMapping(value = "create-insurance")
  public ResponseEntity<?> create(@RequestBody InsuranceDto insuranceDto) {
    System.out.println(insuranceDto);
    if (insuranceService.existsByName(insuranceDto.getName())) {
      return new ResponseEntity<>(
        new Message("Insurance already exists."),
        HttpStatus.BAD_REQUEST
      );
    }
    if (
      !insuranceParentService.existsById(
        UUID.fromString(insuranceDto.getInsuranceParentId())
      )
    ) {
      return new ResponseEntity<>(
        new Message("No insurance parent found."),
        HttpStatus.NOT_FOUND
      );
    }
    Insurance insurance = new Insurance();
    insurance.setName(insuranceDto.getName());
    insurance = insuranceService.save(insurance);
    InsuranceParent insuranceParent = insuranceParentService
      .getById(UUID.fromString(insuranceDto.getInsuranceParentId()))
      .get();
    insurance.setInsuranceParent(insuranceParent);
    insuranceParent.addInsurance(insurance);
    insuranceParentService.save(insuranceParent);
    insurance = insuranceService.save(insurance);
    return new ResponseEntity<>(insurance, HttpStatus.OK);
  }

  @PutMapping(value = "update-parent")
  public ResponseEntity<?> updateParent(
    @RequestParam String id,
    @RequestBody InsuranceParentDto insuranceDto
  ) {
    if (!insuranceParentService.existsById(UUID.fromString(id))) {
      return new ResponseEntity<>(
        new Message("No insurance found."),
        HttpStatus.NOT_FOUND
      );
    }

    InsuranceParent insurance = insuranceParentService
      .getById(UUID.fromString(id))
      .get();
    insurance.setName(insuranceDto.getName());
    insurance.setDailyLimit(insuranceDto.getDailyLimit());
    insurance = insuranceParentService.save(insurance);
    return new ResponseEntity<>(insurance, HttpStatus.OK);
  }

  @PutMapping(value = "update-insurance")
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
    if (
      !insuranceParentService.existsById(
        UUID.fromString(insuranceDto.getInsuranceParentId())
      )
    ) {
      return new ResponseEntity<>(
        new Message("No insurance parent found."),
        HttpStatus.NOT_FOUND
      );
    }

    Insurance insurance = insuranceService.getById(UUID.fromString(id)).get();
    insurance.setName(insuranceDto.getName());
    insurance = insuranceService.save(insurance);
    return new ResponseEntity<>(insurance, HttpStatus.OK);
  }

  @DeleteMapping(value = "delete-parent")
  public ResponseEntity<?> deleteParent(@RequestParam String id) {
    if (!insuranceParentService.existsById(UUID.fromString(id))) {
      return new ResponseEntity<>(
        new Message("No insurance found."),
        HttpStatus.NOT_FOUND
      );
    }
    InsuranceParent insurance = insuranceParentService
      .getById(UUID.fromString(id))
      .get();
    insuranceParentService.delete(insurance);
    return new ResponseEntity<>(
      new Message("Insurance deleted."),
      HttpStatus.OK
    );
  }

  @DeleteMapping(value = "delete-insurance")
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
