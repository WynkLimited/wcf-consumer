package com.wynk.consumerservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AllPlansResponse extends ResponseData<AllPlansResponse> {
  List<PlanDTO> plans;

  public AllPlansResponse() {
    super(AllPlansResponse.class);
  }
}
