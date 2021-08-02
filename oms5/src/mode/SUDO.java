package mode;
import java.util.Arrays;
import java.util.List;

import dish.Menu;
import list.PersonList;
import person.*;

/**
 * @author ly
 */
public class SUDO {
    public static void sudoMode(){
        enterSudoMode();

        String[] sudoSnSet = {"sncu","snwa","snco"};
        String[] sudodSet = {"dcu","dwa","dco"};
        String[] sudoElseSet = {"pp","SQ"};
        String[] otherCommandSet = {"gd", "nd" ,"udd ","pm"};

        List<String> sudoSnList = Arrays.asList(sudoSnSet);
        List<String> sudodList = Arrays.asList(sudodSet);
        List<String> sudoElseList = Arrays.asList(sudoElseSet);
        List<String> otherCommandList = Arrays.asList(otherCommandSet);

    
        while(true){
            String sudoCommandStr = Main.in.nextLine();
            String[] subCommand = sudoCommandStr.split("\\s+");
            String command = subCommand[0];
            if(sudoSnList.contains(command)){
                sudoAddPerson(subCommand);
            }
            else if(sudodList.contains(command)){
                sudoDeletePerson(subCommand);
            }
            else if(sudoElseList.contains(command)){
                if(subCommand.length!=1){
                    Print.paramsCountIllegal();
                    continue;
                }
                if("SQ".equals(command)){
                    quitSudoMode();
                    break;
                }
                else if("pp".equals(command)){

                    sudoCommmandPp();
                }
            }
            else if(otherCommandList.contains(command)){
                switch (command) {
                    case "pm" -> Menu.menuCommandPm(subCommand);
                    case "gd" -> Menu.menuCommandGd(subCommand);
                    case "udd" -> Menu.menuCommandUdd(subCommand);
                    case "nd" -> Menu.menuCommandNd(subCommand);
                    default -> {
                    }

                }
            }
            else {
                System.out.println("Call sudo method illegal");
            }
        }
    }
    public static void enterSudoMode(){
        System.out.println("Enter sudo mode");
    }
    public static void quitSudoMode(){
        System.out.println("Quit sudo mode");
    }
    public static int findWherePid(String pid){

        for(int i = 0; i< PersonList.getInstance().size(); i++){
            Person newPerson = PersonList.getInstance().get(i);
            if(newPerson.getPid().equals(pid)){
                return i;
            }
        }
        return -1;
    }
    public static int findWhereName(String name){
        for(int i=0;i<PersonList.getInstance().size();i++){
            Person newPerson = PersonList.getInstance().get(i);
            if(newPerson.getName().equals(name)){
                return i;
            }
        }
        return -1;
    }
    public static boolean checkPhoneNumisExists(String phoneNum){
        for(int i=0;i<PersonList.getInstance().size();i++){
            Person newPerson = PersonList.getInstance().get(i);
            if(newPerson.getPhoneNum().equals(phoneNum)){
                return true;
            }
        }
        return false;
    }
    public static void sudoAddPerson(String[] subCommand){
        int subCommandLen = subCommand.length;
        String command = subCommand[0];
        if(subCommandLen != 5){
        Print.paramsCountIllegal();
        return;
    }
        String name = subCommand[1],sexStr = subCommand[2];
        String phoneNum = subCommand[3],pid = subCommand[4];
        //begin checkSex
        if(sexStr.length()!=1){
            System.out.println("Sex illegal");
            return ;
        }
        char sex = sexStr.charAt(0);
        if(!Person.checkSex(sex)){
            System.out.println("Sex illegal");
            return;
        }
        //Sex legal begin check phoneNum
        if(! Person.onlyCheckPhoneNum(phoneNum)){
            System.out.println("Phone number illegal");
            return;
        }// whether matched
        if(! Person.checkNum(phoneNum,sex)){
            System.out.println("Phone number doesn't match sex");
            return;
        }
        if(checkPhoneNumisExists(phoneNum)){
            System.out.println("Phone number exists");
            return;
        }
        // phoneNum legal begin check pid
        boolean judgePid ;
        boolean pidIsExists = findWherePid(pid)>=0;
        switch (command){
            case "sncu":
                judgePid = Customer.checkPid(pid);
                if(!judgePid){
                    System.out.println("Customer PID illegal");
                }
                else if(pidIsExists){
                    System.out.println("Customer PID exists");
                }
                else{
                    Customer customer = new Customer(name,sex,phoneNum,pid);
                    PersonList.getInstance().add(customer);
                    System.out.println("Add new customer success");
                }
                break;
            case "snwa":
                judgePid = Waiter.checkPid(pid);
                if(!judgePid){
                    System.out.println("Waiter PID illegal");
                }
                else if(pidIsExists){
                    System.out.println("Waiter PID exists");
                }
                else{
                    Waiter waiter = new Waiter(name,sex,phoneNum,pid);
                    PersonList.getInstance().add(waiter);
                    System.out.println("Add new waiter success");
                }
                break;
            case "snco":
                judgePid = Cook.checkPid(pid);
                if(!judgePid){
                    System.out.println("Cook PID illegal");
                }
                else if(pidIsExists){
                    System.out.println("Cook PID exists");
                }
                else{
                    Cook cook = new Cook(name,sex,phoneNum,pid);
                    PersonList.getInstance().add(cook);
                    System.out.println("Add new cook success");
                }
                break;
            default:
                break;
        }
    }
    public static void sudoDeletePerson(String[] subCommand){
        int subCommandLen = subCommand.length;
        if(subCommandLen!= 2){
            Print.paramsCountIllegal();
            return;
        }
        String commmand = subCommand[0];
        String pid = subCommand[1];
        boolean judgePid ;
        int pidLocation = findWherePid(pid);
        boolean pidIsExists = pidLocation>=0;

        switch(commmand){

            case "dcu":{
                judgePid = Customer.checkPid(pid);
                if(!judgePid){
                    System.out.println("D-Customer PID illegal");
                }
                else if(!pidIsExists){
                    System.out.println("D-Customer PID doesn't exist");
                }
                else{
                    PersonList.getInstance().remove(pidLocation);
                    System.out.println("Delete customer success");

                }
                break;
            }
            case "dwa":{
                judgePid = Waiter.checkPid(pid);
                if(!judgePid){
                    System.out.println("D-Waiter PID illegal");
                }
                else if(!pidIsExists){
                    System.out.println("D-Waiter PID doesn't exist");
                }else{
                    PersonList.getInstance().remove(pidLocation);
                    System.out.println("Delete waiter success");

                }
                break;
            }
            case "dco":{
                judgePid = Cook.checkPid(pid);
                if(!judgePid){
                    System.out.println("D-Cook PID illegal");
                } else if (!pidIsExists) {
                    System.out.println("D-Cook PID doesn't exist");
                }else{
                    PersonList.getInstance().remove(pidLocation);
                    System.out.println("Delete cook success");
                }
                break;
            }default:
                break;
        }

    }
    public static void sudoCommmandPp(){

        if(PersonList.getInstance().isEmpty()){
            System.out.println("Empty person list");
            return;
        }
        PersonList.sortList();
        int listLen = PersonList.getInstance().size();
            for(int i=0;i<listLen;i++){
                Person nowPerson = PersonList.getInstance().get(i);
                System.out.println(i+1+"."
                        +"PID:"+nowPerson.getPid()
                        +",Name:"+nowPerson.getName()
                        +",Sex:"+nowPerson.getSex()
                        +",Phone:"+nowPerson.getPhoneNum()
                        +",PWD:"+nowPerson.getPwd()
                );
            }



    }


}
