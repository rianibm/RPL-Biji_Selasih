package id.ac.ukdw.fti.rpl.kelompokbijiselasih;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainController {

    @FXML
    private TextField textFieldCari;

    @FXML
    private Button btnCari;

    @FXML
    private Text resultText;

    @FXML
    private TableView<Item> tvResult;

    @FXML
    private TableColumn<Item, String> tColVerseNum;
    
    @FXML
    private TableColumn<Item, String> tColVerses;

    @FXML
    private TableColumn<Item, String> tColEvents;
    
    private ObservableList<Item> dataResult = FXCollections.observableArrayList();
    
    @FXML
    private Button btnVisual;
    
    private boolean haveSearch = false;
    private String searchBy = "";
    private boolean indikator = true; //true untuk searchBy person-false untuk searchByEvent
//    private String fromBook = "";
    
    @FXML
    public void cari(ActionEvent event) {
        DBHelper db = new DBHelper();
        dataResult.clear();
        if(textFieldCari.getText().equals("")){
            resultText.setText("Hasil pencarian untuk "+textFieldCari.getText()+" : ");
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Hey, ada error!");
            alert.setContentText("Ooops, kata pencarian tidak boleh kosong!");

            alert.showAndWait();
        }else{
            ArrayList<Item> listHasil = new ArrayList<Item>();
            listHasil = db.searchWord(textFieldCari.getText());
            if(listHasil.size()==0){
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Hmm!");
                alert.setContentText("Hasil pencarian tidak ditemukan...!");

                alert.showAndWait();
            }else{
                for(int i=0;i<listHasil.size()-1;i++){
                    System.out.println(listHasil.get(i).toString());
                    dataResult.add(listHasil.get(i));
                }
                resultText.setText("Hasil pencarian untuk "+textFieldCari.getText().substring(0,1).toUpperCase()+textFieldCari.getText().substring(1).toLowerCase()+" : ");
                searchBy = listHasil.get(listHasil.size()-1).getEvent();
                if(listHasil.get(listHasil.size()-1).getVerse()=="event"){
                    indikator = false;
                    searchBy = listHasil.get(listHasil.size()-1).getNum();
                }
            }

        }
        tColVerseNum.setCellValueFactory(new PropertyValueFactory<Item, String>("num"));
        tColVerses.setCellValueFactory(new PropertyValueFactory<Item, String>("verse"));
        tColEvents.setCellValueFactory(new PropertyValueFactory<Item,String>("event"));
        tvResult.setItems(dataResult);
        haveSearch = true;
        btnVisual.setVisible(haveSearch);

    }   
    
    @FXML
    public void visualisasi(ActionEvent event) {
        System.out.println("search by : "+searchBy);
        System.out.println("indikator : "+indikator);
        Stage stage = new Stage();
        try {
            // Step 2
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("utama.fxml"));
            Parent root = loader.load();

            // FXMLLoader loader = FXMLLoader.load(getClass().getClassLoader().getResource("id/ac/ukdw/fti/rpl/detail.fxml"));
            // Step 3
            UtamaController controller = loader.getController();
            controller.recieve(searchBy, dataResult, indikator);
            // Step 4
            loader.setController(controller);
            // Step 5
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println(String.format("Error: %s", e.getMessage()));
        }
    }
    
    public void initialize(URL url, ResourceBundle rb) {
        tColVerseNum.setCellValueFactory(new PropertyValueFactory<Item, String>("num"));
        tColVerses.setCellValueFactory(new PropertyValueFactory<Item, String>("verse"));
        tColEvents.setCellValueFactory(new PropertyValueFactory<Item,String>("event"));
        
        btnVisual.setVisible(haveSearch);
        
    } 
    
}