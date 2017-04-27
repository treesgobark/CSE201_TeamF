import javax.swing.JFrame;
public class Driver {
	public static void main(String[] args) {
		ConnectFourBoard2 gameBoard = new ConnectFourBoard2();
//		gameBoard.TitleScreen();
//		gameBoard.colorSelection();
//		gameBoard.setSize(1075, 1000);
		gameBoard.setLocationRelativeTo(null);
		gameBoard.setVisible(true);
		gameBoard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
}