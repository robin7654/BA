import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class PokerKI {
	public JFrame frame;
	private JTextField textField;
	public static GameContoller gc = new GameContoller();
	
	
	
	
	JLabel lblBoard0 = new JLabel("Board0");
	JLabel lblBoard1 = new JLabel("Board1");
	JLabel lblBoard2 = new JLabel("Board2");
	JLabel lblBoard3 = new JLabel("Board3");
	JLabel lblBoard4 = new JLabel("Board4");
	JLabel lblHole0 = new JLabel("Hole1");
	JLabel lblHole1 = new JLabel("Hole2");
	JLabel lblHole2 = new JLabel("Hole3");
	JLabel lblHole3 = new JLabel("Hole4");
	JLabel lblHole4 = new JLabel("Hole5");
	JLabel lblHole5 = new JLabel("Hole6");
	ImageIcon testimg = new ImageIcon("C:/Users/robin7654/Desktop/pokerkartenklein/" + 1 + ".jpg");
	private JLabel lblDealerButton = new JLabel("D");
	private JLabel lblBalancePlayer0 = new JLabel("$0");
	private JLabel lblBalancePlayer1 = new JLabel("$0");
	private JLabel lblBalancePlayer2 = new JLabel("$0");
	private JLabel lblBet0 = new JLabel("");
	private JLabel lblBet1 = new JLabel("");
	private JLabel lblBet2 = new JLabel("");
	private JLabel lblPot = new JLabel("");
	Font tFFont = new Font("SansSerif", Font.BOLD, 15);
	Font mainFont = new Font("SansSerif", Font.BOLD, 15);
	
	static JButton btnFold;
	static JButton btnCall;
	static JButton btnRaise;
	static JButton btnStartNewHand;
	static JButton btnStartNewGame;
	
	public static void setCCButton() {
		if(!gc.activeGame) {
			btnCall.setBackground(Color.GRAY);
			btnFold.setBackground(Color.GRAY);
			btnRaise.setBackground(Color.GRAY);
			btnStartNewHand.setBackground(Color.GRAY);
			return;
		}
		
		if(gc.activeHand) {
			btnCall.setBackground(Color.GREEN);
			btnFold.setBackground(Color.GREEN);
			btnRaise.setBackground(Color.GREEN);
			btnStartNewHand.setBackground(Color.GRAY);
		}
		else{
			btnCall.setBackground(Color.GRAY);
			btnFold.setBackground(Color.GRAY);
			btnRaise.setBackground(Color.GRAY);
			btnStartNewHand.setBackground(Color.GREEN);
		}
		
		if(gc.highestBet == gc.getPlayer(0).bet) btnCall.setText("Check");
		else btnCall.setText("Call $" + (gc.highestBet-gc.getPlayer(0).bet));
	}
	
	public static void setStartNewHandButton() {
		if(gc.activeHand) btnStartNewHand.setBackground(Color.GRAY);
		else btnStartNewHand.setBackground(Color.GREEN);
	}
	
	public static void setButtons() {
		setCCButton();
		setStartNewHandButton();
	}
	
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
		frame.setBounds(0, 0, 1000, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.getContentPane().setLayout(null);
		frame.getContentPane().setBackground(Color.decode("#555555"));
		frame.setContentPane(new JLabel(new ImageIcon("src/images/table.jpg")));

		

		
		
		

		btnRaise = new JButton("Raise");
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
		btnRaise.setBounds(frame.getWidth() - 20 - 90, frame.getHeight()-120, 90, 24);
		btnRaise.setBackground(Color.GRAY);
		btnRaise.setFont(mainFont);
		frame.getContentPane().add(btnRaise);
		
		textField = new JTextField();
		textField.setBounds(btnRaise.getX() - 20 - 90, frame.getHeight()-120, 90, 24);
		textField.setColumns(10);
		textField.setFont(tFFont);
		frame.getContentPane().add(textField);
		
		btnCall = new JButton("Call");
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
		btnCall.setBounds(textField.getX() - 20 - 90, frame.getHeight()-120, 90, 24);
		btnCall.setBackground(Color.GRAY);
		btnCall.setFont(mainFont);
		frame.getContentPane().add(btnCall);
		
		btnFold = new JButton("Fold");
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
		btnFold.setBounds(btnCall.getX() - 20 - 90, frame.getHeight()-120, 90, 24);
		btnFold.setBackground(Color.GRAY);
		btnFold.setFont(mainFont);
		frame.getContentPane().add(btnFold);

		
      
		lblHole0.setBounds(415, 455, 83, 117);
		frame.getContentPane().add(lblHole0);
		
		lblHole1.setBounds(500, 455, 83, 117);
		frame.getContentPane().add(lblHole1);

		lblHole2.setBounds(150, 145, 83, 117);
		frame.getContentPane().add(lblHole2);

		lblHole3.setBounds(235, 145, 83, 117);
		frame.getContentPane().add(lblHole3);

		lblHole4.setBounds(645, 145, 83, 117);
		frame.getContentPane().add(lblHole4);

		lblHole5.setBounds(730, 145, 83, 117);
		frame.getContentPane().add(lblHole5);

		lblBoard0.setBounds(310, 263, 83, 117);
		frame.getContentPane().add(lblBoard0);

		lblBoard1.setBounds(395, 263, 83, 117);
		frame.getContentPane().add(lblBoard1);

		lblBoard2.setBounds(480, 263, 83, 117);
		frame.getContentPane().add(lblBoard2);

		lblBoard3.setBounds(580, 263, 83, 117);
		frame.getContentPane().add(lblBoard3);

		lblBoard4.setBounds(680, 263, 83, 117);
		frame.getContentPane().add(lblBoard4);
		
		setCardLabel(0,52);
		setCardLabel(1,52);
		setCardLabel(2,52);
		setCardLabel(3,52);
		setCardLabel(4,52);
		
		setCardLabel(5,52);
		setCardLabel(6,52);
		setCardLabel(7,52);
		setCardLabel(8,52);
		setCardLabel(9,52);
		setCardLabel(10,52);

		btnStartNewHand = new JButton("Next Hand");
		btnStartNewHand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(gc.gamestate < 4) return;
				setCardLabel(0,52);
				setCardLabel(1,52);
				setCardLabel(2,52);
				setCardLabel(3,52);
				setCardLabel(4,52);
				
				gc.startHand();
				placeButton(gc.button);
				setCardLabel(7,52);
				setCardLabel(8,52);
				setCardLabel(9,52);
				setCardLabel(10,52);
				
				setCardLabel(5,gc.karten[5]);
				setCardLabel(6,gc.karten[6]);
				updatePlayerBalance();
				updatePlayerBet();
				updatePot();
				
			}
		});
		btnStartNewHand.setBounds(50, frame.getHeight()-120, 120, 48);
		btnStartNewHand.setBackground(Color.GRAY);
		btnStartNewHand.setFont(mainFont);
		frame.getContentPane().add(btnStartNewHand);
		
		btnStartNewGame = new JButton("Start New Game");
		btnStartNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setCardLabel(0,52);
				setCardLabel(1,52);
				setCardLabel(2,52);
				setCardLabel(3,52);
				setCardLabel(4,52);
				gc.startNewGame();
				placeButton(gc.button);
				setCardLabel(7,52);
				setCardLabel(8,52);
				setCardLabel(9,52);
				setCardLabel(10,52);
				
				setCardLabel(5,gc.karten[5]);
				setCardLabel(6,gc.karten[6]);
				updatePlayerBalance();
				updatePlayerBet();
				updatePot();
				
				
				
				
			}
		});
		btnStartNewGame.setBounds(50, 50, 180, 48);
		btnStartNewGame.setBackground(Color.GREEN);
		btnStartNewGame.setFont(mainFont);
		frame.getContentPane().add(btnStartNewGame);
		
		lblDealerButton.setBounds(347, 506, 46, 14);
		lblDealerButton.setForeground(Color.RED);
		frame.getContentPane().add(lblDealerButton);
		
		lblBalancePlayer0.setBounds(444, 595, 46, 14);
		lblBalancePlayer0.setForeground(Color.WHITE);
		frame.getContentPane().add(lblBalancePlayer0);
		lblBalancePlayer1.setBounds(208, 120, 46, 14);
		lblBalancePlayer1.setForeground(Color.WHITE);
		frame.getContentPane().add(lblBalancePlayer1);
		lblBalancePlayer2.setBounds(680, 120, 46, 14);
		lblBalancePlayer2.setForeground(Color.WHITE);
		frame.getContentPane().add(lblBalancePlayer2);
		
		lblBet0.setBounds(444, 413, 46, 14);
		lblBet0.setForeground(Color.WHITE);
		frame.getContentPane().add(lblBet0);
		lblBet1.setBounds(347, 233, 46, 14);
		lblBet1.setForeground(Color.WHITE);
		frame.getContentPane().add(lblBet1);
		lblBet2.setBounds(543, 233, 46, 14);
		lblBet2.setForeground(Color.WHITE);
		frame.getContentPane().add(lblBet2);
		
		lblPot.setBounds(853, 370, 46, 14);
		lblPot.setForeground(Color.WHITE);
		frame.getContentPane().add(lblPot);
		
		
	}

	 protected void updatePlayerBet() {
		 if(gc.player0.bet == 0) lblBet0.setText("");
		 else lblBet0.setText("$" + Integer.toString(gc.player0.bet));
		 if(gc.player1.bet == 0) lblBet1.setText("");
		 else lblBet1.setText("$" + Integer.toString(gc.player1.bet));
		 if(gc.player2.bet == 0) lblBet2.setText("");
		 else lblBet2.setText("$" + Integer.toString(gc.player2.bet));
	}

	protected void updatePlayerBalance() {
		lblBalancePlayer0.setText("$" + Integer.toString(gc.player0.balance));
		lblBalancePlayer1.setText("$" + Integer.toString(gc.player1.balance));
		lblBalancePlayer2.setText("$" + Integer.toString(gc.player2.balance));
	}
	protected void updatePot() {
		lblPot.setText("$" + Integer.toString(gc.pot));
	}
	
	
	
	
	
	public void placeButton(int x) {
		switch (x) {
		case 0: lblDealerButton.setBounds(347, 506, 46, 14);
		break;
		case 1: lblDealerButton.setBounds(81, 196, 46, 14);
		break;
		case 2: lblDealerButton.setBounds(565, 180, 46, 14);
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

					System.out.println(gc.getPlayer(0).balance + " "
							+ gc.getPlayer(1).balance + " "
							+ gc.getPlayer(2).balance);

					int[] winnerArray = gc.evaluate();
					int max = gc.max(winnerArray);

					if (max == 2) {
						for (int i = 0; i < winnerArray.length; i++) {
							if (winnerArray[i] == 2) {
								System.out.println("Winner: Player" + i);
								gc.getPlayer(i).balance += gc.pot;
							}
						}

					} else if (max == 1) {

						if ((gc.player0.active == false
								| gc.player1.active == false | gc.player2.active == false)
								& winnerArray[0] + winnerArray[1]
										+ winnerArray[2] == 0) {
							for (int i = 0; i < winnerArray.length; i++) {
								if (winnerArray[i] == 1) {
									System.out.println("Winner: Player" + i);
									gc.getPlayer(i).balance += gc.pot;
								}
							}

						} else {
							System.out.println("SplitPot between 2 Players");
							for (int i = 0; i < winnerArray.length; i++) {
								if (winnerArray[i] == 1) {
									System.out.println("Winner: Player" + i);
									gc.getPlayer(i).balance += gc.pot / 2;
								}
							}
						}

					} else {
						if ((gc.player0.active == false
								| gc.player1.active == false | gc.player2.active == false)
								& winnerArray[0] + winnerArray[1]
										+ winnerArray[2] == 0) {

							for (int i = 0; i < winnerArray.length; i++) {
								System.out.println("Winner: Player" + i);
								gc.getPlayer(i).balance += gc.pot / 2;
							}

						} else {
							System.out.println("SplitPot between 3 Players");
							for (int i = 0; i < winnerArray.length; i++) {
								System.out.println("Winner: Player" + i);
								gc.getPlayer(i).balance += gc.pot / 3;
							}
						}
					}

					gc.pot = 0;
					updatePlayerBalance();
					updatePlayerBet();
					updatePot();

					System.out.println(gc.getPlayer(0).balance + " "
							+ gc.getPlayer(1).balance + " "
							+ gc.getPlayer(2).balance);

					/*
					 * System.out.println(splitPot(winnerArray));
					 * if(!splitPot(winnerArray)) { btnStartGame.doClick();
					 * btnCall.doClick(); btnCall.doClick(); btnCall.doClick();
					 * btnCall.doClick(); btnCall.doClick(); }
					 */
				}
			}, 2000);
			break;
		}
		
	}
	
	
	public boolean splitPot(int[] winnerArray) {
		for(int i = 0; i < winnerArray.length; i++) {
			if(winnerArray[i] == 2 || winnerArray[i] == 1) return false;
		}
		return true;
	}
	
	public void setCardLabel(int x, int y){
		switch (x) {
		case 0: ImageIcon imgBoard = new ImageIcon("src/images/" + y + ".jpg");
		lblBoard0.setIcon(imgBoard);
		break;
		case 1: ImageIcon imgBoard_1 = new ImageIcon("src/images/" + y + ".jpg");
		lblBoard1.setIcon(imgBoard_1);
		break;
		case 2: ImageIcon imgBoard_2 = new ImageIcon("src/images/" + y + ".jpg");
		lblBoard2.setIcon(imgBoard_2);
		break;
		case 3: ImageIcon imgBoard_3 = new ImageIcon("src/images/" + y + ".jpg");
		lblBoard3.setIcon(imgBoard_3);
		break;
		case 4: ImageIcon imgBoard_4 = new ImageIcon("src/images/" + y + ".jpg");
		lblBoard4.setIcon(imgBoard_4);
		break;
		case 5: ImageIcon imgHole = new ImageIcon("src/images/" + y + ".jpg");
		lblHole0.setIcon(imgHole);
		break;
		case 6: ImageIcon imgHole_1 = new ImageIcon("src/images/" + y + ".jpg");
		lblHole1.setIcon(imgHole_1);
		break;
		case 7: ImageIcon imgHole_2 = new ImageIcon("src/images/" + y + ".jpg");
		lblHole2.setIcon(imgHole_2);
		break;
		case 8: ImageIcon imgHole_3 = new ImageIcon("src/images/" + y + ".jpg");
		lblHole3.setIcon(imgHole_3);
		break;
		case 9: ImageIcon imgHole_4 = new ImageIcon("src/images/" + y + ".jpg");
		lblHole4.setIcon(imgHole_4);
		break;
		case 10: ImageIcon imgHole_5 = new ImageIcon("src/images/" + y + ".jpg");
		lblHole5.setIcon(imgHole_5);
		break;
		}
	}
}
