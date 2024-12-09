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
        double width = Math.abs(endPoint.getX() - startPoint.getX());
        double height = Math.abs(endPoint.getY() - startPoint.getY());

        double centerX = Math.min(startPoint.getX(), endPoint.getX()) + width / 2;
        double centerY = Math.min(startPoint.getY(), endPoint.getY()) + height / 2;

        return new Ellipse(new Point(centerX, centerY), width, height);
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