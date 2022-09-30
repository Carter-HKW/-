package application;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Block extends Group{
	
	double positionX,positionY;
	ImageView block;


	public Block(Image block,double positionX,double positionY){
		
		
		this.positionX=positionX;
		this.positionY=positionY;
		this.block= new ImageView(block);
		this.block.setTranslateX(positionX);
		this.block.setTranslateY(positionY);
	
	}
}
