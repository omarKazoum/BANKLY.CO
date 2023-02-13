package co.bankly.transactions.repositories;

import co.bankly.transactions.entity.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationRepository extends MongoRepository<Operation,String> {
    Page<Operation> findByAccountId(String accountId, Pageable pageable);
}
