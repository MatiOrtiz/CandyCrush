package Animaciones;

import javax.swing.JLabel;

import Entidades.Entidad;
import Grafica.EntidadGrafica;

public class AnimadorIntercambio extends Animacion{
	protected Entidad e1,e2;
	protected static final int cantidadSteps = 30;
	
	public AnimadorIntercambio(Entidad e1, Entidad e2, ManagerAnimaciones m) {
		this.e1 = e1;
		this.e2 = e2;
		prioridad = 1;
		manager = m;
	}
	public void run() {
		JLabel lbl1 = e1.getEntidadGrafica().getLabel();
		JLabel lbl2 = e2.getEntidadGrafica().getLabel();
		int x1 = lbl1.getX();
		int x2 = lbl2.getX();
		int y1 = lbl1.getY();
		int y2 = lbl2.getY();
		
		//Guardo cuantos pixeles se debe mover cada label en cada direccion
		int stepX1 = (x2-x1)/cantidadSteps;
		int stepX2 = -stepX1;
		int stepY1 = (y2-y1)/cantidadSteps;
		int stepY2 = -stepY1;
		
		for(int i = 0; i < cantidadSteps; i++) {
			lbl1.setBounds(lbl1.getX()+stepX1, lbl1.getY()+stepY1, lbl1.getWidth(), lbl1.getHeight());
			lbl2.setBounds(lbl2.getX()+stepX2, lbl2.getY()+stepY2, lbl2.getWidth(), lbl2.getHeight());
			
			try {
				sleep(10);
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		lbl1.setBounds(x2, y2, lbl1.getWidth(), lbl1.getHeight());
		lbl2.setBounds(x1, y1, lbl2.getWidth(), lbl2.getHeight());
		notificarFinAnimacion();
	}
	@Override
	public EntidadGrafica[] getEntidades() {
		return null;
	}
}
