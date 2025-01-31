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
import com.iagocharon.IGOF.Dto.PaymentMethodDto;
import com.iagocharon.IGOF.Entity.PaymentMethod;
import com.iagocharon.IGOF.Service.PaymentMethodService;

@RestController
@RequestMapping("/api/payment-method")
public class PaymentMethodController {

  @Autowired
  PaymentMethodService paymentMethodService;

  @GetMapping(value = "list")
  public ResponseEntity<?> list() {
    List<PaymentMethod> paymentMethods = paymentMethodService.getAll();
    return new ResponseEntity<>(paymentMethods, HttpStatus.OK);
  }

  @GetMapping(value = "")
  public ResponseEntity<?> getById(@RequestParam String id) {
    if (!paymentMethodService.existsById(UUID.fromString(id))) {
      return new ResponseEntity<>(new Message("No payment method found."), HttpStatus.NOT_FOUND);
    }

    PaymentMethod paymentMethod = paymentMethodService.getById(UUID.fromString(id)).get();
    return new ResponseEntity<>(paymentMethod, HttpStatus.OK);
  }

  @PostMapping(value = "create")
  public ResponseEntity<?> create(@RequestBody PaymentMethodDto paymentMethodDto) {
    if (paymentMethodService.existsByName(paymentMethodDto.getName())) {
      return new ResponseEntity<>(new Message("Payment method already exists."),
          HttpStatus.BAD_REQUEST);
    }
    PaymentMethod paymentMethod = new PaymentMethod();
    paymentMethod.setName(paymentMethodDto.getName());
    paymentMethodService.save(paymentMethod);

    return new ResponseEntity<>(new Message("Payment method created successfully."), HttpStatus.OK);
  }

  @PutMapping(value = "update")
  public ResponseEntity<?> update(@RequestParam String id,
      @RequestBody PaymentMethodDto paymentMethodDto) {
    if (!paymentMethodService.existsById(UUID.fromString(id))) {
      return new ResponseEntity<>(new Message("No payment method found."), HttpStatus.NOT_FOUND);
    }

    PaymentMethod paymentMethod = paymentMethodService.getById(UUID.fromString(id)).get();
    paymentMethod.setName(paymentMethodDto.getName());
    paymentMethodService.save(paymentMethod);

    return new ResponseEntity<>(new Message("Payment method updated successfully."), HttpStatus.OK);
  }

  @DeleteMapping(value = "delete")
  public ResponseEntity<?> delete(@RequestParam String id) {
    if (!paymentMethodService.existsById(UUID.fromString(id))) {
      return new ResponseEntity<>(new Message("No payment method found."), HttpStatus.NOT_FOUND);
    }

    PaymentMethod paymentMethod = paymentMethodService.getById(UUID.fromString(id)).get();
    paymentMethodService.delete(paymentMethod);

    return new ResponseEntity<>(new Message("Payment method deleted successfully."), HttpStatus.OK);
  }

}
