package c1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Arquivo {

	public String lerArquivo(String caminho) {

		BufferedReader inputStream = null;
		String texto = "", l;

		try {

			inputStream = new BufferedReader(new FileReader(caminho));

			while ((l = inputStream.readLine()) != null) {
				texto = texto + l;
			}

			inputStream.close();

		} catch (Exception e) {
			e.printStackTrace();

		}

		return texto;

	}

	public int gravarArquivoComprimido(String saida, String nomeArquvio) {

		PrintWriter outputStream1 = null;

		try {

			outputStream1 = new PrintWriter(new FileWriter(nomeArquvio));

			outputStream1.print(saida);

			outputStream1.close();

			return 1;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;

	}

}
