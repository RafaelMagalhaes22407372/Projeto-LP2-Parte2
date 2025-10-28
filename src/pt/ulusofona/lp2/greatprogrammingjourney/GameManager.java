package pt.ulusofona.lp2.greatprogrammingjourney;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

public class GameManager {
    String[][] jogadores = new String[6][4];


    public GameManager() {
    }

    public boolean createInitialBoard(String[][] playerInfo, int worldSize) {
        return false;
    }

    public String getImagePng(int nrSquare) {
        return "";
    }

    public String[] getProgrammerInfo(int id) {
        String[] jogador = new String[5];

        for (int i = 0; i < jogadores.length; i++) {
            if (Integer.parseInt(jogadores[i][0]) == id) {
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
        return "";
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
