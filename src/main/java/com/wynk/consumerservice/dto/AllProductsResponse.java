package com.wynk.consumerservice.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Data
public class AllProductsResponse {

  List<Product> allProducts;

  @Data
  public static class Product {
    Integer id;
    Integer hierarchy;
    State state;
    String service;
    String packGroup;
    String title;
    String cpName;
    List<String> eligibleAppIds;
    Map<String, Object> features;
  }
}
