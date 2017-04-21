

package ija.ija2017.project.klondike;

import ija.ija2017.project.cards.Card;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author xhruba08
 */
public class Assets {
    public static String path = "lib/cards/";
    public static BufferedImage back, bg, arrow, victory, newgame, hint, undo, quit, hint_arrow, hint_active;
    public static ArrayList<BufferedImage> cards = new ArrayList<>();
    public static void init(){
        try {
            bg = ImageIO.read(new File(path+"table.gif")); 
            back = ImageIO.read(new File(path+"back.png"));
            arrow = ImageIO.read(new File(path+"arrow.png"));
            victory = ImageIO.read(new File(path+"win.png"));
            hint = ImageIO.read(new File(path+"hint.png"));
            hint_active = ImageIO.read(new File(path+"hint_active.png"));
            undo = ImageIO.read(new File(path+"undo.png"));
            newgame = ImageIO.read(new File(path+"newgame.png"));
            quit = ImageIO.read(new File(path+"quit.png")); 
            hint_arrow = ImageIO.read(new File(path+"hint_arrow.png")); 
            for(int i = 1; i <= 52; i++){
                cards.add(ImageIO.read(new File(path+i+".png")));                
            }
         
        } catch (IOException ex) {
            Launcher.exitError("Error loading textures.");
        }
        Launcher.log("# of cards loaded: "+cards.size());
    }
    
    public static BufferedImage get(int value, Card.Color color){
        int index = 0;        
        if (null != color) switch (color) {
            case DIAMONDS:
                index += 13;
                break;
            case HEARTS:
                index += 26;
                break;
            case SPADES:
                index += 39;
                break;
            default:
                break;
        }
        index += value - 1;
        return cards.get(index);
    }
}
