package pt.ulusofona.lp2.greatprogrammingjourney;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class GameManager {
    String[][] playerInfo;
    ArrayList<Jogador> players;
    int turno = 1;
    int tamanhoFinalTabuleiro;
    Tabuleiro tabuleiro;
    private HashMap<String, Integer> turnosSaltados = new HashMap<>();
    int ultimoDadoJogado = 0;
    String playerAtual;
    EstadoDoJogo estadoDoJogo;

    public GameManager() {
    }

    public boolean createInitialBoard(String[][] playerInfo, int worldSize, String[][] abyssesAndTools) {
        players = new ArrayList<>();
        turno = 1;
        tamanhoFinalTabuleiro = worldSize;
        this.playerInfo = playerInfo;
        tabuleiro = new Tabuleiro(playerInfo, worldSize, abyssesAndTools);
        turnosSaltados.clear();
        if ((worldSize >= playerInfo.length * 2) && tabuleiro.verificaTabuleiroValido()) {
            for (int i = 0; i < playerInfo.length; i++) {
                int id = Integer.parseInt(playerInfo[i][0]);
                String nome = playerInfo[i][1];
                String linguagens = playerInfo[i][2];
                String cor = playerInfo[i][3];

                Jogador jogador = new Jogador(id, nome, linguagens, cor);
                jogador.setAlive(true);
                players.add(jogador);
                tabuleiro.adicionarJogador(jogador);

            }
            players.sort(Comparator.comparingInt(jogador -> Integer.parseInt(jogador.getId())));
            playerAtual = players.get(0).getId();
            estadoDoJogo = EstadoDoJogo.EM_CURSO;
            turno = 1;

            if (abyssesAndTools != null) {
                for (String[] linha : abyssesAndTools) {
                    if (linha == null || linha.length != 3) {
                        return false;
                    }
                    try {
                        int id = Integer.parseInt(linha[0]);
                        int tipo = Integer.parseInt(linha[1]);
                        int posicao = Integer.parseInt(linha[2]);

                        // Verifica se há 1 ferramenta ou 1 abismo na casa, caso exista return falso
                        if (tabuleiro.getAbismoOuFerramenta(posicao) != null) {
                            return false;
                        }

                        if (id == 0) {
                            String nome = getNomeAbismo(tipo);
                            if (nome == null) {
                                return false;
                            }
                            Abismo abismo = new Abismo(id, nome, posicao);
                            tabuleiro.posicionaAbismoEposicionaFerramenta(abismo);
                        } else if (id == 1) {
                            String nome = getNomeDaFerramenta(tipo);
                            if (nome == null) {
                                return false;
                            }
                            Ferramenta ferramenta = new Ferramenta(id, nome, posicao);
                            tabuleiro.posicionaAbismoEposicionaFerramenta(ferramenta);
                        } else {
                            return false;
                        }
                    }catch (NumberFormatException e) {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    public boolean createInitialBoard(String[][] playerInfo, int worldSize) {
        return createInitialBoard(playerInfo, worldSize, null);
    }

    private String getNomeAbismo(int id) {
        switch (id) {
            case 0:
                return "Erro de sintaxe";
            case 1:
                return "Erro de lógica";
            case 2:
                return "Exception";
            case 3:
                return "FileNotFoundException";
            case 4:
                return "Crash";
            case 5:
                return "Código duplicado";
            case 6:
                return "Efeitos secundários";
            case 7:
                return "Blue Screen of Death";
            case 8:
                return "Ciclo infinito";
            case 9:
                return "Segmentation fault";
            default:
                return null;
        }
    }

    private String getNomeDaFerramenta(int id) {
        switch (id) {
            case 0:
                return "Herança";
            case 1:
                return "Programação Funcional";
            case 2:
                return "Testes Unitários";
            case 3:
                return "Tratamento de Excepções";
            case 4:
                return "IDE";
            case 5:
                return "Ajuda Do Professor";
            default:
                return null;
        }
    }

    public String getImagePng(int nrSquare) {
        if (nrSquare == tamanhoFinalTabuleiro) {
            return "glory.png";
        }
        return null;
    }

    public String[] getProgrammerInfo(int id) {
        for (Jogador jogador : players) {
            if (Integer.parseInt(jogador.getId()) == id) {
                return jogador.formatarJogador();
            }
        }
        return null;
    }

    public String getProgrammersInfo() {
        List<String> info = new ArrayList<>();
        for (Jogador player : players) {
            if (player != null && player.getAlive()) {
                String nome = player.getNome();
                List<String> ferramentas = player.getFerramentas();
                String resultado;
                if (ferramentas.isEmpty()) {
                    resultado = "No tools";
                } else {
                    resultado = String.join(";", ferramentas);
                }
                info.add(nome + " : " + resultado);
            }
        }
        return String.join(" | ", info);
    }

    public String getProgrammerInfoAsStr(int id) {
        Jogador jogador = getJogadorId(String.valueOf(id));
        if (jogador == null) {
            return null;
        }
        String ferramentas = jogador.getFerramentas().isEmpty() ? "No tools"
                : String.join("; ", jogador.getFerramentas());

        String estado;
        if (!jogador.getAlive()) {
            estado = "Derrotado";
        } else if (turnosSaltados.containsKey(jogador.getId()) && turnosSaltados.get(jogador.getId()) > 0) {
            estado = "Preso";
        } else {
            estado = "Em Jogo";
        }

        return jogador.getId() + " | " + jogador.getNome() + " | " + jogador.getPosicaoAtual() + " | " + ferramentas
                + " | " + jogador.getLinguagensOrdenadas() + " | " + estado;
    }

    public String[] getSlotInfo(int position) {
        return tabuleiro.getSlotInfo(position);
    }

    public int getCurrentPlayerID() {
        return Integer.parseInt(playerAtual);
    }

    public boolean moveCurrentPlayer(int nrSpaces) {
        if (nrSpaces < 1 || nrSpaces > 6) {
            return false;
        }

        if (estadoDoJogo == EstadoDoJogo.ACABADO || tabuleiro == null || playerAtual == null) {
            return false;
        }


        Jogador jogadorAtual = getJogadorId(playerAtual);
        if (jogadorAtual == null || !jogadorAtual.isAlive) {
            proximoPlayer();
            return false;
        }

        Integer pular = turnosSaltados.getOrDefault(jogadorAtual.getId(), 0);
        if (pular > 0) {
            turnosSaltados.put(jogadorAtual.getId(), pular - 1);
            if (turnosSaltados.get(jogadorAtual.getId()) == 0) {
                turnosSaltados.remove(jogadorAtual.getId());
            }
            turno++;
            proximoPlayer();
            return false;
        }

        String linguagemProgramador = jogadorAtual.getLinguagensOrdenadas();
        if (linguagemProgramador != null && !linguagemProgramador.isEmpty()) {
            String primeira = linguagemProgramador;
            if (linguagemProgramador.contains(";")){
                primeira = linguagemProgramador.split(";")[0].trim();
            }
            if (primeira.equals("C") && nrSpaces >= 4) {
                return false;
            }

            if (primeira.equals("Assembly") && nrSpaces >= 3) {
                return false;
            }

            tabuleiro.moverJogadorNoTabueleiro(jogadorAtual, nrSpaces);
            ultimoDadoJogado = nrSpaces;
            turno++;

            if (tabuleiro.verificaFinal(jogadorAtual)) {
                estadoDoJogo = EstadoDoJogo.ACABADO;
                return true;
            }
            return true;

        }


    }

    public String reactToAbyssOrTool() {
    }

    public boolean gameIsOver() {
        return estadoDoJogo == EstadoDoJogo.ACABADO;
    }

    public ArrayList<String> getGameResults() {
        ArrayList<String> results = new ArrayList<>();
        results.add("THE GREAT PROGRAMMING JOURNEY");
        results.add("");
        results.add("NR. DE TURNOS");
        results.add((turno - 1) + "");
        results.add("");

        results.add("VENCEDOR");

        ArrayList<Jogador> vencedores = new ArrayList<>();
        for (Jogador jogador : players) {
            if (jogador != null && jogador.getPosicaoAtual() == tamanhoFinalTabuleiro) {
                vencedores.add(jogador);
            }
        }

        vencedores.sort(Comparator.comparing(Jogador::getNome));

        String nomeVencedor = vencedores.isEmpty() ? "" : vencedores.get(0).getNome();

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

        restantes.sort(Comparator
                .comparingInt(Jogador::getPosicaoAtual).reversed()
                .thenComparing(Jogador::getNome)
        );

        for (Jogador jogador : restantes) {
            results.add(jogador.getNome() + " " + jogador.getPosicaoAtual());
        }

        return results;
    }

    public void loadGame(File file) throws InvalidFileException, FileNotFoundException {
        if (!file.exists()) {
            throw new FileNotFoundException("Ficheiro não encontrado.");
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            // 1. Ler turno
            String linha = br.readLine();
            if (linha == null) {
                throw new InvalidFileException("Ficheiro inválido (turno).");
            }
            turno = Integer.parseInt(linha.trim());

            // 2. Ler tamanho tabuleiro
            linha = br.readLine();
            if (linha == null) {
                throw new InvalidFileException("Ficheiro inválido (tamanho).");
            }
            tamanhoFinalTabuleiro = Integer.parseInt(linha.trim());

            // 3. Jogadores
            linha = br.readLine();
            if (linha == null) {
                throw new InvalidFileException("Ficheiro inválido (num jogadores).");
            }
            int nJogadores = Integer.parseInt(linha.trim());

            players = new ArrayList<>();

            for (int i = 0; i < nJogadores; i++) {
                linha = br.readLine();
                if (linha == null) {
                    throw new InvalidFileException("Dados de jogador em falta.");
                }

                String[] p = linha.split("\\|");
                if (p.length < 5) {
                    throw new InvalidFileException("Formato de jogador inválido.");
                }

                int id = Integer.parseInt(p[0].trim());
                String nome = p[1].trim();
                int pos = Integer.parseInt(p[2].trim());
                String linguagens = p[3].trim();
                String estado = p[4].trim();

                Jogador j = new Jogador(id, nome, linguagens, "none"); // cor não é guardada
                j.setPosicaoAtual(pos);
                j.setAlive(estado.equals("Em Jogo"));

                players.add(j);
            }

            // 4. Abismos e Ferramentas
            linha = br.readLine();
            if (linha == null) {
                throw new InvalidFileException("Número de casas em falta.");
            }
            int nCasas = Integer.parseInt(linha.trim());

            abimosEFerramentas = new ArrayList<>();
            CriacaoAbismos criadorAbismos = new CriacaoAbismos();
            CriacaoFerramentas criadorFerramentas = new CriacaoFerramentas(); // se existir

            for (int i = 0; i < nCasas; i++) {
                linha = br.readLine();
                if (linha == null) {
                    throw new InvalidFileException("Casa em falta.");
                }

                String[] c = linha.split(";");
                if (c.length < 3) {
                    throw new InvalidFileException("Formato de casa inválido.");
                }

                int pos = Integer.parseInt(c[0].trim());
                int idAbismo = Integer.parseInt(c[1].trim());
                int idFerramenta = Integer.parseInt(c[2].trim());

                Casa casa = new Casa(pos);

                // Criar abismo se existir
                if (idAbismo != -1) {
                    casa.setAbismo(idAbismo, criadorAbismos);
                }

                // Criar ferramenta se existir
                if (idFerramenta != -1) {
                    casa.setFerramenta(idFerramenta, criadorFerramentas);
                }

                abimosEFerramentas.add(casa);
            }

        } catch (NumberFormatException e) {
            throw new InvalidFileException("Erro a interpretar números.");
        } catch (InvalidFileException e) {
            throw e;
        } catch (Exception e) {
            throw new InvalidFileException("Erro genérico: " + e.getMessage());
        }
    }

    public boolean saveGame(File file) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {

            bw.write(String.valueOf(turno));
            bw.newLine();

            bw.write(String.valueOf(tamanhoFinalTabuleiro));
            bw.newLine();

            // Jogadores
            bw.write(String.valueOf(players.size()));
            bw.newLine();

            for (Jogador jogador : players) {
                String linha = jogador.formatarJogador();
                bw.write(linha);
                bw.newLine();
            }

            // Casas
            bw.write(String.valueOf(abimosEFerramentas.size()));
            bw.newLine();

            for (Casa casa : abimosEFerramentas) {
                String linha =
                        casa.getPosicao() + ";" +
                                casa.getAbismo() + ";" +
                                casa.getFerramenta();
                bw.write(linha);
                bw.newLine();
            }

            return true;

        } catch (Exception e) {
            return false;
        }
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

    public int getUltimoDadoJogado() {
        return ultimoDadoJogado;
    }

    public Jogador getJogadorId(String id) {
        for (Jogador jogador : players) {
            if (jogador.getId().equals(id)) {
                return jogador;
            }
        }
        return null;
    }

    private void proximoPlayer() {
        if (players.isEmpty()) {
            playerAtual = null;
            return;
        }

        int indice = 0;
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getId().equals(playerAtual)) {
                indice = i;
                break;
            }
        }

        for (int i = 1; i <= players.size(); i++) {
            int nextIndex = (indice + i) % players.size();
            Jogador nextPlayer = players.get(nextIndex);

            if (nextPlayer.getAlive()) {
                playerAtual = nextPlayer.getId();
                return;
            }
        }
        playerAtual = null;
        estadoDoJogo = EstadoDoJogo.ACABADO;
    }
}
