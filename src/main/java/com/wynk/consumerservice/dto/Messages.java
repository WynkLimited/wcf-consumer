package com.wynk.consumerservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class Messages {

  private Message activation;
  private Message deactivation;
  private Message renewal;
  private Message download;
  private List<ReminderMessage> reminder;
}
