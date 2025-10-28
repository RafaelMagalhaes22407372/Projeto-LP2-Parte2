package pt.ulusofona.lp2.greatprogrammingjourney;


import java.util.ArrayList;
import java.util.HashSet;

public class Tabuleiro {

    int boardSize;
    String[][] jogadores;


    public Tabuleiro(String[][] jogadores) {
        this.jogadores = jogadores;
    }

    boolean verificarIdsValidosERepetidos(String[][] playerInfo) {
        // ArrayListq que guarda os ids dos jogadores para depois verificar se há repetidos
        ArrayList<Integer> idsJogadores = new ArrayList<>();

        // Ve se há numeros maiores que 2 ou 4
        for (int i = 0; i < playerInfo.length; i++) {
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

}
