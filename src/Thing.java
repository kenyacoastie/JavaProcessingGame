import processing.core.PApplet;

import static java.lang.Math.cos;
import static java.lang.StrictMath.sin;
import static processing.core.PConstants.CLOSE;
import static processing.core.PConstants.TWO_PI;

public class Thing {
    PApplet p;

    public Thing(PApplet p){
        this.p = p;
    }

    float x = 100;
    float y = 100;
    float xspeed = 1;
    float yspeed = 3;

    public void bounceBall(){
//        p.background(255);

        x = x + xspeed;
        y = y + yspeed;
        if ((x > 500) || (x < 0)) {
            xspeed = xspeed * -2;
        }
        if ((y > 500) || (y < 0)) {
            yspeed = yspeed * -1;
        }
        p.stroke(0);
        p.fill(175);
        p.ellipse(x,y,16,16);

    }

    public void star(float x, float y, float radius1, float radius2, int npoints) {

        float angle = TWO_PI / npoints;
        float halfAngle = angle/2;
        p.beginShape();
        for (float a = 0; a < TWO_PI; a += angle) {
            float sx = (float) (x + cos(a) * radius2);
            float sy = (float) (y + sin(a) * radius2);
            p.vertex(sx,sy);
            sx = (float) (x + cos(a+halfAngle) * radius1);
            sy = (float) (y + sin(a+halfAngle) * radius1);
            p.vertex(sx, sy);
        }

        p.endShape(CLOSE);
    }
}