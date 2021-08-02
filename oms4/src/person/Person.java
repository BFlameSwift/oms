package person;
import mode.*;
import dish.*;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
/**
 * @author ly
 */
public class Person{
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhoneNum() {
        return phoneNum;
    }
    public boolean setPhoneNum(String phoneNum) {
        if(! checkNum(phoneNum,this.sex) ){
            System.out.println("Phone number illegal");
            return false;
        }
        this.phoneNum = phoneNum;
        return true;
    }
    public char getSex() {
        return sex;
    }
    public boolean setSex(char sex) {
        if(! checkSex(sex)){
            System.out.println("Sex illegal");
            return false;
        }
        this.sex = sex;
        return true;
    }

    public String getPid() {
        return pid;
    }
    public void setPid(String pid) {
        this.pid = pid;
    }
    public String getPwd() {
        return pwd;
    }
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public static boolean checkName(String name){
       return Dish.checkName(name);
    }
    public static boolean checkSex(char sex){
        return sex == 'M' || sex == 'F';
    }
    public  static boolean checkNum(String phoneNum,char sex){
        if(phoneNum.length()!=11){
            return false;
        }
        if(!Character.isDigit(phoneNum.charAt(0))){
            return false;
        }
        int headNum = 0, lastNum = 0;
        for(int i=0;i<3;i++){
            headNum = headNum*10+phoneNum.charAt(i) -'0';
        }

        for(int i=7;i<10;i++){
            lastNum = lastNum*10+phoneNum.charAt(i)-'0';
        }
        boolean judge = true;
        if(headNum<130||headNum>187) {
            judge = false;
        }

        if(lastNum<31||lastNum>71) {
            judge = false;
        }

        if(!((phoneNum.charAt(10)=='1'&&sex=='F')
                ||(phoneNum.charAt(10)=='0'&&sex=='M'))) {
            judge = false;
        }
        return judge;
    }
    public static boolean onlyCheckPhoneNum(String phoneNum){
        if(phoneNum.length()!=11){
            return false;
        }
        if(!Character.isDigit(phoneNum.charAt(0))){
            return false;
        }
        int headNum = 0, lastNum = 0;
        for(int i=0;i<3;i++){
            headNum = headNum*10+phoneNum.charAt(i) -'0';
        }

        for(int i=7;i<10;i++){
            lastNum = lastNum*10+phoneNum.charAt(i)-'0';
        }
        boolean judge = true;
        if(headNum<130||headNum>187) {
            judge = false;
        }
        if(lastNum<31||lastNum>71) {
            judge = false;
        }
        int last = phoneNum.charAt(10);
        if(last>'1'||last<'0') {
            judge = false;
        }
        return judge;
    }
    public static void personCommandNp(String[] subCommand){
        int subCommandLen = subCommand.length;
        if(subCommandLen != 4){
            Print.paramsCountIllegal();
            return;
        }
        if(subCommand[2].length()!=1){
            System.out.println("Sex illegal");
            return;
        }
        Person person = new Person("",'c',"","");
        String name = subCommand[1],phoneNum = subCommand[3];
        char gender = subCommand[2].charAt(0);

        boolean judgeSex= person.setSex(gender);
        if(!judgeSex) {
            return;
        }

        boolean judgePhoneNum = person.setPhoneNum(phoneNum);
        if(!judgePhoneNum) {
            return;
        }

        person.setName(name);
        System.out.print(person.toString());


    }
    public static boolean checkPid(String pid){
        boolean judge = true;
        if(pid.length() != 7){
            return false;
        }
        String[] signalArray = {"Cu","Wa","Co"};
        List<String> signalList = Arrays.asList(signalArray);
        String pidSignal = pid.substring(0,2);
        if(!signalList.contains(pidSignal)){
            return false;
        }
        String pidNum = pid.substring(2);
        Pattern pattern = Pattern.compile(Dish.INTEGER);
        return pattern.matcher(pidNum).find();
    }
    public static boolean checkPwd(String pwd){
        int pwdLen = pwd.length();
        if(pwdLen < 8 || pwdLen > 18){
            return false;
        }
        int charNum = 0,numNum = 0;
        for(int i=0;i<pwdLen;i++){
            char nowChar = pwd.charAt(i);
            if(Character.isDigit(nowChar)){
                numNum ++;
            }
            else if(Character.isLowerCase(nowChar) || Character.isUpperCase(nowChar)){
                charNum ++;
            }
            else if (nowChar<33||nowChar>126){
                return false;
            }
        }
        if(charNum ==0 || numNum == 0){
            return false;
        }


        return true;
    }

    private String name,phoneNum;
    private String pid;
    private String pwd;
    private  char sex;
    public String type;


    public Person(String n,char s,String p,String pid){
        if(!Person.checkPid(pid)){
            return;
        }
        setPid(pid);
        setPwd("oms1921SE");
        if(!Person.checkSex(s)){
            System.out.println("Sex illegal");
            return;
        }setSex(s);
        if(!Person.checkNum(p,s)){
            System.out.println("Phone number illegal");
            return;
        }
        setPhoneNum(p);

        setName(n);

    }

    @Override
    public String toString() {
        return "Name:" +name+'\n'+
                "Sex:"+sex+'\n'+
                "Phone:" + phoneNum +'\n'
                ;
    }
    public Person addPerson(String n,char s,String p,String pid){//TODO String pid and default password
        if(!Person.checkPid(pid)){
            return null;
        }
        setPid(pid);
        setPwd("oms1921SE");
        if(!Person.checkSex(s)){
            System.out.println("Sex illegal");
            return null;
        }setSex(s);
        if(!Person.checkNum(p,s)){
            System.out.println("Phone number illegal");
            return null;
        }
        setPhoneNum(p);

        setName(n);
        return this;
    }





}