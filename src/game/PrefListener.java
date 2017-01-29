package game;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrefListener implements ActionListener {
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source= e.getSource();
//		if(source==LittleNibbles.getButton()){
//			
//		}
		if(source==NibblesFrame.vertigo)
			NibblesFrame.desperado=NibblesFrame.vertigo.isSelected();
		if(source==NibblesFrame.indentMode){
			NibblesFrame.indent=NibblesFrame.indentMode.isSelected();

		}
		if (source==NibblesFrame.classicMode){
			NibblesPanel.classic=NibblesFrame.classicMode.isSelected();
			NibblesPanel.setScore(0);
			
		}
		if (source==NibblesFrame.dietMode){
			NibblesPanel.diet=NibblesFrame.dietMode.isSelected();
			NibblesPanel.setScore(0);
		}

		
		

	}

}
