import java.util.*;

// Singleton pattern: Ensures only one instance of BankingSystem is created

class BankingSystem {
    private static BankingSystem instance;
    private Map<String, Account> accounts;

    private BankingSystem() {
        accounts = new HashMap<>();
    }
    protected boolean isActive;

    // Method to get the single instance of BankingSystem
    public static BankingSystem getInstance() {
        if (instance == null) {
            instance = new BankingSystem();
        }
        return instance;
    }
    // Method to check account active or not
    public boolean isActive() {
        return isActive;
    }
    // Account Creation
    public void createAccount(String accountType, String accountName, double initialDeposit) {
        if (!accounts.containsKey(accountName)) {
            Account account = createAccountWithTransactionFee(accountType, accountName, initialDeposit);
            accounts.put(accountName, account);
            System.out.printf("A new %s account created for %s with an initial balance of $%.3f.%n", accountType, accountName, initialDeposit);
        } else {
            System.out.println("Error: Account " + accountName + " already exists.");
        }
    }

    // Helper method to create account with transaction fee
    private Account createAccountWithTransactionFee(String accountType, String accountName, double initialDeposit) {
        Account account;
        switch (accountType) {
            case "Savings":
                account = new SavingsAccount(accountName, initialDeposit);
                account = new SavingsAccountTransactionFeeDecorator(account);
                break;
            case "Checking":
                account = new CheckingAccount(accountName, initialDeposit);
                account = new CheckingAccountTransactionFeeDecorator(account);
                break;
            case "Business":
                account = new BusinessAccount(accountName, initialDeposit);
                account = new BusinessAccountTransactionFeeDecorator(account);
                break;
            default:
                throw new IllegalArgumentException("Invalid account type.");
        }
        return account;
    }

    // Deposit: the account adds amount of money to yourself
    public void deposit(String accountName, double depositAmount) {
        Account account = accounts.get(accountName);
        if (account != null) {
            account.deposit(depositAmount);
        } else {
            System.out.println("Error: Account " + accountName + " does not exist.");
        }
    }

    // Withdraw: the account withdraws the amount of money
    public void withdraw(String accountName, double withdrawalAmount) {
        Account account = accounts.get(accountName);
        if (account != null) {
            account.withdraw(withdrawalAmount);
        } else {
            System.out.println("Error: Account " + accountName + " does not exist.");
        }
    }
    //Transfer: the account transfer the amount of money to another one
    public void transfer(String fromAccountName, String toAccountName, double transferAmount) {
        Account fromAccount = accounts.get(fromAccountName);
        Account toAccount = accounts.get(toAccountName);
        if (fromAccount != null) {
            if (toAccount != null) {
                fromAccount.transfer(toAccount, transferAmount);
            } else {
                System.out.println("Error: Account " + toAccountName + " does not exist.");
            }
        }else {
            System.out.println("Error: Account " + fromAccountName + " does not exist.");
        }
    }

    // View Account Details
    public void viewAccountDetails(String accountName) {
        Account account = accounts.get(accountName);
        if (account != null) {
            account.viewDetails();
        } else {
            System.out.println("Error: Account " + accountName + " does not exist.");
        }
    }

    // Deactivate Account: after deactivating account does not have some functions
    public void deactivateAccount(String accountName) {
        Account account = accounts.get(accountName);
        if (account != null) {
            account.deactivate(); // Сначала деактивируем аккаунт
        } else {
            System.out.println("Error: Account " + accountName + " does not exist.");
        }
    }


    // Activate Account
    public void activateAccount(String accountName) {
        Account account = accounts.get(accountName);
        if (account != null) {
            account.setActive(true);
            account.activate();
        } else {
            System.out.println("Error: Account " + accountName + " does not exist.");
        }
    }
}
// Savings Account class: Creating the new type of account
class SavingsAccount extends Account {
    // The constructor that initialize the transactionFeeDecorator
    public SavingsAccount(String name, double initialDeposit) {
        super(name, initialDeposit);
        this.transactionFeeDecorator = new SavingsAccountTransactionFeeDecorator(this);
    }

    @Override
    public void deposit(double amount) {
        super.deposit(amount);
    }

    @Override
    public void withdraw(double amount) {
        super.withdraw(amount);
    }

    @Override
    public void transfer(Account toAccount, double amount) {
       super.transfer(toAccount, amount);
    }
    @Override
    public void deactivate() {
        super.deactivate();
    }
    @Override
    public void viewDetails() {
        System.out.println(this);
    }
}

// Checking Account class: Creating the new type of account
class CheckingAccount extends Account {
    public CheckingAccount(String name, double initialDeposit) {
        super(name, initialDeposit);
        this.transactionFeeDecorator = new CheckingAccountTransactionFeeDecorator(this);
    }

    @Override
    public void deposit(double amount) {
        super.deposit(amount);
    }

    @Override
    public void withdraw(double amount) {
        super.withdraw(amount);
    }

    @Override
    public void transfer(Account toAccount, double amount) {
        super.transfer(toAccount, amount);
    }
    @Override
    public void deactivate() {
        super.deactivate();
    }

    @Override
    public void viewDetails() {
        System.out.println(this);
    }
}

// Business Account class: Creating the new type of account
class BusinessAccount extends Account {
    public BusinessAccount(String name, double initialDeposit) {
        super(name, initialDeposit);
        this.transactionFeeDecorator = new BusinessAccountTransactionFeeDecorator(this);
    }

    @Override
    public void deposit(double amount) {
        super.deposit(amount);
    }

    @Override
    public void withdraw(double amount) {
        super.withdraw(amount);
    }

    @Override
    public void transfer(Account toAccount, double amount) {
        super.transfer(toAccount, amount);
    }
    @Override
    public void deactivate() {
        super.deactivate();
    }
    @Override
    public void viewDetails() {
        System.out.println(this);
    }
}

// Base Account class: account with the base functional
abstract class Account {
    protected String name;
    protected double balance;
    protected AccountState state;
    List<String> history;
    protected boolean isActive;
    TransactionFeeDecorator transactionFeeDecorator;

    // the constructor that initialize all characteristics
    public Account(String name, double initialDeposit ) {
        this.name = name;
        this.balance = initialDeposit;
        this.state = new ActiveState(this);
        this.history = new ArrayList<>();
        history.add(String.format("Initial Deposit $%.3f", initialDeposit));
        this.isActive = true;
    }
    // Deposit method: Delegates the deposit action to the current account state
    public void deposit(double amount) {
        state.deposit(amount);
    }
    // Withdraw method: Delegates the withdrawal action to the current account state
    public void withdraw(double amount) {
        state.withdraw(amount);
    }
    // Transfer method: Delegates the transfer action to the current account state
    public void transfer(Account toAccount, double amount) {
        state.transfer(toAccount, amount);
    }
    // Activate method: Delegates the activation action to the current account state
    public void activate() {
        state.activate();
    }
    // Deactivate method: Delegates the deactivation action to the current account state
    public void deactivate() {
        state.deactivate();
    }
    // SetState method: Sets the current account state to the provided state
    public void setState(AccountState state) {
        this.state = state;
    }

    public abstract void viewDetails();

    @Override
    public String toString() {
        String stateString = state instanceof ActiveState ? "Active" : "Inactive";
        return String.format("%s's Account: Type: %s, Balance: $%.3f, State: %s, Transactions: %s.", name, getAccountType(), balance, stateString, history);
    }
    // GetAccountType method: to get the account type based on the transaction fee decorator
    private String getAccountType() {
        if (transactionFeeDecorator instanceof SavingsAccountTransactionFeeDecorator) {
            return "Savings";
        } else if (transactionFeeDecorator instanceof CheckingAccountTransactionFeeDecorator) {
            return "Checking";
        } else if (transactionFeeDecorator instanceof BusinessAccountTransactionFeeDecorator) {
            return "Business";
        }
        return "Unknown";
    }
    // IsActive method: Check if the account is active
    public boolean isActive() {
        return isActive;
    }
    // SetActive method: Set the active status of the account
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
    // Deposit method: deposit funds into the account, delegated to the current state
    protected void Deposit(double amount) {
        state.deposit(amount);
    }
}

// State pattern: Interface defining the various states an account can be in
interface AccountState {
    // Method to deposit funds into the account
    void deposit(double amount);

    // Method to withdraw funds from the account
    void withdraw(double amount);

    // Method to transfer funds from the account to another account
    void transfer(Account toAccount, double amount);

    // Method to activate the account
    void activate();

    // Method to deactivate the account
    void deactivate();

    // Method to view details of the account
    void viewDetails();
}
// Class representing the active state of an account
class ActiveState implements AccountState {
    private Account account;
    // Constructor taking the account associated with this state
    public ActiveState(Account account) {
        this.account = account;
    }

    @Override
    public void deposit(double amount) {
        account.balance += amount;
        account.setActive(true);
        System.out.printf("%s successfully deposited $%.3f. New Balance: $%.3f.%n", account.name, amount, Math.abs(account.balance));

    }

    @Override
    public void withdraw(double amount) {
        account.setActive(true);
        account.state.withdraw(amount);
    }

    @Override
    public void transfer(Account toAccount, double amount) {
        account.setActive(true);
        account.state.transfer(toAccount, amount);
    }

    @Override
    public void activate() {
        account.setActive(true);
        System.out.printf("Error: Account %s is already activated.%n", account.name);
    }

    @Override
    public void deactivate() {
        account.setState(new InactiveState(account));
        account.setActive(false);
        System.out.printf("%s's account is now deactivated.%n", account.name);
    }
    @Override
    public void viewDetails() {
        account.setActive(true);
        System.out.println(account);
    }

}
// Class representing the inactive state of an account
class InactiveState implements AccountState {
    private Account account;
    // Constructor taking the account associated with this state
    public InactiveState(Account account) {
        this.account = account;
    }

    @Override
    public void deposit(double amount) {
        account.balance += amount;
        System.out.printf("%s successfully deposited $%.3f. New Balance: $%.3f.%n", account.name, amount, Math.abs(account.balance));

    }

    @Override
    public void withdraw(double amount) {

    }

    @Override
    public void transfer(Account toAccount, double amount) {

    }

    @Override
    public void activate() {
        account.setState(new ActiveState(account));
        System.out.printf("%s's account is now activated.%n", account.name);
    }

    @Override
    public void deactivate() {
        account.setActive(false);
        System.out.printf("Error: Account %s is already deactivated.%n", account.name);
    }
    @Override
    public void viewDetails() {
        System.out.println(account);
    }
}

// Decorator pattern: Abstract class representing a decorator that adds transaction fee functionality to an account
abstract class TransactionFeeDecorator extends Account {
    protected Account account;
    // Constructor taking the account to be decorated
    public TransactionFeeDecorator(Account account) {
        super(account.name, account.balance);
        this.account = account;
    }

    // Default implementations for methods, you can override as needed
    @Override
    public void deposit(double amount) {
        account.deposit(amount);
    }

    @Override
    public void withdraw(double amount) {
        account.withdraw(amount);
    }

    @Override
    public void transfer(Account toAccount, double amount) {
        account.transfer(toAccount, amount);
    }

    @Override
    public void activate() {
        account.activate();
    }

    @Override
    public void deactivate() {
        account.deactivate();
    }

    @Override
    public void viewDetails() {
        account.viewDetails();
    }
}

// Decorator for adding transaction fee calculation functionality to Savings Account
class SavingsAccountTransactionFeeDecorator extends TransactionFeeDecorator {
    // Constructor taking the account to be decorated
    public SavingsAccountTransactionFeeDecorator(Account account) {
        super(account);

    }
    // Overrides the deposit method to include transaction fee calculation
    @Override
    public void deposit(double amount) {
        account.balance += amount;
        System.out.printf("%s successfully deposited $%.3f. New Balance: $%.3f.%n", account.name, amount, Math.abs(account.balance));
        account.history.add(String.format("Deposit $%.3f", amount));
    }
    public void Deposit(double amount) {
        account.balance += amount;
    }

    // Overrides the withdraw method to include transaction fee calculation
    @Override
    public void withdraw(double amount) {
        if (account.isActive()) {
            double transactionFee = 0.015 * amount;
            if (account.balance >= amount && (amount>=1 && amount<=5000)) {
                System.out.printf("%s successfully withdrew $%.3f. New Balance: $%.3f. Transaction Fee: $%.3f (%.1f%%)" +
                        " in the system.\n", account.name, amount - transactionFee, Math.abs(account.balance - amount), transactionFee, (transactionFee / amount) * 100);
                account.history.add(String.format("Withdrawal $%.3f", amount));
                account.balance -= amount;
            } else {
                System.out.printf("Error: Insufficient funds for %s.%n", account.name);
            }
        } else {
            System.out.printf("Error: Account %s is inactive.%n", account.name);
        }
    }
    // Overrides the transfer method to include transaction fee calculation

    @Override
    public void transfer(Account toAccount, double amount) {
        if (account.isActive()) {
        double transactionFee = 0.015 * amount;
        double transferAmountWithFee = amount + transactionFee;
        if (account.name.equals(toAccount.name)) {
            if (account.balance >= amount && (amount>=1 && amount<=5000)) {
                System.out.printf("%s successfully transferred $%.3f to %s. New Balance: $%.3f. Transaction Fee: $%.3f (%.1f%%) in the system.%n", account.name, amount - transactionFee, toAccount.name,  Math.abs(account.balance - transactionFee), transactionFee, (transactionFee / amount) * 100);
                account.history.add(String.format("Transfer $%.3f", amount));
                account.balance-=transactionFee;
            } else {
                System.out.printf("Error: Insufficient funds for %s.%n", account.name);
            }
        } else  {
            if (account.balance >= amount && (amount>=1 && amount<=5000)) {
                toAccount.Deposit(amount- transactionFee); // Only add the transfer amount to the recipient's balance
                System.out.printf("%s successfully transferred $%.3f to %s. New Balance: $%.3f. Transaction Fee: $%.3f (%.1f%%) in the system.%n", account.name, amount - transactionFee, toAccount.name,  Math.abs(account.balance - amount) , transactionFee, (transactionFee / amount) * 100);
                account.history.add(String.format("Transfer $%.3f", amount));
                account.balance = account.balance - amount ;
            } else {
                System.out.printf("Error: Insufficient funds for %s.%n", account.name);
            }
        }
        } else {
            System.out.printf("Error: Account %s is inactive.%n", account.name);
        }
    }
    // Overrides the activate method to set the account active
    @Override
    public void activate() {
        account.setActive(true);
        super.activate();
    }
    // Overrides the deactivate method to set the account inactive
    @Override
    public void deactivate() {
        account.setActive(false);
        super.deactivate();
    }
    // Overrides the viewDetails method to display account details
    @Override
    public void viewDetails() {
        System.out.println(account);
    }
}

// Decorator for adding transaction fee calculation functionality to Checking Account
class CheckingAccountTransactionFeeDecorator extends TransactionFeeDecorator {
    public CheckingAccountTransactionFeeDecorator(Account account) {
        super(account);
    }
    @Override
    public void deposit(double amount) {
        account.balance += amount;
        System.out.printf("%s successfully deposited $%.3f. New Balance: $%.3f.%n", account.name, amount, Math.abs(account.balance));
        account.history.add(String.format("Deposit $%.3f", amount));
    }
    public void Deposit(double amount) {
        account.balance += amount;
    }
    @Override
    public void withdraw(double amount) {
        if (account.isActive()) {
            double transactionFee = 0.02 * amount;
            if (account.balance >= amount && (amount>=1 && amount<=5000)) {
                System.out.printf("%s successfully withdrew $%.3f. New Balance: $%.3f. Transaction Fee: $%.3f (%.1f%%)" +
                        " in the system.\n", account.name, amount - transactionFee, Math.abs(account.balance - amount), transactionFee, (transactionFee / amount) * 100);
                account.history.add(String.format("Withdrawal $%.3f", amount));
                account.balance -= amount;
            } else {
                System.out.printf("Error: Insufficient funds for %s.%n", account.name);
            }
        } else {
            System.out.printf("Error: Account %s is inactive.%n", account.name);
        }
    }

    @Override
    public void transfer(Account toAccount, double amount) {
        if (account.isActive()) {
        double transactionFee = 0.02 * amount;
        double transferAmountWithFee = amount + transactionFee;
        if (account.name.equals(toAccount.name)) {
            if (account.balance >= amount && (amount>=1 && amount<=5000)) {
                System.out.printf("%s successfully transferred $%.3f to %s. New Balance: $%.3f. Transaction Fee: $%.3f (%.1f%%) in the system.%n", account.name, amount - transactionFee, toAccount.name,  Math.abs(account.balance - transactionFee), transactionFee, (transactionFee / amount) * 100);
                account.history.add(String.format("Transfer $%.3f", amount));
                account.balance-=transactionFee;
            } else {
                System.out.printf("Error: Insufficient funds for %s.%n", account.name);
            }
        } else  {
            if (account.balance >= amount && (amount>=1 && amount<=5000)) {
                toAccount.Deposit(amount- transactionFee); // Only add the transfer amount to the recipient's balance
                System.out.printf("%s successfully transferred $%.3f to %s. New Balance: $%.3f. Transaction Fee: $%.3f (%.1f%%) in the system.%n", account.name, amount - transactionFee, toAccount.name, Math.abs(account.balance - amount), transactionFee, (transactionFee / amount) * 100);
                account.history.add(String.format("Transfer $%.3f", amount));
                account.balance = account.balance - amount ;
            } else {
                System.out.printf("Error: Insufficient funds for %s.%n", account.name);
            }
        }
        } else {
            System.out.printf("Error: Account %s is inactive.%n", account.name);
        }
    }
    @Override
    public void activate() {
        account.setActive(true);
        super.activate();
    }

    @Override
    public void deactivate() {
        super.deactivate();
    }

    @Override
    public void viewDetails() {
        System.out.println(account);
    }
}

// Decorator for adding transaction fee calculation functionality to Business Account
class BusinessAccountTransactionFeeDecorator extends TransactionFeeDecorator {
    public BusinessAccountTransactionFeeDecorator(Account account) {
        super(account);
    }
    @Override
    public void deposit(double amount) {
        account.balance += amount;
        System.out.printf("%s successfully deposited $%.3f. New Balance: $%.3f.%n", account.name, amount, Math.abs(account.balance));
        account.history.add(String.format("Deposit $%.3f", amount));
    }
    public void Deposit(double amount) {
        account.balance += amount;
    }
    @Override
    public void withdraw(double amount) {
        if (account.isActive()) {
            double transactionFee = 0.025 * amount;
            if (account.balance >= amount && (amount>=1 && amount<=5000)) {
                System.out.printf("%s successfully withdrew $%.3f. New Balance: $%.3f. Transaction Fee: $%.3f (%.1f%%)" +
                        " in the system.\n", account.name, amount - transactionFee, Math.abs(account.balance - amount), transactionFee, (transactionFee / amount) * 100);
                account.history.add(String.format("Withdrawal $%.3f", amount));
                account.balance -= amount;
            } else {
                System.out.printf("Error: Insufficient funds for %s.%n", account.name);
            }
        } else {
            System.out.printf("Error: Account %s is inactive.%n", account.name);
        }
    }

    @Override
    public void transfer(Account toAccount, double amount) {
        if (account.isActive()) {
        double transactionFee = 0.025 * amount;
        double transferAmountWithFee = amount + transactionFee;
        if (account.name.equals(toAccount.name)) {
            if (account.balance >= amount && (amount>=1 && amount<=5000)) {
                System.out.printf("%s successfully transferred $%.3f to %s. New Balance: $%.3f. Transaction Fee: $%.3f (%.1f%%) in the system.%n", account.name, amount - transactionFee, toAccount.name,  Math.abs(account.balance - transactionFee), transactionFee, (transactionFee / amount) * 100);
                account.history.add(String.format("Transfer $%.3f", amount));
                account.balance-=transactionFee;
            } else {
                System.out.printf("Error: Insufficient funds for %s.%n", account.name);
            }
        } else  {
            if (account.balance >= amount && (amount>=1 && amount<=5000)) {
                 // Only add the transfer amount to the recipient's balance
                System.out.printf("%s successfully transferred $%.3f to %s. New Balance: $%.3f. Transaction Fee: $%.3f (%.1f%%) in the system.%n", account.name, amount - transactionFee, toAccount.name, Math.abs(account.balance- amount), transactionFee, (transactionFee / amount) * 100);
                account.history.add(String.format("Transfer $%.3f", amount));
                account.balance = account.balance - amount ;
                toAccount.Deposit(amount-transactionFee);
            } else {
                System.out.printf("Error: Insufficient funds for %s.%n", account.name);
            }
        }
    } else {
        System.out.printf("Error: Account %s is inactive.%n", account.name);
    }
    }
    @Override
    public void activate() {
        account.setActive(true);
        super.activate();
    }

    @Override
    public void deactivate() {
        super.deactivate();
    }

    @Override
    public void viewDetails() {
        System.out.println(account);
    }
}

public class Main2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BankingSystem bankingSystem = BankingSystem.getInstance();

        int n = scanner.nextInt(); // Read the number of banking operations

        for (int i = 0; i < n; i++) {
            String operation = scanner.next(); // Read the operation type
            switch (operation) {
                case "Create":
                    createAccount(scanner, bankingSystem);
                    break;
                case "Deposit":
                    deposit(scanner, bankingSystem);
                    break;
                case "Withdraw":
                    withdraw(scanner, bankingSystem);
                    break;
                case "Transfer":
                    transfer(scanner, bankingSystem);
                    break;
                case "View":
                    viewAccountDetails(scanner, bankingSystem);
                    break;
                case "Deactivate":
                    deactivateAccount(scanner, bankingSystem);
                    break;
                case "Activate":
                    activateAccount(scanner, bankingSystem);
                    break;
                default:
                    System.out.println("Error: Invalid operation.");
            }
        }
    }
    // Method to create an account
    private static void createAccount(Scanner scanner, BankingSystem bankingSystem) {
        String operationType = scanner.next(); // Read the operation type
        if (!operationType.equals("Account")) {
            System.out.println("Error: Invalid operation.");
            return;
        }
        String accountType = scanner.next();
        String accountName = scanner.next();
        double initialDeposit = Double.parseDouble(scanner.next());
        bankingSystem.createAccount(accountType, accountName, initialDeposit);
    }
    // Method to deposit money into an account
    private static void deposit(Scanner scanner, BankingSystem bankingSystem) {
        String accountName = scanner.next();
        double depositAmount = Double.parseDouble(scanner.next());
        bankingSystem.deposit(accountName, depositAmount);
    }
    // Method to withdraw money from an account
    private static void withdraw(Scanner scanner, BankingSystem bankingSystem) {
        String accountName = scanner.next();
        double withdrawalAmount = Double.parseDouble(scanner.next());
        bankingSystem.withdraw(accountName, withdrawalAmount);
    }
    // Method to transfer money between accounts
    private static void transfer(Scanner scanner, BankingSystem bankingSystem) {
        String fromAccountName = scanner.next();
        String toAccountName = scanner.next();
        double transferAmount = Double.parseDouble(scanner.next());
        bankingSystem.transfer(fromAccountName, toAccountName, transferAmount);
    }
    // Method to view account details
    private static void viewAccountDetails(Scanner scanner, BankingSystem bankingSystem) {
        String accountName = scanner.next();
        bankingSystem.viewAccountDetails(accountName);
    }
    // Method to deactivate an account
    private static void deactivateAccount(Scanner scanner, BankingSystem bankingSystem) {
        String accountName = scanner.next();
        bankingSystem.deactivateAccount(accountName);
    }
    // Method to activate an account
    private static void activateAccount(Scanner scanner, BankingSystem bankingSystem) {
        String accountName = scanner.next();
        bankingSystem.activateAccount(accountName);
    }
}
