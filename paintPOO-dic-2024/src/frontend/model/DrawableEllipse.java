package frontend.model;

import backend.interfaces.Figure;
import backend.model.Ellipse;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.*;
import javafx.scene.shape.ArcType;

public class DrawableEllipse extends DrawFigure {

    private final Ellipse ellipse;

    public DrawableEllipse(FigureInfo info, Figure figure, GraphicsContext gc) {
        super(info, figure, gc);
        this.ellipse = (Ellipse) figure;
    }

    @Override
    public void draw(GraphicsContext gc, FigureInfo info, Color strokeColor, Figure figure) {
        double x = ellipse.getCenterPoint().getX() - ellipse.getMajorAxis() / 2;
        double y = ellipse.getCenterPoint().getY() - ellipse.getMinorAxis() / 2;

        double width = ellipse.getMajorAxis();
        double height = ellipse.getMinorAxis();

        setShadowOval(gc, ellipse.getCenterPoint(), ellipse.getMinorAxis(), ellipse.getMajorAxis());

        // Configuración de colores de relleno y borde
        gc.setFill(getGradientColor(info.getColor(), info.getSecondaryColor()));
        gc.setStroke(strokeColor);

        // Dibuja la elipse
        gc.fillOval(x, y, width, height);
        gc.strokeOval(x, y, width, height);

        if (info.getArcType()) {
            setEllipseArcType(gc);
        }
        gc.setLineWidth(1);
    }

    public void moveAndSync(double deltaX, double deltaY) {
        ellipse.move(deltaX, deltaY);
    }

    private RadialGradient getGradientColor(Color firstFillColor, Color secondFillColor) {
        return new RadialGradient(0, 0, 0.5, 0.5, 0.5, true,
                CycleMethod.NO_CYCLE,
                new Stop(0, firstFillColor),
                new Stop(1, secondFillColor));
    }

    private void setEllipseArcType(GraphicsContext gc) {
        double ellipseX = ellipse.getCenterPoint().getX() - ellipse.getMajorAxis() / 2;
        double ellipseY = ellipse.getCenterPoint().getY() - ellipse.getMinorAxis() / 2;

        double width = ellipse.getMajorAxis();
        double height = ellipse.getMinorAxis();

        gc.setLineWidth(10);
        gc.setStroke(Color.LIGHTGRAY);
        gc.strokeArc(ellipseX, ellipseY, width, height, 45, 180, ArcType.OPEN);
        gc.setStroke(Color.BLACK);
        gc.strokeArc(ellipseX, ellipseY, width, height, 225, 180, ArcType.OPEN);
    }

    @Override
    public void rotateRight(FigureInfo info) {
        if (info.getRotate()) {
            double tempAxis = getFigure().getMajorAxis();
            ellipse.setsMayorAxis(ellipse.getMinorAxis());
            ellipse.setsMinorAxis(tempAxis);
            info.setRotate(false);
        }
    }

    private void customFlipEllipse(double deltaX, double deltaY, FigureInfo info, boolean isVertical) {
        Point newCenter = new Point(ellipse.getCenterPoint().getX() + deltaX, ellipse.getCenterPoint().getY() + deltaY);
        ellipse.setCenterPoint(newCenter);

        if (isVertical) {
            info.setFlipV(false);
        } else {
            info.setFlipH(false);
        }
    }

    public void flipVertically(FigureInfo info) {
        double height = ellipse.getMinorAxis();
        customFlipEllipse(0, height, info, true);
    }

    public void flipHorizontally(FigureInfo info) {
        double width = ellipse.getMajorAxis();
        customFlipEllipse(width, 0, info, false);
    }

}
