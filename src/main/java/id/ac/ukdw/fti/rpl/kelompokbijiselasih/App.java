package id.ac.ukdw.fti.rpl.kelompokbijiselasih;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

 private static Scene scene;

    @Override
    //load file xml dan tampilannya + controller
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("main")); //load main
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    //diakses pertama kali public static void main yang memanggil method launch
    public static void main(String[] args) {
        launch();
    }

}
