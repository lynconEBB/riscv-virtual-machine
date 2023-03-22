package br.unioeste.riscvirtualmachine.components;

import br.unioeste.riscvirtualmachine.Buffer;
import br.unioeste.riscvirtualmachine.ReadOnlyBuffer;

public class Multiplexer extends Component{
    private final ReadOnlyBuffer input0;
    private final ReadOnlyBuffer input1;
    private final ReadOnlyBuffer control;
    private final Buffer defaultOut;

    public Multiplexer(ReadOnlyBuffer input0, ReadOnlyBuffer input1, ReadOnlyBuffer control) {
        this.input0 = input0;
        this.input1 = input1;
        this.control = control;
        this.defaultOut = new Buffer(0);
    }

    @Override
    public void tick() {
        if (control.read() == 0) {
           defaultOut.write(input0.read());
        } else {
           defaultOut.write(input1.read());
        }
    }

    public Buffer getDefaultOut() {
        return defaultOut;
    }
}
