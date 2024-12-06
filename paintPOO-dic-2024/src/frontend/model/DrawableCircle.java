package frontend.model;

import backend.interfaces.Figure;
import backend.model.Circle;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;

public class DrawableCircle extends DrawFigure{
    private final Circle circle;

    public DrawableCircle(Point centerPoint, double radius){
        this.circle = new Circle(centerPoint, radius);
    }

    @Override
    public void draw(GraphicsContext gc, Color firstFillColor, Color secondFillColor, Color strokeColor){
        double x = circle.getCenterPoint().getX() - circle.getRadius() / 2;
        double y = circle.getCenterPoint().getY() - circle.getRadius() / 2;
        double radius = circle.getRadius();

        gc.setFill(getGradientColor(firstFillColor, secondFillColor));
        gc.setStroke(strokeColor);

        gc.fillOval(x, y, radius, radius);
        gc.strokeOval(x, y, radius, radius);
    }

    private RadialGradient getGradientColor(Color firstFillColor, Color secondFillColor){
        return  new RadialGradient(0, 0, 0.5, 0.5, 0.5, true,
                CycleMethod.NO_CYCLE,
                new Stop(0, firstFillColor),
                new Stop(1, secondFillColor));
    }

    @Override
    public Figure getFigure(){
        return circle;
    }
}
