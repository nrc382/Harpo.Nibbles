package classAccessory;

import game.NibblesPanel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
//import java.io.Writer;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;






public class HightScoreReader {
	
	public static final String LINESEPARATOR="//";
	public static final String SEPARATOR = ";";
	public static final String POINTSEPARATOR = ":";
	private static String line;
	private BufferedReader innerReader;
	private static StringTokenizer tokenizer;
	private static String best="Migliore";
	private static String best2="Migliore";
	private static String current;
	private static String current2;
	
	public static String getBest() {
		return best;
	}

	public static void setBest(String bestA) {
		best = bestA;
	}

	public static String getBest2() {
		return best2;
	}

	public static void setBest2(String bestA2) {
		best2 = bestA2;
	}
	
	
	public static String getCurrent() {
		return current;
	}

	public static void setCurrent(String bestA) {
		current = bestA;
	}

	public static String getCurrent2() {
		return current2;
	}

	public static void setCurrent2(String bestA2) {
		current2 = bestA2;
	}
	
	

	

	public HightScoreReader() throws Exception {
		try{
		File saver= new File(SettingsDir.getSettingsDirectory().getAbsolutePath()+"/SnakeScore.txt");
		if(!saver.exists()){
			System.out.println("Nessun dato precedente...");
			saver.createNewFile();
			HightScoreWriter writer = null;
			try{
				 writer = new HightScoreWriter();
			}catch (Exception e){
				System.out.println("Impossibile creare file in: "+ writer.getSavePath());

			}
		}
		innerReader=  new BufferedReader(new FileReader(saver));
		line = innerReader.readLine();
		readLines();
		}catch (IOException ex){
			
		}
		innerReader.close();
		
	}

	public static void readLines() throws Exception {
		try{
			tokenizer = new StringTokenizer(line);
			setBest(tokenizer.nextToken(POINTSEPARATOR+"\n").replace(";", ""));
			NibblesPanel.setHightScore(tokenizer.nextToken(SEPARATOR));
			setCurrent(tokenizer.nextToken(POINTSEPARATOR).replace(";", ""));
			NibblesPanel.setSecondScore(Integer.valueOf(tokenizer.nextToken(SEPARATOR).replaceAll(":", "")));
			setBest2(tokenizer.nextToken(POINTSEPARATOR).replace(";", ""));
			NibblesPanel.setClassicHightScore(Integer.valueOf(tokenizer.nextToken(SEPARATOR).replaceAll(":", "")));
			setCurrent2(tokenizer.nextToken(POINTSEPARATOR).replace(";", ""));
			NibblesPanel.setClassicCurrentScore(Integer.valueOf(tokenizer.nextToken(LINESEPARATOR).replaceAll(":", "")));
			
//			System.out.println("Miglior punteggio: "+NibblesPanel.getHightScore());
//			System.out.println("Miglior punteggio (classica): "+NibblesPanel.getClassicHightScore());
			}catch (NoSuchElementException e)
			{
				throw new Exception(e);
			}
	}


}
