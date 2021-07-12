package com.bigdataConcept.akka.serverless.ewallet.view;


import com.akkaserverless.javasdk.view.UpdateHandler;
import com.akkaserverless.javasdk.view.View;
import com.bigdataConcept.akka.serverless.ewallet.domain.WalletDomain;
import com.bigdataConcept.akka.serverless.ewallet.domain.WalletImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Optional;

@View
public class WalletViewService {

    private static final Logger logger = LoggerFactory.getLogger(WalletViewService.class);




    @UpdateHandler
    public WalletViewAPI.TransactionsHistory ProcessWalletCredited(WalletDomain.WalletCreditedEvent event){
        WalletViewAPI.TransactionsHistory transHistory = WalletViewAPI.TransactionsHistory.newBuilder()
                .setTransType(event.getTransType().name())
                .setAmount(event.getAmt().getAmount())
                .setTransactionDate(event.getTransactionDate())
                .setWalletId(event.getWalletId())
                .build();
         return transHistory;

    }


   @UpdateHandler
    public   WalletViewAPI.TransactionsHistory ProcessWalletDebited(WalletDomain.WalletDebitedEvent event){
        WalletViewAPI.TransactionsHistory transHistory = WalletViewAPI.TransactionsHistory.newBuilder()
                .setTransType(event.getTransType().name())
                .setAmount(event.getAmt().getAmount())
                .setTransactionDate(event.getTransactionDate())
                .setWalletId(event.getWalletId())
                .build();
        return transHistory;
    }
}
