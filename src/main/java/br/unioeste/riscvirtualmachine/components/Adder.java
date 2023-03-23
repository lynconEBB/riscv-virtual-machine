package br.unioeste.riscvirtualmachine.components;

import br.unioeste.riscvirtualmachine.utils.Buffer;
import br.unioeste.riscvirtualmachine.utils.ReadOnlyBuffer;

// Componenete para adicionar números
public class Adder extends Component{
    // Primeiro operando de entrada
    private final ReadOnlyBuffer operand1;
    // Segundo operando de entrada
    private final ReadOnlyBuffer operand2;

    // Buffer de resultado
    private final Buffer result;

    public Adder(ReadOnlyBuffer operand1, ReadOnlyBuffer operand2) {
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.result = new Buffer();
    }

    // Adiciona os operandos e escreve no resultado
    @Override
    public void tick() {
        result.write(operand1.read() + operand2.read());
    }

    public Buffer getResult() {
        return result;
    }
}
