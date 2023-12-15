package Logica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("serial")
public class RankingJugadores implements Serializable {
    private List<Jugador> listaJugadores;
    public static final int MAX_SIZE = 5;

    public RankingJugadores() {
        listaJugadores= new ArrayList<Jugador>();
    }

    public void addJugador(Jugador jugador) {
    	int cantElementos = listaJugadores.size();
    	if(cantElementos < 6) {
    		listaJugadores.add(jugador);
    	}
    	else {
    		listaJugadores.remove(cantElementos-1);
    		listaJugadores.add(jugador);
    		Collections.sort(listaJugadores, Collections.reverseOrder());
    	}
    }

    public void actualizarRanking() {
    	Collections.sort(listaJugadores, Collections.reverseOrder());
    }
    
	public int getSize() {
		return listaJugadores.size();
	}

    public List<Jugador> getListaJugadores() { return listaJugadores; }

    public Jugador getJugadorEnPos(int posActual) {
		return listaJugadores.get(posActual - 1);
	}

}
