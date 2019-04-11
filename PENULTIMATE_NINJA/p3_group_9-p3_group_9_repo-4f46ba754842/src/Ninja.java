import java.io.File;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.event.EventHandler;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Screen;
import javafx.util.Duration;

public class Ninja extends Actor {
	private Ninja n;
	private double dx;
	private double dy;
	boolean onEdge = false;
	boolean shortJump = false;
	final double GRAVITY = Screen.getPrimary().getBounds().getMaxY() / 768 * 3;
	final Image RIGHTLEAN = new Image("Ninja_RIGHTLEAN.PNG", Screen.getPrimary().getBounds().getMaxX() / 1366 * 40,
			Screen.getPrimary().getBounds().getMaxY() / 768 * 40, false, false);
	final Image LEFTLEAN = new Image("Ninja_LEFTLEAN.PNG", Screen.getPrimary().getBounds().getMaxX() / 1366 * 40,
			Screen.getPrimary().getBounds().getMaxY() / 768 * 40, false, false);
	final Image FALLLEFT = new Image("Ninja_FALLLEFT.png", Screen.getPrimary().getBounds().getMaxX() / 1366 * 45,
			Screen.getPrimary().getBounds().getMaxY() / 768 * 45, false, false);
	final Image FALLRIGHT = new Image("Ninja_FALLRIGHT.png", Screen.getPrimary().getBounds().getMaxX() / 1366 * 45,
			Screen.getPrimary().getBounds().getMaxY() / 768 * 45, false, false);
	final Image RUN1 = new Image("Ninja_RUN1.png", Screen.getPrimary().getBounds().getMaxX() / 1366 * 40,
			Screen.getPrimary().getBounds().getMaxY() / 768 * 40, false, false);
	final Image RUN2 = new Image("Ninja_RUN2.png", Screen.getPrimary().getBounds().getMaxX() / 1366 * 40,
			Screen.getPrimary().getBounds().getMaxY() / 768 * 40, false, false);
	final Image RUN3 = new Image("Ninja_RUN3.png", Screen.getPrimary().getBounds().getMaxX() / 1366 * 40,
			Screen.getPrimary().getBounds().getMaxY() / 768 * 40, false, false);
	final Image RUN4 = new Image("Ninja_RUN4.png", Screen.getPrimary().getBounds().getMaxX() / 1366 * 40,
			Screen.getPrimary().getBounds().getMaxY() / 768 * 40, false, false);
	final Image JUMPRIGHT = new Image("Ninja_JUMPRIGHT.png", Screen.getPrimary().getBounds().getMaxX() / 1366 * 50,
			Screen.getPrimary().getBounds().getMaxY() / 768 * 50, false, false);
	final Image JUMPLEFT = new Image("Ninja_JUMPLEFT.png", Screen.getPrimary().getBounds().getMaxX() / 1366 * 50,
			Screen.getPrimary().getBounds().getMaxY() / 768 * 50, false, false);
	final Image STEALTH = new Image("Ninja_Stealth.PNG", Screen.getPrimary().getBounds().getMaxX() / 1366 * 45,
			Screen.getPrimary().getBounds().getMaxY() / 768 * 45, false, false);
	final Image DEATH = new Image("Ninja_DEATH.PNG", Screen.getPrimary().getBounds().getMaxX() / 1366 * 40,
			Screen.getPrimary().getBounds().getMaxY() / 768 * 40, false, false);
	boolean wasMovingRight = true;
	boolean running = false;
	boolean stealthOn = false;
	private int runCounter = 0;
	private int numLives;
	public boolean swinging = false;
	private boolean levelCompleted = false;
	Media zap = new Media(new File("Zap.mp3").toURI().toString());
	AnimationTimer run = new AnimationTimer() {
		long oldTime;
		@Override
		public void handle(long time) {
			if (time - oldTime > 100000000) {
				if (runCounter % 2 == 0) {
					if (wasMovingRight) {
						n.setImage(RUN1);
					} else {
						n.setImage(RUN3);
					}
				} else {
					if (wasMovingRight) {
						n.setImage(RUN2);
					} else {
						n.setImage(RUN4);
					}
					runCounter--;
				}
				oldTime = time;
				runCounter++;
			}
		}
	};

	public Ninja() {
		n = this;
		numLives = 3;
		setImage(RIGHTLEAN);
		this.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (!stealthOn) {
					if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A) {
						dx = -getWorld().getWidth() / 600;
						wasMovingRight = false;
						if (!running && onEdge) {
							running = true;
							run.start();
						}
					} else if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D) {
						dx = getWorld().getWidth() / 600;
						wasMovingRight = true;
						if (!running && onEdge) {
							running = true;
							run.start();
						}
					} else if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.W) {
						if (!shortJump && onEdge) {
							shortJump = true;
							onEdge = false;
							shortJumpAnimation();
						}else{
							if(getWorld().getObjects(Shuriken.class).size()>0){
								Shuriken s = getWorld().getObjects(Shuriken.class).get(getWorld().getObjects(Shuriken.class).size()-1);
								if(!s.isMoving()&&s.hasLine()) {
									double xDistance = s.getX()-n.getX();
									double yDistance = s.getY()-n.getY();
									double hypotenuse = Math.sqrt(Math.pow(xDistance, 2)+Math.pow(yDistance, 2));
									n.setX(n.getX()+Screen.getPrimary().getBounds().getMaxX() / 1366 *5*xDistance/hypotenuse);
									n.setY(n.getY()+Screen.getPrimary().getBounds().getMaxX() / 1366 *5*yDistance/hypotenuse);
								}
							}

						}
							swinging = getWorld().getObjects(Rope.class)!=null;
						
					} else if (event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.S) {
						if (onEdge) {
							running = false;
							dx = 0;
							n.setImage(STEALTH);
							n.setEffect(new GaussianBlur(6));
							stealthOn = true;
						}
					}
				}

			}
		});
		this.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A || event.getCode() == KeyCode.D) {
					dx = 0;
					running = false;
					run.stop();
				}
				if (event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.S) {
					n.setEffect(null);
					stealthOn = false;
					n.setImage(RIGHTLEAN);
				}
				dy = 0;
			}
		});

	}


	public void shortJumpAnimation() {
		dy = GRAVITY * -3;
		double dyOriginal = dy;
		AnimationTimer jump = new AnimationTimer() {
			long oldTime;

			@Override
			public void handle(long time) {
				if (dy < 0 && wasMovingRight) {
					n.setImage(JUMPRIGHT);
				} else if (dy < 0 && !wasMovingRight) {
					n.setImage(JUMPLEFT);
				}
				if (dy != dyOriginal && n.getOneIntersectingObject(Block.class) != null) {
					dy = 0;
					n.setX(n.getX()-dx);
				}
				if (time - oldTime > 100000000) {
					dy += GRAVITY/2.0;
					oldTime = time;
				}
				if (dy >= 0) {
					this.stop();
					shortJump = false;
				}
			}
		};
		jump.start();
	}

	@Override
	public void act(long now) {
		if (super.getOneIntersectingObject(Tablet.class) != null) {
			if (!levelCompleted) {
				levelCompleted = true;
				Media levelComplete = new Media(new File("LevelComplete.mp3").toURI().toString());
				MediaPlayer mp = new MediaPlayer(levelComplete);
				mp.play();

			}
			FadeTransition ft = new FadeTransition(Duration.millis(2020), n);
			ft.play();
			AnimationTimer t = new AnimationTimer() {
				long oldTime;
				int counter;

				@Override
				public void handle(long time) {
					if (time - oldTime > 10000) {
						n.move(0, -0.2);
						counter++;
						if (counter == 25) {
							try {
								if(((NinjaWorld)(n.getWorld())).getCurrentLevel()==4){
								}else{


									((NinjaWorld) (n.getWorld()))
									.setLevel(((NinjaWorld) (n.getWorld())).getCurrentLevel() + 1);
								}
							} catch (NullPointerException e) {
							}
						}
					}
				}
			};
			t.start();
		}
		if (super.getOneIntersectingObject(Block.class) != null) {
			Block b = super.getOneIntersectingObject(Block.class);
			if (b.getY() >= n.getY()) {
				onEdge = true;
				if(b.getY()-(n.getY()-n.getImage().getHeight())<78){
					setY(b.getY()-42);
				}
			}
			int xPos = ((int) (getX()) / 40) * 40;
			int yPos = ((int) (getY()) / 40) * 40;
			List<Block> list = super.getIntersectingObjects(Block.class);
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getX() == xPos && list.get(i).getY() == yPos) {
					if (dx < 0) {
						n.setX(n.getX() + 2);
					} else {
						n.setX(n.getX() - 2);
					}
					dx = 0;
				}
			}
		} else {
			onEdge = false;
		}
		if (getX() + dx + getImage().getWidth() < getWorld().getWidth() && getX() + dx > 0) {
			setX(getX() + dx);
		}
		if (getY() + dy + getImage().getHeight() < getWorld().getHeight() && getY() + dy > 0) {
			setY(getY() + dy);
		}
		if (getY() < getWorld().getHeight() - super.getImage().getHeight() - 1 && !onEdge) {
			setY(getY() + GRAVITY);
		} else if (getY() >= getWorld().getHeight() - super.getImage().getHeight() - 1) {
			numLives = 0;
			((NinjaWorld) this.getParent()).setHealthBar(numLives);
			onLifeLost();
		}
		if (!stealthOn) {
			if (!running) {
				if (dy == 0 && !onEdge && wasMovingRight) {
					setImage(FALLRIGHT);
				} else if (dy == 0 && !onEdge && !wasMovingRight) {
					setImage(FALLLEFT);
				} else if (dy == 0 && onEdge && wasMovingRight) {
					setImage(RIGHTLEAN);
				} else if (dy == 0 && onEdge && !wasMovingRight) {
					setImage(LEFTLEAN);
				}
			}
		}
	}

	public void reduceLives() {
		((NinjaWorld) this.getParent()).setHealthBar(--numLives);
		onLifeLost();
	}

	public void onLifeLost() {
		if (numLives <= 0) {
			dx = 0;
			n.setOnKeyReleased(null);
			n.setOnKeyPressed(null);
			Media death = new Media(new File("Death.mp3").toURI().toString());
			MediaPlayer player = new MediaPlayer(death);
			player.play();
			try {
				n.getWorld().remove(n);
			} catch (NullPointerException e) {
			}
		}else{

			MediaPlayer mp = new MediaPlayer(zap);
			mp.setStartTime(new Duration(400));
			mp.setVolume(5.0);
			mp.play();
			AnimationTimer t = new AnimationTimer() {
				long oldTime;
				int counter = 0;
				@Override
				public void handle(long time) {
					if (time - oldTime > 80000000) {
						oldTime = time;
						if (counter % 2 == 0) {
							setImage(DEATH);
						} else {
							if (!running) {
								if (dy == 0 && !onEdge && wasMovingRight) {
									setImage(FALLRIGHT);
								} else if (dy == 0 && !onEdge && !wasMovingRight) {
									setImage(FALLLEFT);
								} else if (dy == 0 && onEdge && wasMovingRight) {
									setImage(RIGHTLEAN);
								} else if (dy == 0 && onEdge && !wasMovingRight) {
									setImage(LEFTLEAN);
								}
							}
						}

						counter++;
					}
					if (counter == 15) {
						this.stop();

					}
				}
			};
			t.start();
		}
	}

	public boolean getStealth(){
		return stealthOn;
	}

	public void gainLife() {
		((NinjaWorld) this.getParent()).setHealthBar((numLives == 3) ? 3 : ++numLives);
	}
}
