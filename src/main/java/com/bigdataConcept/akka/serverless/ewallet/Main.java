package com.bigdataConcept.akka.serverless.ewallet;

import com.akkaserverless.javasdk.AkkaServerless;
import com.bigdataConcept.akka.serverless.ewallet.domain.WalletDomain;
import com.bigdataConcept.akka.serverless.ewallet.domain.WalletImpl;
import com.bigdataConcept.akka.serverless.ewallet.view.WalletViewAPI;
import com.bigdataConcept.akka.serverless.ewallet.view.WalletViewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Main {
    
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);


    public static AkkaServerless walletServices =
            new AkkaServerless()
                    .registerEventSourcedEntity(
                            WalletImpl.class,
                            WalletApi.getDescriptor().findServiceByName("WalletService"),
                            WalletDomain.getDescriptor()
                    )
                    .registerView(
            WalletViewService .class,
            WalletViewAPI.getDescriptor()
                                    .findServiceByName("WalletView"),
                            "walletTblQuery",
                            WalletDomain.getDescriptor(),
                            WalletViewAPI.getDescriptor());

    public static void main(String[] args) throws Exception {
        LOG.info("starting the Akka Serverless service");
        walletServices.start().toCompletableFuture().get();
    }
}