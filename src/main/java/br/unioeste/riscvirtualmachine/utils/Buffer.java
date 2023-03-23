package br.unioeste.riscvirtualmachine.utils;

// Classe responsável por realizar a comunicação entre componentes
// armazenado um dado para ser lido por outro componente
public class Buffer implements ReadOnlyBuffer {
    private int value;

    public Buffer() {}

    public Buffer(int value) {
        this.value = value;
    }

    // Salva do dado
    public void write(int value) {
        this.value = value;
    }

    // Lê o dado salvo
    public int read() {
        return value;
    }
}
