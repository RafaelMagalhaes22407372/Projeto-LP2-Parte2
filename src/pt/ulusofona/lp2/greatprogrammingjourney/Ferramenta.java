package pt.ulusofona.lp2.greatprogrammingjourney;

public abstract class Ferramenta {
    protected int id;
    protected String nome;

    public Ferramenta(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public abstract void usar(Jogador jogador);
}
