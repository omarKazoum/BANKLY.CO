package co.bankly.bankleywallet.beans;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
@Data
@Builder
public class OperationBean {
    String id;
    String accountId;
    double amount;
    Date createdAt;
    OperationTypeEnum type;
    public enum OperationTypeEnum{
        WITHDRAWAL,
        DEPOSIT
    }
}
