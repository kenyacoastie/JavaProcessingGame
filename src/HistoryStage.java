import processing.core.PApplet;

import java.util.ArrayList;

public class HistoryStage extends MainApp{

    PApplet p;
    public HistoryStage(PApplet p){
        this.p = p;
    }

//  MainApp syncs scoreHistory from Game.java
    public ArrayList<Integer> scoreHistory = new ArrayList<>();
    public ArrayList<Integer> highScores = new ArrayList<>();

     public void draw() {
        p.textAlign(p.CENTER);
        p.text("Recent Scores: "+scoreHistory, p.width/2, p.height/2);
        p.text("High Scores: "+highScores, p.width/2, 2*p.height/3);
    }
}