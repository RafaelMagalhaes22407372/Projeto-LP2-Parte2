package pt.ulusofona.lp2.greatprogrammingjourney;

public class AbismoSegmentationFault extends Abismo{
    public AbismoSegmentationFault() {
        super(9, "Segmentation Fault");
    }

    @Override
    void aplicarEfeito(Jogador jogador) {
        int recuarPosicao = jogador.getPosicaoAtual() - 3;
        jogador.setPosicaoAtual(recuarPosicao);
    }
}
