package br.unioeste.riscvirtualmachine;

import br.unioeste.riscvirtualmachine.components.InstructionMemory;
import br.unioeste.riscvirtualmachine.components.PCRegister;

import java.io.InputStream;

public class Processor {

    public Processor() {
        PCRegister pcRegister = new PCRegister(new Buffer());
        new InstructionMemory(pcRegister.getDefaultOut());

    }
}
