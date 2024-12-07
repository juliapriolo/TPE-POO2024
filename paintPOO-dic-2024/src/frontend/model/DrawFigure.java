package frontend.model;

import backend.interfaces.Figure;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class DrawFigure {

    private FigureInfo info;

    public DrawFigure(FigureInfo info){
        this.info = info;
    }

    public abstract void draw(GraphicsContext gc, FigureInfo info, Color colorStroke);

    public abstract Figure getFigure();

    public abstract void rotateRight(FigureInfo info);

    public void setShadowOval(GraphicsContext gc, Point centerPoint, double sMinorAxis, double sMayorAxis){
        if(!info.getShadowType().equals(ShadowType.NONE)){
            gc.setFill(info.getShadowType().getColor(info.getColor()));

            double coordX = centerPoint.getX() - sMayorAxis/2 + info.getShadowType().getOffSet();
            double coordY = centerPoint.getY() - sMinorAxis/2 + info.getShadowType().getOffSet();

            gc.fillOval(coordX, coordY, sMayorAxis, sMinorAxis);
        }
    }

    public void setShadowRect(GraphicsContext gc, Point startPoint, Point endPoint){
        if(!info.getShadowType().equals(ShadowType.NONE)){
            gc.setFill(info.getShadowType().getColor(info.getColor()));

            double width = Math.abs(endPoint.getX() - startPoint.getX());
            double height = Math.abs(endPoint.getY() - startPoint.getY());

            double adjustedX = startPoint.getX() + info.getShadowType().getOffSet();
            double adjustedY = startPoint.getY() + info.getShadowType().getOffSet();

            gc.fillRect(adjustedX, adjustedY, width, height);
        }
    }
}