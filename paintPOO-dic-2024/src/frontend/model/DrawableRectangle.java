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
    public void draw(GraphicsContext gc, Color firstFillColor, Color secondFillColor, Color strokeColor) {

        double x = Math.min(rectangle.getTopLeft().getX(), rectangle.getBottomRight().getX());
        double y = Math.min(rectangle.getTopLeft().getY(), rectangle.getBottomRight().getY());

        double width = Math.abs(rectangle.getTopLeft().getX() - rectangle.getBottomRight().getX());
        double height = Math.abs(rectangle.getTopLeft().getY() - rectangle.getBottomRight().getY());

        setShadowRect(gc, rectangle.getTopLeft(), rectangle.getBottomRight());

        gc.setFill(getGradientColor(firstFillColor, secondFillColor));
        gc.setStroke(strokeColor);
        
        gc.fillRect(x, y, width, height);
        gc.strokeRect(x, y, width, height);
    }

    private LinearGradient getGradientColor(Color firstFillColor, Color secondFillColor){
        return new LinearGradient(0, 0, 1, 0, true,
                CycleMethod.NO_CYCLE,
                new Stop(0, firstFillColor),
                new Stop(1, secondFillColor));
    }

    @Override
    public Figure getFigure() {
        return rectangle;
    }

}
