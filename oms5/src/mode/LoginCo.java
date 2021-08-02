package mode;

import list.Order;
import person.Cook;
/**
 * @author ly
 * @date 2021/5/14 10:50
 */
public class LoginCo {
    public static boolean login(Cook cook, String[] subCommand){
        String command = subCommand[0];
        if(!"cook".equals(command)){
            return false;
        }
        if(subCommand.length!=1){
            Print.paramsCountIllegal();
            return true;
        }
        cook();
        return true;
    }
    private static void cook(){
        for(int i=0;i<Main.orderCookList.size();i++){
            Order order = Main.orderCookList.get(i);
            if(order.isConfirmed()&& order.isManaged() && !order.isCooked())  {
                System.out.println("Finish order:"+order.getOrderId());
                order.setCooked(true);
                break;
            }
        }
    }
}
