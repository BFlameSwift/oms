package person;
import java.util.ArrayList;
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
    }
    public boolean isVip() {
        return isVip;
    }
    public void setVip(boolean vip) {
        isVip = vip;
    }
    public boolean isDining() {
        return isDining;
    }
    public void setDining(boolean dining) {
        isDining = dining;
    }

    private boolean isVip,isDining;

    public double balance;//TODO 保留一位小数
    ArrayList<Dish> listDishOrdering = new ArrayList<>();
    ArrayList<Dish> listDishNotService = new ArrayList<>();

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
