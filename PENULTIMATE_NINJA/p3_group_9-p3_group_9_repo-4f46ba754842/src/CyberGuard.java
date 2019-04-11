import java.io.File;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Screen;
import javafx.util.Duration;

public class CyberGuard extends Enemy {
	private CyberGuard c;
	boolean onEdge = false;
	final double GRAVITY = Screen.getPrimary().getBounds().getMaxY() / 768 * 3;
	private double scalar = Screen.getPrimary().getBounds().getMaxX() / 1366 * 40;
	final Image CYBER1 = new Image("CyberGuard1.PNG", Screen.getPrimary().getBounds().getMaxX() / 1366 * 40,
			Screen.getPrimary().getBounds().getMaxY() / 768 * 40, false, false);
	final Image CYBER2 = new Image("CyberGuard2.PNG", Screen.getPrimary().getBounds().getMaxX() / 1366 * 40,
			Screen.getPrimary().getBounds().getMaxY() / 768 * 40, false, false);
	final Image CYBER3 = new Image("CyberGuard3.png", Screen.getPrimary().getBounds().getMaxX() / 1366 * 40,
			Screen.getPrimary().getBounds().getMaxY() / 768 * 40, false, false);
	final Image CYBER4 = new Image("CyberGuard4.png", Screen.getPrimary().getBounds().getMaxX() / 1366 * 40,
			Screen.getPrimary().getBounds().getMaxY() / 768 * 40, false, false);
	int counter = 0;
	double dx = -Screen.getPrimary().getBounds().getMaxX() / 1366 * 1.5;
	double dy;
	long oldTime;
	Ninja n = null;
	double previousDX = -Screen.getPrimary().getBounds().getMaxX() / 1366 * 1.5;
	boolean spotted = false;
	Media shot = new Media(new File("Shot.mp3").toURI().toString());
	MediaPlayer mp;
	
	public CyberGuard(double d, double e) {
		c = this;
		setImage(CYBER1);
		setX(d);
		setY(e);
		super.setNumLives(2);
	}

	@Override
	void onContact() {

	}

	@Override
	void onSeen() {
		previousDX = dx;
		dx = 0;
	}

	@Override
	public void act(long now) {

		try {
			n = getWorld().getObjects(Ninja.class).get(0);
		} catch (NullPointerException e) {
		} catch (IndexOutOfBoundsException e1) {
		}
		if (super.getOneIntersectingObject(Block.class) != null) {
			onEdge = true;
			double xPos = ( (getX()) / scalar) * scalar;
			double yPos = ( (getY()) / scalar) * scalar;
			List<Block> list = super.getIntersectingObjects(Block.class);
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getX() == xPos + scalar && list.get(i).getY() == yPos) {
					if (dx < 0) {
						setX(getX() + 2);
					} else {
						setX(getX() - 2);
					}
					dx=-dx;
				}
			}
		} else {
			if(onEdge){
				dx = - dx;
			}else{
				onEdge = false;
			}
		}
		if (getX() + dx < 0 || getX() + getImage().getWidth() + dx > getWorld().getWidth()) {
			dx = -dx;
		}
		if (!onEdge) {
			super.move(0, GRAVITY);
		} else {
			super.move(dx, dy);
		}
		if (counter % 2 == 0) {
			if (dx < 0) {
				setImage(CYBER1);
			} else if (dx > 0) {
				setImage(CYBER3);
			}
		} else {
			if (dx < 0) {
				setImage(CYBER2);
			} else if (dx > 0) {
				setImage(CYBER4);
			}
		}
		if (now - oldTime > 100000000) {
			counter++;
			oldTime = now;
		}
		if (!spotted) {
			if (n != null && Math.abs(n.getY()- getY())<3 && ((n.getX() < getX() && dx < 0) || (n.getX() > getX() && dx > 0))
					&& !n.getStealth()) {
				spotted = true;
				previousDX = dx;
				dx = 0;
				AnimationTimer t = new AnimationTimer() {
					long oldTime;
					@Override
					public void handle(long time) {
						if (time - oldTime > 1000000000) {
							oldTime = time;
							Bullet b = new Bullet(getX() + dx * 15 * GRAVITY, getY() + 10);
							if (previousDX > 0) {
								b.setDX(2);
							} else {
								b.setDX(-2);
							}
							mp = new MediaPlayer(shot);
							mp.setStopTime(new Duration(100));
							mp.setVolume(10);
							mp.play();
							
//							mp.setOnStopped(new Runnable(){
//								@Override
//								public void run() {
//									mp.stop();
//								}
//							});
							try {
								c.getWorld().getChildren().add(b);
								if (Math.abs(n.getY()- getY())>=3) {
									dx = previousDX;
									this.stop();
									spotted = false;
								}
							} catch (NullPointerException e) {
							}
						}
					}
				};
				t.start();
				if(super.getNumLives()<0){
					mp = null;
					t.stop();
				}
			}
		}
	}

	@Override
	void onDeath() {
		mp = null;
		this.getWorld().remove(this);
	}
}