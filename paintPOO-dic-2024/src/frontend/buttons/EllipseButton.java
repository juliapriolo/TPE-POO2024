package frontend.buttons;

import backend.interfaces.Figure;
import backend.model.Point;
import frontend.model.DrawableEllipse;
import javafx.scene.canvas.GraphicsContext;

public class EllipseButton extends FigureButton {
    public EllipseButton(String name) {
        super(name);
    }

    @Override
    public Figure create(Point startPoint, Point endPoint) {
        Point centerPoint = new Point(Math.abs(endPoint.getX() + startPoint.getX()) / 2, (Math.abs((endPoint.getY() + startPoint.getY())) / 2));
        double sMayorAxis = Math.abs(endPoint.getX() - startPoint.getX());
        double sMinorAxis = Math.abs(endPoint.getY() - startPoint.getY());
        return new DrawableEllipse(centerPoint, sMayorAxis, sMinorAxis);
    }
}