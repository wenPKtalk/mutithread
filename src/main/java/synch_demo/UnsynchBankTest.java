package synch_demo;

/**
 * <br>
 * 〈不安全的银行交易测试代码〉
 *
 * @author wensir
 * @create 2018/12/23
 * @since 1.0.0
 */
public class UnsynchBankTest {

    public static final int NACCOUNTS = 100;
    public static final double INITIAL_BALANCE = 1000;
    public static final double MAX_AMOUNT = 1000;
    public static final int DELAY = 10;

    public static void main(String[] args) {
        unsynchBankTest();
        bankByConditionTest(); //条件同步安全测试

    }

    private static void unsynchBankTest() {
        BanK bank = new BanK(NACCOUNTS, INITIAL_BALANCE);

        for (int i = 0; i< NACCOUNTS;i++) {
            int fromAccount = i;
            Runnable r = () -> {
                try {
                    while (true) {
                        int toAccount = (int) (bank.size() * Math.random());
                        double amount = MAX_AMOUNT * Math.random();
                        bank.transfer(fromAccount, toAccount, amount);
                        Thread.sleep((int)(DELAY * Math.random()));
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
            Thread t = new Thread(r);
            t.start();
        }
    }

    private static void bankByConditionTest() {
        BankByCondition bank = new BankByCondition(NACCOUNTS, INITIAL_BALANCE);

        for (int i = 0; i< NACCOUNTS;i++) {
            int fromAccount = i;
            Runnable r = () -> {
                try {
                    while (true) {
                        int toAccount = (int) (bank.size() * Math.random());
                        double amount = MAX_AMOUNT * Math.random();
                        bank.transfer(fromAccount, toAccount, amount);
                        Thread.sleep((int)(DELAY * Math.random()));
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
            Thread t = new Thread(r);
            t.start();
        }
    }
}