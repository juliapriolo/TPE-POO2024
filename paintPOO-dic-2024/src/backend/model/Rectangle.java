package backend.model;

import backend.interfaces.Figure;

public class Rectangle implements Figure {

    private Point topLeft, bottomRight;

    public Rectangle(Point topLeft, Point bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    @Override
    public double getWidth() {
        return Math.abs(getTopLeft().getX() - getBottomRight().getX());
    }

    @Override
    public double getHeight() {
        return Math.abs(getTopLeft().getY() - getBottomRight().getY());
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public Point getBottomRight() {
        return bottomRight;
    }

    public Point getCenter(){
        return new Point((getTopLeft().getX() + getBottomRight().getX()) / 2, (getTopLeft().getY() + getBottomRight().getY()) / 2);
    }

    public void setTopLeft(Point topLeft){
        this.topLeft = topLeft;
    }

    public void setBottomRight(Point bottomRight){
        this.bottomRight = bottomRight;
    }

    @Override
    public String toString() {
        return String.format("Rectángulo [ %s , %s ]", topLeft, bottomRight);
    }

    @Override
    public void move(double deltaX, double deltaY){
        topLeft.move(deltaX, deltaY);
        bottomRight.move(deltaX, deltaY);

    }

    @Override
    public boolean contains(Point eventPoint) {
        return eventPoint.getX() > topLeft.getX() && eventPoint.getX() < bottomRight.getX() &&
                eventPoint.getY() > topLeft.getY() && eventPoint.getY() < bottomRight.getY();
    }

}
