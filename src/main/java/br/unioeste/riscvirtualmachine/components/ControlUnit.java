package br.unioeste.riscvirtualmachine.components;

import br.unioeste.riscvirtualmachine.Buffer;
import br.unioeste.riscvirtualmachine.ReadOnlyBuffer;

public class ControlUnit extends Component{
    private final ReadOnlyBuffer defaultIn;
    private final Buffer branchFlag;
    private final Buffer memToRegFlag;
    private final Buffer menReadFlag;
    private final Buffer ALUOpFlag;
    private final Buffer memWriteFlag;
    private final Buffer ALUSrcFlag;
    private final Buffer regWriteFlag;

    public final static int ADDI_OP = 0x13;
    public final static int BRANCH_OP = 0x63;
    public final static int REG_OP = 0x33;
    public final static int LW_OP = 0x3;
    public final static int SW_OP = 0x23;

    public ControlUnit(ReadOnlyBuffer defaultIn) {
        this.defaultIn = defaultIn;
        regWriteFlag = new Buffer();
        ALUSrcFlag = new Buffer();
        memWriteFlag = new Buffer();
        branchFlag = new Buffer();
        ALUOpFlag = new Buffer();
        menReadFlag = new Buffer();
        memToRegFlag = new Buffer();
    }

    @Override
    public void tick() {
        int opcode = defaultIn.read() & 0x7f;
        int[] outFlags;
        switch (opcode) {
            case ADDI_OP, SW_OP -> outFlags = new int[]{0, 0, 0, 0, 1, 1, 0};
            case BRANCH_OP -> outFlags = new int[]{1, 0, 0, 1, 0, 0, 0};
            case REG_OP -> outFlags = new int[]{0, 0, 0, 2, 0, 0, 1};
            case LW_OP -> outFlags = new int[]{0, 1, 1, 0, 0, 1, 1};
            default -> outFlags = new int[7];
        }

        branchFlag.write(outFlags[0]);
        memToRegFlag.write(outFlags[1]);
        menReadFlag.write(outFlags[2]);
        ALUOpFlag.write(outFlags[3]);
        memWriteFlag.write(outFlags[4]);
        ALUSrcFlag.write(outFlags[5]);
        regWriteFlag.write(outFlags[6]);
    }

    public Buffer getBranchFlag() {
        return branchFlag;
    }

    public Buffer getMemToRegFlag() {
        return memToRegFlag;
    }
    public Buffer getMenReadFlag() {
        return menReadFlag;
    }

    public Buffer getALUOpFlag() {
        return ALUOpFlag;
    }

    public Buffer getMemWriteFlag() {
        return memWriteFlag;
    }
    public Buffer getALUSrcFlag() {
        return ALUSrcFlag;
    }

    public Buffer getRegWriteFlag() {
        return regWriteFlag;
    }
}
