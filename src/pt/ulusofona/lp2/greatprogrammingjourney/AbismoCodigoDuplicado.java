package pt.ulusofona.lp2.greatprogrammingjourney;

public class AbismoCodigoDuplicado extends Abismo {
    public AbismoCodigoDuplicado() {
        super(5, "Codigo Duplicado");
    }

    @Override
    void aplicarEfeito(Jogador jogador) {
        int posicaoAtual = jogador.getPosicaoAtual();
        int posicaoAnterior;

        if (posicaoAtual >= 1) {
            posicaoAnterior = jogador.getPosicaoNTurnosAtras(1);
            jogador.setPosicaoAtual(posicaoAnterior);
        }
    }
}
