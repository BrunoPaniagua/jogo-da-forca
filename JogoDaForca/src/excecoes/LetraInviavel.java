package excecoes;

@SuppressWarnings("serial")
public class LetraInviavel extends Exception {

	public String getMessage() {
		return "Digite uma letra válida";
	}
}
