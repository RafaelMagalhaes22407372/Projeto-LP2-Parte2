package pt.ulusofona.lp2.greatprogrammingjourney;

public class CriacaoFerramentas {

    Ferramenta criarFerramenta(int id) {
        return switch (id) {
            case 0 -> new FerramentaHeranca();
            case 1 ->  new FerramentaProgramacaoFuncional();
            case 2 ->  new FerramentaTestesUnitarios();
            case 3 ->  new FerramentaTratamentoExcepcoes();
            case 4 ->  new FerramentaIDE();
            case 5 ->  new FerramentaAjudaDoProfessor();
            default -> null;
        };
    }
}
