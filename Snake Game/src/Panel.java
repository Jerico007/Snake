import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyAdapter;
import java.util.Arrays;
import java.util.Random;

public class Panel extends JPanel implements ActionListener {

    //Width of the screen
    static final int width = 800;
    static final int height = 600;
    //Unit of each block will be 25
    int unit = 25;

    //To count score of the game
    static int score;
    static boolean flag = false;

    //Array for drawing body of the snake
   static int [] xsnake = new int[768];
   static  int  [] ysnake = new int[768];

    //Direction variable
    static char dir = 'R';

    //Timer
    Timer timer;

    //Random to get random co-ordinates
    Random random ;

    //To get x and y co-ordinates of the inner panel components
    int fx , fy ;

    //To make snake length 3 at the beginning of the game
   static int length = 3;
    Panel()
    {
        this.setPreferredSize(new Dimension(width,height));
        this.setBackground(Color.BLUE);
        random = new Random();
        this.setFocusable(true);
        this.addKeyListener(new key());
        gamestart();
    }

    public void gamestart()
    {
          snakeFood();
          flag = true;

          timer = new Timer(95,this);
          timer.start();
    }

    //To assign random food particles at x and y co-ordinates
    public  void snakeFood(){

        fx = random.nextInt(width / unit) * unit;

        fy = random.nextInt(height/unit) * unit;



    }

    public  void paintComponent(Graphics graphics)
    {
        super.paintComponent(graphics);
        draw(graphics);
    }

    public void draw(Graphics graphics)
    {
        //Methode to draw food particles if the game is running
        if(flag)
        {
            //To set the color of food
            graphics.setColor(Color.orange);
            //To fill the area of food particles
            graphics.fillOval(fx,fy,25,25);

            //To fill the snake Body
            for(int i = 0 ; i < length ; i++)
            {
                graphics.setColor(Color.white);
                graphics.fillRect(xsnake[i],ysnake[i], unit,unit );
            }

            //To Display the score
            graphics.setColor(Color.YELLOW);
            graphics.setFont(new Font("San sarif",Font.BOLD , 20));
            FontMetrics fme = getFontMetrics(graphics.getFont());
            graphics.drawString("Score : " + score , ((width-fme.stringWidth("Score : " + score))/2) , graphics.getFont().getSize());
        }
        else {
            //To Display the final score
            graphics.setColor(Color.white);
            graphics.setFont(new Font("Monospaced",Font.BOLD , 45));
            FontMetrics fme = getFontMetrics(graphics.getFont());
            graphics.drawString("Score : " + score , ((width-fme.stringWidth("Score : " + score))/2) , graphics.getFont().getSize());
           //To Display Game Over text

            graphics.setColor(Color.YELLOW);
            graphics.setFont(new Font("Monospaced",Font.BOLD , 40));
             fme = getFontMetrics(graphics.getFont());
            graphics.drawString("Game Over" , ((width-fme.stringWidth("Game Over"))/2) , height/2);
            //To Display Replay button

            graphics.setColor(Color.pink);
            graphics.setFont(new Font("Monospaced",Font.BOLD , 25));
             fme = getFontMetrics(graphics.getFont());
            graphics.drawString("Press R to replay"  , ((width-fme.stringWidth("Press R to replay"))/2) , height/2+150);
        }
    }

    public void move(){
        for (int i = length; i > 0 ; i--) {
            xsnake[i] = xsnake[i-1];
            ysnake[i] = ysnake[i-1];
        }

        switch (dir) {
            case 'R' -> xsnake[0] = xsnake[0] + unit;
            case 'L' -> xsnake[0] = xsnake[0] - unit;
            case 'U' -> ysnake[0] = ysnake[0] - unit;
            case 'D' -> ysnake[0] = ysnake[0] + unit;
        }
    }

    public void foodEaten()
    {
        if((xsnake[0] == fx) && (ysnake[0] == fy) )
        {
            length++;
            score++;
            snakeFood();
        }
    }

    public  void checkHit(){

        //To check hit with its boundaries
        if(ysnake[0] < 0)
        {
            flag = false;
        }
        if(ysnake[0] > 600)
        {
            flag = false;
        }
        if(xsnake[0] < 0)
        {
            flag = false;
        }
        if(xsnake[0] > 800)
        {
            flag = false;
        }

        //To check hit with its Own body
        for(int i = length ; i > 0 ; i--)
        {
            if ((xsnake[0] == xsnake[i]) && (ysnake[0] == ysnake[i])) {
                flag = false;
                break;
            }
        }

        if(!flag)
            timer.stop();

    }
    public  class key extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
          switch (e.getKeyCode())
          {
              case KeyEvent.VK_LEFT :
                  if(dir != 'R')
                  {
                      dir = 'L';
                  }
                  break;
              case KeyEvent.VK_RIGHT :
                  if(dir != 'L')
                  {
                      dir = 'R';
                  }
                  break;
              case KeyEvent.VK_UP :
                  if(dir != 'D')
                  {
                      dir = 'U';
                  }
                  break;
              case KeyEvent.VK_DOWN :
                  if(dir != 'U')
                  {
                      dir = 'D';
                  }
                  break;
              case KeyEvent.VK_R :
                 dir = 'R';
                 score = 0;
                 length = 3;
                  Arrays.fill(xsnake , 0);
                  Arrays.fill(ysnake , 0);
                  gamestart();

          }
        }

    }

    public void actionPerformed(ActionEvent evt)
    {
        if(flag)
        {
            move();
            foodEaten();
            checkHit();
        }

        repaint();
    }
}
