package pt.ulusofona.lp2.greatprogrammingjourney;


import java.util.ArrayList;

public class Tabuleiro {

    int boardSize;
    String[][] jogadores;


    boolean verificaIdsValidosERepetidos(String[][] playerInfo) {
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
            for (int j = 1; j < idsJogadores.size(); j++) {
                if (num == idsJogadores.get(j)) {
                    return false;
                }
            }
        }
        return true;
    }

    boolean verificaNomesValidos(String[][] playerInfo) {

        for (int i = 0; i < playerInfo.length; i++) {
            int id = Integer.parseInt(playerInfo[i][0]);

        }
    }
}
