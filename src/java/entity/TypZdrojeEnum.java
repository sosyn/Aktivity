/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author Ivo
 */
public enum TypZdrojeEnum {
    VOZIDLO("Vozidlo"),
    MISTNOST("Místnost"),
    DOPRAVA("Dopravní prostředek (jiný než vozidlo)"),
    TECHNIKA("Výpočetní technika"),
    OSTATNI("Ostatní nezařazené"),
    OSOBA("Osoba");

    private final String popis;

    TypZdrojeEnum(String popis) {
        this.popis=popis;
    }

    public int getId() {
        return this.ordinal();
    }   

    /**
     * @return the popis
     */
    public String getPopis() {
        return popis;
    }
}
