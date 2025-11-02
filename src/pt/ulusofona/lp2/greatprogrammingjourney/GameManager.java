package pt.ulusofona.lp2.greatprogrammingjourney;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class GameManager {
    ArrayList<Jogador> players = new ArrayList<>();
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
            for (int i = 0; i < playerInfo.length; i++) {
                int id = Integer.parseInt(playerInfo[i][0]);
                String nome = playerInfo[i][1];
                String linguagens = playerInfo[i][2];
                String cor = playerInfo[i][3];

                players.add(new Jogador(id, nome, linguagens, cor));
            }
            tamanhoFinalTabuleiro = worldSize;
            return true;
        }
        return false;
    }

    public String getImagePng(int nrSquare) {
        if (nrSquare > 1 && nrSquare < tamanhoFinalTabuleiro) {
            return "blank.png";
        }
        if (nrSquare == tamanhoFinalTabuleiro) {
            return "glory.png";
        }
        return null;
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
        boolean primeiroId = true;
        StringBuilder idsOrganizados = new StringBuilder();
        if (position < 1 || position > tamanhoFinalTabuleiro) {
            return null;
        }

        for (Jogador jogador : players) {
            if (jogador.getPosicaoAtual() == position) {
                if (primeiroId) {
                    idsOrganizados.append(jogador.getId());
                    primeiroId = false;
                } else {
                    idsOrganizados.append(",").append(jogador.getId());
                }
            }
        }

        idNaPosicao[0] = idsOrganizados.toString();
        return idNaPosicao;
    }

    public int getCurrentPlayerID() {
        if (players == null || players.isEmpty()) {
            return -1;
        }

        // Criar um novo array com os mesmos jogadores
        Jogador[] ordenados = new Jogador[players.size()];
        for (int i = 0; i < players.size(); i++) {
            ordenados[i] = players.get(i);
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
        for (Jogador player : players) {
            if (player.posicaoAtual == tamanhoFinalTabuleiro) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<String> getGameResults() {
        ArrayList<String> results = new ArrayList<>();
        results.add("THE GREAT PROGRAMMING JOURNEY");
        results.add("");
        results.add(turno + "");
        results.add("VENCEDOR");

        String nomeVencedor = "";
        for (Jogador jogador : players) {
            if (jogador.getPosicaoAtual() == tamanhoFinalTabuleiro) {
                nomeVencedor = jogador.getNome();
            }
        }
        results.add(nomeVencedor);
        results.add("");
        results.add("RESTANTES");

        int count = 0;
        Jogador[] restantes = new Jogador[players.size()];
        for (Jogador jogador : players) {
            if (jogador.getPosicaoAtual() == tamanhoFinalTabuleiro) {
                continue;
            }
            restantes[count] = jogador;
            count++;
        }

        java.util.Arrays.sort(restantes, Comparator.comparingInt(Jogador::getPosicaoAtual).reversed());

        for (Jogador jogador : restantes) {
            results.add(jogador.getNome() + " " + jogador.getPosicaoAtual());
        }

        return results;
    }

    public JPanel getAuthorsPanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(300, 300));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("CRÉDITOS", SwingConstants.CENTER);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titulo);

        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel autor1 = new JLabel("Rafael Magalhães - a22407372");
        JLabel autor2 = new JLabel("Diogo Alves - a22407372");

        autor1.setAlignmentX(Component.CENTER_ALIGNMENT);
        autor2.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(autor1);
        panel.add(autor2);

        return panel;
    }

    public HashMap<String, String> customizeBoard() {
        return new HashMap<String, String>();
    }
}
