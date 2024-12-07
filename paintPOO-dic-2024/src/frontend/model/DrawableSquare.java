package frontend.model;

import backend.interfaces.Figure;
import backend.model.Point;
import backend.model.Square;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

public class DrawableSquare extends DrawFigure{

    private final Square square;

    public DrawableSquare(Point startPoint, Point endPoint, FigureInfo info){
        super(info);
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
    public void draw(GraphicsContext gc, FigureInfo info, Color strokeColor){
        double x = Math.min(square.getTopLeft().getX(), square.getBottomRight().getX());
        double y = Math.min(square.getTopLeft().getY(), square.getBottomRight().getY());

        double side = Math.abs(square.getTopLeft().getX() - square.getBottomRight().getX());

        setShadowRect(gc, square.getTopLeft(), square.getBottomRight());

        gc.setFill(getGradientColor(info.getColor(), info.getSecondaryColor()));
        gc.setStroke(strokeColor);

        gc.fillRect(x, y, side, side);
        gc.strokeRect(x, y, side, side);

        if(info.getArcType()) {
            setSquareArcType(gc);
        }
        gc.setLineWidth(1);
    }

    private LinearGradient getGradientColor(Color firstFillColor, Color secondFillColor){
        return new LinearGradient(0, 0, 1, 0, true,
                CycleMethod.NO_CYCLE,
                new Stop(0, firstFillColor),
                new Stop(1, secondFillColor));
    }

    private void setSquareArcType(GraphicsContext gc){
        double x = square.getTopLeft().getX();
        double y = square.getTopLeft().getY();

        double side = Math.abs(x - square.getBottomRight().getX());

        gc.setLineWidth(10);
        gc.setStroke(Color.LIGHTGRAY);
        gc.strokeLine(x, y, x + side, y);
        gc.strokeLine(x, y, x, y + side);
        gc.setStroke(Color.BLACK);
        gc.strokeLine(x + side, y, x + side, y + side);
        gc.strokeLine(x, y + side, x + side, y + side);
    }

    @Override
    public void rotateRight(FigureInfo info) {
        if (info.getRotate()) {
            double centerX = (square.getTopLeft().getX() + square.getBottomRight().getX()) / 2;
            double centerY = (square.getTopLeft().getY() + square.getBottomRight().getY()) / 2;

            double side = Math.abs(square.getBottomRight().getX() - square.getTopLeft().getX());

            Point newTopLeft = new Point(centerX - side / 2, centerY - side / 2);
            Point newBottomRight = new Point(centerX + side / 2, centerY + side / 2);

            square.setTopLeft(newTopLeft);
            square.setBottomRight(newBottomRight);

            // Resetea el flag después de rotar.
            info.setRotate(false);
        }
    }

    @Override
    public Figure getFigure(){
        return square;
    }
}
