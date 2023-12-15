package Logica;

public class ReiniciarTablero implements ManejadorSolicitudesFinalAnimaciones {
	private Juego juego;
	public ReiniciarTablero(Juego j) {
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
		juego.setManejadorSolicitudes(new FinalizarJuego(juego));
	}

	@Override
	public void ejecutarSolicitud() {
		juego.resetearNivel();
		juego.setManejadorSolicitudes(new EnEspera(juego));
	}

}
