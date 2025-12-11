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

    public void setAbismo(int id, CriacaoAbismos abismos) {
        this.abismo = abismos.criarAbismo(id);
    }

    public void setFerramenta(int id, CriacaoFerramentas ferramentas) {
        this.ferramenta = ferramentas.criarFerramenta(id);
    }

    public Abismo getAbismo() {
        return abismo;
    }

    public Ferramenta getFerramenta() {
        return ferramenta;
    }
}

