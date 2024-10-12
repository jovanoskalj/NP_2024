package aud4.account;

import java.util.Arrays;

public abstract class Account {
    private String accountOwner;
    private int id;
    public static int idSeed = 1;
    private double currentAmount;
    private AccountType accountType;

    public Account(String accountOwner, double currentAmount) {
        this.accountOwner = accountOwner;
        this.id = idSeed++;
        this.currentAmount = currentAmount;

    }
    public abstract AccountType getAccountType();

    public double getCurrentAmount() {
        return currentAmount;
    }

    public void addAmount(double amount) {
        this.currentAmount += amount;
    }

    public void withdrawAmount(double amount) throws NotEnoughAmountException {
        if (currentAmount >= amount) {
            this.currentAmount -= amount;
        } else {
            throw new NotEnoughAmountException(currentAmount, amount);
        }

    }

    @Override
    public String toString() {
        return String.format("%d: %.2f", id, currentAmount);
    }
}

class NotEnoughAmountException extends Exception {
    public NotEnoughAmountException(double currentAmount, double amount) {
        super(String.format("Your current amount is: %.2f and you can not withdraw this amount: %.2f", currentAmount, amount));
    }
}

class InterestCheckingAccount extends Account implements InterestBearingAccount {
    public InterestCheckingAccount(String accountOwner, double currentAmount) {
        super(accountOwner, currentAmount);
    }

    @Override
    public AccountType getAccountType() {
        return AccountType.INTEREST;
    }

    static final double INTEREST_RATE = 0.03;

    @Override
    public void addInterest() {
        addAmount(getCurrentAmount() * INTEREST_RATE);
    }


}

class PlatinumCheckingAccount extends InterestCheckingAccount {

    public PlatinumCheckingAccount(String accountOwner, double currentAmount) {
        super(accountOwner, currentAmount);
    }

    @Override
    public void addInterest() {
        addAmount(getCurrentAmount() * INTEREST_RATE * 2);
    }
    @Override
    public AccountType getAccountType() {
        return AccountType.INTEREST;
    }

}

class NonInterestCheckingAcoount extends Account {

    public NonInterestCheckingAcoount(String accountOwner, double currentAmount) {
        super(accountOwner, currentAmount);
    }

    @Override
    public AccountType getAccountType() {
        return AccountType.NON_INTEREST;
    }
}

class Bank
{
    private  Account[] accounts;
    private  int totalAccounts;
    private  int maxAccounts;


    public Bank(int maxAccounts)
    {
        this.maxAccounts = maxAccounts;
        this.accounts=new Account[maxAccounts];
        this.totalAccounts=0;
    }
    public void addAccount(Account account){
        if(totalAccounts==maxAccounts){
            accounts= Arrays.copyOf(accounts,maxAccounts*2);
            maxAccounts*=2;
        }
        accounts[this.totalAccounts++]=account;
    }
    public  double totalAssets(){
        double sum=0;
    for(Account a :accounts){
        sum+=a.getCurrentAmount();
    }
    return sum;
    }
    public  void addInterestToAllAccounts(){
        for(Account a:accounts){
            if(a.getAccountType().equals("INTEREST")){
                ((InterestBearingAccount)a).addInterest();
            }
        }
    }

}
enum AccountType
{
    INTEREST,
    NON_INTEREST
}