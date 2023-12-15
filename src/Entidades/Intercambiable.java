package Entidades;

public interface Intercambiable {
	public boolean intercambiarCon(Gelatina g);
	
	public boolean intercambiarCon(Caramelo c);
	
	public boolean intercambiarCon(EntidadVacia v);
	
	public boolean intercambiarCon(Glaseado g);
	
	public boolean intercambiarCon(Explosivo t);
}
