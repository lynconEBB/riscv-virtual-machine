package br.unioeste.riscvirtualmachine.components;

import br.unioeste.riscvirtualmachine.utils.Buffer;
import br.unioeste.riscvirtualmachine.utils.ReadOnlyBuffer;
import javafx.scene.control.Label;

import java.util.List;

// Componente responsável por realizar as ações realizadas com registadores
// como escrita e leitura
public class RegisterFile extends Component{

    private final ReadOnlyBuffer regWrite;
    private final ReadOnlyBuffer register1;
    private final ReadOnlyBuffer register2;
    private final ReadOnlyBuffer writeRegister;
    private ReadOnlyBuffer writeData;
    private final Buffer readData1;
    private final Buffer readData2;
    private final int[] registers;
    private final List<Label> registerLabels;

    public RegisterFile(List<Label> registerLabels,
                        ReadOnlyBuffer regWrite,
                        ReadOnlyBuffer register1,
                        ReadOnlyBuffer register2,
                        ReadOnlyBuffer writeRegister) {
        this.registerLabels = registerLabels;
        this.regWrite = regWrite;
        this.register1 = register1;
        this.register2 = register2;
        this.writeRegister = writeRegister;
        this.readData1 = new Buffer();
        this.readData2 = new Buffer();
        this.registers = new int[32];
    }

    // Escreve nos buffers de saida o valor de cada registrador requistado
    @Override
    public void tick() {
        int r1 = (register1.read() >> 15) & 0x1F;
        int r2 = (register2.read() >> 20) & 0x1F;

        readData1.write(registers[r1]);
        readData2.write(registers[r2]);
    }

    // Atualiza o registrador "writeRegister" com o dado "writeData"
    // caso a flag "regWrite" esteja acionada
    @Override
    public void lateTick() {
        super.lateTick();
        if (regWrite.read() == 1) {
            int rw = (writeRegister.read() >> 7) & 0x1F;
            registers[rw] = writeData.read();
            registerLabels.get(rw).setText("x" + rw + ": " + registers[rw]);
        }
    }

    public Buffer getReadData1() {
        return readData1;
    }

    public Buffer getReadData2() {
        return readData2;
    }

    public void setWriteData(ReadOnlyBuffer writeData) {
        this.writeData = writeData;
    }
}
