package person;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    ArrayList<Dish> listDishFinished = new ArrayList<>();
    public static boolean checkPid(String pid){
        boolean judge = true;
        if(pid.length() != 7){

            return false;
        }

        String pidSignal = pid.substring(0,2);
        if(!"Co".equals(pidSignal)){
            return false;
        }
        String pidNum = pid.substring(2);
        Pattern pattern = Pattern.compile(Dish.INTEGER);
        if(!pattern.matcher(pidNum).find()){
            return false;
        }

        return judge;
    }



}
