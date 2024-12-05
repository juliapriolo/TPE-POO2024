package frontend.model;

import backend.interfaces.Figure;
import backend.model.Circle;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawableCircle extends DrawFigure{
    private final Circle circle;

    public DrawableCircle(Point centerPoint, double radius){
        this.circle = new Circle(centerPoint, radius);
    }

    @Override
    public void draw(GraphicsContext gc, Color fillColor, Color strokeColor){
        double x = circle.getCenterPoint().getX() - circle.getRadius() / 2;
        double y = circle.getCenterPoint().getY() - circle.getRadius() / 2;
        double radius = circle.getRadius();

        gc.setFill(fillColor);
        gc.setStroke(strokeColor);

        gc.fillOval(x, y, radius, radius);
        gc.strokeOval(x, y, radius, radius);
    }

    @Override
    public Figure getFigure(){
        return circle;
    }
}
