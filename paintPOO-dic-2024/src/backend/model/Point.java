package backend.model;

import backend.interfaces.Movable;

public class Point implements Movable {

    private double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void move(double deltaX, double deltaY){
        this.x += deltaX;
        this.y += deltaY;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setPointX(double x) {
        this.x += x;
    }

    public void setPointY(double y) {
        this.y += y;
    }

    @Override
    public String toString() {
        return String.format("{%.2f , %.2f}", x, y);
    }

}
