package co.bankly.transactions.web.dto;

import co.bankly.transactions.entity.Operation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class OperationDTO {
    String id;
    String accountId;
    double amount;
    Operation.OperationTypeEnum type;
}
