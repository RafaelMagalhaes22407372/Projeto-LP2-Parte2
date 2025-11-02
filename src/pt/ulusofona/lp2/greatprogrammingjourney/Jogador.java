package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.ArrayList;
import java.util.Arrays;

public class Jogador {
    int id;
    String corAvatar;
    String nome;
    String linguagensFavoritas;
    int posicaoAtual = 1;
    String estaEmJogo = "Em Jogo";


    public Jogador(int id, String nome, String linguagensFavoritas, String corAvatar) {
        this.id = id;
        this.nome = nome;
        this.linguagensFavoritas = linguagensFavoritas;
        this.corAvatar = corAvatar; // 3
    }


    String formatarJogador() {
        String[] linguagens = linguagensFavoritas.split(";");
        for (int i = 0; i < linguagens.length; i++) {
            linguagens[i] = linguagens[i].trim();
        }

        Arrays.sort(linguagens);

        linguagensFavoritas = String.join("; ", linguagens);

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

    String getLinguagensFavoritas() {
        return linguagensFavoritas;
    }

    int getPosicaoAtual(){
        return posicaoAtual;
    }

    void setPosicaoAtual(int novaPosicao) {
        posicaoAtual = novaPosicao;
    }
}
