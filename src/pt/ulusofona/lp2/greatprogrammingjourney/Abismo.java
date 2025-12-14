package pt.ulusofona.lp2.greatprogrammingjourney;

public abstract class Abismo {
    private int id;
    private String titulo;

    public Abismo(int id, String titulo) {
        this.id = id;
        this.titulo = titulo;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    abstract void aplicarEfeito(Jogador jogador);

}
