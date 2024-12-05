package frontend.model;

import backend.interfaces.Figure;
import backend.model.Point;
import backend.model.Rectangle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class DrawableRectangle extends DrawFigure {

    private final Rectangle rectangle;

    public DrawableRectangle(Point topLeft, Point bottomRight) {
        this.rectangle = new Rectangle(topLeft, bottomRight);
    }

    @Override
    public void draw(GraphicsContext gc, Color fillColor, Color strokeColor) {

        double x = Math.min(rectangle.getTopLeft().getX(), rectangle.getBottomRight().getX());
        double y = Math.min(rectangle.getTopLeft().getY(), rectangle.getBottomRight().getY());

        double width = Math.abs(rectangle.getTopLeft().getX() - rectangle.getBottomRight().getX());
        double height = Math.abs(rectangle.getTopLeft().getY() - rectangle.getBottomRight().getY());

        gc.setFill(fillColor);
        gc.setStroke(strokeColor);
        
        gc.fillRect(x, y, width, height);
        gc.strokeRect(x, y, width, height);
    }


    @Override
    public Figure getFigure() {
        return rectangle;
    }

}
