package GUIPackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class ClickListener implements ActionListener
{
    public  int times = 0;

    public void actionPerformed(ActionEvent event)
    {
        GUI gui = new GUI();
        gui.showEditor();
    }
}