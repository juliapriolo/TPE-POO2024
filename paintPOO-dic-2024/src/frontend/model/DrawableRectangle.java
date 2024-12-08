package frontend.model;

import backend.interfaces.Figure;
import backend.model.Point;
import backend.model.Rectangle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.*;


public class DrawableRectangle extends DrawFigure {

    private Rectangle rectangle;

    public DrawableRectangle(FigureInfo info, Figure figure, GraphicsContext gc) {
        super(info, figure, gc);
        this.rectangle = (Rectangle) figure;
    }

    @Override
    public void draw(GraphicsContext gc, FigureInfo info, Color strokeColor,Figure figure) {

        double x = Math.min(rectangle.getTopLeft().getX(), rectangle.getBottomRight().getX());
        double y = Math.min(rectangle.getTopLeft().getY(), rectangle.getBottomRight().getY());

        double width = rectangle.getWidth();
        double height = rectangle.getHeight();

        setShadowRect(gc, rectangle.getTopLeft(), rectangle.getBottomRight());

        gc.setFill(getGradientColor(info.getColor(), info.getSecondaryColor()));
        gc.setStroke(strokeColor);

        gc.fillRect(x, y, width, height);
        gc.strokeRect(x, y, width, height);

        if (info.getArcType()) {
            setRectangleArcType(gc);
        }
        gc.setLineWidth(1);
    }

    private LinearGradient getGradientColor(Color firstFillColor, Color secondFillColor) {
        return new LinearGradient(0, 0, 1, 0, true,
                CycleMethod.NO_CYCLE,
                new Stop(0, firstFillColor),
                new Stop(1, secondFillColor));
    }

    private void setRectangleArcType(GraphicsContext gc) {
        double x = rectangle.getTopLeft().getX();
        double y = rectangle.getTopLeft().getY();

        double width = rectangle.getWidth();
        double height = rectangle.getHeight();

        gc.setLineWidth(10);
        gc.setStroke(Color.LIGHTGRAY);
        gc.strokeLine(x, y, x + width, y);
        gc.strokeLine(x, y, x, y + height);
        gc.setStroke(Color.BLACK);
        gc.strokeLine(x + width, y, x + width, y + height);
        gc.strokeLine(x, y + height, x + width, y + height);
    }

    @Override
    public void rotateRight(FigureInfo info) {
        if (info.getRotate()) {
            double halfNewWidth = rectangle.getHeight() / 2;
            double halfNewHeight = rectangle.getWidth() / 2;

            Point newTopLeft = new Point(rectangle.getCenter().getX() - halfNewWidth, rectangle.getCenter().getY() - halfNewHeight);
            Point newBottomRight = new Point(rectangle.getCenter().getX() + halfNewWidth, rectangle.getCenter().getY() + halfNewHeight);

            updateInfo(newTopLeft, newBottomRight, info);

            // Una vez rotada, resetea el flag para evitar rotaciones repetidas.
            info.setRotate(false);
        }
    }

    public void moveAndSync(double deltaX, double deltaY, FigureInfo info) {
        double centerX = (rectangle.getTopLeft().getX() + rectangle.getBottomRight().getX()) / 2;
        double centerY = (rectangle.getTopLeft().getY() + rectangle.getBottomRight().getY()) / 2;

        double newCenterX = centerX + deltaX;
        double newCenterY = centerY + deltaY;

        double width = Math.abs(rectangle.getBottomRight().getX() - rectangle.getTopLeft().getX());
        double height = Math.abs(rectangle.getBottomRight().getY() - rectangle.getTopLeft().getY());

        Point newTopLeft = new Point(newCenterX - width / 2, newCenterY - height / 2);
        Point newBottomRight = new Point(newCenterX + width / 2, newCenterY + height / 2);

        updateInfo(newTopLeft, newBottomRight, info);
    }

    public void updateInfo(Point newStartPoint, Point newEndPoint, FigureInfo info) {
        rectangle.setTopLeft(newStartPoint);
        rectangle.setBottomRight(newEndPoint);

        info.setStartPoint(newStartPoint);
        info.setEndPoint(newEndPoint);
    }

    public void customFlipRect(double deltaX, double deltaY, FigureInfo info, boolean isVertical){
        Point newTopLeft = new Point(rectangle.getTopLeft().getX() + deltaX, rectangle.getTopLeft().getY() + deltaY);
        Point newBottomRight = new Point(rectangle.getBottomRight().getX() + deltaX, rectangle.getBottomRight().getY() + deltaY);

        updateInfo(newTopLeft, newBottomRight, info);

        if(isVertical){
            info.setFlipV(false);
        }else{
            info.setFlipH(false);
        }
    }

    public void flipVertically(FigureInfo info) {
        double height = rectangle.getHeight();

        customFlipRect(0, height, info, true);
    }

    public void flipHorizontally(FigureInfo info) {
        double width = rectangle.getWidth();

        customFlipRect(width, 0, info, false);
    }


}
