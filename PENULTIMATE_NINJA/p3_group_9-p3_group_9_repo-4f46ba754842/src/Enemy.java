
public abstract class Enemy extends Actor{
	private int lives;
	public Enemy(){
	}
	abstract void onContact();
	abstract void onSeen();
	abstract void onDeath();
	public void loseLife(){
		lives--;
		if(lives<=0){
			onDeath();
		}
	}
	public void setNumLives(int num){
		lives = num;
	}
	public int getNumLives(){
		return lives;
	}
}
