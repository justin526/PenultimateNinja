import java.io.File;

import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Screen;

public class BlueMine extends Enemy{
	Media close = new Media(new File("Close Boom.mp3").toURI().toString());
	Media far = new Media(new File("Far Boom.mp3").toURI().toString());
	double dx = Screen.getPrimary().getBounds().getMaxY() / 768 * 3;
	double dy = 0;
	boolean onPause = false;
	long oldTime;
	public BlueMine(double d, double e){
		this.setX(d);
		this.setY(e);
		if(Math.random()>0.5){
			dy = dx;
			dx = 0;
		}
		super.setImage(new Image("EnergyBall.png",Screen.getPrimary().getBounds().getMaxX()/1366*35,Screen.getPrimary().getBounds().getMaxY()/768*35, false, false));
	}
	@Override
	void onContact() {
		
	}

	@Override
	void onSeen() {
	}

	@Override
	public void act(long now) {
		if(super.getOneIntersectingObject(Block.class)!=null|| getX()<0 || getX()>Screen.getPrimary().getBounds().getMaxX()-getImage().getWidth() || getY()<0){
			move(-dx,-dy);
			dx = -dx;
			dy = -dy;
			super.setRotate(getRotate()+5);
			/*
			if(Math.random()>0.5){
				double temp = dy;
				dy = dx;
				dx = temp;
			}
			*/
		}
		if(super.getOneIntersectingObject(Ninja.class)!=null){
			getOneIntersectingObject(Ninja.class).reduceLives();
			getWorld().remove(this);
		}
		super.move(dx, dy);
		
	}
	@Override
	void onDeath() {
		Ninja n = this.getWorld().getObjects(Ninja.class).get(0);
		MediaPlayer mp = null;
		if(Math.sqrt(Math.pow(this.getX()-n.getX(), 2)+Math.pow(this.getY()-n.getY(), 2))>40){
			mp = new MediaPlayer(far);
		}else{
			mp = new MediaPlayer(close);
		}
		mp.play();
		this.getWorld().remove(this);
	}


}