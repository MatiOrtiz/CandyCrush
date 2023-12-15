package Logica;

import Entidades.Explosivo;
import java.util.Timer;
import java.util.TimerTask;

public class CronometroExplosivo {

    protected Timer timer;
    protected TimerTask timerTask;
    protected boolean enUso;
    protected int min, seg, segInicial;
    protected Juego juego;
    protected Explosivo carameloExplosivo;

    public CronometroExplosivo(int segLim, Juego j, Explosivo e) {
        carameloExplosivo = e;
        segInicial = segLim;
        seg = segInicial;
        juego = j;
        timer = new Timer();
        enUso = false;
        timerTask = new TimerTask() {
            public void run() {
                if(enUso) {
                    if (seg > 0) {
                        if (seg > 0) {
                            seg--;
                        } else {
                            seg = 59;
                        }
                    }
                    notificarTiempoAExplosivo(seg);
                    if(seg==0)
                        cancel();
                }
            };
        };
    }

    public void notificarTiempoAExplosivo(int segundos) {
        String strTiempo = segundos+"";
        carameloExplosivo.actualizarTiempo(strTiempo);
        if(segundos == 0) {
            enUso = false;
            carameloExplosivo.explotar();
        }
    }

    public void iniciar() {
        enUso = true;
        timer.schedule(timerTask, 0, 1000);
    }

    public void pausar() {
        enUso = false;
    }



}
