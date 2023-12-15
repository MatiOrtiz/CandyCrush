package Animaciones;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import Grafica.Gui;

import Entidades.Entidad;

public class ManagerAnimaciones {
	private Gui gui;
	private List<List<PriorityQueue<Animacion>>> listaDeCiclos;
	private boolean caenCaramelos;
	
	public ManagerAnimaciones(Gui g) {
		listaDeCiclos = new LinkedList<List<PriorityQueue<Animacion>>>();
		caenCaramelos = false;
		gui = g;
	}
	
	public void animarIntercambio(Entidad e1, Entidad e2) {
		PriorityQueue<Animacion> q = new PriorityQueue<>();
		List<PriorityQueue<Animacion>> l = new LinkedList<PriorityQueue<Animacion>>();
		q.add(new AnimadorIntercambio(e1,e2, this));
		l.add(q);
		listaDeCiclos.add(l);
		if(listaDeCiclos.size() == 1) {
			q.peek().start();
		}
	}
	
	public void animarRomper(Entidad e) {
		List<PriorityQueue<Animacion>> ultimoCiclo = listaDeCiclos.get(listaDeCiclos.size()-1);
		if(caenCaramelos) {
			PriorityQueue<Animacion> q = new PriorityQueue<>();
			q.add(new AnimadorRomper(e,this));
			ultimoCiclo.add(q);
			caenCaramelos = false;
		}else {
			ultimoCiclo.get(ultimoCiclo.size()-1).add(new AnimadorRomper(e,this));
		}
	}
	
	public void animarCaida(Entidad e1, Entidad e2) {
		caenCaramelos = true;
		List<PriorityQueue<Animacion>> ultimoCiclo = listaDeCiclos.get(listaDeCiclos.size()-1);
		int x = e2.getY()*75;
		int y = e2.getX()*75;
		ultimoCiclo.get(ultimoCiclo.size()-1).add(new AnimadorCaida(e1,x,y,this));
	}
	
	public void notificacionFinAnimacion(Animacion a) {
		List<PriorityQueue<Animacion>> primerCiclo = listaDeCiclos.get(0);
		PriorityQueue<Animacion> primerMiniCiclo = primerCiclo.get(0);
		primerMiniCiclo.poll();
		if(!primerMiniCiclo.isEmpty()) {
			primerMiniCiclo.peek().start();
		}else {
			primerCiclo.remove(0);
			if(!primerCiclo.isEmpty()) {
				primerCiclo.get(0).peek().start();
			}else {
				listaDeCiclos.remove(0);
				if(!listaDeCiclos.isEmpty()) {
					listaDeCiclos.get(0).get(0).peek().start();
				}else {
					gui.noficarAnimacionesTerminadas();
				}
			}
		}
	}
	public boolean hayAnimacionesEnCurso() {
		return !listaDeCiclos.isEmpty();
	}
}
