package backend.model;

public class Square extends Rectangle {


    public Square(Point topLeft, double size) {
        super(topLeft, new Point(topLeft.getX() + size, topLeft.getY() + size));
    }

    @Override
    public String toString() {
        return String.format("Cuadrado [ %s , %s ]", getStartPoint(), getEndPoint());
    }

    @Override
    public double getWidth(){
        return getSide(getStartPoint(), getEndPoint());
    }

    @Override
    public double getHeight(){
        return getSide(getStartPoint(), getEndPoint());
    }

    private double getSide(Point topLeft, Point bottomRight) {
        return Math.abs(topLeft.getX() - bottomRight.getX());
    }

}
