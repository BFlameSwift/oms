import java.util.*;

/**
 * @author ly
 */
public class Menu {
    ArrayList<Dish> allDish = new ArrayList<>(1000);
    public void main(String[] args){

        Dish newOne = new Dish();


    }
    public void update(){
        this.sortList();
    }

    public void print(){
        for(int i = 1;i<=allDish.size();i++){
            System.out.println(i+". "+allDish.get(i-1).toString());
        }
    }
    public Dish getDishById(String did){
        for(int i=0;i<allDish.size();i++){
            Dish now = allDish.get(i);
            if(now.getDishId().equals(did)){
                return now;
            }
        }
        return null;
    }
    public ArrayList<Dish> getDishByKeyWord(String keyword){
        ArrayList<Dish> findKeyDish = new ArrayList<>(1000);
        for (Dish now : allDish) {
            boolean judge = now.getDishName().toLowerCase().contains(keyword.toLowerCase());
            if (judge) {
                findKeyDish.add(now);
            }
        }
        return findKeyDish;
    }
    public void sortList(){
        Collections.sort(allDish,comparator);
    }
    Comparator<Dish> comparator = new Comparator<>() {
        @Override
        public int compare(Dish o1, Dish o2) {
            int o1FirstChar = o1.getDishId().charAt(0);
            int o2FirstChar = o2.getDishId().charAt(0);
            if (o1FirstChar != o2FirstChar) {
//                String[] optionDish = {"H","C","O"};
                String optionDish = "HCO";
                int o1Priority = optionDish.indexOf(o1FirstChar);
                int o2Priority = optionDish.indexOf(o2FirstChar);
                return o1Priority - o2Priority;
            } else {
                int thisNum = Integer.valueOf(o1.getDishId(), 1);
                int oNum = Integer.valueOf(o2.getDishId(), 1);
                System.out.println(thisNum);
                System.out.println(oNum);
                return thisNum - oNum;
            }
        }
    };
    public boolean checkNdNameIsExist(String name){
        for(int i=0;i<this.allDish.size();i++){
            String thisDishName = this.allDish.get(i).getDishName();
            if(name.equals(thisDishName)){
                return true;
            }
        }
        return false;
    }

}
