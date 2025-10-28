package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.ArrayList;

public class Jogador {
    int id;
    String corAvatar;
    String nome;
    String linguagensFavoritas;
    String posicaoAtual;
    Boolean estaEmJogo;

    public Jogador(int id, String nome, String linguagensFavoritas, String corAvatar) {
        this.id = id;
        this.nome = nome;
        this.linguagensFavoritas = linguagensFavoritas;
        this.corAvatar = corAvatar;
    }


    String formatarJogador() {
        return id + " | " + nome + " | " + posicaoAtual + " | " + linguagensFavoritas + " | " + estaEmJogo;
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
