package pt.ulusofona.lp2.greatprogrammingjourney;

public class FerramentaIDE extends Ferramenta {

    public FerramentaIDE() {
        super(4, "IDE");
    }

    @Override
    public Boolean podeNeutralizarAbismo(int id) {
        return false;
    }
}
