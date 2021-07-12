package com.bigdataConcept.akka.serverless.ewallet.domain;

import com.akkaserverless.javasdk.EntityId;
import com.akkaserverless.javasdk.eventsourcedentity.*;
import com.bigdataConcept.akka.serverless.ewallet.WalletApi;
import com.google.protobuf.Timestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


/** An event sourced entity. */
@EventSourcedEntity(entityType = "wallet")
public class WalletImpl extends WalletInterface {

    private static final Logger logger = LoggerFactory.getLogger(WalletImpl.class);
    @SuppressWarnings("unused")
    private final String entityId;

    List<WalletDomain.Transaction> transactionList;
    double balance ;
    WalletDomain.Profile profile;
    
    public WalletImpl(@EntityId String entityId) {
        logger.info("Calling Walling");
        this.entityId = entityId;
        this.transactionList = new ArrayList<WalletDomain.Transaction>();
        this.balance = 0;
        this.profile = WalletDomain.Profile.newBuilder().build();
    }
    
    @Override
    public WalletDomain.WalletState snapshot() {
        // TODO: produce state snapshot here
        logger.info("Snapshotting");
        return WalletDomain.WalletState.newBuilder()
                .setProfile(this.profile)
                .addAllTransactionHistory(this.transactionList)
                .setBalance(this.balance)
                .build();
    }
    
    @Override
    public void handleSnapshot(WalletDomain.WalletState snapshot) {
        // TODO: restore state from snapshot here
        logger.info("handleSnapShot");
        transactionList.clear();
        this.profile = snapshot.getProfile();
        this.transactionList = snapshot.getTransactionHistoryList();
        this.balance = snapshot.getBalance();
        
    }
    
    @Override
    protected WalletApi.TransactionResponse withdraw(WalletApi.DebitCommand command, CommandContext ctx) {
        logger.info("Debit Wallet" + command.getMoney().getAmount());
        if(command.getMoney().getAmount() > balance)
          return WalletApi.TransactionResponse.newBuilder().setMessage("Insufficient Funds").build();

        WalletDomain.WalletDebitedEvent walletDebitedEvent = WalletDomain.WalletDebitedEvent.newBuilder()
                .setAmt(command.getMoney())
                .setTransType(WalletDomain.TransactionType.Debit)
                .setTransactionDate(initTransactionDate())
                .setWalletId(this.entityId)
               .build();
        ctx.emit(walletDebitedEvent);
        return WalletApi.TransactionResponse.newBuilder().setMessage("Wallet Debited Successfully").build();
    }
    
    @Override
    protected WalletApi.WalletResponse createWallet(WalletApi.CreateWalletCommand command, CommandContext ctx) {
        logger.info("Create Wallet" + command.getProfile());
        if(command == null || command.getProfile() == null) return WalletApi.WalletResponse.newBuilder().setMessage("Profile details must not be empty").build();

        WalletDomain.WalletCreatedEvent walletCreatedEvent = WalletDomain.WalletCreatedEvent.newBuilder()
                .setProfile(command.getProfile())
                .build();
        ctx.emit(walletCreatedEvent);
        return WalletApi.WalletResponse.newBuilder().setMessage("Wallet Created Successfully").build();
    }
    
    @Override
    protected WalletApi.TransactionResponse deposit(WalletApi.CreditCommand command, CommandContext ctx) {
        logger.info("Credit from Wallet");
        WalletDomain.WalletCreditedEvent walletCreditedEvent = WalletDomain.WalletCreditedEvent.newBuilder()
                .setAmt(command.getMoney())
                .setTransactionDate(initTransactionDate())
                .setTransType(WalletDomain.TransactionType.Credit)
                .setWalletId(this.entityId)
                .build();
        ctx.emit(walletCreditedEvent);
        return WalletApi.TransactionResponse.newBuilder().setMessage("Wallet Credited Successfully").build();
    }
    
    @Override
    protected WalletApi.MiniStatement getMiniStatement(WalletApi.RequestMiniStatement command, CommandContext ctx) {
        logger.info("Get MiniStatement ");
         WalletApi.MiniStatement miniStatement = WalletApi.MiniStatement.newBuilder()
                .setBalance(balance)
                .addAllTransactions(this.transactionList)
                .build();
              return miniStatement;

    }

    @Override
    protected WalletApi.ProfileResponse getProfile(WalletApi.RequestProfile command, CommandContext ctx) {
         logger.info("GetProfile" + profile.getEmail());
        WalletApi.ProfileResponse profileResponse = WalletApi.ProfileResponse.newBuilder()
                .setProfile(this.profile)
                .build();
        return profileResponse;
    }
    
    @Override
    public void walletCreatedEvent(WalletDomain.WalletCreatedEvent event) {
        logger.info("WalletCreatedEvent" + event.getProfile().getEmail());
        this.profile = event.getProfile();
    }
    
    @Override
    public void walletCreditedEvent(WalletDomain.WalletCreditedEvent event) {
        logger.info("Wallet Credited Event");
        transactionList.add(WalletDomain.Transaction.newBuilder()
                .setTransactionDate(event.getTransactionDate())
                .setAmount(event.getAmt().getAmount())
                .setTransactionType(event.getTransType())
                .build());
        balance +=  event.getAmt().getAmount();
    }
    
    @Override
    public void walletDebitedEvent(WalletDomain.WalletDebitedEvent event) {
        logger.info("Wallet Debited Event");
       transactionList.add(WalletDomain.Transaction.newBuilder()
       .setTransactionDate(event.getTransactionDate())
        .setTransactionType(event.getTransType())
               .setAmount(event.getAmt().getAmount())
               .build());
       balance -= event.getAmt().getAmount();
    }

    private com.google.protobuf.Timestamp initTransactionDate(){
        return   Timestamp.newBuilder()
                .setSeconds(Instant.now().getEpochSecond())
                .setNanos(Instant.now().getNano())
                .build();
    }

}