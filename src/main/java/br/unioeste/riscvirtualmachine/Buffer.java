package br.unioeste.riscvirtualmachine;

public class Buffer implements ReadOnlyBuffer{
    private int value;

    public Buffer() {}

    public Buffer(int value) {
        this.value = value;
    }

    public void write(int value) {
        this.value = value;
    }

    public int read() {
        return value;
    }
}
