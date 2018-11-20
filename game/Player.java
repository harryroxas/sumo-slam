// Note: In progress

public class Player extends Sprite {
	public static final int normalSpeed = 10;
	public int playerNo;
	public int speed;
	public int noOfWins;
	public boolean isDead;
	public String imgPath;

	public Player(int playerNo, int x, int y) {
		super(x, y);
		this.playerNo = playerNo;
		this.speed = this.normalSpeed;
		this.noOfWins = 0;
		this.isDead = false;
		this.imgPath = "Images/" + this.playerNo + ".png";
		setImage(imgPath);
	}

	// Setters
	public void move(int x, int y) {
		int xPosition = getX() + x;
		int yPosition = getY() + y;

		setX(xPosition);
		setY(yPosition);
	}

	public void speedUp() {
		this.speed = this.speed + 1;
	}

	public void slowDown() {
		if(this.speed > 1) {
			this.speed = this.speed - 1;
		}
	}

	public void resetSpeed() {
		this.speed = this.normalSpeed;
	}

	public void resetPlayer() {
		this.resetSpeed();
		this.isDead = false;
	}

	public void playerWins() {
		this.noOfWins = this.noOfWins + 1;
	}

	public void playerDies() {
		this.isDead = true;
	}

	// Getters
	public boolean getPlayerStatus() {
		return this.isDead;
	}
}

// References: zetcode.com/tutorials/javagamestutorial
