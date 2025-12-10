package pt.ulusofona.lp2.greatprogrammingjourney;

public class Casa {
    private int posicao;
    private Abismo abismo;       // pode ser null
    private Ferramenta ferramenta; // pode ser null

    public Casa(int posicao) {
        this.posicao = posicao;
    }

    public int getPosicao() {
        return posicao;
    }

    public boolean temAbismo() {
        return abismo != null;
    }

    public boolean temFerramenta() {
        return ferramenta != null;
    }

    public void setAbismo(int id) {
        this.abismo = switch (id) {
            case 0 -> new AbismoErroSintaxe();
            case 1 -> new AbismoErroLogica();
            case 2 -> new AbismoException();
            case 3 -> new AbismoFileNotFoundException();
            case 4 -> new AbismoCrash();
            case 5 -> new AbismoCodigoDuplicado();
            case 6 -> new AbismoEfeitosSecundarios();
            case 7 -> new AbismoBlueScreenofDeath();
            case 8 -> new AbismoCicloInfinito();
            case 9 -> new AbismoSegmentationFault();
            default -> null;
        };
    }

    public void setFerramenta(int id) {
        this.ferramenta = switch (id) {
            case 0 -> new FerramentaHeranca();
            case 1 ->  new FerramentaProgramacaoFuncional();
            case 2 ->  new FerramentaTestesUnitarios();
            case 3 ->  new FerramentaTratamentoExcepcoes();
            case 4 ->  new FerramentaIDE();
            case 5 ->  new FerramentaAjudaDoProfessor();
            default -> null;
        };
    }

    public Abismo getAbismo() {
        return abismo;
    }

    public Ferramenta getFerramenta() {
        return ferramenta;
    }
}

