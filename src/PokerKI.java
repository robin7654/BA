import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Timer;
import java.util.TimerTask;

public class PokerKI {
	public JFrame frame;
	private JTextField textField;
	public static GameContoller gc = new GameContoller();
	
	
	JLabel lblBoard = new JLabel("Board0");
	JLabel lblBoard_1 = new JLabel("Board1");
	JLabel lblBoard_2 = new JLabel("Board2");
	JLabel lblBoard_3 = new JLabel("Board3");
	JLabel lblBoard_4 = new JLabel("Board4");
	JLabel lblHole = new JLabel("Hole1");
	JLabel lblHole_1 = new JLabel("Hole2");
	JLabel lblHole_2 = new JLabel("Hole3");
	JLabel lblHole_3 = new JLabel("Hole4");
	JLabel lblHole_4 = new JLabel("Hole5");
	JLabel lblHole_5 = new JLabel("Hole6");
	ImageIcon testimg = new ImageIcon("C:/Users/robin7654/Desktop/pokerkartenklein/" + 1 + ".jpg");
	private JLabel lblD = new JLabel("D");
	private JLabel lblBalance = new JLabel("x");
	private JLabel lblBalance_1 = new JLabel("x");
	private JLabel lblBalance_2 = new JLabel("x");
	private JLabel lblBet = new JLabel("Bet");
	private JLabel lblBet_1 = new JLabel("Bet");
	private JLabel lblBet_2 = new JLabel("Bet");
	private JLabel lblPot = new JLabel("Pot");
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PokerKI window = new PokerKI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PokerKI() {
		initialize();
		
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame();
		frame.setBounds(100, 100, 1247, 775);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JButton btnFold = new JButton("Fold");
		btnFold.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gc.fold(gc.player0);
				
//				gc.continueBetting(gc.nextPlayer(0));
//		
//				gc.nextGameState();
//				openCards(gc.gamestate);
//				updatePlayerBalance();
//				updatePlayerBet();
//				updatePot();
				
				while (gc.gamestate < 4){
					gc.continueBetting(gc.nextPlayer(0));
					
					gc.nextGameState();
					openCards(gc.gamestate);
					updatePlayerBalance();
					updatePlayerBet();
					updatePot();
				}
			}
		});
		btnFold.setBounds(741, 664, 89, 23);
		frame.getContentPane().add(btnFold);

		JButton btnCall = new JButton("Call");
		btnCall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gc.call(gc.player0);
				
				if(gc.button != 0 ){
					gc.continueBetting(gc.nextPlayer(0));
				} else {
					if (gc.player2.bet > gc.player1.bet & gc.player1.active == true & gc.player1.allin== false){
						gc.continueBetting(gc.nextPlayer(0));
					}
				}
				
				
				
				gc.nextGameState();
				openCards(gc.gamestate);
				updatePlayerBalance();
				updatePlayerBet();
				updatePot();
			}
		});
		btnCall.setBounds(837, 664, 89, 23);
		frame.getContentPane().add(btnCall);

		JButton btnRaise = new JButton("Raise");
		btnRaise.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int amount = Integer.parseInt(textField.getText());
				gc.raise(gc.player0, amount);
				gc.continueBetting(gc.nextPlayer(0));
				
				gc.nextGameState();
				openCards(gc.gamestate);
				updatePlayerBalance();
				updatePlayerBet();
				updatePot();
				textField.setText("");
				
			}
		});
		btnRaise.setBounds(1032, 664, 89, 23);
		frame.getContentPane().add(btnRaise);

		textField = new JTextField();
		textField.setBounds(936, 665, 86, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
      
		lblHole.setBounds(415, 455, 83, 117);
		frame.getContentPane().add(lblHole);
		
		lblHole_1.setBounds(500, 455, 83, 117);
		frame.getContentPane().add(lblHole_1);

		lblHole_2.setBounds(150, 145, 83, 117);
		frame.getContentPane().add(lblHole_2);

		lblHole_3.setBounds(235, 145, 83, 117);
		frame.getContentPane().add(lblHole_3);

		lblHole_4.setBounds(645, 145, 83, 117);
		frame.getContentPane().add(lblHole_4);

		lblHole_5.setBounds(730, 145, 83, 117);
		frame.getContentPane().add(lblHole_5);

		lblBoard.setBounds(310, 263, 83, 117);
		frame.getContentPane().add(lblBoard);

		lblBoard_1.setBounds(395, 263, 83, 117);
		frame.getContentPane().add(lblBoard_1);

		lblBoard_2.setBounds(480, 263, 83, 117);
		frame.getContentPane().add(lblBoard_2);

		lblBoard_3.setBounds(580, 263, 83, 117);
		frame.getContentPane().add(lblBoard_3);

		lblBoard_4.setBounds(680, 263, 83, 117);
		frame.getContentPane().add(lblBoard_4);

		JButton btnStartGame = new JButton("start game");
		btnStartGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				setCardLabel(0,53);
				setCardLabel(1,53);
				setCardLabel(2,53);
				setCardLabel(3,53);
				setCardLabel(4,53);
				
				gc.startGame();
				placeButton(gc.button);
				setCardLabel(7,52);
				setCardLabel(8,52);
				setCardLabel(9,52);
				setCardLabel(10,52);
				
				setCardLabel(5,gc.karten[5]);
				setCardLabel(6,gc.karten[6]);
				updatePlayerBalance();
				updatePlayerBet();
			}
		});
		btnStartGame.setBounds(42, 636, 200, 50);
		frame.getContentPane().add(btnStartGame);
		
		lblD.setBounds(347, 506, 46, 14);
		frame.getContentPane().add(lblD);
		
		lblBalance.setBounds(444, 595, 46, 14);
		frame.getContentPane().add(lblBalance);
		lblBalance_1.setBounds(208, 120, 46, 14);
		frame.getContentPane().add(lblBalance_1);
		lblBalance_2.setBounds(680, 120, 46, 14);
		frame.getContentPane().add(lblBalance_2);
		
		lblBet.setBounds(444, 413, 46, 14);
		frame.getContentPane().add(lblBet);
		lblBet_1.setBounds(347, 233, 46, 14);
		frame.getContentPane().add(lblBet_1);
		lblBet_2.setBounds(543, 233, 46, 14);
		frame.getContentPane().add(lblBet_2);
		
		lblPot.setBounds(853, 370, 46, 14);
		frame.getContentPane().add(lblPot);
		
		
	}

	 protected void updatePlayerBet() {
		 lblBet.setText(Integer.toString(gc.player0.bet));
		 lblBet_1.setText(Integer.toString(gc.player1.bet));
		 lblBet_2.setText(Integer.toString(gc.player2.bet));
	}

	protected void updatePlayerBalance() {
		lblBalance.setText(Integer.toString(gc.player0.balance));
		lblBalance_1.setText(Integer.toString(gc.player1.balance));
		lblBalance_2.setText(Integer.toString(gc.player2.balance));
	}
	protected void updatePot() {
		lblPot.setText(Integer.toString(gc.pot));
	}
	
	
	
	
	
	public void placeButton(int x) {
		switch (x) {
		case 0: lblD.setBounds(347, 506, 46, 14);
		break;
		case 1: lblD.setBounds(81, 196, 46, 14);
		break;
		case 2: lblD.setBounds(565, 180, 46, 14);
		break;
		}
	}
	public void openCards(int x){
		switch (x){
		case 1: 
			setCardLabel(0,gc.karten[0]);
			setCardLabel(1,gc.karten[1]);
			setCardLabel(2,gc.karten[2]);
			break;
		case 2: 
			setCardLabel(3,gc.karten[3]);
			break;
		case 3:
			setCardLabel(4,gc.karten[4]);
			break;
		case 4:		
			setCardLabel(7,gc.karten[7]);
			setCardLabel(8,gc.karten[8]);
			setCardLabel(9,gc.karten[9]);
			setCardLabel(10,gc.karten[10]);
			
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				public void run() {
					
					int[] winnerArray = gc.evaluate();
					int max = gc.max(winnerArray);
					
					if(max == 2) {
						for(int i = 0; i < winnerArray.length; i++) {
							if(winnerArray[i] == 2) {
								if(i == 0) System.out.println("Winner: Player");
								else System.out.println("Winner: Enemy" + i);
								gc.getPlayer(i).balance += gc.pot;
							}
						}
						
					}
					else if(max == 1) {
						System.out.println("SplitPot between 2 Players");
						for(int i = 0; i < winnerArray.length; i++) {
							if(winnerArray[i] == 1) System.out.println("Winner: Player" + i);
							gc.getPlayer(i).balance += gc.pot/2;
						}
						
					}
					else {
						System.out.println("SplitPot between 3 Players");
						for(int i = 0; i < winnerArray.length; i++) {
							if(winnerArray[i] == 1) System.out.println("Winner: Player" + i);
							gc.getPlayer(i).balance += gc.pot/3;
						}
					}
					
					gc.pot = 0;
					updatePlayerBalance();
					updatePlayerBet();
					updatePot();
				}
			}, 2000);
			
			
			
			//pot yuschieben
			
			break;
		}
		
	}
	public void setCardLabel(int x, int y){
		switch (x) {
		case 0: ImageIcon imgBoard = new ImageIcon("src/images/" + y + ".jpg");
		lblBoard.setIcon(imgBoard);
		break;
		case 1: ImageIcon imgBoard_1 = new ImageIcon("src/images/" + y + ".jpg");
		lblBoard_1.setIcon(imgBoard_1);
		break;
		case 2: ImageIcon imgBoard_2 = new ImageIcon("src/images/" + y + ".jpg");
		lblBoard_2.setIcon(imgBoard_2);
		break;
		case 3: ImageIcon imgBoard_3 = new ImageIcon("src/images/" + y + ".jpg");
		lblBoard_3.setIcon(imgBoard_3);
		break;
		case 4: ImageIcon imgBoard_4 = new ImageIcon("src/images/" + y + ".jpg");
		lblBoard_4.setIcon(imgBoard_4);
		break;
		case 5: ImageIcon imgHole = new ImageIcon("src/images/" + y + ".jpg");
		lblHole.setIcon(imgHole);
		break;
		case 6: ImageIcon imgHole_1 = new ImageIcon("src/images/" + y + ".jpg");
		lblHole_1.setIcon(imgHole_1);
		break;
		case 7: ImageIcon imgHole_2 = new ImageIcon("src/images/" + y + ".jpg");
		lblHole_2.setIcon(imgHole_2);
		break;
		case 8: ImageIcon imgHole_3 = new ImageIcon("src/images/" + y + ".jpg");
		lblHole_3.setIcon(imgHole_3);
		break;
		case 9: ImageIcon imgHole_4 = new ImageIcon("src/images/" + y + ".jpg");
		lblHole_4.setIcon(imgHole_4);
		break;
		case 10: ImageIcon imgHole_5 = new ImageIcon("src/images/" + y + ".jpg");
		lblHole_5.setIcon(imgHole_5);
		break;
		}
	}
}
