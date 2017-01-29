package classAccessory;

import game.NibblesPanel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class HightScoreWriter {
	private BufferedWriter innerWriter;
	private String savePath="NO PATH";
	public static final String FINALSEPARATOR="/";
	public static final String LINESEPARATOR="/";
	public static final String SEPARATOR = ";";
	public static final String POINTSEPARATOR = ":";

	public HightScoreWriter() throws IOException{
			System.out.println("Inizio a salvare...");
			File loader= new File(classAccessory.SettingsDir.getSettingsDirectory().getAbsolutePath()+"/SnakeScore.txt");
			savePath= loader.getAbsolutePath();
			if(loader.exists())
				System.out.println("Punteggi Snake salvati.");
			if(!loader.exists())
				loader.createNewFile();
			innerWriter= new BufferedWriter(new FileWriter(loader));
			saveScore();
			innerWriter.close();
		}
	
	public String getSavePath(){
		return savePath;
	}

	public void saveScore() throws IOException{
		StringBuilder sb= new StringBuilder();
		//sb.append("(#1) "+HightScoreReader.getBest()+". Punteggio    "+POINTSEPARATOR);
		sb.append("(#1) Miglior punteggio   "+POINTSEPARATOR);
		sb.append(Integer.toString(NibblesPanel.getHightScore())+";");
		sb.append("(#2) Secondo punteggio   "+POINTSEPARATOR);
		sb.append(Integer.toString(NibblesPanel.getSecondScore())+";");
		//sb.append("(#1) "+HightScoreReader.getBest2()+" (Classic Mode)  "+POINTSEPARATOR);
		sb.append("(#1) Miglior punteggio   "+POINTSEPARATOR);
		sb.append(Integer.toString(NibblesPanel.getClassicHightScore())+";");
		sb.append("(#2) Secondo punteggio   "+POINTSEPARATOR);
		sb.append(Integer.toString(NibblesPanel.getClassicCurrentScore())+"//");
		
		innerWriter.write(sb.toString());
	}

}
