package Grafica;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.List;

import javax.swing.*;

import Animaciones.ManagerAnimaciones;
import Entidades.Entidad;
import Logica.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Gui {
	//Atributos
	private int pxFilas;
	private int pxColumnas;
	private Juego juego;
	private AbstractFactory fabricaDeGraficas;
	private ManagerAnimaciones manager;
	private JFrame frmCandyCrush;
	private JPanel panelTablero;
	private JPanel panelEstadisticas;
	private JLabel lblNumNivel;
	private JLabel lblMovimientos;
	private JLabel lblVidas;
	private JLabel lblTiempo;
	private JLabel lblDescObjetivo;
	private JLabel lblContObjetivo;
	private JLabel lblEstrategiasMatch;
	private JLabel lblPuntaje;
	private JButton btnDominioMinimalista;
	private JButton btnDominioOriginal;
	public static Properties configuration;
	private JTextField txtIngreseSuNombre;
	private static RankingJugadores rankingJugadores;
	private static VentanaRankingJugadores ventanaRanking;

	private JLabel lblImgFondo;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui window = new Gui();
					window.frmCandyCrush.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		cargarConfiguracion();
		
		rankingJugadores = new RankingJugadores();
		try {
			FileInputStream fileInputStream = new FileInputStream(Gui.configuration.getProperty("file"));
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			rankingJugadores= (RankingJugadores) objectInputStream.readObject();
			objectInputStream.close();
		}
		catch(FileNotFoundException e){
		
		}
		catch (IOException | ClassNotFoundException exception){
			exception.printStackTrace();
		}
	}

	
	/**
	 * Create the application.
	 */
	public Gui() {
		initialize();
	}

	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCandyCrush = new JFrame();
		frmCandyCrush.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if(juego != null) {
					juego.guardarDatos();
				}
			}
		});
		frmCandyCrush.setResizable(false);
		frmCandyCrush.setTitle("Candy Crush");
		frmCandyCrush.setSize(1366,768);
		frmCandyCrush.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCandyCrush.getContentPane().setLayout(null);
		frmCandyCrush.getContentPane().setVisible(true);
		agregarBotonesDominio();
	}

	private void agregarBotonesDominio() {
		btnDominioMinimalista = new JButton("Minimalista");
		btnDominioMinimalista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fabricaDeGraficas = new ConcreteFactoryMinimalista();
				iniciarJuego();
				notificarNombreJugador(txtIngreseSuNombre.getText());
			}
		});
		btnDominioMinimalista.setBounds(745, 550, 140, 70);
		frmCandyCrush.getContentPane().add(btnDominioMinimalista);
		
		btnDominioOriginal = new JButton("Original");
		btnDominioOriginal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fabricaDeGraficas = new ConcreteFactoryOriginal();
				iniciarJuego();
				notificarNombreJugador(txtIngreseSuNombre.getText());
			}
		});
		btnDominioOriginal.setBounds(450, 550, 140, 70);
		frmCandyCrush.getContentPane().add(btnDominioOriginal);
		
		txtIngreseSuNombre = new JTextField();
		txtIngreseSuNombre.setText("Ingrese su nombre");
		txtIngreseSuNombre.setToolTipText("Ingrese su nombre");
		txtIngreseSuNombre.setHorizontalAlignment(SwingConstants.CENTER);
		txtIngreseSuNombre.setBounds(450, 400, 435, 50);
		frmCandyCrush.getContentPane().add(txtIngreseSuNombre);
		agregarFondo("Fondo");
	}

	public void notificarNombreJugador(String nombre) {
		juego.guardarJugador(nombre);
	}

	public void agregarFondo(String nombreImg) {
		ImageIcon imgFondoJuego= new ImageIcon(Gui.class.getResource("/ImagenesInterfaz/"+nombreImg+".jpg"));
		lblImgFondo = new JLabel();
		lblImgFondo.setIcon(imgFondoJuego);
		lblImgFondo.setBounds(0, 0, 1366, 746);
		frmCandyCrush.getContentPane().add(lblImgFondo);
	}

	public void quitarFondo(){
		frmCandyCrush.remove(lblImgFondo);
	}

	public void pedirImgFondoDeJuego() {
		agregarFondo("FondoDeJuego");
	}

	private void iniciarJuego() {
		frmCandyCrush.remove(btnDominioMinimalista);
		frmCandyCrush.remove(btnDominioOriginal);
		frmCandyCrush.remove(txtIngreseSuNombre);
		frmCandyCrush.remove(lblImgFondo);
		
		frmCandyCrush.requestFocus();
		
		panelTablero = new JPanel();
        panelTablero.setLayout(null);
        frmCandyCrush.getContentPane().add(panelTablero);
        pxFilas = EntidadGrafica.alto;
        pxColumnas = EntidadGrafica.ancho;
        panelTablero.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	
		juego = new Juego(this);
		int filas = juego.getTablero().getFilas();
		int columnas = juego.getTablero().getColumnas();
		int anchoPanel = EntidadGrafica.ancho*columnas;
		int altoPanel = EntidadGrafica.alto*filas;
		panelTablero.setBounds(frmCandyCrush.getWidth()/2 - (anchoPanel)/2 , 125, altoPanel, anchoPanel);
		agregarOyentesMover();
		agregarOyentesIntercambiar();
		agregarEstadisticasJuego();
		manager = new ManagerAnimaciones(this);

		agregarFondo("FondoDeJuego");
	}

	public void notificacionQuitarTablero(String mensaje) {
		mostrarMensaje(mensaje);
		quitarFondo();
		eliminarTablero();
		pedirImgFondoDeJuego();
	}

	public void actualizarGraficaResetearNivel() {
		juego.resetearReloj();
		actualizarMov();
		actualizarContObj();
		actualizarVida();
	}

	public void actualizarGraficaAumentarNivel() {
		juego.resetearReloj();
		actualizarMov();
		actualizarVida();
		actualizarLvl();
		actualizarEstrategias();
	}


	private void agregarOyentesMover() {
		frmCandyCrush.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					juego.moverCursor(Juego.IZQUIERDA);
					break;
				case KeyEvent.VK_RIGHT:
					juego.moverCursor(Juego.DERECHA);
					break;
				case KeyEvent.VK_UP:
					juego.moverCursor(Juego.ARRIBA);
					break;
				case KeyEvent.VK_DOWN:
					juego.moverCursor(Juego.ABAJO);
					break;
				}
			}
		});
	}

	private void agregarOyentesIntercambiar() {
		frmCandyCrush.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyCode()) {
				case KeyEvent.VK_A:
					juego.intercambiar(Juego.IZQUIERDA);
					break;
				case KeyEvent.VK_D:
					juego.intercambiar(Juego.DERECHA); 
					break;
				case KeyEvent.VK_W:
					juego.intercambiar(Juego.ARRIBA);
					break;
				case KeyEvent.VK_S:
					juego.intercambiar(Juego.ABAJO);
					break;
				case KeyEvent.VK_R:
					juego.restarVida();
					break;
				}
			}
		});
	}
	

	public void agregarGrafica(Entidad e) {
		EntidadGrafica entGrafica = fabricaDeGraficas.crearGrafica(e);
		e.setEntidadGrafica(entGrafica);
		entGrafica.setGui(this);
		ImageIcon aux = entGrafica.getImagen();
        entGrafica.setGui(this);
        ImageIcon escalada = new ImageIcon(aux.getImage().getScaledInstance(pxColumnas, pxFilas, Image.SCALE_SMOOTH));
        JLabel lblImagen = new JLabel(escalada);
        entGrafica.setLabel(lblImagen);
        panelTablero.add(lblImagen);
        lblImagen.setBounds(e.getY()*pxFilas, e.getX()*pxColumnas, pxFilas, pxColumnas);
        entGrafica.actualizarImg();
        frmCandyCrush.repaint();
	}
	
	public void eliminarGrafica(Entidad e) {
		EntidadGrafica entGrafica = e.getEntidadGrafica();
		panelTablero.remove(entGrafica.getLabel());
		panelTablero.repaint();
	}
	
	public int getPxColumna() {
		return pxColumnas;
	}
	
	public int getPxFila() {
		return pxFilas;
	}
	
	public JPanel getPanelTablero() {
		return panelTablero;
	}

	public void agregarEstadisticasJuego() {
		panelEstadisticas = new JPanel();
		panelEstadisticas.setBackground(new Color(255, 192, 203));
		panelEstadisticas.setBounds(panelTablero.getX(),10,450,panelTablero.getY()-10);
		frmCandyCrush.getContentPane().add(panelEstadisticas);
		panelEstadisticas.setLayout(null);

		lblNumNivel = new JLabel("Nivel "+juego.getNumNivel());
		lblNumNivel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNumNivel.setBounds(10, 27, 60, 20);
		panelEstadisticas.add(lblNumNivel);
		
		lblTiempo = new JLabel("reloj nuevo");
		lblTiempo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTiempo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTiempo.setBounds(318, 27, 122, 20);

		
		lblMovimientos = new JLabel(""+juego.getNivelActual().getMovimientos());
		lblMovimientos.setHorizontalAlignment(SwingConstants.CENTER);
		lblMovimientos.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblMovimientos.setBounds(20, 62, 107, 20);
		panelEstadisticas.add(lblMovimientos);

		lblVidas = new JLabel(""+juego.getVidas());
		lblVidas.setHorizontalAlignment(SwingConstants.CENTER);
		lblVidas.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblVidas.setBounds(88, 27, 27, 20);
		panelEstadisticas.add(lblVidas);
		
		ImageIcon imagenObjetivo = fabricaDeGraficas.getImagen(juego.getNivelActual().getStringObjetivo());
		Image imgEscalada= imagenObjetivo.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		ImageIcon escalada = new ImageIcon(imgEscalada);
		lblDescObjetivo = new JLabel(escalada);
        lblDescObjetivo.setBounds(180, 75, 40, 40);
        panelEstadisticas.add(lblDescObjetivo);
		
		lblContObjetivo = new JLabel(""+juego.getNivelActual().getCantidadObjetivos());
        lblContObjetivo.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblContObjetivo.setBounds(230, 70, 122, 45);
        panelEstadisticas.add(lblContObjetivo);
        
        //lblEstrategiasMatch = new JLabel("<html>Estrategias de match habilitadas:<br>Ejemplo</html>");
        lblEstrategiasMatch = new JLabel();
        lblEstrategiasMatch.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblEstrategiasMatch.setText(getStringEstrategias());
        lblEstrategiasMatch.setBounds(260, 60, 200, 40);
        panelEstadisticas.add(lblEstrategiasMatch);
        
        lblPuntaje = new JLabel("Puntaje: 0");
        lblPuntaje.setHorizontalAlignment(SwingConstants.CENTER);
        lblPuntaje.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblPuntaje.setBounds(160, 29, 122, 20);
        panelEstadisticas.add(lblPuntaje);
        
        JButton btnRanking = new JButton("Ver ranking");
    	btnRanking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarRanking();
			}
		});
        lblPuntaje.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnRanking.setBounds(160, 0, 122, 31);
        panelEstadisticas.add(btnRanking);
		panelEstadisticas.add(lblTiempo);
		
		
		ImageIcon imagenVida = new ImageIcon(this.getClass().getResource("/ImagenesInterfaz/Vidas.png"));
		JLabel lblImgVidas = new JLabel(imagenVida);
		lblImgVidas.setBounds(71, 27, 20, 20);
		panelEstadisticas.add(lblImgVidas);
		juego.iniciarReloj();
	}

	public void eliminarTablero() {
		int filas = juego.getTablero().getFilas();
		int columnas = juego.getTablero().getColumnas();
		frmCandyCrush.remove(panelTablero);
		frmCandyCrush.repaint();
		panelTablero = new JPanel();
		panelTablero.setBounds(frmCandyCrush.getWidth()/2 - (EntidadGrafica.ancho)*filas/2 , 125, EntidadGrafica.alto * filas, EntidadGrafica.ancho * columnas);
        panelTablero.setLayout(null);
        panelTablero.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        frmCandyCrush.getContentPane().add(panelTablero);
	}
	
	public void actualizarTiempo(String strTiempo) {
		lblTiempo.setText(strTiempo);
	}

	public void actualizarMov() {
		lblMovimientos.setText(""+juego.getNivelActual().getMovimientos());
	}
	
	public void actualizarVida() {
		lblVidas.setText(""+juego.getVidas());
	}
	
	public void actualizarPuntaje(int p) {
		lblPuntaje.setText("Puntaje: "+p);
	}
	
	public void actualizarContObj() {
		int contador = juego.getNivelActual().getCantidadObjetivos()-juego.getNivelActual().getContadorObjetivo();
		if(contador >=0)
			lblContObjetivo.setText(""+contador);
		else
			lblContObjetivo.setText(""+0);
    }
	
	public void actualizarLvl() {
		lblNumNivel.setText("Nivel "+juego.getNumNivel());
		ImageIcon imagenObjetivo = fabricaDeGraficas.getImagen(juego.getNivelActual().getStringObjetivo());
		Image imgEscalada= imagenObjetivo.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		ImageIcon escalada = new ImageIcon(imgEscalada);
		lblDescObjetivo.setIcon(escalada);
		actualizarContObj();
	}
	
	public void actualizarEstrategias() {
        lblEstrategiasMatch.setText(getStringEstrategias());
    }
	
	public void mostrarMensaje(String msg) {
		JOptionPane.showMessageDialog(null, msg);
	}

	public void animarIntercambio(Entidad adyacente, Entidad e) {
		juego.bloquarControles();
		manager.animarIntercambio(adyacente, e);
	}

	public void animarCaida(Entidad arriba, Entidad e) {
		manager.animarCaida(arriba, e);
	}

	public void animarDetonacion(Entidad e) {
		manager.animarRomper(e);
	}


	public void noficarAnimacionesTerminadas() {
		juego.desbloquearControles();
		Entidad cursor = juego.getTablero().getCursor();
		cursor.cambiarFoco();
		juego.ejecutarAccionesPendientes();
	}
	
	public boolean hayAnimacionesEnCurso() {
		return manager.hayAnimacionesEnCurso();
	}
	
	public void finalizarJuego() {
		juego.guardarDatos();
		System.exit(0);
	}

	public void mostrarRanking() {
		ventanaRanking = new VentanaRankingJugadores(rankingJugadores);
		ventanaRanking.setVisible(true);
		frmCandyCrush.requestFocus();
	}
	
	
	private static void cargarConfiguracion() {
		Path dir = Paths.get(System.getProperty("java.class.path"));
		String rutaArchivo = dir.toString() + "/configuration.properties";
		try {
			InputStream input = new FileInputStream(rutaArchivo);
            Gui.configuration = new Properties();
            Gui.configuration.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
	}
	
	private String getStringEstrategias() {
		String descripcion = "<html>Estrategias de match habilitadas: <br>";
		List<VerificadorDeMatch> estrategias = juego.getEstraregiasDeMatch();
		int size = estrategias.size();
		for (VerificadorDeMatch vdm : estrategias) {
			descripcion += vdm.toString();
			//Si no es el ultimo elemento de la lista, agrega una coma al final
			if (estrategias.indexOf(vdm) != size-1) {
				descripcion += ", "; 
			}
			//Si es el ultimo elemento cierra el html para que funcione la linea nueva 
			else {
				descripcion += "</html>";
			}
		}
		return descripcion;
	}

	public RankingJugadores getRankingJugadores() { return rankingJugadores; }
}