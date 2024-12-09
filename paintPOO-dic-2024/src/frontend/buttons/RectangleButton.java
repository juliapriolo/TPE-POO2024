package frontend.buttons;

import backend.interfaces.Figure;
import backend.model.Point;
import backend.model.Rectangle;
import frontend.model.DrawFigure;
import frontend.model.DrawableRectangle;
import frontend.model.FigureInfo;

public class RectangleButton extends FigureButton {

    public RectangleButton(String name) {
        super(name);
    }

    @Override
    public Figure create(Point startPoint, Point endPoint) {
        return new Rectangle(startPoint, endPoint);
    }

    @Override
    public Figure createDividedFigure(Point startPoint, Point endPoint, Point centrePoint, double height, double width){
        return create(startPoint, endPoint);
    }

    @Override
    public DrawFigure createDrawFigure(FigureInfo info,Figure figure) {
        return new DrawableRectangle(info,figure);
    }



}
