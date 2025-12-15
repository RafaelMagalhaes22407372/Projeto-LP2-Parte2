package pt.ulusofona.lp2.greatprogrammingjourney;

public class AbismoErroSintaxe extends Abismo{

    public AbismoErroSintaxe() {
        super(0, "Erro de sintaxe");
    }

    @Override
    void aplicarEfeito(Jogador jogador) {
        int recuarPosicao = jogador.getPosicaoAtual() - 1;
        jogador.setPosicaoAtual(recuarPosicao);
    }

}
