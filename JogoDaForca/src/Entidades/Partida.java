package Entidades;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import excecoes.LetraInviavel;
import excecoes.Perder;
import excecoes.SemPalavra;

public class Partida {

	private String palavra;
	private Scanner leitor;
	private List<Character> letrasTotal;
	private List<Character> letrasCertas;
	private List<Character> letrasErradas;
	private int vidas;
	private boolean vencer;

	public Partida() {

		try {
			this.palavra = definirPalavra();
			letrasTotal = new ArrayList<Character>();
			letrasCertas = new ArrayList<Character>();
			letrasErradas = new ArrayList<Character>();
			vidas = 5;
			vencer = false;
			leitor = new Scanner(System.in);

			arrumarListas();
			start();
		} catch (SemPalavra e) {
			System.out.println(e.getMessage());
		}
	}

	// -------------------------------------------

	public String definirPalavra() throws SemPalavra {

		String path = "Palavras.txt";
		List<String> listaPalavras = new ArrayList<String>();

		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String line = br.readLine();
			while (line != null) {
				line.trim();
				if (!line.isEmpty() && line.matches("[A-Za-zÀ-ÖØ-öø-ÿ ]+")) {
					listaPalavras.add(line);
				}
				line = br.readLine();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		if (listaPalavras.isEmpty()) {
			throw new SemPalavra();
		}

		Random random = new Random();
		String palavraEscolhida = listaPalavras.get(random.nextInt(listaPalavras.size()));

		return removerAcentos(palavraEscolhida.toLowerCase());
	}

	private String removerAcentos(String palavra) {
		return Normalizer.normalize(palavra, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
	}

	public void arrumarListas() {
		for (Character c : palavra.toLowerCase().toCharArray()) {
			letrasTotal.add(c);
		}

		if (palavra.contains(" ")) {
			letrasCertas.add(' ');
		}
	}

	public void start() {
		try {
			do {
				System.out.println("Você tem " + vidas + " vidas");
				escreverPalavraOculta();
				escreverLetrasErradas();
				try {
					escreverNovaLetra();
				} catch (LetraInviavel e) {
					System.out.println(e.getMessage());
					System.out.println("");
				}
			} while (partidaVencida() == false);

			System.out.println("Você ganhou");
			System.out.println("A palavra era: " + palavra);
		} catch (Perder e) {
			System.out.println(e.getMessage());
			leitor.close();
		}
	}

	public void escreverNovaLetra() throws Perder, LetraInviavel {

		System.out.println("Digite uma letra");
		String letraString = leitor.nextLine();
		if (letraString.length() != 1 || !Character.isLetter(letraString.charAt(0))) {
			throw new LetraInviavel();
		}
		char letra = letraString.toLowerCase().charAt(0);

		if (letrasTotal.contains(letra)) {
			if (letrasCertas.contains(letra)) {
				System.out.println(" ");
				System.out.println("Você já digitou essa letra");
				System.out.println(" ");
			} else {
				letrasCertas.add(letra);
			}
		} else {
			if (letrasErradas.contains(letra)) {
				System.out.println(" ");
				System.out.println("Você já digitou essa letra");
				System.out.println(" ");
			} else {
				letrasErradas.add(letra);
				vidas -= 1;
				if (vidas <= 0) {
					throw new Perder();
				}
			}
		}
	}

	public void escreverPalavraOculta() {
		for (int i = 0; i < letrasTotal.size(); i++) {
			if (letrasTotal.get(i) == ' ') {
				System.out.print("   ");
			} else {
				if (letrasCertas.contains(letrasTotal.get(i))) {
					System.out.print(letrasTotal.get(i) + " ");
				} else {
					System.out.print("_ ");
				}
			}
		}
		System.out.println("");
	}

	public void escreverLetrasErradas() {
		System.out.print("Letras erradas: ");
		if (letrasErradas.size() == 0) {
			System.out.print("nenhuma letra errada até o momento");
		} else {
			letrasErradas.stream().forEach(l -> System.out.print(l + " "));
		}
		System.out.println("");
	}

	public boolean partidaVencida() {
		return letrasTotal.stream().allMatch(letrasCertas::contains);
	}

}
