package pt.ulusofona.lp2.greatprogrammingjourney;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

public class TestGameManager {
    @Test
    public void testCreateInitialBoard_TamanhoInvalido() {
        GameManager gm = new GameManager();
        String[][] jogadores = {
                {"1", "Alice", "Java", "Blue"},
                {"2", "Bob", "Python", "Green"}
        };

        // Tamanho menor que 2*n jogadores (2*2=4, precisa de pelo menos 4)
        assertFalse(gm.createInitialBoard(jogadores, 3, null));
    }

    @Test
    public void testCreateInitialBoard_NumeroJogadoresInvalido() {
        GameManager gm = new GameManager();
        String[][] jogadores = {
                {"1", "Alice", "Java", "Blue"}  // Apenas 1 jogador
        };

        assertFalse(gm.createInitialBoard(jogadores, 10, null));
    }

    @Test
    public void testMoveCurrentPlayer_MovimentoValido() {
        GameManager gm = new GameManager();
        String[][] jogadores = {
                {"1", "Alice", "Java", "Blue"},
                {"2", "Bob", "Python", "Green"}
        };

        assertTrue(gm.createInitialBoard(jogadores, 10, null));

        // Jogador 1 move 3 casas
        assertTrue(gm.moveCurrentPlayer(3));
        assertEquals("1", gm.jogadorAtual); // Deve passar para próximo jogador
    }

    @Test
    public void testMoveCurrentPlayer_MovimentoInvalido() {
        GameManager gm = new GameManager();
        String[][] jogadores = {
                {"1", "Alice", "C", "Blue"},  // Linguagem C não pode mover >=4
                {"2", "Bob", "Python", "Green"}
        };

        assertTrue(gm.createInitialBoard(jogadores, 10, null));

        // Tenta mover 4 casas com linguagem C (não permitido)
        assertFalse(gm.moveCurrentPlayer(4));
    }

    @Test
    public void testReactToAbyssOrTool_ComFerramenta() {
        GameManager gm = new GameManager();
        String[][] jogadores = {
                {"1", "Alice", "Java", "Blue"},
                {"2", "Bob", "Python", "Green"}
        };

        String[][] objetos = {
                {"1", "0", "3"}  // Ferramenta "Herança" na posição 3
        };

        assertTrue(gm.createInitialBoard(jogadores, 10, objetos));

        // Move jogador para posição com ferramenta
        gm.moveCurrentPlayer(2);
        String resultado = gm.reactToAbyssOrTool();
        assertNotNull(resultado);
        assertTrue(resultado.contains("Apanhou Ferramenta:"));
    }

    @Test
    public void testGetGameResults_JogoNaoTerminado() {
        GameManager gm = new GameManager();
        String[][] jogadores = {
                {"1", "Alice", "Java", "Blue"},
                {"2", "Bob", "Python", "Green"}
        };

        assertTrue(gm.createInitialBoard(jogadores, 10, null));

        ArrayList<String> resultados = gm.getGameResults();
        assertTrue(resultados.isEmpty());
    }

    @Test
    public void testSkipTurns() {
        GameManager gm = new GameManager();
        String[][] jogadores = {
                {"1", "Alice", "Java", "Blue"},
                {"2", "Bob", "Python", "Green"}
        };

        assertTrue(gm.createInitialBoard(jogadores, 10, null));

        Jogador alice = gm.getJogadorById("1");
        gm.skipTurns(alice, 2);

        // Alice deve pular seu turno
        gm.moveCurrentPlayer(3); // Bob move
        assertEquals("2", gm.jogadorAtual); // Ainda é Bob
    }

    @Test
    public void testEliminatePlayer() {
        GameManager gm = new GameManager();
        String[][] jogadores = {
                {"1", "Alice", "Java", "Blue"},
                {"2", "Bob", "Python", "Green"},
                {"3", "Charlie", "C++", "Purple"}
        };

        assertTrue(gm.createInitialBoard(jogadores, 10, null));

        Jogador bob = gm.getJogadorById("2");
        gm.eliminatePlayer(bob);

        assertFalse(bob.estaVivo());
        assertEquals("1", gm.jogadorAtual); // Continua com Alice
    }

    @Test
    public void testGetSlotInfo() {
        GameManager gm = new GameManager();
        String[][] jogadores = {
                {"1", "Alice", "Java", "Blue"},
                {"2", "Bob", "Python", "Green"}
        };

        String[][] objetos = {
                {"0", "2", "5"}  // Abismo Exception na posição 5
        };

        assertTrue(gm.createInitialBoard(jogadores, 10, objetos));

        String[] slotInfo = gm.getSlotInfo(5);
        assertEquals(3, slotInfo.length);
        assertEquals("Exception", slotInfo[1]); // Nome do abismo
        assertEquals("A:2", slotInfo[2]); // Tipo e ID
    }

    @Test
    public void testGetProgrammerInfoAsStr() {
        GameManager gm = new GameManager();
        String[][] jogadores = {
                {"1", "Alice", "Java;Python", "Blue"},
                {"2", "Bob", "C;Assembly", "Green"}
        };

        assertTrue(gm.createInitialBoard(jogadores, 10, null));

        String info = gm.getProgrammerInfoAsStr(1);
        assertNotNull(info);
        assertTrue(info.contains("Alice"));
        assertTrue(info.contains("Java; Python"));
        assertTrue(info.contains("Em Jogo"));
    }

    @Test
    public void testLoadAndSaveGame() {
        GameManager gm1 = new GameManager();
        String[][] jogadores = {
                {"1", "Alice", "Java", "Blue"},
                {"2", "Bob", "Python", "Green"}
        };

        String[][] objetos = {
                {"0", "3", "4"},
                {"1", "1", "6"}
        };

        assertTrue(gm1.createInitialBoard(jogadores, 10, objetos));

        // Move alguns jogadores
        gm1.moveCurrentPlayer(2);
        gm1.reactToAbyssOrTool();

        try {
            // Salva o jogo
            java.io.File tempFile = java.io.File.createTempFile("test_save", ".txt");
            assertTrue(gm1.saveGame(tempFile));

            // Carrega em outro GameManager
            GameManager gm2 = new GameManager();
            gm2.loadGame(tempFile);

            // Verifica se os dados são os mesmos
            assertEquals(gm1.tamanhoTabuleiro, gm2.tamanhoTabuleiro);
            assertEquals(gm1.contadorTurnos, gm2.contadorTurnos);
            assertEquals(gm1.jogadores.size(), gm2.jogadores.size());

            tempFile.delete();

        } catch (Exception e) {
            fail("Exceção lançada: " + e.getMessage());
        }
    }

    @Test
    public void testGetProgrammersInfo() {
        GameManager gm = new GameManager();
        String[][] jogadores = {
                {"1", "Alice", "Java", "Blue"},
                {"2", "Bob", "Python", "Green"}
        };

        assertTrue(gm.createInitialBoard(jogadores, 10, null));

        String info = gm.getProgrammersInfo();
        assertNotNull(info);
        assertTrue(info.contains("Alice"));
        assertTrue(info.contains("Bob"));
        assertTrue(info.contains("No tools"));
    }

    @Test
    public void testGameIsOver() {
        GameManager gm = new GameManager();
        String[][] jogadores = {
                {"1", "Alice", "Java", "Blue"},
                {"2", "Bob", "Python", "Green"}
        };

        assertTrue(gm.createInitialBoard(jogadores, 10, null));

        assertFalse(gm.gameIsOver());

        // Simula término do jogo
        gm.estadoJogo = EstadoJogo.TERMINADO;
        assertTrue(gm.gameIsOver());
    }
}