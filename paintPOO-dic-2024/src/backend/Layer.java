package backend;


import backend.interfaces.Figure;
import java.util.ArrayList;
import java.util.List;

public class Layer implements Comparable<Layer> {

    private final int layerNumber;
    private boolean visible;

    public Layer(int layerNumber) {
        this.layerNumber = layerNumber;
        unHide();
    }

    public void hide(){
        visible = false;
    }

    public void unHide(){
        visible = true;
    }

    public boolean getVisibility(){
        return visible;
    }

    public boolean canDelete(){
        return layerNumber > 3;
    }

    @Override
    public String toString() {
        return "Capa %d".formatted(layerNumber);
    }

    @Override
    public int compareTo(Layer o) {
        return Integer.compare(this.layerNumber, o.layerNumber);
    }

}
