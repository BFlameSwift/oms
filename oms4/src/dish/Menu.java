package dish;
import mode.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;
import java.util.regex.Pattern;

/**
 * @author ly
 */
public class Menu {
    public ArrayList<Dish> allDish = new ArrayList<>(1000);

    public void update(){
        this.sortList();
    }

    public void print(){
        for(int i = 1;i<=allDish.size();i++){
            System.out.println(i+". "+allDish.get(i-1).toString());
        }
    }
    public Dish getDishById(String did){
        for(int i=0;i<allDish.size();i++){
            Dish now = allDish.get(i);
            if(now.getDishId().equals(did)){
                return now;
            }
        }
        return null;
    }
    public ArrayList<Dish> getDishByKeyWord(String keyword){
        ArrayList<Dish> findKeyDish = new ArrayList<>(1000);
        for (Dish now : allDish) {
            boolean judge = now.getDishName().toLowerCase().contains(keyword.toLowerCase());
            if (judge) {
                findKeyDish.add(now);
            }
        }
        return findKeyDish;
    }
    public void sortList(){
        Collections.sort(allDish,comparator);
    }
    Comparator<Dish> comparator = new Comparator<>() {
        @Override
        public int compare(Dish o1, Dish o2) {
            int o1FirstChar = o1.getDishId().charAt(0);
            int o2FirstChar = o2.getDishId().charAt(0);
            if (o1FirstChar != o2FirstChar) {
//                String[] optionDish = {"H","C","O"};
                String optionDish = "HCO";
                int o1Priority = optionDish.indexOf(o1FirstChar);
                int o2Priority = optionDish.indexOf(o2FirstChar);
                return o1Priority - o2Priority;
            } else {
                int thisNum = Integer.valueOf(o1.getDishId().substring(1));
                int oNum = Integer.valueOf(o2.getDishId().substring(1));
//                System.out.println(thisNum);
//                System.out.println(oNum);
                return thisNum - oNum;
            }
        }
    };


    public static void menuCommandGd(Menu menu, String[] subCommand){
        int subCommandLen = subCommand.length;
        String parameter;
        if(subCommandLen>1){
            parameter = subCommand[1];
        }
        else{
            Print.commandNotExist();
            return;
        }
        if("-id".equals(parameter)){
            if(subCommandLen!=3){
                Print.paramsCountIllegal();
                return;
            }
            String did = subCommand[2];
            if(!Dish.checkDishID(did)){
                Print.didInputIllegal();
                return;
            }
            Dish foundDish =  menu.getDishById(did);
            if(foundDish == null){
                Print.dishDoesNotExist();
            }
            else{
                System.out.println(foundDish.toString());
            }
        }
        else if("-key".equals(parameter)){

            if(subCommandLen!=3 && subCommandLen != 5){
                Print.paramsCountIllegal();
                return;
            }
            String nStr,mStr;
            int n,m;
            String keyWord = subCommand[2];
            ArrayList<Dish>  foundMenu = menu.getDishByKeyWord(keyWord);

            if(foundMenu.isEmpty()){
                Print.dishDoesNotExist();
                return ;
            }
            else{
                if(subCommandLen == 5){
                    nStr = subCommand[3];mStr =subCommand[4];
                    boolean judgeNum = Menu.checkPageNum(nStr,mStr);
                    if(!judgeNum){
                        Print.pageSliceMethodParamsInputIllegal();
                        return;
                    }
                    n = Integer.parseInt(nStr);
                    m = Integer.parseInt(mStr);
                    Menu.showMenu(foundMenu,n,m);
                    return;
                }
                for(int i=0;i<foundMenu.size();i++){
                    System.out.println(i+1+". "+foundMenu.get(i).toString());
                }// subLen = 3
            }
        }
        else{
            Print.commandNotExist();
        }
    }
    public static void menuCommandUdd(Menu menu, String[] subCommand){
        int subCommandLen = subCommand.length;
        String[] uddCommand = {"-n","-t","-p"};
        List<String> uddListCommand = Arrays.asList(uddCommand);
        String uddOption,dishId;
        if(! Main.checkxInstructionParamIsLegal(
                subCommand,4,uddListCommand,2)){
            return;
        }
        uddOption = subCommand[1];
//        if(subCommandLen > 1) {
//            uddOption = subCommand[1];
//        }
//        else{
//            Print.commandNotExist();
//            return;
//        }
//
//        if(! uddListCommand.contains(uddOption)){
//            Print.commandNotExist();
//            return;
//        }
//        if(subCommandLen != 4) {
//            Print.paramsCountIllegal();
//            return;
//        }
        dishId = subCommand[2];
        if(! Dish.checkDishID(dishId) ){
            Print.didInputIllegal();
            return;
        }

        Dish beFoundDish = menu.getDishById(dishId);
        if(beFoundDish == null){
            Print.dishDoesNotExist();
            return;
        }
        if("-n".equals(uddOption)){
            String newDishName = subCommand[3];
            if( ! Dish.checkName(newDishName) ){
                System.out.println("New name input illegal");
                return;
            }
            if(menu.checkNdNameIsExist(newDishName)){
                System.out.println("New name repeated");
                return;
            }
            beFoundDish.setDishName(newDishName);
            System.out.println("Update dish's name success");
        }
        else if("-t".equals(uddOption)){
            String total = subCommand[3];
            if(!Dish.checkSum(total)){
                System.out.println("Change dish's total illegal");
                return;
            }

            beFoundDish.setSum(Integer.parseInt(total));
            System.out.println("Update dish's total success");
        }
        else if("-p".equals(uddOption)){
            String priceString = subCommand[3];

            if(!Dish.checkPrice(priceString)){
                System.out.println("Change dish's price illegal");
                return;
            }
            double price = Double.parseDouble(subCommand[3]);
//            beFoundDish.price =  price;
            beFoundDish.setPrice(price);
            System.out.println("Update dish's price success");
        }
    }
    public static void menuCommandNd(Menu menu, String[] subCommand){
        int subCommandLen = subCommand.length;

        if(subCommandLen != 5){
            Print.paramsCountIllegal();
            return;
        }
        String sumString = subCommand[4];
        String priceString = subCommand[3];
        String name = subCommand[2],id =  subCommand[1];
        if(! Dish.checkDishID(id)){
            Print.didInputIllegal();
            return;
        }
        if(menu.getDishById(id)!=null){
            Print.dishExists();
            return;
        }
        if( ! Dish.checkPrice(priceString)
                || ! Dish.checkSum(sumString)
                || ! Dish.checkName(name)  ){
            System.out.println("New dish's attributes input illegal");
            return;
        }
        if ( menu.checkNdNameIsExist(name)){
            System.out.println("Name repeated");
            return;
        }
        int sum = Integer.parseInt(sumString);
        double price = Double.parseDouble(subCommand[3]);
        Dish dishToBeAdded = new Dish(id,name, price ,sum);
        menu.allDish.add(dishToBeAdded);
        System.out.println("Add dish success");
        return ;
    }
    public static void menuCommandPm(Menu menu, String[] subCommand){
        int subCommandLen = subCommand.length;
        if(subCommandLen!=3 && subCommandLen != 1){
            Print.paramsCountIllegal();
            return;
        }
        String nStr, mStr ;
        if(subCommandLen==3){
            nStr = subCommand[1];mStr = subCommand[2];
            boolean judgeNum = Menu.checkPageNum(nStr,mStr);
            if(!judgeNum){
                Print.pageSliceMethodParamsInputIllegal();
                return;
            }
            int n = Integer.parseInt(nStr), m = Integer.parseInt(mStr);

            Menu.showMenu(menu.allDish,n,m);
            return;

        }
        else {//subCommand == 1
            if(menu.allDish.isEmpty()){
                Print.emptyMenu();
            }
            else{
//                showMenu(menu.allDish,1,10000);
                for(int i=0;i<menu.allDish.size();i++){
                    System.out.println(i+1+". "+menu.allDish.get(i).toString());
                }
            }
        }



    }



    public boolean checkNdNameIsExist(String name){
        for(int i=0;i<this.allDish.size();i++){
            String thisDishName = this.allDish.get(i).getDishName();
            if(name.equals(thisDishName)){
                return true;
            }
        }
        return false;
    }
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
    public static void showMenu(ArrayList<Dish> dishList,int n ,int m){
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
