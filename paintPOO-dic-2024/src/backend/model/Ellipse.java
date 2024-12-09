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

    @Override
    public Point getStartPoint() {
        return getCenterPoint();
    }

    @Override
    public Point getEndPoint() {
        return new Point(centerPoint.getX() + getWidth(), centerPoint.getY() + getHeight());
    }

    public void setCenterPoint(Point centerPoint){
        this.centerPoint = centerPoint;
    }

    @Override
    public Point getCenterPoint() {
        return centerPoint;
    }

    public void setMajorAxis(double sMayorAxis) {
        this.sMajorAxis = sMayorAxis;
    }
    @Override
    public double getWidth() {
        return sMajorAxis;
    }

    public void setMinorAxis(double sMinorAxis) {
        this.sMinorAxis = sMinorAxis;
    }
    @Override
    public double getHeight() {
        return sMinorAxis;
    }

    @Override
    public void move(double deltaX, double deltaY){
        centerPoint.move(deltaX, deltaY);
    }

    //Verifica directamente en el rectangulo que la contiene
    @Override
    public boolean contains(Point eventPoint) {
        double xCenter = centerPoint.getX();
        double yCenter = centerPoint.getY();
        double majorA = sMajorAxis / 2;
        double minorA = sMinorAxis / 2;

        double x = eventPoint.getX();
        double y = eventPoint.getY();

        // Ecuación de la elipse
        double equation = Math.pow((x - xCenter) / majorA, 2) + Math.pow((y - yCenter) / minorA, 2);

        return equation <= 1; // El punto está dentro de la elipse
    }

    @Override
    public String toString() {
        return String.format("Elipse [Centro: %s, DMayor: %.2f, DMenor: %.2f]", centerPoint, sMajorAxis, sMinorAxis);
    }
}
