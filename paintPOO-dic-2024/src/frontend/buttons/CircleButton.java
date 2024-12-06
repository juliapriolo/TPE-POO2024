package frontend.buttons;


import backend.interfaces.Figure;
import backend.model.Circle;
import backend.model.Ellipse;
import backend.model.Point;
import frontend.model.DrawFigure;
import frontend.model.DrawableCircle;
import frontend.model.DrawableEllipse;
import frontend.model.FigureInfo;

public class CircleButton extends FigureButton {
    public CircleButton(String name) {
        super(name);
    }

    @Override
    public Figure create(Point startPoint, Point endPoint) {
        double radius = Math.abs(endPoint.getX() - startPoint.getX());
        return new Circle(startPoint, radius);
    }

    @Override
    public DrawFigure createDrawFigure(Point startPoint, Point endPoint, FigureInfo info) {
        double radius = Math.abs(endPoint.getX() - startPoint.getX());
        return new DrawableCircle(startPoint, radius, info);
    }
}