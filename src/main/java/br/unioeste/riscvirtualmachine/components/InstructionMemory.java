package br.unioeste.riscvirtualmachine.components;

import br.unioeste.riscvirtualmachine.Buffer;
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

    static String findTwoscomplement(StringBuffer str)
    {
        int n = str.length();

        // Traverse the string to get first '1' from
        // the last of string
        int i;
        for (i = n-1 ; i >= 0 ; i--)
            if (str.charAt(i) == '1')
                break;

        // If there exists no '1' concat 1 at the
        // starting of string
        if (i == -1)
            return "1" + str;

        // Continue traversal after the position of
        // first '1'
        for (int k = i-1 ; k >= 0; k--)
        {
            //Just flip the values
            if (str.charAt(k) == '1')
                str.replace(k, k+1, "0");
            else
                str.replace(k, k+1, "1");
        }

        // return the modified string
        return str.toString();
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