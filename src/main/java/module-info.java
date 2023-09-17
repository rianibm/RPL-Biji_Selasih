module id.ac.ukdw.fti.rpl.kelompokbijiselasih {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;
    
    opens id.ac.ukdw.fti.rpl.kelompokbijiselasih to javafx.fxml;
    exports id.ac.ukdw.fti.rpl.kelompokbijiselasih;
}
