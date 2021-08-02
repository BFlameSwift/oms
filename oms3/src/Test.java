
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;




/**
 * @author ly
 */
public class Test {
    static Scanner in = new Scanner(System.in);
    public static void main(String[] args) {

        Menu menu = new Menu();
        while (true){
            String command = in.nextLine();
            String[] subCommand = command.split("\\s+");
            String headCommand = subCommand[0];
            String[] menuCommand = {"gd", "nd" ,"udd ","pm","np"};
            String[] mainCommand = {"QUIT"};
            Dish dishExampleNeverUsed=  new Dish();
            List<String> listMenuCommand = Arrays.asList(menuCommand);
            List<String> listMainCommand = Arrays.asList(mainCommand);
            if (listMenuCommand.contains(headCommand))  {
                if (! menu.allDish.isEmpty()) {
                    menu.update();
                }
                chooseMenuCommand(headCommand,dishExampleNeverUsed,menu,subCommand);
            }
            else if(listMainCommand.contains(headCommand)){
                chooseMainCommand(subCommand);
                // goodbye
            }
            else{
                commandNotExist();
            }
        }
//        goodbye();
    }

    public static void commandNotExist(){
        System.out.println("Command not exist");
    }
    public static void paramsCountIllegal(){
        System.out.println("Params' count illegal");
    }
    public static void dishExists(){
        System.out.println("Dish exists");
    }
    public static void dishDoesNotExist(){
        System.out.println("Dish does not exist");
    }
    public static void didInputIllegal(){
            System.out.println("Did input illegal");
        }
    public static void inputIllegal(){
        System.out.println("Input illegal");
    }
    public static void emptyMenu(){
        System.out.println("Empty Menu");
    }
    public static void thisIsTheLastPage(){
        System.out.println("This is the last page");
    }
    public static void thisIsTheFirstPage(){
        System.out.println("This is the first page");
    }
    public static void exitPageCheckMode(){
        System.out.println("Exit page check mode");
    }
    public static void menuIsEmptyExitBack(){
        System.out.println("Menu is empty, exit page check mode");
    }
    public static void pageSliceMethodParamsInputIllegal(){
        System.out.println("Page slice method's params input illegal");
    }
    public static void callInnerMethodIllegal(){
        System.out.println("Call inner method illegal");
    }
    public static void chooseMainCommand(String[] subCommand){
        String command = subCommand[0];
        int subCommandLen = subCommand.length;
        if("QUIT".equals(command)){
            if(subCommandLen != 1){
                paramsCountIllegal();
                return;
            }
            else {
                goodbye();
            }
        }
    }
    public static void chooseMenuCommand(String command,Dish dish,Menu menu,String[] subCommand){
        if("np".equals(command)){
            personCommandNp(subCommand);
        }
        else if("gd".equals(command)){
            menuCommandGd(menu,subCommand,dish);
        }
        else if("nd".equals(command)){
            menuCommandNd(menu,subCommand,dish);
        }
        else if("udd".equals(command)){
            menuCommandUdd(menu,subCommand,dish);
        }
        else if("pm".equals(command)){
            menuCommandPm(menu,subCommand);
        }
    }
    public static void personCommandNp(String[] subCommand){
        int subCommandLen = subCommand.length;
        if(subCommandLen != 4){
            paramsCountIllegal();
            return;
        }
        if(subCommand[2].length()!=1){
            System.out.println("Sex illegal");
            return;
        }
        Person person = new Person();
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
    public static void menuCommandGd(Menu menu, String[] subCommand, Dish dish){
        int subCommandLen = subCommand.length;
        String parameter;
        if(subCommandLen>1){
            parameter = subCommand[1];
        }
        else{
            commandNotExist();
            return;
        }
        if("-id".equals(parameter)){
            if(subCommandLen!=3){
                paramsCountIllegal();
                return;
            }
            String did = subCommand[2];
            if(!dish.checkDishID(did)){
                didInputIllegal();
                return;
            }
            Dish foundDish =  menu.getDishById(did);
            if(foundDish == null){
                dishDoesNotExist();
            }
            else{
                System.out.println(foundDish.toString());
            }
        }
        else if("-key".equals(parameter)){

            if(subCommandLen!=3 && subCommandLen != 5){
                paramsCountIllegal();
                return;
            }
            String nStr,mStr;
            int n,m;
            String keyWord = subCommand[2];
            ArrayList<Dish>  foundMenu = menu.getDishByKeyWord(keyWord);

            if(foundMenu.isEmpty()){
                dishDoesNotExist();
                return ;
            }
            else{
                if(subCommandLen == 5){
                nStr = subCommand[3];mStr =subCommand[4];
                boolean judgeNum = checkPageNum(nStr,mStr);
                if(!judgeNum){
                    pageSliceMethodParamsInputIllegal();
                    return;
                }
                n = Integer.parseInt(nStr);
                m = Integer.parseInt(mStr);
                showMenu(foundMenu,n,m);
                return;
               }
                for(int i=0;i<foundMenu.size();i++){
                    System.out.println(i+1+". "+foundMenu.get(i).toString());
                }// subLen = 3
            }
        }
        else{
            commandNotExist();
        }
    }
    public static void menuCommandUdd(Menu menu, String[] subCommand, Dish dish){
        int subCommandLen = subCommand.length;
        String uddOption,dishId;
        if(subCommandLen > 1) {
            uddOption = subCommand[1];
        }
        else{
            commandNotExist();
            return;
        }
        String[] uddCommand = {"-n","-t","-p"};
        List<String> uddListCommand = Arrays.asList(uddCommand);
        if(! uddListCommand.contains(uddOption)){
            commandNotExist();
            return;
        }
        if(subCommandLen != 4) {
            paramsCountIllegal();
            return;
        }
        dishId = subCommand[2];
        if(! dish.checkDishID(dishId) ){
            didInputIllegal();
            return;
        }

        Dish beFoundDish = menu.getDishById(dishId);
        if(beFoundDish == null){
            dishDoesNotExist();
            return;
        }
        if("-n".equals(uddOption)){
            String newDishName = subCommand[3];
            if( ! dish.checkName(newDishName) ){
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
            if(!beFoundDish.checkSum(total)){
                System.out.println("Change dish's total illegal");
                return;
            }

            beFoundDish.setSum(Integer.parseInt(total));
            System.out.println("Update dish's total success");
        }
        else if("-p".equals(uddOption)){
            String priceString = subCommand[3];

            if(!beFoundDish.checkPrice(priceString)){
                System.out.println("Change dish's price illegal");
                return;
            }
            double price = Double.parseDouble(subCommand[3]);
//            beFoundDish.price =  price;
            beFoundDish.setPrice(price);
            System.out.println("Update dish's price success");
        }
    }
    public static void menuCommandNd(Menu menu, String[] subCommand, Dish dish){
        int subCommandLen = subCommand.length;

        if(subCommandLen != 5){
            paramsCountIllegal();
            return;
        }
        String sumString = subCommand[4];
        String priceString = subCommand[3];
        String name = subCommand[2],id =  subCommand[1];
        if(! dish.checkDishID(id)){
            didInputIllegal();
            return;
        }
        if(menu.getDishById(id)!=null){
            dishExists();
            return;
        }
        if( ! dish.checkPrice(priceString)
                || ! dish.checkSum(sumString)
                || ! dish.checkName(name)  ){
            System.out.println("New dish's attributes input illegal");
            return;
        }
        if ( menu.checkNdNameIsExist(name)){
            System.out.println("Name repeated");
            return;
        }
        int sum = Integer.parseInt(sumString);
        double price = Double.parseDouble(subCommand[3]);
        Dish dishToBeAdded = new Dish();
        dishToBeAdded = dishToBeAdded.addDish(id,name, price ,sum);
        menu.allDish.add(dishToBeAdded);
        System.out.println("Add dish success");
        return ;
    }
    public static void menuCommandPm(Menu menu, String[] subCommand){
        int subCommandLen = subCommand.length;
        if(subCommandLen!=3 && subCommandLen != 1){
            paramsCountIllegal();
            return;
        }
        String nStr, mStr ;
        if(subCommandLen==3){
            nStr = subCommand[1];mStr = subCommand[2];
            boolean judgeNum = checkPageNum(nStr,mStr);
            if(!judgeNum){
                pageSliceMethodParamsInputIllegal();
                return;
            }
            int n = Integer.parseInt(nStr), m = Integer.parseInt(mStr);

            showMenu(menu.allDish,n,m);
            return;

        }
        else {//subCommand == 1
            if(menu.allDish.isEmpty()){
                emptyMenu();
            }
            else{
//                showMenu(menu.allDish,1,10000);
               for(int i=0;i<menu.allDish.size();i++){
                   System.out.println(i+1+". "+menu.allDish.get(i).toString());
               }
            }
        }



    }
    public static void goodbye(){
        System.out.println("----- Good Bye! -----");
        System.exit(0);
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
            menuIsEmptyExitBack();
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
            String orderBegin = in.nextLine();
            String[] subOrder =orderBegin.split("\\s+");
            String orderStr = subOrder[0];
            String orderSet = "nlfq";
            char order = orderStr.charAt(0);

            if(orderStr.length()!=1
                    || subOrder.length!=1
                    || ( orderSet.indexOf(order)<0)){
                callInnerMethodIllegal();
                continue;
            }

            if(order == 'l'){
                if(nowPage == 1){
                    thisIsTheFirstPage();
                    continue;
                }
                nowPage -= 1;
            }
            else if (order == 'n'){
                if(nowPage == lastPage){
                    thisIsTheLastPage();
                    continue;
                }
                nowPage ++;
            }
            else if (order == 'f'){
                nowPage = 1;
            }
            else if(order == 'q'){
                exitPageCheckMode();
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
    private static final String INTEGER = "^"
            + "([\\d]+)"
            + "$";
}


