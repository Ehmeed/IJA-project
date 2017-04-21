

package ija.ija2017.project.klondike;

import ija.ija2017.project.cards.CardDeck;
import ija.ija2017.project.cards.TargetPack;
import ija.ija2017.project.cards.WorkingPack;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFileChooser;

/**
 * Class representing save of a game
 * @author xhruba08
 */
public class Save implements Serializable{
    /**
     *  Main card deck of the game
     */
    private CardDeck mainCardDeck;
    /**
     * Carddeck to take new cards
     */
    private CardDeck otherCardDeck;
    /*
    target pack for each color
    */
    private TargetPack targetD;
    private TargetPack targetH;
    private TargetPack targetS;
    private TargetPack targetC;
    /*
    set of seven working packs
    */
    private WorkingPack work1;
    private WorkingPack work2;
    private WorkingPack work3;
    private WorkingPack work4;
    private WorkingPack work5;
    private WorkingPack work6;
    private WorkingPack work7;
    
    private int timeElapsed;
    
    public Save(){}
    public Save(CardDeck mainCardDeck, CardDeck otherCardDeck, TargetPack targetD, TargetPack targetH, TargetPack targetS,
                TargetPack targetC, WorkingPack work1, WorkingPack work2, WorkingPack work3, WorkingPack work4, WorkingPack work5,
                WorkingPack work6, WorkingPack work7, int timeElapsed){
        this.mainCardDeck = mainCardDeck;
        this.otherCardDeck = otherCardDeck;
        this.targetC = targetC;
        this.targetD = targetD;
        this.targetS = targetS;
        this.targetH = targetH;
        this.work1 = work1;
        this.work2 = work2;
        this.work3 = work3;
        this.work4 = work4;
        this.work5 = work5;
        this.work6 = work6;
        this.work7 = work7;
        this.timeElapsed = timeElapsed;
    }
    public Save loadGame(){
        Save save = null;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result;
        result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                File saveFile = fileChooser.getSelectedFile();
                FileInputStream fis = new FileInputStream(saveFile);
                ObjectInputStream ois = new ObjectInputStream(fis);
                save = (Save) ois.readObject();
            } catch (IOException | ClassNotFoundException ex) {
                save = null;
            }
            
        }
        return save;
    }
    public boolean chooseLocation(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd_HHmmss");
        String name = simpleDateFormat.format( new Date()) + ".save";
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result;
        result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                File selectedDir = fileChooser.getSelectedFile();
                File newSaveFile = new File(selectedDir.getAbsolutePath()+"\\"+name);
                FileOutputStream fos = new FileOutputStream(newSaveFile);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(this);
            } catch (IOException ex) {
                return false;
                //Logger.getLogger(Save.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else return false;
        
        
        return true;
    }
    public int getTime(){
        return this.timeElapsed;
    }
    public CardDeck getMain(){
        return this.mainCardDeck;
    }
    public CardDeck getOther(){
        return this.otherCardDeck;
    }

    public TargetPack getTargetC(){
        return this.targetC;
    }
    public TargetPack getTargetD(){
        return this.targetD;
    }
    public TargetPack getTargetS(){
        return this.targetS;
    }
    public TargetPack getTargetH(){
        return this.targetH;
    }
    public WorkingPack getWork1(){
        return this.work1;
    }
    public WorkingPack getWork2(){
        return this.work2;
    }
    public WorkingPack getWork3(){
        return this.work3;
    }
    public WorkingPack getWork4(){
        return this.work4;
    }
    public WorkingPack getWork5(){
        return this.work5;
    }
    public WorkingPack getWork6(){
        return this.work6;
    }
    public WorkingPack getWork7(){
        return this.work7;
    }
    
}
