package excecoes;

@SuppressWarnings("serial")
public class OpcaoInvalida extends Exception {

	public OpcaoInvalida() {
	}

	public String getMessage() {
		return "Digite uma opcao válida";
	}
}
