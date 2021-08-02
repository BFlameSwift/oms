package mode;
import java.util.Arrays;
import java.util.List;

import list.PersonList;
import person.*;
/**
 * @author ly
 */
public class Login {

    public static void loginByPwdorName(String[] subCommand){
        String[] loginCmdSet = {"-i","-n"};
        List<String> loginCmdList = Arrays.asList(loginCmdSet);
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
                Person person = PersonList.getInstance().get(personLocation);
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
                Person person = PersonList.getInstance().get(personLocation);
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
                Print.commandNotExist();
                continue;
            }
            String command = subCommand[0];
            if(specificUserLogin(subCommand,person)){
                continue;
            }

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

                }
                else if(!newPwd.equals(newPwdAgain)){
                    System.out.println("Not match");

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
        // 只能减少一部分行数 没太大用
        //子程序同理
        String identity = person.getPid().substring(0,2);
        switch (identity){

            case "Cu":
                return LoginCus.login((Customer) person,subCommand);
            case "Wa":
                return LoginWa.login((Waiter) person,subCommand);
            case "Co":
                return LoginCo.login((Cook) person, subCommand);
            default:
                return false;
        }
    }

}

