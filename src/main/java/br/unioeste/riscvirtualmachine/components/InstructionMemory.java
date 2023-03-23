package br.unioeste.riscvirtualmachine.components;

import br.unioeste.riscvirtualmachine.utils.Buffer;
import br.unioeste.riscvirtualmachine.ReadOnlyBuffer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InstructionMemory extends Component{
    private final ReadOnlyBuffer defaultIn;
    private final Buffer defaultOut;
    private final List<Integer> memory;

    public InstructionMemory(ReadOnlyBuffer in) {
        this.defaultIn = in;
        this.defaultOut = new Buffer();
        this.memory = new ArrayList<>();
    }

    public boolean load(InputStream in) {
        Scanner scanner = new Scanner(in);
        while (scanner.hasNextLine()) {
            String strInstruction = scanner.nextLine();
            if (strInstruction.length() != 32)
                return false;

            int down = Integer.parseInt(strInstruction.substring(1), 2);
            int up = Integer.parseInt(strInstruction.substring(0,2),2);
            int b = (up << 31) | down;
            memory.add(b);
        }

        return true;
    }

    @Override
    public void tick() {
        int i = defaultIn.read() / 4;
        defaultOut.write(memory.get(i));
    }

    public Buffer getDefaultOut() {
        return defaultOut;
    }
}