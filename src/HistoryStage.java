import processing.core.PApplet;
import java.util.ArrayList;

public class HistoryStage extends MainApp{
    PApplet p;
    public HistoryStage(PApplet p){
        this.p = p;
    }


    public ArrayList<Integer> scoreHistory = new ArrayList<>();
    public ArrayList<Integer> highScores = new ArrayList<>();

    public void setup(){
        System.out.println("in the setup for history");
    }


     public void draw() {

        p.background(255);
        p.textAlign(p.CENTER);
        p.text("Recent Scores: "+scoreHistory, p.width/2, p.height/2);
        p.text("High Scores", p.width/2, 2*p.height/3);
    }

    // if mouse pressed, change color
//    public void mousePressed() {
//        if (rect2Over) {
//            currentColor = rect2Color;
//        }
//        if (rectOver) {
//            currentColor = rectColor;
//        }
//    }

    // Is rectangle 1 being hovered by the mouse xy coords?
//    public boolean overRect(int x, int y, int width, int height)  {
//        if (p.mouseX >= x && p.mouseX <= x+width &&
//                p.mouseY >= y && p.mouseY <= y+height) {
//            return true;
//        } else {
//            return false;
//        }
//    }
}
