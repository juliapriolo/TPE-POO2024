package backend.model;

import backend.interfaces.Figure;

public class Rectangle implements Figure {

    private Point topLeft, bottomRight;

    public Rectangle(Point topLeft, Point bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    public void setStartPoint(Point startPoint) {
        this.topLeft = startPoint;
    }

    public void setEndPoint(Point endPoint){
        this.bottomRight = endPoint;
    }

    @Override
    public Point getStartPoint() {
        return topLeft;
    }

    @Override
    public Point getEndPoint() {
        return bottomRight;
    }

    @Override
    public Point getCenterPoint(){
        return new Point((getStartPoint().getX() + getEndPoint().getX()) / 2, (getStartPoint().getY() + getEndPoint().getY()) / 2);
    }

    @Override
    public double getWidth() {
        return Math.abs(getStartPoint().getX() - getEndPoint().getX());
    }

    @Override
    public double getHeight() {
        return Math.abs(getStartPoint().getY() - getEndPoint().getY());
    }

    @Override
    public void move(double deltaX, double deltaY){
        topLeft.move(deltaX, deltaY);
        bottomRight.move(deltaX, deltaY);
    }

    @Override
    public boolean contains(Point eventPoint) {
        return eventPoint.getX() > topLeft.getX() && eventPoint.getX() < bottomRight.getX() && eventPoint.getY() > topLeft.getY() && eventPoint.getY() < bottomRight.getY();
    }

    @Override
    public String toString() {
        return String.format("Rectángulo [ %s , %s ]", topLeft, bottomRight);
    }
}
