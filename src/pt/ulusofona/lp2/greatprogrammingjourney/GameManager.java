package pt.ulusofona.lp2.greatprogrammingjourney;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class GameManager {
    ArrayList<Jogador> players;
    int turno = 1;
    int tamanhoFinalTabuleiro;

    public GameManager() {
    }

    public boolean createInitialBoard(String[][] playerInfo, int worldSize) {
        players = new ArrayList<>();
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

        // construir lista apenas com jogadores não nulos
        ArrayList<Jogador> lista = new ArrayList<>();
        for (Jogador jogador : players) {
            lista.add(jogador);
        }

        // ordenar por ID crescente
        lista.sort(Comparator.comparingInt(Jogador::getId));

        // calcular índice baseado no turno
        int ordIndex = turno - 1;
        while (ordIndex >= lista.size()) {
            ordIndex -= lista.size();
        }

        // devolver o ID correspondente
        return lista.get(ordIndex).getId();
    }




    public boolean moveCurrentPlayer(int nrSpaces) {
        // validação do número de casas
        if (nrSpaces < 1 || nrSpaces > 6) {
            return false;
        }

        // obter o ID do jogador actual
        int idAtual = getCurrentPlayerID();
        if (idAtual == -1) {
            return false;
        }

        // encontrar o jogador no ArrayList players
        Jogador jogadorAtual = null;
        int indiceOriginal = -1;
        for (int i = 0; i < players.size(); i++) {
            Jogador player = players.get(i);
            if (player != null && player.getId() == idAtual) {
                jogadorAtual = player;
                indiceOriginal = i;
                break;
            }
        }

        if (jogadorAtual == null) {
            return false;
        }

        // mover o jogador
        int novaPosicao = jogadorAtual.getPosicaoAtual() + nrSpaces;
        jogadorAtual.setPosicaoAtual(novaPosicao);

        // avançar o turno
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
        results.add("NR. DE TURNOS");
        results.add(turno + "");
        results.add("");
        results.add("VENCEDOR");

        String nomeVencedor = "";
        for (Jogador jogador : players) {
            if (jogador != null && jogador.getPosicaoAtual() == tamanhoFinalTabuleiro) {
                nomeVencedor = jogador.getNome();
            }
        }
        results.add(nomeVencedor);
        results.add("");
        results.add("RESTANTES");

        ArrayList<Jogador> restantes = new ArrayList<>();
        for (Jogador jogador : players) {
            if (jogador.getPosicaoAtual() == tamanhoFinalTabuleiro) {
                continue;
            }
            restantes.add(jogador);
        }

        restantes.sort(Comparator.comparingInt(Jogador::getPosicaoAtual).reversed());

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
