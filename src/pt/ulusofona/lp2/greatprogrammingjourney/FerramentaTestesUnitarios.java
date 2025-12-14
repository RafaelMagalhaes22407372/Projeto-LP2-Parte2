package pt.ulusofona.lp2.greatprogrammingjourney;

public class FerramentaTestesUnitarios extends Ferramenta {

    public FerramentaTestesUnitarios() {
        super(2, "Testes Unitarios");
    }

    @Override
    public Boolean podeNeutralizarAbismo(int id) {
        return false;
    }
}
