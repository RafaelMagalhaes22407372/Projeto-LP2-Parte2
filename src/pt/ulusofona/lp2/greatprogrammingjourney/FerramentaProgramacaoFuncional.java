package pt.ulusofona.lp2.greatprogrammingjourney;

public class FerramentaProgramacaoFuncional extends Ferramenta {


    public FerramentaProgramacaoFuncional() {
        super(1, "Programação Funcional");
    }

    @Override
    public Boolean podeNeutralizarAbismo(int id) {
        return id == 6;
    }
}
