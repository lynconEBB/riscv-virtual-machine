package br.unioeste.riscvirtualmachine.components;

import br.unioeste.riscvirtualmachine.Buffer;
import br.unioeste.riscvirtualmachine.ReadOnlyBuffer;

public class AndGate extends Component{

    private final ReadOnlyBuffer operand1;
    private final ReadOnlyBuffer operand2;
    private final Buffer defaultOut;

    public AndGate(ReadOnlyBuffer operand1, ReadOnlyBuffer operand2) {
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.defaultOut = new Buffer();
    }

    @Override
    public void tick() {
        if (operand1.read() == 1 && operand2.read() == 1) {
            defaultOut.write(1);
        } else {
           defaultOut.write(0);
        }
    }

    public Buffer getDefaultOut() {
        return defaultOut;
    }
}
