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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

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
            }
            for(int i=0;i<listHasil.size();i++){
                System.out.println(listHasil.get(i).toString());
                dataResult.add(listHasil.get(i));
            }
            resultText.setText("Hasil pencarian untuk "+textFieldCari.getText().substring(0,1).toUpperCase()+textFieldCari.getText().substring(1).toLowerCase()+" : ");
        }
        tColVerseNum.setCellValueFactory(new PropertyValueFactory<Item, String>("num"));
        tColVerses.setCellValueFactory(new PropertyValueFactory<Item, String>("verse"));
        tColEvents.setCellValueFactory(new PropertyValueFactory<Item,String>("event"));
        tvResult.setItems(dataResult);
    }   
    
    @FXML
    public void hitung(ActionEvent event) {
        System.out.println("asda");
    }
    
    public void initialize(URL url, ResourceBundle rb) {
        tColVerseNum.setCellValueFactory(new PropertyValueFactory<Item, String>("num"));
        tColVerses.setCellValueFactory(new PropertyValueFactory<Item, String>("verse"));
        tColEvents.setCellValueFactory(new PropertyValueFactory<Item,String>("event"));
    } 
    
}