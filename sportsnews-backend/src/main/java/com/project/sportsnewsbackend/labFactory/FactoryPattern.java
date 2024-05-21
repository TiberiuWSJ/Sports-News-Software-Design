package com.project.sportsnewsbackend.labFactory;

public class FactoryPattern {

    private Account createFactoryObject(AccountType type) {
//        if(AccountType.GENERAL_ACCOUNT.equals(type)){
//            return new GeneralAccount();
//        }
//        else if(AccountType.SAVINGS_ACCOUNT.equals(type)) {
//            return new SavingsAccount();
//        }
//        else{
//            return null;
//        }
        return switch (type) {
            case GENERAL_ACCOUNT -> new GeneralAccount();
            case SAVINGS_ACCOUNT -> new SavingsAccount();
            case INVESTMENT_ACCOUNT -> new InvestmentAccount();
        };
    }
}
