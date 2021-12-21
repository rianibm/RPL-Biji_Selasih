/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.ukdw.fti.rpl.kelompokbijiselasih;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
/**
 *
 * @author USER
 */
public class DBHelper {
    public Connection connect() {
        String url = "jdbc:sqlite:src/main/resources/vizbible.sqlite"; //merujuk ke alamat database dmn
        Connection conn = null;
        System.out.println("testt");
        // create a connection to the database  
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Gagal");
        }
        return conn;
    }
    
    public ArrayList<Item> searchWord(String keyword) { //fungsi yg dipanggil sama main controller pas cari kata
        ArrayList<Item> listHasil = new ArrayList<Item>(); //tampung hasil
        ArrayList<String> tempList = new ArrayList<String>(); //tampung pencarian

        //kalo dia ga nyari apa apa tapi masi tembus, return array list kosongan
        if(keyword.length()==0){
            return listHasil;
        }

        //baris 47 konversi kata yang dicari spy ga perlu sama persis kayak database
        //ambil kata pertama pake substring, konversi ke huruf gede sisanya ke huruf kecil
        keyword = keyword.substring(0,1).toUpperCase()+keyword.substring(1).toLowerCase();
        //ambil semua kolom dari table people jika namanya sesuai dengan keyword
        //kalo gada di people nanti cek di event
        String sql = "SELECT * FROM people where name='"+keyword+"'"; 
//        String personLookUp = "";
        System.out.println(sql);
        //baris 54 dst ambil datanya
        try {
            Connection conn = this.connect(); //buat koneksi ke databasenya, con manggil fungsi connect
            Statement stmt = conn.createStatement(); //klo udh ada koneksi bikin statement sqlnya
            ResultSet rs = stmt.executeQuery(sql); //ambil ResultSet-nya
            // 59 - 66 ambil query  
            while (rs.next()) {
//                personLookUp = rs.getString("personLookUp");
                String temp = rs.getString("verses"); //nyimpen ayatnya
                String[] temp2 = temp.split(","); //misahin jadi gen 1.1 string sendiri, gen 1.2 string sendiri, dipisahin pake koma
                for(int i=0;i<temp2.length;i++){ //looping temp2
                    tempList.add(temp2[i]);
                }
            }
            for(int i=0;i<tempList.size();i++){
                sql = "SELECT * FROM events where verses like '%"+tempList.get(i)+"%'";
                rs = stmt.executeQuery(sql);
                // loop through the result set, tampilin ayat + isi  
                while (rs.next()) {
                    String tempTitle = rs.getString("title");
                    String sql2 = "SELECT * FROM verses where osisRef='"+tempList.get(i)+"'";
                    ResultSet rs2 = stmt.executeQuery(sql2);
                    while(rs2.next()){
                        String ayat = rs2.getString("verseText");
                        Item item = new Item(tempList.get(i),ayat,tempTitle);
                        listHasil.add(item);
                    }
                }
            }
            if(!listHasil.isEmpty()){
                listHasil.add(new Item("0","person",keyword));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        //kalo ga ditemukan di people, dicari di event
        if(listHasil.isEmpty()){
            System.out.println("Hasil tidak ditemukan di person");
            sql = "SELECT * FROM events where title like '%"+keyword+"%'";
            ArrayList<String> tempVerses = new ArrayList<String>();
            System.out.println(sql);
            try {
                Connection conn = this.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                // loop through the result set  
                while (rs.next()) {
                    String temp = rs.getString("verses");
                    String[] temp2 = temp.split(",");
                    for(int i=0;i<temp2.length;i++){
                        tempVerses.add(temp2[i]);
                    }
                }
                System.out.println("dapat " + tempVerses.size());
                for(int i=0;i<tempVerses.size();i++){
                    String sql2 = "SELECT * FROM verses where osisRef='"+tempVerses.get(i)+"'";
                    String persons = "";
                    int counter = 0;
                    ResultSet rs2 = stmt.executeQuery(sql2);
                    while(rs2.next()){
                        String ayat = rs2.getString("verseText");
                        String personString = rs2.getString("people");
                        if(personString!=null){
                            String[] tempPerson = personString.split(",");
                            if(tempPerson.length!=0){
                                for(int j=0;j<tempPerson.length;j++){
                                    //query lagi supaya dapet nama krn sebelumnya dapetnya id misal adam 21
                                    String sql3 = "SELECT * FROM people where personLookup='"+tempPerson[j]+"'";
                                    System.out.println(sql3);
                                    ResultSet rs3 = stmt.executeQuery(sql3);
                                    while(rs3.next()){
                                        persons+=rs3.getString("name")+", "; //formatting nama: ...
                                        counter++;
                                    }
                                }                            
                            }
                        }
                        //hapus koma yang berlebihan kalo misal cuma dapet hasil 1 orang, adam koma spasi abraham koma
                        if(counter>=1){
                            persons = persons.substring(0, persons.length()-2);
                        }
                        
                        Item item = new Item(tempVerses.get(i),ayat,persons);
                        listHasil.add(item);
                    }
                }
                for(int j=0;j<listHasil.size();j++){
                    if(!listHasil.get(j).getEvent().isEmpty()){
                        String first = listHasil.get(0).getNum();
                        System.out.println(first);
                        String second = first.substring(0,first.lastIndexOf('.'));
                        //Gen.4.12
                        //01234567
                        String sql4 = "select * from books where chapters like '%"+second+"%';";
                        System.out.println(sql4);
                        ResultSet rs3 = stmt.executeQuery(sql4);
                        String book = "";
                        while (rs3.next()) {
                            book = rs3.getString("bookName");
                        }
                        System.out.println(book);
                        listHasil.add(new Item(book,"event",keyword));
                        break;
                    }
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return listHasil; //tipe datanya arrayList of Item
    }
    
   
    
}
