package frontend.model;

import backend.interfaces.Figure;
import backend.model.Point;
import backend.model.Rectangle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.*;


public class DrawableRectangle extends DrawFigure {

    private final Rectangle rectangle;

    public DrawableRectangle(Point topLeft, Point bottomRight, FigureInfo info) {
        super(info);
        this.rectangle = new Rectangle(topLeft, bottomRight);
    }

    @Override
    public void draw(GraphicsContext gc, FigureInfo info, Color strokeColor) {

        double x = Math.min(rectangle.getTopLeft().getX(), rectangle.getBottomRight().getX());
        double y = Math.min(rectangle.getTopLeft().getY(), rectangle.getBottomRight().getY());

        double width = Math.abs(rectangle.getTopLeft().getX() - rectangle.getBottomRight().getX());
        double height = Math.abs(rectangle.getTopLeft().getY() - rectangle.getBottomRight().getY());

        setShadowRect(gc, rectangle.getTopLeft(), rectangle.getBottomRight());

        gc.setFill(getGradientColor(info.getColor(), info.getSecondaryColor()));
        gc.setStroke(strokeColor);

        gc.fillRect(x, y, width, height);
        gc.strokeRect(x, y, width, height);

        if(info.getArcType()){
            setRectangleArcType(gc);
        }
        gc.setLineWidth(1);

        if(info.getRotate()){
            info.setRotate(false);
            rotateRight();
        }

    }

    private LinearGradient getGradientColor(Color firstFillColor, Color secondFillColor){
        return new LinearGradient(0, 0, 1, 0, true,
                CycleMethod.NO_CYCLE,
                new Stop(0, firstFillColor),
                new Stop(1, secondFillColor));
    }

    private void setRectangleArcType(GraphicsContext gc){
        double x = rectangle.getTopLeft().getX();
        double y = rectangle.getTopLeft().getY();

        double width = Math.abs(x - rectangle.getBottomRight().getX());
        double height = Math.abs(y - rectangle.getBottomRight().getY());

        gc.setLineWidth(10);
        gc.setStroke(Color.LIGHTGRAY);
        gc.strokeLine(x, y, x + width, y);
        gc.strokeLine(x, y, x, y + height);
        gc.setStroke(Color.BLACK);
        gc.strokeLine(x + width, y, x + width, y + height);
        gc.strokeLine(x, y + height, x + width, y + height);
    }

    public void rotateRight() {
        double topLeftX = rectangle.getTopLeft().getX();
        double topLeftY = rectangle.getTopLeft().getY();
        double bottomRightX = rectangle.getBottomRight().getX();
        double bottomRightY = rectangle.getBottomRight().getY();

        double centerX = (topLeftX + bottomRightX) / 2;
        double centerY = (topLeftY + bottomRightY) / 2;

        double deltaX = topLeftX - centerX;
        double deltaY = topLeftY - centerY;
        double newTopLeftX = centerX - deltaY;
        double newTopLeftY = centerY + deltaX;

        deltaX = bottomRightX - centerX;
        deltaY = bottomRightY - centerY;
        double newBottomRightX = centerX - deltaY;
        double newBottomRightY = centerY + deltaX;

        // Establecer los nuevos puntos para el rectángulo
        rectangle.setTopLeft(new Point(newTopLeftX, newTopLeftY));
        rectangle.setBottomRight(new Point(newBottomRightX, newBottomRightY));
    }


    @Override
    public Figure getFigure() {
        return rectangle;
    }

}
