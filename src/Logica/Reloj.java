package Logica;

import java.util.Timer;
import java.util.TimerTask;

public class Reloj {

    protected Timer timer;
    protected TimerTask timerTask;
    protected boolean enUso;
    protected int min, seg, minInicial, segInicial;
    protected Juego juego;

    /**
	 * @param minLim La parte de los minutos del l�mite que tendr� el timer.
	 * @param segLim La parte de los segundos del l�mite que tendr� el timer.
	 */
    public Reloj(int minLim, int segLim, Juego j) {
        minInicial = minLim;
        segInicial = segLim;
        min = minInicial;
    	seg = segInicial;
        while(seg>59) {
            seg-=60;
            min++;
        }
    	juego = j;
        timer = new Timer();
        enUso = false;
        timerTask = new TimerTask() {
            @Override
            public void run() {
                if(enUso) {
                	if (seg > 0 || min > 0) {
                        if (seg > 0) {
                            seg--;
                        } else {
                            seg = 59;
                            min--;
                        }
                    }
                    notificarTiempoAJuego(min,seg);
                    if(seg==0 && min==0)
                        cancel();
                }
            };
        };
    }

    public void notificarTiempoAJuego(int minutos, int segundos) {
        int minDecenas = minutos/10;
        int minUnidad = minutos % 10;
        int segDecenas = segundos/10;
        int segUnidad = segundos % 10;
        String strTiempo = minDecenas+minUnidad+":"+segDecenas+segUnidad;
        juego.notificarTiempo(strTiempo);
        if(minutos == 0 && segundos == 0) {
            enUso = false;
        	juego.restarVida();
        }
    }

    public void setLimite(int min, int seg) {
    	this.min = min;
    	this.seg = seg;
    }

    public void iniciar() {
    	enUso = true;
        timer.schedule(timerTask, 0, 1000);
    }
    
    public void pausar() {
    	enUso = false;
    }
    
    /**
     * Cancela la ejecuci�n de la TimerTask anterior si es necesario, y asigna una
     * nueva para poder iniciar nuevamente.
     */
    public void reset() {
    	timerTask.cancel();
    	enUso = false;
    	min = minInicial;
    	seg = segInicial;
    	while(seg>59) {
            seg-=60;
            min++;
        }
    	timerTask = new TimerTask() {
            @Override
            public void run() {
                if(enUso) {
                	if (seg > 0 || min > 0) {
                        if (seg > 0) {
                            seg--;
                        } else {
                            seg = 59;
                            min--;
                        }
                    }
                    notificarTiempoAJuego(min,seg);
                    if(seg==0 && min==0)
                        cancel();
                }
            };
        };
    }

    public boolean isEnUso() {
        return enUso;
    }

}
