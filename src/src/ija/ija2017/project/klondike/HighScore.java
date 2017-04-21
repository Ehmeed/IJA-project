

package ija.ija2017.project.klondike;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 *
 * @author xhruba08
 */
public class HighScore implements Serializable{
    private LinkedList<ScoreRecord> scores;
    private final int size = 10;
    private static final String filePath = "examples/highScores.ez";
    
    public HighScore(){
        scores = new LinkedList<>();
    }
    public boolean isFull(){
        return scores.size() == size;
    }
    public boolean checkScore(int time){
        return ( scores.isEmpty() || time < scores.getLast().getScore());
    }
    public void addScore(int time){
        String name;
        name = System.getProperty("user.name");   
        ScoreRecord newHighScore = new ScoreRecord(time, name);
        //for loop and insert   
        if(scores.isEmpty()) {
            scores.add(newHighScore);
        }
        else{
            for(int i = scores.size(); i > 0; i--){
                if(time > scores.get(i-1).getScore()){
                    scores.add(i, newHighScore);
                    break;
                }
                if(i==1)scores.add(0, newHighScore);
            }

            if(scores.size() > size){
                scores.removeLast();
            }
        }
        show();
        writeScores();
    }
    public void show(){
       JFrame hsFrame = new JFrame("HIGH SCORES");
       hsFrame.setPreferredSize(new Dimension(300,600));
       hsFrame.setMinimumSize(new Dimension(300,600));
       hsFrame.setResizable(false);
       hsFrame.setLocationRelativeTo(null);
       hsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       hsFrame.setVisible(true);
       JComponent panel = new JComponent() {
           @Override
           public void paintComponent(Graphics g){
               int y = 0;
               int x = 50;
               g.setColor(Color.black);
               g.fillRect(0,0,300,600);
               g.setColor(Color.white);
               g.setFont(new Font("arial", 1, 30));
               if(scores.isEmpty()){
                  g.drawString("No Records", 55, 200);
               }else {
                   for(ScoreRecord sc : scores){
                        if(y==9) x-=17;
                        g.drawString(y+1+". "+sc.getName()+" " +StopWatch.getTimeToString(sc.getScore()),x, 50+y*50);
                        y++;
                   }
               }
           }
       };
       hsFrame.add(panel);
    }
    public void resetScores(){
        HighScore dummy = new HighScore();
        dummy.writeScores();
        this.scores.clear();
    }
    public static HighScore loadScores(){
        //load scores from file
        HighScore hs;
        try {
            File scoreFile = new File(filePath);
            FileInputStream fis = new FileInputStream(scoreFile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            hs = (HighScore) ois.readObject();
        } catch (IOException | ClassNotFoundException ex) {
                hs = null;
                Launcher.log("No high score table found");
        }
        if(hs == null) hs = new HighScore();
        return hs;
    }
    public void writeScores(){
        try {
            File scoreFile = new File(filePath);
            FileOutputStream fos = new FileOutputStream(scoreFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            }catch (IOException ex) {
                Launcher.log("Error writing scores");
            }
    }
    
    public class ScoreRecord implements Serializable{
        private int score;
        private String name;
        
        public ScoreRecord(int score, String name){
            this.score = score;
            this.name = name;
        }
        public int getScore(){
            return this.score;
        }
        public String getName(){
            return this.name;
        }
    }
    
    /**
     * debug, prints scores to console
     */
    public void printScores(){
        for (ScoreRecord score : scores) {
            Launcher.log(score.getName() + score.getScore());
        }
    }
    
}
