package co.bankly.bankleywallet.proxies.dto;

import co.bankly.bankleywallet.beans.OperationBean;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OperationDTO {
    String id;
    String accountId;
    double amount;
    OperationBean.OperationTypeEnum type;
}
