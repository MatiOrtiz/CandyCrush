package Logica;

public class SiguienteNivel implements ManejadorSolicitudesFinalAnimaciones {
	private Juego juego;
	public SiguienteNivel(Juego j) {
		juego = j;
	}
	@Override
	public void solicitarSiguienteNivel() {
	}

	@Override
	public void solicitarReiniciarTablero() {
	}

	@Override
	public void solicitarFinalizarJuego() {
	}

	@Override
	public void ejecutarSolicitud() {
		juego.aumentarNivel();
		juego.setManejadorSolicitudes(new EnEspera(juego));
	}

}
