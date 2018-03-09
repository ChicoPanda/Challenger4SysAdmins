package fvarrui.sysadmin.editor.components;

import java.util.Optional;

import fvarrui.sysadmin.editor.application.EditorApp;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;

/**
 * Clase Auxiliar para generar cuadros de dialogo.
 * 
 * @author Ricardo Vargas
 *
 */
public class DialogBuilder {

	/**
	 * Configura y muestra un di�logo
	 * @param type Tipo de la alerta.
	 * @param title T�tulo del di�logo.
	 * @param header Cabecera del di�logo.
	 * @param content Contenido del di�logo.
	 * @param buttons Botones que presentar� el di�logo.
	 * @return Bot�n pulsado en el di�logo.
	 */
	public static Optional<ButtonType> alert(AlertType type, String title, String header, String content, ButtonType ... buttons) {
		Alert alert = new Alert(type);
		alert.initOwner(EditorApp.getPrimaryStage());
		alert.initModality(Modality.WINDOW_MODAL);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		if (buttons.length > 0) {
			alert.getButtonTypes().setAll(buttons);
		}
		return alert.showAndWait();
	}
	
	/**
	 * Muestra un di�logo de error 
	 * @param title T�tulo del di�logo.
	 * @param header Cabecera del di�logo.
	 * @param content Contenido del di�logo.
	 */
	public static void error(String title, String header, String content) {
		alert(AlertType.ERROR, title, header, content);
	}
	
	/**
	 * Muestra un di�logo de error 
	 * @param title T�tulo del di�logo.
	 * @param header Cabecera del di�logo.
	 * @param content Contenido del di�logo.
	 */
	public static void error(String title, String header, Throwable th) {
		alert(AlertType.ERROR, title, header, th.getLocalizedMessage());
	}


	/**
	 * Muestra un di�logo de aviso
	 * @param title T�tulo del di�logo.
	 * @param header Cabecera del di�logo.
	 * @param content Contenido del di�logo.
	 */
	public static void warning(String title, String header, String content) {
		alert(AlertType.WARNING, title, header, content);
	}

	/**
	 * Muestra un di�logo informativo
	 * @param title T�tulo del di�logo.
	 * @param header Cabecera del di�logo.
	 * @param content Contenido del di�logo.
	 */
	public static void info(String title, String header, String content) {
		alert(AlertType.INFORMATION, title, header, content);
	}

	/**
	 * Muestra un di�logo de confirmaci�n con respuestas de tipo SI/NO
	 * @param title T�tulo del di�logo.
	 * @param header Cabecera del di�logo.
	 * @param content Contenido del di�logo.
	 * @return Devuelve "true" si se ha seleccionado SI y "false" en cualquier otro caso. 
	 */
	public static boolean confirm(String title, String header, String content) {
		Optional<ButtonType> response = alert(AlertType.CONFIRMATION, title, header, content, ButtonType.YES, ButtonType.NO);
		return (ButtonType.YES.equals(response.get()));
	}
	
}
