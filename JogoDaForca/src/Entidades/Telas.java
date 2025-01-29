package Entidades;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

import excecoes.OpcaoInvalida;

public class Telas {

	static Scanner leitor = new Scanner(System.in);

	public static void telaInicio() {

		int escolha = 1;

		do {
			try {
				System.out.println("========= JOGO DA FORCA =========");
				System.out.println("1 - Comecar partida");
				System.out.println("2 - Adicionar ou remover palavras");
				System.out.println("0 - Sair");
				System.out.println("======= BY Bruno Paniagua =======");

				System.out.println("Digite a ação desejada");
				escolha = leitor.nextInt();
				leitor.nextLine();

				switch (escolha) {
				case 1:
					telaJogar();
					break;

				case 2:
					telaManipularPalavras();
					break;

				case 0:
					leitor.close();
					System.exit(0);
					break;

				default:
					System.out.println("Digite uma ação válida");
				}

			} catch (Exception e) {
				System.out.println("Digite um número");
				leitor.nextLine();
			}

		} while (escolha != 0);

	}

	public static void telaJogar() {
		int continuarJogando = 0;

		do {
			Partida p1 = new Partida();
			System.out.println("Quer jogar mais uma partida?");
			System.out.println("0 - Continuar");
			System.out.println("Pressione qualquer outra tecla para voltar ao menu");
			try {
				continuarJogando = leitor.nextInt();
				leitor.nextLine();
			} catch (Exception e) {
				continuarJogando = 1;
			}

		} while (continuarJogando == 0);

	}

	public static void telaManipularPalavras() {
		int escolha = 1;
		do {
			System.out.println("1 - Adicionar palavra");
			System.out.println("2 - Remover palavra");
			System.out.println("0 - Voltar");

			System.out.println("Digite a ação desejada");

			try {
				escolha = leitor.nextInt();
				leitor.nextLine();
				switch (escolha) {
				case 1:
					telaAdicionarPalavra();
					break;

				case 2:
					telaRemoverPalavra();
					break;

				case 0:
					break;

				default:
					System.out.println("Digite uma ação válida");
				}
			} catch (Exception e) {
				System.out.println("Entrada inválida. Por favor, insira um número válido.");
				leitor.nextLine();
			}

		} while (escolha != 0);
	}

	public static void telaAdicionarPalavra() {
		String path = "C:\\Users\\Bruno\\git\\repositoryForca\\JogoDaForca\\Palavras.txt";
		File file = new File(path);

		System.out.println("=== Regras para Adicionar palavra===");
		System.out.println("1 - Não pode números");
		System.out.println("2 - Não pode caracteres especiais");
		System.out.println("====================================");
		System.out.println("");

		System.out.println("Digite a palavra desejada");
		String palavraNova = leitor.nextLine();

		if (!palavraNova.isEmpty() && palavraNova.matches("[A-Za-zÀ-ÖØ-öø-ÿ ]+")) {

			try (BufferedWriter bf = new BufferedWriter(new FileWriter(file, file.exists()))) {
				bf.write(palavraNova);
				bf.newLine();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println("Digite uma palavra válida");
		}
	}

	public static void telaRemoverPalavra() {

		String path = "C:\\Users\\Bruno\\git\\repositoryForca\\JogoDaForca\\Palavras.txt";
		File file = new File(path);
		LinkedList<String> listaPalavras = new LinkedList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line = br.readLine();
			while (line != null) {
				listaPalavras.add(line.trim());
				line = br.readLine();
			}
		} catch (IOException e) {
			System.out.println("Erro ao ler o arquivo: " + e.getMessage());
			return;
		}

		if (listaPalavras.isEmpty()) {
			System.out.println("Não há palavras para remover.");
			return;
		}

		System.out.println("Palavras disponíveis para remover:");
		for (int i = 0; i < listaPalavras.size(); i++) {
			System.out.println(i + " - " + listaPalavras.get(i));
		}

		int indiceEscolhido = -1;
		boolean indiceValido = false;

		while (!indiceValido) {
			System.out.println("Digite o índice da palavra que deseja remover:");

			try {
				indiceEscolhido = leitor.nextInt();
				leitor.nextLine();
				if (indiceEscolhido < 0 || indiceEscolhido >= listaPalavras.size()) {
					throw new OpcaoInvalida();
				}
				indiceValido = true;
			} catch (OpcaoInvalida e) {
				System.out.println(e.getMessage());
			} catch (Exception e) {
				System.out.println("Entrada inválida. Por favor, insira um número válido.");
				leitor.nextLine();
			}
		}

		if (indiceEscolhido < 0 || indiceEscolhido >= listaPalavras.size()) {
			System.out.println("Índice inválido.");
			return;
		}

		listaPalavras.remove(indiceEscolhido);

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
			for (String palavra : listaPalavras) {
				bw.write(palavra);
				bw.newLine();
			}
			System.out.println("Palavra removida com sucesso!");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
