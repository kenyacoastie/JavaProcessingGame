import processing.core.PApplet;
import processing.core.PImage;


public class Button{

    PApplet p;

    public Button(PApplet p){
        this.p = p;
    }

    int rectX, rectY, rect2X;      // Position of square button
    int rectSize = 90;     // Diameter of rect
    int rectColor, rect2Color, baseColor;
    int rectHighlight, rect2Highlight;
    int currentColor;
    boolean rectOver = false;
    boolean rect2Over = false;

    boolean rectSelected = false;
    boolean rect2Selected = false;

    public void draw() {
        p.size(500, 500);
        rectColor = p.color(0);
        rect2Color = p.color(0);
        rectHighlight = p.color(51);
        rect2Highlight = p.color(51);

        baseColor = p.color(102);
        currentColor = baseColor;

        rectX = p.width/2-rectSize-10;
        rectY = p.height/2-rectSize/2;

        rect2X = p.width/2-rectSize+100;

        p.ellipseMode(p.CENTER);

        update(p.mouseX, p.mouseY);

        if (rectOver) {
            p.fill(rectHighlight);
        } else {
            p.fill(rectColor);
        }
        p.stroke(255);
        p.rect(rectX, rectY, rectSize, rectSize);

        if (rect2Over) {
            p.fill(rect2Highlight);
        } else {
            p.fill(rect2Color);
        }
        p.stroke(255);
        p.rect(rect2X, rectY, rectSize, rectSize);

    }

    // Update if a square is hovered by mouse xy
    public void update(int x, int y) {
        if ( overRect2(rect2X, rectY, rectSize, rectSize) ) {
            rect2Over = true;
            rectOver = false;
        } else if ( overRect(rectX, rectY, rectSize, rectSize) ) {
            rectOver = true;
            rect2Over = false;
        } else {
            rect2Over = rectOver = false;
        }
    }

    // if mouse pressed, change color
    public void mousePressed() {
        if (rect2Over) {
            currentColor = rect2Color;
        }
        if (rectOver) {
            currentColor = rectColor;
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

    // Is rectangle 2 being hovered by the mouse xy coords?
    public boolean overRect2(int x, int y, int width, int height) {
        if (p.mouseX >= x && p.mouseX <= x+width &&
                p.mouseY >= y && p.mouseY <= y+height) {
            return true;
        } else {
            return false;
        }
    }
}
