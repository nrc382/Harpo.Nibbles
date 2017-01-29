package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;


public class LittleNibbles extends JPanel  implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int DOT_SIZE= 10;
	private final int ALL_DOTS;
	
	private Image body;
	private Image head;
	static Timer timer;
	private static int DELAY=100;

	
	// A!B! = up
	// A!B = right _ INT POINT / RES
	// AB! = left
	// AB= down
	
	private boolean A;
	private boolean B;

	private int x[];//= new int [ALL_DOTS];
	private int y[];//= new int [ALL_DOTS];

	private int dots;
	private int ds;
	private ImageIcon sh;
	private ImageIcon sb;
	private String c;
	private int h;
	private int w;




	public LittleNibbles(String color){
		c= color;
		
		if(color.equals("pk")){
			setBackground(new Color(197, 147, 214));

			sb= new ImageIcon(getClass().getClassLoader().getResource("Sources/sBodypink.png"));
			sh= new ImageIcon(getClass().getClassLoader().getResource("Sources/sHeadpink.png"));

		}
		if(color.equals("b")){
			setBackground(new Color(100, 183, 218));

			sb= new ImageIcon(getClass().getClassLoader().getResource("Sources/sBodyblue.png"));
			sh= new ImageIcon(getClass().getClassLoader().getResource("Sources/sHeadblue.png"));

		}
		if (color.equals("g")){
			setBackground(new Color(136, 204, 0));

			sb= new ImageIcon(getClass().getClassLoader().getResource("Sources/sBody.png"));
			sh= new ImageIcon(getClass().getClassLoader().getResource("Sources/sHead.png"));


		}
		if(NibblesFrame.indent){
			sb= new ImageIcon(getClass().getClassLoader().getResource("Sources/sBodyIndent.png"));
			sh= new ImageIcon(getClass().getClassLoader().getResource("Sources/sHeadIndent.png"));
		}

//		setPreferredSize(new Dimension(100, 70));
//		setMinimumSize(new Dimension(100, 70));
//		setMaximumSize(new Dimension(100, 70));

		body=sb.getImage();
		head=sh.getImage();

		timer = new Timer(DELAY, this);
		h= 80;
		w= 110;
		
		ALL_DOTS= (int)(12)*(10);
		x= new int [ALL_DOTS];
		y= new int [ALL_DOTS];

		
		
		
		startGame();
	}
	
	private void startGame() {
		Random generator = new Random();
		if ((ds= generator.nextInt(8))>=3)
			dots=ds;
		else
			dots=4;
		
		A=false;
		B=true;
		
		for(int i=0;i<dots; i++){
			x[i]= 60-(i*10);
			y[i]= 60;
		}
		timer.setDelay(DELAY);
		timer.start();
	}
	
	public void pause(boolean bool){
		if(bool){
			timer.stop();
		}
		else{
			timer.restart();
		}
	}
	
	private void checkBox() {
		
		// A!B! = up
		// A!B = right _ INT POINT / RES
		// AB! = left
		// AB= down
		
		if (y[0]>= h-DOT_SIZE || y[0]<= DOT_SIZE) {
			System.out.println(c+" Basso o alto ("+Integer.toString(y[0])+")");
			if(y[0]>= h-DOT_SIZE)
				y[0]= h-DOT_SIZE;
			else{
				y[0]= DOT_SIZE;
			}
			randLeftRight();

		}

		else if (x[0]> w-DOT_SIZE || x[0]<= DOT_SIZE) {
			System.out.println("sinistro o destro"+Integer.toString(y[0])+")");

			if(x[0]<= DOT_SIZE)
				x[0]= DOT_SIZE;
			else{
				x[0]= w-DOT_SIZE;
			}
			randUPDowns();

		}
		else{
			randMoves();
		}

		
		
	}



	public void randUPDowns() 
	{
		if(timer.isRunning()){
			Random generator = new Random();
			int x= generator.nextInt(6);
			
			// A!B! = up
			// A!B = right _ INT POINT / RES
			// AB! = left
			// AB= down
			
			if (x<3 && x>=0)// UP
			{
				A=B=false;
			}
			else{ // DOWN
				A=B=true;
			}
			
//			int h= this.getHeight();
//
//			if(y[0]<=10){
//				A= true; B=false;
//			}
//			if(y[0]>= h -10){
//				A= false; B=true;
//
//			}

		}
	}
	
	public void randLeftRight() 
	{
		if(timer.isRunning()){

			Random generator = new Random();
			int rand= generator.nextInt(5);
			// A!B! = up
			// A!B = right _ INT POINT / RES
			// AB! = left
			// AB= down
			
			if (rand<3 && rand>=0)// left
			{
				A=true;
				B=false;
			}
			else{ // right
				A=false;
				B=true;
			}
			
//			int w= this.getWidth();
//
//			if(x[0]<=10){
//				A=false;
//				B=false;
//			}
//			if(x[0]>= w -10){
//				A=true;
//				B=true;
//			}

		}
	}


	public void randMoves() 
	{
		if(timer.isRunning()){
			Random generator = new Random();
			int x= generator.nextInt(3);
			if(c.equals("pk"))
				System.out.println("RandomMoves di "+c+" A: "+A+"B: "+B);
			switch (x){
				case 0: if(!(A&B==true)) A=B=false;
				case 1: if(!(A==false && B==false)) A=B=true;
				case 2: if(!(A==true && B== false)){
					A= false;
					B= true;
				}
				case 3: if(!(A==false && B== true)){
					A= true;
					B= false;
				}
			}


		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {	
		if(timer.isRunning()){
		checkBox();
		//randMoves();
		move();    
		repaint();
		}
	}

	private void move() {
		for (int i=dots; i> 0; i--) {
			x[i]= x[(i - 1)];
			y[i]= y[(i - 1)];
		}
		
		
		// A!B! = up
		// A!B = right _ INT POINT / RES
		// AB! = left
		// AB= down
		

		//UP
		if (A==true && B==true) {
			//y[1] = y[0];
			y[0]-= DOT_SIZE;
		}
		//DOWN
		else if (A==false && B==false) {
			y[0]+= DOT_SIZE;
		}
		//Right
		else if (A==false && B==true) {
			x[0]+= DOT_SIZE;
		}
		//Left
		else if (A==true && B==false) {
			x[0]-= DOT_SIZE;
		}

		if(c.equals("pk"))
			System.out.println("1 Muovo "+c+"\nx: "+x[0]+" y: "+y[0]+"\nEra: A: "+A+" B: "+B);
		
	}
	
	public void paint(Graphics g){
		super.paint(g);
		g.drawRect(1, 1, this.getWidth()-1, this.getHeight()-1);
		for(int i=0; i<dots; i++){
			if (i==0)
				g.drawImage(head, x[i], y[i], this);
			else
				g.drawImage(body, x[i], y[i], this);
		}
		Toolkit.getDefaultToolkit().sync();
		g.dispose();

	}
	
	public static void setDelay(int delay2) {
		DELAY=delay2;
		timer.setDelay(DELAY);
	}




}