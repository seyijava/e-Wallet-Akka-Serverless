package com.bigdataConcept.akka.serverless.ewallet.domain;

import com.akkaserverless.javasdk.AkkaServerless;
import com.bigdataConcept.akka.serverless.ewallet.Main;
import com.bigdataConcept.akka.serverless.ewallet.MainComponentRegistrations;
import com.bigdataConcept.akka.serverless.ewallet.WalletServiceClient;
import com.akkaserverless.javasdk.testkit.junit.AkkaServerlessTestkitResource;
import org.junit.ClassRule;
import org.junit.Test;
import static com.bigdataConcept.akka.serverless.ewallet.MainComponentRegistrations.*;
import static java.util.concurrent.TimeUnit.*;

// Example of an integration test calling our service via the Akka Serverless proxy
// Run all test classes ending with "IntegrationTest" using `mvn verify -Pit`
public class WalletIntegrationTest {
    
    /**
     * The test kit starts both the service container and the Akka Serverless proxy.
     */
    @ClassRule
    public static final AkkaServerlessTestkitResource testkit = new AkkaServerlessTestkitResource(MainComponentRegistrations.withGeneratedComponentsAdded(new AkkaServerless()));
    
    /**
     * Use the generated gRPC client to call the service through the Akka Serverless proxy.
     */
    private final WalletServiceClient client;
    
    public WalletIntegrationTest() {
       client = WalletServiceClient.create(testkit.getGrpcClientSettings(), testkit.getActorSystem());


    }
    
    @Test
    public void debitOnNonExistingEntity() throws Exception {
        // TODO: set fields in command, and provide assertions to match replies
        // client.debit(WalletApi.DebitCommand.newBuilder().build())
        //         .toCompletableFuture().get(2, SECONDS);
        System.out.println("\n\n\n\n" + client);
    }
    
    @Test
    public void createWalletOnNonExistingEntity() throws Exception {
        // TODO: set fields in command, and provide assertions to match replies
        // client.createWallet(WalletApi.CreateWalletCommand.newBuilder().build())
        //         .toCompletableFuture().get(2, SECONDS);
    }
    
    @Test
    public void creditOnNonExistingEntity() throws Exception {
        // TODO: set fields in command, and provide assertions to match replies
        // client.credit(WalletApi.CreditCommand.newBuilder().build())
        //         .toCompletableFuture().get(2, SECONDS);
    }
}