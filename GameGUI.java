import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// With added Swing functionaility
// This is just ane example
// There are more elegant solutions!!!
/**
 * Write a description of class GameGUI here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GameGUI extends JFrame
{
    Game g1; 
    private Parser parser;
    private boolean finished;
    
    private Container contentPane;
    private JPanel p = new JPanel(new GridBagLayout());
    private GridBagConstraints c = new GridBagConstraints();
    //private GridBagConstraints directionControls = new GridBagConstraints();

    public GameGUI()
    {
        g1 = new Game();
        finished = false;
        parser = new Parser();
        //setup();
        
        addGameControls();
        
        g1.play();
    }

    public void setup()
    {
        contentPane = this.getContentPane();
        
        // Add a label to tell the user what to do ...
        final JLabel label1 = new JLabel("Just type your command here!!");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 5;
        p.add(label1,c);
        
        // Now add a box that the user can enter a number into
        final JTextField field1 = new JTextField("");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 4;
        p.add(field1,c);
        
        // Now add a button that the user can proess to submit their guess
        // This will require an event handler
        JButton b1 = new JButton("Perform command");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 4;
        
        // Now add an event listener for the button. To save having to 
        // redesign the Game class for this simple version, we will just pass the text to the
        // Parser class that already exists
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) 
            {
                // Fetch the command from field1 ...
                String commandText = field1.getText();
                Command command = parser.getCommandFromText(commandText);
                finished = g1.processCommand(command);
                if (finished == true)
                // Game is over
                {
                    System.out.println("Thank you for playing.  Good bye.");
                    System.exit(0);
                }

            }
        });
        p.add(b1,c);
        
        // Finally add the Panel to the Frame and set thinsg visible ...
        this.add(p);
        this.pack();
        this.setVisible(true);
        
        
    }
    
    private void addGameControls()
    {
        JButton button;
        p.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        
        button = new JButton("Button 1");
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        p.add(button, c);

        button = new JButton("Button 2");
        c.gridx = 1;
        c.gridy = 0;
        p.add(button, c);

        button = new JButton("Button 3");
        c.gridx = 2;
        c.gridy = 0;
        p.add(button, c);

        button = new JButton("Long-Named Button 4");
        c.ipady = 40;      //make this component tall
        c.weightx = 0.0;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 1;
        p.add(button, c);

        button = new JButton("5");
        c.ipady = 0;       //reset to default
        c.weighty = 1.0;   //request any extra vertical space
        c.anchor = GridBagConstraints.PAGE_END; //bottom of space
        c.insets = new Insets(10,0,0,0);  //top padding
        c.gridx = 1;       //aligned with button 2
        c.gridwidth = 2;   //2 columns wide
        c.gridy = 2;       //third row
        p.add(button, c);
                
        // Finally add the Panel to the Frame and set thinsg visible ...
        this.add(p);
        this.pack();
        this.setVisible(true);
    }
}
