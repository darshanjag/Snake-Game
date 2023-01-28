import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Random;

public class SnakePanel extends JPanel implements ActionListener{

    //size of the panel
    static int width=1200, height= 600;
    //size of each unit
    static int unit = 50;

    // for food spawns
    Random random;
    //co-ordinates for the food
    int fx,fy;
    //to check the state of the game
    Timer timer;
    static int delay =160;
    int body =3;

    int dir= 'R';
    int score = 0;
    boolean flag = false;
    static int size = (width*height)/(unit*unit);
    int [] xSnake =  new int[size];
    int [] ySnake =  new int[size];

    SnakePanel(){
        this.setPreferredSize(new Dimension(width,height));
        this.setBackground(Color.black);
        this.setFocusable(true);
        random = new Random();
        this.addKeyListener(new Key());
        game_start();
    }
    public void game_start(){
        spawFood();
        //setting the game running flag to true.
        flag = true;
        //starting the timer with delay
        timer = new Timer(delay,this);
        timer.start();

    }
    public void spawFood(){
        //setting co ordinates for the food in multiples of 50;
        fx=random.nextInt((int)(width/unit))*unit;
        fy=random.nextInt((int)(height/unit))*unit;


    }
    public void checkHit(){
        //checking the snakes head collision with its own body or walls.

        //checking with the body parts.
        for(int i=body; i>0; i--){
            if((xSnake[0]==xSnake[i])&& (ySnake[0])==ySnake[i]){
                flag=false;
            }
        }
        // checking with the walls
        if(xSnake[0]<0){
            flag=false;
        }
        if(xSnake[0]>width){
            flag=false;
        }
        if(ySnake[0]<0){
            flag=false;
        }
        if(ySnake[0]>height){
            flag=false;
        }
        if(flag==false){
            timer.stop();
        }
    }
    // intermediate function to call draw function

    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        draw(graphics);
    }
    public void draw(Graphics graphics){
        if(flag){
            //setting parameters fot the food block.
            graphics.setColor(Color.red);
            graphics.fillOval(fx,fy,unit,unit);
            //setting the params for the snake
            for(int i =0; i<body;i++){

                //for head
                if(i==0){
                    graphics.setColor(Color.green);
                    graphics.fillRect(xSnake[i],ySnake[i],unit,unit);
               //for tail
                }else{
                    graphics.setColor(Color.orange);
                    graphics.fillRect(xSnake[i],ySnake[i],unit,unit);
                }
            }
            //drawing the score
            graphics.setColor(Color.blue);
            graphics.setFont(new Font("Comic Sans",Font.BOLD,40));
            FontMetrics f = getFontMetrics(graphics.getFont());

            //string starting position in x and y
            graphics.drawString("SCORE: "+score,(width-f.stringWidth("SCORE: "+score))/2,graphics.getFont().getSize());
        }else{
            game_over(graphics);
        }
    }
    public void game_over(Graphics graphics){
        //Score on game over screen
        graphics.setColor(Color.RED);
        graphics.setFont(new Font("Comic Sans",Font.BOLD,40));
        FontMetrics f = getFontMetrics(graphics.getFont());
        graphics.drawString("SCORE: "+score,(width-f.stringWidth("SCORE: "+score))/2,graphics.getFont().getSize());

        //game over string
        graphics.setColor(Color.RED);
        graphics.setFont(new Font("Comic Sans",Font.BOLD,80));
        FontMetrics f2 = getFontMetrics(graphics.getFont());
        graphics.drawString("Game Over ",(width-f2.stringWidth("Game Over"))/2,height/2);

        //prompt to restart
        graphics.setColor(Color.BLUE);
        graphics.setFont(new Font("Comic Sans",Font.BOLD,40));
        FontMetrics f3 = getFontMetrics(graphics.getFont());
        graphics.drawString("Press R to Replay",(width-f3.stringWidth("Press R to Replay"))/2,height/2-180);


    }
    public void move(){
        //updating the body
        for(int i=body; i>0;i--){
            xSnake[i]=xSnake[i-1];
            ySnake[i]=ySnake[i-1];
        }
        //update the head
        switch (dir){
            case 'U':
                ySnake[0]=ySnake[0]-unit;
                break;
            case 'D':
                ySnake[0]=ySnake[0]+unit;
                break;
            case 'L':
                xSnake[0]=xSnake[0]-unit;
                break;
            case 'R':
                xSnake[0]=xSnake[0]+unit;
                break;
        }
    }
    public void food(){
        if((xSnake[0]==fx)&& (ySnake[0])==fy){
            body++;
            score++;
            spawFood();
        }
    }
    public class Key extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(dir!='R'){
                        dir='L';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(dir!='D'){
                        dir='U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(dir!='U'){
                        dir='D';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(dir!='L'){
                        dir='R';
                    }
                    break;
                case KeyEvent.VK_R:
                    if(!flag){
                        score=0;
                        body=3;
                        dir='R';
                        Arrays.fill(xSnake,0);
                        Arrays.fill(ySnake,0);
                        game_start();
                    }
            }
        }

    }
    public void actionPerformed(ActionEvent args0) {
        if(flag){
            move();
            food();
            checkHit();

        }
        repaint();

    }


}
