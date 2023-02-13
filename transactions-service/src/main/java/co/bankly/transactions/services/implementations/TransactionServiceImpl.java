package co.bankly.transactions.services.implementations;

import co.bankly.transactions.entity.Operation;
import co.bankly.transactions.repositories.OperationRepository;
import co.bankly.transactions.services.TransactionService;
import co.bankly.transactions.web.dto.OperationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {
    final OperationRepository operationRepository;

    public TransactionServiceImpl(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    @Override
    public Operation createOperation(OperationDTO operation) {
        Operation operationToAdd=Operation.builder()
                .createdAt(new Date())
                .type(operation.getType())
                .accountId(operation.getAccountId())
                .amount(operation.getAmount()).build();
        return operationRepository.save(operationToAdd);

    }

    @Override
    public Page<OperationDTO> getOperationsForAccount(String accountId, int pageIndex, Integer size) {
            Page<Operation> operationsPage=operationRepository.findByAccountId(accountId,PageRequest.of(pageIndex, size));
            //if the user is a client he should only see approved and available hotels
        return  operationsPage.map(operation -> OperationDTO.builder()
                .accountId(operation.getAccountId()).id(operation.getId()).amount(operation.getAmount()).type(operation.getType()).build());
    }
}
