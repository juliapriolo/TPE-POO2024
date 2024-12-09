package backend.model;

import backend.interfaces.Figure;


public class Ellipse implements Figure {

    private Point centerPoint;
    private double sMayorAxis, sMinorAxis;

    public Ellipse(Point centerPoint, double sMayorAxis, double sMinorAxis) {
        this.centerPoint = centerPoint;
        this.sMayorAxis = sMayorAxis;
        this.sMinorAxis = sMinorAxis;
    }

    public Point getCenterPoint() {
        return centerPoint;
    }

    public void setCenterPoint(Point centerPoint){
        this.centerPoint = centerPoint;
    }

    public double getsMayorAxis() {
        return sMayorAxis;
    }

    public double getsMinorAxis() {
        return sMinorAxis;
    }

    @Override
    public Point getStartPoint() {
        return centerPoint;
    }

    @Override
    public Point getEndPoint() {
        return new Point(centerPoint.getX() + getWidth()/2, centerPoint.getY());
    }

    @Override
    public String toString() {
        return String.format("Elipse [Centro: %s, DMayor: %.2f, DMenor: %.2f]", centerPoint, sMayorAxis, sMinorAxis);
    }

    public void setsMayorAxis(double sMayorAxis) {
        this.sMayorAxis = sMayorAxis;
    }

    public void setsMinorAxis(double sMinorAxis) {
        this.sMinorAxis = sMinorAxis;
    }

    @Override
    public void move(double deltaX, double deltaY){
        centerPoint.move(deltaX, deltaY);
    }

    //Verifica directamente en el rectangulo que la contiene
    @Override
    public boolean contains(Point eventPoint) {
        double x = centerPoint.getX() - sMayorAxis / 2;
        double y = centerPoint.getY() - sMinorAxis / 2;

        return eventPoint.getX() >= x && eventPoint.getX() <= x + getWidth() && eventPoint.getY() >= y && eventPoint.getY() <= y + getHeight();
    }

    @Override
    public double getWidth() {
        return sMayorAxis;
    }

    @Override
    public double getHeight() {
        return sMinorAxis;
    }
}
