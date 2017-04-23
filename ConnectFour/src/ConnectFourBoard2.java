import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;

@SuppressWarnings("serial")
public class ConnectFourBoard2 extends JFrame implements ActionListener {
	private JButton[][] buttons;
	private JButton resetButton;
	private JButton instructionsButton;
	private JButton exitButton;
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
		barLabel = new JLabel();
		turnLabel = new JLabel();
		iconColor = new int[7][6];
		redCircle = new ImageIcon(getClass().getResource("/resources/Red Circle.png"));
		yellowCircle = new ImageIcon(getClass().getResource("/resources/Yellow Circle.png"));
		driver = new ConnectFourDriver(this);
		setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
		colorSelection();
		iconCount = driver.getPlayerColor();
		constructComponents();
	}
	
	public void colorSelection() {
		JDialog frame = new JDialog();
		Object[] options = { "Red", "Yellow" };
		int choice = JOptionPane.showOptionDialog(frame, "Select your game piece color", "Color Selection",
				JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		System.out.println(choice);
		if (choice == 1) driver.setPlayerColor(2);
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

		JToolBar toolbar = new JToolBar();
		toolbar.setFloatable(false);
		toolbar.add(resetButton);
		toolbar.addSeparator();
		toolbar.add(instructionsButton);
		toolbar.addSeparator();
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
		barLabel.setText("Red's Turn");
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
	}

	public void instructions() {
		JOptionPane.showMessageDialog(null,
				"\n\nObject: You must get four disks in a row. You can do it horizontally,"
						+ "\nvertically, or diagonal, but it has to be before the other player.\n\n"
						+ "Buttons: You will have a hint button in which you will click on and it\n "
						+ "will display the potential places that the opposite player may "
						+ "place their disk.\n A reset button, if you decide that the game "
						+ "is not going as planned\n or if you simply clicked the wrong "
						+ " place. There is an exit button to close\n the window.",
				"Instructions", JOptionPane.QUESTION_MESSAGE);
	}

}