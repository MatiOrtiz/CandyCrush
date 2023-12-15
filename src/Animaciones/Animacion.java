package Animaciones;

import Grafica.EntidadGrafica;

public abstract class Animacion extends Thread implements Comparable<Animacion>{
	protected ManagerAnimaciones manager;
	protected int prioridad;
	public abstract EntidadGrafica[] getEntidades();
	protected void notificarFinAnimacion() {
		manager.notificacionFinAnimacion(this);
	}
	protected int getPrioridad() {
		return prioridad;
	}
	public int compareTo(Animacion o) {
		return prioridad - o.getPrioridad();
	}
}
