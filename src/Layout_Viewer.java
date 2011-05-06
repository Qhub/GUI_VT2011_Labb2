import java.awt.*;

import javax.swing.*;

public class Layout_Viewer extends JPanel implements Observer
{
	
	private JFrame frame;
	private GameWindow windowOne, windowTwo;
	private Controller controller;
	private Model theModel;
	private JButton[][] buttons1 = new JButton[3][3];
	private JButton[][] buttons2 = new JButton[3][3];
	private JButton restartButton, quitButton;
	private JLabel playersText;
	private JTextField infoBox;
	private JPanel players, info, buttons;
	
	public Layout_Viewer()
	{
		super();
		frame = new JFrame();
		frame.setSize(300, 150);
		frame.setLocation(425, 25);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.add(this);
		
		theModel = new Model(this);
		controller = new Controller(theModel);
		
		players = new JPanel();
		players.setPreferredSize(new Dimension(300, 30));
		frame.add(players, BorderLayout.NORTH);
		playersText = new JLabel("Player One is X, Player Two is O");
		players.add(playersText);
		
		info = new JPanel();
		info.setPreferredSize(new Dimension(300, 40));
		frame.add(info, BorderLayout.CENTER);
		infoBox = new JTextField(20);
		infoBox.setText("Player X Starts");
		info.add(infoBox);
		
		buttons = new JPanel();
		buttons.setPreferredSize(new Dimension(300, 50));
		frame.add(buttons, BorderLayout.SOUTH);
		restartButton = new JButton("Restart");
		restartButton.addActionListener(controller);
		buttons.add(restartButton);
		quitButton = new JButton("Quit");
		quitButton.addActionListener(controller);
		buttons.add(quitButton);
		
		windowOne = new GameWindow(50, 200, buttons1);
		windowTwo = new GameWindow(700, 200, buttons2);
		theModel.addObserver(this);
	}
	
	private class GameWindow extends JPanel
	{
		private JFrame gameFrame;
		
		public GameWindow(int _x, int _y, JButton[][] _buttons)
		{
			super(new GridLayout(3,3));
			gameFrame = new JFrame();
			gameFrame.setSize(400, 400);
			gameFrame.setDefaultCloseOperation(gameFrame.EXIT_ON_CLOSE);
			gameFrame.setResizable(false);
			gameFrame.setVisible(true);
			gameFrame.setLocation(_x, _y);
			gameFrame.add(this);
			CreateButtons(this, _buttons);
		}
		
		private void CreateButtons(GameWindow gameWindow, JButton[][] _buttons)
		{
			JButton aButton;
			int buttonNumber = 1;
			int theRow = 0;
			for(int rows = 80; rows < 250; rows += 80)
			{
				int theColumn = 0;
				for(int columns = 80; columns < 250; columns += 80)
				{
					aButton = new JButton();
					aButton.setName(Integer.toString(buttonNumber));
					aButton.setText("");
					gameWindow.add(aButton);
					aButton.addActionListener(controller);
					_buttons[theRow][theColumn] = aButton;
					theModel.initiateArray(theRow, theColumn, aButton.getName());
					buttonNumber++;
					theColumn++;
				}
				theRow++;
			}
		}
	}

	@Override
	public void Notify() 
	{
		update(theModel.getSymbol(), theModel.getRow(), theModel.getColumn());
	}
	
	private void update(String _symbol, int _row, int _column) 
	{
		
		JButton tempButton = buttons1[_row][_column];
		tempButton.setText(_symbol);
		
		JButton tempButton2 = buttons2[_row][_column];
		tempButton2.setText(_symbol);
		
		if(theModel.Winner())
		{
			gameOver();
			infoBox.setText("Player " + _symbol + " won! Game Over!");
		}
	}
	
	public void gameOver()
	{
		for(int row = 0; row < 3; row++)
		{
			for(int column = 0; column < 3; column++)
			{
				JButton tempButton = buttons1[row][column];
				tempButton.setEnabled(false);
				JButton tempButton2 = buttons2[row][column];
				tempButton2.setEnabled(false);
			}
		}
		infoBox.setText("Game Over!");
	}

}
