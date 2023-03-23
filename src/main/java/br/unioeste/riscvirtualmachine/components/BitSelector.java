package br.unioeste.riscvirtualmachine.components;

import br.unioeste.riscvirtualmachine.utils.ReadOnlyBuffer;
import br.unioeste.riscvirtualmachine.utils.Buffer;

// Classe responsável por selecionar um unico bit da instrução
public class BitSelector extends Component{
    private final ReadOnlyBuffer defaultIn;
    private final Buffer defaultOut;

    public BitSelector(ReadOnlyBuffer defaultIn) {
        this.defaultIn = defaultIn;
        this.defaultOut = new Buffer();
    }

    // Escreve em seu buffer de saida o valor do bit da instrução responsável
    // por diferenciar as instruções bne e beq
    @Override
    public void tick() {
        defaultOut.write((defaultIn.read() >> 12) & 0x1);
    }

    public Buffer getDefaultOut() {
        return defaultOut;
    }
}
