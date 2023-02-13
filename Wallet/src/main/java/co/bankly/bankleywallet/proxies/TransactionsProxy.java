package co.bankly.bankleywallet.proxies;

import co.bankly.bankleywallet.proxies.dto.OperationDTO;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
@FeignClient(name = "operations-service",url = "http://localhost:9106/operations")
@RibbonClient(name="operations-service")
public interface TransactionsProxy {

    @PostMapping("/add")
    public OperationDTO createTransaction(OperationDTO operation);
}
