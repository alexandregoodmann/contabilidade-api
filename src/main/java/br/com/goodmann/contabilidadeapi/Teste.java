package br.com.goodmann.contabilidadeapi;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Teste {

	public static void main(String[] args) {
		DateFormat df = new SimpleDateFormat("MM-dd-yyyy HH:mm");
		// df.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
		df.setTimeZone(TimeZone.getTimeZone("GMT"));
		System.out.println(df.format(new Date()));
	}
}
