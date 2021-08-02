package dish;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author ly
 */
public class Dish{
    public String getDishName() {
        return dishName;
    }
    public void setDishName(String dishName) {
        this.dishName = dishName;
    }
    public String getDishId() {
        return dishId;
    }
    public void setDishId(String dishId) {
        this.dishId = dishId;
    }
    public int getSum() {
        return sum;
    }
    public void setSum(int sum) {
        this.sum = sum;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    private String dishName,dishId;
    private int sum;
    private double price;

    public Dish(String dishId,String dishName,double price,int sum){
        this.setPrice(price);
        this.setSum(sum);
        this.setDishName(dishName);
        this.setDishId(dishId);
    }

    public static boolean checkSum(String  sumString){
        Pattern r = Pattern.compile(INTEGER);
        return r.matcher(sumString).find();
    }
    public static  boolean checkPrice(String priceString){
        Pattern pattern = Pattern.compile(DOUBLE);
        if(!pattern.matcher(priceString).matches()){
            return false;
        }
        double price = Double.parseDouble(priceString);
        return price >=0;

    }
    public static boolean checkName(String name){
        int judge = 0;
        for(int i=0;i<name.length();i++){
            char c = name.charAt(i);
            if (checkCharIsNumOrAlp(c)){
                judge ++;
            }
        }
        return judge == name.length();
    }
    public static boolean checkDishId(String dishId){
        String[] dishIdHead = {"H","C","O"};
        List<String> dishIdArray = Arrays.asList(dishIdHead);
        if( ! dishIdArray.contains(dishId.substring(0,1))
                    || dishId.length()!=7 ){

            return false;
        }
        String subIdNum = dishId.substring(1);
        Pattern r = Pattern.compile(INTEGER);
        return r.matcher(subIdNum).find();
    }
    public static boolean checkCharIsNumOrAlp(char c){
        return (c >= '0' && c <= '9') || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    @Override
    public String toString() {
        return "DID:" + dishId + ',' +
                "DISH:" + dishName + ',' +
                "PRICE:" + String.format("%.1f",price) +
                ",TOTAL:" + sum
                ;
    }
    public static final String DOUBLE = "^"
            + "(([\\s]*)?)"
            + "([\\d]+)"
            + "("
            + "(.)"
            + "([\\d]+)"
            + ")?"
            + "$";
     public static final String INTEGER = "^"
            + "([\\d]+)"
            + "$";


}

