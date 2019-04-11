import javafx.scene.image.Image;
import javafx.stage.Screen;

public class Block extends Actor{
	final Image SQUARE = new Image("Block.png",Screen.getPrimary().getBounds().getMaxX()/1366*40,Screen.getPrimary().getBounds().getMaxY()/768*40, false, false);
	final Image FRAME = new Image("Frame.png",Screen.getPrimary().getBounds().getMaxX()/1366*60,Screen.getPrimary().getBounds().getMaxY()/768*100, false, false);
	final Image PLATFORM = new Image("Block3.png",Screen.getPrimary().getBounds().getMaxX()/1366*156,Screen.getPrimary().getBounds().getMaxY()/768*40.8, false, false);
	
	public Block(int code){
		if(code==1){
			setImage(SQUARE);
		}else if(code==2){
			setImage(FRAME);
		}else if (code==3){
			setImage(PLATFORM);
		}
	}
	
	@Override
	public void act(long now) {
	}

}
