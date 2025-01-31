package com.iagocharon.IGOF.Dto.Projections;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public interface WorkScheduleProjection {
  public UUID getId();

  public DayOfWeek getDay();

  public LocalTime getStart();

  public LocalTime getEnd();

  public LocalDate getStartDate();

  public LocalDate getEndDate();
}
