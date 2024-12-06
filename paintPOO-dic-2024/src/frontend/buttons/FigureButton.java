package frontend.buttons;

import backend.interfaces.Figure;
import backend.model.Point;
import frontend.model.DrawFigure;
import frontend.model.FigureInfo;
import javafx.scene.control.ToggleButton;


public abstract class FigureButton extends ToggleButton {
    public FigureButton(String name) {
        super(name);
    }

    public abstract Figure create(Point startPoint, Point endPoint);

    public abstract DrawFigure createDrawFigure(Point startPoint, Point endPoint, FigureInfo info);
}
