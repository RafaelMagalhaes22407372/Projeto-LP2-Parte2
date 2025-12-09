package pt.ulusofona.lp2.greatprogrammingjourney;

public abstract class Ferramenta extends Casa {
    protected String nome;

    public Ferramenta(int posicao, String[][] abyssesAndTools) {
        super(abyssesAndTools,posicao);
        this.nome = nome;
    }

    public String getNome() { return nome; }

    @Override
    public abstract void acao(Jogador jogador);
}

