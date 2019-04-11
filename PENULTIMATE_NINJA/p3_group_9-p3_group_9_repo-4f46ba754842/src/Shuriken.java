import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;

public class Shuriken extends Actor {
	private double dx;
	Shuriken sh;
	private double dy;
	final Image SHURIKEN = new Image("Shuriken.png", Screen.getPrimary().getBounds().getMaxX()/1366*40,Screen.getPrimary().getBounds().getMaxY()/768*40, false, false);
	MouseEvent e;
	long oldTime = 0;
	double scalar;
	private int rotate = 5;
	boolean hasLine = false;
	
	public Shuriken(double x, double y, MouseEvent ev){
		sh = this;
		e = ev;
		dy = e.getY() - y;
		dx = e.getX() - x;
		scalar =  6/(Math.sqrt(dx*dx + dy*dy));
		sh.setImage(SHURIKEN);
		sh.setFitHeight(20);
		sh.setFitWidth(20);
		sh.setX(x);
		sh.setY(y);
		sh.setVisible(true);
		AnimationTimer timer = new AnimationTimer(){
			@Override
			public void handle(long now) {
				if(now % 100 == 0){
					act(now);
				}	
			}
		};
		timer.start();
	}

	@SuppressWarnings("unlikely-arg-type")
	public void act(long now) {
		if(now - oldTime > 1000000){
			sh.move(dx * scalar, dy * scalar);
			sh.setRotate(sh.getRotate()+rotate);
				hasLine = this.getWorld().getChildren().contains(Rope.class);
			oldTime = now;
		}
		isTouching();
	}
	public void isTouching(){
		try{
			if(getOneIntersectingObject(Enemy.class) != null && isMoving()){
				getOneIntersectingObject(Enemy.class).loseLife();
				getWorld().getChildren().remove(this);
			}else if(getOneIntersectingObject(Block.class) != null){
				if(super.getWorld().getObjects(Rope.class)!=null){
					dx = 0;
					dy = 0;
					rotate = 0;
				}else{
					getWorld().getChildren().remove(this);
				}
				
			}
		}catch(NullPointerException e){
		}
	}
	public boolean isMoving(){
		if(dx == 0 && dy == 0){
			return false;
		}else{
			return true;
		}
	}
	public boolean hasLine() {
		return hasLine;
	}
	public void setLine(boolean t) {
		hasLine = t;
	}
}
