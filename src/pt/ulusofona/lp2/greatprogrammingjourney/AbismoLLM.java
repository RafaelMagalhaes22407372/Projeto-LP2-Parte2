package pt.ulusofona.lp2.greatprogrammingjourney;

public class AbismoLLM extends AbismoPai{

    public AbismoLLM(int id, String nome, int posicao) {
        super(id, nome, posicao);
    }

    @Override
    public String aplicaJogador(Jogador jogador, GameManager gestorJogo) {
        int turnoAtual = gestorJogo.getContadorTurnos();
        int posicaoAnterior = jogador.getUltimaPosicao();
        int posicaoAtual = jogador.getPosicao();
        int avanco = posicaoAtual - posicaoAnterior;
        int posicaoAposAvanco = jogador.getPosicao() + avanco;
        int turnosDoJogador = gestorJogo.getTurnosPorJogador().getOrDefault(jogador.getId(), 0);

        if (turnosDoJogador > 3) {
            gestorJogo.setPlayerPosition(jogador, posicaoAposAvanco);
            return "Caiu no " + nome + " mas já tem experiência! Avança tantas casas como a do último movimento";
        }

        if (anularComFerramenta(jogador)) {
            return this.nome + " anulado por " + anulacoes.get(id);
        }

        gestorJogo.setPlayerPosition(jogador, posicaoAnterior);
        return "Caiu em " + nome;
    }
}
