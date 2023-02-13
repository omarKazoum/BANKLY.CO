package co.bankly.transactions;

import co.bankly.transactions.entity.Operation;
import co.bankly.transactions.repositories.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import java.util.Date;

@SpringBootApplication
@EnableDiscoveryClient
public class TransactionsServiceApplication implements CommandLineRunner {
    @Autowired
    OperationRepository operationRepository;

    public static void main(String[] args) {
        SpringApplication.run(TransactionsServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    //    operationRepository.save(Operation.builder().id("sdsds").amount(10).createdAt(new Date()).accountId("ZAZAzez").type(Operation.OperationTypeEnum.WITHDRAWAL).build());

    }
}
