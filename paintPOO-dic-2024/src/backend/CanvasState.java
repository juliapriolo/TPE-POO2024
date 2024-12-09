package backend;

import backend.interfaces.Figure;

import java.util.ArrayList;
import java.util.List;

public class CanvasState {
    private static final int INITIAL_LAYER = 1;
    private static int currentLayer = INITIAL_LAYER;
    private static int lastLayer = 3;

    private final List<Figure> list = new ArrayList<>();

    public void addFigure(Figure figure) {
        list.add(figure);
    }

    public void deleteFigure(Figure figure) {
        list.remove(figure);
    }

    public int getCurrentLayer(){
        return currentLayer;
    }

    public int getLastLayerAndIncrement(){
        return ++lastLayer;
    }

}
