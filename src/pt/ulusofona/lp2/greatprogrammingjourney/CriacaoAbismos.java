package pt.ulusofona.lp2.greatprogrammingjourney;

public class CriacaoAbismos {

    Abismo criarAbismo(int id) {
        return switch (id) {
            case 0 -> new AbismoErroSintaxe();
            case 1 -> new AbismoErroLogica();
            case 2 -> new AbismoException();
            case 3 -> new AbismoFileNotFoundException();
            case 4 -> new AbismoCrash();
            case 5 -> new AbismoCodigoDuplicado();
            case 6 -> new AbismoEfeitosSecundarios();
            case 7 -> new AbismoBlueScreenofDeath();
            case 8 -> new AbismoCicloInfinito();
            case 9 -> new AbismoSegmentationFault();
            default -> null;
        };
    }
}
