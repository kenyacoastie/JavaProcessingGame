# JavaProcessingGame

#### Overview

I just learned how to code in Java 4 days ago. SO LET'S MAKE A GAME!

---

#### Technologies

    -Java, Processing

#### Features

    Main Menu: enter "Play" or "Score History"
    Score History: Score History displays an array of 10 most recent scores and a Top Ten scores list as well
    Save Game: High Scores and Top Ten Scores persists across sessions via csv file save
    Pause menu: pause or view score history mid game, then resume game

#### Installation/Demo
    - Installation instructions
    
#### Development Process
    Focused on backend primarily, marking off each feature's functionality, 
    simply being sure I can pass in valuse to the draw functions when needed.
    A running game was the first big piece; collisions, draw refresh, off screen destruction of items. 
    Implementing the basics like tracking scores, losses, game over were marked off via having tests set up. 
    Once the data was flowing in properly, I then focused on how to display game data as needed. 
    Then finally revisiting UI blocks as spaceholders to make them more useful to users.
    
    Biggest win: was setting up the savegame between sessions functionality.
    It's quite simple once it's actually done.
    
    Biggest challenge: was getting the different classes to synchronize and communicate with the Processing framework.
    I ended up making MainApp as the router and the source of truth for values and stagemode.
     
    Still to do: fix UI bugs during pause or on player death. 
     
    - A five-slide presentation, to be given in front of classmates, outlining the following points:
    - Game: Space shooter. 
    - The rules of the game: Shoot oncoming asteroids without getting hit to beat high scores.
    - Your process for turning that game into a web application (wireframes, blockers/issues that popped up).
    - Screenshots of actual game play.
    - Screenshots of the snippets of code you used to solve particularly interesting problems (think code highlights)