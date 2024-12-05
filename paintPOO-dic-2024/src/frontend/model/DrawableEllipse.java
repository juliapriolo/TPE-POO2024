package frontend.model;

import backend.interfaces.Figure;
import backend.model.Ellipse;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawableEllipse extends DrawFigure {

    private final Ellipse ellipse;

    public DrawableEllipse(Point centerPoint, double sMayorAxis, double sMinorAxis) {
        this.ellipse = new Ellipse(centerPoint, sMayorAxis, sMinorAxis);
    }

    @Override
    public void draw(GraphicsContext gc, Color fillColor, Color strokeColor) {
        // Cálculo de los valores de posición y dimensiones de la elipse
        double x = ellipse.getCenterPoint().getX() - ellipse.getsMayorAxis() / 2;
        double y = ellipse.getCenterPoint().getY() - ellipse.getsMinorAxis() / 2;
        double width = ellipse.getsMayorAxis();
        double height = ellipse.getsMinorAxis();

        // Configuración de colores de relleno y borde
        gc.setFill(fillColor);
        gc.setStroke(strokeColor);

        // Dibuja la elipse (relleno y borde)
        gc.fillOval(x, y, width, height);
        gc.strokeOval(x, y, width, height);
    }

    @Override
    public Figure getFigure() {
        return ellipse;
    }
}
