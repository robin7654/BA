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
	private JLabel lblDealerButton = new JLabel("");
	private JLabel lblBalancePlayer0 = new JLabel("$0");
	private JLabel lblBalancePlayer1 = new JLabel("$0");
	private JLabel lblBalancePlayer2 = new JLabel("$0");
	private JLabel lblBet0 = new JLabel("");
	private JLabel lblBet1 = new JLabel("");
	private JLabel lblBet2 = new JLabel("");
	private JLabel lblPot = new JLabel("");
	Font tFFont = new Font("SansSerif", Font.BOLD, 15);
	Font mainFont = new Font("SansSerif", Font.BOLD, 15);
	Font moneyFont = new Font("SansSerif", Font.BOLD, 25);
	int moneyHeight = 20;
	
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
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setBackground(Color.decode("#555555"));
		//frame.setContentPane(new JLabel(backGround));

		

		
		
		

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
		

		lblBoard2.setBounds((frame.getWidth()/2) - 83/2, frame.getHeight()/2 - 117, 83, 117);
		frame.getContentPane().add(lblBoard2);
		
		lblBoard1.setBounds(lblBoard2.getX() - lblBoard2.getWidth() - 2, frame.getHeight()/2 - 117, 83, 117);
		frame.getContentPane().add(lblBoard1);
		
		lblBoard0.setBounds(lblBoard1.getX() - lblBoard1.getWidth() - 2, frame.getHeight()/2 - 117, 83, 117);
		frame.getContentPane().add(lblBoard0);

		lblBoard3.setBounds(lblBoard2.getX() + lblBoard2.getWidth() + 8, frame.getHeight()/2 - 117, 83, 117);
		frame.getContentPane().add(lblBoard3);

		lblBoard4.setBounds(lblBoard3.getX() + lblBoard3.getWidth() + 8, frame.getHeight()/2 - 117, 83, 117);
		frame.getContentPane().add(lblBoard4);
		
		
		lblHole0.setBounds((frame.getWidth()/2 - 83 - 1), lblBoard2.getY() + 117 + 30, 83, 117);
		frame.getContentPane().add(lblHole0);
		
		lblHole1.setBounds((frame.getWidth()/2 + 1), lblBoard2.getY() + 117 + 30, 83, 117);
		frame.getContentPane().add(lblHole1);

		lblHole2.setBounds(lblBoard0.getX() - 83/2, lblBoard2.getY() - 117 - 30, 83, 117);
		frame.getContentPane().add(lblHole2);

		lblHole3.setBounds(lblHole2.getX() - 83 - 2, lblBoard2.getY() - 117 - 30, 83, 117);
		frame.getContentPane().add(lblHole3);

		lblHole4.setBounds(lblBoard4.getX() + 83/2, lblBoard2.getY() - 117 - 30, 83, 117);
		frame.getContentPane().add(lblHole4);

		lblHole5.setBounds(lblHole4.getX() + 83 + 2, lblBoard2.getY() - 117 - 30, 83, 117);
		frame.getContentPane().add(lblHole5);
		
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
		btnStartNewGame.setBounds(0, 0, 180, 48);
		btnStartNewGame.setBackground(Color.GREEN);
		btnStartNewGame.setFont(mainFont);
		frame.getContentPane().add(btnStartNewGame);
		
		lblDealerButton.setBounds(347, 506, 46, moneyHeight);
		lblDealerButton.setForeground(Color.RED);
		lblDealerButton.setFont(new Font("SansSerif", Font.BOLD, 22));
		frame.getContentPane().add(lblDealerButton);
		
		lblBalancePlayer0.setBounds(lblHole0.getX(), lblHole0.getY() + 117 + 8, lblHole0.getWidth()*2 + 2, moneyHeight);
		lblBalancePlayer0.setHorizontalAlignment(JLabel.CENTER);
		lblBalancePlayer0.setForeground(Color.WHITE);
		lblBalancePlayer0.setFont(moneyFont);
		frame.getContentPane().add(lblBalancePlayer0);
		
		lblBalancePlayer1.setBounds(lblHole3.getX(), lblHole3.getY() - 14 - 12, lblHole0.getWidth()*2 + 2, moneyHeight);
		lblBalancePlayer1.setHorizontalAlignment(JLabel.CENTER);
		lblBalancePlayer1.setForeground(Color.WHITE);
		lblBalancePlayer1.setFont(moneyFont);
		frame.getContentPane().add(lblBalancePlayer1);
		
		lblBalancePlayer2.setBounds(lblHole4.getX(), lblHole4.getY() - 14 - 12, lblHole0.getWidth()*2 + 2, moneyHeight);
		lblBalancePlayer2.setHorizontalAlignment(JLabel.CENTER);
		lblBalancePlayer2.setForeground(Color.WHITE);
		lblBalancePlayer2.setFont(moneyFont);
		frame.getContentPane().add(lblBalancePlayer2);
		
		lblBet0.setBounds(lblBalancePlayer0.getX(), lblHole0.getY() - 14 - 12, lblHole0.getWidth()*2 + 2, moneyHeight);
		lblBet0.setForeground(Color.WHITE);
		lblBet0.setHorizontalAlignment(JLabel.CENTER);
		lblBet0.setFont(moneyFont);
		frame.getContentPane().add(lblBet0);
		lblBet1.setBounds(lblHole2.getX() + lblHole2.getWidth() + 8, lblHole2.getY() + (lblHole2.getHeight()/2 - 14/2), 46, moneyHeight);
		lblBet1.setForeground(Color.WHITE);
		lblBet1.setHorizontalAlignment(JLabel.CENTER);
		lblBet1.setFont(moneyFont);
		frame.getContentPane().add(lblBet1);
		lblBet2.setBounds(lblHole4.getX() - 8 - 46, lblHole4.getY() + (lblHole4.getHeight()/2 - 14/2), 46, moneyHeight);
		lblBet2.setForeground(Color.WHITE);
		lblBet2.setHorizontalAlignment(JLabel.CENTER);
		lblBet2.setFont(moneyFont);
		frame.getContentPane().add(lblBet2);
		
		lblPot.setBounds(lblBoard4.getX() + lblBoard4.getWidth() + 8, lblBoard4.getY() + lblBoard4.getHeight()/2 - 14/2, 100, moneyHeight);
		lblPot.setForeground(Color.WHITE);
		lblPot.setFont(moneyFont);
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
		lblDealerButton.setText("D");
		switch (x) {
		case 0: lblDealerButton.setBounds(lblHole1.getX() + lblHole1.getWidth() + 8, lblHole1.getY() - 14 - 8, lblDealerButton.getWidth(), lblDealerButton.getHeight());
		lblDealerButton.setHorizontalAlignment(JLabel.LEFT);
		break;
		case 1: lblDealerButton.setBounds(lblBet1.getX(), lblBet1.getY() + lblBet1.getHeight() + 8, lblDealerButton.getWidth(), lblDealerButton.getHeight());
		lblDealerButton.setHorizontalAlignment(JLabel.LEFT);
		break;
		case 2: lblDealerButton.setBounds(lblBet2.getX() + lblBet2.getWidth() - 46, lblBet2.getY() + lblBet2.getHeight() + 8, lblDealerButton.getWidth(), lblDealerButton.getHeight());
		lblDealerButton.setHorizontalAlignment(JLabel.RIGHT);
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
