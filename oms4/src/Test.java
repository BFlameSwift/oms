import dish.*;
import mode.*;
/**@author ly*/
public class Test {
//    public static Scanner in = new Scanner(System.in);
    public static void main(String[] args) {
        Menu menu = new Menu();
        while (true){
            String command = Main.in.nextLine();
            String[] subCommand = command.split("\\s+");
            String headCommand = subCommand[0];

            if (Main.listMenuCommand.contains(headCommand))  {
                //listMenuCommand = {"gd", "nd" ,"udd ","pm","np"};
                if (! menu.allDish.isEmpty()) {
                    menu.update();
                }

                Main.chooseMenuCommand(headCommand,menu,subCommand);
            }
            else if(Main.listMainCommand.contains(headCommand)){
                //listMainCommand = {"QUIT","SUDO","login"};
                Main.chooseMainCommand(subCommand);
            }
            else{
                Print.commandNotExist();
            }
        }
    }
}