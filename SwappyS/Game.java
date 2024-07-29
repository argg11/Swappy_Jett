import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

public class Game extends JPanel implements ActionListener,KeyListener{
    int boardwidth = 360;
            int boardheight = 640;

        Image Backgroundimg;
        Image shivaimg;
        Image topimg;
        Image bottomimg;
        Image ScaleImg;
         
        
        int birdX = boardwidth/8;    
        int birdY = boardheight/2; 
        int birdwidth = 34;
        int birdheight = 24;

        class Bird{
            int x = birdX;
            int y = birdY;
            int width = birdwidth;
            int height = birdheight;
            Image img;

            Bird(Image img){
                this.img = img;
            }
        }

        int pipeX = boardwidth;
        int pipeY = 0;
        int pipewidth = 64;
        int pipeheight = 512;

        class pipe{
            int x = pipeX;
            int y = pipeY;
            int width = pipewidth;
            int height = pipeheight;
            Image img;
            boolean passed = false;
            pipe(Image img){
                this.img = img;
            }
        }
        Bird bird;
        int velocityX = -6;
        int velocityY = -30;
        int gravity = 2;

        double score = 0;
        

        ArrayList<pipe> pipes;
        boolean gameover = false;
        Random random = new Random();

        Timer gameloop; 
        Timer placepipesTimer;


        Game(){     
                    
           setPreferredSize(new Dimension(boardwidth,boardheight));
           setBackground(Color.blue);
           setFocusable(true);
        addKeyListener(this);

        Backgroundimg = new ImageIcon(getClass().getResource("./swappysbg.png")).getImage();
        shivaimg = new ImageIcon(getClass().getResource("./shiva.png")).getImage();
        topimg=new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottomimg=new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();
           
        bird = new Bird(shivaimg);

        pipes = new ArrayList<pipe>();

        gameloop = new Timer (1000/60,this);
        gameloop.start();

        placepipesTimer = new Timer(1500, new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e){
                    placepipes();
            }

        });
        
        placepipesTimer.start();

         
    
    }

        public void placepipes(){

            int randompipeY = (int)(pipeY-pipeheight/4 - Math.random()*(pipeheight/2));
            int openingS = boardheight/4;
            pipe top = new pipe(topimg);
            top.y = randompipeY;
            pipes.add(top);

            pipe bottom = new pipe(bottomimg);
            bottom.y = top.y + openingS + pipeheight;
            pipes.add(bottom);
        
        }

            
        public void paintComponent(Graphics g){
               super.paintComponent(g);
               draw(g);
           }
           public void draw(Graphics g){
               g.drawImage(Backgroundimg,0,0,boardwidth,boardheight,null);
               g.drawImage(shivaimg, bird.x, bird.y, birdwidth, birdheight, null);

               for(int i=0 ; i<pipes.size(); i++){
                pipe pipe = pipes.get(i);
                g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width,pipe.height,null);
               }

               g.setColor(Color.black.brighter());
               g.setFont(new Font("Comic Sans MS",Font.PLAIN,32));

               if(gameover){
                g.drawString("Press R (SCORE)--> "+ String.valueOf((int)score),10,320);
               }
               else{
                g.drawString(String.valueOf((int)score),15,35);
               }




            }

        public void move(){
            velocityY += gravity;
            bird.y += velocityY;
            bird.y = Math.max(bird.y,0);



            for(int i=0 ; i<pipes.size() ; i++){
                pipe pipe = pipes.get(i);
                pipe.x += velocityX;

                

                if(!pipe.passed && bird.x >pipe.x+pipe.width){
                    
                    pipe.passed = true;
                    score += 0.5;

                    if(score==10){
                        velocityX = -8;
            
                    }

                    if(score==20){
                        velocityX = -10;
            
                    }

                    if(score==30){
                        velocityX = -12;
            
                    }
                    
                }

                

                if(collison(bird , pipe)){
                    gameover=true;
                }

            }

            if(bird.y>boardheight){
                gameover=true;
            }

        }


        public boolean collison(Bird a , pipe b){
            return a.x<b.x+b.width &&
                   a.x+a.width>b.x &&
                   a.y<b.y+b.height &&
                   a.y+a.height>b.y;

        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            move();
            repaint();
            if(gameover==true){
                placepipesTimer.stop();
                gameloop.stop();
            }

        }
        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode()==KeyEvent.VK_SPACE){
                velocityY = -15; }

            if(e.getKeyCode()==KeyEvent.VK_R){
                if(gameover){
    
                    bird.y=birdY;
                    velocityY=0;
                    pipes.clear();
                    score=0;
                    gameover=false;
                    gameloop.start();
                    placepipesTimer.start();
                    velocityX = -6;
            
                    } 
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {

        }

        

        @Override
        public void keyReleased(KeyEvent e) {
            
        }  
}
