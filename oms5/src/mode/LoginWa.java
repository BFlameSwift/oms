package mode;

import dish.Dish;
import list.Order;
import list.PersonList;
import person.Customer;
import person.Person;
import person.Waiter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author ly
 * @date 2021/5/11 14:49
 */
public class  LoginWa {
    public static boolean login(Waiter waiter, String[] subCommand){
        String[] commandSet = {"gl", "mo", "sr", "rw"};
        List<String> commandList = Arrays.asList(commandSet);
        String option = subCommand[0];
        if(! commandList.contains(option)){
            return  false;
        }
        switch (option){
            case "gl":{
                return glCommand(waiter,subCommand);
            }
            case "sr":{
                return srCommand(waiter, subCommand);
            }
            case "mo":{
                return moCommand(waiter,subCommand);
            }
            case "rw":{
                return rwCommand(waiter, subCommand);
            }
            default:{
                return true;
            }
        }

    }
    private static boolean glCommand(Waiter waiter,String[] subCommand){
        if(subCommand.length>1){
            Print.paramsCountIllegal();
            return true;
        }


        ArrayList<Order> orderList = findOrderList(waiter,true,false,false,false,false);
        if(orderList.size()>0){
            int i=0;
            for(Order order:orderList){
                System.out.println(i+1+". "+Order.dishToStringGl(order));
                i++;
            }
        }
        else {
            Print.noServingOrder();
        }
        return true;
    }
    private static boolean moCommand(Waiter waiter,String[] subCommand){
        if(subCommand.length>1){
            Print.paramsCountIllegal();
            return true;
        }
        Order order =  findOrder(waiter,true,false,false,false,false);
        if(order == null){
            Print.noServingOrder();
            return true;
        }

        order.setManaged(true);
        System.out.println("Manage order success");
        Main.orderCookList.add(order);
        return true;
    }
    private static boolean srCommand(Waiter waiter,String[] subCommand){
        if(subCommand.length!=2){
            Print.paramsCountIllegal();
            return true;
        }
        String orderId = subCommand[1];
        Order order=null;
        for(int i=0;i<Order.getInstances().size();i++){
            Order order1 = Order.getInstances().get(i);
            if(order1.getOrderId().equals(orderId)){
                order = order1;
                break;
            }
        }


        if( order ==null || !order.getWaiterId().equals(waiter.getPid()) ){
            System.out.println("Order serve illegal");
            return true;

        }else if(!order.isCooked()){
            System.out.println("Order not cooked");
            return true;
        }
        else if(order.isCheckouted()){
            System.out.println("Order already checkout");
            return true;
        }else{
            double balance = findCustomer(order.getCustomerId()).getBalance();
            if(balance < order.getSumMoney()){
                System.out.println("Insufficient balance");
                return true;
            }
            else{
                order.setCheckouted(true);
                order.setDelivered(true);

                System.out.print(Order.dishToStringSr(order));

                waiter.orderFinish();//finish orderCount--

                System.out.printf("TOTAL:%.1f,BALANCE:%.1f\n",order.getSumMoney(),balance - order.getSumMoney());
                findCustomer(order.getCustomerId()).setBalance(findCustomer(order.getCustomerId()).getBalance()-order.getSumMoney());
            }
        }


        
        return  true;
    }
    private static boolean rwCommand(Waiter waiter,String[] subCommand){
        if(subCommand.length!=3){
            Print.paramsCountIllegal();
            return true;
        }

        String customerId = subCommand[1];
        Customer customer = findCustomer(customerId);
        String chargeStr = subCommand[2];
        if (!Dish.checkPrice(chargeStr)) {
            Print.rechargeInputIllegal();
            return true;
        }
        double charge = Double.parseDouble(chargeStr);
        if (charge < 100.0 || charge >= 1000.0) {
            Print.rechargeInputIllegal();
            return true;
        }
        customer.setBalance(customer.getBalance()+charge);
        return true;
    }
    private static Order findOrder(Waiter waiter,boolean isConfirmed, boolean isManaged, boolean isCheckouted,boolean isCooked,boolean isDelivered ){
        for(int i=0;i<waiter.list.size();i++){
            Order order = waiter.list.get(i);

            if(order.getWaiterId()==null){
                continue;
            }

            if(order.getWaiterId().equals(waiter.getPid())
                        && order.isConfirmed()==isConfirmed && order.isManaged() == isManaged && order.isCheckouted() ==isCheckouted
                        && order.isCooked() ==isCooked && order.isDelivered() == isDelivered
            ){
                return order;
            }
        }
        return null;
    }
    private static ArrayList<Order> findOrderList(Waiter waiter,boolean isConfirmed,boolean isManaged, boolean isCheckouted,boolean isCooked,boolean isDelivered ){
        ArrayList<Order> orders = new ArrayList<>();

        for(int i=0;i<waiter.list.size();i++){
            Order order = waiter.list.get(i);

            if(order.getWaiterId()==null){
                continue;
            }

            if(order.getWaiterId().equals(waiter.getPid())
                    && order.isConfirmed()==isConfirmed && order.isManaged() == isManaged && order.isCheckouted() ==isCheckouted
                    && order.isCooked() ==isCooked && order.isDelivered() == isDelivered
            ){
                orders.add(order);
            }
        }
        return orders;
    }
    public static Customer findCustomer(String customerId){
        for(int i=0;i< PersonList.getInstance().size();i++){
            Person person = PersonList.getInstance().get(i);
            if(person.getPid().equals(customerId)){
                return (Customer) person;
            }
        }
        return null;
    }


}