package frontend.model;

import backend.interfaces.Figure;
import backend.model.Ellipse;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.*;
import javafx.scene.shape.ArcType;

public class DrawableEllipse extends DrawFigure {

    private Ellipse ellipse;

    public DrawableEllipse(FigureInfo info, Figure figure, GraphicsContext gc) {
        super(info, figure, gc);
        this.ellipse = (Ellipse) getFigure();
    }

    @Override
    public void draw(GraphicsContext gc, FigureInfo info, Color strokeColor, Figure figure) {
        double x = ellipse.getCenterPoint().getX() - ellipse.getsMayorAxis() / 2;
        double y = ellipse.getCenterPoint().getY() - ellipse.getsMinorAxis() / 2;
        double width = ellipse.getsMayorAxis();
        double height = ellipse.getsMinorAxis();

        setShadowOval(gc, ellipse.getCenterPoint(), ellipse.getsMinorAxis(), ellipse.getsMayorAxis());

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

    public void moveAndSync(double deltaX, double deltaY, FigureInfo info) {
        ellipse.move(deltaX, deltaY);
        updateInfo(info);
    }

    private RadialGradient getGradientColor(Color firstFillColor, Color secondFillColor) {
        return new RadialGradient(0, 0, 0.5, 0.5, 0.5, true,
                CycleMethod.NO_CYCLE,
                new Stop(0, firstFillColor),
                new Stop(1, secondFillColor));
    }

    private void setEllipseArcType(GraphicsContext gc) {
        double ellipseX = ellipse.getCenterPoint().getX() - ellipse.getWidth() / 2;
        double ellipseY = ellipse.getCenterPoint().getY() - ellipse.getHeight() / 2;
        double width = ellipse.getWidth();
        double height = ellipse.getHeight();

        gc.setLineWidth(10);
        gc.setStroke(Color.LIGHTGRAY);
        gc.strokeArc(ellipseX, ellipseY, width, height, 45, 180, ArcType.OPEN);
        gc.setStroke(Color.BLACK);
        gc.strokeArc(ellipseX, ellipseY, width, height, 225, 180, ArcType.OPEN);
    }

    @Override
    public void rotateRight(FigureInfo info) {
        if (info.getRotate()) {
            double tempAxis = getFigure().getWidth();
            ellipse.setsMayorAxis(ellipse.getsMinorAxis());
            ellipse.setsMinorAxis(tempAxis);
            updateInfo(info);
            info.setRotate(false);
        }
    }


    private void customFlipEllipse(double deltaX, double deltaY, FigureInfo info, boolean isVertical) {
        Point newCenter = new Point(ellipse.getCenterPoint().getX() + deltaX, ellipse.getCenterPoint().getY() + deltaY);
        ellipse.setCenterPoint(newCenter);

        updateInfo(info);

        if (isVertical) {
            info.setFlipV(false);
        } else {
            info.setFlipH(false);
        }
    }

    public void flipVertically(FigureInfo info) {
        double height = ellipse.getHeight();
        customFlipEllipse(0, height, info, true);
    }

    public void flipHorizontally(FigureInfo info) {
        double width = ellipse.getWidth();
        customFlipEllipse(width, 0, info, false);
    }

    public void updateInfo(FigureInfo info) {
        double x = ellipse.getCenterPoint().getX();
        double y = ellipse.getCenterPoint().getY();

        double width = ellipse.getWidth();
        double height = ellipse.getHeight();

        if (isCircle(info)) { // Método para verificar si es un círculo
            width /= 2;
            height /= 2;
        }

        Point newStartPoint = new Point(x, y);
        Point newEndPoint = new Point(x + width, y + height);

        info.setStartPoint(newStartPoint);
        info.setEndPoint(newEndPoint);
    }

    private boolean isCircle(FigureInfo info) {
        return ellipse.getWidth() == ellipse.getHeight(); // O usa una bandera específica en `info`
    }

    @Override
    public DrawFigure[] divide(FigureInfo info, GraphicsContext gc) {
        double width = ellipse.getsMayorAxis();
        double height = ellipse.getsMinorAxis();

        double centerX = ellipse.getCenterPoint().getX();
        double centerY = ellipse.getCenterPoint().getY();

        double halfWidth = width / 2;
        double halfHeight = height / 2;

        // Primer elipse
        Point newCenter1 = new Point(centerX - halfWidth / 2, centerY); // Alineado al centro de la altura
        Ellipse newEllipse1 = new Ellipse(newCenter1, halfWidth, halfHeight);

        // Segunda elipse
        Point newCenter2 = new Point(centerX + halfWidth / 2, centerY); // Alineado al centro de la altura
        Ellipse newEllipse2 = new Ellipse(newCenter2, halfWidth, halfHeight);

        DrawFigure drawable1 = new DrawableEllipse(info, newEllipse1, gc);
        DrawFigure drawable2 = new DrawableEllipse(info, newEllipse2, gc);

        return new DrawFigure[]{drawable1, drawable2};
    }



}
