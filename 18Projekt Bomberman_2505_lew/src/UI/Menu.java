package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
//swing dein ding
public class Menu extends JFrame implements WindowListener {

    /**
     * main menu and starting point for the application. only job is to
     * have functioning start and quit button.
     * <p>
     * ToDo: design! (low priority)
     */

    JButton start, exit;

    public Menu(String title) {
        JPanel mainmenu = new JPanel();

        //initial settings for frame Menu layout
        setTitle(title);
        addWindowListener(this);
        setSize(1280, 720);
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);
        add(mainmenu, BorderLayout.CENTER);

        //initial settings for Panel main Menu layout
        mainmenu.setLayout(null);
        mainmenu.setVisible(true);

        //initial settings for button layout
        start = new JButton("Start!");
        exit = new JButton("Exit!");

        start.setBounds(465, 285, 150, 50);

        exit.setBounds(465, 410, 150, 50);

        mainmenu.add(start);
        mainmenu.add(exit);

        //actionListener for jbuttons
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //removes Menu from frame
                start.setVisible(false);
                start.setVisible(false);

                remove(mainmenu);

                //adds main Panel to frame

                Panel gamepanel = new Panel();
                add(gamepanel);
                gamepanel.addKeyListener(gamepanel);
                gamepanel.requestFocus();

            }
        });

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("User killed program via exit button");
                System.exit(0);

            }
        });
    }

    public static void main(String[] args) {
        new Menu("Projekt Bomberman");
    }


    // overrides for WindowListener

    @Override
    public void windowOpened(WindowEvent e) {

        System.out.println("Window initialised");

    }

    @Override
    public void windowClosing(WindowEvent e) {

        System.out.println("User killed program by windowClosing event");
        System.exit(0);

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
