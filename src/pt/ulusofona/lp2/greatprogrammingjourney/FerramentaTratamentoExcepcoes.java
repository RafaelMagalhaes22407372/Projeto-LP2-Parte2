package pt.ulusofona.lp2.greatprogrammingjourney;

public class FerramentaTratamentoExcepcoes extends Ferramenta {

    public FerramentaTratamentoExcepcoes() {
        super(3, "Tratamento Excepções");
    }

    @Override
    public Boolean podeNeutralizarAbismo(int id) {
        return id == 3;
    }
}
