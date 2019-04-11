import javafx.scene.image.Image;
import javafx.stage.Screen;

public class Tablet extends Actor{
	public Tablet(double d, double e){
		super.setImage(new Image("Tablet.png",Screen.getPrimary().getBounds().getMaxX()/1366*25,Screen.getPrimary().getBounds().getMaxY()/768*25, false, false));
		setX(d);
		setY(e);
	}

	@Override
	public void act(long now) {
	}
}
