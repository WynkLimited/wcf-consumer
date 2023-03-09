package in.wynk.consumerservice.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.annotation.Id;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public abstract class BaseEntity {

    @Id
    private String id;
}
