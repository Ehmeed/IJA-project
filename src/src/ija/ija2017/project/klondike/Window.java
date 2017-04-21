

package ija.ija2017.project.klondike;;



import java.awt.event.ComponentEvent;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToolBar.Separator;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;


/**
 * Main window of the application.
 * @author xhruba08
 */

public class Window {
    private JFrame frame;
    private Launcher launcher;
    private int gameSelected = 0;
    
    public Window(String title, int width, int height, Launcher launcher){
        this.launcher = launcher;
        frame = new JFrame(title);
        frame.setPreferredSize(new Dimension(width,height));
        //frame.setMaximumSize(new Dimension(width,height));
        frame.setMinimumSize(new Dimension(500,500/12*9));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);        
        frame.pack();  
        frame.addComponentListener(new FrameListen());
        frame.setLocationRelativeTo(null);             
        frame.setVisible(true);         
        //Jmenu bar
        
        ActionListener menuListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                String action = event.getActionCommand(); 
                Launcher.log(event.paramString());
                if(action.equals("Exit")){
                    System.exit(0);
                }else if(action.equals("New Game")) {
                    launcher.newGame();
                }else if(action.equals("How to play")){
                    //TODO
                }else if(action.equals("Restart")){
                    Launcher.log("Restarting all games!");
                    for(Game game : Launcher.games){                        
                            frame.remove(game);                        
                    }
                    Launcher.games.clear();
                    setLayout(1,1);
                    launcher.newGame();                           
                }else if(action.equals("Show")){
                    launcher.getHighScore().show();
                }else if(action.equals("Reset")){
                    Launcher.log("reseting scores");
                    launcher.getHighScore().resetScores();
                }else if(action.equals("New")){
                    if(Launcher.games.size()-1 >= gameSelected ){
                       if(Launcher.games.size() == 1 && gameSelected == 0){
                           launcher.newGame();
                           Launcher.games.get(gameSelected).quit();
                       }else{
                           Launcher.log("Other game selected");
                           //Launcher.games.get(gameSelected).quit();
                           Launcher.games.get(gameSelected).start();
                       }
                    }else{
                        if(gameSelected == 1){
                            launcher.newGame();
                        }else if (gameSelected == 2){
                            launcher.newGame();
                            launcher.newGame();
                            Launcher.games.get(1).quit();
                        }else if(gameSelected == 3){
                            launcher.newGame();
                            launcher.newGame();
                            launcher.newGame();
                            Launcher.games.get(1).quit();
                            Launcher.games.get(2).quit();
                        }
                    }
                }else if(action.equals("Save")){
                    if(Launcher.games.size()-1 >= gameSelected && Launcher.games.get(gameSelected).running()){
                        Launcher.games.get(gameSelected).saveGame();
                    }
                }else if(action.equals("Load")){
                    if(Launcher.games.size()-1 < gameSelected){
                        launcher.newGame();
                    }
                    if(!Launcher.games.get(gameSelected).running()){
                        Launcher.games.get(gameSelected).start();
                    }
                    Launcher.games.get(gameSelected).loadGame();
                }else if(action.equals("Close")){
                    if(Launcher.games.size()-1 >= gameSelected && Launcher.games.get(gameSelected).running()){
                        Launcher.games.get(gameSelected).quit();
                    }
                }
            }
        };
        MenuListener ml = new MenuListener(){
            @Override
            public void menuSelected(MenuEvent e) {
                JMenu menu = (JMenu) e.getSource();
                switch(menu.getText()){
                    case "Game 1":
                        gameSelected = 0;
                        break;
                    case "Game 2":
                        gameSelected = 1;
                        break;
                    case "Game 3":
                        gameSelected = 2;
                        break;
                    case "Game 4":
                        gameSelected = 3;
                        break;
                }
                Launcher.log("Menu game " + menu.getText()+ " selected.");
            }

            @Override
            public void menuDeselected(MenuEvent e) {
            }

            @Override
            public void menuCanceled(MenuEvent e) {
            }
        
        };
        
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        
        JMenu games = new JMenu("Game");
        menuBar.add(games);
        JMenu highScores = new JMenu("High Scores");
        menuBar.add(highScores);
        JMenu help = new JMenu("Help");
        menuBar.add(help);
        
        menuBar.add(new Separator(new Dimension(Launcher.WIDTH/50,0)));
      
        JMenu game1 = new JMenu("Game 1");
        menuBar.add(game1);
        game1.addMenuListener(ml);
        JMenu game2 = new JMenu("Game 2");
        menuBar.add(game2);
        game2.addMenuListener(ml);
        JMenu game3 = new JMenu("Game 3");
        menuBar.add(game3);
        game3.addMenuListener(ml);
        JMenu game4 = new JMenu("Game 4");
        menuBar.add(game4);
        game4.addMenuListener(ml);
        
        
        
        JMenuItem item = new JMenuItem("New Game");
        games.add(item);
        item.addActionListener(menuListener);
        item = new JMenuItem("Restart");
        games.add(item);
        item.addActionListener(menuListener);
        item = new JMenuItem("Exit");
        games.add(item);
        item.addActionListener(menuListener);
        
        item = new JMenuItem("Show");
        highScores.add(item);
        item.addActionListener(menuListener);
        item = new JMenuItem("Reset");
        highScores.add(item);
        item.addActionListener(menuListener);
        
        item = new JMenuItem("How to play");
        help.add(item);
        item.addActionListener(menuListener);
        
        item = new JMenuItem("About");
        help.add(item);
        item.addActionListener(menuListener);
        
        item = new JMenuItem("New");
        game1.add(item);
        item.addActionListener(menuListener);
        item = new JMenuItem("Save");
        game1.add(item);
        item.addActionListener(menuListener);
        item = new JMenuItem("Load");
        game1.add(item);
        item.addActionListener(menuListener);
        item = new JMenuItem("Close");
        game1.add(item);
        item.addActionListener(menuListener);
        
        item = new JMenuItem("New");
        game2.add(item);
        item.addActionListener(menuListener);
        item = new JMenuItem("Save");
        game2.add(item);
        item.addActionListener(menuListener);
        item = new JMenuItem("Load");
        game2.add(item);
        item.addActionListener(menuListener);
        item = new JMenuItem("Close");
        game2.add(item);
        item.addActionListener(menuListener);
        
        item = new JMenuItem("New");
        game3.add(item);
        item.addActionListener(menuListener);
        item = new JMenuItem("Save");
        game3.add(item);
        item.addActionListener(menuListener);
        item = new JMenuItem("Load");
        game3.add(item);
        item.addActionListener(menuListener);
        item = new JMenuItem("Close");
        game3.add(item);
        item.addActionListener(menuListener);
        
        item = new JMenuItem("New");
        game4.add(item);
        item.addActionListener(menuListener);
        item = new JMenuItem("Save");
        game4.add(item);
        item.addActionListener(menuListener);
        item = new JMenuItem("Load");
        game4.add(item);
        item.addActionListener(menuListener);
        item = new JMenuItem("Close");
        game4.add(item);
        item.addActionListener(menuListener);
        
        
      }
     
    /**
     * Component listener class
     * Handles window resize events
     */
    private class FrameListen implements ComponentListener{

        @Override
        public void componentResized(ComponentEvent e) {
            Launcher.WIDTH = frame.getWidth();
            Launcher.HEIGHT = frame.getHeight();
            Launcher.updateDimensions();
        }

        @Override
        public void componentMoved(ComponentEvent e) {
        }

        @Override
        public void componentShown(ComponentEvent e) {
        }

        @Override
        public void componentHidden(ComponentEvent e) {
        }
        
        

    }
    /*
    * Adds game to the window
    * Up to 4 games can be added at the time.
    * Grid layout has to be setup before adding games.
    * @param game Game we are adding
    */
    public void add(Game game){
        frame.add(game);
        frame.validate();
        frame.repaint();
    }
    /**
     * Removes game from the frame
     * @param game game to be removed
     */
    public void remove(Game game){
        frame.remove(game);
        frame.validate();
        frame.repaint();
    }
    
    /**
     * Sets layout to grid
     * @param x width of the grid
     * @param y length of the grid
     */
    public void setLayout(int x, int y){
        frame.setLayout(new GridLayout(x,y));
        frame.validate();
        frame.repaint(); 
    }
    
    
}
