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
            //make wrond sex cant print illegal
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

    private String name,phoneNum;
    private  char sex;
    public boolean checkSex(char sex){
        return sex == 'M' || sex == 'F';
    }
    public  boolean checkNum(String phoneNum,char sex){
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

    @Override
    public String toString() {
        return "Name:" +name+'\n'+
                "Sex:"+sex+'\n'+
                "Phone:" + phoneNum +'\n'
                ;
    }
    public Person addPerson(String n,char s,String p){
        Person newPerson = new Person();

        if(!newPerson.checkSex(s)){
            System.out.println("Sex illegal");
            return null;
        }setSex(s);
        if(!newPerson.checkNum(p,s)){
            System.out.println("Phone number illegal");
            return null;
        }
        setPhoneNum(p);

        setName(n);
        return this;
    }





}