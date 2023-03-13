package in.wynk.consumerservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class PriceDTO {

  private Double amount;
  private String currency;
  private List<DiscountDTO> discount;
}
