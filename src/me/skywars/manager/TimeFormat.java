package me.skywars.manager;

public class TimeFormat {
	
	public static String getTimerFormat(int timer) {
		int a = timer / 60, b = timer % 60;
		String c = null;
		String d = null;
		if (a >= 10) {
			c = "" + a;
		} else {
			c = "0" + a;
		}
		if (b >= 10) {
			d = "" + b;
		} else {
			d = "0" + b;
		}
		return c + ":" + d;
	}
	
	public static String getTimerChat(int timer) {
		int minutos = timer / 60,
				segundos = timer % 60;
		String mensagem = "";
		String mMsg = "";
		String sMsg = "";
		if (minutos > 0 && segundos == 0) {
			mMsg = minutos == 1 ? " minuto" : " minutos";
			mensagem = minutos + mMsg;
		} else if (minutos == 0 && segundos > 0) {
			sMsg = segundos == 1 ? " segundo" : " segundos";
			mensagem = segundos + sMsg;
		} else if (minutos > 0 && segundos > 0) {
			mMsg = minutos == 1 ? " minuto" : " minutos";
			sMsg = segundos == 1 ? " segundo" : " segundos";
			mensagem = minutos + mMsg + " e " + segundos + sMsg;
		}
		return mensagem;
	}

}
