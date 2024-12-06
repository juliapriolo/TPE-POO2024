package frontend.model;

import backend.interfaces.Figure;
import backend.model.Circle;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.ArcType;

public class DrawableCircle extends DrawFigure{
    private final Circle circle;

    public DrawableCircle(Point centerPoint, double radius, FigureInfo info){
        super(info);
        this.circle = new Circle(centerPoint, radius);
    }

    @Override
    public void draw(GraphicsContext gc, FigureInfo info, Color strokeColor){
        double x = circle.getCenterPoint().getX() - circle.getRadius() / 2;
        double y = circle.getCenterPoint().getY() - circle.getRadius() / 2;
        double radius = circle.getRadius();

        setShadowOval(gc, circle.getCenterPoint(), circle.getRadius(), circle.getRadius());

        gc.setFill(getGradientColor(info.getColor(), info.getSecondaryColor()));
        gc.setStroke(strokeColor);

        gc.fillOval(x, y, radius, radius);
        gc.strokeOval(x, y, radius, radius);

        if(info.getArcType()){
            setCircleArcType(gc);
        }
        gc.setLineWidth(1);
    }

    private RadialGradient getGradientColor(Color firstFillColor, Color secondFillColor){
        return  new RadialGradient(0, 0, 0.5, 0.5, 0.5, true,
                CycleMethod.NO_CYCLE,
                new Stop(0, firstFillColor),
                new Stop(1, secondFillColor));
    }

    private void setCircleArcType(GraphicsContext gc){
        double arcX = circle.getCenterPoint().getX() - circle.getRadius() / 2;
        double arcY = circle.getCenterPoint().getY() - circle.getRadius() / 2;

        gc.setLineWidth(10);
        gc.setStroke(Color.LIGHTGRAY);
        gc.strokeArc(arcX, arcY, circle.getRadius(), circle.getRadius(), 45, 180, ArcType.OPEN);
        gc.setStroke(Color.BLACK);
        gc.strokeArc(arcX, arcY, circle.getRadius(), circle.getRadius(), 225, 180, ArcType.OPEN);
    }

    @Override
    public Figure getFigure(){
        return circle;
    }
}
