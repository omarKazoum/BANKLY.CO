package co.bankly.bankleywallet.services;

import co.bankly.bankleywallet.proxies.dto.OperationDTO;
import co.bankly.bankleywallet.web.controller.dto.AccountDTO;

public interface WalletService {

    OperationDTO deposit(double amount, long userId);

    OperationDTO withdraw(double amount, long userId);

    AccountDTO getAccount(long userId);
}
