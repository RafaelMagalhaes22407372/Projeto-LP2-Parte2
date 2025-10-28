package pt.ulusofona.lp2.greatprogrammingjourney;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

public class GameManager {
    String[][] jogadores = new String[4][6];


    public GameManager() {
    }

    public boolean createInitialBoard(String[][] playerInfo, int worldSize) {
        Tabuleiro tabuleiro = new Tabuleiro(playerInfo);
        if ((worldSize >= playerInfo.length * 2) && tabuleiro.verificarCores(playerInfo) && tabuleiro.verificarNomesValidos(playerInfo) && tabuleiro.verificarIdsValidosERepetidos(playerInfo)) {
            jogadores = playerInfo;
            return true;
        }
        return false;
    }

    public String getImagePng(int nrSquare) {
        return "";
    }

    public String[] getProgrammerInfo(int id) {
        String[] jogador = new String[5];

        for (int i = 0; i < jogadores.length; i++) {
            if (jogadores[i][0] != null && Integer.parseInt(jogadores[i][0]) == id) {
                // Copia as informações do jogador encontrado
                for (int j = 0; j < jogadores[i].length; j++) {
                    jogador[j] = jogadores[i][j];
                }
                return jogador; // devolve o jogador encontrado
            }
        }

        return null;
    }

    public String getProgrammerInfoAsStr(int id) {

        for (int i = 0; i < jogadores.length; i++) {
            if (jogadores[i][0] != null && Integer.parseInt(jogadores[i][0]) == id) {
                Jogador jogador = new Jogador(id, jogadores[i][1], jogadores[i][2], jogadores[i][3]);
                return jogador.formatarJogador();
            }
        }

        return null;
    }

    public String[] getSlotInfo(int position) {
        return new String[0];
    }

    public int getCurrentPlayerID() {
        return 0;
    }

    public boolean moveCurrentPlayer(int nrSpaces) {
        return false;
    }

    public boolean gameIsOver() {
        return false;
    }

    public ArrayList<String> getGameResults() {
        return new ArrayList<>();
    }

    public JPanel getAuthorsPanel() {
        return new JPanel();
    }

    public HashMap<String, String> customizeBoard() {
        return new HashMap<String, String>();
    }
}
