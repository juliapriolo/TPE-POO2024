package frontend.buttons;

import backend.interfaces.Figure;
import backend.model.Point;
import backend.model.Square;

public class SquareButton extends RectangleButton{
    public SquareButton(String name) {
        super(name);
    }

    @Override
    public Figure create(Point startPoint, Point endPoint) {
        return new Square(startPoint, getSize(startPoint, endPoint));
    }

    @Override
    public Figure createDividedFigure(Point startPoint, Point endPoint, Point centrePoint, double height, double width){
        return new Square(startPoint, getSize(startPoint, endPoint));
    }

    private double getSize(Point startPoint, Point endPoint){
        return Math.abs(endPoint.getX() - startPoint.getX());
    }

}