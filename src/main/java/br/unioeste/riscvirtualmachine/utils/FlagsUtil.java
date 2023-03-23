package br.unioeste.riscvirtualmachine.utils;

import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FlagsUtil {
    // Mapa sequencial contendo os nomes das flags para serem exibidas
    // na janela
    public static Map<Integer, String> namesMap = Stream.of(
                    new AbstractMap.SimpleEntry<>(0, "Branch"),
                    new AbstractMap.SimpleEntry<>(1, "memToReg"),
                    new AbstractMap.SimpleEntry<>(2, "memRead"),
                    new AbstractMap.SimpleEntry<>(3, "AluOp1"),
                    new AbstractMap.SimpleEntry<>(4, "AluOp0"),
                    new AbstractMap.SimpleEntry<>(5, "memWrite"),
                    new AbstractMap.SimpleEntry<>(6, "AluSrc"),
                    new AbstractMap.SimpleEntry<>(7, "RegWrite"))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
}
