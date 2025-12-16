package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.HashMap;

public class Abismo extends AbismoOuFerramenta{


    public Abismo(int id, String nome, int posicaon) {
        super(id, nome, posicaon);
    }

    @Override
    public String get() {
        return "Abismo";
    }

    @Override
    public String aplicaJogador(Jogador jogador, GameManager gameManager) {
        HashMap<Integer, String> anulacoes = new HashMap<>();
        anulacoes.put(0, "IDE");
        anulacoes.put(1, "Testes Unitários");
        anulacoes.put(2, "Tratamento de Excepções");
        anulacoes.put(3, "Tratamento de Excepções");
        anulacoes.put(4, "Ajuda Do Professor");
        anulacoes.put(5, "Herança");
        anulacoes.put(6, "Programação Funcional");
        anulacoes.put(7, "Ajuda Do Professor");
        anulacoes.put(8, "Programação Funcional");
        anulacoes.put(9, "IDE");

        String ferramentaNecessaria = anulacoes.get(id);


        if (ferramentaNecessaria != null && jogador.getFerramentas().contains(ferramentaNecessaria)) {
            jogador.getFerramentas().remove(ferramentaNecessaria);
            return this.nome + " anulado por " + ferramentaNecessaria;
        }


        int novaPosicao = jogador.getPosicaoAtual();

        switch (id) {
            case 0: // Syntax Error
                novaPosicao = Math.max(1, jogador.getPosicaoAtual() - 1);
                break;

            case 1: // Logic Error → Recua N casas = floor(dado / 2) E perde 1 turno
                int lastRoll = gameManager.getUltimoDadoJogado();
                int recuo = (int) Math.floor(lastRoll / 2.0);

                novaPosicao = Math.max(1, jogador.getPosicaoAtual() - recuo);
                // REMOVIDO: gm.skipTurns(p, 1); // Não perdem turno
                break;

            case 2:
                novaPosicao = Math.max(1, jogador.getPosicaoAtual() - 2);
                break;

            case 3: // FileNotFound → Recua 3 casas
                novaPosicao = Math.max(1, jogador.getPosicaoAtual() - 3);
                break;

            case 4: // Crash → volta à primeira casa
                novaPosicao = 1;
                break;

            case 5: // Código Duplicado → recua para a posição anterior E perde 1 turno
                novaPosicao = jogador.getUltimaPosicao();

                break;

            case 6: // Efeitos Secundários → recua para a posição de 2 movimentos atrás E perde 1 turno
                novaPosicao = jogador.getPenultimaPosicao();

                break;

            case 7: // BSOD → perde imediatamente o jogo
                gameManager.eliminatePlayer(jogador);
                return "Caiu em " + nome;

            case 8: // Ciclo Infinito → perde 3 turnos
                gameManager.skipTurns(jogador, 3);
                break;

            case 9: // Segmentation Fault → Se >= 2 jogadores, todos recuam 3 casas
                novaPosicao = Math.max(1, jogador.getPosicaoAtual() - 3);
                break;
        }


        if (novaPosicao != jogador.getPosicaoAtual()) {
            gameManager.setPlayerPosition(jogador, novaPosicao);
        }

        return "Caiu em " + nome;
    }

}
