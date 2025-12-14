package pt.ulusofona.lp2.greatprogrammingjourney;

public class FerramentaAjudaDoProfessor extends Ferramenta{

    public FerramentaAjudaDoProfessor() {
        super(5, "Ajuda Do Professor");
    }

    @Override
    public Boolean podeNeutralizarAbismo(int id) {
        return false;
    }
}
