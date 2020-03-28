import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用同步的钱款交易
 *
 * 使用锁实现串行访问
 *
 * @version 1.0.0 2020-03-28 16:30
 * @author bruce
 */
public class SynchBankTest {

    public static final int N_ACCOUNTS = 100;
    public static final double INITIAL_BALANCE = 1000;
    public static final double MAX_AMOUNT = 1000;
    public static final int DELAY = 10;

    public static void main(String[] args) {

        Bank bank = new Bank(N_ACCOUNTS, INITIAL_BALANCE);
        for (int i = 0; i < N_ACCOUNTS; i++) {
            int fromAccount = i;
            Runnable r = () -> {
               try {
                   while (true) {
                       int toAccount = (int) (bank.size() * Math.random());
                       double amount = MAX_AMOUNT * Math.random();
                       bank.transfer(fromAccount, toAccount, amount);
                       Thread.sleep((int)(DELAY * Math.random()));
                   }
               } catch (InterruptedException ex) {

               }
            };
            Thread t = new Thread(r);
            t.start();
        }
    }
}

/**
 * A bank with a number of bank accounts that uses locks for serializing access
 */
class Bank {

    private final double[] accounts;
    private Lock bankLock;
    private Condition sufficientFunds;


    public Bank(int n, double initialBalance) {
        accounts = new double[n];
        Arrays.fill(accounts, initialBalance);
        bankLock = new ReentrantLock();
        sufficientFunds = bankLock.newCondition();
    }

    public void transfer(int from, int to, double amount) throws InterruptedException {

        bankLock.lock();
        try {
            while (accounts[from] < amount) {
                sufficientFunds.await();
            }
            System.out.print(Thread.currentThread());
            accounts[from] -= amount;
            System.out.printf("%10.2f from %d to %d", amount, from, to);
            accounts[to] += amount;
            System.out.printf("Total Balance: %10.2f%n", getTotalBalance());
            sufficientFunds.signalAll();
        } finally {
            bankLock.unlock();
        }

    }

    public double getTotalBalance() {
        bankLock.lock();
        try {
            double sum = 0;
            for (double a : accounts) {
                sum += a;
            }
            return sum;
        } finally {
            bankLock.unlock();
        }

    }

    public int size() {
        return accounts.length;
    }
}
