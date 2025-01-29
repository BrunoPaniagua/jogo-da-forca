package excecoes;

@SuppressWarnings("serial")
public class Perder extends Exception {
	public Perder() {
	};

	public String getMessage() {
		return "Suas vidas acabaram, tente novamente na próxima";
	}
}
