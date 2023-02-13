package co.bankly.bankleywallet.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.BAD_REQUEST,reason = "solde inssufisant")
public class InsufficientBalanceException extends RuntimeException{
    public InsufficientBalanceException(){
        super("You do not have enough funds in your account! ");
    }
    
}
