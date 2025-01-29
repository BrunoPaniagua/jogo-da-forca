package Entidades;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import excecoes.LetraInviavel;
import excecoes.Perder;

public class Partida {

	private String palavra;
	private Scanner leitor;
	private List<Character> letrasTotal;
	private List<Character> letrasCertas;
	private List<Character> letrasErradas;
	private int vidas;
	private boolean vencer;

	public Partida(String palavra) {

		this.palavra = palavra.toLowerCase();
		letrasTotal = new ArrayList<Character>();
		letrasCertas = new ArrayList<Character>();
		letrasErradas = new ArrayList<Character>();
		vidas = 5;
		vencer = false;
		leitor = new Scanner(System.in);

		arrumarListas();
		start();
	}

	// -------------------------------------------

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
