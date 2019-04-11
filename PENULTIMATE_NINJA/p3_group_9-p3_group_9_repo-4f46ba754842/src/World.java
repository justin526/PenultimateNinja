import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public abstract class World extends Pane{
	World w;
	private AnimationTimer timer;
	
	public World(){
		super();
		w = this;
		timer = new AnimationTimer(){
			@Override
			public void handle(long now) {
				act(now);
				for(int i = 0;i<w.getChildren().size(); i++){
					if(w.getChildren().get(i).getClass() == Actor.class)
						((Actor) w.getChildren().get(i)).act(now);
				}
			}
		};
		timer.start();
	}
	
	public abstract void act(long now);
	
	public void add(Actor actor){
		this.getChildren().add(actor);
	}
	
	public <A extends Actor> java.util.List<A> getObjects(java.lang.Class<A> cls){
		ArrayList<A> returnList = new ArrayList<>();
		List<Node> l= this.getChildren();
		for(int i = 0; i<l.size(); i++){
			if(cls.isInstance(l.get(i))){
				returnList.add(cls.cast(l.get(i)));
			}
		}
		return returnList;
	}
	
	public void remove(Actor actor){
		this.getChildren().remove(actor);
	}
	
	public void start(){
		timer.start();
	}
	
	public void stop(){
		timer.stop();
	}
}
