package list;

import person.Person;
import person.Waiter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * @author ly
 */
public class PersonList {
    private static ArrayList<Person> instance;
    public static ArrayList<Person> getInstance(){
        if(instance == null){
            instance = new ArrayList<>();
        }
        return instance;
    }

     public static  void sortList(){
          Collections.sort(instance,comparator);
     }
    static Comparator<Person> comparator = new Comparator<>() {
         @Override
         public int compare(Person o1, Person o2) {
              String o1FirstStr = o1.getPid().substring(0,2);
              String o2FirstStr = o2.getPid().substring(0,2);
              if(!o2FirstStr.equals(o1FirstStr)){
                   String optionDish = "CuWaCo";
                   int o1Priority = optionDish.indexOf(o1FirstStr);
                   int o2Priority = optionDish.indexOf(o2FirstStr);
                   return o1Priority - o2Priority;
              }
               else {
                   int thisNum = Integer.parseInt(o1.getPid().substring(2));
                   int oNum = Integer.parseInt(o2.getPid().substring(2));
//
                   return thisNum - oNum;
              }
         }
    };

    public static Waiter chooseWaiterOrder(){
        int shouldBe = 0;
        for(int i=0;i<instance.size();i++){
            //找到第一个Waiter (用Customer来（waiter）会RE
            Person person = instance.get(i);
            if(person.getPid().startsWith("Wa")){
                shouldBe = i;
                break;
            }
        }

        for(int i=shouldBe;i<instance.size();i++){
            Person person = instance.get(i);
            if(person.getPid().startsWith("Wa") ){
                Waiter waiter = (Waiter) person,waiterShouldBe = (Waiter) instance.get(shouldBe);
                    if(waiter.getOrderCount() < waiterShouldBe.getOrderCount()){
                        //只有订单数少的时候才转换 ， 自动保持pid最小
                        shouldBe = i;
                    }
            }
        }

        return (Waiter) instance.get(shouldBe);
    }

}
