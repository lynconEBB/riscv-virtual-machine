package br.unioeste.riscvirtualmachine.components;

import br.unioeste.riscvirtualmachine.utils.Buffer;
import br.unioeste.riscvirtualmachine.ReadOnlyBuffer;

import java.util.ArrayList;
import java.util.List;

public class DataMemory extends Component{
    private final ReadOnlyBuffer address;
    private final ReadOnlyBuffer writeData;
    private final ReadOnlyBuffer memWrite;
    private final ReadOnlyBuffer memRead;
    private final Buffer defaultOut;
    private final int[] memory;

    public DataMemory(ReadOnlyBuffer in, ReadOnlyBuffer writeData, ReadOnlyBuffer memWrite, ReadOnlyBuffer memRead) {
        this.address = in;
        this.writeData = writeData;
        this.memWrite = memWrite;
        this.memRead = memRead;
        this.defaultOut = new Buffer();
        this.memory = new int[64];
    }

    @Override
    public void tick() {
        int index = address.read() / 4;

        if (memRead.read() == 1)
            defaultOut.write(memory[index]);

        if (memWrite.read() == 1)
           memory[index] = writeData.read();
    }

    public Buffer getDefaultOut() {
        return defaultOut;
    }
}
