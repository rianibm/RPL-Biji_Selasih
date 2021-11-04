package id.ac.ukdw.fti.rpl.kelompokbijiselasih;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene; //scene itu konten di windownya
import javafx.stage.Stage; //stage sbg window baru

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

 private static Scene scene; //scene belum ada nilai, diset di bawah

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("main")); //di dalam start dibuat scene baru dengan parameter FXMLoader
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml)); //set root agar bisa diakses pop up window
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml")); //diambil nama filenya apa
        return fxmlLoader.load(); //diload file nya
    }

    public static void main(String[] args) {
        launch(); //launch fungsi bawaan dari application
    }

}