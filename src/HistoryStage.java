import processing.core.PApplet;
import processing.data.Table;

import java.util.ArrayList;

public class HistoryStage{

    PApplet p;
    public HistoryStage(PApplet p){
        this.p = p;
    }

//    Table table;

//  MainApp syncs scoreHistory from Game.java
    public ArrayList<Integer> scoreHistory = new ArrayList<>();
    public ArrayList<Integer> highScores = new ArrayList<>();

     public void draw() {
//         font settings
         p.fill( 255);
         p.textSize(20);
         p.textAlign(p.CENTER);
//        iterate the scores, position them appropriately
         p.text("Recent Scores: ", p.width/3-(30), p.height/3-(20));
         for (int i = 0; i < scoreHistory.size(); i++ ) {
             p.text(scoreHistory.get(i), p.width/3-(30), p.height/3+(i*25));
         }
         p.text("High Scores: ", p.width/3*(2), p.height/3-(20));
         for (int i = 0; i < highScores.size(); i++ ) {
             p.text(highScores.get(i), p.width/3*(2), p.height/3+(i*25));
         }
//        p.text("High Scores: "+highScores, p.width/2, 2*p.height/3);
    }
}