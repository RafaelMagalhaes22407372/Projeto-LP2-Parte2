package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Jogador {
    String id;
    String corAvatar;
    String nome;
    String linguagensFavoritas;
    int posicao = 1;
    int ultimaPosicao = 1;
    int penultimaPosicao = 1;
    ArrayList<Integer> historicoPosicoes = new ArrayList<>();
    String estado = "Em Jogo";
    Boolean isAlive = true;
    List<String> ferramentas = new ArrayList<>();
    Boolean estaPreso = false;


    public Jogador(String id, String nome, String linguagensFavoritas, String corAvatar) {
        this.id = id;
        this.nome = nome;
        this.linguagensFavoritas = linguagensFavoritas;
        this.corAvatar = corAvatar; // 3
        this.historicoPosicoes.add(1);
    }

    String getId(){
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

    public Boolean getAlive() {
        return isAlive;
    }

    public void setAlive(Boolean alive) {
        this.isAlive = alive;
    }

    int getPosicaoAtual(){
        return posicao;
    }

    int getUltimaPosicao() {
        return ultimaPosicao;
    }

    int getPenultimaPosicao() {
        return penultimaPosicao;
    }

    public List<String> getFerramentas() {
        return ferramentas;
    }

    public String getEstado() {
        return estado;
    }

    public int getPosicaoNTurnosAtras(int n) {
        int size = historicoPosicoes.size();
        if (size > n) {
            return historicoPosicoes.get(size - n - 1);
        }
        return 1;
    }

    public void adicionarFerramenta(String ferramenta) {
        if (ferramenta != null && !ferramenta.contains(ferramenta)) {
            ferramentas.add(ferramenta);
        }
    }

    public void removerFerramenta(Ferramenta ferramenta) {
        this.ferramentas.remove(ferramenta);
    }

    void setPosicaoAtual(int novaPosicao) {
        this.penultimaPosicao = this.ultimaPosicao;
        this.ultimaPosicao = this.posicao;
        this.posicao = novaPosicao;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setEstaPreso(Boolean estaPreso) {
        this.estaPreso = estaPreso;
    }

    public void setUltimaPosicao(int ultimaPosicao) {
        this.ultimaPosicao = ultimaPosicao;
    }

    public void setPenultimaPosicao(int penultimaPosicao) {
        this.penultimaPosicao = penultimaPosicao;
    }

    String getLinguagensOrdenadas() {
        String[] linguagens = linguagensFavoritas.split(";");
        for (int i = 0; i < linguagens.length; i++) {
            linguagens[i] = linguagens[i].trim();
        }

        Arrays.sort(linguagens);
        return String.join("; ", linguagens);
    }

    String[] formatarJogador() {
        String ferramentasStr;
        if (ferramentas == null || ferramentas.isEmpty()) {
            ferramentasStr = "No tools";
        } else {
            ferramentasStr = ferramentas.toString();
        }

        if (!getAlive()) {
            setEstado("Derrotado");
        }

        return new String[] {id, nome, getLinguagensOrdenadas(), corAvatar, String.valueOf(posicao), ferramentasStr, estado};
    }


}
