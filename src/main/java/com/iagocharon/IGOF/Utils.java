package com.iagocharon.IGOF;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.iagocharon.IGOF.Entity.Doctor;
import com.iagocharon.IGOF.Entity.Insurance;
import com.iagocharon.IGOF.Entity.InsuranceParent;
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
import com.iagocharon.IGOF.Service.InsuranceParentService;
import com.iagocharon.IGOF.Service.InsuranceService;
import com.iagocharon.IGOF.Service.MedicalRecordService;
import com.iagocharon.IGOF.Service.PatientService;
import com.iagocharon.IGOF.Service.SpecialtyService;
import com.iagocharon.IGOF.Service.UltrasoundDoctorService;
import com.iagocharon.IGOF.Service.UltrasoundStudyService;
import com.iagocharon.IGOF.Service.WorkScheduleService;

import jakarta.transaction.Transactional;

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

  @Autowired
  InsuranceParentService insuranceParentService;

  @Override
  public void run(String... args) {
    // createInsurancesAndPatients();
    // createPaymentMethods();
    // createAdmin(
    //   "adminIGOF",
    //   "Admin@2025",
    //   "admins@igof.com.ar",
    //   "Admins",
    //   "IGOF"
    // );
    // createDoctors();
    // createUltrasoundDoctors();

    //  ---------- UTILS ----------

    // removeDuplicateInsurances();
    // removeDuplicateInsuranceParents();

    Optional<User> admin = userRepository.findByUsername("admin");
    User user = admin.orElse(null);
    if (user != null) {
      user.setPassword(passwordEncoder.encode("admin"));
      userRepository.save(user);
    }
  }

  @Transactional
  public void removeDuplicateInsurances() {
    List<Insurance> allInsurances = insuranceService.getAllInsurances();
    Map<String, Insurance> uniqueInsurances = new HashMap<>();

    // Identificar y eliminar duplicados
    for (Insurance insurance : allInsurances) {
      String name = insurance.getName();
      if (!uniqueInsurances.containsKey(name)) {
        uniqueInsurances.put(name, insurance);
      } else {
        // Primero eliminar todas las relaciones
        insuranceService.deleteById(insurance.getId());
      }
    }
  }

  @Transactional
  public void removeDuplicateInsuranceParents() {
    List<InsuranceParent> allInsurances =
      insuranceParentService.getAllInsuranceParents();
    Map<String, InsuranceParent> uniqueInsurances = new HashMap<>();

    // Identificar y eliminar duplicados
    for (InsuranceParent insurance : allInsurances) {
      String name = insurance.getName();
      if (!uniqueInsurances.containsKey(name)) {
        uniqueInsurances.put(name, insurance);
      } else {
        // Primero eliminar todas las relaciones
        insuranceParentService.deleteById(insurance.getId());
      }
    }
  }

  public void createInsurancesAndPatients() {
    List<String> insurances = Arrays.asList(
      "PARTICULAR",
      "AATRAC (TELEGRAFISTAS)",
      "ACA SALUD",
      "ACCORD",
      "ALIMENTACION SAN IGNACIO",
      "APTM (TABACALEROS)",
      "APTM-MUTUAL DE SERVICIOS SOCIALES",
      "ART ASOCIART",
      "ART COLONIA SUIZA",
      "ART EXPERTA",
      "ART INTEGR",
      "ART PREVENCION",
      "ART PROFU",
      "ART SINGER",
      "ART SWISS MEDICAL",
      "ASOC. ARG MUTUAL MOTOCICLISTAS",
      "ASSIST CARD",
      "ATSA",
      "AVALIAN",
      "CAMARA DEL TABACO DE MISIONES",
      "CAMIONEROS",
      "CAPITA PAMI - III NIVEL",
      "CASIMIRO ART",
      "CENTRO DE PATRONES DE CABOTAJE",
      "CIRUGIA CARDIOVASCULAR",
      "CITY SALUD",
      "COLEGIO DE ABOGADOS PROV. DE MISIONES",
      "COLONIA SUIZA ART",
      "COMEDI",
      "CON SALUD PARTICULARES",
      "CONDUCTORES NAVALES",
      "CONVENIOS SA",
      "COOP. DE PRODUCTORES YERBATEROS DE JARDÍN",
      "CS SALUD",
      "D.I.B.A. No Usuar",
      "DOCENTES PARTICULARES (OSDOP)",
      "DOCTHOS",
      "DOMIN",
      "ESTABLECIMIENTO LAS MARIAS SACIFA",
      "EX COMBATIENTES - VETERANOS DE GUERRA",
      "FARMACIA (O.S. DEL PERSONAL DE FARMACIA)",
      "FEDERACIÓN DEL TABACO (OSPT)",
      "FEDERACIÓN PATRONAL ART",
      "GALENO ARGENTINA S.A.",
      "GALENO ARGENTINA S.A. (PLAN AZUL)",
      "GASTRONÓMICOS (OSPAHG)",
      "HBC MEDICAL SRL",
      "HOSPITAL PUB. DR. RAMÓN MADARIAGA",
      "HOSPITAL PÚBLICO PROV. DE PEDIATRÍA",
      "HOSPITAL PÚBLICO SAMIC DE AUTOGESTIÓN",
      "IAMIP - MÉDICOS ZONA SUR",
      "IAMIP - PARTICULARES",
      "IAMIP - SERVICIO PENITENCIARIO FEDERAL",
      "IAMIP - CONVENIO C/ DOCTHOS S.A.",
      "IAMIP - CONVENIO CON OBRAS SANITARIAS",
      "IAMIP - PERSONAL DE SEGURIDAD",
      "IAMIP - DORADO",
      "IAMIP - MEDISUR S.A.",
      "IAMIP - MEDISUR S.A. (CONVENIO HACIENDA-OS)",
      "INSTITUTO MATERNO INFANTIL S.A",
      "INSTITUTO PRIVADO DE NEFROLOGIA",
      "IOSFA",
      "IPS",
      "IPS AFILIADOS INDIRECTOS",
      "IPS III NIVEL INTERNADO (SB)",
      "IPS III NIVEL",
      "IPS PRDHICAR/M. SANA/H SANO",
      "JERARQUICO SALUD",
      "LA SEGUNDA ART",
      "LA SEGUNDA SEGUROS PERSONALES",
      "LABORATORIO NOVARTIS",
      "LUZ Y FUERZA (DIRECTO)",
      "MEDICINA ALTA COMPETENCIA",
      "MEDICINA CENTRO S.A.",
      "MEDICUS S.A.",
      "MEDIFE - AFILIADOS OBLIGATORIOS",
      "MEDIFE - AFILIADOS VOLUNTARIOS",
      "MELD SALUD",
      "MINISTERIO DE SALUD PÚBLICA",
      "MINISTERIO DE SALUD PÚBLICA (PROFE)",
      "MISIONES SALUD (YERBATEROS)",
      "MUTUAL DEL CLERO SAN PEDRO Y SAN PABLO",
      "NOSIGLIA SALUD",
      "NUESTRA SRA DEL ROSARIO ART",
      "O.S. DEL PERSONAL DE AERONAVEGACIÓN PRIV",
      "O.S. DEL PERSONAL ESPECTÁCULOS PÚBLICOS",
      "O.S. PARA LA ACTIVIDAD DE LA CONSTRUCCIÓN",
      "O.S. DE LEGISLADORES DE LA REP. ARG.",
      "O.S.E.C.A.C.",
      "O.S.M.A.T.A.",
      "O.S.P.I.T. (INDUSTRIA DEL TABACO)",
      "O.S.P.L.A.D.",
      "OBRA SOCIAL DE BANCARIOS",
      "Obra Social de Vendedores Ambulantes",
      "OBRA SOCIAL DEL PERSONAL DE SEGURIDAD",
      "OBRA SOCIAL DEL PETROLEO Y GAS PRIVADOS",
      "OMINT S.A.",
      "OPDEA",
      "OSA AERONAVEGANTES",
      "OSACRA",
      "OSALARA (DIRECTO)",
      "OSALARA (O.S. DE AGENTES DE LOTERIAS)",
      "OSCEARA",
      "OSCOEMA",
      "OSDE 210 (FRANJA 1)",
      "OSDE 310 (FRANJA 2)",
      "OSDE 410 (FRANJA 3)",
      "OSDE 450 (FRANJA 3)",
      "OSDE 510 (FRANJA 3)",
      "OSDE PLAN 6-030",
      "OSDEPYM",
      "OSDO (ALTO PARANA)",
      "OSFE FERROVIARIA",
      "OSMISS",
      "OSMISS (GRUPO MELD SALUD)",
      "OSMITA",
      "OSPA",
      "OSPACA (OBRA SOC. P/LA ACT. CERVECERA)",
      "OSPACP (Personal auxiliar de casas particulares)",
      "OSPAGA - GASEOSAS Y AFINES",
      "OSPAT (O.S.P/LA ACTIVIDAD DEL TURF)",
      "OSPATCA",
      "OSPEC (CORREOS)",
      "OSPECON (EMPLEADOS DE LA CONSTRUCCIÓN)",
      "OSPEDYC (O.S. ENTIDADES DEPORTIVAS Y CIV.)",
      "OSPES (ESTAC. DE SERVICIO)",
      "OSPRAL (IND. ALIMENTACION)",
      "OSPLAD",
      "OSPREME (P. DIRECCION DE IND. AUTOMOTRIZ)",
      "OSPRERA",
      "OSPRERA EXTRACAPITA MONOTRIBUTISTA",
      "OSPRERA MONOTRIBUTISTA",
      "OSPRERA/RSAU",
      "OSPSA (SANIDAD ARG.)",
      "OSPSIP",
      "OSSAGRA",
      "OSSEG - SEGUROS (I.S.S.S.)",
      "OSSMIRA (O/S SUP IND METALURGICA REP ARG)",
      "OSUTHGRA",
      "PAMI RED - IMI",
      "PAMI RED - SANATORIO BUDDENBERG",
      "PAMI RED - SANATORIO BUENOS AIRES",
      "PAMI RED - SANATORIO NOSIGLIA",
      "PAMI VETERANOS DE GUERRA",
      "PAMI/ACE",
      "PANADEROS (OSPEP)",
      "PASTELEROS CONFITEROS PIZZEROS HELADEROS",
      "PERSONAL DE TELEVISION",
      "PERSONAL MARÍTIMO (SOMU)",
      "PIGERL",
      "PLAN DE FORTALECIMIENTO NUTRICIONAL",
      "PODER JUDICIAL",
      "POLICIA FEDERAL",
      "PREPAGAS - IDEALES",
      "PREPAGOS MEDICOS (ASSURANCE MEDICALE)",
      "PRESTADORES SANATORIALES",
      "PREVENCION SALUD",
      "PROTECCION FAMILIAR",
      "PROVINCIA ART",
      "RESONANCIA MAGNETICA MISIONES S.R.L",
      "SAMA",
      "SAN PEDRO",
      "SANCOR SALUD",
      "SCIS",
      "SERVICIO HEMOD. SANATORIO BORATTI",
      "SIN CARGO",
      "SINDICALES - IDEALES",
      "SINDICATO AMAS DE CASA",
      "SINDICATO DE CANILLITAS",
      "SMANAM",
      "Superintendencia Riesgos de Trabajo",
      "SWISS MEDICAL S.A.",
      "TECNICO AERONAUTICO - APTA",
      "TIPOKA SA",
      "TRASLADARTE SALUD",
      "U.O.C.R.A.",
      "U.T.E.D.Y.C. O.S.P.E.D.Y.C.",
      "UNION PERSONAL CIVIL DE LA NACION (TODOS PLANES)",
      "UTA (DIRECTO)",
      "UTA (UNION TRANVIARIO AUTOMOTOR)",
      "VIALIDAD NACIONAL",
      "VISITAR"
    );
    for (String insuranceName : insurances) {
      InsuranceParent insuranceParent = new InsuranceParent();
      insuranceParent.setName(insuranceName);
      insuranceParent = insuranceParentService.save(insuranceParent);
      Insurance insurance = new Insurance();
      insurance.setName(insuranceName);
      insurance.setInsuranceParent(insuranceParent);
      insurance = insuranceService.save(insurance);
      insuranceParent.addInsurance(insurance);
      insuranceParentService.save(insuranceParent);
    }
    // createPatient(
    //   "pedrogarcia",
    //   "pedrogarcia@gmail.com",
    //   "Pedro",
    //   "García",
    //   "password1",
    //   LocalDate.of(1985, 3, 12),
    //   "1112345671"
    // );
    // createPatient(
    //   "mariacastro",
    //   "mariacastro@gmail.com",
    //   "María",
    //   "Castro",
    //   "password2",
    //   LocalDate.of(1990, 7, 25),
    //   "1112345672"
    // );
    // createPatient(
    //   "luislopez",
    //   "luislopez@gmail.com",
    //   "Luis",
    //   "López",
    //   "password3",
    //   LocalDate.of(1982, 10, 30),
    //   "1112345673"
    // );
    // createPatient(
    //   "sofiafernandez",
    //   "sofiafernandez@gmail.com",
    //   "Sofía",
    //   "Fernández",
    //   "password4",
    //   LocalDate.of(1993, 5, 14),
    //   "1112345674"
    // );
    // createPatient(
    //   "andresrodriguez",
    //   "andresrodriguez@gmail.com",
    //   "Andrés",
    //   "Rodríguez",
    //   "password5",
    //   LocalDate.of(1980, 11, 20),
    //   "1112345675"
    // );
    // createPatient(
    //   "anabelaperez",
    //   "anabelaperez@gmail.com",
    //   "Anabela",
    //   "Pérez",
    //   "password6",
    //   LocalDate.of(1984, 4, 8),
    //   "1112345676"
    // );
    // createPatient(
    //   "gustavodominguez",
    //   "gustavodominguez@gmail.com",
    //   "Gustavo",
    //   "Domínguez",
    //   "password7",
    //   LocalDate.of(1986, 9, 15),
    //   "1112345677"
    // );
    // createPatient(
    //   "carlarodriguez",
    //   "carlarodriguez@gmail.com",
    //   "Carla",
    //   "Rodríguez",
    //   "password8",
    //   LocalDate.of(1990, 10, 20),
    //   "1112345678"
    // );
    // createPatient(
    //   "martinlopez",
    //   "martinlopez@gmail.com",
    //   "Martín",
    //   "López",
    //   "password9",
    //   LocalDate.of(1982, 2, 13),
    //   "1112345679"
    // );
    // createPatient(
    //   "patriciamorales",
    //   "patriciamorales@gmail.com",
    //   "Patricia",
    //   "Morales",
    //   "password10",
    //   LocalDate.of(1994, 6, 22),
    //   "1112345680"
    // );
    // createPatient(
    //   "rebeccaguerrero",
    //   "rebeccaguerrero@gmail.com",
    //   "Rebeca",
    //   "Guerrero",
    //   "password11",
    //   LocalDate.of(1987, 6, 19),
    //   "1112345681"
    // );
    // createPatient(
    //   "danielcortes",
    //   "danielcortes@gmail.com",
    //   "Daniel",
    //   "Cortés",
    //   "password12",
    //   LocalDate.of(1991, 1, 5),
    //   "1112345682"
    // );
    // createPatient(
    //   "lilianamartinez",
    //   "lilianamartinez@gmail.com",
    //   "Liliana",
    //   "Martínez",
    //   "password13",
    //   LocalDate.of(1989, 8, 22),
    //   "1112345683"
    // );
    // createPatient(
    //   "eduardoramos",
    //   "eduardoramos@gmail.com",
    //   "Eduardo",
    //   "Ramos",
    //   "password14",
    //   LocalDate.of(1992, 4, 10),
    //   "1112345684"
    // );
    // createPatient(
    //   "gloriasanchez",
    //   "gloriasanchez@gmail.com",
    //   "Gloria",
    //   "Sánchez",
    //   "password15",
    //   LocalDate.of(1985, 12, 18),
    //   "1112345685"
    // );
    // createPatient(
    //   "alvarohernandez",
    //   "alvarohernandez@gmail.com",
    //   "Álvaro",
    //   "Hernández",
    //   "password16",
    //   LocalDate.of(1983, 11, 3),
    //   "1112345686"
    // );
    // createPatient(
    //   "susanavazquez",
    //   "susanavazquez@gmail.com",
    //   "Susana",
    //   "Vázquez",
    //   "password17",
    //   LocalDate.of(1995, 1, 17),
    //   "1112345687"
    // );
    // createPatient(
    //   "oscarfernandez",
    //   "oscarfernandez@gmail.com",
    //   "Óscar",
    //   "Fernández",
    //   "password18",
    //   LocalDate.of(1987, 9, 14),
    //   "1112345688"
    // );
    // createPatient(
    //   "margaritamoreno",
    //   "margaritamoreno@gmail.com",
    //   "Margarita",
    //   "Moreno",
    //   "password19",
    //   LocalDate.of(1991, 5, 8),
    //   "1112345689"
    // );
    // createPatient(
    //   "robertopereira",
    //   "robertopereira@gmail.com",
    //   "Roberto",
    //   "Pereira",
    //   "password20",
    //   LocalDate.of(1980, 7, 20),
    //   "1112345690"
    // );
    // createPatient(
    //   "carlosjimenez",
    //   "carlosjimenez@gmail.com",
    //   "Carlos",
    //   "Jiménez",
    //   "password21",
    //   LocalDate.of(1994, 12, 5),
    //   "1112345691"
    // );
    // createPatient(
    //   "patricianieto",
    //   "patricianieto@gmail.com",
    //   "Patricia",
    //   "Nieto",
    //   "password22",
    //   LocalDate.of(1990, 4, 11),
    //   "1112345692"
    // );
    // createPatient(
    //   "raulsilva",
    //   "raulsilva@gmail.com",
    //   "Raúl",
    //   "Silva",
    //   "password23",
    //   LocalDate.of(1988, 2, 19),
    //   "1112345693"
    // );
    // createPatient(
    //   "veronicasalazar",
    //   "veronicasalazar@gmail.com",
    //   "Verónica",
    //   "Salazar",
    //   "password24",
    //   LocalDate.of(1982, 6, 25),
    //   "1112345694"
    // );
    // createPatient(
    //   "victorortega",
    //   "victorortega@gmail.com",
    //   "Víctor",
    //   "Ortega",
    //   "password25",
    //   LocalDate.of(1986, 10, 10),
    //   "1112345695"
    // );
  }

  public void createDoctors() {
    Specialty gyo = specialtyService.save(
      new Specialty("Ginecología y Obstetricia")
    );
    Specialty fer = specialtyService.save(new Specialty("Fertilidad"));
    Specialty gij = specialtyService.save(
      new Specialty("Ginecología Infanto Juvenil")
    );
    Specialty ped = specialtyService.save(new Specialty("Pediatría"));
    Specialty nut = specialtyService.save(new Specialty("Nutrición"));
    Specialty cmh = specialtyService.save(
      new Specialty("Clínica Médica y Hematológica")
    );
    Specialty general = specialtyService.save(
      new Specialty("Medicina General")
    );

    createDoctor(
      "mayraestigarribia",
      "mayraestigarribia",
      "mayraestigarribia@igof.com.ar",
      "Mayra",
      "Estigarribia",
      Arrays.asList(gyo)
    );
    createDoctor(
      "camilagonzalez",
      "camilagonzalez",
      "camilagonzalez@igof.com.ar",
      "Camila",
      "González",
      Arrays.asList(gyo)
    );
    createDoctor(
      "silviavera",
      "silviavera",
      "silviavera@igof.com.ar",
      "Silvia",
      "Vera",
      Arrays.asList(gyo)
    );
    createDoctor(
      "ernestoirrazabal",
      "ernestoirrazabal",
      "ernestoirrazabal@igof.com.ar",
      "Ernesto",
      "Irrazabal",
      Arrays.asList(gyo)
    );

    createDoctor(
      "juancarloshobecker",
      "juancarloshobecker",
      "juancarloshobecker@igof.com.ar",
      "Juan Carlos",
      "Hobecker",
      Arrays.asList(fer, gij)
    );
    createDoctor(
      "nataliamarchessini",
      "nataliamarchessini",
      "nataliamarchessini@igof.com.ar",
      "Natalia",
      "Marchesini",
      Arrays.asList(ped)
    );
    createDoctor(
      "jorgefernandez",
      "jorgefernandez",
      "jorgefernandez@igof.com.ar",
      "Jorge",
      "Fernández",
      Arrays.asList(ped)
    );
    createDoctor(
      "giselamarini",
      "giselamarini",
      "giselamarini@igof.com.ar",
      "Gisela",
      "Marini",
      Arrays.asList(nut)
    );
    createDoctor(
      "fatimacufre",
      "fatimacufre",
      "fatimacufre@igof.com.ar",
      "Fátima",
      "Cufré",
      Arrays.asList(general)
    );
    createDoctor(
      "luciomariani",
      "luciomariani",
      "luciomariani@igof.com.ar",
      "Lucio",
      "Mariani",
      Arrays.asList(cmh)
    );
  }

  public void createUltrasoundDoctors() {
    List<UltrasoundStudy> ultrasoundStudies = Arrays.asList(
      ultrasoundStudyService.save(
        new UltrasoundStudy("Ecografía Ginecológica")
      ),
      ultrasoundStudyService.save(new UltrasoundStudy("Ecografía Obstétrica")),
      ultrasoundStudyService.save(
        new UltrasoundStudy("Screening del Primer Trimestre")
      ),
      ultrasoundStudyService.save(new UltrasoundStudy("Ecografía San Fetal")),
      ultrasoundStudyService.save(new UltrasoundStudy("Ecografía Mamaria")),
      ultrasoundStudyService.save(new UltrasoundStudy("Eco Doppler Fetal")),
      ultrasoundStudyService.save(
        new UltrasoundStudy("Eco Doppler Arterias Uterinas")
      ),
      ultrasoundStudyService.save(
        new UltrasoundStudy("Eco Doppler Ginecológico")
      ),
      ultrasoundStudyService.save(new UltrasoundStudy("Eco Doppler Mamario")),
      ultrasoundStudyService.save(new UltrasoundStudy("Ecografía Abdominal")),
      ultrasoundStudyService.save(new UltrasoundStudy("Ecografía Tiroidea")),
      ultrasoundStudyService.save(new UltrasoundStudy("Ecografía Renal")),
      ultrasoundStudyService.save(
        new UltrasoundStudy("Ecografía Hepatobiliopancreatica")
      ),
      ultrasoundStudyService.save(
        new UltrasoundStudy("Ecografía Transvaginal")
      ),
      ultrasoundStudyService.save(
        new UltrasoundStudy("Ecografía de partes Blandas")
      ),
      ultrasoundStudyService.save(
        new UltrasoundStudy("Ecografía Musculoesquelética")
      ),
      ultrasoundStudyService.save(
        new UltrasoundStudy("Ecografía de cadera en Niños")
      ),
      ultrasoundStudyService.save(
        new UltrasoundStudy("Ecografía Cerebral en Niños")
      ),
      ultrasoundStudyService.save(
        new UltrasoundStudy("Ecografía 3d Transvaginal")
      ),
      ultrasoundStudyService.save(
        new UltrasoundStudy("Ecografía 3d - 4d - 5d")
      ),
      ultrasoundStudyService.save(
        new UltrasoundStudy("Ecografía Vesicoprostática")
      ),
      ultrasoundStudyService.save(
        new UltrasoundStudy("Ecografía Testicular y de Vesículas Seminales")
      ),
      ultrasoundStudyService.save(new UltrasoundStudy("Eco Doppler General")),
      ultrasoundStudyService.save(new UltrasoundStudy("Otros"))
    );

    createUltrasoundDoctor(
      "marianoelialuque",
      "marianoelialuque",
      "marianoelialuque@igof.com.ar",
      "María Noelia",
      "Luque",
      ultrasoundStudies
    );

    createUltrasoundDoctor(
      "noeliafochesatto",
      "noeliafochesatto",
      "noeliafochesatto@igof.com.ar",
      "Noelia",
      "Fochesatto",
      ultrasoundStudies
    );
    createUltrasoundDoctor(
      "gabrielbaez",
      "gabrielbaez",
      "gabrielbaez@igof.com.ar",
      "Gabriel",
      "Baez",
      ultrasoundStudies
    );
    createUltrasoundDoctor(
      "arielrey",
      "arielrey",
      "arielrey@igof.com.ar",
      "Ariel",
      "Rey",
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
    List<Insurance> insurances = insuranceService.getAllInsurances();

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
    // List<WorkSchedule> workSchedules = createWorkScheduleForDoctor(user);

    // user.setWorkSchedules(workSchedules);
    // doctorService.save(user);
  }

  private void createUltrasoundDoctor(
    String username,
    String password,
    String email,
    String firstName,
    String lastName,
    List<UltrasoundStudy> ultrasoundStudies
  ) {
    List<Insurance> insurances = insuranceService.getAllInsurances();

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

    // List<WorkSchedule> workSchedules = createWorkSchedulesForUltraSoundDoctor(
    //   user
    // );

    // user.setWorkSchedules(workSchedules);
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
    mp.setName("Transferencia");
    paymentMethodRepository.save(mp);
    PaymentMethod tarjeta = new PaymentMethod();
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
