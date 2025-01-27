package br.com.renan.Model;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Arquivo {

	public String lerArquivo(String caminho) {

		String texto = "";
		
		try {

			BufferedInputStream inputstream = new BufferedInputStream(
				    new FileInputStream(caminho));

			//4096 = 1024*4
			byte[] dataAsByte = new byte[4096];
			inputstream.read(dataAsByte, 0, 0);

			int nRead;
			
			while ((nRead = inputstream.read(dataAsByte)) != -1) {

				for (int i = 0; i < nRead; i++) {

					//System.out.print((char) dataAsByte[i]);
					texto = texto + (char) dataAsByte[i];

				}
			}
			inputstream.close();
			
			return texto;

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	public String lerArquivoCompleto(String caminho) {

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
