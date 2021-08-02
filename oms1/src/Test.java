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
            if(command.equals("QUIT")){
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
//            else{
//                commandNotExists();
//            }
        }
        goodbye();
    }

//    public static boolean checkNdNameRepetition(Menu menu,String name){
//        for(int i=0;i<menu.allDish.size();i++){
//            String thisDishName = menu.allDish.get(i).getDishName();
//            if(name.equals(thisDishName)){
//                return false;
//            }
//        }
//        return true;
//    }
    public static void commandNotExists(){
        System.out.println("Command not exists");
    }
    public static void paramsCountIllegal(){
        System.out.println("Params' count illegal");
    }
    public static void dishExists(){
        System.out.println("Dish exists");
    }
    public static void dishDoesNotExist(){
        System.out.println("Did input illegal");
    }
    public static void didInputIllegal(){
            System.out.println("Did input illegal");
        }
    public static void inputIllegal(){
        System.out.println("Input illegal");
    }
    public static void chooseMenuCommand(String command,Dish dish,Menu menu,String[] subCommand){
        if(command.equals("np")){
            personCommandNp(subCommand);
        }
        else if(command.equals("gd")){
            menuCommandGd(menu,subCommand,dish);
        }
        else if(command.equals("nd")){
            menuCommandNd(menu,subCommand,dish);
        }
        else if(command.equals("udd")){
            menuCommandUdd(menu,subCommand,dish);
        }
        else if(command.equals("pm")){
            menuCommandPm(menu,subCommand);
        }
    }
    public static void personCommandNp(String[] subCommand){
            int subCommandLen = subCommand.length;
            if(subCommandLen != 4){
                System.out.println("Arguments illegal");
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
        if(!judgeSex)   return;

        boolean judgePhoneNum = person.setPhoneNum(phoneNum);
        if(!judgePhoneNum)  return;

        person.setName(name);
        System.out.print(person.toString());


    }
    public static void menuCommandGd(Menu menu, String[] subCommand, Dish dish){
        int subCommandLen = subCommand.length;
        String parameter = subCommand[1];
        if(subCommandLen!=3){
            paramsCountIllegal();
            return;
        }
        if(parameter.equals("-id")){
            String did = subCommand[2];
            if(!dish.checkDishID(did)){
                System.out.println("Did input illegal");
                return;
            }
            Dish foundDish =  menu.getDishById(did);
            if(foundDish == null){
                System.out.println("Dish does not exist");
            }
            else{
                System.out.println(foundDish.toString());
            }
        }
        else if(parameter.equals("-key")){
            String keyWord = subCommand[2];
            ArrayList<Dish>  foundMenu = menu.getDishByKeyWord(keyWord);
            if(foundMenu.isEmpty()){
                System.out.println("Dish does not exist");
            }
            else{
                for(int i=0;i<foundMenu.size();i++){
                    System.out.println(i+1+". "+foundMenu.get(i).toString());
                }
            }
        }
        else{
            commandNotExists();
        }
    }
    public static void menuCommandUdd(Menu menu, String[] subCommand, Dish dish){
        int subCommandLen = subCommand.length;
        String dishId = subCommand[1];
        String[] uddCommand = {"-n","-t","-p"};
        String uddOption = subCommand[2];
        List<String> uddListCommand = Arrays.asList(uddCommand);

        if(subCommandLen != 4) {
            paramsCountIllegal();
            return;
        }
        if(! uddListCommand.contains(uddOption)){
            commandNotExists();
            return;
        }
        if(! dish.checkDishID(dishId) ){
            didInputIllegal();
            return;
        }
        Dish beFoundDish = menu.getDishById(dishId);
        if(beFoundDish == null){
            dishDoesNotExist();
            return;
        }
        if(uddOption.equals("-n")){
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
        else if(uddOption.equals("-t")){
            String total = subCommand[3];
            if(!beFoundDish.checkSum(total)){
                System.out.println("Change dish's total illegal");
                return;
            }
//            beFoundDish.sum = Integer.parseInt(total);
            beFoundDish.setSum(Integer.parseInt(total));
            System.out.println("Update dish's total success");
        }
        else if(uddOption.equals("-p")){
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
        return;
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


