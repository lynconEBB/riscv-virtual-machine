package br.unioeste.riscvirtualmachine.components;

import br.unioeste.riscvirtualmachine.utils.Buffer;
import br.unioeste.riscvirtualmachine.utils.ReadOnlyBuffer;

// Classe responsável por inverter os bits de entrada
public class NotGate extends Component{

    private final ReadOnlyBuffer defaultIn;
    private final Buffer defaultOut;

    public NotGate(ReadOnlyBuffer defaultIn) {
        this.defaultIn = defaultIn;
        this.defaultOut = new Buffer();
    }

    // Inverte o valor recebido como entrada escrevendo em seu buffer de saida
    @Override
    public void tick() {
        defaultOut.write((defaultIn.read() == 1) ? 0 : 1);
    }

    public Buffer getDefaultOut() {
        return defaultOut;
    }
}
