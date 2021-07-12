package com.bigdataConcept.akka.serverless.ewallet.domain;

import com.akkaserverless.javasdk.EntityId;
import com.akkaserverless.javasdk.Reply;
import com.akkaserverless.javasdk.eventsourcedentity.*;
import com.bigdataConcept.akka.serverless.ewallet.WalletApi;

/** An event sourced entity. */
public abstract class WalletInterface {
    
    public class CommandNotImplementedException extends UnsupportedOperationException {
        public CommandNotImplementedException() {
            super("You have either created a new command or removed the handling of an existing command. Please declare a method in your \"impl\" class for this command.");
        }
    }
    
    @Snapshot
    public abstract WalletDomain.WalletState snapshot();
    
    @SnapshotHandler
    public abstract void handleSnapshot(WalletDomain.WalletState snapshot);
    
    @CommandHandler(name = "Withdraw")
    public Reply<WalletApi.TransactionResponse> withdrawWithReply(WalletApi.DebitCommand command, CommandContext ctx) {
        return Reply.message(withdraw(command, ctx));
    }
    
    protected WalletApi.TransactionResponse withdraw(WalletApi.DebitCommand command, CommandContext ctx) {
        return withdraw(command);
    }
    
    protected WalletApi.TransactionResponse withdraw(WalletApi.DebitCommand command) {
        throw new CommandNotImplementedException();
    }
    
    @CommandHandler(name = "CreateWallet")
    public Reply<WalletApi.WalletResponse> createWalletWithReply(WalletApi.CreateWalletCommand command, CommandContext ctx) {
        return Reply.message(createWallet(command, ctx));
    }
    
    protected WalletApi.WalletResponse createWallet(WalletApi.CreateWalletCommand command, CommandContext ctx) {
        return createWallet(command);
    }
    
    protected WalletApi.WalletResponse createWallet(WalletApi.CreateWalletCommand command) {
        throw new CommandNotImplementedException();
    }
    
    @CommandHandler(name = "Deposit")
    public Reply<WalletApi.TransactionResponse> depositWithReply(WalletApi.CreditCommand command, CommandContext ctx) {
        return Reply.message(deposit(command, ctx));
    }
    
    protected WalletApi.TransactionResponse deposit(WalletApi.CreditCommand command, CommandContext ctx) {
        return deposit(command);
    }
    
    protected WalletApi.TransactionResponse deposit(WalletApi.CreditCommand command) {
        throw new CommandNotImplementedException();
    }
    
    @CommandHandler(name = "GetMiniStatement")
    public Reply<WalletApi.MiniStatement> getMiniStatementWithReply(WalletApi.RequestMiniStatement command, CommandContext ctx) {
        return Reply.message(getMiniStatement(command, ctx));
    }
    
    protected WalletApi.MiniStatement getMiniStatement(WalletApi.RequestMiniStatement command, CommandContext ctx) {
        return getMiniStatement(command);
    }
    
    protected WalletApi.MiniStatement getMiniStatement(WalletApi.RequestMiniStatement command) {
        throw new CommandNotImplementedException();
    }
    
    @CommandHandler(name = "GetProfile")
    public Reply<WalletApi.ProfileResponse> getProfileWithReply(WalletApi.RequestProfile command, CommandContext ctx) {
        return Reply.message(getProfile(command, ctx));
    }
    
    protected WalletApi.ProfileResponse getProfile(WalletApi.RequestProfile command, CommandContext ctx) {
        return getProfile(command);
    }
    
    protected WalletApi.ProfileResponse getProfile(WalletApi.RequestProfile command) {
        throw new CommandNotImplementedException();
    }
    
    @EventHandler
    public abstract void walletCreatedEvent(WalletDomain.WalletCreatedEvent event);
    
    @EventHandler
    public abstract void walletCreditedEvent(WalletDomain.WalletCreditedEvent event);
    
    @EventHandler
    public abstract void walletDebitedEvent(WalletDomain.WalletDebitedEvent event);
}