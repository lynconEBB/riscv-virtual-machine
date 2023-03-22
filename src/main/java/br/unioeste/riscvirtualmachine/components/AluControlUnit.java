package br.unioeste.riscvirtualmachine.components;

import br.unioeste.riscvirtualmachine.Buffer;
import br.unioeste.riscvirtualmachine.ReadOnlyBuffer;

public class AluControlUnit extends Component {

    private final ReadOnlyBuffer instruction;
    private final ReadOnlyBuffer aluOp;
    private final Buffer defaultOut;

    public AluControlUnit(ReadOnlyBuffer instruction, ReadOnlyBuffer aluOp) {
        this.instruction = instruction;
        this.aluOp = aluOp;
        this.defaultOut = new Buffer();
    }

    @Override
    public void tick() {

    }

    public Buffer getDefaultOut() {
        return defaultOut;
    }
}
