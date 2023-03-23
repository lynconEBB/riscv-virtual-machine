package br.unioeste.riscvirtualmachine;

import br.unioeste.riscvirtualmachine.components.*;
import br.unioeste.riscvirtualmachine.utils.Buffer;
import br.unioeste.riscvirtualmachine.utils.MemoryEntry;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

// Classe representando o processador
public class Processor {

    // Lista de componentes do processador
    private final List<Component> components;

    // Construtor do Processador, responsável por inicializar e configurar as entradas
    // e saidas de cada componente
    public Processor(File file, TableView<MemoryEntry> memoryTable, List<Label> registerLabels, List<CheckBox> flagCheckBoxes) throws FileNotFoundException {
        components = new ArrayList<>();

        PCRegister pcRegister = new PCRegister();
        components.add(pcRegister);

        InstructionMemory instructionMemory = new InstructionMemory(pcRegister.getDefaultOut());
        components.add(instructionMemory);
        instructionMemory.load(new FileInputStream(file));

        ControlUnit controlUnit = new ControlUnit(flagCheckBoxes, instructionMemory.getDefaultOut());
        components.add(controlUnit);

        RegisterFile registerFile = new RegisterFile(
                registerLabels,
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
                memoryTable,
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

    // Atualiza todos os componentes presentes na lista de componente
    // retorna uma variavel booleana indicando se o programa pode continuar
    // executando ou não
    public boolean tick() {
        for (Component component : components) {
            component.tick();
        }
        for (Component component : components) {
            component.lateTick();
        }
        InstructionMemory instructionMemory = (InstructionMemory) components.get(1);
        return (getCurrentPC() / 4) < instructionMemory.getInstructionsCount();
    }

    // Retorna a instrução a ser executada neste ciclo
    public int getCurrentPC() {
        Multiplexer finalMux = (Multiplexer) components.get(16);
        return finalMux.getDefaultOut().read();
    }

}
