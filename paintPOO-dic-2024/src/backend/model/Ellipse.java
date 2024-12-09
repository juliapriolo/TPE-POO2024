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

    public Point getCenterPoint() {
        return centerPoint;
    }

    public void setCenterPoint(Point centerPoint){
        this.centerPoint = centerPoint;
    }

    @Override
    public Point getStartPoint() {
        return getCenterPoint();
    }

    @Override
    public Point getEndPoint() {
        return new Point(centerPoint.getX() + getMajorAxis(), centerPoint.getY() + getMinorAxis());
    }

    public double getMajorAxis() {
        return sMajorAxis;
    }

    public double getMinorAxis() {
        return sMinorAxis;
    }

    @Override
    public String toString() {
        return String.format("Elipse [Centro: %s, DMayor: %.2f, DMenor: %.2f]", centerPoint, sMajorAxis, sMinorAxis);
    }

    public void setsMayorAxis(double sMayorAxis) {
        this.sMajorAxis = sMayorAxis;
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
        double h = centerPoint.getX();
        double k = centerPoint.getY();
        double a = sMajorAxis / 2;
        double b = sMinorAxis / 2;

        double x = eventPoint.getX();
        double y = eventPoint.getY();

        // Ecuación de la elipse
        double equation = Math.pow((x - h) / a, 2) + Math.pow((y - k) / b, 2);

        return equation <= 1; // El punto está dentro de la elipse
    }

    @Override
    public double getWidth() {
        return 0;
    }

    @Override
    public double getHeight() {
        return 0;
    }
}
