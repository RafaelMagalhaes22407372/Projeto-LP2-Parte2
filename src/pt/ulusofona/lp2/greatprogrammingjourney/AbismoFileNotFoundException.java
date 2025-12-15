package pt.ulusofona.lp2.greatprogrammingjourney;

public class AbismoFileNotFoundException extends Abismo{
    public AbismoFileNotFoundException() {
        super(3, "File Not Found Exception");
    }

    @Override
    void aplicarEfeito(Jogador jogador) {
        int recuarPosicao = jogador.getPosicaoAtual() - 3;
        jogador.setPosicaoAtual(recuarPosicao);
    }
}
