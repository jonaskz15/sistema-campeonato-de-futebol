import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

class Clube {
    String nome;
    int pontos;
    int saldoGols;

    Clube(String nome) {
        this.nome = nome;
        this.pontos = 0;
        this.saldoGols = 0;
    }

    void ganhar(int pontosGanhos, int pontosSofridos) {
        this.pontos += 3;
        atualizarSaldoGols(pontosGanhos, pontosSofridos);
    }

    void empatar(int pontosGanhos, int pontosSofridos) {
        this.pontos += 1;
        atualizarSaldoGols(pontosGanhos, pontosSofridos);
    }

    void perder(int pontosGanhos, int pontosSofridos) {
        atualizarSaldoGols(pontosGanhos, pontosSofridos);
    }

    private void atualizarSaldoGols(int pontosGanhos, int pontosSofridos) {
        this.saldoGols += (pontosGanhos - pontosSofridos);
        if (this.saldoGols < 0) {
            this.saldoGols = 0; // Impede que o saldo de gols seja negativo
        }
    }
}

class Campeonato {
    List<Clube> clubes;

    Campeonato(List<Clube> clubes) {
        this.clubes = clubes;
    }

    void jogarCampeonato() {
        for (int i = 0; i < clubes.size(); i++) {
            for (int j = i + 1; j < clubes.size(); j++) {
                jogarPartida(clubes.get(i), clubes.get(j));
                jogarPartida(clubes.get(j), clubes.get(i));
            }
        }
    }

    private void jogarPartida(Clube time1, Clube time2) {
        Random random = new Random();
        int pontosTime1 = random.nextInt(6);
        int pontosTime2 = random.nextInt(6);

        System.out.println(time1.nome + " " + pontosTime1 + " x " + pontosTime2 + " " + time2.nome);

        if (pontosTime1 > pontosTime2) {
            time1.ganhar(pontosTime1, pontosTime2);
            time2.perder(pontosTime2, pontosTime1);
        } else if (pontosTime1 < pontosTime2) {
            time1.perder(pontosTime1, pontosTime2);
            time2.ganhar(pontosTime2, pontosTime1);
        } else {
            time1.empatar(pontosTime1, pontosTime2);
            time2.empatar(pontosTime2, pontosTime1);
        }
    }

    String getClassificacao() {
        Collections.sort(clubes, new Comparator<Clube>() {
            public int compare(Clube c1, Clube c2) {
                if (c1.pontos != c2.pontos) {
                    return c2.pontos - c1.pontos;
                } else {
                    return c2.saldoGols - c1.saldoGols;
                }
            }
        });

        StringBuilder classificacao = new StringBuilder();
        for (Clube clube : clubes) {
            classificacao.append(clube.nome)
                        .append(": ")
                        .append(clube.pontos)
                        .append(" pontos, Saldo de gols: ")
                        .append(clube.saldoGols)
                        .append("\n");
        }
        return classificacao.toString();
    }

    Clube getCampeao() {
        return clubes.get(0);
    }
}

public class Teste {
    public static void main(String[] args) {
        List<Clube> clubes = new ArrayList<>();
        clubes.add(new Clube("Flamengo"));
        clubes.add(new Clube("Atletico Mineiro"));
        clubes.add(new Clube("Fluminense"));

        Campeonato resultado = new Campeonato(clubes);
        resultado.jogarCampeonato();

        System.out.println("\nResultado Final:");
        System.out.println(resultado.getClassificacao());

        Clube campeao = resultado.getCampeao();
        System.out.println("Parabéns ao " + campeao.nome + ", o campeão do campeonato!");
    }
}
