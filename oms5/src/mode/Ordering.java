package mode;

import dish.Dish;
import dish.Menu;
import list.Order;
import person.Customer;

import java.util.Arrays;
import java.util.List;

/**
 * @author ly
 * @date 2021/5/6 11:15
 */
public class Ordering {

    public static  boolean orderMode(Customer customer){
        Order order = findNotFinishedOrder(customer.getPid());
        boolean hasExistOrder = true;
        if( order == null){
            order = new Order(customer.getPid());
            hasExistOrder = false;
        }

        String[] commandSet = {"add","finish"};
        List<String> commandList = Arrays.asList(commandSet);

        String orderId = customer.getPid()+"_"+customer.getOrderNum();
        order.setOrderId(orderId);

        while(true){
            String rawCommand = Main.in.nextLine() ;
            String[] subCommand = rawCommand.split("\\s+");

            String option = subCommand[0];
            int subCommandLength = commandList.size();
            if(! commandList.contains(option)){
                Print.commandNotExist();
                continue;
            }

            if(option.equals("add")){

                String[] optionSet = {"-n","-i"};
                List<String> optionList =Arrays.asList(optionSet);
                boolean judge = Main.checkxInstructionParamIsLegal
                        (subCommand,4,optionList,2);
                if(!judge){
                   continue;
                }// 参数非法已经输出完成
                order.sortList();
                String subOption = subCommand[1];
                if(subOption.equals("-i")){
                    String did = subCommand[2];
                    int m = Integer.valueOf(subCommand[3]);
                    Dish dish = Menu.getDishById(did);
                    if(dish == null){
                        Print.dishSelectedNotExist();
                        continue;
                    }
                    if(dish.getSum() == 0){
                        Print.dishSelectedIsSoldOut();
                        continue;
                    }
                    if(m > dish.getSum()){
                        Print.dishIsOutOfStock();
                        continue;
                    }
                    dish.setSum(dish.getSum()-m);

                    Dish findDish = findSameDish(order,dish.getDishId());
                    if(findDish!=null){
                        findDish.setSum(findDish.getSum()+m);
                        continue;
                    }

                    Dish  dishBeAdd = new Dish(dish.getDishId(),
                            dish.getDishName(),dish.getPrice(),m);
                    order.addDish(dishBeAdd);

                }
                else if(subOption.equals("-n")){
                    String dishName = subCommand[2];
                    int m = Integer.valueOf(subCommand[3]);
                    Dish dish = Menu.getDishByName(dishName);
                    if(dish == null){
                        Print.dishSelectedNotExist();
                        continue;
                    }
                    if(dish.getSum() == 0){
                        Print.dishSelectedIsSoldOut();
                        continue;
                    }
                    if(m> dish.getSum()){
                        Print.dishIsOutOfStock();
                        continue;
                    }
                    dish.setSum(dish.getSum()-m);
                    Dish findDish = findSameDish(order,dish.getDishId());
                    if(findDish!=null){
                        findDish.setSum(findDish.getSum()+m);
                        continue;
                    }
                    Dish  dishBeAdd = new Dish(dish.getDishId(),dish.getDishName(),dish.getPrice(),m);
                    order.addDish(dishBeAdd);
                }
            }

            else if (option.equals("finish")) {
                if(order.dishList.size() == 0){
                    Print.pleaseSelectAtLeastOneDishToYourOrder();
                    continue;
                }
                if(!hasExistOrder)
                    Order.getInstances().add(order);
                return true;
            }

        }

    }
    private static  Dish findSameDish(Order order,String did){
        for(Dish dish:order.dishList){
            if(dish.getDishId().equals(did)){
                return dish;
            }
        }
        return null;
    }
    private static Order findNotFinishedOrder(String customerId){
        for(Order order :Order.getInstances()){
            if(!order.isConfirmed() && order.getCustomerId().equals(customerId)){
                return order;
            }
        }
        return null;
    }
}
