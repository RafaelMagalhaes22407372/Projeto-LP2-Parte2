package pt.ulusofona.lp2.greatprogrammingjourney;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

public class GameManager {
    String[][] jogadores = new String[4][6];
    int turno = 1;
    int indiceJogadorAtual = -1;
    int tamanhoFinalTabuleiro;

    public GameManager() {
    }

    public boolean createInitialBoard(String[][] playerInfo, int worldSize) {
        Tabuleiro tabuleiro = new Tabuleiro(playerInfo, worldSize);
        if ((worldSize >= playerInfo.length * 2) && tabuleiro.verificarCores(playerInfo) && tabuleiro.verificarNomesValidos(playerInfo) && tabuleiro.verificarIdsValidosERepetidos(playerInfo)) {
            for (int i = 0; i < jogadores.length; i++) {
                for (int j = 0; j < jogadores[i].length; j++) {
                    jogadores[i][j] = playerInfo[i][j];
                    if (j == 4) {
                        jogadores[i][j] = "1";
                    }
                    if (j == 5) {
                        jogadores[i][j] = "Em Jogo";
                    }
                }
            }
            tamanhoFinalTabuleiro = worldSize;
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
        //Registar o menor id no inicio do jogo
        int menorId = 0;

        //Se estivermos no inicio do jogo (turno 1)
        if (turno == 1) {
            //Percorre os jogadores para saber o menor id
            for (int i = 0; i < jogadores.length; i++) {
                if (Integer.parseInt(jogadores[i][0]) > menorId) {
                    menorId = Integer.parseInt(jogadores[i][0]);
                    indiceJogadorAtual = i;
                }
            }
            //Retorna o menor id no inicio do jogo
            return menorId;
        } else {
            return Integer.parseInt(jogadores[indiceJogadorAtual][0]);
        }
    }

    public boolean moveCurrentPlayer(int nrSpaces) {
        int posição_destino = 0;
        if (nrSpaces < 1 || nrSpaces > 6) {
            return false;
        }

        if (indiceJogadorAtual == -1) {
            getCurrentPlayerID();
        } else {
            indiceJogadorAtual++;
            if (indiceJogadorAtual >= jogadores.length) {
                indiceJogadorAtual = 0;
            }
        }

        posição_destino = Integer.parseInt(jogadores[indiceJogadorAtual][4]) + nrSpaces;
        return true;
    }

    public boolean gameIsOver() {
        Tabuleiro tabuleiro = new Tabuleiro(jogadores, tamanhoFinalTabuleiro);

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
