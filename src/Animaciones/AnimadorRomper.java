package Animaciones;

import javax.swing.JLabel;

import Entidades.Entidad;
import Grafica.EntidadGrafica;

public class AnimadorRomper extends Animacion {
	protected Entidad entidad;
	protected int stepH,stepW;
	public AnimadorRomper(Entidad e,ManagerAnimaciones m) {
		entidad = e;
		prioridad = 3;
		manager = m;
	}
	public void run() {
		JLabel lbl = entidad.getEntidadGrafica().getLabel();
		stepW = lbl.getWidth()/15;
		stepH = lbl.getHeight()/15;
		for(int i = 0; i < 15; i++) {
			lbl.setSize(lbl.getWidth()-stepW, lbl.getHeight()-stepH);
			try {
				Thread.sleep(6);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		entidad.incrementarPuntaje();
		entidad.getEntidadGrafica().romperse();
		notificarFinAnimacion();
	}
	@Override
	public EntidadGrafica[] getEntidades() {
		return null;
	}

}
