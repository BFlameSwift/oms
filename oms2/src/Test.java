
import java.util.*;
/**
 * @author ly
 */
public class Test{
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Menu menu = new Menu();
        while(true){
            String command = in.nextLine();
            if("QUIT".equals(command)){
                break;
            }
            String[] subCommand =command.split("\\s+");

            String headCommand = subCommand[0];
            String[] menuCommand = {"gd","nd","udd","pm","np"};
            Dish dishExampleNeverUsed= new Dish();
            List<String> listMenuCommand = Arrays.asList(menuCommand);
            if (listMenuCommand.contains(headCommand) ){
                if(! menu.allDish.isEmpty()){
                    menu.update();
                }
                chooseMenuCommand(headCommand,dishExampleNeverUsed,menu,subCommand);
            }
            else{
                commandNotExist();
            }
        }
        goodbye();
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
//            System.out.println("Arguments illegal");
            paramsCountIllegal();
//            paramsCountIllegal();
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
            parameter = subCommand[1];  /
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
            if(subCommandLen!=3){
                paramsCountIllegal();
                return;
            }
            String keyWord = subCommand[2];
            ArrayList<Dish>  foundMenu = menu.getDishByKeyWord(keyWord);

            if(foundMenu.isEmpty()){
                dishDoesNotExist();
                return ;
            }
            else{
                // no need to sort
                for(int i=0;i<foundMenu.size();i++){
                    System.out.println(i+1+". "+foundMenu.get(i).toString());
                }
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
        if(subCommandLen!=1){
            paramsCountIllegal();
            return;
        }
        if(menu.allDish.isEmpty()){
            System.out.println("Empty Menu");
        }
        else{
            menu.print();
        }
        return;
    }
    public static void goodbye(){
        System.out.println("----- Good Bye! -----");
        System.exit(0);
    }

}


