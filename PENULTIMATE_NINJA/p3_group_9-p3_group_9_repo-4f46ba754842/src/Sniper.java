import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;

public class Sniper extends Enemy{

	final Image SNIPER = new Image("Sniper.PNG", Screen.getPrimary().getBounds().getMaxX() / 1366 * 40,
			Screen.getPrimary().getBounds().getMaxY() / 768 * 40, false, false);
	final Image TARGET = new Image("Target.PNG", Screen.getPrimary().getBounds().getMaxX() / 1366 * 40,
			Screen.getPrimary().getBounds().getMaxY() / 768 * 40, false, false);
	ImageView t;
	Ninja n;
	int pause = 0;
	
	public Sniper(double x, double y){
		setX(x);
		setY(y);
		super.setImage(SNIPER);
		super.setNumLives(1);
	}
	
	@Override
	void onContact() {
	}

	@Override
	void onSeen() {
	}

	@Override
	public void act(long now) {
		if(this.getNumLives()<=0){
			this.getWorld().getChildren().remove(t);
		}
		if(Math.sqrt(Math.pow(getX()-n.getX(), 2) + Math.pow(getY()-n.getY(), 2))<Screen.getPrimary().getBounds().getMaxX() / 1366 * 500){
			if(!this.getWorld().getChildren().contains(t)){
				this.getWorld().getChildren().add(t);
				t.setX(getX());
				t.setY(getY());
			}else{
				target();
			}
		}else if (this.getWorld().getChildren().contains(t)){
			target();
		}
	}
	
	private void target() {
		if(!n.getStealth()){
			double xDistance = n.getX()-t.getX();
			double yDistance = n.getY()-t.getY();
			double hypotenuse = Math.sqrt(Math.pow(xDistance, 2)+Math.pow(yDistance, 2));
			t.setX(t.getX()+Screen.getPrimary().getBounds().getMaxX() / 1366 * 2*xDistance/hypotenuse);
			t.setY(t.getY()+Screen.getPrimary().getBounds().getMaxX() / 1366 * 2*yDistance/hypotenuse);
		}
		if(Math.abs(n.getX()-t.getX())<3&&Math.abs(n.getY()-t.getY())<3){
			if(pause>=100){
				try{
					n.reduceLives();
				}catch(NullPointerException e){
				}
				pause = 0;
			}else{
				if(0<pause%10&& pause%10<5){
					t.setImage(null);
				}else{
					t.setImage(TARGET);
				}
				pause++;
			}
		}else{
			if(t.getImage()==null){
				t.setImage(TARGET);
			}
		}

	}

	public void setNinja(Ninja nin){
		n = nin;
		t = new ImageView(TARGET);
		
	}

	@Override
	void onDeath() {
		this.getWorld().getChildren().remove(t);
		this.getWorld().remove(this);
	}

}
