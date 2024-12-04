package frontend.buttons;

import backend.interfaces.Figure;
import backend.model.Point;
import frontend.model.DrawableSquare;
import javafx.scene.canvas.GraphicsContext;

public class SquareButton extends FigureButton{
    public SquareButton(String name) {
        super(name);
    }

    @Override
    public Figure create(Point startPoint, Point endPoint) {
        double size = Math.abs(endPoint.getX() - startPoint.getX());
        return new DrawableSquare(startPoint, size);
    }
}