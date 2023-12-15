package Animaciones;

import javax.swing.JLabel;

import Entidades.Entidad;
import Grafica.EntidadGrafica;

public class AnimadorCaida extends Animacion{
	protected Entidad e1,e2;
	protected int x,y;
	protected static final int cantidadSteps = 30;
	
	public AnimadorCaida(Entidad e1, int x, int y, ManagerAnimaciones m) {
		this.e1 = e1;
		prioridad = 4;
		this.x = x;
		this.y = y;
		manager = m;
	}
	public void run() {
		JLabel lbl1 = e1.getEntidadGrafica().getLabel();
		int x1 = lbl1.getX();
		int y1 = lbl1.getY();
		
		//Guardo cuantos pixeles se debe mover cada label en cada direccion
		int stepX1 = (x-x1)/cantidadSteps;
		int stepY1 = (y-y1)/cantidadSteps;
		for(int i = 0; i < cantidadSteps; i++) {
			lbl1.setBounds(lbl1.getX()+stepX1, lbl1.getY()+stepY1, lbl1.getWidth(), lbl1.getHeight());
			
			
			try {
				sleep(5);
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		lbl1.setBounds(x, y, lbl1.getWidth(), lbl1.getHeight());
		notificarFinAnimacion();
	}

	public EntidadGrafica[] getEntidades() {
		return null;
	}
}
