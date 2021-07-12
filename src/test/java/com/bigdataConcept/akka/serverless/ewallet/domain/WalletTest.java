package com.bigdataConcept.akka.serverless.ewallet.domain;

import com.akkaserverless.javasdk.eventsourcedentity.CommandContext;
import com.bigdataConcept.akka.serverless.ewallet.WalletApi;
import com.google.protobuf.Empty;
import org.junit.Test;
import org.mockito.*;

import static org.junit.Assert.assertThrows;

public class WalletTest {
    private String entityId = "entityId1";
    private WalletImpl entity;
    private CommandContext context = Mockito.mock(CommandContext.class);
    
    private class MockedContextFailure extends RuntimeException {};
    
    @Test
    public void debitTest() {
        entity = new WalletImpl(entityId);
        
        Mockito.when(context.fail("The command handler for `Debit` is not implemented, yet"))
            .thenReturn(new MockedContextFailure());
        
        // TODO: set fields in command, and update assertions to match implementation
        assertThrows(MockedContextFailure.class, () -> {
           // entity.debitWithReply(WalletApi.DebitCommand.newBuilder().build(), context);
        });
        
        // TODO: if you wish to verify events:
        //    Mockito.verify(context).emit(event);
    }
    
    @Test
    public void createWalletTest() {
        entity = new WalletImpl(entityId);
        
        Mockito.when(context.fail("The command handler for `CreateWallet` is not implemented, yet"))
            .thenReturn(new MockedContextFailure());
        
        // TODO: set fields in command, and update assertions to match implementation
        assertThrows(MockedContextFailure.class, () -> {
            entity.createWalletWithReply(WalletApi.CreateWalletCommand.newBuilder().build(), context);
        });
        
        // TODO: if you wish to verify events:
        //    Mockito.verify(context).emit(event);
    }
    
    @Test
    public void creditTest() {
        entity = new WalletImpl(entityId);
        
        Mockito.when(context.fail("The command handler for `Credit` is not implemented, yet"))
            .thenReturn(new MockedContextFailure());
        
        // TODO: set fields in command, and update assertions to match implementation
        assertThrows(MockedContextFailure.class, () -> {
            //entity.creditWithReply(WalletApi.CreditCommand.newBuilder().build(), context);
        });
        
        // TODO: if you wish to verify events:
        //    Mockito.verify(context).emit(event);
    }
}