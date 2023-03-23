package br.unioeste.riscvirtualmachine.utils;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

// Classe modelo de uma entrada na memória
public class MemoryEntry {
    // Endereço do item da memória
    private final SimpleIntegerProperty address;
    // Dado contido no endereço em formato decimal
    private final SimpleIntegerProperty decimalValue;

    // Dado contido no endereço em formato binario
    private final SimpleStringProperty binaryValue;

    public MemoryEntry(int address, int decimalValue, String binaryValue) {
        this.address = new SimpleIntegerProperty(address);
        this.decimalValue = new SimpleIntegerProperty(decimalValue);
        this.binaryValue = new SimpleStringProperty(binaryValue);
    }

    // Atualiza o dado presente nesta entrada
    public void update(int value) {
        decimalValue.set(value);
        StringBuilder str = new StringBuilder(Integer.toBinaryString(value));
        while (str.length() < 32) {
           str.insert(0, "0");
        }
        str.insert(0, "0b");
        binaryValue.set(str.toString());
    }

    public int getAddress() {
        return address.get();
    }

    public SimpleIntegerProperty addressProperty() {
        return address;
    }

    public void setAddress(int address) {
        this.address.set(address);
    }

    public int getDecimalValue() {
        return decimalValue.get();
    }

    public SimpleIntegerProperty decimalValueProperty() {
        return decimalValue;
    }

    public void setDecimalValue(int decimalValue) {
        this.decimalValue.set(decimalValue);
    }

    public String getBinaryValue() {
        return binaryValue.get();
    }

    public SimpleStringProperty binaryValueProperty() {
        return binaryValue;
    }

    public void setBinaryValue(String binaryValue) {
        this.binaryValue.set(binaryValue);
    }
}
