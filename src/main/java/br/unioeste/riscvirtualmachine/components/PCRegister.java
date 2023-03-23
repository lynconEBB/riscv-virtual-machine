package br.unioeste.riscvirtualmachine.components;

import br.unioeste.riscvirtualmachine.utils.Buffer;
import br.unioeste.riscvirtualmachine.utils.ReadOnlyBuffer;

// Classe representando o registrador que armazena o endereço da próxima
// instrução a ser executada
public class PCRegister extends Component{

    private ReadOnlyBuffer defaultIn;
    private final Buffer defaultOut;

    public PCRegister() {
        defaultOut = new Buffer();
    }

    // Transmite o valor presente em seu buffer de entrada
    // para seu buffer de saida
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
