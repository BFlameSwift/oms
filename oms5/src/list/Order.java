package list;

import dish.Dish;
import dish.Menu;
import mode.LoginWa;
import person.Customer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * @author ly
 * @date 2021/5/6 11:07
 */
public class Order {
    private final String customerId;
    private String orderId;
    private String waiterId;
    public ArrayList<Dish> dishList ;
    private double sumMoney;
    private boolean isConfirmed;
    private boolean isManaged;
    private boolean isCooked;
    private boolean isCheckouted;
    private boolean isDelivered;

    private static ArrayList<Order> instances;
    public static ArrayList<Order> getInstances(){
        if(instances == null)
            instances = new ArrayList<>();
        return instances;
    }

    public Order(String customerId){
        this.customerId = customerId;
        dishList = new ArrayList<>();
        sumMoney = 0;
        isManaged = isCheckouted = isCooked = isDelivered = isConfirmed = false;
    }
    public void sortList(){
        Collections.sort(dishList, Menu.comparator);
    }
    public static String dishToStringGl(Order order){
        String theString= "OID:" + order.getOrderId() + ",DISH:[";
        double sumMoney =0;
        for (int i = 0; i < order.dishList.size(); i++) {
            Dish dish = order.dishList.get(i);
            theString += String.format("%s %s", dish.getSum(),dish.getDishName());
            sumMoney += dish.getPrice() * dish.getSum();
            if (i < order.dishList.size() - 1) {
                theString+=",";
            } else {
                theString+="]";
            }
            order.setSumMoney(sumMoney);
        }return theString;
    }
    public static String dishToStringSr(Order order){
        String theString= "OID:" + order.getOrderId() + ",DISH:[";
        double sumMoney =0;
        for (int i = 0; i < order.dishList.size(); i++) {
            Dish dish = order.dishList.get(i);
            theString += String.format("%s %.1f", dish.getDishName(), dish.getPrice() * dish.getSum());
            sumMoney += dish.getPrice() * dish.getSum();
            if (i < order.dishList.size() - 1) {
                theString+=",";
            } else {
                theString+="],";
            }
            order.setSumMoney(sumMoney);

        }return theString;
    }


    public double getSumMoney() {
        Customer customer = LoginWa.findCustomer(customerId);
        if(customer.isVip())
            return sumMoney*0.8;
        return sumMoney;
    }

    public void setSumMoney(double sumMoney) {
        this.sumMoney = sumMoney;
    }

    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public boolean isCooked() {
        return isCooked;
    }
    public void setCooked(boolean cooked) {
        isCooked = cooked;
    }
    public boolean isCheckouted() {
        return isCheckouted;
    }

    public void setCheckouted(boolean checkouted) {
        isCheckouted = checkouted;
    }
    public void addDish(Dish dish){
        dishList.add(dish);
    }
    public boolean isManaged() {
        return isManaged;
    }

    public void setManaged(boolean managed) {
        isManaged = managed;
    }
    public String getCustomerId() {
        return customerId;
    }
    public boolean isConfirmed() {
        return isConfirmed;
    }
    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }
    public boolean isDelivered() {
        return isDelivered;
    }
    public void setDelivered(boolean delivered) {
        isDelivered = delivered;
    }
    public String getWaiterId() {
        return waiterId;
    }
    public void setWaiterId(String waiterId) {
        this.waiterId = waiterId;
    }


}
