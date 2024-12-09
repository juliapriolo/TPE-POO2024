package frontend.buttons;


import backend.interfaces.Figure;
import backend.model.Circle;
import backend.model.Point;

public class CircleButton extends EllipseButton {
    public CircleButton(String name) {
        super(name);
    }

    @Override
    public Figure create(Point startPoint, Point endPoint) {
        double radius = Math.abs(endPoint.getX() - startPoint.getX());
        return new Circle(startPoint, radius);
    }

    @Override
    public Figure createDividedFigure(Point startPoint, Point endPoint, Point centrePoint, double height, double width){
        return new Circle(centrePoint, height/2);
    }

}