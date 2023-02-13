package co.bankly.bankleywallet.entity;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
@Document("accounts")
@Data
@Builder
public class Account {
    @Id
    String id;
    long ownerId;
    Date createdAt;
    Date closedAt;
    boolean isFrozen;
    double balance;
}
