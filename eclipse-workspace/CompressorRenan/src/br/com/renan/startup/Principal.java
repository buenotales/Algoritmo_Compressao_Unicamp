package br.com.renan.startup;

import java.io.File;
import java.util.ArrayList;

import br.com.renan.Model.Arquivo;
import br.com.renan.Model.Modelagem;
import br.com.renan.Model.Texto;

public class Principal {

	public static void main(String[] args) {

		File file = new File(System.getProperty("user.dir") + "\\" + args[1]);

		if (!file.exists()) {
			System.out.println("Arquivo n�o encontrado.");

		} else {

			if (args[0].equals("-c")) {

				comprimir(args[1]);
			}

			if (args[0].equals("-d")) {
				descomprimi(args[1]);
			}

		}

	}

	public static void descomprimi(String nomeArquvio) {

		Modelagem m = new Modelagem();
		Arquivo a = new Arquivo();

		// Le arquivo
		String texto = a.lerArquivo(System.getProperty("user.dir") + "\\" + nomeArquvio);

		// Descomprimi
		String textoDescomprimido = m.descomprimi(texto);

		// Escreve no arquivo
		if (a.gravarArquivoComprimido(textoDescomprimido, "descomprimido-" + nomeArquvio) == 1) {
			System.out.println("Descompress�o com sucesso!");
		} else {
			System.out.println("Erro na hora de gravar no arquivo!");
		}

	}

	public static void comprimir(String nomeArquvio) {

		Modelagem m = new Modelagem();
		Arquivo a = new Arquivo();

		// Le arquivo
		String texto = a.lerArquivo(System.getProperty("user.dir") + "\\" + nomeArquvio);

		// Remove acentos do texto
		texto = m.removerAcentos(texto);

		// Cria dicionario com todas as palavras
		ArrayList<String> dicionario = new ArrayList<String>();
		dicionario = m.criaDicionario(texto);

		// Cria dicionario apenas com as palavras a serem criptogrfadas
		ArrayList<String> dicionarioFiltrado = new ArrayList<String>();
		dicionarioFiltrado = m.filtraRepeticao(dicionario);

		// Codifica as palavras filtradas
		ArrayList<Texto> filtroCodificado = new ArrayList<Texto>();
		filtroCodificado = m.criaVetorPosicaoDecimal(dicionarioFiltrado);

		// Cria o cabecalho
		int[] c = m.criaCabecalho(dicionarioFiltrado);

		// cria a string final comprimida
		String textoComprimido = m.comprimi(c, dicionario, dicionarioFiltrado, filtroCodificado);

		// Escreve no arquivo
		if (a.gravarArquivoComprimido(textoComprimido, "comprimido-" + nomeArquvio) == 1) {
			System.out.println("Comprimido com sucesso!");
		} else {
			System.out.println("Erro na hora de gravar no arquivo!");
		}
	}

}
