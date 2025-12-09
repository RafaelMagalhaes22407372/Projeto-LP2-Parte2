package pt.ulusofona.lp2.greatprogrammingjourney;

public abstract class Casa {
    protected int tipo;
    protected int id;
    protected int posicao;
    protected String[][] abyssesAndTools;
    protected int boardSize;


    public Casa(String[][] abyssesAndTools, int boardSize) {
        this.abyssesAndTools = abyssesAndTools;
        this.boardSize = boardSize;

    }

    boolean verificaTiposValidos() {
        for (int i = 0; i < abyssesAndTools.length; i++) {
            int temp = Integer.parseInt(abyssesAndTools[i][0]);
            if (temp != 0 && temp != 1) {
                return false;
            }
        }
        return true;
    }

    boolean  verificaIdsValidos() {
        for (int i = 0; i < abyssesAndTools.length; i++) {
            int temp = Integer.parseInt(abyssesAndTools[i][0]);
            if (Integer.parseInt(abyssesAndTools[i][0]) == 1) {
                if (temp < 0  || temp > 9) {
                    return false;
                }
            } else {
                if (temp < 0 || temp > 5) {
                    return false;
                }
            }

        }
        return true;
    }

    boolean verificaPosicoesInvalidas() {
        for (int i = 0; i < abyssesAndTools.length; i++) {
            if (Integer.parseInt(abyssesAndTools[i][2]) > boardSize || Integer.parseInt(abyssesAndTools[i][2]) < 0) {
                return false;
            }
        }
        return true;
    }

    // Metodo que define o efeito da casa
    public abstract void acao(Jogador jogador);
}

