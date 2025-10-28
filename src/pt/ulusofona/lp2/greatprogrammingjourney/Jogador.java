package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.ArrayList;

public class Jogador {
    int id;
    int corAvatar;
    String nome;
    ArrayList<String> linguagensFavoritas;
    Boolean estaEmJogo;

    public Jogador(int id, int corAvatar) {
        this.id = id;
        this.corAvatar = corAvatar;
    }


}
