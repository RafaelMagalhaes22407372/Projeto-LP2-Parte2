package pt.ulusofona.lp2.greatprogrammingjourney;

import org.junit.jupiter.api.Test;

public class TestGameManager {
    @Test
    public void testGetProgrammerInfo(){
        GameManager manager = new GameManager();
        String[][] jogadores = new String[2][5];
        String[] jogador =  {"1", "blue", "bruh", "java", "1"};
        String[] jogador2 =  {"2", "blue", "bruh", "java", "1"};
        jogadores[0] =  jogador;
        jogadores[1] = jogador2;
        manager.getProgrammerInfo(1);
    }
}
