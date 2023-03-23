package br.unioeste.riscvirtualmachine.components;

import br.unioeste.riscvirtualmachine.utils.Buffer;
import br.unioeste.riscvirtualmachine.ReadOnlyBuffer;

public class PCRegister extends Component{

    private ReadOnlyBuffer defaultIn;
    private final Buffer defaultOut;

    public PCRegister() {
        defaultOut = new Buffer();
    }

    @Override
    public void tick() {
        defaultOut.write(defaultIn.read());
    }

    public Buffer getDefaultOut() {
        return  defaultOut;
    }

    public void setDefaultIn(ReadOnlyBuffer defaultIn) {
        this.defaultIn = defaultIn;
    }
}
