import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PShape;


import java.util.ArrayList;

public class MainApp extends PApplet{

    public static void main(String[] args){
        PApplet.main("MainApp", args);
    }

    public void settings(){
        size(500, 500);
    }

//    Thing ball;
//    Thing star;


//  GLOBAL SETTINGS
    private int asteroid_rate = 2 * 60;
    private int asteroid_count = 0;
    private float ast_size = 10;
    private int ast_id = 1;
    private int score = 0;
    float hitRate = 0;
    public int numShots = 0;
    private int ships = 3;
    private int pause = 0;


    //  Create arrays of shot & asteroid objects
    ArrayList<shot> shots = new ArrayList<shot>();
    ArrayList<asteroid> asteroids = new ArrayList<asteroid>();

    public void setup() {
        frameRate(60);
        stroke(255);
        fill(255);


        // this loads mysong.wav from the data folder
//        song = minim.loadFile("audio/bgMusic.mp3");

        // Load a soundfile from the /data folder of the sketch and play it back

//        ball = new Thing(this);
//        star = new Thing(this);
//        SimpleAudioPlayer = new T

    }

    public void draw()
    {

        // Track the mouse
        float angle = atan2(mouseY - 250, mouseX - 250);

        int i;
        if (pause==0) {

            // Add New Asteroids. Random locations.
            if (asteroid_count--==0) {
                asteroids.add(new asteroid(random(0, TWO_PI), random(1, 2), random(1, 4), random(-1, 1),
                        random(-150, 150), random(-150, 150), ast_id++));
                // Rate Acceleration
                asteroid_count = asteroid_rate--;
            }

            // Clear screen, black
            background(0);
            PImage img;
            img = loadImage("images/stars.png");
            background(img);

            // Go through all asteroids (if any) and update their position
            for (i = 0; i<asteroids.size(); i++) {
                asteroid a = asteroids.get(i);
                if (a.update()) {
                    // Remove bullet, if outside screen
                    asteroids.remove(i);
                }
                // Detect collisions with asteroids by approximating ship with 4 circles
                 fill(160, 33, 100);
                 ellipse(250, 250, 11, 11);
                 ellipse(13*cos(angle-PI)+250, 13*sin(angle-PI)+250, 17, 17);
                 ellipse(10*cos(angle)+250, 10*sin(angle)+250, 7, 7);
                 ellipse(18*cos(angle)+250, 18*sin(angle)+250, 2, 2);
                if (a.coll(250, 250, 6, -1) ||
                        a.coll(13*cos(angle-PI)+250, 13*sin(angle-PI)+250, 9, -1) ||
                        a.coll(10*cos(angle)+250, 10*sin(angle)+250, 4, -1) ||
                        a.coll(18*cos(angle)+250, 18*sin(angle)+250, 1, -1)) {
                    ships--;
                    pause=3*60;
                }
            }

            // "pushMatrix" saves current viewpoint
            pushMatrix();
            // Set 250,250 as the new 0,0
            translate(250, 250);
            // Rotate screen "angle"
            rotate(angle);
            fill(255);
            // Draw a triangle (the ship)
            triangle(20, 0, -20, -10, -20, 10);
            // Bring back normal perspective
            popMatrix();
        } else {
            // Pause is larger than 0
            // Clear screen, black
            background(0, 10);

            // Go through all asteroids (if any) and update their position
            for (i = 0; i<asteroids.size(); i++) {
                asteroid a = asteroids.get(i);
                a.incSpeed();
                if (a.update()) {
                    // Remove bullet, if outside screen
                    asteroids.remove(i);
                }
            }
            if (ships == 0) {
                // Clear screen, black
                textAlign(CENTER);
                text("Game Over", width/2, height/2);
                text("Press any key to restart", width/2, 2*height/3);
                // 1 new asteroid every 0.5 seconds (60 fps * 0.5 sec)
                // To make something happen while waiting
                if (asteroid_count--==0) {
                    asteroids.add(new asteroid(random(0, TWO_PI), random(1, 2), random(1, 4), random(-1, 1),
                            random(-150, 150), random(-150, 150), ast_id++));
                    // Increase rate just a little
                    asteroid_count = 30;
                }
                if (keyPressed == true) {
                    score = 0;
                    numShots = 0;
                    ships = 1;
                    asteroid_rate = 3 * 60;
                    asteroid_count = 0;
                    ast_id = 1;
                    asteroids = new ArrayList<asteroid>();
                }
            } else {
                // Wait until asteroids are gone
                if (asteroids.size()==0) {
                    pause=0;
                }
            }
        }
        // Go through all shots (if any) and update their position
        for (i = 0; i<shots.size(); i++) {
            shot s = shots.get(i);
            if (s.update()) {
                // Remove bullet, if outside screen or if hits asteroid
                shots.remove(i);
            }
        }
        textAlign(LEFT);
        text("Score   : " + score, 15, 15);
        text("Ships   : " + ships, 15, 30);
//        text("Hit rate: " + int(100*score/float(numShots)) + "%", 15, 45);

//        ball.bounceBall();
//        star.star(100, 100, 15, 35, 3);

    }

    // When left mouse button is pressed, create a new shot
    public void mousePressed() {
        if (pause==0) {
            // Only add shots when in action
            if (mouseButton == LEFT) {
                float angle = atan2(mouseY - 250, mouseX - 250);
                shots.add(new shot(angle, 4));
                numShots++;
            }
            if (mouseButton == RIGHT) {
                asteroids.add(new asteroid(random(0, TWO_PI), random(1, 2), random(1, 4), random(-1, 1),
                        random(-80, 80), random(-80, 80), ast_id++));
            }
        }
    }

    // Class definition for the shot
    class shot {
        // A shot has x,y, and speed in x,y. All float for smooth movement
        float angle, speed;
        float x, y, x_speed, y_speed;

        // Constructor
        shot(float _angle, float _speed) {
            angle = _angle;
            speed = _speed;
            x_speed = speed*cos(angle);
            y_speed = speed*sin(angle);
            x = width/2+20*cos(angle);
            y = height/2+20*sin(angle);
        }

        // Update position, return true when out of screen
        boolean update() {
            int i;
            x = x + x_speed;
            y = y + y_speed;

            // Draw bullet
            ellipse (x, y, 3, 3);

            // Check for collisions
            // Go through all asteroids (if any)
            for (i = 0; i<asteroids.size(); i++) {
                asteroid a = asteroids.get(i);
                if (a.coll(x, y, 3, -1)) {
                    score++;
                    ast_id++;
                    asteroids.remove(i);
                    //Remove bullet
                    return true;
                }
            }
            // End, check if outside screen
            if (x<0 || x>width || y<0 || y>height) {
                return true;
            } else {
                return false;
            }
        }
    }



    // Class. Asteroid constructor.
    class asteroid {
        // An asteroid angle, speed, size, rotation
        float angle, speed, size, rotSpeed;
        float position;
        float rotation;
        float xoff, yoff;
        float x, y;
        PShape s;  // The PShape object - Keeps the asteroid shape
        float i;
        int id;


        // Constructor
        asteroid(float _angle, float _speed, float _size, float _rotSpeed, float _xoff, float _yoff, int _id) {
            angle = _angle;
            speed = _speed;
            size = _size;
            rotSpeed = _rotSpeed;
            xoff = _xoff;
            yoff = _yoff;
            id = _id;
            if (xoff<1000) {
                x = 250+500*cos(angle)+xoff;
                y = 250+500*sin(angle)+yoff;
            } else {
                x = _xoff-2000;
                y = _yoff-2000;
            }
            rotation = 0;
            // Generate the shape of the asteroid - Some variations for all
            s = createShape();
            s.beginShape();
            s.fill(255, 255, 100);
            s.noStroke();
            for (i=0; i<TWO_PI; i=i+PI/(random(4, 11))) {
                s.vertex(random(ast_size*1, ast_size*1)*cos(i), random(ast_size*1, ast_size*1)*sin(i));
            }
            s.endShape(CLOSE);
        }

        // Increases the speed. (used in game clearing)
        void incSpeed() {
            speed = speed * 2;
        }

        // Update position, return boolean if out of screen
        boolean update() {
            int i;
            x = x - cos(angle)*speed;
            y = y - sin(angle)*speed;
            rotation = rotation + rotSpeed;

            // Check for asteroid vs asteroid collision
            for (i = 0; i<asteroids.size(); i++) {
                asteroid a = asteroids.get(i);
                if ((a != this) && (a.coll(x, y, ast_size*size, id))) {
                    if (size > 1) {
                        asteroids.add(new asteroid(angle-random(PI/5, PI/7), speed+random(0, speed/2), size/2, rotSpeed, 2000+x, 2000+y, id));
                        asteroids.add(new asteroid(angle+random(PI/5, PI/7), speed+random(0, speed/2), size/2, rotSpeed, 2000+x, 2000+y, id));
                        ast_id++;
                    }
                    asteroids.remove(i);
                }
            }

            pushMatrix();
            // Set position as the new 0,0
            translate(x, y);
            // Rotate screen "angle"
            rotate(rotation);
            // Draw asteroid
            scale(size);
            shape(s, 0, 0);
            // Bring back normal perspektive
            popMatrix();

            if (x<-300 || x>800 || y<-300 || y>800) {
                return true;
            } else {
                return false;
            }
        }

        //
        boolean coll(float _x, float _y, float _size, int _id) {
            float dist;

            dist = sqrt ((x-_x)*(x-_x) + (y-_y)*(y-_y));

            // Check if distance is shorter than asteroid size and other objects size
            if ((dist<(_size+ast_size*size)) && (id!=_id)) {
                // Collision,
                if (_id>0) id = _id;
                if (size > 1) {
                    // If the asteroid was "large" generate two new fragments
                    asteroids.add(new asteroid(angle-random(PI/5, PI/7), speed+random(0, speed/2), size/2, rotSpeed, 2000+x, 2000+y, id));
                    asteroids.add(new asteroid(angle+random(PI/5, PI/7), speed+random(0, speed/2), size/2, rotSpeed, 2000+x, 2000+y, id));
                }
                return true;
            } else {
                return false;
            }
        }
    }


}


