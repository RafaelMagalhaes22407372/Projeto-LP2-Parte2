package pt.ulusofona.lp2.greatprogrammingjourney;

public abstract class Abismo {
    protected int id;

    public Abismo(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    abstract void aplicarEfeito(Jogador jogador);

}
