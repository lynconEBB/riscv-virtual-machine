package br.unioeste.riscvirtualmachine.components;

import br.unioeste.riscvirtualmachine.utils.Buffer;
import br.unioeste.riscvirtualmachine.ReadOnlyBuffer;

public class Alu extends Component{

    private final ReadOnlyBuffer operand1;
    private final ReadOnlyBuffer operand2;
    private final ReadOnlyBuffer operator;

    private final Buffer zeroFlag;
    private final Buffer result;

    public static final int ADD_OP = 0x2;
    public static final int SUB_OP = 0x6;
    public static final int AND_OP = 0x0;
    public static final int OR_OP = 0x1;

    public Alu(ReadOnlyBuffer operand1, ReadOnlyBuffer operand2, ReadOnlyBuffer operator) {
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.operator = operator;
        this.zeroFlag = new Buffer();
        this.result = new Buffer();
    }

    @Override
    public void tick() {
        switch (operator.read()) {
            case ADD_OP -> result.write(operand1.read() + operand2.read());
            case SUB_OP -> result.write(operand1.read() - operand2.read());
            case AND_OP -> result.write(operand1.read() & operand2.read());
            case OR_OP -> result.write(operand1.read() | operand2.read());
        }

        zeroFlag.write((result.read() == 0) ? 1 : 0);
    }

    public Buffer getZeroFlag() {
        return zeroFlag;
    }

    public Buffer getResult() {
        return result;
    }
}
