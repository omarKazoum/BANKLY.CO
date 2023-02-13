package co.bankly.bankleywallet.repositories;

import co.bankly.bankleywallet.entity.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface AccountRepository extends MongoRepository< Account,String> {
    Optional<Account> findByOwnerId(long ownerId);
}
