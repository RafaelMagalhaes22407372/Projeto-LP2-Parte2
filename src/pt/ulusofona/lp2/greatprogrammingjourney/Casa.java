package pt.ulusofona.lp2.greatprogrammingjourney;

public abstract class Casa {
    protected int tipo;
    protected int id;
    protected int posicao;
    protected String[][] abyssesAndTools;
    protected int boardSize;


    public Casa(String[][] abyssesAndTools, int boardSize) {
        this.abyssesAndTools = abyssesAndTools;
        this.boardSize = boardSize;

    }


    // Metodo que define o efeito da casa
    public abstract void acao(Jogador jogador);
}

