package pt.ulusofona.lp2.greatprogrammingjourney;

public class AbismoException extends Abismo{
    public AbismoException() {
        super(2, "Exception");
    }

    @Override
    void aplicarEfeito(Jogador jogador) {
        int recuarPosicao = jogador.getPosicaoAtual() - 2;
        jogador.setPosicaoAtual(recuarPosicao);
    }
}
