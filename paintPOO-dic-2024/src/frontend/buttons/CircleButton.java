package frontend.buttons;


import backend.interfaces.Figure;
import backend.model.Point;
import frontend.model.DrawFigure;
import frontend.model.DrawableEllipse;

public class CircleButton extends FigureButton {
    public CircleButton(String name) {
        super(name);
    }

    /*@Override
    public Figure create(Point startPoint, Point endPoint) {
        double radius = Math.abs(endPoint.getX() - startPoint.getX());
        return new Ellipse(startPoint, radius, radius);
    }*/

    @Override
    public DrawFigure createDrawFigure(Point startPoint, Point endPoint) {
        double radius = Math.abs(endPoint.getX() - startPoint.getX());
        return new DrawableEllipse(startPoint, radius, radius);
    }
}