package person;
import java.util.ArrayList;
import java.util.regex.Pattern;
import dish.Dish;
import list.Order;

/**
 * @author ly
 */

public class Waiter extends Person{
    public Waiter(String n,char s,String p,String pid){

        super(n,s,p,pid);
        type = "Waiter";
        this.orderCount = 0;
    }
    public ArrayList<Order> list = new ArrayList<>();
    public int getOrderCount() {
        return this.orderCount;
    }
    public void orderAnOrder(){
        this.orderCount++;
    }
    public void orderFinish(){
        this.orderCount--;
    }
    private int orderCount ;
    public static boolean checkPid(String pid){
        if(pid.length() != 7){

            return false;
        }
        String pidSignal = pid.substring(0,2);
        if(!"Wa".equals(pidSignal)){
            return false;
        }
        String pidNum = pid.substring(2);
        Pattern pattern = Pattern.compile(Dish.INTEGER);

        return pattern.matcher(pidNum).find();
    }
}
