package pt.ulusofona.lp2.greatprogrammingjourney;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class TestGameManager {

    @Test
    public void testGetGameResults_basicScenario() {
        GameManager gm = new GameManager();

        gm.tamanhoFinalTabuleiro = 10;
        gm.turno = 4;

        Jogador p1 = new Jogador(1, "Bruninho", "Common Lisp; PHP", "red");
        Jogador p2 = new Jogador(2, "Vencedor", "Java; Python", "blue");
        Jogador p3 = new Jogador(3, "Player3", "Clojure; D", "green");

        p1.setPosicaoAtual(2);
        p2.setPosicaoAtual(10);
        p3.setPosicaoAtual(5);

        gm.players = new ArrayList<>();
        gm.players.add(p1);
        gm.players.add(p2);
        gm.players.add(p3);

        ArrayList<String> results = gm.getGameResults();

        ArrayList<String> expected = new ArrayList<>();
        expected.add("THE GREAT PROGRAMMING JOURNEY");
        expected.add("");
        expected.add("NR. DE TURNOS");
        expected.add(gm.turno + "");
        expected.add("");
        expected.add("VENCEDOR");
        expected.add("Vencedor");
        expected.add("");
        expected.add("RESTANTES");
        expected.add("Player3 5");
        expected.add("Bruninho 2");

        assertEquals(expected.size(), results.size());

        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i), results.get(i));
        }
    }



    @Test
    public void testCreateTable() {
        GameManager manager = new GameManager();
        String[][] jogadores = new String[2][5];
        String[] jogador =  {"1", "Pedro", "Java", "Blue"};
        String[] jogador2 =  {"2", "Bruh2", "Java2", "Purple"};
        jogadores[0] =  jogador;
        jogadores[1] = jogador2;
        String[][] vazio = new String[2][3];
        String[] abismo = {"0", "0", "3"};
        String[] ferramenta = {"1", "4", "5"};
        vazio[0] = abismo;
        vazio[1] = ferramenta;

        System.out.println(manager.createInitialBoard(jogadores, 20, vazio));

        assertTrue(manager.createInitialBoard(jogadores, 6, vazio));
    }

    @Test
    public void testCriacaoAbismosEFeramentas() {
        GameManager manager = new GameManager();

        // Corrigido: Inicializar e preencher o array jogadores
        String[][] jogadores = new String[2][4]; // Corrigido para [2][4] se apenas precisar de 4 campos
        String[] jogador =  {"1", "Pedro", "Java", "Blue"};
        String[] jogador2 =  {"2", "Bruh2", "Java2", "Purple"};

        jogadores[0] = jogador; // Adicionar o primeiro jogador
        jogadores[1] = jogador2; // Adicionar o segundo jogador

        String[][] vazio = new String[2][3];
        String[] abismo = {"0", "0", "3"}; // Tipo=0 (Abismo), ID=0, Posição=3
        String[] ferramenta = {"1", "4", "5"}; // Tipo=1 (Ferramenta), ID=4, Posição=5
        vazio[0] = abismo;
        vazio[1] = ferramenta;

        manager.createInitialBoard(jogadores, 20, vazio);
        for (int i = 0; i < manager.abimosEFerramentas.size(); i++ ){
            System.out.printf(manager.abimosEFerramentas.get(i).toString());
        }
    }

    @Test
    public void testMoveCurrentPlayer() {
        GameManager gm = new GameManager();
        String[][] jogadores = new String[2][5];
        String[] jogador = {"1", "Alice", "Java; C++", "Green"};
        String[] jogador2 = {"2", "Bob", "Python; JS", "Blue"};
        jogadores[0] =  jogador;
        jogadores[1] = jogador2;

        String[][] vazio = new String[0][0];

        boolean created = gm.createInitialBoard(jogadores, 20, vazio);
        assertTrue(created);

        int idInicial = gm.getCurrentPlayerID();
        assertEquals(1, idInicial);

    }

    @Test
    public void testgetProgrammerInfo() {
        GameManager gm = new GameManager();
        String[][] jogadores = new String[2][5];
        String[] jogador = {"1", "Alice", "Java; C++", "Green"};
        String[] jogador2 = {"2", "Bob", "Python; JS", "Blue"};
        jogadores[0] =  jogador;
        jogadores[1] = jogador2;

        String[][] vazio = new String[0][0];

        boolean created = gm.createInitialBoard(jogadores, 20, vazio);
        assertTrue(created);
        assertEquals("1 | Alice | 1 | No tools | C++; Java | Em Jogo", gm.getProgrammerInfoAsStr(1));
    }
}
