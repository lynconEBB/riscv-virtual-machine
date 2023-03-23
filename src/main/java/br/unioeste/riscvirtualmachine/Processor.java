package br.unioeste.riscvirtualmachine;

import br.unioeste.riscvirtualmachine.components.*;
import br.unioeste.riscvirtualmachine.utils.Buffer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Processor {

    private final List<Component> components;

    public Processor() {
        components = new ArrayList<>();

        PCRegister pcRegister = new PCRegister();
        components.add(pcRegister);

        InstructionMemory instructionMemory = new InstructionMemory(pcRegister.getDefaultOut());
        components.add(instructionMemory);
        try {
            instructionMemory.load(new FileInputStream("testFiles/teste1.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        ControlUnit controlUnit = new ControlUnit(instructionMemory.getDefaultOut());
        components.add(controlUnit);

        RegisterFile registerFile = new RegisterFile(
                controlUnit.getRegWriteFlag(),
                instructionMemory.getDefaultOut(),
                instructionMemory.getDefaultOut(),
                instructionMemory.getDefaultOut());
        components.add(registerFile);

        ImmediateGenerator immediateGenerator = new ImmediateGenerator(instructionMemory.getDefaultOut());
        components.add(immediateGenerator);

        Multiplexer muxAluSrc = new Multiplexer(
                registerFile.getReadData2(),
                immediateGenerator.getDefaultOut(),
                controlUnit.getALUSrcFlag());
        components.add(muxAluSrc);

        AluControlUnit aluControlUnit = new AluControlUnit(
                instructionMemory.getDefaultOut(),
                 controlUnit.getALUOpFlag());
        components.add(aluControlUnit);

        Alu alu = new Alu(
                registerFile.getReadData1(),
                muxAluSrc.getDefaultOut(),
                aluControlUnit.getDefaultOut());
        components.add(alu);

        DataMemory dataMemory = new DataMemory(
                alu.getResult(),
                registerFile.getReadData2(),
                controlUnit.getMemWriteFlag(),
                controlUnit.getMenReadFlag());
        components.add(dataMemory);

        Multiplexer muxMemToReg = new Multiplexer(alu.getResult(), dataMemory.getDefaultOut(), controlUnit.getMemToRegFlag());
        registerFile.setWriteData(muxMemToReg.getDefaultOut());
        components.add(muxMemToReg);

        NotGate notZeroGate = new NotGate(alu.getZeroFlag());
        components.add(notZeroGate);
        BitSelector bitSelector = new BitSelector(instructionMemory.getDefaultOut());
        components.add(bitSelector);
        Multiplexer muxBranch = new Multiplexer(alu.getZeroFlag(), notZeroGate.getDefaultOut(), bitSelector.getDefaultOut());
        components.add(muxBranch);
        AndGate andGate = new AndGate(controlUnit.getBranchFlag(), muxBranch.getDefaultOut());
        components.add(andGate);

        Adder defaultAdder = new Adder(pcRegister.getDefaultOut(), new Buffer(4));
        components.add(defaultAdder);
        Adder jumpAdder = new Adder(pcRegister.getDefaultOut(), immediateGenerator.getDefaultOut());
        components.add(jumpAdder);

        Multiplexer muxJump = new Multiplexer(
                defaultAdder.getResult(),
                jumpAdder.getResult(),
                andGate.getDefaultOut());
        components.add(muxJump);

        pcRegister.setDefaultIn(muxJump.getDefaultOut());
    }

    public void tick() {
        for (Component component : components) {
            component.tick();
        }
        for (Component component : components) {
            component.lateTick();
        }
    }
}
