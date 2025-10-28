package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.ArrayList;

public class Jogador {
    int id;
    String corAvatar;
    String nome;
    ArrayList<String> linguagensFavoritas;
    String posicaoAtual;
    Boolean estaEmJogo;

    public Jogador(int id, String corAvatar) {
        this.id = id;
        this.corAvatar = corAvatar;
    }

    int getId(){
        return id;
    }

    String getCorAvatar(){
        return corAvatar;
    }

    String getNome(){
        return nome;
    }

    String getPosicaoAtual(){
        return posicaoAtual;
    }
}
