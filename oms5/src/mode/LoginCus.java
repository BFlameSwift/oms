package mode;

import dish.Dish;
import dish.Menu;
import list.Order;
import list.PersonList;
import person.Customer;
import person.Waiter;

import java.util.Arrays;
import java.util.List;

/**
 * @author ly
 * @date 2021/5/6 11:09
 */
public class LoginCus {
    public static boolean login(Customer customer, String[] subCommand){
        String[] commandSet = {"rc","aplVIP","gb",
                "order","co","confirm",
                "pm","gd"
        };
        List<String> commandList = Arrays.asList(commandSet);
        String command = subCommand[0];
        if(!commandList.contains(command)){
            return false;
        }
        switch (command) {
            case "rc" : {
                return  rcCommand(customer,subCommand);
            }
            case "aplVIP" : {
                return aplVipCommand(customer,subCommand);
            }
            case "gb" : {
               return gbCommand(customer, subCommand);
            }
            case "gd":{
                Menu.menuCommandGd(subCommand);
                return true;
            }
            case "pm":{
                Menu.menuCommandPm(subCommand);
                return true;
            }
            case "order":{
               return orderCommand(customer, subCommand);
            }
            case "confirm":{
                //比较复杂，当confirm时退出通过login判断
                return confirmCommand(customer, subCommand);
            }
            case "co":{
                return coCommand(customer, subCommand);
            }

            default : return true;

        }

    }
    private static boolean rcCommand(Customer customer,String[]subCommand){
        int subCommandLength = subCommand.length;
        if (subCommandLength != 2) {
            Print.paramsCountIllegal();
            return true;
        }
        String chargeStr = subCommand[1];
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
    private static  boolean gbCommand(Customer customer,String[]subCommand){
        if (subCommand.length != 1) {
            Print.paramsCountIllegal();
            return true;
        }

        System.out.printf("Balance: " + "%.1f\n", customer.getBalance());
        return true;
    }
    private static boolean aplVipCommand(Customer customer,String[]subCommand){
        if (subCommand.length != 1) {
            Print.paramsCountIllegal();
            return true;
        }
        double money = customer.getBalance();
        if (money >= 200.0) {
            customer.setVip(true);
            Print.applyVipSuccess();

        } else {
            customer.setVip(false);
            Print.pleaseRechargeMore();
        }
        return true;
    }
    private static  boolean orderCommand(Customer customer,String[]subCommand){
        if(subCommand.length>1){
            Print.paramsCountIllegal();
            return true;
        }
        return Ordering.orderMode(customer);
    }
    private static boolean confirmCommand(Customer customer,String[]subCommand){
        if(subCommand.length>1){
            Print.paramsCountIllegal();
            return true;
        }
        for(Order order : Order.getInstances()){
            order.sortList();
            if(!order.isConfirmed() && order.getCustomerId().equals(customer.getPid())){
                System.out.println("Order Confirmed");

                order.setConfirmed(true);

                Waiter waiter = PersonList.chooseWaiterOrder();

                order.setWaiterId(waiter.getPid());
                waiter.orderAnOrder();
                customer.orderFinish();
                waiter.list.add(order);

                return true;
            }
        }
        System.out.println("No order can be confirmed");

        return true;
    }
    private static boolean coCommand(Customer customer, String[] subCommand){
        if(subCommand.length>1){
            Print.paramsCountIllegal();
            return true;
        }
        int i ;
        Order order1 =null,order = null;
        for(i=0;i<Order.getInstances().size();i++){
           order = Order.getInstances().get(i);
            if(!order.isConfirmed() && order.getCustomerId().equals(customer.getPid())){
                order1 = order;
                break;
            }
        }
        if(order1==null){
            System.out.println("No order");
            return true;
        }

        order.sortList();
        int now = 0;
        double sumMoney = 0;
        for(Dish dish:order.dishList){
            System.out.println(now+1+"."+
                    "DID:"+dish.getDishId() +
                    ",DISH:" + dish.getDishName()+
                    String.format(",PRICE:" + "%.1f",dish.getPrice() )+
                    ",NUM:"+dish.getSum()
            );

            sumMoney += dish.getPrice()* dish.getSum();
            now++;
        }
        order.setSumMoney(sumMoney);
        System.out.printf("|\nSUM:%.1f\n",sumMoney);
        return true;

    }

}
