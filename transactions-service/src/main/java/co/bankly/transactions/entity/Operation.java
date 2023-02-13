package co.bankly.transactions.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Operation {
    String id;
    String accountId;
    double amount;
    @JsonIgnore
    Date createdAt;
    OperationTypeEnum type;
    public enum OperationTypeEnum{
        WITHDRAWAL,
        DEPOSIT
    }
}
