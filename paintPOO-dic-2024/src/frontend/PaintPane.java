package frontend;

import backend.Layer;
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

import java.util.*;

public class PaintPane extends BorderPane {

	private CanvasState canvasState;

	// Canvas y relacionados
	private final Canvas canvas = new Canvas(800, 600);
	private GraphicsContext gc = canvas.getGraphicsContext2D();
	private final Color lineColor = Color.BLACK;
	private final Color defaultFillColor = Color.YELLOW;
	private final ShadowType defaultShadowType = ShadowType.NONE;
	private final boolean defaultArcType = false;
	private boolean initializedCopyFormatButton = false;
	private final boolean defaultRotate = false;
	private final boolean defaultFlipH = false;
	private final boolean defaultFlipV = false;
	private final double offset = 10;

	// Botones Barra Izquierda
	private final ToggleButton selectionButton = new ToggleButton("Seleccionar");
	private final FigureButton rectangleButton = new RectangleButton("Rectángulo");
	private final FigureButton circleButton = new CircleButton("Círculo");
	private final FigureButton squareButton = new SquareButton("Cuadrado");
	private final FigureButton ellipseButton = new EllipseButton("Elipse");
	private final ToggleButton deleteButton = new ToggleButton("Borrar");
	private final ToggleButton copyFormatButton = new ToggleButton("Copiar Fmt.");

	//Botones Barra Derecha
	private final ToggleButton turnRightButton = new ToggleButton("Girar D");
	private final ToggleButton flipHorizontally = new ToggleButton("Voltear H");
	private final ToggleButton flipVertically = new ToggleButton("Voltear V");
	private final ToggleButton duplicate = new ToggleButton("Duplicar");
	private final ToggleButton divide = new ToggleButton("Dividir");

	//Botones Barra Horizontal Superior
	private final ToggleButton bringToFrontButton = new ToggleButton("Traer al Frente");
	private final ToggleButton sendToBackButton = new ToggleButton("Enviar al Fondo");
	private final ToggleButton addLayer = new ToggleButton("Agregar capa");
	private final ToggleButton deleteLayer = new ToggleButton("Eliminar capa");
	private final ToggleButton showLayer = new RadioButton("Mostrar");
	private final ToggleButton hideLayer = new RadioButton("Ocultar");

	// Selector de color de relleno
	private final ColorPicker fillColorPicker = new ColorPicker(defaultFillColor);
	private final ColorPicker secondFillColorPicker = new ColorPicker(defaultFillColor);

	private Point startPoint;
	private Figure selectedFigure;
	private Layer currentLayer;

	//Sombra
	private final ChoiceBox<ShadowType> shadowsChoiceBox = new ChoiceBox<>();

	//Layers
	private final ChoiceBox<Layer> layersChoiceBox = new ChoiceBox<>();
	Label LayersText = new Label("Capas");

	//Biselado
	private final CheckBox biselado = new CheckBox("Biselado");

	// StatusBar
	private StatusPane statusPane;

	//Informacion para cada figura
	private final Map<Figure, FigureInfo> figureInfoMap = new HashMap<>();

	//Botones por figura
	private final Map<Figure, FigureButton> figureToButtonMap = new HashMap<>();

	//Drawable Map
	private final Map<Figure, DrawFigure> drawFigures = new HashMap<>();

	//Layer Map
	private final SortedMap<Layer, List<Figure>> layersMap = new TreeMap<>();

	private final FigureButton[] figureButtons = {circleButton, ellipseButton, rectangleButton, squareButton};


	public PaintPane(CanvasState canvasState, StatusPane statusPane) {
		this.canvasState = canvasState;
		this.statusPane = statusPane;

		ToggleButton[] toolsArr = {selectionButton, rectangleButton, circleButton, squareButton, ellipseButton, deleteButton};
		ToggleGroup tools = new ToggleGroup();
		for (ToggleButton tool : toolsArr) {
			tool.setMinWidth(90);
			tool.setToggleGroup(tools);
			tool.setCursor(Cursor.HAND);
		}

		ToggleButton[] toolsRight = {turnRightButton, flipHorizontally, flipVertically, duplicate, divide};
		ToggleGroup rightTools = new ToggleGroup();
		for(ToggleButton tool : toolsRight){
			tool.setMinWidth(90);
			tool.setToggleGroup(rightTools);
			tool.setCursor(Cursor.HAND);
		}

		ToggleGroup layerVisibilityGroup = new ToggleGroup();
		showLayer.setToggleGroup(layerVisibilityGroup);
		hideLayer.setToggleGroup(layerVisibilityGroup);

		shadowsChoiceBox.getItems().addAll(ShadowType.values());
		Label shadowText = new Label("Formato");
		shadowsChoiceBox.setValue(ShadowType.NONE);

		Label actionsText = new Label("Acciones");

		//Agrega los botones de la columna izquierda
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

		//Agrega los botones de la columna derecha
		VBox rightButtons = new VBox(10);
		setRight(rightButtons);
		rightButtons.getChildren().add(actionsText);
		rightButtons.getChildren().addAll(toolsRight);
		rightButtons.setPadding(new Insets(5));
		rightButtons.setStyle("-fx-background-color: #999");
		rightButtons.setPrefWidth(100);
		gc.setLineWidth(1);


		//Agrega los botones de las capas a una fila arriba del canvas
		for(int i = canvasState.getCurrentLayer(); i <= 3; i++){
			layersMap.putIfAbsent(new Layer(i), new ArrayList<>());
		}

		layersChoiceBox.setValue(layersMap.firstKey());
		currentLayer = layersMap.firstKey();
		showLayer.fire();

		HBox layersButtons = new HBox(10);
		layersButtons.getChildren().add(bringToFrontButton);
		layersButtons.getChildren().add(sendToBackButton);
		layersButtons.getChildren().add(LayersText);
		layersButtons.getChildren().add(layersChoiceBox);
		layersChoiceBox.getItems().addAll(layersMap.keySet());
		layersButtons.getChildren().add(showLayer);
		layersButtons.getChildren().add(hideLayer);
		layersButtons.getChildren().add(addLayer);
		layersButtons.getChildren().add(deleteLayer);
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
					figureInfoMap.put(newFigure, new FigureInfo(fillColorPicker.getValue(), secondFillColorPicker.getValue(), defaultShadowType, defaultArcType, defaultRotate, defaultFlipH, defaultFlipV));
					canvasState.addFigure(newFigure);
					drawFigures.putIfAbsent(newFigure, newButton.createDrawFigure(figureInfoMap.get(newFigure), newFigure));
					layersMap.get(currentLayer).add(newFigure);
					biselado.setSelected(false);
					startPoint = null;

					redrawCanvas();
				}
			}
		});


		canvas.setOnMouseMoved(event -> {
			Point eventPoint = new Point(event.getX(), event.getY());
			boolean found = false;
			StringBuilder label = new StringBuilder();
			List<Map.Entry<Layer, List<Figure>>> layersList = new ArrayList<>(layersMap.entrySet());
			Collections.reverse(layersList);

			for (Map.Entry<Layer, List<Figure>> entry : layersList) {
				Layer layer = entry.getKey();
				if (layer.getVisibility()) {
					List<Figure> figures = entry.getValue();
					for (int i = figures.size() - 1; i >= 0; i--) {
						Figure figure = figures.get(i);
						if (figureBelongs(figure, eventPoint)) {
							found = true;
							label = new StringBuilder(figure.toString());
							break;
						}
					}
				}
				if (found) {
					break;
				}
			}
			if (found) {
				statusPane.updateStatus(label.toString());
			} else {
				statusPane.updateStatus(eventPoint.toString());
			}
		});

		canvas.setOnMouseClicked(event -> {
			Point eventPoint = new Point(event.getX(), event.getY());
			boolean found = false;
			StringBuilder label = new StringBuilder("Se seleccionó: ");

			if (selectionButton.isSelected()) {
				for (Map.Entry<Layer, List<Figure>> entry : layersMap.entrySet()) {
					Layer layer = entry.getKey();
					if (layer.getVisibility()) {
						List<Figure> figures = entry.getValue();
						for (int i = figures.size() - 1; i >= 0; i--) {
							Figure figure = figures.get(i);
							if (figureBelongs(figure, eventPoint)) {
								if (initializedCopyFormatButton && selectedFigure != null) {
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
								fillColorPicker.setValue(figureInfoMap.get(selectedFigure).getColor());
								secondFillColorPicker.setValue(figureInfoMap.get(selectedFigure).getSecondaryColor());
								shadowsChoiceBox.setValue(figureInfoMap.get(selectedFigure).getShadowType());
								biselado.setSelected(figureInfoMap.get(selectedFigure).getArcType());
								label.append(figure.toString());
								break;
							}
						}
					}
					if (found) {
						break;
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
				double diffX = eventPoint.getX() - startPoint.getX();
				double diffY = eventPoint.getY() - startPoint.getY();

				DrawFigure df = drawFigures.get(selectedFigure);

				if (df != null) {
					df.moveAndSync(diffX, diffY);
				}

				startPoint = eventPoint;
				redrawCanvas();
			}
		});


		deleteButton.setOnAction(event -> {
			if (selectedFigure != null) {
				layersMap.get(currentLayer).remove(selectedFigure);
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
				FigureInfo figureInfo = figureInfoMap.get(selectedFigure);
				figureInfo.setColor(fillColorPicker.getValue());
				redrawCanvas();
			}
		});

		secondFillColorPicker.setOnAction(event -> {
			if (selectedFigure != null && selectionButton.isSelected()) {
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

		layersChoiceBox.setOnAction(event -> {
			currentLayer = layersChoiceBox.getValue();
			selectedFigure = null;
			if(currentLayer.getVisibility()){
				showLayer.fire();
			} else{
				hideLayer.fire();
			}
			redrawCanvas();
		});

		showLayer.setOnAction(event -> {
			layersChoiceBox.getValue().unHide();
			hideLayer.setSelected(false);
			redrawCanvas();
		});

		hideLayer.setOnAction(event ->{
			showLayer.setSelected(false);
			layersChoiceBox.getValue().hide();
			redrawCanvas();
		});

		deleteLayer.setOnAction(event -> {
			if(currentLayer.canDelete()) {
				layersMap.remove(currentLayer);
				layersChoiceBox.getItems().remove(currentLayer);
				layersChoiceBox.setValue(layersMap.firstKey());
			}
		});

		addLayer.setOnAction(event -> {
			int newLayerNumber = canvasState.getLastLayerAndIncrement();
			Layer newLayer = new Layer(newLayerNumber);
			layersMap.put(newLayer, new ArrayList<>());
			layersChoiceBox.getItems().add(newLayer);
			currentLayer = newLayer;
			layersChoiceBox.setValue(currentLayer);
			showLayer.setSelected(true);
			redrawCanvas();
		});

		biselado.setOnAction(event -> {
			if(selectedFigure != null && selectionButton.isSelected()){
				FigureInfo figureInfo = figureInfoMap.get(selectedFigure);
				if (biselado.isSelected()) {
					if (!figureInfo.getArcType()) {
						figureInfo.setArcType();
						redrawCanvas();
					}
				} else {
					if (figureInfo.getArcType()) {
						figureInfo.setArcType();
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

				Point newStartPoint = new Point(figure.getStartPoint().getX() + offset, figure.getStartPoint().getY() + offset);
				Point newEndPoint = new Point(figure.getEndPoint().getX() + offset, figure.getEndPoint().getY() + offset);

				Figure duplicateFigure = figureToButtonMap.get(figure).create(newStartPoint, newEndPoint);


				FigureInfo duplicatedInfo = new FigureInfo(originalInfo.getColor(), originalInfo.getSecondaryColor(),
						originalInfo.getShadowType(), originalInfo.getArcType(), originalInfo.getRotate(),
						originalInfo.getFlipH(), originalInfo.getFlipV());

				DrawFigure duplicateDrawFig = figureToButtonMap.get(selectedFigure).createDrawFigure(duplicatedInfo,duplicateFigure);
				figureInfoMap.put(duplicateFigure, duplicatedInfo);
				drawFigures.put(duplicateFigure, duplicateDrawFig);
				figureToButtonMap.putIfAbsent(duplicateFigure, figureToButtonMap.get(figure));
				selectedFigure = null;
				canvasState.addFigure(duplicateFigure);
				layersMap.get(currentLayer).add(duplicateFigure);
				redrawCanvas();

			}}));

		divide.setOnAction(event -> {
			if(selectedFigure != null && selectionButton.isSelected()){
				FigureInfo info = figureInfoMap.get(selectedFigure);

				double newHeight = selectedFigure.getHeight()/2;
				double newWidth = selectedFigure.getWidth()/2;

				double xStartPoint = selectedFigure.getStartPoint().getX();
				double yStartPoint = selectedFigure.getStartPoint().getY();

				double xEndPoint = selectedFigure.getEndPoint().getX();
				double yEndPoint = selectedFigure.getEndPoint().getY();

				Point centrePoint = selectedFigure.getCenterPoint();

				Figure leftDividedFigure = figureToButtonMap.get(selectedFigure).createDividedFigure(
						new Point(xStartPoint,
								yStartPoint + newHeight / 2),
						new Point (centrePoint.getX(),
								yEndPoint - newHeight/ 2),
						new Point(centrePoint.getX() - newWidth/2, centrePoint.getY()),
						newHeight, newWidth);

				FigureInfo dividedInfoLeftFigure = new FigureInfo(info.getColor(), info.getSecondaryColor(),
						info.getShadowType(), info.getArcType(), info.getRotate(),
						info.getFlipH(), info.getFlipV());

				Figure rightDividedFigure = figureToButtonMap.get(selectedFigure).createDividedFigure(
						new Point(centrePoint.getX(),
								yStartPoint + newHeight / 2),
						new Point(xEndPoint,
								yEndPoint - newHeight / 2),
						new Point(centrePoint.getX() + newWidth/2, centrePoint.getY()),
						newHeight, newWidth);

				FigureInfo dividedInfoRightFigure = new FigureInfo(info.getColor(), info.getSecondaryColor(),
						info.getShadowType(), info.getArcType(), info.getRotate(),
						info.getFlipH(), info.getFlipV());


				figureInfoMap.put(leftDividedFigure, dividedInfoLeftFigure);
				figureInfoMap.put(rightDividedFigure, dividedInfoRightFigure);
				drawFigures.put(leftDividedFigure, figureToButtonMap.get(selectedFigure).createDrawFigure(dividedInfoLeftFigure,leftDividedFigure));
				drawFigures.put(rightDividedFigure, figureToButtonMap.get(selectedFigure).createDrawFigure(dividedInfoRightFigure,rightDividedFigure));
				figureToButtonMap.put(leftDividedFigure, figureToButtonMap.get(selectedFigure));
				figureToButtonMap.put(rightDividedFigure, figureToButtonMap.get(selectedFigure));

				canvasState.deleteFigure(selectedFigure);
				layersMap.get(currentLayer).remove(selectedFigure);
				figureInfoMap.remove(selectedFigure);
				drawFigures.remove(selectedFigure);

				selectedFigure = null;

				canvasState.addFigure(rightDividedFigure);
				canvasState.addFigure(leftDividedFigure);
				layersMap.get(currentLayer).add(rightDividedFigure);
				layersMap.get(currentLayer).add(leftDividedFigure);

				redrawCanvas();
			}
		});

		bringToFrontButton.setOnAction(event -> {
			if(selectedFigure != null && selectionButton.isSelected()){
				layersMap.get(currentLayer).remove(selectedFigure);
				layersMap.get(currentLayer).add( selectedFigure);
				redrawCanvas();
			}
		});

		sendToBackButton.setOnAction(event -> {
			if(selectedFigure != null && selectionButton.isSelected()){
				layersMap.get(currentLayer).remove(selectedFigure);
				layersMap.get(currentLayer).add(0, selectedFigure);
				redrawCanvas();
			}
		});
	}

	private void redrawCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

		for (Map.Entry<Layer, List<Figure>> entry : layersMap.entrySet()) {
			Layer layer = entry.getKey();
			if (layer.getVisibility()){
				for (Figure figure : entry.getValue()) {
					Color strokeColor = (figure == selectedFigure) ? Color.RED : lineColor;
					DrawFigure df = drawFigures.get(figure);
					if (df != null) {
						df.draw(gc, figureInfoMap.get(figure), strokeColor, figure);
					}
				}
			}
		}
	}


	boolean figureBelongs(Figure figure, Point eventPoint) {
		return figure.contains(eventPoint);
	}

}