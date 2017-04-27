import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.FontUIResource;

public class ConnectFourDriver {

	private ConnectFourBoard2 board;
	private int winner;
	private int playerColor;
	private int opponentColor;
	private int playerWinCount;
	private int opponentWinCount;
	private int tieCount;

	public ConnectFourDriver(ConnectFourBoard2 board) {
		this.board = board;
		winner = 0;
		playerColor = 1;
		opponentColor = 2;
		playerWinCount = 0;
		opponentWinCount = 0;
		tieCount = 0;
	}

	public int getTieCount() {
		return tieCount;
	}

	public void setTieCount(int tieCount) {
		this.tieCount = tieCount;
	}
	
	public int getPlayerWinCount() {
		return playerWinCount;
	}

	public void setPlayerWinCount(int playerWinCount) {
		this.playerWinCount = playerWinCount;
	}

	public int getOpponentWinCount() {
		return opponentWinCount;
	}

	public void setOpponentWinCount(int opponentWinCount) {
		this.opponentWinCount = opponentWinCount;
	}

	public int getPlayerColor() {
		return this.playerColor;
	}

	public void setPlayerColor(int playerColor) {
		this.playerColor = playerColor;
	}

	public int getOpponentColor() {
		return this.opponentColor;
	}

	public void setOpponentColor(int opponentColor) {
		this.opponentColor = opponentColor;
	}

	public boolean buttonPress(int x) {
		boolean loop = true;
		boolean yIsSafe = true;
		int y = board.getButtons()[0].length - 1;
		while (loop) {
			if (y >= 0) {
				if (board.getIconColor()[x][y] > 0)
					y--;
				else
					loop = false;
			} else {
				loop = false;
				yIsSafe = false;
			}
		}
		if (yIsSafe) {
			resetBackgrounds();
			board.getButtons()[x][y].setBackground(new Color(0, 0, 155));
			if (board.getIconCount() == 1) {
				board.getIconColor()[x][y] = board.getIconCount();
				board.getButtons()[x][y].setIcon(board.getRedCircle());
				board.setIconCount(2);
			} else if (board.getIconCount() == 2) {
				board.getIconColor()[x][y] = board.getIconCount();
				board.getButtons()[x][y].setIcon(board.getYellowCircle());
				board.setIconCount(1);
			} else {
				System.out.println("U DID SOMETHING WRONG");
			}
		}
		return yIsSafe;
	}

	public void compMove() {
		// buttonPress((int)(Math.random()*7));
		// int canWin = -1;
		// int canPreventWin = -1;
		// int canPreventGap = -1;
		// int canPreventAdjacent = -1;
		// int canCenter = -1;
		// int canCorner = -1;
		// int canEdge = -1;
		int firstY = -1;
		int opponentCount = 0;
		if (board.getIconCount() == 1)
			opponentCount = 2;
		else if (board.getIconCount() == 2)
			opponentCount = 1;

		int[] movePriorities = new int[board.getButtons().length];
		for (int x = 0; x < board.getButtons().length; x++) {
			boolean yIsSafe = true;
			boolean loop = true;
			int y = board.getButtons()[0].length - 1;
			while (loop) {
				if (y >= 0) {
					if (board.getIconColor()[x][y] > 0)
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
				int temp1 = board.getIconColor()[x][y];
				board.getIconColor()[x][y] = board.getIconCount();
				if (checkForWin(0))
					if (winner == board.getIconCount())
						movePriorities[x] += 1024;

				if (y > 0) {
					int temp2 = board.getIconColor()[x][y - 1];
					board.getIconColor()[x][y - 1] = opponentCount;
					if (checkForWin(0))
						if (winner == opponentCount)
							movePriorities[x] -= 512;
					board.getIconColor()[x][y - 1] = temp2;
				}

				board.getIconColor()[x][y] = opponentCount;
				if (checkForWin(0))
					if (winner == opponentCount)
						movePriorities[x] += 512;

				board.getIconColor()[x][y] = temp1;

				if (y >= 0) {
					if (checkForGap(x, y, 0)) {
						movePriorities[x] += 128;
						movePriorities[x + 2] += 128;
						movePriorities[x + 4] += 128;
					}
					if (checkForAdjacent(x, y, 0, 0)) {
						movePriorities[x + 1] += 128;
						movePriorities[x + 4] += 128;
					}
					if (checkForAdjacent(x, y, 0, 1)) {
						movePriorities[x] += 128;
						movePriorities[x + 3] += 128;
					}
				}

				if (x == board.getButtons().length / 2)
					movePriorities[x] += 64;

				else if (x == 0 || x == board.getButtons().length - 1)
					movePriorities[x] += 32;

				else
					movePriorities[x] += 16;

				if (y > 0) {
					int temp2 = board.getIconColor()[x][y - 1];
					board.getIconColor()[x][y - 1] = board.getIconCount();
					if (checkForWin(0))
						if (winner == board.getIconCount())
							movePriorities[x] -= 8;
					board.getIconColor()[x][y - 1] = temp2;
				}
			}
		}

//		for (int x : movePriorities)
//			System.out.print(x + "            ");
//		System.out.println();

		ArrayList<Integer> indexes = new ArrayList<>();
		int highest = -2096;
		for (int i = 0; i < movePriorities.length; i++) {
			if (movePriorities[i] != 0 && movePriorities[i] > highest) {
				indexes.clear();
				indexes.add(i);
				highest = movePriorities[i];
			} else if (movePriorities[i] == highest)
				indexes.add(i);
		}
		
//		System.out.println(indexes.size());

		int randomChoice = (int) (Math.random() * indexes.size());

		buttonPress(indexes.get(randomChoice));

		// System.out.println("canWin == " + canWin);
		// System.out.println("canPreventWin == " + canPreventWin);
		// System.out.println("canPreventGap == " + canPreventGap);
		// System.out.println("canPreventAdjacent == " + canPreventAdjacent);
		// System.out.println("canCenter == " + canCenter);
		// System.out.println("canCorner == " + canCorner);
		// System.out.println("canEdge == " + canEdge + "\n");
		// if (canWin != -1)
		// buttonPress(canWin);
		// else if (canPreventWin != -1)
		// buttonPress(canPreventWin);
		// else if (canPreventGap != -1)
		// buttonPress(canPreventGap);
		// else if (canPreventAdjacent != -1)
		// buttonPress(canPreventAdjacent);
		// else if (canCenter != -1)
		// buttonPress(canCenter);
		// else if (canCorner != -1)
		// buttonPress(canCorner);
		// else
		// buttonPress(canEdge);
	}

	public void autoGame() {
		while (!(checkForTie() || checkForWin(0))) {
			compMove();
		}
		if (checkForWin(0)) {
			if (winner == playerColor) {
				playerWinCount++;
				resetBoard();
			}
			if (winner == opponentColor) {
				opponentWinCount++;
				resetBoard();
			}
		} else if (checkForTie()) {
			tieCount++;
			System.out.println(tieCount);
			resetBoard();
		}
		// barLabel.setText("Red's wins: " + redWinCount + ". Yellow's Wins: " +
		// yellowWinCount + ". Ties: " + tieCount + ".");
	}

	public void resetBoard() {
		board.setIconCount(playerColor);
		resetBackgrounds();
		board.setIconColor(new int[board.getButtons().length][board.getButtons()[0].length]);
		for (int j = 0; j < board.getButtons()[0].length; j++) {
			for (int i = 0; i < board.getButtons().length; i++) {
				board.getButtons()[i][j].setIcon(null);
			}
		}
	}

	public void resetBackgrounds() {
		for (int j = 0; j < board.getButtons()[0].length; j++) {
			for (int i = 0; i < board.getButtons().length; i++) {
				board.getButtons()[i][j].setBackground(Color.BLUE);
			}
		}
	}

	public boolean checkForTie() {
		for (int j = 0; j < board.getIconColor()[0].length; j++) {
			for (int i = 0; i < board.getIconColor().length; i++) {
				if (board.getIconColor()[i][j] == 0)
					return false;
			}
		}
		return true;
	}

	public void checkForResolution() {
		UIManager UI = new UIManager();
		UI.put("OptionPane.background", Color.darkGray);
		UI.put("Panel.background", Color.darkGray);
		UI.put("OptionPane.buttonFont", new FontUIResource(new Font("Serif", Font.PLAIN, 20)));
		UI.put("OptionPane.forground", Color.WHITE);
		UI.put("OptionPane.border", new EmptyBorder(50, 50, 50, 50));
		String t = "<html><font color = \"white\">You Lost!</font></html>";
		if (checkForWin(0)) {
			if (winner == playerColor) {
				playerWinCount++;
				JOptionPane.showMessageDialog(board,
						"<html><font color = \"white\"><font size = \"+100\">You Win!</font></html>");
				resetBoard();
			}

			// "<html><font color = \"white\"You Win!</font></html>"

			if (winner == opponentColor) {
				opponentWinCount++;
				JOptionPane.showMessageDialog(board,
						"<html><font color = \"white\"><font size = \"+100\">You Lost!</font></html>");
				resetBoard();
			}
		} else if (checkForTie()) {
			tieCount++;
			JOptionPane.showMessageDialog(board,
					"<html><font color = \"white\"><font size = \"+100\">It's A Tie!</font></html>");
			resetBoard();
		}
		// barLabel.setText("Red's wins: " + redWinCount + ". Yellow's Wins: " +
		// yellowWinCount + ". Ties: " + tieCount + ".");
	}

	public boolean checkForWin(int pos) {
		if (pos >= board.getButtons().length * board.getButtons()[0].length)
			return false;
		int x = pos % board.getButtons().length;
		int y = pos / board.getButtons().length;
		// System.out.print(pos + ": " + x + ", " + y + ": " +
		// board.getIconColor()x][y]);
		// System.out.print(x + ", ");
		// System.out.println(y);
		if (board.getIconColor()[x][y] != 0) {
			if (y - 1 >= 0)
				if (board.getIconColor()[x][y] == board.getIconColor()[x][y - 1])
					if (checkForWin(x + (y - 1) * board.getButtons().length, 1, 0))
						return true;
			if (x + 1 < board.getButtons().length && y - 1 >= 0)
				if (board.getIconColor()[x][y] == board.getIconColor()[x + 1][y - 1])
					if (checkForWin((x + 1) + (y - 1) * board.getButtons().length, 1, 1))
						return true;
			if (x + 1 < board.getButtons().length)
				if (board.getIconColor()[x][y] == board.getIconColor()[x + 1][y])
					if (checkForWin((x + 1) + y * board.getButtons().length, 1, 2))
						return true;
			if (x + 1 < board.getButtons().length && y + 1 < board.getButtons()[0].length)
				if (board.getIconColor()[x][y] == board.getIconColor()[x + 1][y + 1])
					if (checkForWin((x + 1) + (y + 1) * board.getButtons().length, 1, 3))
						return true;
			if (y + 1 < board.getButtons()[0].length)
				if (board.getIconColor()[x][y] == board.getIconColor()[x][y + 1])
					if (checkForWin(x + (y + 1) * board.getButtons().length, 1, 4))
						return true;
		}
		// System.out.println();
		return checkForWin(pos + 1);
	}

	public boolean checkForWin(int pos, int inARow, int direction) {
		if (pos >= board.getButtons().length * board.getButtons()[0].length)
			return false;
		int x = pos % board.getButtons().length;
		int y = pos / board.getButtons().length;
		if (inARow == 3) {
			winner = board.getIconColor()[x][y];
			return true;
		}
		// System.out.print(pos + ": " + x + ", " + y + " ");
		if (board.getIconColor()[x][y] != 0) {
			if (direction == 0)
				if (y - 1 >= 0)
					if (board.getIconColor()[x][y] == board.getIconColor()[x][y - 1])
						return checkForWin(x + (y - 1) * board.getButtons().length, inARow + 1, 0);
			if (direction == 1)
				if (x + 1 < board.getButtons().length && y - 1 >= 0)
					if (board.getIconColor()[x][y] == board.getIconColor()[x + 1][y - 1])
						return checkForWin((x + 1) + (y - 1) * board.getButtons().length, inARow + 1, 1);
			if (direction == 2)
				if (x + 1 < board.getButtons().length)
					if (board.getIconColor()[x][y] == board.getIconColor()[x + 1][y])
						return checkForWin((x + 1) + y * board.getButtons().length, inARow + 1, 2);
			if (direction == 3)
				if (x + 1 < board.getButtons().length && y + 1 < board.getButtons()[0].length)
					if (board.getIconColor()[x][y] == board.getIconColor()[x + 1][y + 1])
						return checkForWin((x + 1) + (y + 1) * board.getButtons().length, inARow + 1, 3);
			if (direction == 4)
				if (y + 1 < board.getButtons()[0].length)
					if (board.getIconColor()[x][y] == board.getIconColor()[x][y + 1])
						return checkForWin(x + (y + 1) * board.getButtons().length, inARow + 1, 4);
		}
		return false;
	}

	public boolean checkForGap(int x, int y, int inARow) {
		// System.out.println("I checked for a Gap Fork: " + x + ", " + y + ": "
		// +
		// inARow);
		if (inARow > 4)
			return true;
		if (x < board.getButtons().length) {
			// System.out.println("Passed the first check");
			if (inARow == 0 || inARow == 4) {
				// System.out.println("Passed the second check");
				if (board.getIconColor()[x][y] == 0) {
					// System.out.println("Passed the third check");
					if (checkForGap(x + 1, y, inARow + 1))
						return true;
				}
			}
			if (inARow == 2) {
				// System.out.println("Passed the second check");
				if (board.getIconCount() == playerColor)
					if (board.getIconColor()[x][y] != playerColor) {
						// System.out.println("Passed the third check");
						if (checkForGap(x + 1, y, inARow + 1))
							return true;
					}
				if (board.getIconCount() == opponentColor)
					if (board.getIconColor()[x][y] != opponentColor) {
						// System.out.println("Passed the third check");
						if (checkForGap(x + 1, y, inARow + 1))
							return true;
					}
			}
			if (inARow == 1 || inARow == 3) {
				// System.out.println("Passed the second check");
				if (board.getIconCount() == playerColor)
					if (board.getIconColor()[x][y] == opponentColor) {
						// System.out.println("Passed the third check\n");
						if (checkForGap(x + 1, y, inARow + 1))
							return true;
					}
				if (board.getIconCount() == opponentColor)
					if (board.getIconColor()[x][y] == playerColor) {
						// System.out.println("Passed the third check\n");
						if (checkForGap(x + 1, y, inARow + 1))
							return true;
					}
			}
		}
		return false;
	}

	public boolean checkForAdjacent(int x, int y, int inARow, int caseType) {
//		System.out.println("I checked for an adjacent case: " + x + ", " + y + ", case: " + caseType + ": " + inARow);
		if (inARow > 4)
			return true;
		if (caseType == 0) {
			if (x < board.getButtons().length) {
				// System.out.println("Passed the first check");
				if (inARow == 0 || inARow == 1 || inARow == 4) {
					// System.out.println("Passed the second check");
					if (board.getIconColor()[x][y] == 0) {
						// System.out.println("Passed the third check");
						if (checkForAdjacent(x + 1, y, inARow + 1, 0))
							return true;
					}
				}
				if (inARow == 2 || inARow == 3) {
					// System.out.println("Passed the second check");
					if (board.getIconCount() == playerColor)
						if (board.getIconColor()[x][y] == opponentColor) {
							// System.out.println("Passed the third check\n");
							if (checkForAdjacent(x + 1, y, inARow + 1, 0))
								return true;
						}
					
					if (board.getIconCount() == opponentColor)
						if (board.getIconColor()[x][y] == playerColor) {
							// System.out.println("Passed the third check\n");
							if (checkForAdjacent(x + 1, y, inARow + 1, 0))
								return true;
						}
				}
			}
		} else if (caseType == 1) {
			if (x < board.getButtons().length) {
				// System.out.println("Passed the first check");
				if (inARow == 0 || inARow == 3 || inARow == 4) {
					// System.out.println("Passed the second check");
					if (board.getIconColor()[x][y] == 0) {
						// System.out.println("Passed the third check");
						if (checkForAdjacent(x + 1, y, inARow + 1, 1))
							return true;
					}
				}
				if (inARow == 1 || inARow == 2) {
					// System.out.println("Passed the second check");
					if (board.getIconCount() == playerColor)
						if (board.getIconColor()[x][y] == opponentColor) {
							// System.out.println("Passed the third check\n");
							if (checkForAdjacent(x + 1, y, inARow + 1, 1))
								return true;
						}
					
					if (board.getIconCount() == opponentColor)
						if (board.getIconColor()[x][y] == playerColor) {
							// System.out.println("Passed the third check\n");
							if (checkForAdjacent(x + 1, y, inARow + 1, 1))
								return true;
						}
				}
			}
		}
		return false;
	}
}
