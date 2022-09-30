package application;


import java.util.ArrayList;
import java.util.Collection;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.LongProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;


import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Main extends Application {

	Stage stage;
	Pane root;
	Timeline gameLoop;
	ImageView player,pnor,over;
	ImageView playersolid;
	ImageView back;
	Text Score;
	
	//Image
	Image play = new Image(getClass().getResourceAsStream("rex.png"));
	Image nor = new Image(getClass().getResourceAsStream("nor1.png"));
	Image img_playersolid = new Image(getClass().getResourceAsStream("playersolid.png"));
	Image cactus1 = new Image(getClass().getResourceAsStream("cactus1.png"));
	Image cactus2 = new Image(getClass().getResourceAsStream("cactus2.png"));
	Image cactus3 = new Image(getClass().getResourceAsStream("cactus3.png"));
	Image cactus4 = new Image(getClass().getResourceAsStream("cactus4.png"));
	Image background = new Image(getClass().getResourceAsStream("backgroud.png"));
	Image dead = new Image(getClass().getResourceAsStream("dead.png"));
	//Animation
	Animation animationwalk;
	Animation animationjump;
	
	int Timecount=0, score, blockID,  Pre_block = -1, Now_block = -1;
	double FPS = 70,FPS1 = 400;
	boolean GameOver = false, stop = false, hurt = false;
	private final int width = 1050, height = 600; // set screen size
	final double initialSpeed = 24, g = 1 ; // move speed
	double speed = -20 ;
	//final double minX = 0, maxX = width - 32; // move range
	ArrayList<Block> listOfBlocks = new ArrayList<>();

	public void start(Stage primaryStage) {
		stage = primaryStage;
		root = new Pane();

		root.setTranslateX(0);
		root.setTranslateY(0);
		root.setPrefWidth(width);
		root.setPrefHeight(height);
		
		
		player = new ImageView(play);
		player.setTranslateX(100);
		player.setTranslateY(350);
		
		pnor = new ImageView(nor);
		pnor.setTranslateX(100);
		pnor.setTranslateY(350);
		
		over = new ImageView(dead);
		over.setTranslateX(pnor.getTranslateX());
		over.setTranslateY(pnor.getTranslateY()+4);
		
		playersolid = new ImageView(img_playersolid);
		
		//set back
		back = new ImageView(background);
		back.setFitHeight(600);
		back.setFitWidth(1050);
		
		
		
		Scene scene = new Scene(root, width, height );
		primaryStage.setScene(scene);
		
		primaryStage.show();
		animationwalk = new SpriteAnimation(player, Duration.millis(250),2,2,92,0,92,120);
		animationwalk.setCycleCount(-1);
		animationwalk.play();
		animationjump = new Timeline(new KeyFrame(Duration.millis(1000 / FPS), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {			
					pnor.setTranslateY(pnor.getTranslateY()- speed);
					speed -= g ;
					if(pnor.getTranslateY()>350) {
						animationjump.stop();
						speed =-20;
						if(speed ==-20) {
							pnor.setVisible(false);
							player.setVisible(true);
						}		
					
				}
			}}	));
		
		animationjump.setCycleCount(-1);
				scene.setOnKeyPressed( new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (!GameOver) {
					if(event.getCode()==KeyCode.SPACE&&speed == -20) {
					
						pnor.setVisible(true);
						player.setVisible(false);
						speed = initialSpeed ;
						animationjump.play();
					
					}
				}
				if (event.getCode() == KeyCode.ENTER) {
					if (GameOver)
						initialize();
				}
		}});
		
		game();	
		
	}
	void cactus1() {
		Block block = new Block( cactus1,  width , 397);
		
		listOfBlocks.add(block);
		root.getChildren().add(block.block);
	
	}
	void cactus2() {
		Block block = new Block( cactus2,  width , 385);
		listOfBlocks.add(block);
		root.getChildren().add(block.block);
	
	}
	void cactus3() {
		Block block = new Block( cactus3,  width , 385);
		listOfBlocks.add(block);
		root.getChildren().add(block.block);
	
	}
	void cactus4() {
		Block block = new Block( cactus4,  width , 389);
		listOfBlocks.add(block);
		root.getChildren().add(block.block);
		
	}
	void game() {
		gameLoop = new Timeline(new KeyFrame(Duration.millis(1000 / FPS1), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				playersolid.setTranslateX(player.getTranslateX() + 91);
				playersolid.setTranslateY(player.getTranslateY());
				if(collisions()) {
					pnor.setVisible(false);
					player.setVisible(false);
					over.setVisible(true);
					GameOver = true;
					gameLoop.stop();
				}
				if (Timecount % (FPS1) == 0) {
					switch ((int) (Math.random() * 6) + 1) {
					case 1:
						cactus1();
						break;
					case 2:
						cactus2();
						break;
					case 3:
						cactus3();
						break;
					case 4:
						cactus4();
						break;
					default:
						break;
					}
				}
				for (int i = 0; i < listOfBlocks.size(); i++) {
					listOfBlocks.get(i).positionX -= 1;
					listOfBlocks.get(i).block.setTranslateX(listOfBlocks.get(i).positionX);
					

					
					if (listOfBlocks.get(i).positionY <= 0) {
						listOfBlocks.remove(i);
						root.getChildren().remove(5);
						root.getChildren().remove(5);
					}
				}	
				Timecount++;
			}}));
		gameLoop.setCycleCount(-1) ;
		gameLoop.play();
		initialize();
	}
		
	boolean collisions() {
		for (int i = 0; i < listOfBlocks.size(); i++) {
			if (pnor.getBoundsInParent().intersects(listOfBlocks.get(i).block.getBoundsInParent())) {
				
				return true;
			}
		}
		return false;
	}
	void initialize() {

		Timecount = 0;
		GameOver = false;
		listOfBlocks.clear();
		root.getChildren().clear();
		root.getChildren().add(back);
		root.getChildren().add(player);
		player.setVisible(true);
		root.getChildren().add(playersolid);
		root.getChildren().add(pnor);
		pnor.setVisible(false);
		root.getChildren().add(over);
		over.setVisible(false);
		gameLoop.play();
		
	}

	public static void main(String[] args) {
		launch(args);
	}

}