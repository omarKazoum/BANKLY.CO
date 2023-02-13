package co.bankly.transactions.services;

import co.bankly.transactions.entity.Operation;
import co.bankly.transactions.web.dto.OperationDTO;
import org.springframework.data.domain.Page;

public interface TransactionService {
    Operation createOperation(OperationDTO operation);

    Page<OperationDTO> getOperationsForAccount(String accountId, int pageIndex, Integer size);
}
