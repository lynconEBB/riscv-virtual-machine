package br.unioeste.riscvirtualmachine.components;

import br.unioeste.riscvirtualmachine.ReadOnlyBuffer;
import br.unioeste.riscvirtualmachine.utils.Buffer;

public class BitSelector extends Component{
    private final ReadOnlyBuffer defaultIn;
    private final Buffer defaultOut;

    public BitSelector(ReadOnlyBuffer defaultIn) {
        this.defaultIn = defaultIn;
        this.defaultOut = new Buffer();
    }

    @Override
    public void tick() {
        defaultOut.write((defaultIn.read() >> 12) & 0x1);
    }

    public Buffer getDefaultOut() {
        return defaultOut;
    }
}
