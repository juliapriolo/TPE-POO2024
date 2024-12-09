package frontend.buttons;

import backend.interfaces.Figure;
import backend.model.Ellipse;
import backend.model.Point;
import frontend.model.DrawFigure;
import frontend.model.DrawableEllipse;
import frontend.model.FigureInfo;

public class EllipseButton extends FigureButton {

    public EllipseButton(String name) {
        super(name);
    }

    @Override
    public Figure create(Point startPoint, Point endPoint) {
        double sMajorAxis = Math.abs(endPoint.getX() - startPoint.getX());
        double sMinorAxis = Math.abs(endPoint.getY() - startPoint.getY());
        return new Ellipse(startPoint, sMajorAxis, sMinorAxis);
    }

    @Override
    public Figure createDividedFigure(Point startPoint, Point endPoint, Point centrePoint, double height, double width){
        return new Ellipse(centrePoint, width, height);
    }

    @Override
    public DrawFigure createDrawFigure(FigureInfo info,Figure figure) {
        return new DrawableEllipse(info, figure);
    }
}