package pt.ulusofona.lp2.greatprogrammingjourney;

public abstract class AbismoOuFerramenta {

    protected int id;
    protected String nome;
    protected int posicaon;

    public AbismoOuFerramenta(int id, String nome, int posicaon) {
        this.id = id;
        this.nome = nome;
        this.posicaon = posicaon;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getPosicaon() {
        return posicaon;
    }

    public abstract String getTipo();

    public abstract String aplicaJogador(Jogador jogador, GameManager gameManager);
}
