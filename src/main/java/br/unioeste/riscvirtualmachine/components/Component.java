package br.unioeste.riscvirtualmachine.components;


public abstract class Component {
    public void initialize() {}

    public abstract void tick();

    public void lateTick() {}

}
