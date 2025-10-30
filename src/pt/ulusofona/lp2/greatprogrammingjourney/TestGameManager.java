package pt.ulusofona.lp2.greatprogrammingjourney;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestGameManager {
    @Test
    public void testGetProgrammerInfo(){
        GameManager manager = new GameManager();
        String[][] jogadores = new String[2][6];
        String[] jogador =  {"1", "Blue", "Pedro", "java", "2", "saS"};
        String[] jogador2 =  {"2", "Brown", "Da", "java"};
        jogadores[0] =  jogador;
        jogadores[1] = jogador2;
        Tabuleiro tabuleiro = new Tabuleiro(jogadores, 20);

        System.out.println(manager.createInitialBoard(jogadores, 20));
        manager.getProgrammerInfo(1);
        System.out.println(jogadores[0][5]);
        //System.out.println(Arrays.toString(manager.getProgrammerInfo(1)));

    }



    @Test
    public void testCreateTable() {
        GameManager manager = new GameManager();

        String[][] jogadores = new String[2][5];
        String[] jogador =  {"1", "Pedro", "Java", "Blue"};
        String[] jogador2 =  {"2", "Bruh2", "Java2", "Purple"};
        jogadores[0] =  jogador;
        jogadores[1] = jogador2;

        Tabuleiro tabuleiro = new Tabuleiro(jogadores, 10);


        assertTrue(manager.createInitialBoard(jogadores, 4));
    }

}
