import javafx.scene.shape.Line;

public class Rope extends Actor{
	private Ninja n;
	private Shuriken s;
	public Line l;
	public Rope(Ninja n, Shuriken s){
		this.n = n;
		this.s = s;
	}
	
	@Override
	public void act(long now) {
		if(getWorld().getChildren().contains(l)){
			getWorld().getChildren().remove(l);
		}
		if(s != null){
			l = new Line(n.getX()+n.getImage().getWidth()/2,n.getY()+n.getImage().getHeight()/2,s.getX()+s.getImage().getWidth()/4,s.getY()+s.getImage().getHeight()/4);
			l.setStrokeWidth(2);
			getWorld().getChildren().add(l);
			s.setLine(true);
		}
		if(!getWorld().getChildren().contains(s)){
			getWorld().getChildren().remove(this);
			s.setLine(false);
		}
	}

}
