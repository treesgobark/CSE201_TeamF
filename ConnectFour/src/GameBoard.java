
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;

@SuppressWarnings("serial")
public class GameBoard extends JFrame implements ActionListener {
	private JButton[][] buttons = new JButton[7][6];
	private JButton autoOnceButton = new JButton("Auto-Game");
	private JButton auto10Button = new JButton("Auto-Game x10");
	private JButton autoCustomButton = new JButton("Auto-Game x#");
	private JButton clearButton = new JButton("Clear");
	private JLabel barLabel = new JLabel();
	private int[][] iconColor = new int[7][6];
	private int iconCount = 1;
	private int winner = 0;
	@SuppressWarnings("unused")
	private int redWinCount = 0;
	@SuppressWarnings("unused")
	private int yellowWinCount = 0;
	@SuppressWarnings("unused")
	private int tieCount = 0;
	private ImageIcon redCircle = new ImageIcon(getClass().getResource("/resources/Red Circle.png"));
	private ImageIcon yellowCircle = new ImageIcon(getClass().getResource("/resources/Yellow Circle.png"));

	public static void main(String[] args) {
		GameBoard cb = new GameBoard();
		cb.setVisible(true);
		cb.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

	public GameBoard() {
		setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
		constructComponents();
	}

	public static final int FRAME_WIDTH = 720;
	public static final int FRAME_HEIGHT = 720;

	public void constructComponents() {
		for (int j = 0; j < buttons[0].length; j++)
			for (int i = 0; i < buttons.length; i++) {
				JButton button = new JButton();
				button.setBackground(Color.BLUE);
				buttons[i][j] = button;
			}

		autoOnceButton.addActionListener(this);
		auto10Button.addActionListener(this);
		autoCustomButton.addActionListener(this);
		clearButton.addActionListener(this);

		JToolBar toolbar = new JToolBar();
		toolbar.setFloatable(false);
		toolbar.add(autoOnceButton);
		toolbar.addSeparator();
		toolbar.add(auto10Button);
		toolbar.addSeparator();
		toolbar.add(autoCustomButton);
		toolbar.addSeparator();
		toolbar.add(clearButton);
		toolbar.addSeparator();
		toolbar.add(barLabel);

		JPanel panel = new JPanel(new GridLayout(6, 7));
		for (int j = 0; j < buttons[0].length; j++)
			for (int i = 0; i < buttons.length; i++)
				panel.add(buttons[i][j]);

		for (int j = 0; j < buttons[0].length; j++)
			for (int i = 0; i < buttons.length; i++)
				buttons[i][j].addActionListener(this);

		add(panel);
		add(toolbar, BorderLayout.PAGE_START);
		setTitle("8 Queens Problem");
//		barLabel.setText("Red's wins: " + redWinCount + ". Yellow's Wins: " + yellowWinCount + ". Ties: " + tieCount + ".");
		barLabel.setText("Red's Turn");
	}

	public void actionPerformed(ActionEvent e) {
		for (int j = 0; j < buttons[0].length; j++)
			for (int i = 0; i < buttons.length; i++)
				if (e.getSource() == buttons[i][j]) {
					if (buttonPress(i)) {
						if (!checkForWin(0)) {
							compMove();
						}
						checkForResolution();
					}
				}
		if (e.getSource() == clearButton) {
			resetBoard();
		} else if (e.getSource() == autoOnceButton) {
			autoGame();
		} else if (e.getSource() == auto10Button) {
			for (int i = 0; i < 10; i++)
				autoGame();
		} else if (e.getSource() == autoCustomButton) {
			int number = 0;
			try {
			number = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter the number of games you wish to automate. (Less than or equal to 10,000)"));
			} catch (NumberFormatException e1) {
				JOptionPane.showMessageDialog(this, "That's not a number.");
			}
			if (number <= 10000)
				for (int i = 0; i < number; i++)
					autoGame();
		}
	}

	public boolean buttonPress(int x) {
		boolean loop = true;
		boolean yIsSafe = true;
		int y = buttons[0].length - 1;
		while (loop) {
			if (y >= 0) {
				if (iconColor[x][y] > 0)
					y--;
				else
					loop = false;
			} else {
				loop = false;
				yIsSafe = false;
			}
		}

		if (yIsSafe) {
//			resetBackgrounds();
//			buttons[x][y].setBackground(Color.GREEN);
			if (iconCount == 1) {
				iconColor[x][y] = iconCount;
				buttons[x][y].setIcon(redCircle);
				iconCount++;
				barLabel.setText("Yellow's Turn");
			} else if (iconCount == 2) {
				iconColor[x][y] = iconCount;
				buttons[x][y].setIcon(yellowCircle);
				iconCount--;
				barLabel.setText("Red's Turn");
			} else {
				System.out.println("U DID SOMETHING WRONG");
			}
		}

		return yIsSafe;
	}

	public void compMove() {
		// buttonPress((int)(Math.random()*7));
//		int canWin = -1;
//		int canPreventWin = -1;
//		int canPreventGap = -1;
//		int canPreventAdjacent = -1;
//		int canCenter = -1;
//		int canCorner = -1;
//		int canEdge = -1;

		int firstY = -1;
		int opponentCount = 0;
		if (iconCount == 1) opponentCount = 2;
		else if (iconCount == 2) opponentCount = 1;
		
		int[] movePriorities = new int[buttons.length];

		for (int x = 0; x < buttons.length; x++) {
			boolean yIsSafe = true;
			boolean loop = true;
			int y = buttons[0].length - 1;
			while (loop) {
				if (y >= 0) {
					if (iconColor[x][y] > 0)
						y--;
					else
						loop = false;

				} else {
					loop = false;
					yIsSafe = false;
				}
			}

			if (yIsSafe) {

				if (x == 0)
					firstY = y;

				int temp = iconColor[x][y];

				iconColor[x][y] = iconCount;
				if (checkForWin(0))
					if (winner == iconCount)
						movePriorities[x] += 1024;

				iconColor[x][y] = opponentCount;
				if (checkForWin(0))
					if (winner == opponentCount)
						movePriorities[x] += 512;

				iconColor[x][y] = temp;

				if (firstY >= 0) {
					if (checkForGap(x, firstY, 0)) {
						movePriorities[x] += 128;
						movePriorities[x+2] += 128;
						movePriorities[x+4] += 128;
					}

					if (checkForAdjacent(x, firstY, 0)) {
						movePriorities[x+1] += 128;
						movePriorities[x+4] += 128;
					}
				}

				if (x == buttons.length / 2)
					movePriorities[x] += 64;
				
				else if (x == 0 || x == buttons.length - 1)
					movePriorities[x] += 32;
				else
					movePriorities[x] += 16;

			}
		}
		
		ArrayList<Integer> indexes = new ArrayList<>();
		int highest = 0;
		for (int i = 0; i < movePriorities.length; i++) {
			if (movePriorities[i] > highest) {
				indexes.clear();
				indexes.add(i);
				highest = movePriorities[i];
			} else if (movePriorities[i] == highest)
				indexes.add(i);
		}
		
		int randomChoice = (int) (Math.random()*indexes.size());
		
		buttonPress(indexes.get(randomChoice));
		
		// System.out.println("canWin == " + canWin);
		// System.out.println("canPreventWin == " + canPreventWin);
		// System.out.println("canPreventGap == " + canPreventGap);
		// System.out.println("canPreventAdjacent == " + canPreventAdjacent);
		// System.out.println("canCenter == " + canCenter);
		// System.out.println("canCorner == " + canCorner);
		// System.out.println("canEdge == " + canEdge + "\n");

//		if (canWin != -1)
//			buttonPress(canWin);
//		else if (canPreventWin != -1)
//			buttonPress(canPreventWin);
//		else if (canPreventGap != -1)
//			buttonPress(canPreventGap);
//		else if (canPreventAdjacent != -1)
//			buttonPress(canPreventAdjacent);
//		else if (canCenter != -1)
//			buttonPress(canCenter);
//		else if (canCorner != -1)
//			buttonPress(canCorner);
//		else
//			buttonPress(canEdge);
	}
	
	public void autoGame() {
		while(!(checkForTie() || checkForWin(0))) {
			compMove();
		}
		if (checkForWin(0)) {
			if (winner == 1) {
				redWinCount++;
				resetBoard();
			}
			if (winner == 2) {
				yellowWinCount++;
				resetBoard();
			}
		} else if (checkForTie()) {
			tieCount++;
			resetBoard();
		}
//		barLabel.setText("Red's wins: " + redWinCount + ". Yellow's Wins: " + yellowWinCount + ". Ties: " + tieCount + ".");
	}

	public void resetBoard() {
		resetBackgrounds();
		barLabel.setText("Red's Turn");
		iconCount = 1;
		iconColor = new int[buttons.length][buttons[0].length];
		for (int j = 0; j < buttons[0].length; j++) {
			for (int i = 0; i < buttons.length; i++) {
				buttons[i][j].setIcon(null);
			}
		}
	}
	
	public void resetBackgrounds() {
		for (int j = 0; j < buttons[0].length; j++) {
			for (int i = 0; i < buttons.length; i++) {
				buttons[i][j].setBackground(Color.BLUE);
			}
		}
	}

	public boolean checkForTie() {
		for (int j = 0; j < iconColor[0].length; j++) {
			for (int i = 0; i < iconColor.length; i++) {
				if (iconColor[i][j] == 0)
					return false;
			}
		}
		return true;
	}
	
	public void checkForResolution() {
		if (checkForWin(0)) {
			if (winner == 1) {
				redWinCount++;
				JOptionPane.showMessageDialog(this, "Red Wins!");
				resetBoard();
			}
			if (winner == 2) {
				yellowWinCount++;
				JOptionPane.showMessageDialog(this, "Yellow Wins!");
				resetBoard();
			}
		} else if (checkForTie()) {
			tieCount++;
			JOptionPane.showMessageDialog(this, "It's a tie because you both suck");
			resetBoard();
		}
//		barLabel.setText("Red's wins: " + redWinCount + ". Yellow's Wins: " + yellowWinCount + ". Ties: " + tieCount + ".");
	}

	public boolean checkForWin(int pos) {
		if (pos >= buttons.length * buttons[0].length)
			return false;

		int x = pos % buttons.length;
		int y = pos / buttons.length;

		// System.out.print(pos + ": " + x + ", " + y + ": " + iconColor[x][y]);

		// System.out.print(x + ", ");
		// System.out.println(y);

		if (iconColor[x][y] != 0) {
			if (y - 1 >= 0)
				if (iconColor[x][y] == iconColor[x][y - 1])
					if (checkForWin(x + (y - 1) * buttons.length, 1, 0))
						return true;

			if (x + 1 < buttons.length && y - 1 >= 0)
				if (iconColor[x][y] == iconColor[x + 1][y - 1])
					if (checkForWin((x + 1) + (y - 1) * buttons.length, 1, 1))
						return true;

			if (x + 1 < buttons.length)
				if (iconColor[x][y] == iconColor[x + 1][y])
					if (checkForWin((x + 1) + y * buttons.length, 1, 2))
						return true;

			if (x + 1 < buttons.length && y + 1 < buttons[0].length)
				if (iconColor[x][y] == iconColor[x + 1][y + 1])
					if (checkForWin((x + 1) + (y + 1) * buttons.length, 1, 3))
						return true;

			if (y + 1 < buttons[0].length)
				if (iconColor[x][y] == iconColor[x][y + 1])
					if (checkForWin(x + (y + 1) * buttons.length, 1, 4))
						return true;
		}

		// System.out.println();

		return checkForWin(pos + 1);
	}

	public boolean checkForWin(int pos, int inARow, int direction) {
		if (pos >= buttons.length * buttons[0].length)
			return false;

		int x = pos % buttons.length;
		int y = pos / buttons.length;

		if (inARow == 3) {
			winner = iconColor[x][y];
			return true;
		}

		// System.out.print(pos + ": " + x + ", " + y + " ");

		if (iconColor[x][y] != 0) {
			if (direction == 0)
				if (y - 1 >= 0)
					if (iconColor[x][y] == iconColor[x][y - 1])
						return checkForWin(x + (y - 1) * buttons.length, inARow + 1, 0);

			if (direction == 1)
				if (x + 1 < buttons.length && y - 1 >= 0)
					if (iconColor[x][y] == iconColor[x + 1][y - 1])
						return checkForWin((x + 1) + (y - 1) * buttons.length, inARow + 1, 1);

			if (direction == 2)
				if (x + 1 < buttons.length)
					if (iconColor[x][y] == iconColor[x + 1][y])
						return checkForWin((x + 1) + y * buttons.length, inARow + 1, 2);

			if (direction == 3)
				if (x + 1 < buttons.length && y + 1 < buttons[0].length)
					if (iconColor[x][y] == iconColor[x + 1][y + 1])
						return checkForWin((x + 1) + (y + 1) * buttons.length, inARow + 1, 3);

			if (direction == 4)
				if (y + 1 < buttons[0].length)
					if (iconColor[x][y] == iconColor[x][y + 1])
						return checkForWin(x + (y + 1) * buttons.length, inARow + 1, 4);
		}

		return false;
	}

	public boolean checkForGap(int x, int y, int inARow) {
		// System.out.println("I checked for a Fork: " + x + ", " + y + ": " +
		// inARow);
		if (inARow > 4)
			return true;
		if (x < buttons.length) {
			// System.out.println("Passed the first check");

			if (inARow == 0 || inARow == 4) {
				// System.out.println("Passed the second check");
				if (iconColor[x][y] == 0) {
					// System.out.println("Passed the third check");
					if (checkForGap(x + 1, y, inARow + 1))
						return true;
				}
			}

			if (inARow == 2) {
				// System.out.println("Passed the second check");

				if (iconCount == 1)
					if (iconColor[x][y] != 1) {
						// System.out.println("Passed the third check");
						if (checkForGap(x + 1, y, inARow + 1))
							return true;
					}

				if (iconCount == 2)
					if (iconColor[x][y] != 2) {
						// System.out.println("Passed the third check");
						if (checkForGap(x + 1, y, inARow + 1))
							return true;
					}
			}

			if (inARow == 1 || inARow == 3) {
				// System.out.println("Passed the second check");

				if (iconCount == 1)
					if (iconColor[x][y] == 2) {
						// System.out.println("Passed the third check\n");
						if (checkForGap(x + 1, y, inARow + 1))
							return true;
					}

				if (iconCount == 2)
					if (iconColor[x][y] == 1) {
						// System.out.println("Passed the third check\n");
						if (checkForGap(x + 1, y, inARow + 1))
							return true;
					}
			}
		}
		return false;
	}

	public boolean checkForAdjacent(int x, int y, int inARow) {
		// System.out.println("I checked for an adjacent case: " + x + ", " + y
		// + ": " + inARow);
		if (inARow > 5)
			return true;
		if (x < buttons.length) {
			// System.out.println("Passed the first check");

			if (inARow == 0 || inARow == 1 || inARow == 4 || inARow == 5) {
				// System.out.println("Passed the second check");
				if (iconColor[x][y] == 0) {
					// System.out.println("Passed the third check");
					if (checkForAdjacent(x + 1, y, inARow + 1))
						return true;
				}
			}

			if (inARow == 2 || inARow == 3) {
				// System.out.println("Passed the second check");
				if (iconColor[x][y] == 1) {
					// System.out.println("Passed the third check\n");
					if (checkForAdjacent(x + 1, y, inARow + 1))
						return true;
				}
			}
		}
		return false;
	}

}
