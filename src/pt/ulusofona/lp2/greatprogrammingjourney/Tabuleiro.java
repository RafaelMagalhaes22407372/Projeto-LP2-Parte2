package pt.ulusofona.lp2.greatprogrammingjourney;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Tabuleiro {

    int boardSize;
    String[][] jogadores;
    String[][] abyssesAndTools;
    ArrayList<String>[] tabuleiro;
    HashMap<String, Integer> positions = new HashMap<>();
    AbismoOuFerramenta[] abismoOuFerramentas;

    public Tabuleiro(String[][] jogadores, int boardSize, String[][] abyssesAndTools) {
        this.jogadores = jogadores;
        this.boardSize = boardSize;
        this.abyssesAndTools = abyssesAndTools;
    }

    boolean verificarIdsValidosERepetidos(String[][] playerInfo) {
        // ArrayListq que guarda os ids dos jogadores para depois verificar se há repetidos
        ArrayList<Integer> idsJogadores = new ArrayList<>();

        // Adiciona os IDs a um ArrayList para saber o tamanho de player's
        for (int i = 0; i < playerInfo.length; i++) {
            if (playerInfo[i][0] == null) {
                return false;
            }
            int id = Integer.parseInt(playerInfo[i][0]);
            idsJogadores.add(id);
        }

        if (idsJogadores.size() > 4 || idsJogadores.size() < 2) {
            return false;
        }

        // Ve se há numeros repetidos
        for (int i = 0; i < idsJogadores.size(); i++) {
            int num = idsJogadores.get(i);
            for (int j = i + 1; j < idsJogadores.size(); j++) {
                if (num == idsJogadores.get(j)) {
                    return false;
                }
            }
        }
        return true;
    }

    boolean verificarNomesValidos(String[][] playerInfo) {
        // Percorre o playerInfo e caso encontre algum nome invalido retorna false
        for (int i = 0; i < playerInfo.length; i++) {
            if (playerInfo[i][1] == null || playerInfo[i][1].trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    boolean verificarCores(String[][] playerInfo) {
        // Array das cores que são validas
        String[] coresDisponiveis = {"Purple", "Green", "Brown", "Blue"};

        // Guardas as cores para saber se há duplicados
        HashSet<String> coresUsadas = new HashSet<>();

        // Percorre o playerInfo para ir buscar a informação das cores
        for (int i = 0; i < playerInfo.length; i++) {
            if (playerInfo[i][3] == null) {
                return false;
            }
            String corJogador = playerInfo[i][3];
            boolean corValida = false;

            // Faz a comparação das cores com o player
            for (int j = 0; j < coresDisponiveis.length; j++) {
                if (corJogador.equals(coresDisponiveis[j])) {
                    corValida = true;
                }
            }

            // Caso a cor não exista, ou não seja valida
            if (!corValida) {
                return false;
            }

            // Verifica se a cor já está no HashSet
            if (!coresUsadas.add(corJogador)) {
                return false;
            }
        }
        return true;
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

    boolean verificaIdsValidos() {
        for (int i = 0; i < abyssesAndTools.length; i++) {
            int tipo = Integer.parseInt(abyssesAndTools[i][0]);
            int id = Integer.parseInt(abyssesAndTools[i][1]);
            if (tipo == 1) {
                if (id < 0 || id > 5) {
                    return false;
                }
            } else if (tipo == 0) {
                if (id < 0 || id > 9) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    boolean verificaPosicoesInvalidas() {
        for (int i = 0; i < abyssesAndTools.length; i++) {
            int pos = Integer.parseInt(abyssesAndTools[i][2]);
            if (pos < 0 || pos >= boardSize) {
                return false;
            }
        }
        return true;
    }

    boolean verificaTabuleiroValido() {
        return verificarCores(jogadores) && verificarNomesValidos(jogadores)
                && verificarIdsValidosERepetidos(jogadores) && verificaIdsValidos()
                && verificaTiposValidos() && verificaPosicoesInvalidas();
    }

    public void adicionarJogador(Jogador jogador) {
        tabuleiro[0].add(jogador.getId());
        positions.put(jogador.getId(), 1);
    }

    public AbismoOuFerramenta getAbismoOuFerramenta(int posicao) {
        if (posicao < 1 || posicao > boardSize) {
            return null;
        }
        return abismoOuFerramentas[posicao - 1];
    }

    public void posicionaAbismoEposicionaFerramenta(AbismoOuFerramenta abismoOuFerramenta) {
        this.abismoOuFerramentas[abismoOuFerramenta.getPosicaon() - 1] = abismoOuFerramenta;
    }

    public String[] getSlotInfo(int position) {
        if (tabuleiro == null || position < 1 || position > boardSize) {
            return new String[]{"", "", ""};
        }
        List<String> ids = tabuleiro[position - 1];
        String jogador = "";
        if (!ids.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < ids.size(); i++) {
                sb.append(ids.get(i));
                if (i < ids.size() - 1) {
                    sb.append(",");
                }
            }
            jogador = sb.toString();
        }

        AbismoOuFerramenta conjuntos = abismoOuFerramentas[position - 1];
        String nomeConjunto = "";
        String atributosDoConjunto = "";

        if (conjuntos != null) {
            nomeConjunto = conjuntos.getNome();
            String conjuntoId = String.valueOf(conjuntos.getId());
            if (conjuntos.getTipo().equals("Abyss")) {
                atributosDoConjunto = "A:" + conjuntoId;
            }else if (conjuntos.getTipo().equals("Tool")) {
                atributosDoConjunto = "T:" + conjuntoId;
            }
        }
        return new String[]{jogador, nomeConjunto, atributosDoConjunto};
    }

    public void moverJogadorNoTabueleiro(Jogador jogador, int nrSpaces) {
        int jogadorAtual = positions.get(jogador.getId());
        int posicaoAposMover = jogadorAtual + nrSpaces;

        if (posicaoAposMover > boardSize) {
            int excesso = posicaoAposMover - boardSize;
            posicaoAposMover = boardSize - excesso;
        }

        tabuleiro[jogadorAtual - 1].remove(jogador.getId());
        tabuleiro[posicaoAposMover - 1].add(jogador.getId());
        positions.put(jogador.getId(),posicaoAposMover);
        jogador.setPosicaoAtual(posicaoAposMover);
    }

    public boolean verificaFinal(Jogador jogador) {
        return positions.get(jogador.getId()) == boardSize;
    }
}
