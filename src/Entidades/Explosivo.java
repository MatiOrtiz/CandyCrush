package Entidades;

import Logica.CronometroExplosivo;
import Logica.Juego;

public class Explosivo extends Entidad {
    private int seg;
    private CronometroExplosivo tiempo;

    public Explosivo(int posX, int posY, int s, Juego j) {
        this.posX= posX;
        this.posY= posY;
        color= "Explosivo";
        seg= s;
        tiempo= new CronometroExplosivo(seg, j, this);
        setPuntaje(150);
    }

    
    public void iniciar() {
    	tiempo.iniciar();
        tiempo.notificarTiempoAExplosivo(seg);
    }
    
    public String getNombreImagen() {
		return "Explosivo";
	}
    
    public void actualizarTiempo(String strTiempo) {
        miGrafica.actualizarTiempo(strTiempo);
    }

    public void setTiempo(int seg) {
        this.seg= seg;
        tiempo= new CronometroExplosivo(seg, miTablero.getMiJuego(), this);
    }

    public boolean puedeIntercambiar(Entidad e) {
    	return false;
    }

    public boolean puedeMoverse() {
    	return false;
    }

    public void romperse() {
    	notificarDetonacion();
    	tiempo.pausar();
    	miTablero.incrementarPuntaje(puntaje);
    }

    public boolean machea(Entidad e) { 
    	return false;
    }

    public boolean puedeRomper(Caramelo c) {
    	return false;
    }

    public boolean puedeRomper(Gelatina g) { 
    	return false;
    }

    public void romperDetonable() {
        miTablero.romperEntidad(posX, posY);
    }

    public boolean bloqueaCaida() { 
    	return true;
    }

    public boolean puedeOcuparse() {
    	return false;
    }

    public boolean puedeCaer() { 
    	return false;
    }

	//TODO
	public boolean intercambiar(Entidad e) {
		return false;
	}

    @Override
    public void ocuparse(Entidad e) {

    }

    @Override
    public void ocupar(EntidadVacia v) {

    }

    @Override
    public void ocupar(Gelatina g) {

    }

    @Override
	public boolean intercambiarCon(Gelatina g) {
		return false;
	}

	@Override
	public boolean intercambiarCon(Caramelo c) {
		return false;
	}

	@Override
	public boolean intercambiarCon(EntidadVacia v) {
		return false;
	}

	@Override
	public boolean intercambiarCon(Glaseado g) {
		return false;
	}

	@Override
	public boolean intercambiarCon(Explosivo t) {
		return false;
	}


	public void explotar() {
		miTablero.getMiJuego().explotarBomba();
	}
	
}
