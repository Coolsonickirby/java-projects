import javafx.scene.image.Image;

public class Sprite {
    private Image spritesheet;
    private double x;
    private double y;
    private double xRect;
    private double yRect;
    private double width;
    private double height;

    public Sprite(Image spritesheet, double x, double y, double xRect, double yRect, double width, double height) {
        this.spritesheet = spritesheet;
        this.x = x;
        this.y = y;
        this.xRect = xRect;
        this.yRect = yRect;
        this.width = width;
        this.height = height;
    }

    public Image getSpritesheet() {
        return spritesheet;
    }
    public void setSpritesheet(Image spritesheet) {
        this.spritesheet = spritesheet;
    }
    public double getX() {
        return x;
    }
    public void setX(double x) {
        this.x = x;
    }
    public double getY() {
        return y;
    }
    public void setY(double y) {
        this.y = y;
    }
    public double getxRect() {
        return xRect;
    }
    public void setxRect(double xRect) {
        this.xRect = xRect;
    }
    public double getyRect() {
        return yRect;
    }
    public void setyRect(double yRect) {
        this.yRect = yRect;
    }
    public double getWidth() {
        return width;
    }
    public void setWidth(double width) {
        this.width = width;
    }
    public double getHeight() {
        return height;
    }
    public void setHeight(double height) {
        this.height = height;
    }   
}
