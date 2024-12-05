package frontend.buttons;

import backend.interfaces.Figure;
import backend.model.Point;
import backend.model.Rectangle;
import frontend.model.DrawFigure;
import frontend.model.DrawableRectangle;
import javafx.scene.canvas.GraphicsContext;

public class RectangleButton extends FigureButton {

    public RectangleButton(String name) {
        super(name);
    }

    /*@Override
    public Figure create(Point startPoint, Point endPoint) {
        return new Rectangle(startPoint, endPoint);
    }*/

    @Override
    public DrawFigure createDrawFigure(Point startPoint, Point endPoint) {
        return new DrawableRectangle(startPoint, endPoint);
    }



}
