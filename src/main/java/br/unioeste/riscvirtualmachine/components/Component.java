package br.unioeste.riscvirtualmachine.components;


// Classe mãe de todos os componentes utilizados pelo processador
public abstract class Component {

    // Função executada para fazer atualização dos buffers dos componentes
    public abstract void tick();

    // Função para fazer ajustes finais nos buffers dos componentes
    public void lateTick() {}

}
