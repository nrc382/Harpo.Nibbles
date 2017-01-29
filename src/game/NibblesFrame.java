package game;


import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
//import java.awt.Menu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.Timer;



import classAccessory.HightScoreReader;
import classAccessory.NIbblesInterface;


public class NibblesFrame extends JFrame implements ActionListener, MouseListener, NIbblesInterface{

	/**
	 * 
	 */
	private static int cont=9;
	private static final long serialVersionUID = 1L;
	private JRadioButton higth;
	private JRadioButton low;
	private static JButton newGameRadio;
	private static JLabel level;
	private String livello="IIIIIIII ";
	private JButton settingsInUpperBar;
	static JCheckBox classicMode;
	static JCheckBox dietMode;

	private JLabel firstName;
	private JLabel firstScore;
	private JLabel secondName;
	private JLabel secondScore;
	private JLabel currName;
	private JLabel currScore;
	private NibblesPanel currNibblesPanel;
	private JPanel settingsController;
	private JButton backToGame;
	private static JPanel bar;

	private LittleNibbles blue;
	private LittleNibbles green;
	private LittleNibbles pink;

	private int tempScore;
	private JPanel curr;
	private String theme;
	private JLabel currTheme;
	static JCheckBox vertigo;
	static boolean desperado=false;
	static JCheckBox indentMode;

	static boolean pinkTheme=false;
	public static boolean blueTheme=false;
	public static boolean greenTheme=true;
	public static boolean indent=false;
	private Timer upLabeltimer;
	private JButton settings;
	private static JPanel preview;
	private JPanel optionPanel;
	private JPanel cardsPanel;
	private JPanel upperCardsPanel;

	private boolean moreOptions=false;
	private JLabel isClassic;
	private JPanel lowerPanel;


	public Dimension barSize(){
		return bar.getSize();
	}

	public NibblesFrame(String arg1, String arg2) throws IOException {
		//frameInit();
		//this.requestFocus();
		BorderLayout layout = new BorderLayout();
		Container contentPane = this.getContentPane();
		contentPane.setLayout(layout);
		//contentPane.setSize(new Dimension(300, 320));
		cardsPanel = new JPanel(new CardLayout());
		upperCardsPanel = new JPanel(new CardLayout());


		//////**********/////////////

		upLabeltimer= new Timer(6000, this);
		upLabeltimer.setRepeats(false);

		theme="Green";
		bar = new JPanel();
		bar.setLayout(new BorderLayout());
		settingsInUpperBar=new JButton("Home");
		//settingsInUpperBar.setLayout(new GridLayout(1,3));

		//settingsInUpperBar.setToolTipText("(Alt+Shift)");
		backToGame=new JButton("Torna alla partita...");
		//backToGame.setToolTipText("(Alt+Shift)");
		neutrize(backToGame);
		level= new JLabel(livello);
		level.addMouseListener(this);
		setHigth(new JRadioButton());
		low= new JRadioButton();
		newGameRadio= new JButton();
		JPanel rows= new JPanel();
		rows.setLayout(new GridLayout(2,1));
		higth.setIcon(new ImageIcon(getClass().getClassLoader().getResource("Sources/up.png")));
		low.setIcon(new ImageIcon(getClass().getClassLoader().getResource("Sources/down.png")));
		//newGameRadio.setIcon(new ImageIcon(getClass().getClassLoader().getResource("Sources/newGame.png")));
		newGameRadio.setText(Integer.toString(NibblesPanel.getScore()));
		neutrize(newGameRadio);
		higth.addActionListener(this); low.addActionListener(this);
		higth.setFocusable(false); low.setFocusable(false); newGameRadio.setFocusable(false);

		neutrize(settingsInUpperBar);
		JPanel levelComp= new JPanel();
		rows.add(getHigth());
		rows.add(low);
		levelComp.add(rows);
		levelComp.add(level);

		currTheme= new JLabel("Mr. "+theme+" in action!\t");

		currTheme.setForeground(new Color(26,153,0));

		bar.add(levelComp, BorderLayout.WEST);
		upperCardsPanel.add(settingsInUpperBar);
		upperCardsPanel.add(backToGame);
		bar.add(upperCardsPanel, BorderLayout.CENTER );
		bar.add(newGameRadio, BorderLayout.EAST);


		if(arg1 != null && arg2 != null)
			System.out.println("Argomenti: "+arg1+" "+arg2);


		if(arg1!= null && arg2.toLowerCase().equals("cheat")){

			System.out.println("\tOh,/n/tDear cheat cheater!/n/t...Cheating is fun!");

			if(arg1.toLowerCase().equals("food")){
				System.out.println("Now is time to set point per fruit!:/t");
				Scanner scannerIO = new Scanner(System.in);
				try{
					NibblesPanel.setLevelPoint(scannerIO.nextInt());
				}catch (IllegalArgumentException e){
					System.out.println("Sorry, not valid imput.");

				}
				scannerIO.close();
			}
			if(arg1.toLowerCase().equals("score")){
				System.out.println("Now is time to set your score!:/t");
				Scanner scannerIO = new Scanner(System.in);
				try{
					NibblesPanel.setScore(scannerIO.nextInt());
				}catch (IllegalArgumentException e){
					System.out.println("Sorry, not valid imput.");

				}
				scannerIO.close();
			}
			System.out.println("Dear cheat cheater,/n/t...Cheating is over");


		}


		contentPane.add(bar, BorderLayout.NORTH);
		bar.setPreferredSize(new Dimension(360, 40));
		bar.setMinimumSize(new Dimension(360, 40));



		currNibblesPanel= new NibblesPanel(360, 340);
		snakeSettings();
		
		
		cardsPanel.add(currNibblesPanel, "nibbles");
		cardsPanel.add(settingsController, "settings");

		contentPane.add(cardsPanel);

		pink.pause(true);
		blue.pause(true);
		green.pause(true);
		
		setPreferredSize(new Dimension(380, 420));
		setMinimumSize(new Dimension(380, 420));


		setDefaultCloseOperation(EXIT_ON_CLOSE);

		updateTitle();
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		setAlwaysOnTop(true);


	}


	private void neutrize(JButton button) {
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.setFocusable(false);
		button.addActionListener(this);


	}


	@Override
	public void actionPerformed(ActionEvent e) {
		Object source= e.getSource();

		if(source!=upLabeltimer){

			if (source==getHigth())
				upLevel();
			if(source==low)
				downLevel();
			if(source==newGameRadio && currNibblesPanel.isVisible()){
				try {
					currNibblesPanel.gameOver();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//NibblesPanel.inGame=false;
			}
			if(source==backToGame){
				moreOptions=false;
				CardLayout cl1 = (CardLayout)(cardsPanel.getLayout());
				CardLayout cl2 = (CardLayout)(upperCardsPanel.getLayout());
				cl1.next(cardsPanel);
				cl2.next(upperCardsPanel);
				newGameRadio.setText(Integer.toString(tempScore));
				
				
				pink.pause(true);
				green.pause(true);
				blue.pause(true);

				//
				//				if(preview!= null){
				//					preview.setVisible(false);
				//					LittleNibbles.timer.stop();
				//				}
				//
				//				if(settingsController!= null){
				//					settingsController.setVisible(false);
				//				}
				//				if(optionPanel!= null){
				//					optionPanel.setVisible(false);
				//				}
				//currNibblesPanel.repaint();

				setTitle("Nibbles");
				currNibblesPanel.requestFocusInWindow();

				//currNibblesPanel.setVisible(true);
			}
			if(source==settingsInUpperBar){		
				setTitle("Nibbles. Di Enrico Guglielmi");
				newGameRadio.setText("");

				CardLayout cl1 = (CardLayout)(cardsPanel.getLayout());
				CardLayout cl2 = (CardLayout)(upperCardsPanel.getLayout());
				currNibblesPanel.pause(true);
				cl2.next(upperCardsPanel);
				cl1.next(cardsPanel);
				tempScore= NibblesPanel.getScore();

				updateSettings();
				updateLowerPanel();


			}

			if (source == settings){
				CardLayout cl = (CardLayout)(lowerPanel.getLayout());
				moreOptions= !moreOptions;
				cl.next(lowerPanel);
				updateLowerPanel();
			}

		}
		else{
			upLabeltimer.stop();
			this.updateTitle();
		}
		//////




	}



	private void updateLowerPanel() {
		if(moreOptions){
			System.out.println("More option VERO: p:? = "+ LittleNibbles.timer.isRunning());

			pink.pause(true);
			blue.pause(true);
			green.pause(true);
			System.out.println("More option VERO: p:? = "+ LittleNibbles.timer.isRunning());


			classicMode.setEnabled(!NibblesPanel.isInGame());
			dietMode.setEnabled(!NibblesPanel.isInGame());
			
			if(tempScore>=1001 || classicMode.isSelected() && tempScore>=750 || indent)
				indentMode.setEnabled(true);

		}
		else{
			pink.pause(false);
			blue.pause(false);
			green.pause(false);
		}


	}

	private void updateSettings() {
		if(NibblesPanel.classic)
			isClassic.setText("(Classic Mode)");
		else
			isClassic.setText("(Normal Mode)");

		if(NibblesPanel.classic){
			firstName.setText(HightScoreReader.getBest2()); 
			firstScore.setText(Integer.toString(NibblesPanel.getClassicHightScore())); 
			secondName.setText(HightScoreReader.getCurrent2()); 
			secondScore.setText(Integer.toString(NibblesPanel.getClassicCurrentScore()));

		}
		else{
			firstName.setText(HightScoreReader.getBest()); 
			firstScore.setText(Integer.toString(NibblesPanel.getHightScore())); 
			secondName.setText(HightScoreReader.getCurrent()); 
			secondScore.setText(Integer.toString(NibblesPanel.getSecondScore())); 
		}
		System.out.println("Punteggio corrente: "+ tempScore);
		currName.setText("(#-) Punteggio corrente: "); 
		currScore.setText(Integer.toString(tempScore)); 

	}

	private void snakeSettings() {
		System.out.println("**Preferenze**\n");

		settingsController = new JPanel();

		ImageIcon st= new ImageIcon(getClass().getClassLoader().getResource("Sources/ScoresTable.png"));
		JLabel firstLine= new JLabel();// ("<html> <font size= +2>Tabellone punteggi");
		//firstLine.setPreferredSize(new Dimension(st.getIconWidth()/2, st.getIconHeight()/2));
		firstLine.setIcon(st);
		//			Dimension firstLineDimension = new Dimension(420, st.getIconHeight());
		//			firstLine.setPreferredSize(firstLineDimension);
		//			firstLine.setMaximumSize(firstLineDimension);
		//			firstLine.setMinimumSize(firstLineDimension);
		settingsController.add(firstLine, BorderLayout.NORTH);

		JPanel best= new JPanel();
		JPanel best1= new JPanel();
		JPanel best2= new JPanel();

		best.setLayout(new GridBagLayout());
		best1.setLayout(new GridBagLayout());
		best2.setLayout(new GridBagLayout());

		best.setFocusable(false);
		best1.setFocusable(false);
		best2.setFocusable(false);

		try {
			new HightScoreReader();
		} catch (Exception e) {
			e.printStackTrace();
		}

		//Divisi in name e score perche non precludo la possiblit� di salvare il punteggio ed abbinarlo ad
		//nick di chi l'ha fatto. Anche il writer e il reader sono pronti a gestire il caso
		//Bisogna solo implementare un entrata testo (dove chi gioca possa inserire il nome)
		//...un simil pop um pare brutto...Devo solo trovare un modo elegante per chiedere all'utente di inserire il suo nome insomma... :|

		isClassic= new JLabel();

		firstName = new JLabel();
		firstScore = new JLabel();
		secondName = new JLabel();
		secondScore = new JLabel();
		currName = new JLabel();
		currScore = new JLabel();


		updateSettings();

		firstName.setFocusable(false);
		firstScore.setFocusable(false);
		secondName.setFocusable(false);
		secondScore.setFocusable(false);
		currName.setFocusable(false);
		currScore.setFocusable(false);


		best.add(firstName); best.add(firstScore);
		best1.add(secondName); best1.add(secondScore);
		best2.add(currName); best2.add(currScore);




		JPanel bests= new JPanel();
		//bests.setLayout(new GridLayout(4, 1));





		best.setAlignmentX(Component.CENTER_ALIGNMENT);
		best1.setAlignmentX(Component.CENTER_ALIGNMENT);
		best2.setAlignmentX(Component.CENTER_ALIGNMENT);
		isClassic.setAlignmentX(Component.CENTER_ALIGNMENT);
		bests.setLayout(new BoxLayout(bests, BoxLayout.Y_AXIS));
		bests.add(best); bests.add(best1); bests.add(best2);
		bests.add(isClassic);
		Dimension dimension = new Dimension(360, 100);
		bests.setPreferredSize(dimension);
		bests.setMinimumSize(dimension);
		bests.setMaximumSize(dimension);

		settingsController.add(bests);




		settingsController.setPreferredSize(new Dimension(380, 380));
		settingsController.setMinimumSize(new Dimension(380, 380));
		settingsController.setMaximumSize(new Dimension(380, 380));

		settingsController.add(currTheme);
		setLowerPanel();
		settingsController.add(lowerPanel);


		settings= new JButton();
		neutrize(settings);
		settings.setIcon(new ImageIcon(getClass().getClassLoader().getResource("Sources/settings.png")));
		settingsController.add(settings, BorderLayout.PAGE_END);

	}


	private void setLowerPanel(){
		Dimension dimension = new Dimension(360, 90);
		lowerPanel= new JPanel();
		lowerPanel.setLayout(new CardLayout());
		lowerPanel.setPreferredSize(dimension);
		lowerPanel.setMinimumSize(dimension);
		lowerPanel.setMaximumSize(dimension);

		classicMode= new JCheckBox("Classic Mode");
		classicMode.setFocusable(false);classicMode.addActionListener(new PrefListener());
		//classicMode.setEnabled(!NibblesPanel.isInGame());
		classicMode.setSelected(NibblesPanel.classic);
		dietMode= new JCheckBox("Diet Mode");
		dietMode.setFocusable(false);dietMode.addActionListener(new PrefListener());
		//dietMode.setEnabled(!NibblesPanel.isInGame());
		dietMode.setSelected(NibblesPanel.diet);
		indentMode= new JCheckBox("007 Mode");
		indentMode.setFocusable(false);indentMode.addActionListener(new PrefListener());
		indentMode.setSelected(indent);
		vertigo= new JCheckBox("Vertigo");
		vertigo.setFocusable(false); vertigo.addActionListener(new PrefListener());
		vertigo.setSelected(desperado);
		//vertigo.setEnabled(false); 
		vertigo.setToolTipText("troppi bug...");

		optionPanel= new JPanel();
		optionPanel.add(classicMode);
		optionPanel.add(dietMode);
		//				optionPanel.add(vertigo);
	
		optionPanel.add(indentMode);
		//indentMode.setVisible(false);
		//indentMode.setEnabled(false);



		//optionPanel.setLayout(new BorderLayout());


		preview= new JPanel();
		preview.setLayout(new GridLayout(0, 3));


		pink= new LittleNibbles("pk");
		pink.addMouseListener(this);
		blue= new LittleNibbles("b");
		blue.addMouseListener(this);
		green= new LittleNibbles("g");
		green.addMouseListener(this);
		


		preview.add(pink);
		preview.add(green);
		preview.add(blue);


//		preview.setPreferredSize(dimension);
//		preview.setMinimumSize(dimension);
//		preview.setMaximumSize(dimension);


		lowerPanel.add(preview);
		lowerPanel.add(optionPanel);
		
		updateLowerPanel();
		
		

	}




	public void downLevel() {
		if(!NibblesPanel.isInGame()){
			cont--;
			if (cont<=1){
				cont=1;
				NibblesPanel.setDELAY(200);
				System.out.println("livello: "+Integer.valueOf(cont)+". Delay: "+Integer.toString(NibblesPanel.getDELAY()));
			}
			else if(cont>0){
				NibblesPanel.setDELAY(NibblesPanel.getDELAY()+20);
				System.out.println("livello: "+Integer.valueOf(cont)+". Delay: "+Integer.toString(NibblesPanel.getDELAY()));
			}
			printLevel();
		}

		else{
			upLabeltimer.start();
			updateTitle();
		}
	}
	public void upLevel() {
		if(!NibblesPanel.isInGame()){
			cont++;
			if(cont>=9){
				cont=9;
				NibblesPanel.setDELAY(40);
				System.out.println("livello: "+Integer.valueOf(cont)+". Delay: "+Integer.toString(NibblesPanel.getDELAY()));
			}
			else if(cont<9){
				NibblesPanel.setDELAY(NibblesPanel.getDELAY()-20);
				System.out.println("livello: "+Integer.valueOf(cont)+". Delay: "+Integer.toString(NibblesPanel.getDELAY()));
			}
			printLevel();
		}
		else{
			upLabeltimer.start();
			updateTitle();
		}
	}
	private static void printLevel() {
		// LOL! :D
		if(cont==1)
			level.setText("I        ");
		if(cont==2)
			level.setText("II       ");
		if(cont==3)
			level.setText("III      ");
		if(cont==4)
			level.setText("IIII     ");
		if(cont==5)
			level.setText("IIIII    ");
		if(cont==6)
			level.setText("IIIIII   ");
		if(cont==7)
			level.setText("IIIIIII  ");
		if(cont==8)
			level.setText("IIIIIIII ");
		if(cont==9)
			level.setText("IIIIIIIII");
	}
	public static int getCont() {
		return cont;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object source= e.getSource();
		if(source==level){
			level.setToolTipText(Integer.toString(cont));
			upLevel();
		}
		if(source==curr){
			if(!NibblesPanel.isInGame())
				curr.setToolTipText("Terminata! Punti bonus: "+NibblesPanel.getBonusPoint());
			else
				curr.setToolTipText("Punti bonus: "+NibblesPanel.getBonusPoint());

		}
	}

	//Ho il dubbio che il mouseListener non serva...
	//Sul lebel prefenenze non serve. Mah! Dubbi!
	@Override
	public void mouseEntered(MouseEvent e) {
		Object source= e.getSource();
		if(source==level){
			level.setToolTipText(Integer.toString(cont));
		}
		if(source==curr){

			if(!NibblesPanel.isInGame())
				curr.setToolTipText("Terminata! Punti bonus: "+NibblesPanel.getBonusPoint());
			else
				curr.setToolTipText("Punti bonus: "+NibblesPanel.getBonusPoint());
		}

		if(source==newGameRadio){
			newGameRadio.setToolTipText("Click per terminare la partita");
		}
	}
	@Override
	public void mouseExited(MouseEvent e) {
		//nada;
	}
	@Override
	public void mousePressed(MouseEvent e) {
		Object source= e.getSource();

		System.out.println("Click");

		if(source==level){
			level.setToolTipText(Integer.toString(cont));
		}
		if(source==curr){
			if(!NibblesPanel.isInGame())
				curr.setToolTipText("Terminata! Punti bonus: "+NibblesPanel.getBonusPoint());
			else
				curr.setToolTipText("Punti bonus: "+NibblesPanel.getBonusPoint());

		}
		if (source==pink){
			theme="Pink";
			pinkTheme=true;
			blueTheme=false;
			greenTheme=false;
			currTheme.setForeground(new Color(153,0,102));
			currNibblesPanel.oneTimePaint();
		}
		if (source==blue){
			theme="Blue";
			blueTheme=true;
			pinkTheme=false;
			greenTheme=false;
			currTheme.setForeground(new Color(0,26,153));
			currNibblesPanel.oneTimePaint();

		}
		if (source==green){
			theme="Green";
			greenTheme=true;
			pinkTheme=false;
			blueTheme=false;
			currTheme.setForeground(new Color(26,153,0));
			currNibblesPanel.oneTimePaint();

		}

		currTheme.setText("Mr. "+theme+" in action!\t");

	}
	@Override
	public void mouseReleased(MouseEvent e) {
	}

	public static void updateScore(){
		if(NibblesPanel.isInGame())
			newGameRadio.setText(Integer.toString(NibblesPanel.getScore()));
		else
			newGameRadio.setText("");
	}

	public static void timeInScoreLabel(int time){
		if(time>0)
			newGameRadio.setText("+"+Integer.toString(time));
		else
			updateScore();
	}

	private void updateTitle() {
		if(upLabeltimer.isRunning()){
			setTitle("Termina la partita...");
		}
		else{
			this.setTitle("Nibbles");
		}
	}


	public JRadioButton getHigth() {
		return higth;
	}


	public void setHigth(JRadioButton higth) {
		this.higth = higth;
	}


}
