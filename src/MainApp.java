import processing.core.PApplet;
import processing.core.PImage;
import processing.data.Table;
import processing.data.TableRow;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class MainApp extends PApplet{

    public static void main(String[] args){
        PApplet.main("MainApp", args);
    }

    public void settings(){
        size(500, 500);
    }

    //Subclasses definitions
    Menu Menu;
    HistoryStage HistoryStage;
    Game Game;
    //Stage mode variables
    public int stage = 0;
    private final int MAIN_MENU = 0;
    private final int HISTORY_MENU = 1;
    private final int PLAY_GAME = 2;
    //define images for preloading to avoid reloading on each class
    PImage gameImg;
    PImage menuImg;
    //Table for loading saved scores
    Table table;
    public ArrayList<Integer> scoreHistory = new ArrayList<>();
    public ArrayList<Integer> highScores = new ArrayList<>();

    public void setup() {
        frameRate(60);
        stroke(255);
        fill(255);
    //Preload bg images to avoid reloading on each class
        gameImg = loadImage("images/stars.png");
        menuImg = loadImage("images/purpleSpace.JPG");
        background(menuImg);
    //Subclass instances
        Menu = new Menu(this);
        HistoryStage = new HistoryStage(this);
        Game = new Game(this);
    //Load saved scores from table
        loadTable();
    }

    public void draw()
    {
    //Stage Mode controller
        switch(stage) {
            case MAIN_MENU:
                Menu.draw();
                break;
            case HISTORY_MENU:
                background(menuImg);
                HistoryStage.highScores = Game.highScores;
                HistoryStage.scoreHistory = Game.scoreHistory;
                HistoryStage.draw();
                break;
            case PLAY_GAME:
                Menu.gameStarted = true;
                background(gameImg);
                Game.draw();
//                if (Game.updateTrigger==true){
//                    updateTable();
//                }
                break;
        }
    }

    //Global mouse event, check booleans from Menu for stage direction
    public void mousePressed() {
        if (mouseButton == LEFT) {
            Game.gameMousePressed();
            if (Menu.rect2Over==true){
                stage = HISTORY_MENU;
            } else if (Menu.rectOver==true){
                stage = PLAY_GAME;
            } else if (Menu.rect3Over==true){
                Game.resetGame();
                Menu.rect3Over=false;
                stage = PLAY_GAME;
            }
        } else if (mouseButton == RIGHT) {
            stage = MAIN_MENU;
            updateTable();
        }
    }

    //Get & Set Methods
    public int getStage(){
        return stage;
    }

    public void setStage(int stage){
        this.stage = stage;
    }

    public void loadTable(){
        //Load saved scores from table
        table = loadTable("data/scores.csv", "header");
        //loop through rows
        for (TableRow row : table.rows()) {
        //get value at each column's rows
            int savedScoreHistory = row.getInt("ScoreHistory");
            int savedHighScores = row.getInt("HighScores");
            //build the scores arrays via adding found values
            scoreHistory.add(savedScoreHistory);
            highScores.add(savedHighScores);
//            sync with game
            Game.scoreHistory = scoreHistory;
            Game.highScores = highScores;
        }
    }

    public void updateTable(){
//        loop through table rows, match values via row.id=array[i]
        for(TableRow row : table.rows()){
            int id = row.getInt("ID");
            int rowRecentScore = Game.scoreHistory.get(id);
            int rowHighScore = Game.highScores.get(id);
            table.setInt(id, "ScoreHistory", rowRecentScore);
            table.setInt(id, "HighScores", rowHighScore);
        }
        saveTable();
    }

    public void saveTable(){
        saveTable(table, "data/scores.csv");
    }
}