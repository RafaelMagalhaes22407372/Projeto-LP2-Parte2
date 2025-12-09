package pt.ulusofona.lp2.greatprogrammingjourney;

public abstract class Abismo extends Casa {
    protected String nome;

    public Abismo(String[][] abyssesAndTools, int posicao) {
        super(abyssesAndTools ,posicao);
        this.nome = nome;
    }

    public String getNome() { return nome; }

    @Override
    public abstract void acao(Jogador jogador);
}

