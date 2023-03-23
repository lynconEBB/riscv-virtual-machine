package br.unioeste.riscvirtualmachine.components;

import br.unioeste.riscvirtualmachine.utils.Buffer;
import br.unioeste.riscvirtualmachine.utils.ReadOnlyBuffer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Classe responsável por carregar as instruções a partir de um arquivo,
// armazenando-as para futuras consultas baseadas em um endereço
public class InstructionMemory extends Component{
    private final ReadOnlyBuffer defaultIn;
    private final Buffer defaultOut;
    private final List<Integer> memory;

    public InstructionMemory(ReadOnlyBuffer in) {
        this.defaultIn = in;
        this.defaultOut = new Buffer();
        this.memory = new ArrayList<>();
    }

    // Popula o vetor de instruções a partir de uma stream de entrada
    // uma exceção é disparada caso o arquivo contenha uma instrução invalida
    public void load(InputStream in) {
        Scanner scanner = new Scanner(in);
        while (scanner.hasNextLine()) {
            String strInstruction = scanner.nextLine();
            if (strInstruction.length() != 32)
                throw new RuntimeException("Invalid file");

            // Necessário para ler os dados como complemento de 2
            int down = Integer.parseInt(strInstruction.substring(1), 2);
            int up = Integer.parseInt(strInstruction.substring(0,2),2);
            int b = (up << 31) | down;
            memory.add(b);
        }
    }

    // Escreve no buffer de saida a instrução contida no endereço recebido
    @Override
    public void tick() {
        int i = defaultIn.read() / 4;
        defaultOut.write(memory.get(i));
    }

    public Buffer getDefaultOut() {
        return defaultOut;
    }

    public int getInstructionsCount() {
        return memory.size();
    }
}