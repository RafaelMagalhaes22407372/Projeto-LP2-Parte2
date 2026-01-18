package pt.ulusofona.lp2.greatprogrammingjourney;

public class AbismoCicloInfinito extends AbismoPai {
    private Jogador jogadorPresoAtualmente = null;

    public AbismoCicloInfinito(int id, String nome, int posicao) {
        super(id, nome, posicao);
    }

    @Override
    public String aplicaJogador(Jogador jogador, GameManager gestorJogo) {
        if (anularComFerramenta(jogador)) {
            return this.nome + " anulado por " + anulacoes.get(id);
        }

        if (jogadorPresoAtualmente != null && jogadorPresoAtualmente != jogador) {
            gestorJogo.libertarJogador(jogadorPresoAtualmente);
        }

        this.jogadorPresoAtualmente = jogador;
        gestorJogo.prenderJogador(jogador);

        return "Caiu em " + nome + ". Ficaste preso num Ciclo Infinito!";
    }
}
