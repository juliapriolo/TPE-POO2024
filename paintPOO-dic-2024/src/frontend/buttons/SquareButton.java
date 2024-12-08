package frontend.buttons;

import backend.interfaces.Figure;
import backend.model.Point;
import backend.model.Square;

public class SquareButton extends RectangleButton{
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

}