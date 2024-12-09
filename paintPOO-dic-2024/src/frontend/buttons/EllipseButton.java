package frontend.buttons;

import backend.interfaces.Figure;
import backend.model.Ellipse;
import backend.model.Point;
import frontend.model.DrawFigure;
import frontend.model.DrawableEllipse;
import frontend.model.FigureInfo;
import javafx.scene.canvas.GraphicsContext;

public class EllipseButton extends FigureButton {

    public EllipseButton(String name) {
        super(name);
    }

    @Override
    public Figure create(Point startPoint, Point endPoint) {
        double sMayorAxis = Math.abs(endPoint.getX() - startPoint.getX());
        double sMinorAxis = Math.abs(endPoint.getY() - startPoint.getY());
        return new Ellipse(startPoint, sMayorAxis, sMinorAxis);
    }

    @Override
    public DrawFigure createDrawFigure(FigureInfo info,Figure figure, GraphicsContext gc) {
        return new DrawableEllipse(info, figure, gc);
    }
}