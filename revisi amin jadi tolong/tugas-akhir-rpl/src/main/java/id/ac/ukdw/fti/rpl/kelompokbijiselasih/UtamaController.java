package id.ac.ukdw.fti.rpl.kelompokbijiselasih;

import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class UtamaController implements Initializable{

    @FXML
    private PieChart pieChart;
    private ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
    private ObservableList<Item> dataResult = FXCollections.observableArrayList();
    private String searchBy = "";
    private Hashtable<String, Integer> my_dict = new Hashtable<>();
    private boolean indikator;


    public void recieve(String searchBy, ObservableList<Item> dataResultSent, boolean indikatorSent){
        this.searchBy = searchBy;
        this.dataResult = dataResultSent;
        this.indikator = indikatorSent;
        System.out.println("recieved");
        for(int i=0;i<dataResult.size()-1;i++){
            if(my_dict.containsKey(dataResult.get(i).getEvent())){
                if(!indikator){
                    String[] listPersons = dataResult.get(i).getEvent().split(",");
                    for(int j=0;j<listPersons.length;j++){
//                        my_dict.put(listPersons[j], 1);
                        int temp = my_dict.get(listPersons[j])+1;
                        my_dict.remove(listPersons[j]);
                        my_dict.put(listPersons[j], temp);
                    }
                }else{
                    int temp = my_dict.get(dataResult.get(i).getEvent())+1;
                    my_dict.remove(dataResult.get(i).getEvent());
                    my_dict.put(dataResult.get(i).getEvent(), temp);
                }
            }else{
                if(!dataResult.get(i).getEvent().isEmpty()){
                    if(!indikator){ //searchByEvent maka slicing person terkait
                        String[] listPersons = dataResult.get(i).getEvent().split(",");
                        for(int j=0;j<listPersons.length;j++){
                            my_dict.put(listPersons[j], 1);
                        }
                    }else{
                        my_dict.put(dataResult.get(i).getEvent(), 1);
                    }
                }
            }
        }
        
        for (String key : my_dict.keySet()) {
            pieChart.getData().add(new PieChart.Data(key + " ("+my_dict.get(key)+")", my_dict.get(key)));
            System.out.println(key+" : "+my_dict.get(key));
        }
        pieChart.getData().addAll(pieChartData);
        if(indikator){
            pieChart.setTitle("Visualisasi Pie Chart Events Untuk Tokoh "+searchBy);
        }else{
            pieChart.setTitle("Visualisasi Pie Chart Tokoh Untuk Book "+searchBy);
        }
    } 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pieChart.setLabelLineLength(50);
        pieChart.setLegendSide(Side.BOTTOM);
    }

}
