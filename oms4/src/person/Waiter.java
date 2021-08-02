package person;
import java.util.ArrayList;
import java.util.regex.Pattern;
import dish.Dish;
/**
 * @author ly
 */
public class Waiter extends Person{
    public Waiter(String n,char s,String p,String pid){

        super(n,s,p,pid);
        type = "Waiter";
    }
    ArrayList<Person> list = new ArrayList<>();
    //TODO 保证服务顾客的不重复

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
