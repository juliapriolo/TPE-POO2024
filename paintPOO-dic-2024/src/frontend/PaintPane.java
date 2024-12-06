package frontend;

import backend.interfaces.Figure;
import backend.CanvasState;
import backend.model.*;
import frontend.buttons.*;
import frontend.model.DrawFigure;
import frontend.model.FigureInfo;
import frontend.model.ShadowType;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
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
	ShadowType defaultShadowType = ShadowType.NONE;
	boolean defaultArcType = false;
	boolean initializedCopyFormatButton = false;

	// Botones Barra Izquierda
	ToggleButton selectionButton = new ToggleButton("Seleccionar");
	FigureButton rectangleButton = new RectangleButton("Rectángulo");
	FigureButton circleButton = new CircleButton("Círculo");
	FigureButton squareButton = new SquareButton("Cuadrado");
	FigureButton ellipseButton = new EllipseButton("Elipse");
	ToggleButton deleteButton = new ToggleButton("Borrar");
	ToggleButton copyFormatButton = new ToggleButton("Copiar Fmt.");

	// Selector de color de relleno
	ColorPicker fillColorPicker = new ColorPicker(defaultFillColor); // inicializa el color default (amarillo) de relleno, ColorPicker es el boton para seleccionar colores
	ColorPicker secondFillColorPicker = new ColorPicker(defaultFillColor); // inicializa el segundo color que se va a usar para hacer el difuminado

	// Dibujar una figura
	Point startPoint; // de donde arrancar a dibujar

	// Seleccionar una figura
	Figure selectedFigure; // la figura seleccionada

	//Sombra
	private final ChoiceBox<ShadowType> shadowsChoiceBox = new ChoiceBox<>();

	//Biselado
	private final CheckBox biselado = new CheckBox("Biselado");

	// StatusBar
	StatusPane statusPane; //barra azul abajo del canvas, indica las coordenadas del cursor en el canvas

	// Colores de relleno de cada figura
	Map<Figure, Color> figureColorMap = new HashMap<>(); //cada figura con su color de relleno

	//Informacion para cada figura
	Map<Figure, FigureInfo> figureInfoMap = new HashMap<>();

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

		shadowsChoiceBox.getItems().addAll(ShadowType.values());
		Label shadowText = new Label("Formato");
		shadowsChoiceBox.setValue(ShadowType.NONE);

		// agrega todos los botones juntos en una misma columna (la columna en este caso es la de la izq, gris)
		VBox buttonsBox = new VBox(10);
		buttonsBox.getChildren().addAll(toolsArr);
		buttonsBox.getChildren().add(fillColorPicker);
		buttonsBox.getChildren().add(secondFillColorPicker);
		buttonsBox.getChildren().add(shadowText);
		buttonsBox.getChildren().add(shadowsChoiceBox);
		buttonsBox.getChildren().add(biselado);
		buttonsBox.getChildren().add(copyFormatButton);
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
					newFigure = button.create(startPoint, endPoint);
					newButton = button;

					figureInfoMap.put(newFigure, new FigureInfo(fillColorPicker.getValue(), secondFillColorPicker.getValue(), startPoint, endPoint, defaultShadowType, defaultArcType));
					figureColorMap.put(newFigure, fillColorPicker.getValue());
					canvasState.addFigure(newFigure);
					drawFigures.putIfAbsent(newFigure, newButton.createDrawFigure(startPoint, endPoint, figureInfoMap.get(newFigure)));
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
			Point eventPoint = new Point(event.getX(), event.getY());
			boolean found = false;
			if(selectionButton.isSelected()) {
				StringBuilder label = new StringBuilder("Se seleccionó: ");
				for (Figure figure : canvasState.figures()) {
					if(figureBelongs(figure, eventPoint)) {
						if(initializedCopyFormatButton && selectedFigure != null) {

							FigureInfo originFigureInfo = figureInfoMap.get(selectedFigure);
							FigureInfo destinyFigureInfo = figureInfoMap.get(figure);

							destinyFigureInfo.setColor(originFigureInfo.getColor());
							destinyFigureInfo.setSecondaryColor(originFigureInfo.getSecondaryColor());
							destinyFigureInfo.setShadowType(originFigureInfo.getShadowType());
							destinyFigureInfo.transferArcType(originFigureInfo.getArcType());
							initializedCopyFormatButton = false;

						}
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

		fillColorPicker.setOnAction(event -> {
			if (selectedFigure != null && selectionButton.isSelected()) {
				// Actualiza el primer color en figureInfoMap
				FigureInfo figureInfo = figureInfoMap.get(selectedFigure);
				figureInfo.setColor(fillColorPicker.getValue());
				redrawCanvas();
			}
		});

		secondFillColorPicker.setOnAction(event -> {
			if (selectedFigure != null && selectionButton.isSelected()) {
				// Actualiza el segundo color en figureInfoMap
				FigureInfo figureInfo = figureInfoMap.get(selectedFigure);
				figureInfo.setSecondaryColor(secondFillColorPicker.getValue());
				redrawCanvas();
			}
		});

		shadowsChoiceBox.setOnAction(event -> {
			if(selectedFigure != null && selectionButton.isSelected()){
				FigureInfo figureInfo = figureInfoMap.get(selectedFigure);
				figureInfo.setShadowType(shadowsChoiceBox.getValue());
				redrawCanvas();
			}
		});

		biselado.setOnAction(event -> {
			if(selectedFigure != null && selectionButton.isSelected()){
				FigureInfo figureInfo = figureInfoMap.get(selectedFigure);
				if (biselado.isSelected()) {
					if (!figureInfo.getArcType()) {
						figureInfo.setArcType(); // Activar el biselado
						redrawCanvas();
					}
				} else {
					// Opcional: lógica para desactivar el efecto
					if (figureInfo.getArcType()) {
						figureInfo.setArcType(); // Desactivar el biselado
						redrawCanvas();
					}
				}
			}
		});

		copyFormatButton.setOnAction(event -> {
			if(selectedFigure != null && selectionButton.isSelected()){
				initializedCopyFormatButton = true;
			}
			redrawCanvas();
		});
	}

	void redrawCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		for(Figure figure : canvasState.figures()) {
			Color strokeColor = (figure == selectedFigure) ? Color.RED : lineColor;
			drawFigures.get(figure).draw(gc, figureInfoMap.get(figure), strokeColor);
		}
	}

	boolean figureBelongs(Figure figure, Point eventPoint) {
		return figure.contains(eventPoint);
	}

}