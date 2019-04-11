import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;

public class Level extends Actor{
	Image mouseHovered;
	Image mouseNotHovered;
	Image mystery = new Image("Mystery.PNG", Screen.getPrimary().getBounds().getMaxX() / 1366 * 47,
			Screen.getPrimary().getBounds().getMaxY() / 768 * 35, false, false);
	int level;
	Level l;
	
	public Level(int code){
		l = this;
		level = code;
		mouseNotHovered = new Image("L" + code + ".PNG", Screen.getPrimary().getBounds().getMaxX() / 1366 * 47,
				Screen.getPrimary().getBounds().getMaxY() / 768 * 35, false, false);
		mouseHovered = new Image("L" + code + "-2.PNG", Screen.getPrimary().getBounds().getMaxX() / 1366 * 47,
				Screen.getPrimary().getBounds().getMaxY() / 768 * 35, false, false);
			this.setOnMouseEntered(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent event) {
					l.setImage(mouseHovered);
				}
			});
			this.setOnMouseExited(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent event) {
					l.setImage(mouseNotHovered);
				}
			});
			this.setOnMouseClicked(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent arg0) {
					NinjaWorld x = (NinjaWorld)(l.getWorld());
					x.setLevel(level);
					x.remove(l);
				}
			});
			this.setOnKeyPressed(new EventHandler<KeyEvent>(){
				@Override
				public void handle(KeyEvent event) {
					NinjaWorld x = (NinjaWorld)(l.getWorld());
					x.setLevel(level);
					x.remove(l);
				}
			});
		super.setImage(mouseNotHovered);
		this.setX(Screen.getPrimary().getBounds().getMaxX()/3+((level+1)/2)*getImage().getWidth());
		this.setY(Screen.getPrimary().getBounds().getMaxY()/4+(level/2)*getImage().getHeight());
		
	}
	
	@Override
	public void act(long now) {
	}
	
	public void setMaxLevel(int lev){
		if(level>lev){
			setImage(mystery);
			this.setOnMouseEntered(null);
			this.setOnMouseClicked(null);
			this.setOnKeyPressed(null);
			this.setOnMouseExited(null);
		}
	}
}
