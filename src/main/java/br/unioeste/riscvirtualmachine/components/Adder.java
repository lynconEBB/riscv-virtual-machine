package br.unioeste.riscvirtualmachine.components;

import br.unioeste.riscvirtualmachine.Buffer;
import br.unioeste.riscvirtualmachine.ReadOnlyBuffer;

public class Adder extends Component{
    private final ReadOnlyBuffer operand1;
    private final ReadOnlyBuffer operand2;

    private final Buffer result;

    public Adder(ReadOnlyBuffer operand1, ReadOnlyBuffer operand2) {
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.result = new Buffer();
    }

    @Override
    public void tick() {
        result.write(operand1.read() + operand2.read());
    }

    public Buffer getResult() {
        return result;
    }
}
