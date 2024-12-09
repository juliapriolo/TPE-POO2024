package frontend.model;

import backend.interfaces.Figure;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class DrawFigure {

    private final FigureInfo info;

    private final Figure figure;


    public DrawFigure(FigureInfo info,Figure figure) {
        this.figure = figure;
        this.info = info;
    }

    public abstract void draw(GraphicsContext gc, FigureInfo info, Color colorStroke, Figure figure);

    public abstract void rotateRight(FigureInfo info);

    public abstract void flipHorizontally(FigureInfo info);

    public abstract void flipVertically(FigureInfo info);

    public abstract void moveAndSync(double deltaX, double deltaY);


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

    public Figure getFigure(){
        return figure;
    }
}