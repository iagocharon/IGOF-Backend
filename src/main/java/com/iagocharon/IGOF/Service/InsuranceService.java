package com.iagocharon.IGOF.Service;

import com.iagocharon.IGOF.Dto.Projections.InsuranceProjection;
import com.iagocharon.IGOF.Entity.Appointment;
import com.iagocharon.IGOF.Entity.Doctor;
import com.iagocharon.IGOF.Entity.Insurance;
import com.iagocharon.IGOF.Entity.InsuranceParent;
import com.iagocharon.IGOF.Entity.UltrasoundAppointment;
import com.iagocharon.IGOF.Entity.UltrasoundDoctor;
import com.iagocharon.IGOF.Repository.AppointmentRepository;
import com.iagocharon.IGOF.Repository.DoctorRepository;
import com.iagocharon.IGOF.Repository.InsuranceParentRepository;
import com.iagocharon.IGOF.Repository.InsuranceRepository;
import com.iagocharon.IGOF.Repository.PatientRepository;
import com.iagocharon.IGOF.Repository.UltrasoundAppointmentRepository;
import com.iagocharon.IGOF.Repository.UltrasoundDoctorRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class InsuranceService {

  @Autowired
  InsuranceRepository insuranceRepository;

  @Autowired
  DoctorRepository doctorRepository;

  @Autowired
  PatientRepository patientRepository;

  @Autowired
  UltrasoundDoctorRepository ultrasoundDoctorRepository;

  @Autowired
  AppointmentRepository appointmentRepository;

  @Autowired
  UltrasoundAppointmentRepository ultrasoundAppointmentRepository;

  @Autowired
  InsuranceParentRepository insuranceParentRepository;

  public boolean existsById(UUID id) {
    return insuranceRepository.existsById(id);
  }

  public boolean existsByName(String name) {
    return insuranceRepository.existsByName(name);
  }

  public Optional<Insurance> getById(UUID id) {
    return insuranceRepository.findById(id);
  }

  public Optional<Insurance> getByName(String name) {
    return insuranceRepository.findByName(name);
  }

  public Insurance save(Insurance insurance) {
    return insuranceRepository.save(insurance);
  }

  public void delete(Insurance insurance) {
    for (Doctor doctor : insurance.getDoctors()) {
      doctor.removeInsurance(insurance);
      doctorRepository.save(doctor);
    }
    for (UltrasoundDoctor ultrasoundDoctor : insurance.getUltrasoundDoctors()) {
      ultrasoundDoctor.removeInsurance(insurance);
      ultrasoundDoctorRepository.save(ultrasoundDoctor);
    }

    for (Appointment appointment : insurance.getAppointments()) {
      appointment.setInsurance(null);
      appointmentRepository.save(appointment);
    }

    for (UltrasoundAppointment ultrasoundAppointment : insurance.getUltrasoundAppointments()) {
      ultrasoundAppointment.setInsurance(null);
      ultrasoundAppointmentRepository.save(ultrasoundAppointment);
    }

    InsuranceParent insuranceParent = insurance.getInsuranceParent();
    if (insuranceParent != null) {
      insuranceParent.removeInsurance(insurance);
      insuranceParentRepository.save(insuranceParent);
    }

    insuranceRepository.delete(insurance);
  }

  public void deleteById(UUID id) {
    Insurance insurance = insuranceRepository.findById(id).get();
    for (Doctor doctor : insurance.getDoctors()) {
      doctor.removeInsurance(insurance);
      doctorRepository.save(doctor);
    }
    for (UltrasoundDoctor ultrasoundDoctor : insurance.getUltrasoundDoctors()) {
      ultrasoundDoctor.removeInsurance(insurance);
      ultrasoundDoctorRepository.save(ultrasoundDoctor);
    }

    for (Appointment appointment : insurance.getAppointments()) {
      appointment.setInsurance(null);
      appointmentRepository.save(appointment);
    }

    for (UltrasoundAppointment ultrasoundAppointment : insurance.getUltrasoundAppointments()) {
      ultrasoundAppointment.setInsurance(null);
      ultrasoundAppointmentRepository.save(ultrasoundAppointment);
    }

    InsuranceParent insuranceParent = insurance.getInsuranceParent();
    if (insuranceParent != null) {
      insuranceParent.removeInsurance(insurance);
      insuranceParentRepository.save(insuranceParent);
    }
    insuranceRepository.deleteById(id);
  }

  public List<InsuranceProjection> getAll() {
    return insuranceRepository.findAllProjectedBy();
  }

  public List<Insurance> getAllInsurances() {
    return insuranceRepository.findAll();
  }

  public List<Insurance> saveAll(List<Insurance> insurances) {
    return insuranceRepository.saveAll(insurances);
  }
}
