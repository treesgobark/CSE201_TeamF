import javax.swing.JFrame;

public class Driver {

	public static void main(String[] args) {
		ConnectFourBoard2 gameBoard = new ConnectFourBoard2();
		gameBoard.setVisible(true);
		gameBoard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}
