package excecoes;

@SuppressWarnings("serial")
public class SemPalavra extends Exception {
	
	public SemPalavra() {};
	
	public String getMessage() {
		return"Adiciona alguma palavra para jogar";
	}
}
