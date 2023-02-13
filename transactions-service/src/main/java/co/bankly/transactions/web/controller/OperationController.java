package co.bankly.transactions.web.controller;

import co.bankly.transactions.entity.Operation;
import co.bankly.transactions.services.TransactionService;
import co.bankly.transactions.web.dto.OperationDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/operations")
public class OperationController {
    final TransactionService transactionService;

    public OperationController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    @PostMapping("/add")
    public ResponseEntity<Operation> createTransaction(@RequestBody OperationDTO operation){
        return ResponseEntity.ok(this.transactionService.createOperation(operation));
    }
    @GetMapping("")
    public ResponseEntity<Page<OperationDTO>> getOperations(@RequestParam String accountId,
                                                            @RequestParam(value = "page",required = false,defaultValue = "0") int pageIndex,
                                                            @RequestParam(name = "size", required = false,defaultValue = "0") Integer size){
        return ResponseEntity.ok(transactionService.getOperationsForAccount(accountId,pageIndex,size));
    }


}
