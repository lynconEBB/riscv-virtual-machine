package br.unioeste.riscvirtualmachine.components;

import br.unioeste.riscvirtualmachine.Buffer;
import br.unioeste.riscvirtualmachine.ReadOnlyBuffer;

public class PCRegister extends Component{

    private ReadOnlyBuffer defaultIn;
    private final Buffer defaultOut;

    public PCRegister(ReadOnlyBuffer defaultIn) {
        this.defaultIn = defaultIn;
        defaultOut = new Buffer();
    }

    @Override
    public void tick() {

    }

    public Buffer getDefaultOut() {
        return  defaultOut;
    }
}
