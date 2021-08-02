package mode;
import dish.Menu;
import person.Person;
import person.PersonList;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * @author m
 */
public class Main {
    //主要参数设定变量声明，控制主程序进行
    public static PersonList personList = new PersonList();
    public static Scanner in = new Scanner(System.in);
    public static void goodbye(){
        System.out.println("----- Good Bye! -----");
        System.exit(0);
    }
    public static String[] menuCommand = {"gd", "nd" ,"udd ","pm","np"};
    public static String[] mainCommand = {"QUIT","SUDO","login"};
    public static List<String> listMenuCommand = Arrays.asList(menuCommand);
    public static List<String> listMainCommand = Arrays.asList(mainCommand);
    public static void chooseMainCommand(String[] subCommand){
        String command = subCommand[0];
        int subCommandLen = subCommand.length;
        if("QUIT".equals(command)){
            if(subCommandLen != 1){
                Print.paramsCountIllegal();
                return;
            }
            else {
                Main.goodbye();
            }
        }
        else if("SUDO".equals(command)){
            SUDO.sudoMode();
        }
        else if("login".equals(command)){
            Login.loginByPwdorName(subCommand);
        }
    }
    public static void chooseMenuCommand(String command, Menu menu, String[] subCommand){
        if("np".equals(command)){
            Person.personCommandNp(subCommand);
        }
        else if("gd".equals(command)){
            Menu.menuCommandGd(menu,subCommand);
        }
        else if("nd".equals(command)){
            Menu.menuCommandNd(menu,subCommand);
        }
        else if("udd".equals(command)){
            Menu.menuCommandUdd(menu,subCommand);
        }
        else if("pm".equals(command)){
            Menu.menuCommandPm(menu,subCommand);
        }
    }
    public static boolean checkxInstructionParamIsLegal(String[] subCommand, int paramNum,List<String>list ,int x ){
        //x级指令参数检测，输入子命令，参数应该有的数量，和首指令是否在List中
        // 在里面输出过了返回False; 在里面没执行，将会向下继续执行返回True
        int subCommandLen = subCommand.length;
        if(subCommandLen < x){
            Print.commandNotExist();
            return false;
        }
        String option = subCommand[x-1];//若是二级指令检测第二个元素
        if(! list.contains(option)){
            Print.commandNotExist();
            return false;
        }//可拓展性好一点
        if(subCommandLen != paramNum){
            Print.paramsCountIllegal();
            return false;
        }
        return true;
    }

}
