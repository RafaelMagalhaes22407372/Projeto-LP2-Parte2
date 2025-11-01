package pt.ulusofona.lp2.greatprogrammingjourney;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class GameManager {
    Jogador[] players = new Jogador[4];
    int turno = 1;
    int indiceJogadorAtual = -1;
    int tamanhoFinalTabuleiro;

    public GameManager() {
    }

    public boolean createInitialBoard(String[][] playerInfo, int worldSize) {
        Tabuleiro tabuleiro = new Tabuleiro(playerInfo, worldSize);
        if ((worldSize >= playerInfo.length * 2)
                && tabuleiro.verificarCores(playerInfo)
                && tabuleiro.verificarNomesValidos(playerInfo)
                && tabuleiro.verificarIdsValidosERepetidos(playerInfo)) {
            players = playerInfo;
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
        String[] idNaPosicao = new String[1];
        Boolean primeiroId = true;
        String idsOrganizados = "";
        if (position < 1 || position > tamanhoFinalTabuleiro) {
            return null;
        }

        for (Jogador jogador : players) {
            if (jogador.posicaoAtual == position) {
                if (primeiroId) {
                    idsOrganizados += "" + jogador;
                    primeiroId = false;
                } else {
                    idsOrganizados += "," + jogador.id;
                }
            }
        }

        idNaPosicao[0] = idsOrganizados;
        return idNaPosicao;
    }

    public int getCurrentPlayerID() {
        if (players == null || players.length == 0) {
            return -1;
        }

        // Criar um novo array com os mesmos jogadores
        Jogador[] ordenados = new Jogador[players.length];
        for (int i = 0; i < players.length; i++) {
            ordenados[i] = players[i];
        }

        // Ordenar os jogadores por ID (ordem crescente)
        java.util.Arrays.sort(ordenados, Comparator.comparingInt(Jogador::getId));

        // começa com o menor ID
        if (indiceJogadorAtual == -1) {
            indiceJogadorAtual = 0;
            return ordenados[0].getId();
        }

        // Passar ao próximo jogador (ordem circular)
        indiceJogadorAtual++;
        if (indiceJogadorAtual >= ordenados.length) {
            indiceJogadorAtual = 0;
        }

        // Retornar o ID do jogador atual
        return ordenados[indiceJogadorAtual].getId();
    }


    public boolean moveCurrentPlayer(int nrSpaces) {
        // Validação do número de casas
        if (nrSpaces < 1 || nrSpaces > 6) {
            return false;
        }

        // Obter o ID do jogador atual
        int idAtual = getCurrentPlayerID();

        // Encontrar o jogador correspondente ao ID atual
        Jogador jogadorAtual = null;
        for (Jogador jogador : players) {
            if (jogador.getId() == idAtual) {
                jogadorAtual = jogador;
                break;
            }
        }

        if (jogadorAtual == null) {
            return false;
        }

        // Mover o jogador
        int novaPosicao = jogadorAtual.getPosicaoAtual() + nrSpaces;
        jogadorAtual.setPosicaoAtual(novaPosicao);

        turno++;

        return true;
    }


    public boolean gameIsOver() {
        Tabuleiro tabuleiro = new Tabuleiro(players, tamanhoFinalTabuleiro);

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
