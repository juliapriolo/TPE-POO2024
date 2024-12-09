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
        return Math.abs(getStartPoint().getX() - getEndPoint().getX());
    }

    @Override
    public double getHeight() {
        return Math.abs(getStartPoint().getY() - getEndPoint().getY());
    }

    @Override
    public double getMajorAxis(){
        return 0;
    }

    @Override
    public double getMinorAxis(){
        return 0;
    }

    @Override
    public Point getStartPoint() {
        return topLeft;
    }

    @Override
    public Point getEndPoint() {
        return bottomRight;
    }

    public Point getCenter(){
        return new Point((getStartPoint().getX() + getEndPoint().getX()) / 2, (getStartPoint().getY() + getEndPoint().getY()) / 2);
    }

    public void setStartPoint(Point startPoint) {
        this.topLeft = startPoint;
    }

    public void setEndPoint(Point endPoint){
        this.bottomRight = endPoint;
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
