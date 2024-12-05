package frontend.model;

import backend.interfaces.Figure;
import backend.model.Point;
import backend.model.Square;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawableSquare extends DrawFigure{

    private final Square square;

    public DrawableSquare(Point startPoint, Point endPoint){
        double size = calculateSize(startPoint, endPoint);
        this.square = new Square(startPoint, size);
    }

    private double calculateSize(Point startPoint, Point endPoint){
        return Math.min(
                Math.abs(endPoint.getX() - startPoint.getX()),
                Math.abs(endPoint.getY() - startPoint.getY())
        );
    }

    @Override
    public void draw(GraphicsContext gc, Color fillColor, Color strokeColor){
        double x = Math.min(square.getTopLeft().getX(), square.getBottomRight().getX());
        double y = Math.min(square.getTopLeft().getY(), square.getBottomRight().getY());

        double side = Math.abs(square.getTopLeft().getX() - square.getBottomRight().getX());

        gc.setFill(fillColor);
        gc.setStroke(strokeColor);

        gc.fillRect(x, y, side, side);
        gc.strokeRect(x, y, side, side);
    }

    @Override
    public Figure getFigure(){
        return square;
    }
}
