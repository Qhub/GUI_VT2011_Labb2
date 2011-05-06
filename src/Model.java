import java.util.ArrayList;
import javax.swing.JButton;

public class Model 
{

	private String[][] saveResults = new String[3][3];
	private Layout_Viewer theViewer;
	private int turn, row, column;
	private String playerOne, playerTwo, symbol;
	private Boolean clickable, winner;
	private JButton theButton;
	private ArrayList<Observer> observerList;
	
	public Model(Layout_Viewer _viewer)
	{
		turn = 1;
		playerOne = "X";
		playerTwo = "O";
		clickable = true;
		winner = false;
		theViewer = _viewer;
		observerList = new ArrayList<Observer>();
	}

	public void initiateArray(int _row, int _column, String _name)
	{
		saveResults[_row][_column] = _name;
	}
	
	public void update(String _name)
	{
		if(turn == 10)
		{
			clickable = false;
		}
		else
		{
			int tempNumber = turn % 2;
			if(tempNumber == 0)
			{
				for(int rows = 0; rows < 3; rows++)
				{
					for(int columns = 0; columns < 3; columns++)
					{
						if(saveResults[rows][columns].equals(_name))
						{
							System.out.println("Found a match!");
							saveResults[rows][columns] = playerTwo;
							System.out.println("Test: " + saveResults[rows][columns]);
							setSymbol(playerTwo);
							setRow(rows);
							setColumn(columns);
							if(turn >= 5)
							{
								checkForWinner(playerTwo);
							}
							theViewer.Notify();
						}
					}
				}
				turn++;
			}
			else
			{
				for(int rows = 0; rows < 3; rows++)
				{
					for(int columns = 0; columns < 3; columns++)
					{
						if(saveResults[rows][columns].equals(_name))
						{
							saveResults[rows][columns] = playerOne;
							System.out.println("Test: " + saveResults[rows][columns]);
							setSymbol(playerOne);
							setRow(rows);
							setColumn(columns);
							if(turn >= 5)
							{
								checkForWinner(playerOne);
							}
							notifyObservers();
						}
					}
				}
				turn++;
			}
			if(turn == 10 && winner == false)
			{
				clickable = false;
				theViewer.gameOver();
			}
		}
	}

	private void checkForWinner(String _player) 
	{
		if(CheckRows(_player) || CheckColumns(_player) || CheckDiagonal(_player))
		{
			winner = true;
			clickable = false;
			System.out.println("Congrats, " + _player);
		}
	}
	
	private boolean CheckDiagonal(String _player) 
	{
		if(saveResults[1][1] == _player)
		{
			if(saveResults[0][0] == _player && saveResults[2][2] == _player)
			{
				winner = true;
				clickable = false;
				System.out.println("Win! (Diagonal)");
			}
			else if(saveResults[0][2] == _player && saveResults[2][0] == _player)
			{
				winner = true;
				clickable = false;
				System.out.println("Win! (Diagonal)");
			}
		}
		return winner;
	}

	private boolean CheckColumns(String _player) 
	{
		for(int column = 0; column < 3; column++)
		{
			if(saveResults[0][column] == _player && saveResults[1][column] == _player && saveResults[2][column] == _player)
			{
				winner = true;
				clickable = false;
				System.out.println("Win! (Column)");
			}
		}
		return winner;
	}

	private boolean CheckRows(String _player) 
	{
		for(int row = 0; row < 3; row++)
		{
			if(saveResults[row][0] == _player && saveResults[row][1] == _player && saveResults[row][2] == _player)
			{
				winner = true;
				clickable = false;
			}
		}
		return winner;
	}

	public Boolean Winner()
	{
		return winner;
	}
	
	private void setSymbol(String _symbol)
	{
		symbol = _symbol;
	}
	
	public String getSymbol()
	{
		return symbol;
	}
	
	private void setRow(int _row)
	{
		row = _row;
	}
	
	public int getRow()
	{
		return row;
	}
	
	private void setColumn(int _column)
	{
		column = _column;
	}
	
	public int getColumn()
	{
		return column;
	}
	
	public void addObserver(Observer o)
	{
		observerList.add(o);
	}
	public void notifyObservers()
	{
		for(int i = 0; i < observerList.size(); i++)
		{
			observerList.get(i).Notify();
		}
	}
}
