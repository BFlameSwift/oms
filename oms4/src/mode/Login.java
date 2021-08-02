package mode;
import java.util.Arrays;
import java.util.List;

import dish.Dish;
import person.*;
/**
 * @author ly
 */
public class Login {

    public static void loginByPwdorName(String[] subCommand){
        String[] loginCmdSet = {"-i","-n"};
        List<String> loginCmdList = Arrays.asList(loginCmdSet);
        int subCommandLen = subCommand.length;
        if(!Main.checkxInstructionParamIsLegal(
                subCommand,4,loginCmdList,2 ) ){
            return; // inlegal 返回
        }
        String option = subCommand[1];

        String password = subCommand[3];
        if("-i".equals(option)){
            String pid = subCommand[2];
            int personLocation = SUDO.findWherePid(pid);

           if(!Person.checkPid(pid)){
                System.out.println("PID illegal");
            }
           else if(personLocation < 0){
               System.out.println("Pid not exist");
           }

            else{
                // pid normal
                Person person = Main.personList.list.get(personLocation);
                if(!person.getPwd().equals(password)){
                    System.out.println("Password not match");
                    return;
                }
                // Login success
                System.out.println("Login success");
                loginMode(person);
            }
        }
        else if("-n".equals(option)){
            String name = subCommand[2];
            int personLocation = SUDO.findWhereName(name);
            if(!Person.checkName(name)){
                System.out.println("Pname illegal");
            }
            else if(personLocation < 0){
                System.out.println("Pname not exist");
            }
            else{
                Person person = Main.personList.list.get(personLocation);
                if(!person.getPwd().equals(password)){
                    System.out.println("Password not match");
                    return;
                }
                System.out.println("Login success");
                loginMode(person);
            }


        }


    }
    public static void loginMode(Person person){

        String[] orderSet = {"chgpw","myinfo","back","QUIT"};
        List<String> orderList = Arrays.asList(orderSet);

        while(true){
            String loginCommandLine = Main.in.nextLine();
            String[] subCommand = loginCommandLine.split("\\s+");
            int subCommandLen = subCommand.length;

            if(subCommandLen<1){
                //TODO printWhat
                Print.commandNotExist();
                continue;
            }
            String command = subCommand[0];
            if(specificUserLogin(subCommand,person)){
                continue;
            }// true 表示已经进行了，false继续进行方法

            if(!orderList.contains(command)){
                Print.commandNotExist();
                continue;
            }
//             begin chooseCommand
            if("chgpw".equals(command)){
                if(subCommandLen != 3 ){
                    Print.paramsCountIllegal();
                    continue;
                }
                String newPwd = subCommand[1], newPwdAgain = subCommand[2];
                if(! Person.checkPwd(newPwd)){
                    System.out.println("New password illegal");
                    continue;
                }
                else if(!newPwd.equals(newPwdAgain)){
                    System.out.println("Not match");
                    continue;
                }
                else {
                    person.setPwd(newPwd);
                    System.out.println("Change password success");
                }
            }
            else if("myinfo".equals(command)){
                System.out.println(
                        "[info]\n" +
                                "| name:\t" + person.getName() + "\n" +
                                "| Sex:\t" + person.getSex() + "\n" +
                                "| Pho:\t" + person.getPhoneNum()+ "\n" +
                                "| PID:\t" + person.getPid() + "\n" +
                                "| Pwd:\t" + person.getPwd() + "\n" +
                                "| Type:\t" + person.type
                );
            }
            else if("back".equals(command)){
                System.out.println("Logout success");
                return;
            }
            else if("QUIT".equals(command)){
                Main.goodbye();
            }

        }
    }
    private static boolean specificUserLogin(String[] subCommand,Person person ){
        //false: 处理失败，继续进行源程序，true 直接continue;
        //子程序同理
        String identity = person.getPid().substring(0,2);
        switch (identity){
            //等以后其他身份
            case "Cu":
                return loginModeCus((Customer) person,subCommand);

            default:
                return false;


        }


    }
    public static boolean loginModeCus(Customer customer,String[] subCommand){
        String[] commandSet = {"rc","aplVIP","gb"};
        List<String> commandList = Arrays.asList(commandSet);
        String command = subCommand[0];
        if(!commandList.contains(command)){
            return false;
        }
        switch (command) {
            case "rc" : {
                int subCommandLength = subCommand.length;
                if (subCommandLength != 2) {
                    Print.paramsCountIllegal();
                    return true;//源程序continuee;
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
                customer.balance += charge;
                return true;
            }
            case "aplVIP" : {
                if (subCommand.length != 1) {
                    Print.paramsCountIllegal();
                    return true;//源程序continuee;
                }
                double money = customer.balance;
                if (money >= 200.0) {
                    customer.setVip(true);
                    Print.applyVipSuccess();

                } else {
                    customer.setVip(false);
                    Print.pleaseRechargeMore();
                }
                return true;
            }
            case "gb" : {
                if (subCommand.length != 1) {
                    Print.paramsCountIllegal();
                    return true;//源程序continuee;
                }

                System.out.printf("Balance: " + "%.1f\n", customer.balance);
                return true;
            }
            default : return true;

        }

    }
}

