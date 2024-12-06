package frontend.buttons;

import backend.interfaces.Figure;
import backend.model.Point;
import backend.model.Square;
import frontend.model.DrawFigure;
import frontend.model.DrawableRectangle;
import frontend.model.DrawableSquare;
import frontend.model.FigureInfo;

public class SquareButton extends FigureButton{
    public SquareButton(String name) {
        super(name);
    }

    @Override
    public Figure create(Point startPoint, Point endPoint) {
        return new Square(startPoint, Math.min(
                Math.abs(endPoint.getX() - startPoint.getX()),
                Math.abs(endPoint.getY() - startPoint.getY())
        ));
    }

    @Override
    public DrawFigure createDrawFigure(Point startPoint, Point endPoint, FigureInfo info) {
        return new DrawableSquare(startPoint, endPoint, info);
    }
}