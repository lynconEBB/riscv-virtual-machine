package br.unioeste.riscvirtualmachine.components;

import br.unioeste.riscvirtualmachine.utils.Buffer;
import br.unioeste.riscvirtualmachine.ReadOnlyBuffer;

public class ImmediateGenerator extends Component{

    private final ReadOnlyBuffer defaultIn;

    private final Buffer defaultOut;

    public ImmediateGenerator(ReadOnlyBuffer defaultIn) {
        this.defaultIn = defaultIn;
        this.defaultOut = new Buffer();
    }

    @Override
    public void tick() {
        int instruction = defaultIn.read();
        int opcode = defaultIn.read() & 0x7f;

        switch (opcode) {
            case ControlUnit.ADDI_OP, ControlUnit.LW_OP -> {
                int aux = (instruction >> 20) & 0xFFF;
                defaultOut.write(aux);
            }
            case ControlUnit.BRANCH_OP -> {
                int aux = ((instruction >> 8) & 0xF) << 1;
                int aux2 = ((instruction >> 25) & 0x3F) << 5;
                aux |= aux2;
                aux2 = ((instruction >> 7) & 0x1) << 11;
                aux |= aux2;
                aux2 = ((instruction >> 31) & 0x1) << 12;
                aux |= aux2;
                defaultOut.write(aux);
            }
            case ControlUnit.SW_OP -> {
                int aux = (instruction >> 7) & 0x1F;
                int aux2 = ((instruction >> 25) & 0x7F) << 5;
                aux |= aux2;
                defaultOut.write(aux);
            }
            default -> defaultOut.write(0);
        }
    }

    public Buffer getDefaultOut() {
        return defaultOut;
    }
}
