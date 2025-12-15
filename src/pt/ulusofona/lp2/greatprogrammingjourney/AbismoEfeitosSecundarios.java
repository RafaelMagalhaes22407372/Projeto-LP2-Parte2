package pt.ulusofona.lp2.greatprogrammingjourney;

public class AbismoEfeitosSecundarios extends Abismo{
    public AbismoEfeitosSecundarios() {
        super(6, "Efeitos Secundarios");
    }

    @Override
    void aplicarEfeito(Jogador jogador) {
        int posicaoAtual = jogador.getPosicaoAtual();
        int posicaoAnterior;

        if (posicaoAtual >= 1) {
            posicaoAnterior = jogador.getPosicaoNTurnosAtras(2);
            jogador.setPosicaoAtual(posicaoAnterior);
        }
    }
}
