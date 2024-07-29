import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class App extends JPanel implements ActionListener{

    public static void main(String[] args) throws Exception {
        new App();
    
    }

    

    JFrame f = new JFrame("G SHIVA SHIVA");
    JPanel p1 = new JPanel();
    public App (){
        int boardwidth = 360;
        int boardheight = 600;
        
        f.setSize(boardwidth,boardheight);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setResizable(false);
        
        JButton b1 = new JButton("Press Space TO Play The Game");
        p1.setBorder(BorderFactory.createEmptyBorder(-10, -10, -10, -10));
        p1.setLayout(new GridLayout(0,1,144,4));
        b1.addActionListener(this);
        f.add(p1);
        p1.add(b1);
        
        
        
        f.setVisible(true);
        
        
         
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {    
        Game game = new Game();
        f.add(game);
        f.pack();
        p1.setVisible(false);
        return;
        
    }

}
