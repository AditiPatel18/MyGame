///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
package main;

import entity.Player;
import java.awt.*;
import tile.TileManager;
import javax.swing.JPanel;
import object.SuperObject;
///**
// *
// * @author admin
// */
public class GamePanel extends JPanel implements Runnable {
    final int originalTileSize=16;   // 16X16 title
    final int scale=3; 
    public final int tileSize=originalTileSize*scale; //48X48 tile
    public final int maxScreenCol=16;
    public final int maxScreenRow=12;
    
    public final int screenWidth=tileSize*maxScreenCol;  //768 pixels
    public final int screenHeight=tileSize*maxScreenRow;  // 576 pixels
    
    public final int maxWorldCol=50;
    public final int maxWorldRow=50;
//    public final int worldWidth=tileSize*maxWorldCol;
//    public final int worldHeight=tileSize*maxWorldRow;
    
    int FPS=60;
    
    public TileManager tileM=new TileManager(this);
    KeyHandler keyH=new KeyHandler();
    
    Sound music=new Sound();
    Sound se=new Sound();
    
    public UI ui=new UI(this);
    Thread gameThread; 
    
    public CollisionChecker cChecker=new CollisionChecker(this);
    public AssetSetter aSetter=new AssetSetter(this);
    
    
    public Player player=new Player(this,keyH);
    
    public SuperObject obj[]=new SuperObject[10];
    
    
    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }
    
    public void setupGame(){
        aSetter.setObject();
        playMusic(0);
    }
    
    public void startGameThread(){
        gameThread=new Thread(this);
        gameThread.start();
    }
    
////    public void run(){
////        
////        double drawInterval=1000000000/FPS;  //0.0166666 seconds
////        double nextDrawTime=System.nanoTime()+drawInterval;
////        
////        
////        
////        while(gameThread!=null){
////            
////            
////            update();
////            
////            repaint();
////            
////            try{
////            
////                double remainingTime=nextDrawTime-System.nanoTime();
////                remainingTime=remainingTime/1000000; //milliseconds
////                
////                if(remainingTime<0){
////                    remainingTime=0;
////                }
////                Thread.sleep((long)remainingTime);
////                
////                nextDrawTime+=drawInterval;
////                
////            }
////            catch(InterruptedException e){
////                e.printStackTrace();
////            }
////        }
////    }
    
    @Override
    public void run(){
        
        double drawInterval=1000000000/FPS;  //0.0166666 seconds
        double delta=0;
        long lastTime=System.nanoTime();
        long currentTime;
        long timer=0;
        int drawCount=0;
        
        while(gameThread!=null){
            
            currentTime=System.nanoTime();
            
            delta+=(currentTime-lastTime)/drawInterval;
            timer+=(currentTime-lastTime);
            lastTime=currentTime;
            
            if(delta>=1){
                update();
                repaint();
                delta--;
                drawCount++;
            }
            
            if(timer>=1000000000){
                //System.out.println("FPS: "+drawCount);
                drawCount=0;
                timer=0;
            }
        }
    }
    
    public void update(){
        player.update();
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        Graphics2D g2=(Graphics2D)g;
        
        
        tileM.draw(g2);
        
        for(int i=0;i<obj.length;i++){
            if(obj[i]!=null){
                obj[i].draw(g2,this);
            }
        }
        player.draw(g2);
        
        ui.draw(g2);
        g2.dispose();
    }
    
    public void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();
    }
    
    public void stopMusic(){
        music.stop();
    }
    
    public void playSE(int i){
        se.setFile(i);
        se.play();
    }
}




