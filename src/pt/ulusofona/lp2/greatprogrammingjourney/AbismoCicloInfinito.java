package pt.ulusofona.lp2.greatprogrammingjourney;

public class AbismoCicloInfinito extends AbismoPai {

    public AbismoCicloInfinito(int id, String nome, int posicao) {
        super(id, nome, posicao);
    }

    @Override
    public String aplicaJogador(Jogador jogador, GameManager gestorJogo) {
        if (anularComFerramenta(jogador)) {
            return this.nome + " anulado por " + anulacoes.get(id);
        }

        gestorJogo.skipTurns(jogador, 3);
        return "Caiu em " + nome;
    }
}
