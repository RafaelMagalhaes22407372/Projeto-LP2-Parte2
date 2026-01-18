package pt.ulusofona.lp2.greatprogrammingjourney;

public class AbismoCicloInfinito extends AbismoPai {

    public AbismoCicloInfinito(int id, String nome, int posicao) {
        super(id, nome, posicao);
    }

    @Override
    public String aplicaJogador(Jogador jogador, GameManager gestorJogo) {

        int posicao = jogador.getPosicao();

        if (anularComFerramenta(jogador)) {
            return nome + " anulado por " + anulacoes.get(id);
        }

        Jogador presoAtual = gestorJogo.getJogadorPresoNaPosicao(posicao);

        if (presoAtual != null) {
            presoAtual.setPreso(false);
        }

        jogador.setPreso(true);

        return "Caiu em " + nome;
    }

}
