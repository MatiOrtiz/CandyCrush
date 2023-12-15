package Logica;

public class EnEspera implements ManejadorSolicitudesFinalAnimaciones {
	private Juego juego;
	public EnEspera(Juego j) {
		juego = j;
	}
	public void solicitarSiguienteNivel() {
		juego.setManejadorSolicitudes(new SiguienteNivel(juego));
	}

	@Override
	public void solicitarReiniciarTablero() {
		juego.setManejadorSolicitudes(new ReiniciarTablero(juego));
	}

	@Override
	public void solicitarFinalizarJuego() {
		juego.setManejadorSolicitudes(new FinalizarJuego(juego));
	}

	@Override
	public void ejecutarSolicitud() {
	}

}
