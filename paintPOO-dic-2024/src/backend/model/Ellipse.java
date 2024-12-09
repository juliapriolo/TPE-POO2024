package backend.model;

import backend.interfaces.Figure;


public class Ellipse implements Figure {

    private Point centerPoint;
    private double sMajorAxis, sMinorAxis;

    public Ellipse(Point centerPoint, double sMajorAxis, double sMinorAxis) {
        this.centerPoint = centerPoint;
        this.sMajorAxis = sMajorAxis;
        this.sMinorAxis = sMinorAxis;
    }

    public void setCenterPoint(Point centerPoint){
        this.centerPoint = centerPoint;
    }

    public void setMajorAxis(double sMayorAxis) {
        this.sMajorAxis = sMayorAxis;
    }

    public void setMinorAxis(double sMinorAxis) { this.sMinorAxis = sMinorAxis; }

    @Override
    public Point getStartPoint() {
        return getCenterPoint();
    }

    @Override
    public Point getEndPoint() {
        return new Point(centerPoint.getX() + getWidth(), centerPoint.getY() + getHeight());
    }

    @Override
    public Figure duplicateWithOffset(double offsetX, double offsetY) {
        Point newCenter = new Point(
                centerPoint.getX() + offsetX,
                centerPoint.getY() + offsetY
        );
        return new Ellipse(newCenter, sMajorAxis, sMinorAxis);
    }


    @Override
    public Point getCenterPoint() {
        return centerPoint;
    }

    @Override
    public double getWidth() {
        return sMajorAxis;
    }

    @Override
    public double getHeight() {
        return sMinorAxis;
    }

    @Override
    public void move(double deltaX, double deltaY){
        centerPoint.move(deltaX, deltaY);
    }

    @Override
    public boolean contains(Point eventPoint) {
        double xCenter = centerPoint.getX();
        double yCenter = centerPoint.getY();
        double majorA = getWidth() / 2;
        double minorA = getHeight() / 2;

        double x = eventPoint.getX();
        double y = eventPoint.getY();

        double equation = Math.pow((x - xCenter) / majorA, 2) + Math.pow((y - yCenter) / minorA, 2);

        return equation <= 1;
    }

    @Override
    public String toString() {
        return String.format("Elipse [Centro: %s, DMayor: %.2f, DMenor: %.2f]", getCenterPoint(), getWidth(), getHeight());
    }
}
