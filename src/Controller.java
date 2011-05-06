import java.awt.Component;
import java.awt.event.*;
import javax.swing.*;

public class Controller implements ActionListener
{

	private JButton aButton = new JButton();
	private Model aModel;
	
	public Controller(Model _model)
	{
		this.aModel = _model;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		aButton = (JButton)e.getSource();
		if(aButton.getText() == "")
		{
			aModel.update(aButton.getName());
		}
		else if(aButton.getText() == "Restart")
		{
			// Not implemented, restart the game
		}
		else if(aButton.getText() == "Quit")
		{
			System.exit(1);
		}
	}
	
}
