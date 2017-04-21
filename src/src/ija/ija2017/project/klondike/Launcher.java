

package ija.ija2017.project.klondike;

import java.util.ArrayList;


/**
 * MAIN CLASS
 * Launcher for the game.
 * @author xhruba08
 */
public class Launcher {

    /**
     * Indicates whether logging to console is on
     */
    public static boolean LOG = true;
     /**
     * Dimensions of the window[pixels]
     */
    public static int WIDTH = 800;
    public static int HEIGHT = WIDTH/12 *9;

    /**
     * Title of the window
     */
    public static final String TITLE = "Klondike";
    
    
    public enum State {
        Menu,
        Single,
        Multi;
    }
    /*
    * Current state of the game
    * Menu, single game, multi game (2-4 games in tiles)
    */
    public static State gameState = State.Menu;
    private Window window;
    private Factory factory;
    private HighScore highScore;
    /*
    * list of all games(both running and stopped)
    */
    public static ArrayList<Game> games;
       
    public static void main(String args[]){
        new Launcher();
    }
    
    public Launcher(){        
        //initialize window
        this.window = new Window(TITLE, WIDTH, HEIGHT, this);  
        this.factory = new Factory();
        this.highScore = HighScore.loadScores();
        games = new ArrayList<>();
        Assets.init();
        newGame();
    }
   
    public void quit(int id){
        int running = 0;
        Game game;
        game = null;
        for(int i=0; i < games.size(); i++){
                if(games.get(i).running()){
                    running++;
                    game = games.get(i);
                }
        }
        if(running == 0){
            System.exit(0);
        }else if(running == 1){
            Launcher.log("ONE GAME RUNNING, RESIZE WINDOW");
            for(Game g : games){
                window.remove(g);
            }
            games.clear();
            games.add(game);
            window.setLayout(1, 1);
            window.add(game);
            updateDimensions();
        }
        
        
    }
    /**
    * Starts new game
    * If no games are running, starts one game filling whole screen.
    * If one game is already running, starts 3 more, but cards are dealt only
    * in one of them. Resizes screen so 4 games fit.
    
    */
    public void newGame(){  
        Game game;
        //starting first game
        if (games.isEmpty()){
            game = new Game(games.size(),WIDTH, HEIGHT, factory, highScore,  this); 
            window.add(game);
            games.add(game);
            game.listen();
            game.start();            
        }else if (games.size() == 1){
            window.setLayout(2, 2);
            game = new Game(games.size(),WIDTH/2, HEIGHT/2, factory, highScore, this);
            window.add(game);
            games.add(game);
            game.listen();
            game.start();
            game = new Game(games.size(),WIDTH/2, HEIGHT/2, factory, highScore, this);
            window.add(game);
            games.add(game);
            game.listen();
            game = new Game(games.size(),WIDTH/2, HEIGHT/2, factory, highScore,  this);
            window.add(game);
            games.add(game);  
            game.listen();
            
            games.stream().forEach((g) -> {
                g.updateDimensions(WIDTH/2,HEIGHT/2);
            });
            
        } else if (games.size() == 4){
            for(Game g : games){
                if(!g.running()){
                    g.start();
                    break;
                }
            }
        }
    }
   
    /**
     * Not used currently. remove
     */
    public void reloadGames(){
        for(Game g : games){
            Launcher.log("reloading games");
            window.remove(g);
            window.add(g);
        }
    }
    /**
     * updates dimension for each game running
     */
    public static void updateDimensions(){
        if (games == null) return;
        if (games.size() == 1){
            games.get(0).updateDimensions(WIDTH,HEIGHT);
        }else if (games.size() > 1){
            games.stream().forEach((g) -> {
                    g.updateDimensions(WIDTH/2,HEIGHT/2);
            });
        }
    }
    
    public HighScore getHighScore(){
        return this.highScore;
    }
    
    /**
     * writes out debug messages in console if logging is on
     * @param msg
     */
    public static void log(String msg){
        if (LOG){
            System.out.println("debug: " + msg);
        }
    }
    /*
    Exits program with error log in console
    @param msg information about the error
    */
    public static void exitError(String msg){
        System.out.println("An error has occured. Log: " + msg);
        System.exit(1);
    }
    
    /*
    Exits program without error log
    */
    public static void exitError(){
        exitError("no info");
    }
    

}
