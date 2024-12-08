package frontend.buttons;

import backend.interfaces.Figure;
import backend.model.Point;
import backend.model.Rectangle;
import frontend.model.DrawFigure;
import frontend.model.DrawableRectangle;
import frontend.model.FigureInfo;
import javafx.scene.canvas.GraphicsContext;

public class RectangleButton extends FigureButton {

    public RectangleButton(String name) {
        super(name);
    }

    @Override
    public Figure create(Point startPoint, Point endPoint) {
        return new Rectangle(startPoint, endPoint);
    }

    @Override
    public DrawFigure createDrawFigure(FigureInfo info,Figure figure, GraphicsContext gc) {
        return new DrawableRectangle(info,figure,gc);
    }



}
