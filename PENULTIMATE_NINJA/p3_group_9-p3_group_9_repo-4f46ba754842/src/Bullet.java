import javafx.scene.image.Image;

public class Bullet extends Actor{

	final Image BULLET = new Image("Bullet.PNG");
	double dx = 0;
	int dy = 0;
	public Bullet(double x, double y){
		setImage(BULLET);
		setX(x);
		setY(y);
	}
	
	public void setDX(double d){
		dx = d;
	}
	
	@Override
	public void act(long now) {
		super.move(dx, dy);
		if(super.getOneIntersectingObject(Block.class)!=null || getX()<0 || getX()+getImage().getWidth()>getWorld().getWidth()){
			getWorld().remove(this);
		}
		try{
			if(super.getOneIntersectingObject(Ninja.class)!=null){
				super.getOneIntersectingObject(Ninja.class).reduceLives();
				getWorld().remove(this);
			}
		}catch(NullPointerException e){
		}

	}

}
