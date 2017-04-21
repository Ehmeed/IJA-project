

package ija.ija2017.project.klondike;

import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author xhruba08
 */
public class StopWatch{
    
    private int timePassed;
    private Timer timer;
    private TimerTask timerTask;
    private Game game;
    private boolean running;
    
    public StopWatch(Game game){
        running = true;
        timePassed = 0;
        timer = new Timer();
        timerTask = new TimerTask(){
            @Override
            public void run() {
                if(running){
                    timePassed++;
                    game.repaint();
                }
            }            
        };
        
    }
    
    public void start(){
        running = true;
        if(timePassed > 0){
            timePassed = 0;            
        }else {
            timer.scheduleAtFixedRate(timerTask, 1000, 1000);
        }
    }
    public void stop(){
        this.running = false;
    }
    
    public void reset(){
        this.timePassed = 0;
    }
    public int getTime(){
        return this.timePassed;
    }
    public void setTime(int time){
        this.timePassed = time;
    }
    
    public static String getTimeToString(int timePassed){
        String time;
        int minutes = timePassed / 60;
        int seconds = timePassed - minutes*60;
        if(minutes < 10) time = "0"+minutes+":";
        else time = minutes+":";
        if(seconds < 10) time += "0"+seconds;
        else time +=seconds+"";
        return time;
    }

}
