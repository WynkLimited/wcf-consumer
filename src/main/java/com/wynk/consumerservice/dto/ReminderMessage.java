package com.wynk.consumerservice.dto;

import lombok.Data;

import java.util.concurrent.TimeUnit;

@Data
public class ReminderMessage {

  private int duration;
  private TimeUnit timeUnit;
  private boolean sms;
  private boolean appNotification;
}
