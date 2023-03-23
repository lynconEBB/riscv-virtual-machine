package br.unioeste.riscvirtualmachine.components;

import br.unioeste.riscvirtualmachine.utils.Buffer;
import br.unioeste.riscvirtualmachine.utils.ReadOnlyBuffer;
import br.unioeste.riscvirtualmachine.utils.MemoryEntry;
import javafx.scene.control.TableView;

// Classe responsável por escrever e ler dados baseando-se em um endereço
public class DataMemory extends Component {
    private final ReadOnlyBuffer address;
    private final ReadOnlyBuffer writeData;
    private final ReadOnlyBuffer memWrite;
    private final ReadOnlyBuffer memRead;
    private final Buffer defaultOut;
    private final int[] memory;
    private final TableView<MemoryEntry> memoryTable;

    public DataMemory(TableView<MemoryEntry> memoryTable, ReadOnlyBuffer in, ReadOnlyBuffer writeData, ReadOnlyBuffer memWrite, ReadOnlyBuffer memRead) {
        this.memoryTable = memoryTable;
        this.address = in;
        this.writeData = writeData;
        this.memWrite = memWrite;
        this.memRead = memRead;
        this.defaultOut = new Buffer();
        this.memory = new int[64];
    }

    // Caso a flag "memRead" esteja acionada, o dado contido no endereço recebido
    // é escrito no buffer de saida, caso a flag "memWrite" esteja acionada, o dado
    // contido em "writeData" é salvo no endereço recebido
    @Override
    public void tick() {
        int index = address.read() / 4;

        if (memRead.read() == 1)
            defaultOut.write(memory[index]);

        if (memWrite.read() == 1) {
            memory[index] = writeData.read();
            memoryTable.getItems().get(index).update(memory[index]);
        }
    }

    public Buffer getDefaultOut() {
        return defaultOut;
    }
}
