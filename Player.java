public class Player extends Sprite {
    public static final int normalSpeed = 10;
    public int xPosition;
    public int yPosition;
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
        this.imgPath = "sumoWrestler" + this.playerNo + ".png";
        this.setImg(this.imgPath);
        this.width = 60;
        this.height = 60;
    }

	public void resetPosition(){
		this.xPosition = 0;
		this.yPosition = 0;
	}
	
	public void moveX(int xPosition){
		this.xPosition = xPosition;
	}
	
	public void moveY(int yPosition){
		this.yPosition = yPosition;
	}
	
    public void move() {
        int newX = this.getX() + this.xPosition;
        int newY = this.getY() + this.yPosition;
        this.setX(newX);
        this.setY(newY);
        System.out.print(this.x);
        System.out.println(this.xPosition);
        System.out.print(this.y);
        System.out.println(this.yPosition);
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

    public void collidesWith(Player others) {
    	if(this.getMoveX() == 0) {
        	int minusY = others.getMoveY() + this.getMoveY();
            others.moveY(minusY);
    	}else {
        	int minusX = others.getMoveX() + this.getMoveX();
        	others.moveX(minusX);
    	}
        others.move();
        others.resetPosition();
    }
    
    public void playerWins() {
        this.noOfWins = this.noOfWins + 1;
    }

    public void playerDies() {
        this.isDead = true;
    }

    public boolean getPlayerStatus() {
        return this.isDead;
    }
    
	public int getMoveX(){
		return this.xPosition;
	}
	
	public int getMoveY(){
		return this.yPosition;
	}
}

// References: zetcode.com/tutorials/javagamestutorial
