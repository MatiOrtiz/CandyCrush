package Grafica;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import Logica.Jugador;
import Logica.RankingJugadores;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class VentanaRankingJugadores extends JFrame {
    private RankingJugadores rankingJugadores;

	public VentanaRankingJugadores(RankingJugadores ranking) {
		super();
		rankingJugadores = ranking;
		setBounds(100,100,422,300);
		initialize();
	}

	public VentanaRankingJugadores() {
		super();
		initialize();
	}

	private void initialize() {
		setBounds(100, 100, 279, 273);
		getContentPane().setLayout(null);
		Jugador jugadorActual;
		
		JLabel lblMejoresJugadores = new JLabel("MEJORES JUGADORES");
		lblMejoresJugadores.setHorizontalAlignment(SwingConstants.CENTER);
		lblMejoresJugadores.setBounds(10, 27, 243, 14);
		getContentPane().add(lblMejoresJugadores);

		rankingJugadores.actualizarRanking();
		
		for(int posActual = 1; posActual <= rankingJugadores.getSize() && posActual <= RankingJugadores.MAX_SIZE; posActual++) {
			jugadorActual = rankingJugadores.getJugadorEnPos(posActual);
			
			JLabel lbl = new JLabel(jugadorActual.getScore()+": "+jugadorActual.getNombre());
			lbl.setBounds(86, 52+(32*posActual), 140, 14);
			getContentPane().add(lbl);
		}
	}
    
}
