/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.ukdw.fti.rpl.kelompokbijiselasih;

/**
 *
 * @author USER
 */
public class DataVisual {
    private String cat;
    private int count;

    public DataVisual(String cat, int count) {
        this.cat = cat;
        this.count = count;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    
    
}
