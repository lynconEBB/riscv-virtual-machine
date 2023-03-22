package br.unioeste.riscvirtualmachine.components;

import br.unioeste.riscvirtualmachine.Buffer;
import br.unioeste.riscvirtualmachine.ReadOnlyBuffer;

import java.util.ArrayList;
import java.util.List;

public class DataMemory extends Component{
    private final ReadOnlyBuffer address;
    private final ReadOnlyBuffer writeData;
    private final ReadOnlyBuffer memWrite;
    private final ReadOnlyBuffer memRead;
    private final Buffer defaultOut;
    private final List<Integer> memory;

    public DataMemory(ReadOnlyBuffer in, ReadOnlyBuffer writeData, ReadOnlyBuffer memWrite, ReadOnlyBuffer memRead) {
        this.address = in;
        this.writeData = writeData;
        this.memWrite = memWrite;
        this.memRead = memRead;
        this.defaultOut = new Buffer();
        this.memory = new ArrayList<>();
    }

    @Override
    public void tick() {
        defaultOut.write(memory.get(address.read()));
    }

    public Buffer getDefaultOut() {
        return defaultOut;
    }
}
