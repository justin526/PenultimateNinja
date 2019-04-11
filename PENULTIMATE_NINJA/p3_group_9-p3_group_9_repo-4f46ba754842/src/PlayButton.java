import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

public class PlayButton extends Actor{

	PlayButton p;
	public PlayButton(){
		p = this;
		super.setImage(new Image("PlayButton.PNG"));
		this.setOnMouseEntered(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				p.setImage(new Image("PlayButtonHover.PNG"));
			}
		});
		this.setOnMouseExited(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				p.setImage(new Image("PlayButton.PNG"));
			}
		});
	}
	
	@Override
	public void act(long now) {
		
	}

}
