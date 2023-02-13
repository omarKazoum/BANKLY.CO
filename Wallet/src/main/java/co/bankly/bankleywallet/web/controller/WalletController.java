package co.bankly.bankleywallet.web.controller;

import co.bankly.bankleywallet.proxies.dto.OperationDTO;
import co.bankly.bankleywallet.services.WalletService;
import co.bankly.bankleywallet.web.controller.dto.AccountDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wallet")
public class WalletController {
    final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }
    @GetMapping("/deposit")
    public ResponseEntity<OperationDTO> deposit(@RequestParam double amount, @RequestParam long userId){
        return ResponseEntity.ok(walletService.deposit(amount,userId));
    }
    @GetMapping("/withdraw")
    public ResponseEntity<OperationDTO> withdraw(@RequestParam double amount,@RequestParam long userId){
        return ResponseEntity.ok(walletService.withdraw(amount,userId));
    }
    @GetMapping("")
    public ResponseEntity<AccountDTO> getAccountInf(long userId){
        return ResponseEntity.ok(walletService.getAccount(userId));
    }
}
