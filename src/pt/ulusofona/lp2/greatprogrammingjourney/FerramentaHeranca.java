package pt.ulusofona.lp2.greatprogrammingjourney;

public class FerramentaHeranca extends Ferramenta {


    public FerramentaHeranca() {
        super(0, "Herança");
    }

    @Override
    public Boolean podeNeutralizarAbismo(int id) {
        return id == 5;
    }
}
