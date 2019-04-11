import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Driver extends Application{
	public static void main(String[] args){
		launch();
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Penultimate Ninja");
		stage.setMaximized(true);
        NinjaWorld w = new NinjaWorld();
		Scene scene = new Scene(w);
		scene.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				if(w.getOnMouseClicked() != null){
					w.getOnMouseClicked().handle(event);
				}
				w.requestFocus();
			}
			
		});
		scene.setOnMouseEntered(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				if(w.getOnMouseMoved()!=null){
					w.getOnMouseMoved().handle(event);
				}
				w.requestFocus();
			}
		});
		scene.setOnMouseExited(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				if(w.getOnMouseExited()!=null){
					w.getOnMouseExited().handle(event);
				}
				w.requestFocus();
			}
		});
		scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent event) {
				if(w.getOnKeyPressed()!= null){
					w.getOnKeyPressed().handle(event);
					w.requestFocus();
				}
			}
		});
		scene.setOnKeyReleased(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent event) {
				if(w.getOnKeyReleased()!=null){
					w.getOnKeyReleased().handle(event);
					w.requestFocus();
				}
			}
		});
		stage.setScene(scene);
//		stage.setResizable(false);
		w.setLevel(-1);
		stage.show();
	}
	
}
