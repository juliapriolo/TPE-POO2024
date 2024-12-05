package frontend;

import backend.interfaces.Figure;
import backend.CanvasState;
import backend.model.*;
import frontend.buttons.*;
import frontend.model.DrawFigure;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;

public class PaintPane extends BorderPane {

	// BackEnd
	CanvasState canvasState;

	// Canvas y relacionados
	Canvas canvas = new Canvas(800, 600); // el canva donde se va a dibujar
	GraphicsContext gc = canvas.getGraphicsContext2D(); // permite dibujar las figuras, lineas, texto, imagen
	Color lineColor = Color.BLACK; // por deafult, la lineas tiene que ser negras
	Color defaultFillColor = Color.YELLOW; // por default el color de las formas es amarillo

	// Botones Barra Izquierda
	ToggleButton selectionButton = new ToggleButton("Seleccionar");
	FigureButton rectangleButton = new RectangleButton("Rectángulo");
	FigureButton circleButton = new CircleButton("Círculo");
	FigureButton squareButton = new SquareButton("Cuadrado");
	FigureButton ellipseButton = new EllipseButton("Elipse");
	ToggleButton deleteButton = new ToggleButton("Borrar");

	// Selector de color de relleno
	ColorPicker fillColorPicker = new ColorPicker(defaultFillColor); // inicializa el color default (amarillo) de relleno, ColorPicker es el boton para seleccionar colores

	// Dibujar una figura
	Point startPoint; // de donde arrancar a dibujar

	// Seleccionar una figura
	Figure selectedFigure; // la figura seleccionada

	// StatusBar
	StatusPane statusPane; //barra azul abajo del canvas, indica las coordenadas del cursor en el canvas

	// Colores de relleno de cada figura
	Map<Figure, Color> figureColorMap = new HashMap<>(); //cada figura con su color de relleno

	FigureButton[] figureButtons = {circleButton, ellipseButton, rectangleButton, squareButton};

	Map<Figure, DrawFigure> drawFigures = new HashMap<>();

	public PaintPane(CanvasState canvasState, StatusPane statusPane) {
		this.canvasState = canvasState;
		this.statusPane = statusPane;
		// configura el tamanio de cada boton en toolsArr y los lace que sean seleccion unica (ademas de la manito)
		ToggleButton[] toolsArr = {selectionButton, rectangleButton, circleButton, squareButton, ellipseButton, deleteButton};
		ToggleGroup tools = new ToggleGroup();
		for (ToggleButton tool : toolsArr) {
			tool.setMinWidth(90);
			tool.setToggleGroup(tools);
			tool.setCursor(Cursor.HAND);
		}

		// agrega todos los botones juntos en una misma columna (la columna en este caso es la de la izq, gris)
		VBox buttonsBox = new VBox(10);
		buttonsBox.getChildren().addAll(toolsArr);
		buttonsBox.getChildren().add(fillColorPicker);
		buttonsBox.setPadding(new Insets(5));
		buttonsBox.setStyle("-fx-background-color: #999");
		buttonsBox.setPrefWidth(100);
		gc.setLineWidth(1);

		canvas.setOnMousePressed(event -> {
			startPoint = new Point(event.getX(), event.getY());
		});

		canvas.setOnMouseReleased(event -> {
			Point endPoint = new Point(event.getX(), event.getY());
			// si no hay tal startPoint o quiero crear la forma arrastrando el mouse de abajo hacia arriba no funca
			if(startPoint == null || endPoint.getX() < startPoint.getX() || endPoint.getY() < startPoint.getY()) {
				return ;
			}

			Figure newFigure = null;
			FigureButton newButton = null;

			for(FigureButton button : figureButtons) {
				if(button.isSelected() ) {
					newFigure = button.createDrawFigure(startPoint, endPoint).getFigure();
					newButton = button;
					figureColorMap.put(newFigure, fillColorPicker.getValue());
					canvasState.addFigure(newFigure);
					drawFigures.putIfAbsent(newFigure, newButton.createDrawFigure(startPoint, endPoint));
					startPoint = null;
					redrawCanvas();
				}
			}
		});

		canvas.setOnMouseMoved(event -> {
			Point eventPoint = new Point(event.getX(), event.getY());
			boolean found = false;
			StringBuilder label = new StringBuilder();
			for(Figure figure : canvasState.figures()) {
				if(figureBelongs(figure, eventPoint)) {
					found = true;
					label.append(figure.toString());
				}
			}
			if(found) {
				statusPane.updateStatus(label.toString());
			} else {
				statusPane.updateStatus(eventPoint.toString());
			}
		});

		canvas.setOnMouseClicked(event -> {
			if(selectionButton.isSelected()) {
				Point eventPoint = new Point(event.getX(), event.getY());
				boolean found = false;
				StringBuilder label = new StringBuilder("Se seleccionó: ");
				for (Figure figure : canvasState.figures()) {
					if(figureBelongs(figure, eventPoint)) {
						found = true;
						selectedFigure = figure;
						label.append(figure.toString());
					}
				}
				if (found) {
					statusPane.updateStatus(label.toString());
				} else {
					selectedFigure = null;
					statusPane.updateStatus("Ninguna figura encontrada");
				}
				redrawCanvas();
			}
		});

		canvas.setOnMouseDragged(event -> {
			if (selectionButton.isSelected() && selectedFigure != null) {
				Point eventPoint = new Point(event.getX(), event.getY());
				double diffX = (eventPoint.getX() - startPoint.getX()) / 100;
				double diffY = (eventPoint.getY() - startPoint.getY()) / 100;

				selectedFigure.move(diffX, diffY);
				redrawCanvas();
			}
		});

		deleteButton.setOnAction(event -> {
			if (selectedFigure != null) {
				canvasState.deleteFigure(selectedFigure);
				selectedFigure = null;
				redrawCanvas();
			}
		});
		setLeft(buttonsBox);
		setRight(canvas);
	}


	void redrawCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		for(Figure figure : canvasState.figures()) {
			Color strokeColor = (figure == selectedFigure) ? Color.RED : lineColor;
			gc.setFill(figureColorMap.get(figure));
			drawFigures.get(figure).draw(gc, figureColorMap.get(figure), strokeColor);
		}
	}

	boolean figureBelongs(Figure figure, Point eventPoint) {
		return figure.contains(eventPoint);
	}

}
