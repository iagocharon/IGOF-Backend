package com.iagocharon.IGOF;

import com.iagocharon.IGOF.Entity.Doctor;
import com.iagocharon.IGOF.Entity.Insurance;
import com.iagocharon.IGOF.Entity.MedicalRecord;
import com.iagocharon.IGOF.Entity.Patient;
import com.iagocharon.IGOF.Entity.PaymentMethod;
import com.iagocharon.IGOF.Entity.Role;
import com.iagocharon.IGOF.Entity.Specialty;
import com.iagocharon.IGOF.Entity.UltrasoundDoctor;
import com.iagocharon.IGOF.Entity.UltrasoundStudy;
import com.iagocharon.IGOF.Entity.User;
import com.iagocharon.IGOF.Entity.WorkSchedule;
import com.iagocharon.IGOF.Repository.PaymentMethodRepository;
import com.iagocharon.IGOF.Repository.UserRepository;
import com.iagocharon.IGOF.Service.AuthService;
import com.iagocharon.IGOF.Service.DoctorService;
import com.iagocharon.IGOF.Service.InsuranceService;
import com.iagocharon.IGOF.Service.MedicalRecordService;
import com.iagocharon.IGOF.Service.PatientService;
import com.iagocharon.IGOF.Service.SpecialtyService;
import com.iagocharon.IGOF.Service.UltrasoundDoctorService;
import com.iagocharon.IGOF.Service.UltrasoundStudyService;
import com.iagocharon.IGOF.Service.WorkScheduleService;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Utils implements CommandLineRunner {

  @Autowired
  AuthService authService;

  @Autowired
  InsuranceService insuranceService;

  @Autowired
  SpecialtyService specialtyService;

  @Autowired
  WorkScheduleService workScheduleService;

  @Autowired
  DoctorService doctorService;

  @Autowired
  PatientService patientService;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  MedicalRecordService medicalRecordService;

  @Autowired
  UserRepository userRepository;

  @Autowired
  PaymentMethodRepository paymentMethodRepository;

  @Autowired
  UltrasoundDoctorService ultrasoundDoctorService;

  @Autowired
  UltrasoundStudyService ultrasoundStudyService;

  @Override
  public void run(String... args) {
    // createInsurancesAndPatients();
    // createPaymentMethods();
    // createAdmin("admin", "admin", "admin@admin.com", "admin", "admin");
    // createUltrasoundDoctors();
  }

  public void createInsurancesAndPatients() {
    // Crear 5 Obras Sociales
    Insurance insurance1 = new Insurance();
    insurance1.setName("Obra Social A");
    insurance1 = insuranceService.save(insurance1);

    Insurance insurance2 = new Insurance();
    insurance2.setName("Obra Social B");
    insurance2 = insuranceService.save(insurance2);

    Insurance insurance3 = new Insurance();
    insurance3.setName("Obra Social C");
    insurance3 = insuranceService.save(insurance3);

    Insurance insurance4 = new Insurance();
    insurance4.setName("Obra Social D");
    insurance4 = insuranceService.save(insurance4);

    Insurance insurance5 = new Insurance();
    insurance5.setName("Obra Social E");
    insurance5 = insuranceService.save(insurance5);

    createPatient(
      "pedrogarcia",
      "pedrogarcia@gmail.com",
      "Pedro",
      "García",
      "password1",
      LocalDate.of(1985, 3, 12),
      insurance1,
      "1112345671"
    );
    createPatient(
      "mariacastro",
      "mariacastro@gmail.com",
      "María",
      "Castro",
      "password2",
      LocalDate.of(1990, 7, 25),
      insurance1,
      "1112345672"
    );
    createPatient(
      "luislopez",
      "luislopez@gmail.com",
      "Luis",
      "López",
      "password3",
      LocalDate.of(1982, 10, 30),
      insurance1,
      "1112345673"
    );
    createPatient(
      "sofiafernandez",
      "sofiafernandez@gmail.com",
      "Sofía",
      "Fernández",
      "password4",
      LocalDate.of(1993, 5, 14),
      insurance1,
      "1112345674"
    );
    createPatient(
      "andresrodriguez",
      "andresrodriguez@gmail.com",
      "Andrés",
      "Rodríguez",
      "password5",
      LocalDate.of(1980, 11, 20),
      insurance1,
      "1112345675"
    );
    createPatient(
      "anabelaperez",
      "anabelaperez@gmail.com",
      "Anabela",
      "Pérez",
      "password6",
      LocalDate.of(1984, 4, 8),
      insurance2,
      "1112345676"
    );
    createPatient(
      "gustavodominguez",
      "gustavodominguez@gmail.com",
      "Gustavo",
      "Domínguez",
      "password7",
      LocalDate.of(1986, 9, 15),
      insurance2,
      "1112345677"
    );
    createPatient(
      "carlarodriguez",
      "carlarodriguez@gmail.com",
      "Carla",
      "Rodríguez",
      "password8",
      LocalDate.of(1990, 10, 20),
      insurance2,
      "1112345678"
    );
    createPatient(
      "martinlopez",
      "martinlopez@gmail.com",
      "Martín",
      "López",
      "password9",
      LocalDate.of(1982, 2, 13),
      insurance2,
      "1112345679"
    );
    createPatient(
      "patriciamorales",
      "patriciamorales@gmail.com",
      "Patricia",
      "Morales",
      "password10",
      LocalDate.of(1994, 6, 22),
      insurance2,
      "1112345680"
    );
    createPatient(
      "rebeccaguerrero",
      "rebeccaguerrero@gmail.com",
      "Rebeca",
      "Guerrero",
      "password11",
      LocalDate.of(1987, 6, 19),
      insurance3,
      "1112345681"
    );
    createPatient(
      "danielcortes",
      "danielcortes@gmail.com",
      "Daniel",
      "Cortés",
      "password12",
      LocalDate.of(1991, 1, 5),
      insurance3,
      "1112345682"
    );
    createPatient(
      "lilianamartinez",
      "lilianamartinez@gmail.com",
      "Liliana",
      "Martínez",
      "password13",
      LocalDate.of(1989, 8, 22),
      insurance3,
      "1112345683"
    );
    createPatient(
      "eduardoramos",
      "eduardoramos@gmail.com",
      "Eduardo",
      "Ramos",
      "password14",
      LocalDate.of(1992, 4, 10),
      insurance3,
      "1112345684"
    );
    createPatient(
      "gloriasanchez",
      "gloriasanchez@gmail.com",
      "Gloria",
      "Sánchez",
      "password15",
      LocalDate.of(1985, 12, 18),
      insurance3,
      "1112345685"
    );
    createPatient(
      "alvarohernandez",
      "alvarohernandez@gmail.com",
      "Álvaro",
      "Hernández",
      "password16",
      LocalDate.of(1983, 11, 3),
      insurance4,
      "1112345686"
    );
    createPatient(
      "susanavazquez",
      "susanavazquez@gmail.com",
      "Susana",
      "Vázquez",
      "password17",
      LocalDate.of(1995, 1, 17),
      insurance4,
      "1112345687"
    );
    createPatient(
      "oscarfernandez",
      "oscarfernandez@gmail.com",
      "Óscar",
      "Fernández",
      "password18",
      LocalDate.of(1987, 9, 14),
      insurance4,
      "1112345688"
    );
    createPatient(
      "margaritamoreno",
      "margaritamoreno@gmail.com",
      "Margarita",
      "Moreno",
      "password19",
      LocalDate.of(1991, 5, 8),
      insurance4,
      "1112345689"
    );
    createPatient(
      "robertopereira",
      "robertopereira@gmail.com",
      "Roberto",
      "Pereira",
      "password20",
      LocalDate.of(1980, 7, 20),
      insurance4,
      "1112345690"
    );
    createPatient(
      "carlosjimenez",
      "carlosjimenez@gmail.com",
      "Carlos",
      "Jiménez",
      "password21",
      LocalDate.of(1994, 12, 5),
      insurance5,
      "1112345691"
    );
    createPatient(
      "patricianieto",
      "patricianieto@gmail.com",
      "Patricia",
      "Nieto",
      "password22",
      LocalDate.of(1990, 4, 11),
      insurance5,
      "1112345692"
    );
    createPatient(
      "raulsilva",
      "raulsilva@gmail.com",
      "Raúl",
      "Silva",
      "password23",
      LocalDate.of(1988, 2, 19),
      insurance5,
      "1112345693"
    );
    createPatient(
      "veronicasalazar",
      "veronicasalazar@gmail.com",
      "Verónica",
      "Salazar",
      "password24",
      LocalDate.of(1982, 6, 25),
      insurance5,
      "1112345694"
    );
    createPatient(
      "victorortega",
      "victorortega@gmail.com",
      "Víctor",
      "Ortega",
      "password25",
      LocalDate.of(1986, 10, 10),
      insurance5,
      "1112345695"
    );

    List<Specialty> specialties = Arrays.asList(
      specialtyService.save(new Specialty("Cardiología")),
      specialtyService.save(new Specialty("Pediatría")),
      specialtyService.save(new Specialty("Dermatología")),
      specialtyService.save(new Specialty("Oftalmología")),
      specialtyService.save(new Specialty("Psiquiatría")),
      specialtyService.save(new Specialty("Neumonología")),
      specialtyService.save(new Specialty("Ginecología")),
      specialtyService.save(new Specialty("Traumatología"))
    );

    List<String[]> doctors = Arrays.asList(
      new String[] {
        "carlosperez",
        "password123",
        "carlosperez@gmail.com",
        "Carlos",
        "Pérez",
      },
      new String[] {
        "josefernandez",
        "password123",
        "josefernandez@gmail.com",
        "José",
        "Fernández",
      },
      new String[] {
        "lauragomez",
        "password123",
        "lauragomez@gmail.com",
        "Laura",
        "Gómez",
      },
      new String[] {
        "juanmartinez",
        "password123",
        "juanmartinez@gmail.com",
        "Juan",
        "Martínez",
      },
      new String[] {
        "luismorales",
        "password123",
        "luismorales@gmail.com",
        "Luis",
        "Morales",
      },
      new String[] {
        "mariacastillo",
        "password123",
        "mariacastillo@gmail.com",
        "María",
        "Castillo",
      },
      new String[] {
        "ricardogonzalez",
        "password123",
        "ricardogonzalez@gmail.com",
        "Ricardo",
        "González",
      },
      new String[] {
        "veronicamartinez",
        "password123",
        "veronicamartinez@gmail.com",
        "Verónica",
        "Martínez",
      },
      new String[] {
        "pedromoreno",
        "password123",
        "pedromoreno@gmail.com",
        "Pedro",
        "Moreno",
      },
      new String[] {
        "mariadominguez",
        "password123",
        "mariadominguez@gmail.com",
        "María",
        "Domínguez",
      },
      new String[] {
        "luisalvarez",
        "password123",
        "luisalvarez@gmail.com",
        "Luis",
        "Álvarez",
      },
      new String[] {
        "danielramirez",
        "password123",
        "danielramirez@gmail.com",
        "Daniel",
        "Ramírez",
      },
      new String[] {
        "teresagonzalez",
        "password123",
        "teresagonzalez@gmail.com",
        "Teresa",
        "González",
      },
      new String[] {
        "susanaguerrero",
        "password123",
        "susanaguerrero@gmail.com",
        "Susana",
        "Guerrero",
      },
      new String[] {
        "franciscogomez",
        "password123",
        "franciscogomez@gmail.com",
        "Francisco",
        "Gómez",
      },
      new String[] {
        "aliciarodriguez",
        "password123",
        "aliciarodriguez@gmail.com",
        "Alicia",
        "Rodríguez",
      }
    );

    // Random para asignar especialidades
    Random random = new Random();

    for (String[] doctor : doctors) {
      // Seleccionar entre 1 y 3 especialidades aleatorias
      int numSpecialties = random.nextInt(3) + 1;
      List<Specialty> selectedSpecialties = new ArrayList<>(specialties);
      Collections.shuffle(selectedSpecialties);
      selectedSpecialties = selectedSpecialties.subList(0, numSpecialties);

      // Crear el médico con especialidades aleatorias
      createDoctor(
        doctor[0],
        doctor[1],
        doctor[2],
        doctor[3],
        doctor[4],
        selectedSpecialties
      );
    }
  }

  public void createUltrasoundDoctors() {
    List<UltrasoundStudy> ultrasoundStudies = Arrays.asList(
      ultrasoundStudyService.save(new UltrasoundStudy("Ecografía Abdominal")),
      ultrasoundStudyService.save(new UltrasoundStudy("Ecografía Obstétrica")),
      ultrasoundStudyService.save(new UltrasoundStudy("Ecografía Mamaria")),
      ultrasoundStudyService.save(new UltrasoundStudy("Ecografía Doppler")),
      ultrasoundStudyService.save(
        new UltrasoundStudy("Ecografía Musculoesquelética")
      ),
      ultrasoundStudyService.save(new UltrasoundStudy("Ecografía Renal")),
      ultrasoundStudyService.save(new UltrasoundStudy("Ecografía Tiroidea")),
      ultrasoundStudyService.save(new UltrasoundStudy("Ecografía Pélvica")),
      ultrasoundStudyService.save(new UltrasoundStudy("Ecografía Testicular")),
      ultrasoundStudyService.save(new UltrasoundStudy("Ecografía Hepática"))
    );

    createUltrasoundDoctor(
      "marianaperez",
      "password123",
      "marianaperez@gmail.com",
      "Mariana",
      "Pérez",
      ultrasoundStudies
    );

    createUltrasoundDoctor(
      "fernandolopez",
      "password123",
      "fernandolopez@gmail.com",
      "Fernando",
      "López",
      ultrasoundStudies
    );
    createUltrasoundDoctor(
      "anagomez",
      "password123",
      "anagomez@gmail.com",
      "Ana",
      "Gómez",
      ultrasoundStudies
    );
    createUltrasoundDoctor(
      "jorgefernandez",
      "password123",
      "jorgefernandez@gmail.com",
      "Jorge",
      "Fernández",
      ultrasoundStudies
    );
    createUltrasoundDoctor(
      "martasanchez",
      "password123",
      "martasanchez@gmail.com",
      "Marta",
      "Sánchez",
      ultrasoundStudies
    );
  }

  private void createPatient(
    String username,
    String email,
    String firstName,
    String lastName,
    String password,
    LocalDate birthDate,
    Insurance insurance,
    String phone
  ) {
    Patient patient = new Patient();
    patient.setUsername(username);
    patient.setEmail(email);
    patient.setName(firstName);
    patient.setLastname(lastName);
    patient.setPassword(passwordEncoder.encode(password));
    patient.setBirthdate(birthDate);
    patient.setPhone(phone);
    patient.setAddress("Calle 123, 456");
    patient.setCity("Posadas");
    patient.setState("Misiones");
    patient.setCountry("Argentina");
    patient.setCitizenship("Argentino");
    patient.setRole(Role.PATIENT);

    patientService.save(patient);

    MedicalRecord medicalRecord = new MedicalRecord();
    medicalRecord.setPatient(patient);
    patient.setMedicalRecord(medicalRecord);

    medicalRecordService.save(medicalRecord);
  }

  private void createDoctor(
    String username,
    String password,
    String email,
    String firstName,
    String lastName,
    List<Specialty> specialties
  ) {
    List<Insurance> insurances = insuranceService.getAll();

    Doctor user = new Doctor();
    user.setUsername(username);
    user.setPassword(passwordEncoder.encode(password));
    user.setEmail(email);
    user.setName(firstName);
    user.setLastname(lastName);
    user.setSpecialties(specialties);
    user.setAppointmentDuration((int) (Math.random() * (45 - 25 + 1) + 25));
    user.setRole(Role.DOCTOR);

    user = doctorService.save(user);

    for (Insurance insurance : insurances) {
      user.addInsurance(insurance);
      insurance.addDoctor(user);
      insuranceService.save(insurance);
    }

    doctorService.save(user);

    List<WorkSchedule> workSchedules = createWorkScheduleForDoctor(user);

    user.setWorkSchedules(workSchedules);
    doctorService.save(user);
  }

  private void createUltrasoundDoctor(
    String username,
    String password,
    String email,
    String firstName,
    String lastName,
    List<UltrasoundStudy> ultrasoundStudies
  ) {
    List<Insurance> insurances = insuranceService.getAll();

    UltrasoundDoctor user = new UltrasoundDoctor();
    user.setUsername(username);
    user.setPassword(passwordEncoder.encode(password));
    user.setEmail(email);
    user.setName(firstName);
    user.setLastname(lastName);
    user.setUltrasoundStudies(ultrasoundStudies);
    user.setAppointmentDuration((int) (Math.random() * (45 - 25 + 1) + 25));
    user.setRole(Role.ULTRASOUND_DOCTOR);

    user = ultrasoundDoctorService.save(user);

    for (Insurance insurance : insurances) {
      user.addInsurance(insurance);
      insurance.addUltrasoundDoctor(user);
      insuranceService.save(insurance);
      user = ultrasoundDoctorService.save(user);
    }

    for (UltrasoundStudy ultrasoundStudy : ultrasoundStudies) {
      user.addUltrasoundStudy(ultrasoundStudy);
      ultrasoundStudy.addUltrasoundDoctor(user);
      ultrasoundStudyService.save(ultrasoundStudy);
      user = ultrasoundDoctorService.save(user);
    }

    List<WorkSchedule> workSchedules = createWorkSchedulesForUltraSoundDoctor(
      user
    );

    user.setWorkSchedules(workSchedules);
    ultrasoundDoctorService.save(user);
  }

  private void createAdmin(
    String username,
    String password,
    String email,
    String firstName,
    String lastName
  ) {
    User user = new User();
    user.setUsername(username);
    user.setPassword(passwordEncoder.encode(password));
    user.setEmail(email);
    user.setName(firstName);
    user.setLastname(lastName);
    user.setRole(Role.ADMIN);

    userRepository.save(user);
  }

  private void createPaymentMethods() {
    PaymentMethod efectivo = new PaymentMethod();
    efectivo.setName("Efectivo");
    paymentMethodRepository.save(efectivo);
    PaymentMethod mp = new PaymentMethod();
    mp.setName("MP");
    paymentMethodRepository.save(mp);
    PaymentMethod tarjeta = new PaymentMethod();
    tarjeta.setName("Tarjeta");
    paymentMethodRepository.save(tarjeta);
  }

  private List<WorkSchedule> createWorkScheduleForDoctor(Doctor doctor) {
    List<WorkSchedule> workSchedules = new ArrayList<>();
    LocalDate today = LocalDate.now().minusDays(1);
    LocalDate threeMonthsLater = today.plusMonths(3);

    for (
      LocalDate date = today;
      !date.isAfter(threeMonthsLater);
      date = date.plusDays(1)
    ) {
      DayOfWeek dayOfWeek = date.getDayOfWeek();
      if (dayOfWeek != DayOfWeek.SUNDAY) {
        WorkSchedule schedule = new WorkSchedule();
        schedule.setDate(date);
        schedule.setStart(LocalTime.of(9, 30));
        schedule.setEnd(LocalTime.of(18, 30));
        schedule.setDoctor(doctor);
        workSchedules.add(workScheduleService.save(schedule));
      }
    }
    return workSchedules;
  }

  private List<WorkSchedule> createWorkSchedulesForUltraSoundDoctor(
    UltrasoundDoctor ultrasoundDoctor
  ) {
    List<WorkSchedule> workSchedules = new ArrayList<>();
    LocalDate today = LocalDate.now().minusDays(1);
    LocalDate threeMonthsLater = today.plusMonths(3);

    for (
      LocalDate date = today;
      !date.isAfter(threeMonthsLater);
      date = date.plusDays(1)
    ) {
      DayOfWeek dayOfWeek = date.getDayOfWeek();
      if (dayOfWeek != DayOfWeek.SUNDAY) {
        WorkSchedule schedule = new WorkSchedule();
        schedule.setDate(date);
        schedule.setStart(LocalTime.of(9, 30));
        schedule.setEnd(LocalTime.of(18, 30));
        schedule.setUltrasoundDoctor(ultrasoundDoctor);
        workSchedules.add(workScheduleService.save(schedule));
      }
    }
    return workSchedules;
  }
}
