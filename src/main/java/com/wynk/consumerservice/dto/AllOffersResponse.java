package com.wynk.consumerservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AllOffersResponse extends ResponseData<AllOffersResponse> {

  List<Offer> allOffers;

  public AllOffersResponse() {
    super(AllOffersResponse.class);
  }
}
