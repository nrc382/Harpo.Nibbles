package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.Timer;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import classAccessory.HightScoreReader;
import classAccessory.HightScoreWriter;


public class NibblesPanel extends JPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int larghezza= 0;
	private int altezza= 0;
	private final int DOT_SIZE= 20;
	private int ALL_DOTS=225;
	private int RAND_POS;
	private int allSteps;
	private int allStepsModule;

	private static int DELAY=60;


	private int x[];
	private int y[];

	private int dots;
	private int fruit_x; private int fruit_y;
	private int bonusF_x; private int bonusF_y;
	private int ligth_x; private int ligth_y;

	private boolean left= false;
	private boolean right= true;
	private boolean up= false;
	private boolean down= false;

	static boolean inGame= true;
	static boolean stop= false;

	public static boolean isStop() {
		return stop;
	}


	public static void setStop(boolean stop) {
//		NibblesPanel.stop = stop;
//		timer.stop();
	}


	static Timer timer;
	private Image pse;
	private Image body;
	private Image head;
	private Image fruit;
	private Image bonusF;
	private Image ligth;
	private boolean bonus=false;
	private int bonusTime;
	static boolean classic;
	static boolean diet;
	static boolean preemptive;
	
	private static int score=0;


	private static int hightScore;
	private static int secondScore;
	private static int classicHightScore;
	private static int classicCurrentScore;
	private static int levelPoint;
	
	
	


	public static boolean restart=false;
	private Font normal= new Font("Courier NEW", Font.BOLD, 45);
	private Font small= new Font("Courier NEW", Font.BOLD, 16);
	private ImageIcon sb;
	private ImageIcon sh;
	private int recalc=0;
	private static int bonusPoint=0;
	private int bonusNumbers=0;
	private int bonusNumberToken=0;
	private boolean pressedArrowflag= true;
	private int startCounter=3;
	private Timer startTimer;
	private boolean procedi;
	private int kLeft = KeyEvent.VK_LEFT;
	private int kRigth = KeyEvent.VK_RIGHT;
	private int kDown = KeyEvent.VK_DOWN;
	private int kUp = KeyEvent.VK_UP;


	public class TableAdapter extends KeyAdapter{// implements NIbblesInterface {

		public void keyPressed(KeyEvent e) {

			int key = e.getKeyCode();
		
			
			if(key== KeyEvent.VK_SHIFT && !isInGame()){
				System.out.println("Nuova partita");
				bonus=false;
				startNewGame();
			}

			if(key== KeyEvent.VK_SHIFT && stop==true){
				bonus=false;
				try {
					gameOver();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				startNewGame();
			}

			if(((key== KeyEvent.VK_SPACE) && isInGame()) || ((key != kLeft || key != kRigth || key != kUp || key != kDown) && !timer.isRunning() && isInGame())){
				stop=!stop;
				pause(stop);

			}


			if(pressedArrowflag){
				
				if ((key== kLeft) && (!right)) {
					left = true;
					up = false;
					down = false;
					pressedArrowflag= false;

				}
				if ((key == kRigth) && (!left)) {
					right = true;
					up = false;
					down = false;
					pressedArrowflag= false;

				}

				if ((key == kUp) && (!down)) {
					up = true;
					right = false;
					left = false;
					pressedArrowflag= false;

				}

				if ((key == kDown) && (!up)) {
					down = true;
					right = false;
					left = false;
					pressedArrowflag= false;

				}

			}
		}
	}

	

	public static int getBonusPoint() {
		return bonusPoint;
	}

	public NibblesPanel(int width, int height){
		
		if(classic)
			System.out.println("Modalità classica attiva");
		
		super.addKeyListener(new TableAdapter());
		
		diet= true;
		
		if(preemptive)
			System.out.println("Modalità pre emptive attiva");

		altezza=height;
		larghezza=width;
		allSteps=0;
		allStepsModule=0;

		ALL_DOTS=(int)((altezza/DOT_SIZE)*(larghezza/DOT_SIZE));
		System.out.println(ALL_DOTS);
		x= new int [ALL_DOTS];
		y= new int [ALL_DOTS];

		setBackground(new Color(136, 204, 0));
		sb= new ImageIcon(getClass().getClassLoader().getResource("Sources/sBodyBig.png"));
		sh= new ImageIcon(getClass().getClassLoader().getResource("Sources/sHeadBig.png"));
		ImageIcon tf= new ImageIcon(getClass().getClassLoader().getResource("Sources/fruitBig.png"));
		ImageIcon tbf= new ImageIcon(getClass().getClassLoader().getResource("Sources/bonusFBig.png"));
		ImageIcon lth= new ImageIcon(getClass().getClassLoader().getResource("Sources/ligth.png"));
		ImageIcon pauseImage= new ImageIcon(getClass().getClassLoader().getResource("Sources/pause.png"));

		body=sb.getImage();
		head=sh.getImage();
		fruit= tf.getImage();
		bonusF= tbf.getImage();
		ligth= lth.getImage();
		this.pse= pauseImage.getImage();


		try{ //Il codice si ripete, ma altrimenti non salva l'hightscore. :(
			new HightScoreReader();
		}catch (Exception e){	
		}



		timer = new Timer(DELAY, this);
		setFocusable(true);

		//		setPreferredSize(new Dimension(320, 320));
		//		setMinimumSize(new Dimension(320, 320));
		//		setMaximumSize(new Dimension(320, 320));



		startNewGame();
	}

	public void startNewGame() {

		System.out.println("Partita avviata con successo...\n");
		bonusNumberToken= 0;
		bonusPoint= 0;
		bonusNumbers= 0;
		recalc= 0;
		score=0;
		startCounter=3;
		procedi=false;
		NibblesFrame.updateScore();
		RAND_POS= altezza/DOT_SIZE;
		//Inizializzazione
		dots = 2;
		right = true;
		left = false;
		up = false;
		down = false;
		allSteps=0;
		allStepsModule=0;
		if(levelPoint == 0)
			levelPoint = NibblesFrame.getCont();
		System.out.println("Valore cibo: " + levelPoint);
		bonusTime=3000/DELAY; //Sempre tre secondi!
		System.out.println("Quattro secondi per il bonus. \n\t/* Per " +DELAY/1000+" frame al secondo, bonusTime vale: "+bonusTime+"*/");
		oneTimePaint();


		//Stampa dei "dots"
		for(int i=0;i<dots; i++){
			x[i]= 120-(i*DOT_SIZE);
			y[i]= 140;
		}
		setInGame(true); //yes!
		locateNew(); //...
		timer.setDelay(DELAY);


		//timer.setInitialDelay(3000);

		//Controllo su console...
		System.out.println("Modatlia' classica: "+classic);
		System.out.println("Delay: "+timer.getDelay());

		timer.start();
	}

	public void gameOver() throws IOException {
		Graphics g= this.getGraphics();
		inGame=false;
		//stop=false;
		gameOver(g);

	}

	@SuppressWarnings("unused")
	private ActionListener startTheGame() {
		System.out.println("Chiamato! contatore: "+startCounter);
		String toUse=Integer.toString(startCounter);
		if(startCounter>0){
			centerAndStampString(toUse,  larghezza+20, 0,altezza/2);
			startCounter--;
			System.out.println("Chiamato! contatore: "+startCounter);
		}
		else{
			System.out.println("Riavvio il gioco: "+startCounter);
			startTimer.stop();
			startCounter=3;
			timer.restart();
			stop=false;
			//timer.start();
		}
		return null;
	}

	public void move(){
		if(!stop){
			//Famo sparire sto for per la modalità irritante. Basta mettere un paio di condizioni al
			//collision check ;)

			for (int i=dots; i> 0; i--) {
				x[i]= x[(i - 1)];
				y[i]= y[(i - 1)];
			}
			
			if (left) {
				x[0]-= DOT_SIZE;
			}

			if (right) {
				x[0]+= DOT_SIZE;
			}

			if (up) {
				y[0]-= DOT_SIZE;
			}

			if (down) {
				y[0]+= DOT_SIZE;
			}
		}
	}

	public void checkCollision() {
		if (y[0]> altezza) {
			if(classic)
				setInGame(false);
			y[0]=0;
		}

		if (y[0]< 0) {
			if(classic)
				setInGame(false);
			y[0]=altezza;
		}

		if (x[0]> larghezza) {
			if(classic)
				setInGame(false);
			else
				x[0]=0;
		}

		if (x[0]< 0) {
			if(classic)
				setInGame(false);
			else
				x[0]=larghezza;
		}

		for (int z= dots; z > 0; z--) {
			if ((x[0]== x[z]) && (y[0]== y[z])) {
				setInGame(false);
			}
		}
	}

	public void checkFruit() {
		int i=2;
		if(dots>2)
			i=3;
		//if(!preemptive)
		i=0;

		if(fruit_x>=500){
			fruit_x=ligth_x;
			fruit_y=ligth_y;
			ligth_y=600;
			ligth_x=500;
		}

		if ((x[0]== fruit_x) && (y[0]== fruit_y)) {
			dots++;
			setScore(getScore()+levelPoint);
			procedi=true;
			locateNew();
		}

		if((x[i]== fruit_x) && (y[i]== fruit_y) && procedi && dots>=3){
			if(!diet && ((dots-2)%4== 0)){
				bonus=true;
				fruit_x=500;
				fruit_y=600;

				bonusF_x=ligth_x;
				bonusF_y=ligth_y;
			}
			else{
				fruit_x=ligth_x;
				fruit_y=ligth_y;
			}
			ligth_y=600;
			ligth_x=500;	
			procedi=false;

		}
	}
	
	private void locateNew() {
		int rand= (int) (Math.random()*RAND_POS);
		int Tempfruit_x=(rand*DOT_SIZE);
		rand= (int) (Math.random()*RAND_POS);
		int Tempfruit_y=(rand*DOT_SIZE);


		//System.out.println(Integer.toString(Tempfruit_x)+Integer.toString(Tempfruit_y));
		for (int z= dots + 1; z > 0; z--) {
			while((Tempfruit_x== x[z]) && (Tempfruit_y== y[z])) // || onScoreLine(Tempfruit_x, Tempfruit_y)){
			{
				recalc++;
				System.out.println("Caso maledetto!");
				rand= (int) (Math.random()*RAND_POS);
				Tempfruit_x=(rand*DOT_SIZE);
				rand= (int) (Math.random()*RAND_POS);
				Tempfruit_y=(rand*DOT_SIZE);
				z=dots;
			}
		}

		ligth_x=Tempfruit_x;
		ligth_y=Tempfruit_y;

		if(dots<3){
			fruit_x=ligth_x;
			fruit_y=ligth_y;
			ligth_y=600;
			ligth_x=500;
		}


	}
	

//	private boolean onScoreLine(int x, int y){
	//		if(x>=250 && y<30){
	//			System.out.println("Caso maledetto, proprio sul punteggio? ("+x+"-"+y+")");
	//			return true;
	//		}
	//		return false;
	//	}

	private void checkBonus() {
		if (!NibblesFrame.desperado) {
			if (bonus) {
				int i=3;
				//if(!preemptive)
				i=0;

				bonusTime--;
				if (bonusTime < 20) {
					bonusNumbers++;
					bonus = false;
					bonusTime = 65;
					procedi=false;
					locateNew();
				}
				else if ((x[0] == bonusF_x) && (y[0] == bonusF_y)) {
					System.out.println("Bonus preso!! Yeah, +" + bonusTime+"pt!!");
					dots++;
					setScore(getScore() + bonusTime);
					procedi=true;
					locateNew();
				}
				if((x[i]== bonusF_x) && (y[i]== bonusF_y) && procedi){
					bonusF_x=500;
					bonusF_y=600;
					bonusNumberToken++;
					bonusPoint += bonusTime;
					bonusTime = 65;
					bonus = false;
					fruit_x=ligth_x;
					fruit_y=ligth_y;
					ligth_y=600;
					ligth_x=500;
					procedi=false;
				}
			}
		}

	}

	private void centerAndStampString(String s, int w, int XPos, int YPos){ 
		Graphics g= this.getGraphics();
		int width=w;
		int stringLen = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();  
		int start = width/2 - stringLen/2;  
		g.drawString(s, start + XPos, YPos);  

	} 

	public void pause(boolean bool) {
		stop=bool;
		if(bool){
			Graphics g= this.getGraphics();
			g.setColor(new Color(1, 1, 1, .95f));
			g.fillRect(0, 0, super.getWidth(),super.getHeight());

			//g.setColor(new Color(255, 255, 255));
			g.drawImage(pse,(super.getWidth()/2)-(pse.getWidth(this)/2), (super.getHeight()/3)-(pse.getHeight(this)/2), this);
			centerAndStampString("Premi una direzione per riprendere.",  larghezza+20, 0,(int)(altezza*0.75));
			centerAndStampString("(Maiuscole (⇧) per una nuova partita)",  larghezza+20, 0,(int)(altezza*0.85));
			
			g.dispose();
			timer.stop();

//			left=false;
//			up=false;
//			down=false;
//			right=false;
		}
		else{
			System.out.println("Ricomincio...");
			timer.restart();
		}
		
	}
	
	public void oneTimePaint(){
		
		
		
		if(NibblesFrame.greenTheme){
			this.setBackground(new Color(136, 204, 0));
			//g.setColor(new Color(0, 73, 9));
			sb= new ImageIcon(getClass().getClassLoader().getResource("Sources/sBodyBig.png"));
			sh= new ImageIcon(getClass().getClassLoader().getResource("Sources/sHeadBig.png"));
		}
		if(NibblesFrame.pinkTheme){
			this.setBackground(new Color(197, 147, 214));
			//g.setColor(new Color(252, 229, 255));
			sb= new ImageIcon(getClass().getClassLoader().getResource("Sources/sBodypinkBig.png"));
			sh= new ImageIcon(getClass().getClassLoader().getResource("Sources/sHeadpinkBig.png"));
		}

		if(NibblesFrame.blueTheme){
			this.setBackground(new Color(100, 183, 218));
			//g.setColor(new Color(229, 253, 255));
			sb= new ImageIcon(getClass().getClassLoader().getResource("Sources/sBodyblueBig.png"));
			sh= new ImageIcon(getClass().getClassLoader().getResource("Sources/sHeadblueBig.png"));

		}

		if(NibblesFrame.indent){
			sb= new ImageIcon(getClass().getClassLoader().getResource("Sources/sBodyIndentBig.png"));
			sh= new ImageIcon(getClass().getClassLoader().getResource("Sources/sHeadIndentBig.png"));
		}

		if(NibblesFrame.desperado){
			this.setBackground(new Color(211, 52, 52));
			//g.setColor(new Color(255, 150, 0));
		}

		body=sb.getImage();
		head=sh.getImage();
		
		//g.dispose(); 

	}

	public void paint(Graphics g){
		super.paint(g);
		if(isInGame()){
			if(classic)
				g.drawRect(0, 0, super.getWidth(), super.getHeight());

			g.setFont(new Font("Sand", Font.ITALIC, 11));
			g.drawImage(fruit, fruit_x, fruit_y, this);
			//g.drawImage(ligth, ligth_x, ligth_y, this);
//			if(dots>100 && dots<156){
//				g.setColor(new Color(248, 253, 255));
//				g.drawString("Attenzione(!): -"+Integer.toString(156-dots), 6, 10);
//			}
			if(bonus){
				//g.setFont(new Font("Serif", Font.BOLD, 14));
				//g.drawString("+"+Integer.toString((bonusTime-20)), this.getWidth()-20, 20);
				NibblesFrame.timeInScoreLabel(bonusTime-20);
				g.drawImage(bonusF, bonusF_x, bonusF_y, this);
			}

			for(int i=0; i<dots; i++){
				if (i==0)
					g.drawImage(head, x[i], y[i], this);
				else
					g.drawImage(body, x[i], y[i], this);
			}
			Toolkit.getDefaultToolkit().sync();
			g.dispose(); //pezzi!!!!
		} else
			try {
				gameOver(g);
			} catch (IOException e) {
				e.printStackTrace();
			}

	}

	private void stampInfo(){
		if(bonusNumberToken>1){
			System.out.println("In questa partita hai accumulato "+bonusPoint+" punti bonus, prendendo "+bonusNumberToken+" volte il 'triangolo', su un totale di "+((bonusNumbers+bonusNumberToken)*65)+" punti disponibili. ;)");
			if(bonusNumbers-bonusNumberToken>1)
				System.out.println("Ne hai persi "+(bonusNumbers-bonusNumberToken)+". (Prendendo tutti i bonus Avresti ottenuto massimo"+((bonusNumbers+bonusNumberToken)*65-bonusPoint)+"pt in piu!).");
		}
		if(recalc>0){
			System.out.println("Durante questa partita ho risettato "+recalc+" volte la posizione del frutto/bonus.");
			System.out.println("Te ne sei accorto?\n");
		}
	}
	
	private boolean gameOver(Graphics g) throws IOException {
		timer.stop(); //Fermiamo il tempo!
		System.out.println("*** Partita terminata :( ***\n");
		
		this.stampInfo();
		

		Dimension dcurrDimension= this.getSize();
		String gOver= "GAME OVER";
		String pt=Integer.toString(getScore());//+"\n("+ Integer.toString(this.allSteps)+" passi)";
		String alrt="\n(Shift (⇧) per una nuova partita)";
		//String opinion=manageOpinionString();
		FontMetrics metr = this.getFontMetrics(normal);
		FontMetrics metr2 = this.getFontMetrics(small);

		g.setFont(normal);
		if(NibblesFrame.pinkTheme)
			g.setColor(new Color(146, 87, 155));
		if(NibblesFrame.blueTheme)
			g.setColor(new Color(5, 115, 161));
		if(NibblesFrame.greenTheme)
			g.setColor(new Color(90,153,0));
		if(NibblesFrame.desperado)
			g.setColor(new Color(255,0,0));

		g.drawString(gOver, (dcurrDimension.width-metr.stringWidth(gOver))/2, (metr.getAscent()+(dcurrDimension.height-(metr.getAscent()+metr.getDescent())))/4);
		g.drawString(pt,(dcurrDimension.width-metr.stringWidth(pt))/2, (metr.getAscent()+(dcurrDimension.height-(metr.getAscent()+metr.getDescent())))/2);
		g.setFont(small);
		g.drawString(alrt,(dcurrDimension.width-metr2.stringWidth(alrt))/2, (metr2.getAscent()+((dcurrDimension.height-(20+metr2.getAscent()+metr2.getDescent())))));
		
		if (!NibblesFrame.desperado){
			if (higthscore(getScore())){
				String newRecord="Congratulazioni!!!";String newRecord2= "Nuovo Record!";
				//opinion="Eccellente";
				g.setColor(new Color(216,216,216));
				g.setFont(normal);
				g.drawString(newRecord, (dcurrDimension.width-metr.stringWidth(newRecord))/2, (metr.getAscent()+(dcurrDimension.height-(metr.getAscent()+metr.getDescent())))/2+20);
				g.drawString(newRecord2, (dcurrDimension.width-metr.stringWidth(newRecord2))/2, (metr.getAscent()+(dcurrDimension.height-(metr.getAscent()+metr.getDescent())))/2+50);
			}
		}
		return true;
	}

	private boolean higthscore(int tempScore) {
		if(classic){
			try{
				new HightScoreWriter();
			}catch (Exception e){	
			}
			if(getScore()>=classicHightScore){
				classicHightScore= getScore();
				try{
					new HightScoreWriter();
				}catch (Exception e){	
				}
				return true;
			}
			else if (getScore()>=classicCurrentScore){
				setClassicCurrentScore(getScore());
				try{
					new HightScoreWriter();
				}catch (Exception e){	
				}
				return false;
			}
		}


		if(!classic){
			try{
				new HightScoreWriter();
			}catch (Exception e){	
			}
			if(getScore()>=hightScore){
				hightScore= getScore();
				try{
					new HightScoreWriter();
				}catch (Exception e){	
				}
				return true;
			}
			else if(getScore()>=secondScore){
				setSecondScore(getScore());
				try{
					new HightScoreWriter();
				}catch (Exception e){	
				}
				return false;
			}
		}
		return false;
	}

	

	@Override
	public void actionPerformed(ActionEvent e) {
		if (isInGame() && timer.isRunning()) {
			checkCollision();
			
			if(bonus && !diet)
				checkBonus();
			else 
				checkFruit();
			
			move(); 
			pressedArrowflag=true;
			repaint();
			
//			if(allStepsModule>= DOT_SIZE){
//				allStepsModule=0;
				allSteps++;
//			}
//			else{
//				allStepsModule++;
//			}
			if(diet){
				if(0==(allSteps%(100-dots))){
					if(dots>=2){
						dots--;
						System.out.println("Perdi le tue energie!");
						allSteps=0;
	
					}
					
					else{
						try {
							gameOver();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		}	
//		if(!isInGame() && dots>100 && dots<156){ //Modalità spettacolo!
//			setInGame(true);
//			pressedArrowflag=true;
//		}
		//flag=true;
	}

	public static void setDELAY(int i) {
		DELAY=i;
	}

	public static int getDELAY() {
		return DELAY;
	}

	public static void setSecondScore(int secondScore2) {
		secondScore = secondScore2;
	}

	public static int getSecondScore() {
		return secondScore;
	}

	public static int getScore() {
		return score;
	}

	public void setInGame(boolean inGame) {
		NibblesPanel.inGame = inGame;
	}

	public static boolean isInGame() {
		return inGame;
	}

	public static int getHightScore() {
		return hightScore;
	}

	public static void setHightScore(String string) {
		hightScore =Integer.valueOf(string.replaceAll(":", ""));

	}
	
	public static int getClassicHightScore() {
		return classicHightScore;
	}

	public static void setClassicHightScore(int classicHightScore) {
		NibblesPanel.classicHightScore = classicHightScore;
	}

	public static int getClassicCurrentScore() {
		return classicCurrentScore;
	}

	public static void setClassicCurrentScore(int classicCurrentScore) {
		NibblesPanel.classicCurrentScore = classicCurrentScore;
	}

	public static void setScore(int score) {
		NibblesPanel.score = score;
		NibblesFrame.updateScore();
	}

	public static int getLevelPoint() {
		return levelPoint;
	}


	public static void setLevelPoint(int levelPoint) {
		NibblesPanel.levelPoint = levelPoint;
	}
}

