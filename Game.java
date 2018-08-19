package castle;

import java.util.HashMap;
import java.util.Scanner;


public class Game {
    private Room currentRoom;
    private HashMap<String, Handler> handlers = new HashMap<String, Handler>();
    
    public Game() 
    {
    	handlers.put("go", new HandlerGo());    
    	handlers.put("help", new HandlerHelp());    
    	handlers.put("bye", new HandlerBye());    
    	createRooms();
    }
    
    private class Handler {
    	public void doCmd(String command) {}
    	
    	public boolean isBye() {
    		return false;
    	}
    }
    
    private class HandlerGo extends Handler {

    	@Override
    	public void doCmd(String command) {
    		Room nextRoom = currentRoom.getExit(command);
            if (nextRoom == null) {
                System.out.println("那里没有门！");
            }
            else {
                currentRoom = nextRoom;
                showPrompt();
            }
    	}

    }
    
    private class HandlerHelp extends Handler {

    	@Override
    	public void doCmd(String command) {
    		System.out.print("迷路了吗？你可以做的命令有：go bye help");
            System.out.println("\t如：go east");	
    	}

    }
    
    private class HandlerBye extends Handler {

    	@Override
    	public boolean isBye() {
    		return true;
    	}

    }
    
    private void createRooms()
    {
        Room outside, lobby, pub, study, bedroom, diningroom;
      
        //	制造房间
        outside = new Room("城堡外");
        lobby = new Room("大堂");
        pub = new Room("小酒吧");
        study = new Room("书房");
        bedroom = new Room("卧室");
        diningroom = new Room("饭厅");
         
        //	初始化房间的出口
        outside.setExit("east", lobby);
        outside.setExit("south", study);
        outside.setExit("west", pub);
        lobby.setExit("west", outside);
        pub.setExit("east", outside);
        study.setExit("north", outside);
        study.setExit("east", bedroom);
        bedroom.setExit("west", study);
        lobby.setExit("up", diningroom);
        diningroom.setExit("down", lobby);
        
        currentRoom = outside;  //	从城堡门外开始
    }

    private void printWelcome() {
        System.out.println();
        System.out.println("欢迎来到城堡！");
        System.out.println("这是一个超级无聊的游戏。");
        System.out.println("如果需要帮助，请输入 'help' 。");
        System.out.println();
        showPrompt();
    }
	
    private void showPrompt() {
    	System.out.println("你在" + currentRoom);
        System.out.print("出口有: ");
        System.out.println(currentRoom.getExitDesc());
    }
    
    private void play() {
    	Scanner in = new Scanner(System.in);
    	while ( true ) {
    		String line = in.nextLine();
    		String[] words = line.split(" ");	
    		String command = words[0];
    		Handler handler = handlers.get(command);
    		if (words.length>1) {
    			command = words[1];
    		}
    		if (handler != null ) {
    			handler.doCmd(command);
    			if (handler.isBye()) 
    				break;
    		}
    	}
    	in.close();
    }
    
	public static void main(String[] args) {
		Game game = new Game();
		game.printWelcome();
		game.play();
		System.out.println("感谢您的光临。再见！");
	}

}
