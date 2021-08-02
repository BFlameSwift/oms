package dish;
import mode.*;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author ly
 */
public class Dish{
    public String getDishName() {
        return dishName;
    }
    public void setDishName(String dishName) {
        this.dishName = dishName;
    }
    public String getDishId() {
        return dishId;
    }
    public void setDishId(String dishId) {
        this.dishId = dishId;
    }
    public int getSum() {
        return sum;
    }
    public void setSum(int sum) {
        this.sum = sum;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    private String dishName,dishId;
    private int sum;
    private double price;

    public Dish(String dishId,String dishName,double price,int sum){
        this.setPrice(price);
        this.setSum(sum);
        this.setDishName(dishName);
        this.setDishId(dishId);
    }
    public static boolean checkSum(String  sumString){
        Pattern r = Pattern.compile(INTEGER);
        return r.matcher(sumString).find();
    }

    public static  boolean checkPrice(String priceString){
//        Pattern pattern = Pattern.compile("^[-\\+]?([1-9]\\d*.\\d*|0\\.\\d*[1-9]\\d*)");
        Pattern pattern = Pattern.compile(DOUBLE);
        if(!pattern.matcher(priceString).matches()){
            return false;
        }
        double price = Double.parseDouble(priceString);
        return price >=0;

    }
    public static boolean checkName(String name){
        int judge = 0;
        for(int i=0;i<name.length();i++){
            char c = name.charAt(i);
            if (checkCharIsNumOrAlp(c)){
                judge ++;
            }
        }
        return judge == name.length();
    }
    public static boolean checkDishID(String dishId){
        String[] dishIdHead = {"H","C","O"};
        List<String> dishIdArray = Arrays.asList(dishIdHead);
        if( ! dishIdArray.contains(dishId.substring(0,1))
                    || dishId.length()!=7 ){

            return false;
        }
        String subIdNum = dishId.substring(1);
        Pattern r = Pattern.compile(INTEGER);
        return r.matcher(subIdNum).find();
//        try{
//            String subId = dishId.substring(1);
//            Integer.parseInt(subId);
//            return true;
//        }catch (NumberFormatException e){
//            return false;
//        }
    }
    public static boolean checkCharIsNumOrAlp(char c){
        if((c>='0'&&c<='9')|| (c>='a'&&c<='z') || (c>='A'&&c<='Z') ) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "DID:" + dishId + ',' +
                "DISH:" + dishName + ',' +
                "PRICE:" + String.format("%.1f",price) +
                ",TOTAL:" + sum
                ;
    }
    public static final String DOUBLE = "^"
            + "(([\\s]*)?)"
            + "([\\d]+)"
            + "("
            + "(.)"
            + "([\\d]+)"
            + ")?"
            + "$";
     public static final String INTEGER = "^"
            + "([\\d]+)"
            + "$";
    public static boolean checkPageNum(String strN,String strM ){
        final Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        boolean judgeN = pattern.matcher(strN).matches();
//        if(!judgeN)
//            System.out.println("n");
        boolean judgeM = pattern.matcher(strM).matches();
//        if(!judgeM)
//            System.out.println("m");
        if(!judgeM || !judgeN){
            return false;
        }

        int n = Integer.parseInt(strN),m = Integer.parseInt(strM);
        return m > 0 ;
    }
    public static void showMenu(ArrayList<Dish> dishList, int n , int m){
        if(dishList.isEmpty()){
            Print.menuIsEmptyExitBack();
            return;
        }
        // TODO 别忘了nm出界的情况
        int dishSize = dishList.size();

        int lastPage = (dishSize-1)/m+1;// 向下取整
        if(n > lastPage) {
            n = lastPage;
        }

        else if(n < 1){
            n = 1;
        }int nowPage = n;

        int minDishAndLen = Math.min(nowPage * m,dishList.size());
        printMenuWithPage(dishList, n, m);

        while(true){

//            Scanner in = new Scanner(System.in);
            String orderBegin = Main.in.nextLine();
            String[] subOrder =orderBegin.split("\\s+");
            String orderStr = subOrder[0];
            String orderSet = "nlfq";
            char order = orderStr.charAt(0);

            if(orderStr.length()!=1
                    || subOrder.length!=1
                    || ( orderSet.indexOf(order)<0)){
                Print.callInnerMethodIllegal();
                continue;
            }

            if(order == 'l'){
                if(nowPage == 1){
                    Print.thisIsTheFirstPage();
                    continue;
                }
                nowPage -= 1;
            }
            else if (order == 'n'){
                if(nowPage == lastPage){
                    Print.thisIsTheLastPage();
                    continue;
                }
                nowPage ++;
            }
            else if (order == 'f'){
                nowPage = 1;
            }
            else if(order == 'q'){
                Print.exitPageCheckMode();
                break;
            }
            printMenuWithPage(dishList,nowPage,m);

        }
        return;
    }
    public static void printMenuWithPage(ArrayList<Dish> dishList,int n ,int m){
        int lastDishNum = Math.min(n*m, dishList.size());
        System.out.println("Page: "+ n);
        for(int i=(n-1)*m;i<lastDishNum;i++){
            int bias = i - (n-1)*m;
            System.out.println(bias+1+". "+dishList.get(i).toString());
        }
        System.out.println("n-next page,l-last page,f-first page,q-quit");

    }

}

