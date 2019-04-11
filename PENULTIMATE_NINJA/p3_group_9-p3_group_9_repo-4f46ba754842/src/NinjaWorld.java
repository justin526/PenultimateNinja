import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Screen;
import javafx.util.Duration;

public class NinjaWorld extends World {
	Ninja n;
	NinjaWorld w;
	MediaPlayer mp;
	ImageView health;
	Media titleTheme = new Media(new File("TitleTheme.mp3").toURI().toString());
	Media stageTheme = new Media(new File("StageTheme.mp3").toURI().toString());
	private int currentLevel = -1;
	private int bubbleCounter = 0;
	private double scalar = Screen.getPrimary().getBounds().getMaxX() / 1366 * 40;
	private int maxLevelCompleted = 1;
	PlayButton b;
	ImageView play;
	ImageView back;
	Shuriken shr;
	Rope r;

	public NinjaWorld() {
		w = this;
		mp = new MediaPlayer(titleTheme);
		mp.setVolume((0.5));
		mp.play();
		mp.setOnEndOfMedia(new Runnable() {
			@Override
			public void run() {
				mp.seek(Duration.ZERO);
			}
		});
		//		setOnKeyPressed(new EventHandler<KeyEvent>() {
		//			@Override
		//			public void handle(KeyEvent event) {
		//				if (event.getCode() == KeyCode.SPACE && currentLevel == -1) {
		//					setLevel(0);
		//					remove(b);
		//					pause();
		//				}
		//				if (event.getCode() == KeyCode.SPACE && currentLevel == 0) {
		//					getChildren().remove(play);
		//					getChildren().remove(back);
		//					setLevel(maxLevelCompleted);
		//				}
		//			}
		//		});
		//		setOnKeyReleased(new EventHandler<KeyEvent>() {
		//			@Override
		//			public void handle(KeyEvent event) {
		//				if (event.getCode() == KeyCode.SPACE) {
		//					synchronized (getOnKeyPressed()) {
		//						notifyAll();
		//					}
		//				}
		//			}
		//		});
		//	}
		//	void pause() {
		//		synchronized (getOnKeyPressed()) {
		//			while (currentLevel == 0) {
		//				try {
		//					getOnKeyReleased().wait();
		//				} catch (InterruptedException e) {
		//				}catch(IllegalMonitorStateException e){
		//					synchronized(getOnKeyReleased()){
		//						notifyAll();
		//					}
		//				}
		//			}
		//		}
	}

	public void setLevel(int level) {
		currentLevel = level;
		if (level == -1) {
			// title screen
			Image img = new Image("NINJA_TITLE.jpg", this.getWidth(), this.getHeight(), true, true);
			BackgroundImage bi = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
					BackgroundPosition.CENTER,
					new BackgroundSize(img.getWidth(), img.getHeight(), true, true, true, false));
			super.setBackground(new Background(bi));
			b = new PlayButton();
			super.getChildren().add(b);
			b.setX(Screen.getPrimary().getBounds().getMaxX() / 3);
			b.setY(Screen.getPrimary().getBounds().getMaxY() * 7 / 8);
			b.setOnMouseClicked(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent event) {
					setLevel(0);
					remove(b);
				}
			});
		} else if (level == 0) {
			// level select screen
			Image img = new Image("LevelSelectScreen.jpg", this.getWidth(), this.getHeight(), true, true);
			BackgroundImage bi = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
					BackgroundPosition.CENTER,
					new BackgroundSize(img.getWidth(), img.getHeight(), true, true, true, false));
			super.setBackground(new Background(bi));
			Level L1 = new Level(1);
			L1.setMaxLevel(maxLevelCompleted);
			Level L2 = new Level(2);
			L2.setMaxLevel(maxLevelCompleted);
			Level L3 = new Level(3);
			L3.setMaxLevel(maxLevelCompleted);
			Level L4 = new Level(4);
			L4.setMaxLevel(maxLevelCompleted);
			Image p1 = new Image("Play.PNG", Screen.getPrimary().getBounds().getMaxX() / 1366 * 127,
					Screen.getPrimary().getBounds().getMaxY() / 768 * 41, false, false);
			Image p2 = new Image("Play-2.PNG", Screen.getPrimary().getBounds().getMaxX() / 1366 * 127,
					Screen.getPrimary().getBounds().getMaxY() / 768 * 41, false, false);
			Image b1 = new Image("Back.PNG", Screen.getPrimary().getBounds().getMaxX() / 1366 * 127,
					Screen.getPrimary().getBounds().getMaxY() / 768 * 41, false, false);
			Image b2 = new Image("Back-2.PNG", Screen.getPrimary().getBounds().getMaxX() / 1366 * 127,
					Screen.getPrimary().getBounds().getMaxY() / 768 * 41, false, false);
			play = new ImageView(p1);
			back = new ImageView(b1);
			play.setX(Screen.getPrimary().getBounds().getMaxX()*2/5);
			play.setY(Screen.getPrimary().getBounds().getMaxY()*2/3);
			back.setX(Screen.getPrimary().getBounds().getMaxX()*3/5);
			back.setY(Screen.getPrimary().getBounds().getMaxY()*2/3);
			play.setOnMouseEntered(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent event) {
					play.setImage(p2);
				}
			});
			play.setOnMouseExited(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent event) {
					play.setImage(p1);
				}
			});
			play.setOnMouseClicked(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent event) {
					setLevel(maxLevelCompleted);
					getChildren().remove(play);
					getChildren().remove(back);
				}
			});
			back.setOnMouseEntered(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent event) {
					back.setImage(b2);
				}
			});
			back.setOnMouseExited(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent event) {
					back.setImage(b1);
				}
			});
			back.setOnMouseClicked(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent event) {
					setLevel(-1);
					getChildren().remove(L1);
					getChildren().remove(L2);
					getChildren().remove(L3);
					getChildren().remove(L4);
					getChildren().remove(play);
					getChildren().remove(back);
				}
			});
			super.getChildren().addAll(L1,L2,L3,L4,play,back);

		} else {
			if(getChildren().contains(play)){
				getChildren().remove(play);
			}
			if(getChildren().contains(back)){
				getChildren().remove(back);
			}
			if(level>maxLevelCompleted){
				maxLevelCompleted = level;
			}
			// loads background
			mp.stop();
			mp = new MediaPlayer(stageTheme);
			mp.setVolume((0.5));
			mp.play();
			mp.setOnEndOfMedia(new Runnable() {
				@Override
				public void run() {
					mp.seek(Duration.ZERO);
				}
			});
			Image img = new Image("NINJA_WORLD.jpg", this.getWidth(), this.getHeight(), true, true);
			BackgroundImage bi = new BackgroundImage(img, BackgroundRepeat.REPEAT, BackgroundRepeat.SPACE,
					BackgroundPosition.DEFAULT,
					new BackgroundSize(img.getWidth(), img.getHeight(), true, true, true, true));
			super.setBackground(new Background(bi));
			for (Actor a : super.getObjects(Actor.class)) {
				w.remove(a);
			}
			n = new Ninja();
			w.add(n);
			Image b1 = new Image("Back.PNG", Screen.getPrimary().getBounds().getMaxX() / 1366 * 127/3,
					Screen.getPrimary().getBounds().getMaxY() / 768 * 41/3, false, false);
			Image b2 = new Image("Back-2.PNG", Screen.getPrimary().getBounds().getMaxX() / 1366 * 127/3,
					Screen.getPrimary().getBounds().getMaxY() / 768 * 41/3, false, false);
			ImageView back = new ImageView(b1);
			back.setOnMouseEntered(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent event) {
					back.setImage(b2);
				}
			});
			back.setOnMouseExited(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent event) {
					back.setImage(b1);
				}
			});
			back.setOnMouseClicked(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent event) {
					setLevel(0);
					for (Actor a : w.getObjects(Actor.class)) {
						w.remove(a);
					}
					health.setImage(null);
					getChildren().remove(back);
					Level L1 = new Level(1);
					L1.setMaxLevel(maxLevelCompleted);
					Level L2 = new Level(2);
					L2.setMaxLevel(maxLevelCompleted);
					Level L3 = new Level(3);
					L3.setMaxLevel(maxLevelCompleted);
					Level L4 = new Level(4);
					L4.setMaxLevel(maxLevelCompleted);
					w.add(L1);
					w.add(L2);
					w.add(L3);
					w.add(L4);
					mp.stop();
					mp = new MediaPlayer(titleTheme);
					mp.play();
				}
			});
			back.setX(Screen.getPrimary().getBounds().getMaxX()-back.getImage().getWidth());
			w.getChildren().add(back);
			health = new ImageView();
			setHealthBar(3);
			w.getChildren().add(health);
			this.setOnKeyPressed(new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent event) {
					for (Actor a : w.getObjects(Actor.class)) {
						if (a.getOnKeyPressed() != null) {
							a.getOnKeyPressed().handle(event);
						}
					}
				}
			});
			this.setOnKeyReleased(new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent event) {
					for (Actor a : w.getObjects(Actor.class)) {
						if (a.getOnKeyReleased() != null) {
							a.getOnKeyReleased().handle(event);
						}
					}
				}
			});

			this.setOnMousePressed(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					shr = new Shuriken(n.getX(), n.getY(), e);
					w.getChildren().add(shr);
					r = new Rope(n,shr);
					w.getChildren().add(r);
				}
			});
			this.setOnMouseReleased(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent event) {
					if(getChildren().contains(r)){
						getChildren().remove(r.l);
						getChildren().remove(r);
					}
					n.swinging = false;
				}
			});

			switch(level) {
			case 1:
				bubbleCounter = 0;
				break;
			case 2:
				bubbleCounter = 4;
				break;
			case 3:
				bubbleCounter = 5;
			case 4:
				bubbleCounter = 7;
			}

			Scanner in = null;
			try {
				in = new Scanner(new File("Level" + level + ".txt"));
			} catch (FileNotFoundException e) {
			}
			int lineCounter = 0;
			while (in.hasNextLine()) {
				String s = in.nextLine();
				lineCounter++;
				for (int i = 0; i < s.length(); i++) {
					if (s.charAt(i) == '1' || s.charAt(i) == '2' || s.charAt(i) == '3') {
						Block b = new Block(Integer.parseInt("" + s.charAt(i)));
						b.setX(i * scalar);
						b.setY(lineCounter * scalar);
						add(b);
					} else if (s.charAt(i) == 'N') {
						n.setX(i * scalar);
						n.setY(lineCounter * scalar);
					} else if (s.charAt(i) == 'B') {
						BlueMine b = new BlueMine(i * scalar + 2, lineCounter * scalar + 2);
						add(b);
					} else if (s.charAt(i) == 'C') {
						CyberGuard c = new CyberGuard(i * scalar + 2, lineCounter * scalar + 2);
						add(c);
					} else if (s.charAt(i)== 'S'){
						Sniper sn = new Sniper(i * scalar + 2, lineCounter * scalar + 2);
						add(sn);
						sn.setNinja(n);
					} else if (s.charAt(i) == 'T') {
						Tablet t = new Tablet(i * scalar + 15, lineCounter * scalar + 15);
						add(t);
					} else if (s.charAt(i) == 'Q'){

						Bubble b = new Bubble(i * scalar + 2, lineCounter * scalar + 2);
						add(b);
						b.setText(bubbleCounter);
						bubbleCounter++;
					} else if (s.charAt(i) == 'F') {
						Food f = new Food(i * scalar + 2, lineCounter * scalar + 2);
						add(f);
					}else if(s.charAt(i) == 'M'){
						Money m = new Money(i * scalar + 2, lineCounter * scalar + 2);
						add(m);
					}
				}
			}
		}
	}

	void setHealthBar(int numLives) {
		if (numLives >= 0) {
			health.setImage(new Image("HealthBar" + numLives + ".png"));
		}
	}

	public int getCurrentLevel() {
		return currentLevel;
	}

	@Override
	public void act(long now) {
		for (Actor a : super.getObjects(Actor.class)) {
			a.act(now);
		}
	}

	public int getMaxCompletedLevel(){
		return maxLevelCompleted;
	}
}
