package br.com.goodmann.contabilidadeapi.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	private static final String complete = "dd/MM/yyyy";
	private static final String reduced = "dd/MM/yy";

	private SimpleDateFormat sdf = new SimpleDateFormat();

	/**
	 * Parse string to date using pattern dd/MM/yyyy
	 * 
	 * @param sDate
	 * @return
	 * @throws ParseException
	 */
	public Date parseToDate(String sDate) throws ParseException {
		this.sdf.applyPattern(complete);
		return this.sdf.parse(sDate);
	}

	/**
	 * Parse string to date using pattern dd/MM/yy
	 * 
	 * @param sDate
	 * @return
	 * @throws ParseException
	 */
	public Date parseToDateReduced(String sDate) throws ParseException {
		this.sdf.applyPattern(reduced);
		return this.sdf.parse(sDate);
	}
}
