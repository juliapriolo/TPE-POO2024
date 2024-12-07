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
        double x = circle.getCenterPoint().getX() - circle.getRadius();
        double y = circle.getCenterPoint().getY() - circle.getRadius();
        double radius = circle.getRadius();

        setShadowOval(gc, circle.getCenterPoint(),radius*2, radius*2);

        gc.setFill(getGradientColor(info.getColor(), info.getSecondaryColor()));
        gc.setStroke(strokeColor);

        gc.fillOval(x, y, radius*2, radius*2);
        gc.strokeOval(x, y, radius*2, radius*2);

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
        double arcX = circle.getCenterPoint().getX() - circle.getRadius();
        double arcY = circle.getCenterPoint().getY() - circle.getRadius();

        gc.setLineWidth(10);
        gc.setStroke(Color.LIGHTGRAY);
        gc.strokeArc(arcX, arcY, circle.getRadius()*2, circle.getRadius()*2, 45, 180, ArcType.OPEN);
        gc.setStroke(Color.BLACK);
        gc.strokeArc(arcX, arcY, circle.getRadius()*2, circle.getRadius()*2, 225, 180, ArcType.OPEN);
    }

    public void voltearH(FigureInfo info){
        double width = circle.getRadius();
        circle.setCenterPoint(new Point(circle.getCenterPoint().getX() + width, circle.getCenterPoint().getY()));

        info.setVoltearH(false);
    }

    public void voltearV(FigureInfo info){
        double height = circle.getRadius();
        circle.setCenterPoint(new Point(circle.getCenterPoint().getX(), circle.getCenterPoint().getY() + height));

        info.setVoltearV(false);
    }

    @Override
    public void rotateRight(FigureInfo info){
        if (info.getRotate()) {
            info.setRotate(false);
        }
    }

    @Override
    public Figure getFigure(){
        return circle;
    }
}
