package in.wynk.consumerservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class AllPlansResponse {
  List<PlanDTO> plans;

}
