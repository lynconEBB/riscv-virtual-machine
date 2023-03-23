package br.unioeste.riscvirtualmachine.components;

import br.unioeste.riscvirtualmachine.utils.Buffer;
import br.unioeste.riscvirtualmachine.utils.ReadOnlyBuffer;
import javafx.scene.control.CheckBox;

import java.util.List;

// Classe responsável por escrever as flags necessárias para
// cada instrução
public class ControlUnit extends Component{
    private final ReadOnlyBuffer defaultIn;
    private final Buffer branchFlag;
    private final Buffer memToRegFlag;
    private final Buffer menReadFlag;
    private final Buffer ALUOpFlag;
    private final Buffer memWriteFlag;
    private final Buffer ALUSrcFlag;
    private final Buffer regWriteFlag;
    private final List<CheckBox> flagCheckBoxes;

    public final static int ADDI_OP = 0x13;
    public final static int BRANCH_OP = 0x63;
    public final static int REG_OP = 0x33;
    public final static int LW_OP = 0x3;
    public final static int SW_OP = 0x23;

    public ControlUnit(List<CheckBox> checkBoxList, ReadOnlyBuffer defaultIn) {
        this.flagCheckBoxes = checkBoxList;
        this.defaultIn = defaultIn;
        regWriteFlag = new Buffer();
        ALUSrcFlag = new Buffer();
        memWriteFlag = new Buffer();
        branchFlag = new Buffer();
        ALUOpFlag = new Buffer();
        menReadFlag = new Buffer();
        memToRegFlag = new Buffer();
    }

    // Atualiza os buffers de flag e os componentes visuais que identificam
    // as flags na janela, baseando-se na instrução recebida
    @Override
    public void tick() {
        int opcode = defaultIn.read() & 0x7f;
        int[] outFlags;
        switch (opcode) {
            case ADDI_OP -> outFlags = new int[]{0, 0, 0, 0, 0, 1, 1};
            case BRANCH_OP -> outFlags = new int[]{1, 0, 0, 1, 0, 0, 0};
            case REG_OP -> outFlags = new int[]{0, 0, 0, 2, 0, 0, 1};
            case LW_OP -> outFlags = new int[]{0, 1, 1, 0, 0, 1, 1};
            case SW_OP -> outFlags = new int[]{0, 0, 0, 0, 1, 1, 0};

            default -> outFlags = new int[7];
        }

        branchFlag.write(outFlags[0]);
        flagCheckBoxes.get(0).setSelected(outFlags[0] == 1);

        memToRegFlag.write(outFlags[1]);
        flagCheckBoxes.get(1).setSelected(outFlags[1] == 1);

        menReadFlag.write(outFlags[2]);
        flagCheckBoxes.get(2).setSelected(outFlags[2] == 1);

        ALUOpFlag.write(outFlags[3]);
        flagCheckBoxes.get(3).setSelected(outFlags[3] == 2);
        flagCheckBoxes.get(4).setSelected(outFlags[3] == 1);

        memWriteFlag.write(outFlags[4]);
        flagCheckBoxes.get(5).setSelected(outFlags[4] == 1);

        ALUSrcFlag.write(outFlags[5]);
        flagCheckBoxes.get(6).setSelected(outFlags[5] == 1);

        regWriteFlag.write(outFlags[6]);
        flagCheckBoxes.get(7).setSelected(outFlags[6] == 1);
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
