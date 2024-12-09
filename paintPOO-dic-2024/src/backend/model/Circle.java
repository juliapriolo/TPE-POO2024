package backend.model;

public class Circle extends Ellipse {

    public Circle(Point centerPoint, double radius) {
        super(centerPoint, 2*radius, 2*radius);
    }

    @Override
    public String toString() {
        return String.format("Círculo [Centro: %s, Radio: %.2f]", getCenterPoint(), getMinorAxis()/2);
    }

    public double getRadius() {
        return getMinorAxis()/2;
    }

    @Override
    public boolean contains(Point eventPoint) {
        double distance = Math.pow(eventPoint.getX() - getCenterPoint().getX(), 2) +
                Math.pow(eventPoint.getY() - getCenterPoint().getY(), 2);

        return distance <= Math.pow(getRadius(), 2);
    }

    @Override
    public Point getEndPoint(){
        return new Point(getCenterPoint().getX() + getRadius(), getCenterPoint().getY());
    }
}
