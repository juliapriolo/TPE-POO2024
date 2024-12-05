package frontend.buttons;

import backend.interfaces.Figure;
import backend.model.Point;
import frontend.model.DrawFigure;
import frontend.model.DrawableRectangle;

public class SquareButton extends FigureButton{
    public SquareButton(String name) {
        super(name);
    }

   /* @Override
    public Figure create(Point startPoint, Point endPoint) {
        return (Figure) new DrawableRectangle(startPoint, endPoint);
    }*/

    @Override
    public DrawFigure createDrawFigure(Point startPoint, Point endPoint) {
        return new DrawableRectangle(startPoint, endPoint);
    }
}