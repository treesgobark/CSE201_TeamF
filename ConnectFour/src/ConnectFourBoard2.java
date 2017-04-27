import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;

@SuppressWarnings("serial")
public class ConnectFourBoard2 extends JFrame implements ActionListener {
	private JButton[][] buttons;
	private JButton resetButton;
	private JButton instructionsButton;
	private JButton exitButton;
	private JButton scoreButton;
	private JButton autoButton;
	private JLabel barLabel;
	private JLabel turnLabel;
	private int[][] iconColor;
	private int iconCount;
	private ImageIcon redCircle;
	private ImageIcon yellowCircle;
	private ConnectFourDriver driver;

	public ConnectFourBoard2() {
		buttons = new JButton[7][6];
		resetButton = new JButton("Reset");
		instructionsButton = new JButton("Instructions");
		exitButton = new JButton("Exit");
		scoreButton = new JButton("Scoreboard");
		autoButton = new JButton("Auto-Game");
		barLabel = new JLabel();
		turnLabel = new JLabel();
		iconColor = new int[7][6];
		redCircle = new ImageIcon(getClass().getResource("/resources/Red Circle.png"));
		yellowCircle = new ImageIcon(getClass().getResource("/resources/Yellow Circle.png"));
		driver = new ConnectFourDriver(this);
		setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
		TitleScreen();
		colorSelection();
		iconCount = driver.getPlayerColor();
		constructComponents();
	}

	public void TitleScreen() {
		JDialog frame = new JDialog();

		// JOptionPane.showMessageDialog(frame, "Welcome To Connect Four!
		// \nClick OK to continue");
		/*
		 * JOptionPane.showMessageDialog(null,
		 * "<html> <b> Has </b>Higher A Market Value</html> ");
		 * 
		 * String msg = "<html>This is how to get:<ul><li><i>italics</i> and " +
		 * "<li><b>bold</b> and " + "<li><u>underlined</u>...</ul></html>";
		 * JLabel label = new JLabel(msg); label.setFont(new Font("serif",
		 * Font.PLAIN, 14)); JOptionPane.showConfirmDialog(null, label);
		 */

		UIManager titleScreen = new UIManager();
		titleScreen.put("OptionPane.background", Color.darkGray);
		titleScreen.put("Panel.background", Color.darkGray);
		titleScreen.put("OptionPane.buttonFont", new FontUIResource(new Font("Courier New Italic", Font.BOLD, 60)));
		titleScreen.put("OptionPane.buttonColor", Color.BLUE);
		titleScreen.put("OptionPane.border", new EmptyBorder(200, 100, 200, 100));
		// titleScreen.put("OptionPane.border",
		// BorderFactory.createLineBorder(Color.RED));
		String titleText = "<html>CONNECT FOUR<br><br></br></html>";
		JLabel label = new JLabel(titleText);
		label.setFont(new Font("Courier New Italic", Font.BOLD, 100));
		label.setForeground(Color.WHITE);
		Object[] options = { "Play" };
		int n = JOptionPane.showOptionDialog(frame, label, "ConnectFour", JOptionPane.PLAIN_MESSAGE,
				JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		if (n == -1)
			System.exit(0);
	}

	public void colorSelection() {
		JDialog frame = new JDialog();
		UIManager colorScreen = new UIManager();
		colorScreen.put("OptionPane.background", Color.darkGray);
		colorScreen.put("Panel.background", Color.darkGray);
		colorScreen.put("OptionPane.buttonFont", new FontUIResource(new Font("Courier New Italic", Font.BOLD, 60)));
		colorScreen.put("OptionPane.border", new EmptyBorder(200, 100, 200, 100));

		Object[] options = { "Red", "Yellow" };
		String colorText = "<html>Select your game piece color<li></li><br></html>";
		JLabel label = new JLabel(colorText);
		label.setFont(new Font("Courier New Bold Italic", Font.BOLD, 50));
		label.setForeground(Color.WHITE);
		int choice = JOptionPane.showOptionDialog(frame, label, "Color Selection", JOptionPane.DEFAULT_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		if (choice == -1)
			System.exit(0);
		if (choice == 1) {
			driver.setPlayerColor(2);
			driver.setOpponentColor(1);
		}
	}

	public void scoreSheet() {
		JDialog frame = new JDialog();
		UIManager instructionScreen = new UIManager();
		instructionScreen.put("OptionPane.background", Color.darkGray);
		instructionScreen.put("Panel.background", Color.darkGray);
		instructionScreen.put("OptionPane.buttonFont",
				new FontUIResource(new Font("Courier New Italic", Font.BOLD, 30)));
		instructionScreen.put("OptionPane.border", new EmptyBorder(200, 100, 200, 100));
		String msg = "<html><center> Score: <br><br> You-CPU <br>" + driver.getPlayerWinCount() + "-"
				+ driver.getOpponentWinCount() + "<br><br></br></center></html> ";
		JLabel label = new JLabel(msg);
		label.setFont(new Font("Courier New Bold Italic", Font.BOLD, 50));
		label.setForeground(Color.WHITE);
		Object[] options = { "OK" };
		JOptionPane.showOptionDialog(frame, label, "Score", JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null,
				options, options[0]);
	}

	public JButton[][] getButtons() {
		return buttons;
	}

	public void setButtons(JButton[][] buttons) {
		this.buttons = buttons;
	}

	public JLabel getBarLabel() {
		return barLabel;
	}

	public void setBarLabel(JLabel barLabel) {
		this.barLabel = barLabel;
	}

	public int[][] getIconColor() {
		return iconColor;
	}

	public void setIconColor(int[][] iconColor) {
		this.iconColor = iconColor;
	}

	public int getIconCount() {
		return iconCount;
	}

	public void setIconCount(int iconCount) {
		this.iconCount = iconCount;
	}

	public ImageIcon getRedCircle() {
		return redCircle;
	}

	public void setRedCircle(ImageIcon redCircle) {
		this.redCircle = redCircle;
	}

	public ImageIcon getYellowCircle() {
		return yellowCircle;
	}

	public void setYellowCircle(ImageIcon yellowCircle) {
		this.yellowCircle = yellowCircle;
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
		resetButton.addActionListener(this);
		instructionsButton.addActionListener(this);
		exitButton.addActionListener(this);
		scoreButton.addActionListener(this);
		autoButton.addActionListener(this);
		JToolBar toolbar = new JToolBar();
		toolbar.setFloatable(false);
		toolbar.add(resetButton);
		toolbar.addSeparator();
		toolbar.add(instructionsButton);
		toolbar.addSeparator();
		toolbar.add(scoreButton);
		toolbar.addSeparator();
//		toolbar.add(autoButton);
//		toolbar.addSeparator();
		toolbar.add(exitButton);
		toolbar.addSeparator();
		toolbar.add(barLabel);
		toolbar.addSeparator();
		toolbar.add(turnLabel);
		JPanel panel = new JPanel(new GridLayout(6, 7));
		for (int j = 0; j < buttons[0].length; j++)
			for (int i = 0; i < buttons.length; i++)
				panel.add(buttons[i][j]);
		for (int j = 0; j < buttons[0].length; j++)
			for (int i = 0; i < buttons.length; i++)
				buttons[i][j].addActionListener(this);
		add(panel);
		add(toolbar, BorderLayout.PAGE_START);
		setTitle("Connect Four");
	}

	public void actionPerformed(ActionEvent e) {
		for (int j = 0; j < buttons[0].length; j++)
			for (int i = 0; i < buttons.length; i++)
				if (e.getSource() == buttons[i][j]) {
					if (driver.buttonPress(i)) {
						if (!driver.checkForWin(0)) {
							driver.compMove();
						}
						driver.checkForResolution();
					}
				}
		if (e.getSource() == instructionsButton) {
			instructions();
		}
		if (e.getSource() == exitButton) {
			System.exit(0);
		}
		if (e.getSource() == resetButton) {
			driver.resetBoard();
		}
		if (e.getSource() == scoreButton) {
			scoreSheet();
		}
		if (e.getSource() == autoButton) {
			for (int i = 0; i < 100; i++)
				driver.autoGame();
		}
	}

	public void instructions() {
		JDialog frame = new JDialog();
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);

		UIManager instructionScreen = new UIManager();
		instructionScreen.put("OptionPane.background", Color.darkGray);
		instructionScreen.put("Panel.background", Color.darkGray);
		instructionScreen.put("OptionPane.buttonFont",
				new FontUIResource(new Font("Courier New Italic", Font.BOLD, 40)));
		instructionScreen.put("OptionPane.border", new EmptyBorder(200, 100, 200, 100));
		// titleScreen.put("OptionPane.border",
		// BorderFactory.createLineBorder(Color.RED));
		String instructionText = "<html><center>Object: You must get four disks in a row. You can do it horizontally,"
				+ "<br>vertically, or diagonal, but it has to be before the other player.<br><br>"
				+ "Buttons: You will have a hint button in which you will click on and it<br> "
				+ "will display the potential places that the opposite player may "
				+ "place their disk.<br> A reset button, if you decide that the game "
				+ "is not going as planned<br> or if you simply clicked the wrong "
				+ " place. There is an exit button to close<br> the window.<br><br></br></center></html>";
		JLabel label = new JLabel(instructionText);
		label.setFont(new Font("Courier New Bold Italic", Font.BOLD, 20));
		label.setForeground(Color.WHITE);
		Object[] options = { "GOT IT" };
		JOptionPane.showOptionDialog(frame, label, "Instructions", JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE,
				null, options, options[0]);
	}
}
