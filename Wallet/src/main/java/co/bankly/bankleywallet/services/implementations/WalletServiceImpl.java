package co.bankly.bankleywallet.services.implementations;

import co.bankly.bankleywallet.beans.OperationBean;
import co.bankly.bankleywallet.entity.Account;
import co.bankly.bankleywallet.exceptions.InsufficientBalanceException;
import co.bankly.bankleywallet.proxies.TransactionsProxy;
import co.bankly.bankleywallet.proxies.dto.OperationDTO;
import co.bankly.bankleywallet.repositories.AccountRepository;
import co.bankly.bankleywallet.services.WalletService;
import co.bankly.bankleywallet.web.controller.dto.AccountDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class WalletServiceImpl implements WalletService {
    final TransactionsProxy transactionsProxy;
    final AccountRepository accountRepository;

    public WalletServiceImpl(TransactionsProxy transactionsProxy, AccountRepository accountRepository) {
        this.transactionsProxy = transactionsProxy;
        this.accountRepository = accountRepository;
    }



    @Override
    public OperationDTO deposit(double amount, long userId) {
        //call the transactions service and create a transaction
        Account account=null;
        Optional<Account> optionalAccount=accountRepository.findByOwnerId(userId);
        if(!optionalAccount.isPresent()){
            // if the user does not already have an account let's make him a new empty one
           account=Account.builder()
                    .balance(0)
                    .ownerId(userId)
                    .createdAt(new Date())
                    .closedAt(null)
                    .isFrozen(false).build();
            account=accountRepository.save(account);

        }else
            account=optionalAccount.get();
        account.setBalance(account.getBalance()+amount);
        OperationDTO operationDTO=OperationDTO.builder()
                .type(OperationBean.OperationTypeEnum.DEPOSIT)
                        .accountId(account.getId())
                .amount(amount).build();
        accountRepository.save(account);
        return  transactionsProxy.createTransaction(operationDTO);
    }



    @Override
    public OperationDTO withdraw(double amount, long userId) {
        //call the transactions service and create a transaction
        Account account=null;
        Optional<Account> optionalAccount=accountRepository.findByOwnerId(userId);
        if(!optionalAccount.isPresent()){
            // if the user does not already have an account let's make him a new empty one
            account=Account.builder()
                    .balance(0d)
                    .ownerId(userId)
                    .createdAt(new Date())
                    .closedAt(null)
                    .isFrozen(false).build();
            account=accountRepository.save(account);

        }else
            account=optionalAccount.get();
        if(account.getBalance()<amount){
            throw new InsufficientBalanceException();
        }
        account.setBalance(account.getBalance()-amount);
        OperationDTO operationDTO=OperationDTO.builder()
                .type(OperationBean.OperationTypeEnum.WITHDRAWAL)
                .accountId(account.getId())
                .amount(amount).build();
        accountRepository.save(account);
        return  transactionsProxy.createTransaction(operationDTO);
    }

    @Override
    public AccountDTO getAccount(long userId) {
        Account account=null;
        Optional<Account> optionalAccount=accountRepository.findByOwnerId(userId);
        if(!optionalAccount.isPresent()){
            // if the user does not already have an account let's make him a new empty one
            account=Account.builder()
                    .balance(0d)
                    .ownerId(userId)
                    .createdAt(new Date())
                    .closedAt(null)
                    .isFrozen(false).build();
            account=accountRepository.save(account);
        }else
            account=optionalAccount.get();
        return AccountDTO.builder()
                .id(account.getId())
                .closedAt(account.getClosedAt())
                .createdAt(account.getCreatedAt())
                .ownerId(account.getOwnerId())
                .balance(account.getBalance())
                .isFrozen(account.isFrozen()).build();
    }
}
