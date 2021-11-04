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
public class Item {
    private String num;
    private String verse;
    private String event;

    public Item(String num, String verse, String event) {
        this.num = num;
        this.verse = verse;
        this.event = event;
    }

    public Item() {
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getVerse() {
        return verse;
    }

    public void setVerse(String verse) {
        this.verse = verse;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "Item{" + "verse=" + verse + ", event=" + event + '}';
    }
    
}
