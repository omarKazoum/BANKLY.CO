package co.bankly.bankleywallet.web.controller.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;
@Data
@Builder
public class AccountDTO {
    String id;
    long ownerId;
    Date createdAt;
    Date closedAt;
    boolean isFrozen;
    double balance;
}
