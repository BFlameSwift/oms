package person;

import java.util.regex.Pattern;
import dish.Dish;
/**
 * @author m
 */
public class Cook extends Person{
    public Cook(String n,char s,String p,String pid){
        super(n,s,p,pid);
        type = "Cook";
    }

    public static boolean checkPid(String pid){

        if(pid.length() != 7){
            return false;
        }

        String pidSignal = pid.substring(0,2);
        if(!"Co".equals(pidSignal)){
            return false;
        }
        String pidNum = pid.substring(2);
        Pattern pattern = Pattern.compile(Dish.INTEGER);
        return pattern.matcher(pidNum).find();
    }
}
