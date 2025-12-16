package pt.ulusofona.lp2.greatprogrammingjourney;

public class NomesDosPNG {

    public NomesDosPNG() {
    }

    public String getNomeFicheiroAbismo(int id) {
        switch (id) {
            case 0: return "syntax.png";
            case 1: return "logic.png";
            case 2: return "exception.png";
            case 3: return "file-not-found-exception.png";
            case 4: return "crash.png";
            case 5: return "duplicated-code.png";
            case 6: return "secondary-effects.png";
            case 7: return "bsod.png";
            case 8: return "infinite-loop.png";
            case 9: return "core-dumped.png";
            default: return null;
        }
    }

    public String getNomeFicheiroFerramenta(int id) {
        switch (id) {
            case 0: return "inheritance.png";
            case 1: return "functional.png";
            case 2: return "unit-test.png";
            case 3: return "catch.png";
            case 4: return "IDE.png";
            case 5: return "ajuda-professor.png";
            default: return null;
        }
    }
}
