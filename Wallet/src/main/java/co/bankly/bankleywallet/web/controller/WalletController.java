package co.bankly.bankleywallet.web.controller;

import co.bankly.bankleywallet.proxies.dto.OperationDTO;
import co.bankly.bankleywallet.services.WalletService;
import co.bankly.bankleywallet.web.controller.dto.AccountDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallet")
public class WalletController {
    final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }
    @GetMapping("/deposit")
    public ResponseEntity<OperationDTO> deposit(@RequestParam double amount, @RequestHeader("userId") long userId){
        return ResponseEntity.ok(walletService.deposit(amount,userId));
    }
    @GetMapping("/withdraw")
    public ResponseEntity<OperationDTO> withdraw(@RequestParam double amount,@RequestHeader("userId") long userId){
        return ResponseEntity.ok(walletService.withdraw(amount,userId));
    }
    @GetMapping("")
    public ResponseEntity<AccountDTO> getAccountInf(@RequestHeader("userId") Long userId){
        return ResponseEntity.ok(walletService.getAccount(userId));
    }

}
