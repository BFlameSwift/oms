package person;

import dish.Dish;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * @author ly
 */
public class PersonList {
     public ArrayList<Person>  list = new ArrayList<>();
     public void sortList(){
          Collections.sort(list,comparator);
     }
    //TODO 添加人的时候自动填入各种人
    Comparator<Person> comparator = new Comparator<>() {
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
                   int thisNum = Integer.valueOf(o1.getPid().substring(2));
                   int oNum = Integer.valueOf(o2.getPid().substring(2));
//
                   return thisNum - oNum;
              }
         }
    };
}
