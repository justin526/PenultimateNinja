import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.stage.Screen;

public class Food extends Actor {
	int lives = 1;
	ArrayList<String> contact = new ArrayList<>();

	public Food(double x, double y) {
		this.setX(x);
		this.setY(y);
		double min = (Screen.getPrimary().getBounds().getMaxX() / 1366 * 40 > Screen.getPrimary().getBounds().getMaxY()
				/ 768 * 40) ? Screen.getPrimary().getBounds().getMaxY() / 768 * 40
						: Screen.getPrimary().getBounds().getMaxX() / 1366 * 40;
		setImage(new Image("Food.png", min, min, false, false));
		addContacts();
	}

	@Override
	public void act(long now) {
		if (onContact()) {
			this.setOpacity((this.getOpacity() - .05 <= 0) ? 0 : this.getOpacity() - .05);
		}
		if (this.getOpacity() == 0) {
			getWorld().remove(this);
		}
	}

	boolean onContact() {
		Ninja n = this.getOneIntersectingObject(Ninja.class);
		if (n != null) {
			n.gainLife();
			return true;
		}
		return false;
	}

	void addContacts() {
		contact.add("Ninja");
//		contact.add("Shuriken");
	}
}
