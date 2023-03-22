package br.unioeste.riscvirtualmachine.components;

import br.unioeste.riscvirtualmachine.Buffer;
import br.unioeste.riscvirtualmachine.ReadOnlyBuffer;

public class Alu extends Component{

    private final ReadOnlyBuffer operand1;
    private final ReadOnlyBuffer operand2;
    private final ReadOnlyBuffer operator;

    private final Buffer zeroFlag;
    private final Buffer result;

    public Alu(ReadOnlyBuffer operand1, ReadOnlyBuffer operand2, ReadOnlyBuffer operator) {
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.operator = operator;
        this.zeroFlag = new Buffer();
        this.result = new Buffer();
    }

    @Override
    public void tick() {

    }

    public Buffer getZeroFlag() {
        return zeroFlag;
    }

    public Buffer getResult() {
        return result;
    }
}
