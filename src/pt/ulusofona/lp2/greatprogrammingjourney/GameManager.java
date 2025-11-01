package pt.ulusofona.lp2.greatprogrammingjourney;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

public class GameManager {
    String[][] jogadores = new String[4][6]; // Ter de mudar isto para o array de player e não ficar em string pois sendo que temos uma classe de jogadores fazemos um array de player's
    Jogador[] players = new Jogador[4];
    int turno = 1;
    int indiceJogadorAtual = -1;
    int tamanhoFinalTabuleiro;

    public GameManager() {
    }

    public boolean createInitialBoard(String[][] playerInfo, int worldSize) {
        Tabuleiro tabuleiro = new Tabuleiro(playerInfo, worldSize);
        if ((worldSize >= playerInfo.length * 2) && tabuleiro.verificarCores(playerInfo) && tabuleiro.verificarNomesValidos(playerInfo) && tabuleiro.verificarIdsValidosERepetidos(playerInfo)) {
            jogadores = playerInfo;
            tamanhoFinalTabuleiro = worldSize;
            for (int i = 0; i < playerInfo.length; i++) {
                int id = Integer.parseInt(playerInfo[i][0]);
                String nome = playerInfo[i][1];
                String linguagens = playerInfo[i][2];
                String cor = playerInfo[i][3];

                players[i] = new Jogador(id, nome, linguagens, cor);
            }
            return true;
        }
        return false;
    }

    public String getImagePng(int nrSquare) {
        return "";
    }

    public String[] getProgrammerInfo(int id) {
        for (Jogador player : players) {
            if (player != null && player.getId() == id) {
                String[] info = new String[4];
                info[0] = String.valueOf(player.getId());
                info[1] = player.getNome();
                info[2] = player.getLinguagensFavoritas();
                info[3] = player.getCorAvatar();
                return info;
            }
        }
        return null;
    }

    public String getProgrammerInfoAsStr(int id) {

        for (Jogador player : players) {
            if (player != null && player.getId() == id) {
                return player.formatarJogador();
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
        jogadores[indiceJogadorAtual][4] = posição_destino + "";
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
