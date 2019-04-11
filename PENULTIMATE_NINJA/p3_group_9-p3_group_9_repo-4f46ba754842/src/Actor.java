import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.ImageView;

public abstract class Actor extends ImageView{
	
	public Actor(){
		
	}
	
	public abstract void act(long now);
	
	public double getHeight(){
		return super.getFitHeight();
	}
	
	public <A extends Actor> java.util.List<A> getIntersectingObjects(java.lang.Class<A> cls){
		List<A> list = new ArrayList<A>();
		for(A a:this.getWorld().getObjects(cls)){
			if(a!=null && a!=this && this.intersects(a.getBoundsInParent())){
				list.add(a);
			}
		}
		return list;
	}
	
	public <A extends Actor> A getOneIntersectingObject(java.lang.Class<A> cls){
		if(this.getIntersectingObjects(cls).size()==0){
			return null;
		}
		return this.getIntersectingObjects(cls).get(0);
	}
	
	public double getWidth(){
		return super.getFitWidth();
	}
	
	public World getWorld(){
		if((World) this.getParent()==null){
			return null;
		}
		return (World) this.getParent();
	}
	
	public void move(double dx, double dy){
		this.setX(this.getX()+dx);
		this.setY(this.getY()+dy);
	}
	
}
