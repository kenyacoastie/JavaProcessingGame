import processing.core.PApplet;

import static processing.core.PConstants.P3D;

public class Menu{

    PApplet p;
    public Menu(PApplet p){
        this.p = p;
    }

    boolean gameStarted = false;

    int rectX, rectY, rect2X, rect3X, rect3Y;     // Position of square button
    int rectSize = 90;     // Diameter of rect
    int rectColor, rect2Color, rect3Color, baseColor;
    int rectHighlight, rect2Highlight, rect3Highlight;
    int currentColor;
    boolean rectOver = false;
    boolean rect2Over = false;
    boolean rect3Over = false;

    boolean rectSelected = false;
    boolean rect2Selected = false;

    public void draw() {
//      Shapes setup for menu blocks
        p.size(500, 500);
        rectColor = p.color(0);
        rectHighlight = p.color(51);
        baseColor = p.color(102);
        currentColor = baseColor;
        rectX = p.width/2-rectSize-10;
        rectY = p.height/2-rectSize/2;
        rect2X = p.width/2-rectSize+100;
        rect3X = p.width/2-rectSize+45;
        rect3Y = p.width/2-rectSize+160;
        p.ellipseMode(p.CENTER);
        update(p.mouseX, p.mouseY);

//        rectangle 1. Play Button
        if (rectOver) {
            p.fill(rectHighlight);
        } else {
            p.fill(rectColor);
        }
        p.stroke(255);
        p.rect(rectX, rectY, rectSize, rectSize);

        p.textSize(23);
        p.fill(0, 102, 153, 204);
        if(gameStarted){
            p.textSize(22);
            p.text("Resume", rectX+3, rectY+40, -30);  // Specify a z-axis value
        } else {
            p.text("Play", rectX+22, rectY+40, -30);  // Specify a z-axis value
        }
        p.textSize(18);
        p.text("Game", rectX+20, rectY+60);  // Default depth, no z-value specified


//        rectangle 2. Scores Button
        if (rect2Over) {
            p.fill(rectHighlight);
        } else {
            p.fill(rectColor);
        }
        p.stroke(255);
        p.rect(rect2X, rectY, rectSize, rectSize);
        p.textSize(23);
        p.fill(0, 102, 153, 204);
        p.text("Score", rect2X+17, rectY+40, -30);  // Specify a z-axis value
        p.textSize(18);
        p.text("History", rect2X+15, rectY+60);  // Default depth, no z-value specified

//        rectangle 3. Reset Button. Only visible if games have been started.
        if(gameStarted){
            if (rect3Over) {
                p.fill(rectHighlight);
            } else {
                p.fill(rectColor);
            }
//        if (stage == 2)
            p.stroke(255);
            p.rect(rect3X, rect3Y, rectSize, rectSize);
            p.textSize(23);
            p.fill(0, 102, 153, 204);
            p.text("Reset", rect3X+18, rect3Y+40, -30);  // Specify a z-axis value
            p.textSize(18);
            p.text("Game", rect3X+22, rect3Y+60);  // Default depth, no z-value specified
        }

    }

    // Update if a square is hovered by mouse xy
    public void update(int x, int y) {
        if ( overRect(rect2X, rectY, rectSize, rectSize) ) {
            rectOver = false;
            rect2Over = true;
            rect3Over = false;
        } else if ( overRect(rectX, rectY, rectSize, rectSize) ) {
            rectOver = true;
            rect2Over = false;
            rect3Over = false;
        } else if ( overRect(rect3X, rect3Y, rectSize, rectSize) ) {
            rectOver = false;
            rect2Over = false;
            rect3Over = true;
    }  else {
            rect3Over = rect2Over = rectOver = false;
        }
    }

    // Is rectangle 1 being hovered by the mouse xy coords?
    public boolean overRect(int x, int y, int width, int height)  {
        if (p.mouseX >= x && p.mouseX <= x+width &&
                p.mouseY >= y && p.mouseY <= y+height) {
            return true;
        } else {
            return false;
        }
    }

}
