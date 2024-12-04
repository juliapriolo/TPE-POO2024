package frontend.buttons;

import javafx.scene.control.ToggleButton;


public abstract class FigureButton extends ToggleButton implements FigureCreator {
    public FigureButton(String name) {
        super(name);
    }
}
