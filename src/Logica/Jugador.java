package Logica;


import java.io.Serializable;

@SuppressWarnings("serial")
public class Jugador implements Comparable<Jugador>, Serializable {

    private String nombre;
    private Integer score;

    public Jugador(String nombre, int score) {
        this.nombre= nombre;
        this.score= score;
    }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getNombre() {
        return nombre;
    }
    
    public void setScore(int s) {
    	score = s;
    }

    public int getScore() {
        return score;
    }

    public int compareTo(Jugador arg0) {
        return score.compareTo(arg0.getScore());
    }

}
