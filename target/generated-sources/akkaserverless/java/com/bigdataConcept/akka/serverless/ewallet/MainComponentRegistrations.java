package com.bigdataConcept.akka.serverless.ewallet;

import com.akkaserverless.javasdk.AkkaServerless;
import com.bigdataConcept.akka.serverless.ewallet.domain.WalletDomain;
import com.bigdataConcept.akka.serverless.ewallet.domain.WalletImpl;

public final class MainComponentRegistrations {
    
    public static AkkaServerless withGeneratedComponentsAdded(AkkaServerless akkaServerless) {
        return akkaServerless
                .registerEventSourcedEntity(
                    WalletImpl.class,
                    WalletApi.getDescriptor().findServiceByName("WalletService"),
                    WalletDomain.getDescriptor()
                );
    }
}