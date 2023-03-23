module br.unioeste.riscvirtualmachine {
    requires javafx.controls;
    requires javafx.fxml;


    opens br.unioeste.riscvirtualmachine to javafx.fxml;
    exports br.unioeste.riscvirtualmachine;
    exports br.unioeste.riscvirtualmachine.utils;
    opens br.unioeste.riscvirtualmachine.utils to javafx.fxml;
}