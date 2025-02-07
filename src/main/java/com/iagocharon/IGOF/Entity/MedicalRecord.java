package com.iagocharon.IGOF.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "medical_records")
public class MedicalRecord {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @OneToOne
  @JoinColumn(name = "patient_id", referencedColumnName = "id")
  @JsonIgnoreProperties("medicalRecord")
  private Patient patient;

  // familia

  @Column(columnDefinition = "CHAR(5)")
  private String asmaFam;

  @Column(columnDefinition = "CHAR(5)")
  private String htaFam;

  @Column(columnDefinition = "CHAR(5)")
  private String diabetesFam;

  @Column(columnDefinition = "CHAR(5)")
  private String dislipidemiaFam;

  @Column(columnDefinition = "CHAR(5)")
  private String iamFam;

  @Column(columnDefinition = "CHAR(5)")
  private String cardiopatiaIsquemicaFam;

  @Column(columnDefinition = "CHAR(5)")
  private String acvIsquemicoFam;

  @Column(columnDefinition = "CHAR(5)")
  private String acvHemorragicoFam;

  @Column(columnDefinition = "CHAR(5)")
  private String cancerColonFam;

  @Column(columnDefinition = "CHAR(5)")
  private String cancerPulmonFam;

  @Column(columnDefinition = "CHAR(5)")
  private String cancerMamaFam;

  @Column(columnDefinition = "CHAR(5)")
  private String cancerOvarioFam;

  @Column(columnDefinition = "CHAR(5)")
  private String cancerProstataFam;

  @Column(columnDefinition = "CHAR(5)")
  private String cancerRinonFam;

  @Column(columnDefinition = "CHAR(5)")
  private String enfermedadTiroideaFam;

  private List<String> otrosAntecedentesFam;

  private String observacionesFam;

  // personales

  @Column(columnDefinition = "CHAR(5)")
  private String hiv;

  @Column(columnDefinition = "CHAR(5)")
  private String tabaquismo;

  @Column(columnDefinition = "CHAR(5)")
  private String alcoholismo;

  @Column(columnDefinition = "CHAR(5)")
  private String aniosFumando;

  @Column(columnDefinition = "CHAR(5)")
  private String cigarrillosDiarios;

  @Column(columnDefinition = "CHAR(5)")
  private String sedentarismo;

  @Column(columnDefinition = "CHAR(5)")
  private String ejercicioDiario;

  @Column(columnDefinition = "CHAR(5)")
  private String hta;

  @Column(columnDefinition = "CHAR(5)")
  private String antiHipertensivos;

  @Column(columnDefinition = "CHAR(5)")
  private String dislipidemia;

  @Column(columnDefinition = "CHAR(5)")
  private String iam;

  @Column(columnDefinition = "CHAR(5)")
  private String enfermedadCoronaria;

  @Column(columnDefinition = "CHAR(5)")
  private String arritmia;

  @Column(columnDefinition = "CHAR(5)")
  private String aneurismaAortaAbdominal;

  @Column(columnDefinition = "CHAR(5)")
  private String enfermedadVascularPeriferica;

  @Column(columnDefinition = "CHAR(5)")
  private String insuficienciaCardiaca;

  @Column(columnDefinition = "CHAR(5)")
  private String acvIsquemico;

  @Column(columnDefinition = "CHAR(5)")
  private String acvHemorragico;

  @Column(columnDefinition = "CHAR(5)")
  private String depresion;

  @Column(columnDefinition = "CHAR(5)")
  private String demencia;

  @Column(columnDefinition = "CHAR(5)")
  private String institucionalizado;

  @Column(columnDefinition = "CHAR(5)")
  private String parkinson;

  @Column(columnDefinition = "CHAR(5)")
  private String irc;

  @Column(columnDefinition = "CHAR(5)")
  private String cirrosis;

  @Column(columnDefinition = "CHAR(5)")
  private String hda;

  @Column(columnDefinition = "CHAR(5)")
  private String hdb;

  private String diabetes;

  private String enfermedadTiroidea;

  @Column(columnDefinition = "CHAR(5)")
  private String bocio;

  @Column(columnDefinition = "CHAR(5)")
  private String epoc;

  @Column(columnDefinition = "CHAR(5)")
  private String asma;

  @Column(columnDefinition = "CHAR(5)")
  private String tepa;

  @Column(columnDefinition = "CHAR(5)")
  private String diatesisHemorragica;

  @Column(columnDefinition = "CHAR(5)")
  private String prostatismo;

  @Column(columnDefinition = "CHAR(5)")
  private String litiasisRenal;

  @Column(columnDefinition = "CHAR(5)")
  private String glaucoma;

  @Column(columnDefinition = "CHAR(5)")
  private String tombosisVenosaProfunda;

  @Column(columnDefinition = "CHAR(5)")
  private String menarca;

  @Column(columnDefinition = "CHAR(5)")
  private String edadMenarca;

  @Column(columnDefinition = "CHAR(5)")
  private String menopausia;

  @Column(columnDefinition = "CHAR(5)")
  private String edadMenopausia;

  @Column(columnDefinition = "CHAR(5)")
  private String gestas;

  @Column(columnDefinition = "CHAR(5)")
  private String partos;

  @Column(columnDefinition = "CHAR(5)")
  private String abortos;

  private List<String> otros;

  @Column(columnDefinition = "CHAR(5)")
  private String cancerColon;

  @Column(columnDefinition = "CHAR(5)")
  private String cancerPulmon;

  @Column(columnDefinition = "CHAR(5)")
  private String cancerMama;

  @Column(columnDefinition = "CHAR(5)")
  private String cancerOvario;

  @Column(columnDefinition = "CHAR(5)")
  private String cancerProstata;

  @Column(columnDefinition = "CHAR(5)")
  private String cancerRinon;

  @Column(columnDefinition = "CHAR(5)")
  private String lupusEritematosoSistemico;

  @Column(columnDefinition = "CHAR(5)")
  private String artritisReumatoidea;

  @Column(columnDefinition = "CHAR(5)")
  private String vasculitis;

  @Column(columnDefinition = "CHAR(5)")
  private String enfermedadesAutoinmunes;

  @Column(columnDefinition = "CHAR(5)")
  private String tratamientoCronicoConCorticoides;

  // alergias

  @Column(columnDefinition = "CHAR(5)")
  private String penicilina;

  @Column(columnDefinition = "CHAR(5)")
  private String iodo;

  @Column(columnDefinition = "CHAR(5)")
  private String dipirona;

  @Column(columnDefinition = "CHAR(5)")
  private String aspirina;

  private List<String> otrasAlergias;

  // quirurgicos

  @Column(columnDefinition = "CHAR(5)")
  private String angioplastiaCoronaria;

  @Column(columnDefinition = "CHAR(5)")
  private String intervencionBypassCoronario;

  @Column(columnDefinition = "CHAR(5)")
  private String esplenectomia;

  @Column(columnDefinition = "CHAR(5)")
  private String colecistectomiaClasica;

  @Column(columnDefinition = "CHAR(5)")
  private String colecistectomiaPorVideo;

  @Column(columnDefinition = "CHAR(5)")
  private String apendicectomia;

  @Column(columnDefinition = "CHAR(5)")
  private String homoplastiaInguinal;

  @Column(columnDefinition = "CHAR(5)")
  private String amigdalectomia;

  @Column(columnDefinition = "CHAR(5)")
  private String hemorroidectomia;

  @Column(columnDefinition = "CHAR(5)")
  private String cesarea;

  @Column(columnDefinition = "CHAR(5)")
  private String histerectomia;

  private List<String> transplantes;

  private List<String> otrosProcedimientos;

  @OneToMany(mappedBy = "medicalRecord")
  @JsonIgnoreProperties("medicalRecord")
  private List<Evolution> evolutions;

  @OneToMany(mappedBy = "medicalRecord")
  @JsonIgnoreProperties("medicalRecord")
  private List<UltrasoundStudyReport> ultrasoundStudyReports;

  public MedicalRecord() {
    this.asmaFam = "NR";
    this.htaFam = "NR";
    this.diabetesFam = "NR";
    this.dislipidemiaFam = "NR";
    this.iamFam = "NR";
    this.cardiopatiaIsquemicaFam = "NR";
    this.acvIsquemicoFam = "NR";
    this.acvHemorragicoFam = "NR";
    this.cancerColonFam = "NR";
    this.cancerPulmonFam = "NR";
    this.cancerMamaFam = "NR";
    this.cancerOvarioFam = "NR";
    this.cancerProstataFam = "NR";
    this.cancerRinonFam = "NR";
    this.enfermedadTiroideaFam = "NR";
    this.otrosAntecedentesFam = new ArrayList<>();
    this.observacionesFam = "";
    this.hiv = "NR";
    this.tabaquismo = "NR";
    this.alcoholismo = "NR";
    this.aniosFumando = "";
    this.cigarrillosDiarios = "";
    this.sedentarismo = "NR";
    this.ejercicioDiario = "";
    this.hta = "NR";
    this.antiHipertensivos = "NR";
    this.dislipidemia = "NR";
    this.iam = "NR";
    this.enfermedadCoronaria = "NR";
    this.arritmia = "NR";
    this.aneurismaAortaAbdominal = "NR";
    this.enfermedadVascularPeriferica = "NR";
    this.insuficienciaCardiaca = "NR";
    this.acvIsquemico = "NR";
    this.acvHemorragico = "NR";
    this.depresion = "NR";
    this.demencia = "NR";
    this.institucionalizado = "NR";
    this.parkinson = "NR";
    this.irc = "NR";
    this.cirrosis = "NR";
    this.hda = "NR";
    this.hdb = "NR";
    this.diabetes = "NR";
    this.enfermedadTiroidea = "NR";
    this.bocio = "NR";
    this.epoc = "NR";
    this.asma = "NR";
    this.tepa = "NR";
    this.diatesisHemorragica = "NR";
    this.prostatismo = "NR";
    this.litiasisRenal = "NR";
    this.glaucoma = "NR";
    this.tombosisVenosaProfunda = "NR";
    this.menarca = "NR";
    this.edadMenarca = "";
    this.menopausia = "NR";
    this.edadMenopausia = "";
    this.gestas = "";
    this.partos = "";
    this.abortos = "";
    this.otros = new ArrayList<>();
    this.cancerColon = "NR";
    this.cancerPulmon = "NR";
    this.cancerMama = "NR";
    this.cancerOvario = "NR";
    this.cancerProstata = "NR";
    this.cancerRinon = "NR";
    this.lupusEritematosoSistemico = "NR";
    this.artritisReumatoidea = "NR";
    this.vasculitis = "NR";
    this.enfermedadesAutoinmunes = "NR";
    this.tratamientoCronicoConCorticoides = "NR";
    this.penicilina = "NR";
    this.iodo = "NR";
    this.dipirona = "NR";
    this.aspirina = "NR";
    this.otrasAlergias = new ArrayList<>();
    this.angioplastiaCoronaria = "NR";
    this.intervencionBypassCoronario = "NR";
    this.esplenectomia = "NR";
    this.colecistectomiaClasica = "NR";
    this.colecistectomiaPorVideo = "NR";
    this.apendicectomia = "NR";
    this.homoplastiaInguinal = "NR";
    this.amigdalectomia = "NR";
    this.hemorroidectomia = "NR";
    this.cesarea = "NR";
    this.histerectomia = "NR";
    this.transplantes = new ArrayList<>();
    this.otrosProcedimientos = new ArrayList<>();
    this.evolutions = new ArrayList<>();
    this.ultrasoundStudyReports = new ArrayList<>();
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Patient getPatient() {
    return patient;
  }

  public void setPatient(Patient patient) {
    this.patient = patient;
  }

  public String getAsmaFam() {
    return asmaFam;
  }

  public void setAsmaFam(String asmaFam) {
    this.asmaFam = asmaFam;
  }

  public String getHtaFam() {
    return htaFam;
  }

  public void setHtaFam(String htaFam) {
    this.htaFam = htaFam;
  }

  public String getDiabetesFam() {
    return diabetesFam;
  }

  public void setDiabetesFam(String diabetesFam) {
    this.diabetesFam = diabetesFam;
  }

  public String getDislipidemiaFam() {
    return dislipidemiaFam;
  }

  public void setDislipidemiaFam(String dislipidemiaFam) {
    this.dislipidemiaFam = dislipidemiaFam;
  }

  public String getIamFam() {
    return iamFam;
  }

  public void setIamFam(String iamFam) {
    this.iamFam = iamFam;
  }

  public String getCardiopatiaIsquemicaFam() {
    return cardiopatiaIsquemicaFam;
  }

  public void setCardiopatiaIsquemicaFam(String cardiopatiaIsquemicaFam) {
    this.cardiopatiaIsquemicaFam = cardiopatiaIsquemicaFam;
  }

  public String getAcvIsquemicoFam() {
    return acvIsquemicoFam;
  }

  public void setAcvIsquemicoFam(String acvIsquemicoFam) {
    this.acvIsquemicoFam = acvIsquemicoFam;
  }

  public String getAcvHemorragicoFam() {
    return acvHemorragicoFam;
  }

  public void setAcvHemorragicoFam(String acvHemorragicoFam) {
    this.acvHemorragicoFam = acvHemorragicoFam;
  }

  public String getCancerColonFam() {
    return cancerColonFam;
  }

  public void setCancerColonFam(String cancerColonFam) {
    this.cancerColonFam = cancerColonFam;
  }

  public String getCancerPulmonFam() {
    return cancerPulmonFam;
  }

  public void setCancerPulmonFam(String cancerPulmonFam) {
    this.cancerPulmonFam = cancerPulmonFam;
  }

  public String getCancerMamaFam() {
    return cancerMamaFam;
  }

  public void setCancerMamaFam(String cancerMamaFam) {
    this.cancerMamaFam = cancerMamaFam;
  }

  public String getCancerOvarioFam() {
    return cancerOvarioFam;
  }

  public void setCancerOvarioFam(String cancerOvarioFam) {
    this.cancerOvarioFam = cancerOvarioFam;
  }

  public String getCancerProstataFam() {
    return cancerProstataFam;
  }

  public void setCancerProstataFam(String cancerProstataFam) {
    this.cancerProstataFam = cancerProstataFam;
  }

  public String getCancerRinonFam() {
    return cancerRinonFam;
  }

  public void setCancerRinonFam(String cancerRinonFam) {
    this.cancerRinonFam = cancerRinonFam;
  }

  public String getEnfermedadTiroideaFam() {
    return enfermedadTiroideaFam;
  }

  public void setEnfermedadTiroideaFam(String enfermedadTiroideaFam) {
    this.enfermedadTiroideaFam = enfermedadTiroideaFam;
  }

  public List<String> getOtrosAntecedentesFam() {
    return otrosAntecedentesFam;
  }

  public void setOtrosAntecedentesFam(List<String> otrosAntecedentesFam) {
    this.otrosAntecedentesFam = otrosAntecedentesFam;
  }

  public String getObservacionesFam() {
    return observacionesFam;
  }

  public void setObservacionesFam(String observacionesFam) {
    this.observacionesFam = observacionesFam;
  }

  public String getHiv() {
    return hiv;
  }

  public void setHiv(String hiv) {
    this.hiv = hiv;
  }

  public String getTabaquismo() {
    return tabaquismo;
  }

  public void setTabaquismo(String tabaquismo) {
    this.tabaquismo = tabaquismo;
  }

  public String getAlcoholismo() {
    return alcoholismo;
  }

  public void setAlcoholismo(String alcoholismo) {
    this.alcoholismo = alcoholismo;
  }

  public String getAniosFumando() {
    return aniosFumando;
  }

  public void setAniosFumando(String aniosFumando) {
    this.aniosFumando = aniosFumando;
  }

  public String getCigarrillosDiarios() {
    return cigarrillosDiarios;
  }

  public void setCigarrillosDiarios(String cigarrillosDiarios) {
    this.cigarrillosDiarios = cigarrillosDiarios;
  }

  public String getSedentarismo() {
    return sedentarismo;
  }

  public void setSedentarismo(String sedentarismo) {
    this.sedentarismo = sedentarismo;
  }

  public String getEjercicioDiario() {
    return ejercicioDiario;
  }

  public void setEjercicioDiario(String ejercicioDiario) {
    this.ejercicioDiario = ejercicioDiario;
  }

  public String getHta() {
    return hta;
  }

  public void setHta(String hta) {
    this.hta = hta;
  }

  public String getAntiHipertensivos() {
    return antiHipertensivos;
  }

  public void setAntiHipertensivos(String antiHipertensivos) {
    this.antiHipertensivos = antiHipertensivos;
  }

  public String getDislipidemia() {
    return dislipidemia;
  }

  public void setDislipidemia(String dislipidemia) {
    this.dislipidemia = dislipidemia;
  }

  public String getIam() {
    return iam;
  }

  public void setIam(String iam) {
    this.iam = iam;
  }

  public String getEnfermedadCoronaria() {
    return enfermedadCoronaria;
  }

  public void setEnfermedadCoronaria(String enfermedadCoronaria) {
    this.enfermedadCoronaria = enfermedadCoronaria;
  }

  public String getArritmia() {
    return arritmia;
  }

  public void setArritmia(String arritmia) {
    this.arritmia = arritmia;
  }

  public String getAneurismaAortaAbdominal() {
    return aneurismaAortaAbdominal;
  }

  public void setAneurismaAortaAbdominal(String aneurismaAortaAbdominal) {
    this.aneurismaAortaAbdominal = aneurismaAortaAbdominal;
  }

  public String getEnfermedadVascularPeriferica() {
    return enfermedadVascularPeriferica;
  }

  public void setEnfermedadVascularPeriferica(
    String enfermedadVascularPeriferica
  ) {
    this.enfermedadVascularPeriferica = enfermedadVascularPeriferica;
  }

  public String getInsuficienciaCardiaca() {
    return insuficienciaCardiaca;
  }

  public void setInsuficienciaCardiaca(String insuficienciaCardiaca) {
    this.insuficienciaCardiaca = insuficienciaCardiaca;
  }

  public String getAcvIsquemico() {
    return acvIsquemico;
  }

  public void setAcvIsquemico(String acvIsquemico) {
    this.acvIsquemico = acvIsquemico;
  }

  public String getAcvHemorragico() {
    return acvHemorragico;
  }

  public void setAcvHemorragico(String acvHemorragico) {
    this.acvHemorragico = acvHemorragico;
  }

  public String getDepresion() {
    return depresion;
  }

  public void setDepresion(String depresion) {
    this.depresion = depresion;
  }

  public String getDemencia() {
    return demencia;
  }

  public void setDemencia(String demencia) {
    this.demencia = demencia;
  }

  public String getInstitucionalizado() {
    return institucionalizado;
  }

  public void setInstitucionalizado(String institucionalizado) {
    this.institucionalizado = institucionalizado;
  }

  public String getParkinson() {
    return parkinson;
  }

  public void setParkinson(String parkinson) {
    this.parkinson = parkinson;
  }

  public String getIrc() {
    return irc;
  }

  public void setIrc(String irc) {
    this.irc = irc;
  }

  public String getCirrosis() {
    return cirrosis;
  }

  public void setCirrosis(String cirrosis) {
    this.cirrosis = cirrosis;
  }

  public String getHda() {
    return hda;
  }

  public void setHda(String hda) {
    this.hda = hda;
  }

  public String getHdb() {
    return hdb;
  }

  public void setHdb(String hdb) {
    this.hdb = hdb;
  }

  public String getDiabetes() {
    return diabetes;
  }

  public void setDiabetes(String diabetes) {
    this.diabetes = diabetes;
  }

  public String getEnfermedadTiroidea() {
    return enfermedadTiroidea;
  }

  public void setEnfermedadTiroidea(String enfermedadTiroidea) {
    this.enfermedadTiroidea = enfermedadTiroidea;
  }

  public String getBocio() {
    return bocio;
  }

  public void setBocio(String bocio) {
    this.bocio = bocio;
  }

  public String getEpoc() {
    return epoc;
  }

  public void setEpoc(String epoc) {
    this.epoc = epoc;
  }

  public String getAsma() {
    return asma;
  }

  public void setAsma(String asma) {
    this.asma = asma;
  }

  public String getTepa() {
    return tepa;
  }

  public void setTepa(String tepa) {
    this.tepa = tepa;
  }

  public String getDiatesisHemorragica() {
    return diatesisHemorragica;
  }

  public void setDiatesisHemorragica(String diatesisHemorragica) {
    this.diatesisHemorragica = diatesisHemorragica;
  }

  public String getProstatismo() {
    return prostatismo;
  }

  public void setProstatismo(String prostatismo) {
    this.prostatismo = prostatismo;
  }

  public String getLitiasisRenal() {
    return litiasisRenal;
  }

  public void setLitiasisRenal(String litiasisRenal) {
    this.litiasisRenal = litiasisRenal;
  }

  public String getGlaucoma() {
    return glaucoma;
  }

  public void setGlaucoma(String glaucoma) {
    this.glaucoma = glaucoma;
  }

  public String getTombosisVenosaProfunda() {
    return tombosisVenosaProfunda;
  }

  public void setTombosisVenosaProfunda(String tombosisVenosaProfunda) {
    this.tombosisVenosaProfunda = tombosisVenosaProfunda;
  }

  public String getMenarca() {
    return menarca;
  }

  public void setMenarca(String menarca) {
    this.menarca = menarca;
  }

  public String getEdadMenarca() {
    return edadMenarca;
  }

  public void setEdadMenarca(String edadMenarca) {
    this.edadMenarca = edadMenarca;
  }

  public String getMenopausia() {
    return menopausia;
  }

  public void setMenopausia(String menopausia) {
    this.menopausia = menopausia;
  }

  public String getEdadMenopausia() {
    return edadMenopausia;
  }

  public void setEdadMenopausia(String edadMenopausia) {
    this.edadMenopausia = edadMenopausia;
  }

  public String getGestas() {
    return gestas;
  }

  public void setGestas(String gestas) {
    this.gestas = gestas;
  }

  public String getPartos() {
    return partos;
  }

  public void setPartos(String partos) {
    this.partos = partos;
  }

  public String getAbortos() {
    return abortos;
  }

  public void setAbortos(String abortos) {
    this.abortos = abortos;
  }

  public List<String> getOtros() {
    return otros;
  }

  public void setOtros(List<String> otros) {
    this.otros = otros;
  }

  public String getCancerColon() {
    return cancerColon;
  }

  public void setCancerColon(String cancerColon) {
    this.cancerColon = cancerColon;
  }

  public String getCancerPulmon() {
    return cancerPulmon;
  }

  public void setCancerPulmon(String cancerPulmon) {
    this.cancerPulmon = cancerPulmon;
  }

  public String getCancerMama() {
    return cancerMama;
  }

  public void setCancerMama(String cancerMama) {
    this.cancerMama = cancerMama;
  }

  public String getCancerOvario() {
    return cancerOvario;
  }

  public void setCancerOvario(String cancerOvario) {
    this.cancerOvario = cancerOvario;
  }

  public String getCancerProstata() {
    return cancerProstata;
  }

  public void setCancerProstata(String cancerProstata) {
    this.cancerProstata = cancerProstata;
  }

  public String getCancerRinon() {
    return cancerRinon;
  }

  public void setCancerRinon(String cancerRinon) {
    this.cancerRinon = cancerRinon;
  }

  public String getLupusEritematosoSistemico() {
    return lupusEritematosoSistemico;
  }

  public void setLupusEritematosoSistemico(String lupusEritematosoSistemico) {
    this.lupusEritematosoSistemico = lupusEritematosoSistemico;
  }

  public String getArtritisReumatoidea() {
    return artritisReumatoidea;
  }

  public void setArtritisReumatoidea(String artritisReumatoidea) {
    this.artritisReumatoidea = artritisReumatoidea;
  }

  public String getVasculitis() {
    return vasculitis;
  }

  public void setVasculitis(String vasculitis) {
    this.vasculitis = vasculitis;
  }

  public String getEnfermedadesAutoinmunes() {
    return enfermedadesAutoinmunes;
  }

  public void setEnfermedadesAutoinmunes(String enfermedadesAutoinmunes) {
    this.enfermedadesAutoinmunes = enfermedadesAutoinmunes;
  }

  public String getTratamientoCronicoConCorticoides() {
    return tratamientoCronicoConCorticoides;
  }

  public void setTratamientoCronicoConCorticoides(
    String tratamientoCronicoConCorticoides
  ) {
    this.tratamientoCronicoConCorticoides = tratamientoCronicoConCorticoides;
  }

  public String getPenicilina() {
    return penicilina;
  }

  public void setPenicilina(String penicilina) {
    this.penicilina = penicilina;
  }

  public String getIodo() {
    return iodo;
  }

  public void setIodo(String iodo) {
    this.iodo = iodo;
  }

  public String getDipirona() {
    return dipirona;
  }

  public void setDipirona(String dipirona) {
    this.dipirona = dipirona;
  }

  public String getAspirina() {
    return aspirina;
  }

  public void setAspirina(String aspirina) {
    this.aspirina = aspirina;
  }

  public List<String> getOtrasAlergias() {
    return otrasAlergias;
  }

  public void setOtrasAlergias(List<String> otrasAlergias) {
    this.otrasAlergias = otrasAlergias;
  }

  public String getAngioplastiaCoronaria() {
    return angioplastiaCoronaria;
  }

  public void setAngioplastiaCoronaria(String angioplastiaCoronaria) {
    this.angioplastiaCoronaria = angioplastiaCoronaria;
  }

  public String getIntervencionBypassCoronario() {
    return intervencionBypassCoronario;
  }

  public void setIntervencionBypassCoronario(
    String intervencionBypassCoronario
  ) {
    this.intervencionBypassCoronario = intervencionBypassCoronario;
  }

  public String getEsplenectomia() {
    return esplenectomia;
  }

  public void setEsplenectomia(String esplenectomia) {
    this.esplenectomia = esplenectomia;
  }

  public String getColecistectomiaClasica() {
    return colecistectomiaClasica;
  }

  public void setColecistectomiaClasica(String colecistectomiaClasica) {
    this.colecistectomiaClasica = colecistectomiaClasica;
  }

  public String getColecistectomiaPorVideo() {
    return colecistectomiaPorVideo;
  }

  public void setColecistectomiaPorVideo(String colecistectomiaPorVideo) {
    this.colecistectomiaPorVideo = colecistectomiaPorVideo;
  }

  public String getApendicectomia() {
    return apendicectomia;
  }

  public void setApendicectomia(String apendicectomia) {
    this.apendicectomia = apendicectomia;
  }

  public String getHomoplastiaInguinal() {
    return homoplastiaInguinal;
  }

  public void setHomoplastiaInguinal(String homoplastiaInguinal) {
    this.homoplastiaInguinal = homoplastiaInguinal;
  }

  public String getAmigdalectomia() {
    return amigdalectomia;
  }

  public void setAmigdalectomia(String amigdalectomia) {
    this.amigdalectomia = amigdalectomia;
  }

  public String getHemorroidectomia() {
    return hemorroidectomia;
  }

  public void setHemorroidectomia(String hemorroidectomia) {
    this.hemorroidectomia = hemorroidectomia;
  }

  public String getCesarea() {
    return cesarea;
  }

  public void setCesarea(String cesarea) {
    this.cesarea = cesarea;
  }

  public String getHisterectomia() {
    return histerectomia;
  }

  public void setHisterectomia(String histerectomia) {
    this.histerectomia = histerectomia;
  }

  public List<String> getTransplantes() {
    return transplantes;
  }

  public void setTransplantes(List<String> transplantes) {
    this.transplantes = transplantes;
  }

  public List<String> getOtrosProcedimientos() {
    return otrosProcedimientos;
  }

  public void setOtrosProcedimientos(List<String> otrosProcedimientos) {
    this.otrosProcedimientos = otrosProcedimientos;
  }

  public List<Evolution> getEvolutions() {
    return this.evolutions;
  }

  public void setEvolutions(List<Evolution> evolutions) {
    this.evolutions = evolutions;
  }

  public void addEvolution(Evolution evolution) {
    this.evolutions.add(evolution);
  }

  public void removeEvolution(Evolution evolution) {
    this.evolutions.remove(evolution);
  }

  public List<UltrasoundStudyReport> getUltrasoundStudyReports() {
    return this.ultrasoundStudyReports;
  }

  public void setUltrasoundStudyReports(
    List<UltrasoundStudyReport> ultrasoundStudyReports
  ) {
    this.ultrasoundStudyReports = ultrasoundStudyReports;
  }

  public void addUltrasoundStudyReport(
    UltrasoundStudyReport ultrasoundStudyReport
  ) {
    this.ultrasoundStudyReports.add(ultrasoundStudyReport);
  }

  public void removeUltrasoundStudyReport(
    UltrasoundStudyReport ultrasoundStudyReport
  ) {
    this.ultrasoundStudyReports.remove(ultrasoundStudyReport);
  }
}
