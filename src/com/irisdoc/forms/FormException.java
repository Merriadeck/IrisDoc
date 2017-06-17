package com.irisdoc.forms;

/**
 * Exception lev�e en cas d'erreur sur un formulaire
 */
public class FormException extends RuntimeException {
	private static final long serialVersionUID = 771163995165004000L;
	

	public FormException (String message, Throwable reason) {
		super(message, reason);
	}
	
	public FormException (String message) {
		super(message);
	}
	
	public FormException (Throwable reason) {
		super(reason);
	}
}
