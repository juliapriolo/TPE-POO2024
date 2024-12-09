package frontend;

import javafx.scene.control.*;

import java.util.Optional;

public class AppMenuBar extends MenuBar {
    private static final String FILE = "Archivo";
    private static final String EXIT = "Salir";
    private static final String EXIT_HEADER = "Salir de la aplicación";
    private static final String EXIT_CONTENT = "¿Está seguro que desea salir de la aplicación?";
    private static final String ABOUT = "Acerca de";
    private static final String ABOUT_HEADER = "Paint";
    private static final String ABOUT_CONTENT = "TPE Final POO Diciembre 2024";
    private static final String HELP = "Ayuda";
    private static final int STATUS = 0;

    public AppMenuBar() {
        Menu file = new Menu(FILE);
        MenuItem exitMenuItem = new MenuItem(EXIT);
        exitMenuItem.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(EXIT);
            alert.setHeaderText(EXIT_HEADER);
            alert.setContentText(EXIT_CONTENT);
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent()) {
                if (result.get() == ButtonType.OK) {
                    System.exit(STATUS);
                }
            }
        });
        file.getItems().add(exitMenuItem);
        Menu help = new Menu(HELP);
        MenuItem aboutMenuItem = new MenuItem(ABOUT_CONTENT);
        aboutMenuItem.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(ABOUT);
            alert.setHeaderText(ABOUT_HEADER);
            alert.setContentText(ABOUT_CONTENT);
            alert.showAndWait();
        });
        help.getItems().add(aboutMenuItem);
        getMenus().addAll(file, help);
    }

}
