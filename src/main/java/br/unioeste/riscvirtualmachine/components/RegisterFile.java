package br.unioeste.riscvirtualmachine.components;

import br.unioeste.riscvirtualmachine.utils.Buffer;
import br.unioeste.riscvirtualmachine.ReadOnlyBuffer;

public class RegisterFile extends Component{

    private final ReadOnlyBuffer regWrite;
    private final ReadOnlyBuffer register1;
    private final ReadOnlyBuffer register2;
    private final ReadOnlyBuffer writeRegister;
    private ReadOnlyBuffer writeData;
    private final Buffer readData1;
    private final Buffer readData2;
    private final int[] registers;

    public RegisterFile(ReadOnlyBuffer regWrite,
                        ReadOnlyBuffer register1,
                        ReadOnlyBuffer register2,
                        ReadOnlyBuffer writeRegister) {
        this.regWrite = regWrite;
        this.register1 = register1;
        this.register2 = register2;
        this.writeRegister = writeRegister;
        this.readData1 = new Buffer();
        this.readData2 = new Buffer();
        this.registers = new int[32];
    }

    @Override
    public void tick() {
        int r1 = (register1.read() >> 15) & 0x1F;
        int r2 = (register2.read() >> 20) & 0x1F;

        readData1.write(registers[r1]);
        readData2.write(registers[r2]);
    }

    @Override
    public void lateTick() {
        super.lateTick();
        if (regWrite.read() == 1) {
            int rw = (writeRegister.read() >> 7) & 0x1F;
            registers[rw] = writeData.read();
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
