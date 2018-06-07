import java.awt.Color;
import java.awt.Font;

import javax.swing.*;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class PokerKI {
	public JFrame frame;
	private JTextField textField;
	
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
	public JLabel lblDealerButton = new JLabel("");
	public JLabel lblBalancePlayer0 = new JLabel("$0");
	public JLabel lblBalancePlayer1 = new JLabel("$0");
	public JLabel lblBalancePlayer2 = new JLabel("$0");
	public JLabel lblBet0 = new JLabel("");
	public JLabel lblBet1 = new JLabel("");
	public JLabel lblBet2 = new JLabel("");
	public JLabel lblPot = new JLabel("");
	public JLabel lblLog = new JLabel("");
	public JScrollPane spLog = new JScrollPane(lblLog);
	JScrollBar vertical;
	Font fontTextField = new Font("SansSerif", Font.BOLD, 15);
	Font fontMain = new Font("SansSerif", Font.BOLD, 15);
	Font fontValues = new Font("SansSerif", Font.BOLD, 25);
	Font fontLog = new Font("SansSerif",Font.PLAIN, 12);
	int moneyHeight = 20;
	int buttonWidth = 120;
	int betWidth = 100;
	String log = "";
	String logStart = "<html>";
	String logEnd = "</html>";
	
	public void setBalanceColorPositive(int i) {
		if(i == 0) lblBalancePlayer0.setForeground(Color.GREEN);
		if(i == 1) lblBalancePlayer1.setForeground(Color.GREEN);
		if(i == 2) lblBalancePlayer2.setForeground(Color.GREEN);
	}
	public void setBalanceColorNeutral(int i) {
		if(i == 0) lblBalancePlayer0.setForeground(Color.WHITE);
		if(i == 1) lblBalancePlayer1.setForeground(Color.WHITE);
		if(i == 2) lblBalancePlayer2.setForeground(Color.WHITE);
	}
	
	static JButton btnFold;
	static JButton btnCall;
	static JButton btnRaise;
	JButton btnStartNewHand;
	static JButton btnStartNewGame;
	static JButton btnExit;
	static JButton btnPlayX;
	static JButton btnLoadFromTxt;
	
	static JButton btnTest;
	
	public void setCCButton() {
		if(!GameController.activeGame) {
			btnCall.setBackground(Color.GRAY);
			btnFold.setBackground(Color.GRAY);
			btnRaise.setBackground(Color.GRAY);
			btnStartNewHand.setBackground(Color.GRAY);
			return;
		}
		
		if(GameController.activeHand) {
			btnCall.setBackground(Color.GREEN);
			btnFold.setBackground(Color.GREEN);
			btnRaise.setBackground(Color.GREEN);
			btnStartNewHand.setBackground(Color.GRAY);
		}
		else{
			btnCall.setBackground(Color.GRAY);
			btnFold.setBackground(Color.GRAY);
			btnRaise.setBackground(Color.GRAY);
			if(GameController.gameState == 5) btnStartNewHand.setBackground(Color.GREEN);
		}
		
		if(GameController.highestBet == GameController.player[0].bet) btnCall.setText("Check");
		else btnCall.setText("Call $" + (GameController.highestBet-GameController.player[0].bet));
	}

	public void setStartNewHandButton() {
		if(GameController.activeHand) btnStartNewHand.setBackground(Color.GRAY);
		else if(GameController.gameState == 5 && GameController.activeGame) btnStartNewHand.setBackground(Color.GREEN);
	}
	
	public void setButtons() {
		setCCButton();
		setStartNewHandButton();
	}
	
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
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
	}*/
	
	/*public void run() {
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
	}*/

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
		frame.setBounds(0, 0, 1304, 799);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setUndecorated(true);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setBackground(Color.decode("#555555"));
		frame.setContentPane(new JLabel(new ImageIcon("src/images/pokertisch.jpg")));

		btnTest = new JButton("TEST");
		btnTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int[] testkarten = {0,51,47,43,38};
				//int x = DetermineWinner.isStraightDraw(testkarten);
				//int y = DetermineWinner.mostCommonSuiteCount(testkarten);
				boolean y = DetermineWinner.isStraight(testkarten) != null;
				//System.out.println(x);
				System.out.println(y);
			}
		});
		btnTest.setBounds(200, 200, 80, 60);
		btnTest.setBackground(Color.PINK);
		btnTest.setFont(fontMain);
		frame.getContentPane().add(btnTest);
		
		btnRaise = new JButton("Raise");
		btnRaise.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(GameController.gameState > 3 || textField.getText().isEmpty()) return;
				int amount = Integer.parseInt(textField.getText());
				GameController.player[0].raise(amount);
				textField.setText("");
				while(GameController.activeHand && GameController.player[GameController.activePlayer].bot) GameController.getNextMove();
				if(GameController.player[GameController.activePlayer].acted == true) {
					GameController.changeGameState();
				}
				
			}
		});
		btnRaise.setBounds(frame.getWidth() - 20 - buttonWidth, frame.getHeight()-120, buttonWidth, 24);
		btnRaise.setBackground(Color.GRAY);
		btnRaise.setFont(fontMain);
		frame.getContentPane().add(btnRaise);
		
		textField = new JTextField();
		textField.setBounds(btnRaise.getX() - 20 - buttonWidth, frame.getHeight()-120, buttonWidth, 24);
		textField.setColumns(10);
		textField.setFont(fontTextField);
		frame.getContentPane().add(textField);
		
		btnCall = new JButton("Call");
		btnCall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(GameController.gameState > 3) return;
				GameController.player[0].call();
				while(GameController.activeHand && GameController.player[GameController.activePlayer].bot) GameController.getNextMove();
				if(GameController.player[GameController.activePlayer].acted == true) {
					GameController.changeGameState();
				}
			}
		});
		btnCall.setBounds(textField.getX() - 20 - buttonWidth, frame.getHeight()-120, buttonWidth, 24);
		btnCall.setBackground(Color.GRAY);
		btnCall.setFont(fontMain);
		frame.getContentPane().add(btnCall);
		
		btnFold = new JButton("Fold");
		btnFold.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(GameController.gameState > 3) return;
				GameController.player[0].fold();
				while(GameController.activeHand && GameController.player[GameController.activePlayer].bot) GameController.getNextMove();
				if(GameController.player[GameController.activePlayer].acted == true) {
					GameController.changeGameState();
				}
			}
		});
		btnFold.setBounds(btnCall.getX() - 20 - buttonWidth, frame.getHeight()-120, buttonWidth, 24);
		btnFold.setBackground(Color.GRAY);
		btnFold.setFont(fontMain);
		frame.getContentPane().add(btnFold);
		
		btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnExit.setBounds(frame.getWidth() - 100, frame.getHeight() - 48, 100, 48);
		btnExit.setBackground(Color.RED);
		btnExit.setFont(fontMain);
		frame.getContentPane().add(btnExit);
		
		btnPlayX = new JButton("Play X Games");
		btnPlayX.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GameController.playX(20);
			}
		});
		btnPlayX.setBounds(frame.getWidth() - 200, 0, 200, 48);
		btnPlayX.setBackground(Color.BLUE);
		btnPlayX.setFont(fontMain);
		frame.getContentPane().add(btnPlayX);
		
		btnLoadFromTxt = new JButton("Load");
		btnLoadFromTxt.setBounds(0,0,100,48);
		btnLoadFromTxt.setFont(fontMain);
		btnLoadFromTxt.setBackground(Color.BLUE);
		btnLoadFromTxt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					GameController.readFromTxt();
					System.out.println("Everything alright");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		frame.getContentPane().add(btnLoadFromTxt);
		

		lblBoard2.setBounds((frame.getWidth()/2) - 83/2, frame.getHeight()/4, 83, 117);
		frame.getContentPane().add(lblBoard2);
		lblBoard1.setBounds(lblBoard2.getX() - lblBoard2.getWidth() - 2, lblBoard2.getY(), 83, 117);
		frame.getContentPane().add(lblBoard1);
		lblBoard0.setBounds(lblBoard1.getX() - lblBoard1.getWidth() - 2, lblBoard2.getY(), 83, 117);
		frame.getContentPane().add(lblBoard0);
		lblBoard3.setBounds(lblBoard2.getX() + lblBoard2.getWidth() + 8, lblBoard2.getY(), 83, 117);
		frame.getContentPane().add(lblBoard3);
		lblBoard4.setBounds(lblBoard3.getX() + lblBoard3.getWidth() + 8, lblBoard2.getY(), 83, 117);
		frame.getContentPane().add(lblBoard4);
		
		lblHole0.setBounds((frame.getWidth()/2 - 83 - 1), frame.getHeight()*4/7, 83, 117);
		frame.getContentPane().add(lblHole0);
		lblHole1.setBounds((frame.getWidth()/2 + 1), lblHole0.getY(), 83, 117);
		frame.getContentPane().add(lblHole1);
		lblHole2.setBounds(frame.getWidth()/5, lblBoard2.getY() - 117 - 30, 83, 117);
		frame.getContentPane().add(lblHole2);
		lblHole3.setBounds(lblHole2.getX() - 83 - 2, lblBoard2.getY() - 117 - 30, 83, 117);
		frame.getContentPane().add(lblHole3);
		lblHole4.setBounds(frame.getWidth()*5/7, lblBoard2.getY() - 117 - 30, 83, 117);
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
				if(GameController.gameState < 5 || GameController.activeGame == false) return;
				GameController.startNewHand();
				drawButton(GameController.button);
			}
		});
		btnStartNewHand.setBounds(btnRaise.getX() + btnRaise.getWidth() - 120, btnRaise.getY() - 48 - 12, 120, 48);
		btnStartNewHand.setBackground(Color.GRAY);
		btnStartNewHand.setFont(fontMain);
		frame.getContentPane().add(btnStartNewHand);
		
		btnStartNewGame = new JButton("Start New Game");
		btnStartNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GameController.startNewGame();
				drawButton(GameController.button);
			}
		});
		btnStartNewGame.setBounds(0, frame.getHeight() - 48, 180, 48);
		btnStartNewGame.setBackground(Color.GREEN);
		btnStartNewGame.setFont(fontMain);
		frame.getContentPane().add(btnStartNewGame);
		
		lblDealerButton.setBounds(347, 506, 46, moneyHeight);
		lblDealerButton.setForeground(Color.RED);
		lblDealerButton.setFont(new Font("SansSerif", Font.BOLD, 22));
		frame.getContentPane().add(lblDealerButton);
		
		lblBalancePlayer0.setBounds(lblHole0.getX(), lblHole0.getY() + 117 + 8, lblHole0.getWidth()*2 + 2, moneyHeight);
		lblBalancePlayer0.setHorizontalAlignment(JLabel.CENTER);
		lblBalancePlayer0.setForeground(Color.WHITE);
		lblBalancePlayer0.setFont(fontValues);
		frame.getContentPane().add(lblBalancePlayer0);
		
		lblBalancePlayer1.setBounds(lblHole3.getX(), lblHole3.getY() - 14 - 12, lblHole0.getWidth()*2 + 2, moneyHeight);
		lblBalancePlayer1.setHorizontalAlignment(JLabel.CENTER);
		lblBalancePlayer1.setForeground(Color.WHITE);
		lblBalancePlayer1.setFont(fontValues);
		frame.getContentPane().add(lblBalancePlayer1);
		
		lblBalancePlayer2.setBounds(lblHole4.getX(), lblHole4.getY() - 14 - 12, lblHole0.getWidth()*2 + 2, moneyHeight);
		lblBalancePlayer2.setHorizontalAlignment(JLabel.CENTER);
		lblBalancePlayer2.setForeground(Color.WHITE);
		lblBalancePlayer2.setFont(fontValues);
		frame.getContentPane().add(lblBalancePlayer2);
		
		lblBet0.setBounds(lblBalancePlayer0.getX(), lblHole0.getY() - 14 - 12, lblHole0.getWidth()*2 + 2, moneyHeight);
		lblBet0.setForeground(Color.WHITE);
		lblBet0.setHorizontalAlignment(JLabel.CENTER);
		lblBet0.setFont(fontValues);
		frame.getContentPane().add(lblBet0);
		
		lblBet1.setBounds(lblHole2.getX() + lblHole2.getWidth() + 8, lblHole2.getY() + (lblHole2.getHeight()/2 - 14/2), betWidth, moneyHeight);
		lblBet1.setForeground(Color.WHITE);
		lblBet1.setHorizontalAlignment(JLabel.CENTER);
		lblBet1.setFont(fontValues);
		frame.getContentPane().add(lblBet1);
		
		lblBet2.setBounds(lblHole4.getX() - 8 - betWidth, lblHole4.getY() + (lblHole4.getHeight()/2 - 14/2), betWidth, moneyHeight);
		lblBet2.setForeground(Color.WHITE);
		lblBet2.setHorizontalAlignment(JLabel.CENTER);
		lblBet2.setFont(fontValues);
		frame.getContentPane().add(lblBet2);
		
		lblPot.setBounds(lblBoard0.getX(), lblBoard2.getY() + lblBoard2.getHeight() + 8, lblBoard0.getWidth()*5 + 2*2 + 2*8, moneyHeight);
		lblPot.setForeground(Color.WHITE);
		lblPot.setHorizontalAlignment(JLabel.CENTER);
		lblPot.setFont(fontValues);
		frame.getContentPane().add(lblPot);
		
		spLog.setBounds(10, btnStartNewGame.getY() - btnStartNewGame.getHeight() - 100, 300, 100);
		frame.getContentPane().add(spLog);
		
		vertical = spLog.getVerticalScrollBar();
		
		lblLog.setBackground(Color.BLACK);
		lblLog.setOpaque(true);
		lblLog.setForeground(Color.WHITE);
		lblLog.setFont(fontLog);
		lblLog.setVerticalAlignment(JLabel.TOP);
		lblLog.setText(log);		
		
	}

	protected void updatePlayerBet() {
		 if(GameController.player[0].bet == 0) lblBet0.setText("");
		 else lblBet0.setText("$" + Integer.toString(GameController.player[0].bet));
		 if(GameController.player[1].bet == 0) lblBet1.setText("");
		 else lblBet1.setText("$" + Integer.toString(GameController.player[1].bet));
		 if(GameController.player[2].bet == 0) lblBet2.setText("");
		 else lblBet2.setText("$" + Integer.toString(GameController.player[2].bet));
		 
		 lblBet0.setText("$" + Integer.toString(GameController.player[0].bet));
		 lblBet1.setText("$" + Integer.toString(GameController.player[1].bet));
		 lblBet2.setText("$" + Integer.toString(GameController.player[2].bet));
	}
	protected void updatePlayerBalance() {
		lblBalancePlayer0.setText("$" + Integer.toString(GameController.player[0].balance));
		lblBalancePlayer1.setText("$" + Integer.toString(GameController.player[1].balance));
		lblBalancePlayer2.setText("$" + Integer.toString(GameController.player[2].balance));
	}
	protected void updatePot() {
		lblPot.setText("$" + Integer.toString(GameController.mainPot));
	}
	public void updateAll() {
		updatePlayerBet();
		updatePlayerBalance();
		updatePot();
	}
	public void addToLog(String s) {
		
		log += s + "<br/>";
		lblLog.setText(logStart + log + logEnd);
		
		vertical.setValue(vertical.getMaximum());
	}
	
	
	
	
	public void drawButton(int x) {
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
		case 0:
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
			
			setCardLabel(5,GameController.cardDeck[5]);
			setCardLabel(6,GameController.cardDeck[6]);
			break;
		case 1: 
			setCardLabel(0, GameController.cardDeck[0]);
			setCardLabel(1, GameController.cardDeck[1]);
			setCardLabel(2, GameController.cardDeck[2]);
			break;
		case 2: 
			setCardLabel(3, GameController.cardDeck[3]);
			break;
		case 3:
			setCardLabel(4, GameController.cardDeck[4]);
			break;
		case 4:		
			setCardLabel(7, GameController.cardDeck[7]);
			setCardLabel(8, GameController.cardDeck[8]);
			setCardLabel(9, GameController.cardDeck[9]);
			setCardLabel(10, GameController.cardDeck[10]);
			break;
		}
		
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
