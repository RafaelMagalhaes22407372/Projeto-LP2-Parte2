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

public class GameManager {
    ArrayList<Jogador> players;
    int turno;
    int tamanhoFinalTabuleiro;
    ArrayList<Casa> abimosEFerramentas = new ArrayList<>();
    ArrayList<Integer> valoresDoDado;

    public GameManager() {
    }

    public boolean createInitialBoard(String[][] playerInfo, int worldSize, String[][] abyssesAndTools) {
        players = new ArrayList<>();
        valoresDoDado = new ArrayList<>();
        turno = 1;
        tamanhoFinalTabuleiro = worldSize;
        Tabuleiro tabuleiro = new Tabuleiro(playerInfo, worldSize, abyssesAndTools);
        if ((worldSize >= playerInfo.length * 2) && tabuleiro.verificaTabuleiroValido()) {
            for (int i = 0; i < playerInfo.length; i++) {
                int id = Integer.parseInt(playerInfo[i][0]);
                String nome = playerInfo[i][1];
                String linguagens = playerInfo[i][2];
                String cor = playerInfo[i][3];

                players.add(new Jogador(id, nome, linguagens, cor));
            }

            // Cria casas do tabuleiro
            abimosEFerramentas = new ArrayList<>();
            for (int i = 1; i <= worldSize; i++) {
                abimosEFerramentas.add(new Casa(i)); // criamos uma casa "normal"
            }

            CriacaoAbismos criadorAbismos = new CriacaoAbismos();
            CriacaoFerramentas criadorFerramentas = new CriacaoFerramentas();

            for (int i = 0; i < abyssesAndTools.length; i++) {
                int tipo = Integer.parseInt(abyssesAndTools[i][0]); // 0 = Abismo, 1 = Ferramenta
                int id = Integer.parseInt(abyssesAndTools[i][1]);
                int posicao = Integer.parseInt(abyssesAndTools[i][2]);

                Casa casa = abimosEFerramentas.get(posicao - 1);
                if (tipo == 0) {
                    casa.setAbismo(id, criadorAbismos);
                } else if (tipo == 1) {
                    casa.setFerramenta(id, criadorFerramentas);
                }
            }
            return true;
        }
        return false;
    }

    public boolean createInitialBoard(String[][] playerInfo, int worldSize) {
        players = new ArrayList<>();
        valoresDoDado = new ArrayList<>();
        turno = 1;
        tamanhoFinalTabuleiro = worldSize;
        Tabuleiro tabuleiro = new Tabuleiro(playerInfo, worldSize, null);
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
            return true;
        }
        return false;
    }

    public String getImagePng(int nrSquare) {
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

    public String getProgrammersInfo() {
        String info = "";
        for (Jogador player : players) {
            if (player != null && player.getAlive()) {
                info += player.getNome() + " : ";
                for (Ferramenta ferramenta : player.getFerramentas()) {
                    info += ferramenta + ";";
                }
            }
        }
        return info;
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
        // 1. Verificação de limites
        if (position < 1 || position > tamanhoFinalTabuleiro) {
            return null;
        }

        // Inicializa o array de resultado com 3 elementos, conforme exigido.
        String[] resultado = new String[3];

        // --- 2. Encontrar e Organizar os IDs dos Jogadores na Posição (Elemento [0]) ---

        StringBuilder idsBuilder = new StringBuilder();
        boolean primeiroId = true;

        for (Jogador jogador : players) {
            // Verifica se o jogador existe e se a sua posição atual corresponde à casa.
            // Assume-se que jogadores inativos não são considerados, mas não há verificação explícita do estado "Em Jogo"
            if (jogador != null && jogador.getPosicaoAtual() == position) {
                if (primeiroId) {
                    idsBuilder.append(jogador.getId());
                    primeiroId = false;
                } else {
                    idsBuilder.append(",").append(jogador.getId());
                }
            }
        }
        resultado[0] = idsBuilder.toString(); // [0]: IDs dos jogadores (ex: "1,3,5" ou "")

        // --- 3. Encontrar a Casa e seu Conteúdo (Abismo/Ferramenta) ---

        Casa casa = null;
        // Percorre a lista de casas (abimosEFerramentas) para encontrar a casa na 'position'
        for (Casa casa1 : abimosEFerramentas) {
            if (casa1 != null && casa1.getPosicao() == position) {
                casa = casa1;
                break;
            }
        }

        // --- 4. Determinar os Elementos [1] (Descrição) e [2] (Tipo/ID) ---

        if (casa == null || (!casa.temAbismo() && !casa.temFerramenta())) {
            // Casa não encontrada (erro de inicialização) ou Casa normal/vazia
            resultado[1] = ""; // [1]: Descrição vazia
            resultado[2] = ""; // [2]: Tipo/ID vazio

        } else if (casa.temAbismo()) {
            Abismo abismo = casa.getAbismo();
            // Assume-se que Abismo tem getTitulo() e getId()
            resultado[1] = abismo.getTitulo();
            resultado[2] = "A:" + abismo.getId();

        } else { // casa.temFerramenta()
            Ferramenta ferramenta = casa.getFerramenta();
            // Assume-se que Ferramenta tem getNome() e getId()
            resultado[1] = ferramenta.getNome();
            resultado[2] = "T:" + ferramenta.getId();
        }

        return resultado;
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

        //Adiciona o numero do dado para depois ir buscar com numero do turno
        valoresDoDado.add(nrSpaces);

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

        //Não se move caso esteja preso no abismo cicloInfinito
        if (jogadorAtual.estaPreso) {
            turno++;
            return false;
        }

        // mover o jogador
        int posTentativa = jogadorAtual.getPosicaoAtual() + nrSpaces;

        if (posTentativa > tamanhoFinalTabuleiro) {
            int excesso = posTentativa - tamanhoFinalTabuleiro;
            posTentativa = tamanhoFinalTabuleiro - excesso; // volta para trás
        }
        jogadorAtual.setPosicaoAtual(posTentativa);

        // avançar o turno
        turno++;

        return true;
    }

    public String reactToAbyssOrTool() {
        int idJogadorAtual = getCurrentPlayerID();
        if (idJogadorAtual == -1) {
            return null;
        }

        Jogador jogadorAtual = null;
        for (Jogador jogador : players) {
            if (jogador.getId() == idJogadorAtual &&
                    ("Em jogo".equals(jogador.getEstaEmJogo()))) {
                jogadorAtual = jogador;
                break;
            }
        }

        if (jogadorAtual == null) {
            return null;
        }

        int posicaoAtual = jogadorAtual.getPosicaoAtual();
        Casa casaAtual = null;

        for (Casa casa : abimosEFerramentas) {
            if (casa.getPosicao() == posicaoAtual) {
                casaAtual = casa;
                break;
            }
        }

        String mensagem = "";

        if (casaAtual != null && casaAtual.temFerramenta()) {
            Ferramenta ferramenta = casaAtual.getFerramenta();
            int tipoFerramenta = ferramenta.getId();

            boolean jaTemEsteTipo = false;
            for (Ferramenta ferramenta1 : jogadorAtual.getFerramentas()) {
                if (ferramenta1.getId() == tipoFerramenta) {
                    jaTemEsteTipo = true;
                    break;
                }
            }

            if (jaTemEsteTipo) {
                mensagem = "O jogador " + jogadorAtual.getNome() +
                        " já tem uma ferramenta do tipo " + tipoFerramenta +
                        ", não pode recolher outra";
            } else {
                jogadorAtual.adicionarFerramenta(ferramenta);
                mensagem = "O jogador " + jogadorAtual.getNome() +
                        " recolheu a ferramenta: " + ferramenta.getNome();
            }
        } else if (casaAtual != null && casaAtual.temAbismo()) {
            Abismo abismo = casaAtual.getAbismo();

            // Verificar se o jogador tem ferramenta para neutralizar este abismo
            boolean abismoNeutralizado = false;
            ArrayList<Ferramenta> ferramentasDoJogador = jogadorAtual.getFerramentas();

            for (Ferramenta ferramenta : ferramentasDoJogador) {
                if (ferramenta.podeNeutralizarAbismo(abismo.getId())) {
                    jogadorAtual.removerFerramenta(ferramenta);
                    mensagem = "O jogador " + jogadorAtual.getNome() +
                            " usou " + ferramenta.getNome() +
                            " para neutralizar " + abismo.getTitulo();
                    abismoNeutralizado = true;
                    break;
                }
            }

            // Se não neutralizou, aplicar efeito do abismo
            if (!abismoNeutralizado) {
                if (abismo.getId() == 1) {

                    int recuo = valoresDoDado.get(turno) / 2; // Arredondamento para baixo (floor)
                    int novaPosicao = jogadorAtual.getPosicaoAtual() - recuo;
                    if (novaPosicao < 1) {
                        novaPosicao = 1;
                    }

                    jogadorAtual.setPosicaoAtual(novaPosicao);

                    mensagem = "O jogador " + jogadorAtual.getNome() +
                            " caiu no abismo: " + abismo.getTitulo() +
                            ". Recuou " + recuo + " casa(s) para a posição " + novaPosicao;

                } else {
                    abismo.aplicarEfeito(jogadorAtual);
                    mensagem = "O jogador " + jogadorAtual.getNome() +
                            " caiu no abismo: " + abismo.getTitulo();
                }
            }
        } else {
            mensagem = "O jogador " + jogadorAtual.getNome() +
                    " não encontrou nada na casa " + posicaoAtual;
        }

        turno++;
        return mensagem;
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
        results.add((turno - 1) + "");
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
}
