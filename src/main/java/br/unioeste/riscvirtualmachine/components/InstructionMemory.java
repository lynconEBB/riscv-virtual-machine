package br.unioeste.riscvirtualmachine.components;

import br.unioeste.riscvirtualmachine.Buffer;
import br.unioeste.riscvirtualmachine.ReadOnlyBuffer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class InstructionMemory extends Component{
    private final ReadOnlyBuffer defaultIn;
    private final Buffer defaultOut;
    private List<Integer> memory;

    public InstructionMemory(Buffer in) {
        this.defaultIn = in;
        this.defaultOut = new Buffer();
        this.memory = new ArrayList<>();
    }

    public void load(InputStream in) {
         
    }

    @Override
    public void tick() {
        defaultOut.write(memory.get(defaultIn.read()));
    }
}