package br.unioeste.riscvirtualmachine.utils;

import br.unioeste.riscvirtualmachine.ReadOnlyBuffer;

public class Buffer implements ReadOnlyBuffer {
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
