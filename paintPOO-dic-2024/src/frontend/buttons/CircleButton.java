package frontend.buttons;


import backend.interfaces.Figure;
import backend.model.Point;
import frontend.model.DrawableEllipse;

public class CircleButton extends FigureButton {
    public CircleButton(String name) {
        super(name);
    }

    @Override
    public Figure create(Point startPoint, Point endPoint) {
        double radius = Math.abs(endPoint.getX() - startPoint.getX());
        return (Figure) new DrawableEllipse(startPoint, radius, radius);
    }

}