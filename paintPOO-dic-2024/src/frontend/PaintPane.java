package frontend;

import backend.interfaces.Figure;
import backend.CanvasState;
import backend.model.*;
import frontend.buttons.*;
import frontend.model.DrawFigure;
import frontend.model.FigureInfo;
import frontend.model.ShadowType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;

public class PaintPane extends BorderPane {

	// BackEnd
	private CanvasState canvasState;

	// Canvas y relacionados
	private Canvas canvas = new Canvas(800, 600); // el canva donde se va a dibujar
	private GraphicsContext gc = canvas.getGraphicsContext2D(); // permite dibujar las figuras, lineas, texto, imagen
	private Color lineColor = Color.BLACK; // por deafult, la lineas tiene que ser negras
	private Color defaultFillColor = Color.YELLOW; // por default el color de las formas es amarillo
	private ShadowType defaultShadowType = ShadowType.NONE;
	private boolean defaultArcType = false;
	private boolean initializedCopyFormatButton = false;
	private boolean defaultRotate = false;
	private boolean defaultFlipH = false;
	private boolean defaultFlipV = false;
	private final double offset = 10;

	// Botones Barra Izquierda
	private ToggleButton selectionButton = new ToggleButton("Seleccionar");
	private FigureButton rectangleButton = new RectangleButton("Rectángulo");
	private FigureButton circleButton = new CircleButton("Círculo");
	private FigureButton squareButton = new SquareButton("Cuadrado");
	private FigureButton ellipseButton = new EllipseButton("Elipse");
	private ToggleButton deleteButton = new ToggleButton("Borrar");
	private ToggleButton copyFormatButton = new ToggleButton("Copiar Fmt.");

	//Botones Barra Derecha
	private ToggleButton turnRightButton = new ToggleButton("Girar D");
	private ToggleButton flipHorizontally = new ToggleButton("Voltear H");
	private ToggleButton flipVertically = new ToggleButton("Voltear V");
	private ToggleButton duplicate = new ToggleButton("Duplicar");

	//Botones Barra Horizontal Superior
	private ToggleButton bringToFrontButton = new ToggleButton("Traer al Frente");
	private ToggleButton sendToBackButton = new ToggleButton("Enviar al Fondo");

	// Selector de color de relleno
	private ColorPicker fillColorPicker = new ColorPicker(defaultFillColor); // inicializa el color default (amarillo) de relleno, ColorPicker es el boton para seleccionar colores
	private ColorPicker secondFillColorPicker = new ColorPicker(defaultFillColor); // inicializa el segundo color que se va a usar para hacer el difuminado

	// Dibujar una figura
	private Point startPoint; // de donde arrancar a dibujar

	// Seleccionar una figura
	private Figure selectedFigure; // la figura seleccionada

	//Sombra
	private final ChoiceBox<ShadowType> shadowsChoiceBox = new ChoiceBox<>();

	//Biselado
	private final CheckBox biselado = new CheckBox("Biselado");

	// StatusBar
	private StatusPane statusPane; //barra azul abajo del canvas, indica las coordenadas del cursor en el canvas

	// Colores de relleno de cada figura
	private Map<Figure, Color> figureColorMap = new HashMap<>(); //cada figura con su color de relleno

	//Informacion para cada figura
	private Map<Figure, FigureInfo> figureInfoMap = new HashMap<>();

	//Botones por figura
	private Map<Figure, FigureButton> figureToButtonMap = new HashMap<>();

	private FigureButton[] figureButtons = {circleButton, ellipseButton, rectangleButton, squareButton};

	private Map<Figure, DrawFigure> drawFigures = new HashMap<>();

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

		ToggleButton[] toolsRight = {turnRightButton, flipHorizontally, flipVertically, duplicate};
		ToggleGroup rightTools = new ToggleGroup();
		for(ToggleButton tool : toolsRight){
			tool.setMinWidth(90);
			tool.setToggleGroup(rightTools);
			tool.setCursor(Cursor.HAND);
		}

		shadowsChoiceBox.getItems().addAll(ShadowType.values());
		Label shadowText = new Label("Formato");
		shadowsChoiceBox.setValue(ShadowType.NONE);

		Label actionsText = new Label("Acciones");

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

		//agrega los otros botones juntos en una misma columna(del lado derecho, gris)
		VBox rightButtons = new VBox(10);
		setRight(rightButtons);
		rightButtons.getChildren().add(actionsText);
		rightButtons.getChildren().addAll(toolsRight);
		rightButtons.setPadding(new Insets(5));
		rightButtons.setStyle("-fx-background-color: #999");
		rightButtons.setPrefWidth(100);
		gc.setLineWidth(1);

		//agrega los botones de las capas a una fila arriba del canvas
		HBox layersButtons = new HBox(10);
		layersButtons.getChildren().add(bringToFrontButton);
		layersButtons.getChildren().add(sendToBackButton);
		layersButtons.setPadding(new Insets(5));
		layersButtons.setAlignment(Pos.CENTER);
		layersButtons.setStyle("-fx-background-color: #999");
		layersButtons.setPrefWidth(100);
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
					figureToButtonMap.putIfAbsent(newFigure, newButton);

					figureInfoMap.put(newFigure, new FigureInfo(fillColorPicker.getValue(), secondFillColorPicker.getValue(),
							startPoint, endPoint, defaultShadowType, defaultArcType,
							defaultRotate, defaultFlipH, defaultFlipV));
					canvasState.addFigure(newFigure);
					drawFigures.putIfAbsent(newFigure, newButton.createDrawFigure(figureInfoMap.get(newFigure),newFigure,gc));
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
				// Crear el nuevo punto basado en el evento del mouse
				Point eventPoint = new Point(event.getX(), event.getY());

				// Calcular las diferencias
				double diffX = eventPoint.getX() - startPoint.getX();
				double diffY = eventPoint.getY() - startPoint.getY();

				// Obtener la DrawFigure y FigureInfo asociadas a la figura seleccionada
				DrawFigure df = drawFigures.get(selectedFigure);
				FigureInfo info = figureInfoMap.get(selectedFigure);

				if (df != null && info != null) {
					// Mover la figura y sincronizar los datos
					df.moveAndSync(diffX, diffY, info);
				}

				// Actualizar el punto inicial para el próximo evento de arrastre
				startPoint = eventPoint;

				// Redibujar el canvas
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
		setRight(rightButtons);
		setCenter(canvas);
		setTop(layersButtons);

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

		turnRightButton.setOnAction((event -> {
			if (selectedFigure != null && selectionButton.isSelected()) {
				FigureInfo info = figureInfoMap.get(selectedFigure);
				info.setRotate();

				DrawFigure drawable = drawFigures.get(selectedFigure);
				if (drawable != null) {
					drawable.rotateRight(info);
					//canvasState.addFigure(drawable.getFigure());
				}
				redrawCanvas();
			}
		}));

		flipHorizontally.setOnAction(event -> {
			if(selectedFigure != null && selectionButton.isSelected()){
				FigureInfo info = figureInfoMap.get(selectedFigure);
				info.setFlipH();

				DrawFigure df = drawFigures.get(selectedFigure);
				if(df != null){
					df.flipHorizontally(info);
				}
				redrawCanvas();
			}
		});

		flipVertically.setOnAction(event -> {
			if(selectedFigure != null && selectionButton.isSelected()){
				FigureInfo info = figureInfoMap.get(selectedFigure);
				info.setFlipV();

				DrawFigure df = drawFigures.get(selectedFigure);
				if(df != null){
					df.flipVertically(info);
				}
				redrawCanvas();
			}
		});

		duplicate.setOnAction((event -> {
			if(selectedFigure != null && selectionButton.isSelected()){
				Figure figure = selectedFigure;
				FigureInfo originalInfo = figureInfoMap.get(figure);

				Point newStartPoint = new Point(originalInfo.getStartPoint().getX() + offset, originalInfo.getStartPoint().getY() + offset);
				Point newEndPoint = new Point(originalInfo.getEndPoint().getX() + offset, originalInfo.getEndPoint().getY() + offset);

				Figure duplicateFigure = figureToButtonMap.get(figure).create(newStartPoint, newEndPoint);

				//duplico la info y pongo las nuevas coordenadas
				FigureInfo duplicatedInfo = new FigureInfo(originalInfo.getColor(), originalInfo.getSecondaryColor(),
						newStartPoint, newEndPoint, originalInfo.getShadowType(), originalInfo.getArcType(),
						originalInfo.getRotate(), originalInfo.getFlipH(), originalInfo.getFlipV());

				//creo la draw figure
				DrawFigure duplicateDrawFig = figureToButtonMap.get(selectedFigure).createDrawFigure(duplicatedInfo,duplicateFigure,gc);
				figureInfoMap.put(duplicateFigure, duplicatedInfo);
				drawFigures.put(duplicateFigure, duplicateDrawFig);
				figureToButtonMap.putIfAbsent(duplicateFigure, figureToButtonMap.get(figure));
				selectedFigure = null;
				canvasState.addFigure(duplicateFigure);
				redrawCanvas();

			}}));
	}

	void redrawCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		for(Figure figure : canvasState.figures()) {
			Color strokeColor = (figure == selectedFigure) ? Color.RED : lineColor;
			drawFigures.get(figure).draw(gc, figureInfoMap.get(figure), strokeColor,figure);
		}
	}


	boolean figureBelongs(Figure figure, Point eventPoint) {
		return figure.contains(eventPoint);
	}

}