package pt.ulusofona.lp2.greatprogrammingjourney;

public abstract class Abismo {
    protected int id;

    public Abismo(int id, String nome) {
        this.id = id;
    }

    public int getId() {
        return id;
    }


    public abstract void aplicarEfeito(Jogador jogador);

}
