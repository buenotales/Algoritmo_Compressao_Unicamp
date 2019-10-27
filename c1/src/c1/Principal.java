package c1;

import java.util.ArrayList;

public class Principal {

	public static void main(String[] args) {

		comprimir("te.txt");
		descomprimi("comprimido-te.txt");

	}

	public static void descomprimi(String nomeArquvio) {

		Modelagem m = new Modelagem();
		Arquivo a = new Arquivo();

		//Le arquivo
		String texto = a.lerArquivo(System.getProperty("user.dir") + "\\"+nomeArquvio);
		
		//Descomprimi
		String textoDescomprimido = m.descomprimi(texto);

		//Escreve no arquivo		
		if(a.gravarArquivoComprimido(textoDescomprimido, "descomprimido-"+nomeArquvio) == 1) {
			System.out.println("Descompressão com sucesso!");
		}else {
			System.out.println("Erro na hora de gravar no arquivo!");
		}
		
	}
	
	public static void comprimir(String nomeArquvio) {

		Modelagem m = new Modelagem();
		Arquivo a = new Arquivo();
		
		//Le arquivo
		String texto = a.lerArquivo(System.getProperty("user.dir") + "\\"+nomeArquvio);

		//Remove acentos do texto
		texto = m.removerAcentos(texto);

		//Cria dicionario com todas as palavras
		ArrayList<String> dicionario = new ArrayList<String>();
		dicionario = m.criaDicionario(texto);

		//Cria dicionario apenas com as palavras a serem criptogrfadas
		ArrayList<String> dicionarioFiltrado = new ArrayList<String>();
		dicionarioFiltrado = m.filtraRepeticao(dicionario);

		//Codifica as palavras filtradas
		ArrayList<Texto> filtroCodificado = new ArrayList<Texto>();
		filtroCodificado = m.criaVetorPosicaoDecimal(dicionarioFiltrado);

		//Cria o cabecalho
		int[] c = m.criaCabecalho(dicionarioFiltrado);

		//cria a string final comprimida
		String textoComprimido = m.comprimi(c, dicionario, dicionarioFiltrado, filtroCodificado);
		
		//Escreve no arquivo		
		if(a.gravarArquivoComprimido(textoComprimido, "comprimido-"+nomeArquvio) == 1) {
			System.out.println("Comprimido com sucesso!");
		}else {
			System.out.println("Erro na hora de gravar no arquivo!");
		}
	}

}
