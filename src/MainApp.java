import processing.core.PApplet;
import processing.core.PImage;

public class MainApp extends PApplet{

    public static void main(String[] args){
        PApplet.main("MainApp", args);
    }

    public void settings(){
        size(500, 500);
    }

    //Subclasses definitions
    Button Button;
    HistoryStage HistoryStage;
    Game Game;

    //Stage mode variables
    private int stage = 0;
    private final int MAIN_MENU = 0;
    private final int HISTORY_MENU = 1;
    private final int PLAY_GAME = 2;
    private final int PAUSE = 3;

    public void setup() {
        frameRate(60);
        stroke(255);
        fill(255);
        PImage img;
        img = loadImage("images/stars.png");
        background(img);

    //Subclass instances
        Button = new Button(this);
        HistoryStage = new HistoryStage(this);
        Game = new Game(this);
    }

    public void draw()
    {
    //Stage Mode controller
        switch(stage) {
            case MAIN_MENU:
                Button.draw();
                break;
            case HISTORY_MENU:
                HistoryStage.draw();
                break;
            case PLAY_GAME:
                Game.draw();
                break;
//            case PAUSE:
//                //Pause Stuff
//                break;
        }
    }

    // When left mouse button is pressed, create a new shot
    public void mousePressed() {
        if (stage == PLAY_GAME){
            Game.gameMousePressed();
        }

//        Main Menu. stage set based on what is being hovered over. refactor to button.press()
        if (Button.rect2Over== true){
            System.out.println("history button");
            stage = HISTORY_MENU;
        } else if (Button.rectOver== true){
            stage = PLAY_GAME;
        }
    }

}


