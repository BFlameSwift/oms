import mode.*;
public class Test {
    public static void main(String[] args) {
        while (true){
            String[] subCommand = Main.in.nextLine().split("\\s+");
            if(Main.listMainCommand.contains(subCommand[0]))
                Main.chooseMainCommand(subCommand);
                //   listMainCommand= {"QUIT","SUDO","login"};
            else  Print.commandNotExist();
        }
    }
}