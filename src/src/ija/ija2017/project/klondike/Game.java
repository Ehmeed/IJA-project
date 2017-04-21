

package ija.ija2017.project.klondike;

import ija.ija2017.project.cards.Card;
import ija.ija2017.project.cards.CardDeck;
import ija.ija2017.project.cards.HeldDeck;
import ija.ija2017.project.cards.TargetPack;
import ija.ija2017.project.cards.WorkingPack;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.event.MouseInputListener;


/**
 * Represents an instance of a game.
 * Each instance has an unique ID.
 * @author xhruba08
 */
public class Game extends JComponent implements MouseInputListener{


    /**
     * ID of the game, ranging from 0 to 3
     */
    private final int ID;
    /**
    Dimensions of the game [pixels]
    */
    private int width,height;
   
    /**
    * Indicates whether the game is running or not
    */
    private boolean running = false;
    
    private boolean hint = false;
    
    private StopWatch stopwatch;
    
    private final Factory factory;
    private final Launcher launcher;
    
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
    
    /**
     * Deck held by player
     */
    private HeldDeck holding;
    
    private ArrayList<Card> cards;
    
    private ArrayList<Command> history;
    
    private HighScore highScore;
    
    private boolean game_won_FLAG = false;
    
   
    public Game(int ID, int width, int height, Factory factory, HighScore highScore, Launcher launcher){   
        this.ID = ID;     
        this.width = width;
        this.height = height;
        this.factory = factory;
        this.launcher = launcher;
        this.highScore = highScore;
        this.cards = new ArrayList<>();
        this.history = new ArrayList<>();
        this.stopwatch = new StopWatch(this);
    }
    
    public void listen(){
        //add mouse listener        
        this.addMouseListener(this);  
        this.addMouseMotionListener(this);
    }
    /**
    Starts up the game
    */
    public void start(){
        Launcher.log("new game with id "+ID+" has started.");
        //initialize all decks
        initDecks();  
        stopwatch.start();
        running = true;
        game_won_FLAG = false;
        repaint();
    }
    /**
    * debug method to print all cards in deck
    * @param deck deck to print
    */
    public void printDeck(CardDeck deck){
        for(int i =0; i < deck.size(); i++){
            Launcher.log(deck.get(i).toString());
        }        
    }
    /**
    * debug method to print all cards in workingpack
    * @param deck deck to print
    */
    public void printWorkingPack(WorkingPack deck){
        for(int i =0; i < deck.size(); i++){
            Launcher.log(deck.get(i).toString());
        }        
    }
   
    /**
     * Initializes all decks needed for a game
     */
    public void initDecks(){
        // deck held by player
        holding = new HeldDeck();
        // main card deck
        mainCardDeck = new CardDeck(1);   
        mainCardDeck = factory.createCardDeck();
        mainCardDeck.shuffle();
        
        otherCardDeck = new CardDeck(2);
        
        cards.clear();
        //copy all cards into local array
        for(int i = 0; i <mainCardDeck.size(); i++){
            cards.add(mainCardDeck.get(i));
        }
            
        // set of working packs
        work1 = factory.createWorkingPack();
        work2 = factory.createWorkingPack();
        work3 = factory.createWorkingPack();
        work4 = factory.createWorkingPack();
        work5 = factory.createWorkingPack();
        work6 = factory.createWorkingPack();
        work7 = factory.createWorkingPack();   
        
        // deal cards from main card deck to working packs
        deal();
           
        // set of target packs
        targetD = factory.createTargetPack(Card.Color.DIAMONDS);
        targetH = factory.createTargetPack(Card.Color.HEARTS);
        targetS = factory.createTargetPack(Card.Color.SPADES);
        targetC = factory.createTargetPack(Card.Color.CLUBS);        
       
    }
    
    /** 
    * deals cards from main card deck to working packs
    * turns first card face up
    */
    public void deal(){
        for(int i = 0; i < 7; i++){
            switch(i){
                case 0:
                    work1.push(mainCardDeck.pop());
                case 1:
                    work2.push(mainCardDeck.pop());
                case 2:
                    work3.push(mainCardDeck.pop());
                case 3:
                    work4.push(mainCardDeck.pop());
                case 4:
                    work5.push(mainCardDeck.pop());
                case 5:
                    work6.push(mainCardDeck.pop());
                case 6:
                    work7.push(mainCardDeck.pop());
            }
        }
        work1.get().turnFaceUp();
        work2.get().turnFaceUp();
        work3.get().turnFaceUp();
        work4.get().turnFaceUp();
        work5.get().turnFaceUp();
        work6.get().turnFaceUp();
        work7.get().turnFaceUp();
        
    }
    /*
    Updates dimension of the game
    */
    public void updateDimensions(int width, int height){
        this.width = width;
        this.height = height;
    }
    public void quit(){
        running = false;
        launcher.quit(ID);
    }
    /**
     * endgame handling
     */
    public void gameWon(){
        game_won_FLAG = true;
        running = false;
        stopwatch.stop();
        if (!highScore.isFull() || highScore.checkScore(stopwatch.getTime())){
            highScore.addScore(stopwatch.getTime());        
        }
    }
    
    public void showHint(){
        ArrayList<WorkingPack> wps = new ArrayList<>();
        wps.add(work1);
        wps.add(work2);
        wps.add(work3);
        wps.add(work4);
        wps.add(work5);
        wps.add(work6);
        wps.add(work7);
        ArrayList<TargetPack> tps = new ArrayList<>();
        tps.add(targetS);
        tps.add(targetH);
        tps.add(targetC);
        tps.add(targetD);
        
        //from working pack to target
        for(int i = 0; i < 7; i++){
            if(wps.get(i).isEmpty()) continue;
            for(int j = 0; j < 4; j++){
                if(tps.get(j).size() + 1 == wps.get(i).get().value() && tps.get(j).color() == wps.get(i).get().color()){
                    //SOURCE = WP I; TARGET = TP J;
                    Launcher.log(i +" to " + j);
                    wps.get(i).setHint();
                    tps.get(j).setHint();
                    return;
                }
            }
        }
        //from other card deck to target
        if(!otherCardDeck.isEmpty()){
            for(int j = 0; j < 4; j++){
                    if(tps.get(j).size() + 1 == otherCardDeck.get().value() && tps.get(j).color() == otherCardDeck.get().color()){
                        //SOURCE = OTHER; TARGET = TP J;
                        Launcher.log("Other to " + j);
                        otherCardDeck.setHint();
                        tps.get(j).setHint();
                        return;
                    }
             }
        }
        
         //from working pack to working pack
        for(int i = 0; i < 7; i++){
            if(wps.get(i).isEmpty()){
                continue;
            }
            Card top = wps.get(i).getTopFacingUp();
            if(top == null) break;
            for(int j = 0; j < 7; j++){
                
                if(top.value() == 13 && wps.get(j).isEmpty() && wps.get(i).get(0).value() != 13 && wps.get(i).get(0).color() != top.color()){ 
                    Launcher.log(i + " to " + j);
                    wps.get(j).setHint();
                    wps.get(i).setHint();
                    return;
                }
                //only king can be placed on empty working pack
                if(wps.get(j).isEmpty()) continue;
                
                if(top.value() + 1== wps.get(j).get().value() && !top.similarColorTo(wps.get(j).get())){
                     Launcher.log(i + " to "+ j);
                     wps.get(j).setHint();
                     wps.get(i).setHint();
                     return;
                 }
            }
            
        }
        
        //from other card deck to working pack
        if(!otherCardDeck.isEmpty()){
            for(int j = 0; j < 7; j++){
                if(wps.get(j).isEmpty()){
                    if(otherCardDeck.get().value() == 13){
                        Launcher.log("other to " + j);
                        otherCardDeck.setHint();
                        wps.get(j).setHint();
                        return;
                    }else {
                        continue;
                    }
                } 
                if(wps.get(j).get().value() == otherCardDeck.get().value()+1 && !wps.get(j).get().similarColorTo(otherCardDeck.get())){
                    //SOURCE = OTHER; TARGET = WP J;
                    Launcher.log("Other to " + j);
                    otherCardDeck.setHint();
                    wps.get(j).setHint();
                    return;
                }
             }
        }
        //check if any card from main card deck or other card deck, that player
        //can't see, cant be used to further advance
        if(mainCardDeck.size() + otherCardDeck.size() > 1){
            if(!mainCardDeck.isEmpty()){
                for(int i = 0; i < mainCardDeck.size(); i++){
                    Card card = mainCardDeck.get(i);
                    //main to tp
                    for(int j = 0; j < 4; j++){
                        if(tps.get(j).size() + 1 == card.value() && tps.get(j).color() == card.color()){
                            //SOURCE = OTHER; TARGET = TP J;
                            Launcher.log("take new");
                            mainCardDeck.setHint();
                            return;
                        }
                    } 
                    // main to wp
                    for(int j = 0; j < 7; j++){
                    if(wps.get(j).isEmpty()){
                        if(card.value() == 13){
                            Launcher.log("take new");;
                            mainCardDeck.setHint();
                            return;
                        }else {
                            continue;
                        }
                    } 
                    if(wps.get(j).get().value() == card.value()+1 && !wps.get(j).get().similarColorTo(card)){
                        //SOURCE = OTHER; TARGET = WP J;
                        Launcher.log("take new");
                        mainCardDeck.setHint();
                        return;
                    }
                 }


                }
            }
            if(otherCardDeck.size() > 1){
                for(int i = 0; i < otherCardDeck.size(); i++){
                    Card card = otherCardDeck.get(i);
                    //main to tp
                    for(int j = 0; j < 4; j++){
                        if(tps.get(j).size() + 1 == card.value() && tps.get(j).color() == card.color()){
                            //SOURCE = OTHER; TARGET = TP J;
                            Launcher.log("take new");
                            mainCardDeck.setHint();
                            return;
                        }
                    } 
                    // main to wp
                    for(int j = 0; j < 7; j++){
                    if(wps.get(j).isEmpty()){
                        if(card.value() == 13){
                           Launcher.log("take new");
                            mainCardDeck.setHint();
                            return;
                        }else {
                            continue;
                        }
                    } 
                    if(wps.get(j).get().value() == card.value()+1 && !wps.get(j).get().similarColorTo(card)){
                        //SOURCE = OTHER; TARGET = WP J;
                        Launcher.log("take new");
                        mainCardDeck.setHint();
                        return;
                    }
                 }


                }
            
            }
        }
       
        /*
        //take new card TODO
        if(!mainCardDeck.isEmpty() || otherCardDeck.size() > 1){
            Launcher.log("take new");
            mainCardDeck.setHint();
            return;
        }*/
        //from target back to working
        // disable it for now since it would make infinite loop
        /*
        for(int i = 0; i < 4; i++){
            if(tps.get(i).isEmpty()) continue;
            for(int j = 0; j < 7; j++){
                if(wps.get(j).isEmpty()) continue;
                if(tps.get(i).size() + 1 == wps.get(j).get().value() && !tps.get(i).get().similarColorTo(wps.get(j).get()) ){
                    //SOURCE = WP I; TARGET = TP J;
                    Launcher.log(i +" to " + j);
                    tps.get(i).setHint();
                    wps.get(j).setHint();
                    return;
                }
            }
        }
        */
        
        //no possible moves
        Launcher.log("NO POSSIBLE MOVES");
        
    }
    //save game handling
    public void saveGame(){
        Launcher.log("Saving game");
        Save save = new Save(mainCardDeck, otherCardDeck, targetD, targetH, targetS, targetC, work1, work2, work3, work4, work5, work6, work7, stopwatch.getTime());
        save.chooseLocation();
    }
    public void loadGame(){
        Save save = new Save();
        save = save.loadGame();
        if(save == null) return;
        mainCardDeck = save.getMain();
        otherCardDeck = save.getOther();
        targetC = save.getTargetC();
        targetD = save.getTargetD();
        targetS = save.getTargetS();
        targetH = save.getTargetH();
        work1 = save.getWork1();
        work2 = save.getWork2();
        work3 = save.getWork3();
        work4 = save.getWork4();
        work5 = save.getWork5();
        work6 = save.getWork6();
        work7 = save.getWork7();
        stopwatch.setTime(save.getTime());
        repaint();
        //update references on new objects
        reloadCards();
        
    }
    public void reloadCards(){
        cards = new ArrayList<>();
        for(int i = 0; i < 52; i++){
            if(mainCardDeck.size()>i) cards.add(mainCardDeck.get(i));
            if(otherCardDeck.size()>i) cards.add(otherCardDeck.get(i));
            if(targetC.size() > i) cards.add(targetC.get(i));
            if(targetD.size() > i) cards.add(targetD.get(i));
            if(targetH.size() > i) cards.add(targetH.get(i));
            if(targetS.size() > i) cards.add(targetS.get(i));
            if(work1.size() > i) cards.add(work1.get(i));
            if(work2.size() > i) cards.add(work2.get(i));
            if(work3.size() > i) cards.add(work3.get(i));
            if(work4.size() > i) cards.add(work4.get(i));
            if(work5.size() > i) cards.add(work5.get(i));
            if(work6.size() > i) cards.add(work6.get(i));
            if(work7.size() > i) cards.add(work7.get(i));
            
            if(cards.size() == 52) break;
        }
    }
    
    public void clearHint(){
        mainCardDeck.clearHint();
        otherCardDeck.clearHint();
        targetC.clearHint();
        targetD.clearHint();
        targetS.clearHint();
        targetH.clearHint();
        work1.clearHint();
        work2.clearHint();
        work3.clearHint();
        work4.clearHint();
        work5.clearHint();
        work6.clearHint();
        work7.clearHint();
    }
    
    public void undo(){
        if(history.isEmpty()) return;
        Launcher.log("Undoing move.");
        Command cmd = history.get(history.size()-1);
        if(cmd.getSource() == 1 && cmd.getTarget() ==  1){
            CardDeck from = (CardDeck) cmd.getFrom();
            CardDeck to = (CardDeck) cmd.getTo();
            for(int i = 0; i < cmd.getCount(); i++){
                from.put(to.pop());
            }        
        }else if(cmd.getSource() == 1 && cmd.getTarget() ==  2){
            CardDeck from = (CardDeck) cmd.getFrom();
            TargetPack to = (TargetPack) cmd.getTo();
            for(int i = 0; i < cmd.getCount(); i++){
                from.put(to.pop().get(0));
            }  
        }else if(cmd.getSource() == 1 && cmd.getTarget() ==  3){
            CardDeck from = (CardDeck) cmd.getFrom();
            WorkingPack to = (WorkingPack) cmd.getTo();
            for(int i = 0; i < cmd.getCount(); i++){
                from.put(to.pop());
            }  
        }else if(cmd.getSource() == 2 && cmd.getTarget() ==  3){
            TargetPack from = (TargetPack) cmd.getFrom();
            WorkingPack to = (WorkingPack) cmd.getTo();
            for(int i = 0; i < cmd.getCount(); i++){
                from.push(to.pop());
            }  
        }else if(cmd.getSource() == 3 && cmd.getTarget() ==  2){
            WorkingPack from = (WorkingPack) cmd.getFrom();
            TargetPack to = (TargetPack) cmd.getTo();
            if(cmd.getTurned()){
                from.get().turnFaceDown();
            }
            for(int i = 0; i < cmd.getCount(); i++){
                from.push(to.pop().get(0));
            }  
        }else if(cmd.getSource() == 3 && cmd.getTarget() ==  3){
            WorkingPack from = (WorkingPack) cmd.getFrom();
            WorkingPack to = (WorkingPack) cmd.getTo();
            if(cmd.getTurned()){
                from.get().turnFaceDown();
            }
            for(int i = 0; i < cmd.getCount(); i++){
                from.push(to.pop());
            }  
        }
        history.remove(history.size()-1);
    }
    
    public void addToHistory(int source, int target, Object from, Object to, int count){
        Command cmd = new Command();
        cmd.setSource(source);
        cmd.setTarget(target);
        cmd.setFrom(from);
        cmd.setTo(to);  
        cmd.setCount(count);
        history.add(cmd);
    }
    
    public boolean running(){
        return this.running;
    }
    public void stop(){
        this.running = false;
    }
    @Override
    public void paintComponent(Graphics g){
        //Launcher.log("Updating gui..");
        // Paint background
        g.drawImage(Assets.bg, 0, 0, width, height, null);
        //g.setColor(Color.white);
        //g.drawString("Klondike", width-60,height-30);
        g.setColor(Color.black);
        g.drawRect(0,0,width,height);
        //new Card(Card.Color.SPADES, 1, true).draw(g, 0, 0       , width/9,height/5);
        //new Card(Card.Color.SPADES, 1, true).draw(g, 0, 20      , width/9,height/5);
        
        if (running){ 
            //undo hint buttons , quit button
            g.drawImage(Assets.undo, width/3, height/20,width/15, height/10, null);
            if(hint){
                g.drawImage(Assets.hint_active, width/3, 3*height/20,width/15, height/10, null);
            }else{
                g.drawImage(Assets.hint, width/3, 3*height/20,width/15, height/10, null);
            }
            g.drawImage(Assets.quit, 37*width/40, height/50,width/30, width/30, null);
            // Paint all decks
            mainCardDeck.draw(g, width, height);
            otherCardDeck.draw(g, width, height);
            work1.draw(g, width, height, 0);
            work2.draw(g, width, height, 1);
            work3.draw(g, width, height, 2);
            work4.draw(g, width, height, 3);
            work5.draw(g, width, height, 4);
            work6.draw(g, width, height, 5);
            work7.draw(g, width, height, 6);
            targetC.draw(g, width, height, 2);
            targetD.draw(g, width, height, 3);
            targetH.draw(g, width, height, 1);
            targetS.draw(g, width, height, 0); 
            
            //check for finished game
            if (targetC.size() == 13 && targetD.size() == 13 && targetH.size() == 13 && targetS.size() ==13){
                g.drawImage(Assets.victory, width/4, height/4, width/2, height/3, null);
                if(!game_won_FLAG){
                    gameWon();
                }
            }
            
            if(holding != null && holding.size() > 0){
                holding.draw(g, width, height);
            }
            //draw timer
            g.setFont(new Font("arial", 1, width/20));
            g.setColor(Color.white);
            g.drawString(StopWatch.getTimeToString(stopwatch.getTime()), width/15, height-height/8);
                  
        }else{
            //draw menu here
            g.drawImage(Assets.newgame, width/4, height/4, width/2, height/3, null);
        } 
    }
    
    /*
    Mouse input listener methods
    */
    @Override
    public void mouseClicked(MouseEvent e) {
        //Launcher.log("Mouse Clicked: "+e.getX()+" "+e.getY());
        int x = e.getX();
        int y = e.getY();  
        if(holding == null) holding = new HeldDeck();
        if(!running || !holding.isEmpty()){
            if(x>= width/4 && x < 3*width/4 && y>= height/4 && y < 3*height/4){
                //launcher.newGame();  
                this.start();
            }
            return;
        }
       
        if (x >= width/20 && y >= height/20 && x < width/20 + width/9 && y < height/20+ height/5){
            if(mainCardDeck.size() > 0){
                if(otherCardDeck.put(mainCardDeck.pop())){
                    //history.add(0,(Game)DeepObjectCopy.clone(this));                    
                    addToHistory(1, 1, mainCardDeck, otherCardDeck, 1);
                }
                clearHint();
                if(hint){
                    showHint();
                }
                Launcher.log("flipped next card.");
            }else {                
                while(otherCardDeck.size() != 0){
                    mainCardDeck.put(otherCardDeck.pop());
                }
                if(mainCardDeck.size()>0){
                    //h/istory.add(0,(Game)DeepObjectCopy.clone(this));
                    addToHistory(1, 1, otherCardDeck, mainCardDeck,mainCardDeck.size());
                }
            }
        }else if(x >= width/3 && x < width/3+width/15 && y >= height/20 && y < 3*height/20){
            //undo
            undo();
            clearHint();
            Launcher.log("undo");
        }else if(x >= width/3 && x < width/3+width/15 && y >= 3*height/20 && y < 5*height/20){
            Launcher.log("hint");
            if(hint){
                clearHint();
                hint = false;
            }else{
                showHint();
                hint = true;                
            }
        }else if(x >= 37*width/40 && x < 37*width/40 + width/30 && y >= height/50 && y < height/50 + width/30){
             Launcher.log("quit");
             quit();
        }
            
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //Launcher.log("Mouse Pressed: "+e.getX()+" "+e.getY());
        int x = e.getX();
        int y = e.getY();
        if(holding == null) holding = new HeldDeck();
        if(!running || !holding.isEmpty()){
            return;
        }
        holding.updatePosition(x,y);
        if (otherCardDeck.size() >= 1 && x >= width/5 && y >= height/20 && x < width/5 + width/9 && y < height/20+ height/5){
            
            holding.clear();
            holding.push(otherCardDeck.pop());
            holding.setID(1);
            holding.setFrom(otherCardDeck);
            return;
        }
        Launcher.log("Mouse pressed");       
        for (Card card : cards){
            if (card.clicked(x, y)){
                Launcher.log(card.toString()+" clicked!");
                if(targetC.size() > 0 && targetC.get().equals(card)){
                    holding = targetC.pop();
                    holding.setFrom(targetC);
                    holding.setID(2);
                    holding.updatePosition(x,y);
                    return;
                }
                if(targetD.size() > 0 && targetD.get().equals(card)){
                    holding = targetD.pop();
                    holding.setFrom(targetD);
                    holding.setID(2);
                    holding.updatePosition(x,y);
                    return;
                }
                if(targetS.size() > 0 && targetS.get().equals(card)){
                    holding = targetS.pop();
                    holding.setFrom(targetS);
                    holding.setID(2);
                    holding.updatePosition(x,y);
                    return;
                }
                if(targetH.size() > 0 && targetH.get().equals(card)){
                    holding = targetH.pop();
                    holding.setFrom(targetH);
                    holding.setID(2);
                    holding.updatePosition(x,y);
                    return;
                }
                
                holding = work1.pop(card);
                if (holding != null){
                    holding.setFrom(work1);
                    holding.setID(3);
                    holding.updatePosition(x,y);
                    return;
                }
                holding = work2.pop(card);
                if (holding != null) {
                    holding.setFrom(work2);
                    holding.setID(3);
                    holding.updatePosition(x,y);
                    return;
                }
                holding = work3.pop(card);
                if (holding != null) {
                    holding.setFrom(work3);
                    holding.setID(3);
                    holding.updatePosition(x,y);
                    return;
                }
                holding = work4.pop(card);
                if (holding != null) {
                    holding.setFrom(work4);
                    holding.setID(3);
                    holding.updatePosition(x,y);
                    return;
                }
                holding = work5.pop(card);
                if (holding != null) {
                    holding.setFrom(work5);
                    holding.setID(3);
                    holding.updatePosition(x,y);
                    return;
                }
                holding = work6.pop(card);
                if (holding != null) {
                    holding.setFrom(work6);
                    holding.setID(3);
                    holding.updatePosition(x,y);
                    return;
                }
                holding = work7.pop(card);
                if (holding != null) {
                    holding.setFrom(work7);
                    holding.setID(3);
                    holding.updatePosition(x,y);                  
                    return;
                }                
                break;
            }
        }
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //Launcher.log("Mouse Released: "+e.getX()+" "+e.getY()); 
        int x = e.getX();
        int y = e.getY();
       
        if(!running){
            return;
        }
        boolean success = false; // true when card dropped of succesfully
        if (holding != null && holding.size() > 0){
            if (holding.size() == 1){
                if (x >= 3*width/7 && x < 3*width/7+width/9 && y >= height/20 && y < height/20 + height/5){
                    //spades
                    success = targetS.put(holding.get(0));
                    if(success) addToHistory(holding.getID(), 2, holding.getFrom(), targetS, 1);
                    Launcher.log("Realeased spades");
                }else if (x >= 3*width/7  + width/8&& x < 3*width/7+width/9 + width/8&& y >= height/20 && y < height/20 + height/5){
                    //hearts
                    success = targetH.put(holding.get(0));
                    if(success) addToHistory(holding.getID(), 2, holding.getFrom(), targetH, 1);
                    Launcher.log("Realeased hearts");
                }else if (x >= 3*width/7  + 2*width/8&& x < 3*width/7+width/9 + 2*width/8&& y >= height/20 && y < height/20 + height/5){
                    //clubs
                    success = targetC.put(holding.get(0));
                    if(success) addToHistory(holding.getID(), 2, holding.getFrom(), targetC, 1);
                    Launcher.log("Realeased clubs");
                }else if (x >= 3*width/7  + 3*width/8&& x < 3*width/7+width/9 + 3*width/8&& y >= height/20 && y < height/20 + height/5){
                    //diamonds
                    success = targetD.put(holding.get(0));
                    if(success) addToHistory(holding.getID(), 2, holding.getFrom(), targetD, 1);
                    Launcher.log("Realeased diamonds");
                }
            }
            //handle dropping on WP here
            if(!success){
                //offset*width/8 + width/15, height/3 + i*width/40, width/9, height/5
                if (x >= width/15 && x < width/15+width/9 && y >= height/3 && y < height/3 + (work1.size())*width/40+height/5){
                    success = work1.put(holding);
                    if(success) addToHistory(holding.getID(), 3, holding.getFrom(), work1, holding.size());
                }else if(x >= width/15 + width/8 && x < width/15+width/9+width/8 && y >= height/3 && y < height/3 + (work2.size())*width/40+height/5){
                    success = work2.put(holding);
                    if(success) addToHistory(holding.getID(), 3, holding.getFrom(), work2, holding.size());
                }else if(x >= width/15 + 2*width/8 && x < width/15+width/9+2*width/8 && y >= height/3 && y < height/3 + (work3.size())*width/40+height/5){
                    success = work3.put(holding);
                    if(success) addToHistory(holding.getID(), 3, holding.getFrom(), work3, holding.size());
                }else if(x >= width/15 + 3*width/8 && x < width/15+width/9+3*width/8 && y >= height/3 && y < height/3 + (work4.size())*width/40+height/5){
                    success = work4.put(holding);
                    if(success) addToHistory(holding.getID(), 3, holding.getFrom(), work4, holding.size());
                }else if(x >= width/15 + 4*width/8 && x < width/15+width/9+4*width/8 && y >= height/3 && y < height/3 + (work5.size())*width/40+height/5){
                    success = work5.put(holding);
                    if(success) addToHistory(holding.getID(), 3, holding.getFrom(), work5, holding.size());
                }else if(x >= width/15 + 5*width/8 && x < width/15+width/9+5*width/8 && y >= height/3 && y < height/3 + (work6.size())*width/40+height/5){
                    success = work6.put(holding);
                    if(success) addToHistory(holding.getID(), 3, holding.getFrom(), work6, holding.size());
                }else if(x >= width/15 + 6*width/8 && x < width/15+width/9+6*width/8 && y >= height/3 && y < height/3 + (work7.size())*width/40+height/5){
                    success = work7.put(holding);
                    if(success) addToHistory(holding.getID(), 3, holding.getFrom(), work7, holding.size());
                }
            }
            
            if (!success){
                if(holding.getID() == 1){
                    CardDeck cd = (CardDeck) holding.getFrom();
                    cd.put(holding.get(0));
                }else if(holding.getID() == 3){
                    WorkingPack wp = (WorkingPack) holding.getFrom();
                    for (int i = 0; i < holding.size(); i++){
                        //wp.turnTopFaceDown();
                        wp.push(holding.get(i));
                    }
                }else if(holding.getID() == 2){
                    TargetPack tp = (TargetPack) holding.getFrom();
                    tp.put(holding.get(0));
                }
                
            }else {
                if(holding.getID() == 3){
                    WorkingPack wp = (WorkingPack) holding.getFrom();
                    if(!wp.isEmpty()){
                        wp.get().turnFaceUp();
                        history.get(history.size()-1).setTurned(); 
                    }
                    
                }
                holding.clear();
                clearHint();
                if(hint){
                    showHint();
                }
                //history.add(0,(Game)DeepObjectCopy.clone(this));
            }
            holding.clear();
        }
        Launcher.log("Mouse released.");
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

    @Override
    public void mouseDragged(MouseEvent e) { 
        if(!running){
            return;
        }
        if (holding != null){
            holding.updatePosition(e.getX(), e.getY());
        }
        repaint();
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {      
    }
}
