package person;
import java.util.regex.Pattern;
import dish.Dish;


/**
 * @author ly
 */
public class Customer extends Person{
    public Customer(String n,char s,String p,String pid){
        super(n,s,p,pid);
        this.type = "Customer";
        this.isVip = false;
        this.balance = 0;
        orderNum = 1;
    }



    private int orderNum;

    public void orderFinish(){
        this.orderNum++;
    }
    public int getOrderNum() {
        return orderNum;
    }
    public boolean isVip() {
        return isVip;
    }
    public void setVip(boolean vip) {
        isVip = vip;
    }


    private boolean isVip;

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
        isVip= !(balance < 200);
    }
    private double balance;
    public static boolean checkPid(String pid){
        if(pid.length() != 7){
            return false;
        }
        String pidSignal = pid.substring(0,2);
        if(!"Cu".equals(pidSignal)){
            return false;
        }
        String pidNum = pid.substring(2);
        Pattern pattern = Pattern.compile(Dish.INTEGER);
        return pattern.matcher(pidNum).find();
    }
}
