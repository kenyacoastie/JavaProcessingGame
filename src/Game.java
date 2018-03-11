import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PShape;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static processing.core.PConstants.TWO_PI;

public class Game {
    PApplet p;

    public Game(PApplet p){
        this.p = p;
    }

    //  GLOBAL SETTINGS
    private int asteroid_rate = 2 * 60;
    private int asteroid_count = 0;
    private float ast_size = 10;
    private int ast_id = 1;
    public int score = 0;
    float hitRate = 0;
    public int numShots = 0;
    private int ships = 3;
    private int pause = 0;
    public int stage = 1;

//    private int[] scoreHistory = new int[0];
    int[] recentScores = {0, 0, 0};

    ArrayList<Integer> scoreHistory = new ArrayList<>();
    ArrayList<Integer> highScores = new ArrayList<>();

    ArrayList<Game.shot> shots = new ArrayList<Game.shot>();
    ArrayList<Game.asteroid> asteroids = new ArrayList<Game.asteroid>();

    public void draw() {
        p.background(0);

        float angle = p.atan2(p.mouseY - 250, p.mouseX - 250);

        int i;
        if (pause==0) {

            // Add New Asteroids. Random locations.
            if (asteroid_count--==0) {
                asteroids.add(new asteroid(p.random(0, TWO_PI), p.random(1, 2), p.random(1, 4), p.random(-1, 1),
                        p.random(-150, 150), p.random(-150, 150), ast_id++));
                // Rate Acceleration
                asteroid_count = asteroid_rate--;
            }

            // Clear screen
            p.background(0);
            PImage img;
            img = p.loadImage("images/stars.png");
            p.background(img);

            // Go through all asteroids (if any) and update their position
            for (i = 0; i<asteroids.size(); i++) {
                asteroid a = asteroids.get(i);
                if (a.update()) {
                    // Remove bullet, if outside screen
                    asteroids.remove(i);
                }
                // Detect collisions with asteroids by approximating ship with 4 circles
                p.fill(160, 33, 100);
                p.ellipse(250, 250, 11, 11);
                p.ellipse(13*p.cos(angle-p.PI)+250, 13*p.sin(angle-p.PI)+250, 17, 17);
                p.ellipse(10*p.cos(angle)+250, 10*p.sin(angle)+250, 7, 7);
                p.ellipse(18*p.cos(angle)+250, 18*p.sin(angle)+250, 2, 2);
                if (a.coll(250, 250, 6, -1) ||
                        a.coll(13*p.cos(angle-p.PI)+250, 13*p.sin(angle-p.PI)+250, 9, -1) ||
                        a.coll(10*p.cos(angle)+250, 10*p.sin(angle)+250, 4, -1) ||
                        a.coll(18*p.cos(angle)+250, 18*p.sin(angle)+250, 1, -1)) {
                    //set up for the next round
                    pushScoreHistory(score);
                    pushHighScores(score);
                    score = 0;
                    ships--;
                    pause=3*60;
                }
            }

            // "pushMatrix" saves current viewpoint
            p.pushMatrix();
            // Set 250,250 as the new 0,0
            p.translate(250, 250);
            // Rotate screen "angle"
            p.rotate(angle);
            p.fill(255);
            // Draw a triangle (the ship)
            p.triangle(20, 0, -20, -10, -20, 10);
            // Bring back normal perspective
            p.popMatrix();
        } else {
            // Pause is larger than 0
            // Clear screen, black
            p.background(0, 10);

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
                // GAME OVER , Clear screen, black
                p.textAlign(p.CENTER);
                p.text("Game Over", p.width/2, p.height/2);
                p.text("Press any key to restart", p.width/2, 2*p.height/3);
//        text("Hit rate: " + int(100*score/float(numShots)) + "%", 15, 45);
                if (p.keyPressed == true || p.mousePressed == true) {
                    score = 0;
                    numShots = 0;
                    ships = 3;
                    asteroid_rate = 3 * 60;
                    asteroid_count = 0;
                    ast_id = 1;
                    asteroids = new ArrayList<Game.asteroid>();
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
        p.textAlign(p.LEFT);
        p.text("Score   : " + score, 15, 15);
        p.text("Ships   : " + ships, 15, 30);

    }

    public void resetGame(){
        score = 0;
        numShots = 0;
        ships = 3;
        asteroid_rate = 3 * 60;
        asteroid_count = 0;
        ast_id = 1;
        asteroids = new ArrayList<Game.asteroid>();
    }

    // Add the round score, remove index 0 if list at 10 scores.
    public void pushScoreHistory(int roundScore){
        if(scoreHistory.size() >= 10) {
            scoreHistory.remove(0);
        }
        scoreHistory.add(roundScore);
    }

    // Check each round's score if in top 10. push and remove anything past 10
    public void pushHighScores(int roundScore){
        highScores.add(roundScore);
        Collections.sort(highScores, Collections.reverseOrder());
        if(highScores.size() > 10) {
            highScores.remove(10);
        }
    }

    public void saveScores(ArrayList<Integer> scoreHistory){
//        create a function that overwrites column with scoreHistory[] ints.
//        saveTable(table, "data/scores.csv");

    }

    // When left mouse button is pressed, create a new shot
    public void gameMousePressed() {
//        Shoot on click
        if (pause==0) {
            // Only add shots when in action
            if (p.mouseButton == p.LEFT) {
                float angle = p.atan2(p.mouseY - 250, p.mouseX - 250);
                shots.add(new shot(angle, 4));
                numShots++;
            }
            if (p.mouseButton == p.RIGHT) {
//                asteroids.add(new asteroid(p.random(0, TWO_PI), p.random(1, 2), p.random(1, 4), p.random(-1, 1),
//                        p.random(-80, 80), p.random(-80, 80), ast_id++));
                System.out.println("right clicking during game");
            }
        }
    }

    // Projectiles
    public class shot {
        // A shot has x,y, and speed in x,y. All float for smooth movement
        float angle, speed;
        float x, y, x_speed, y_speed;

        // Constructor
        shot(float _angle, float _speed) {
            angle = _angle;
            speed = _speed;
            x_speed = speed*p.cos(angle);
            y_speed = speed*p.sin(angle);
            x = p.width/2+20*p.cos(angle);
            y = p.height/2+20*p.sin(angle);
        }

        // Update position, return true when out of screen
        boolean update() {
            int i;
            x = x + x_speed;
            y = y + y_speed;

            // Draw bullet
            p.ellipse (x, y, 3, 3);

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
            if (x<0 || x>p.width || y<0 || y>p.height) {
                return true;
            } else {
                return false;
            }
        }
    }

    // Class. Asteroid constructor, behavior, and collision + position check.
    public class asteroid {
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
                x = 250+500*p.cos(angle)+xoff;
                y = 250+500*p.sin(angle)+yoff;
            } else {
                x = _xoff-2000;
                y = _yoff-2000;
            }
            rotation = 0;
            // Generate the shape of the asteroid - Some variations for all
            s = p.createShape();
            s.beginShape();
            s.fill(255, 255, 100);
            s.noStroke();
            for (i=0; i<TWO_PI; i=i+p.PI/(p.random(4, 11))) {
                s.vertex(p.random(ast_size*1, ast_size*1)*p.cos(i), p.random(ast_size*1, ast_size*1)*p.sin(i));
            }
            s.endShape(p.CLOSE);
        }

        // Increases the speed. (used in game clearing)
        void incSpeed() {
            speed = speed * 2;
        }

        // Update position, return boolean if out of screen
        boolean update() {
            int i;
            x = x - p.cos(angle)*speed;
            y = y - p.sin(angle)*speed;
            rotation = rotation + rotSpeed;

            // Check for asteroid vs asteroid collision
            for (i = 0; i<asteroids.size(); i++) {
                asteroid a = asteroids.get(i);
                if ((a != this) && (a.coll(x, y, ast_size*size, id))) {
                    if (size > 1) {
                        asteroids.add(new asteroid(angle-p.random(p.PI/5, p.PI/7), speed+p.random(0, speed/2), size/2, rotSpeed, 2000+x, 2000+y, id));
                        asteroids.add(new asteroid(angle+p.random(p.PI/5, p.PI/7), speed+p.random(0, speed/2), size/2, rotSpeed, 2000+x, 2000+y, id));
                        ast_id++;
                    }
                    asteroids.remove(i);
                }
            }

            p.pushMatrix();
            // Set position as the new 0,0
            p.translate(x, y);
            // Rotate screen "angle"
            p.rotate(rotation);
            // Draw asteroid
            p.scale(size);
            p.shape(s, 0, 0);
            // Bring back normal perspektive
            p.popMatrix();

            if (x<-300 || x>800 || y<-300 || y>800) {
                return true;
            } else {
                return false;
            }
        }

        // Collision boolean
        boolean coll(float _x, float _y, float _size, int _id) {
            float dist;

            dist = p.sqrt ((x-_x)*(x-_x) + (y-_y)*(y-_y));

            // Check if distance is shorter than asteroid size and other objects size
            if ((dist<(_size+ast_size*size)) && (id!=_id)) {
                // Collision,
                if (_id>0) id = _id;
                if (size > 1) {
                    // If the asteroid was "large" generate two new fragments
                    asteroids.add(new asteroid(angle-p.random(p.PI/5, p.PI/7), speed+p.random(0, speed/2), size/2, rotSpeed, 2000+x, 2000+y, id));
                    asteroids.add(new asteroid(angle+p.random(p.PI/5, p.PI/7), speed+p.random(0, speed/2), size/2, rotSpeed, 2000+x, 2000+y, id));
                }
                return true;
            } else {
                return false;
            }
        }
    }

    //Get & Set methods
    public ArrayList<Integer> getScoreHistory(){
        return scoreHistory;
    }


}