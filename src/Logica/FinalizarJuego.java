package Logica;

public class FinalizarJuego implements ManejadorSolicitudesFinalAnimaciones {
	private Juego juego;
	public FinalizarJuego(Juego j) {
		juego = j;
	}

	@Override
	public void solicitarSiguienteNivel() {
		juego.setManejadorSolicitudes(new SiguienteNivel(juego));
	}

	@Override
	public void solicitarReiniciarTablero() {
	}

	@Override
	public void solicitarFinalizarJuego() {
	}

	@Override
	public void ejecutarSolicitud() {
		juego.setManejadorSolicitudes(new JuegoTerminado());
		juego.finalizarJuego();
	}

}
