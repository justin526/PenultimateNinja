import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Screen;

public class Bubble extends Actor {

	Label l = new Label();
	boolean first = true;
	String[] s = {"BACK HERE AFTER 10 YEARS. I MUST TEST THE SUIT\n"
					+ "BEFORE I UNLEASH MY NINJA SKILLS. THE BASICS\n" 
					+ "ARE LEFT AND RIGHT TO RUN. UP TO JUMP AND DOWN\n" + "TO STEALTH.",
				"LETS TRY FIRING THE ROPE AT THAT YELLOW\n"
					+ "SIGN UP TO THE RIGHT. ONCE IM SWINGING I CAN\n"
					+ "MOVE UP AND DOWN THE ROPE AND SWING LEFT AND\n"
					+ "RIGHT. THE NINJA IS MASTER OF THE ROPE. I MOVE\n"
					+ "THROUGH THE AIR LIKE A SPARROW OF DEATH.",
				"TIME FOR SOME TRAGET PRACTICE USING MY FRIEND\n"
					+ "THE NINJA STAR. ILL START BY TAKING OUT THE\n"
					+ "BLUE MINES. I NEED TO AVOID THEM AS THEY\n"
					+ "EXPLODE ON CONTACT. MANY OF THESE WILL DIE\n" + "BEFORE THE NIGHT IS OUT.",
				"THE BLACK KEY CARDS GET ME INTO THE NEXT AREA\n"
					+ "AND EVEN CLOSER TO MY EX BOSS. BY THE END OF\n"
					+ "THE DAY YOU WILL WISH YOU LEFT ME ALONE AKUMA. I\n"
					+ "GAVE YOU A CHANCE BUT NOW YOU WILL HAVE TO DIE.\n"
					+ "THIS IS THE NINJA WAY OF SOLVING A PROBLEM.",
				"AT LAST I GET TO TEST MY REAL NINJA SKILLS. I\n"
					+ "MUST KEEP AN EYE OUT FOR THE SUDDEN DROPS.\n"
					+ "IF I FALL HERE ITS INSTANT NINJA PANCAKE\n" + "AND NINJA PANCAKE TASTES BAD.",
				"THE BASIC CYBER GUARD. THESE GUYS ARENT TOO\n"
					+ "TOUGH. I CAN STEALTH PAST THEM IF I USE THE\n"
					+ "STEALTH SUIT WHEN THEY ARENT LOOKING IN MY\n"
					+ "DIRECTION. THEN I CAN HONOUR THEM WITH A SILENT\n" + "DEATH. NINJA STYLE.",
				"NICE OF A WORKMAN TO LEAVE SOME SUSHI. A VERY\n"
					+ "HEALTHY MEAL, AND MY FAVOURITE!"};

	public Bubble(double d, double e) {
		l.setFont(Font.font("Verdana", FontPosture.ITALIC, 20));
		l.setTextFill(Color.YELLOW);
		super.setX(d);
		super.setY(e);
		super.setImage(new Image("ThoughtBubble.png", Screen.getPrimary().getBounds().getMaxX() / 1366 * 40,
				Screen.getPrimary().getBounds().getMaxY() / 768 * 40, false, false));
	}

	@Override
	public void act(long now) {
		if (this.getOneIntersectingObject(Ninja.class) != null || first) {
			if (!getWorld().getChildren().contains(l)){
				getWorld().getChildren().add(l);
				l.setLayoutX(super.getWorld().getWidth() / 2 - l.getWidth() / 2);
				l.setLayoutY(super.getWorld().getHeight() - l.getHeight() - 5);
				first = false;
			}
		} else if (getWorld().getChildren().contains(l))
			getWorld().getChildren().remove(l);
	}

	public void setText(int num) {
		l.setText(s[num]);
	}

}
