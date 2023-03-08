package com.wynk.consumerservice.dto;

import lombok.Data;

import java.util.concurrent.TimeUnit;

@Data
public class PlanPeriodDTO {

  private int validity;
  private TimeUnit timeUnit;
}
