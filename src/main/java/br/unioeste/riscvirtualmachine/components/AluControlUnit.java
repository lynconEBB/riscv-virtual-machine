package br.unioeste.riscvirtualmachine.components;

import br.unioeste.riscvirtualmachine.utils.Buffer;
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
        int flag = aluOp.read();

        if (flag == 0) {
            defaultOut.write(Alu.ADD_OP);
        } else if (flag == 1 ) {
            defaultOut.write(Alu.SUB_OP);
        } else if (flag == 2) {

            int inst = instruction.read();
            int aux = (inst >> 12) & 0x7;
            int aux2 = ((inst >> 30) & 0x1) << 3;
            aux |= aux2;

            switch (aux) {
                case 0x0 -> defaultOut.write(Alu.ADD_OP);
                case 0x8 -> defaultOut.write(Alu.SUB_OP);
                case 0x7 -> defaultOut.write(Alu.AND_OP);
                case 0x6 -> defaultOut.write(Alu.OR_OP);
            }
        }
    }

    public Buffer getDefaultOut() {
        return defaultOut;
    }
}
