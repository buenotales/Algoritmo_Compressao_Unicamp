package br.com.renan.Model;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class Modelagem {

	//Metodo para remover os acentos e enquadrao o texto na tabela ASCII
	public String removerAcentos(String input) {

		return Normalizer.normalize(input, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}

	//Metodo que ira criar um array com todas as palavras e simbolos(, e .) do texto - recebe com parametro o texto original
	public ArrayList<String> criaDicionario(String input) {

		//Separa as palavras por espaco e constroi vetor
		String[] vPalavras = input.split(" ");

		ArrayList<String> lista = new ArrayList<String>();

		//Laco necessario para separar a , e . da palavra em que esta junto por exemplo (fim.)
		for (String p : vPalavras) {

			//Verifica se contem , ou . na palavra
			if (p.contains(",") || p.contains(".")) {

				//Caso houver ,
				if (p.contains(",")) {

					//Ele ira separar a palavra da virgula
					String[] aux = p.split(",");
					
					//E acrescentar a plavra na lista
					for (String a : aux) {
						lista.add(a);
					}
					//em seguida e acrescentado a , na posicao seguinte do array
					lista.add(",");

					//Caso houver .
				} else if (p.contains(".")) {

					//Sera separado a plavra do .
					String[] aux = p.split(".");

					//Em alguns casos nao estava separando a plavra do ponto entao foi feito essa condicao
					//Caso a palavra seja separado, ira adicionar a palavra no array
					
					if (aux.length != 0) {

						for (String a : aux) {
							lista.add(a);
						}

						//Caso n�o separe a palavra do ponto =, o ponto sera removido e adicionado a palavra no array
					} else {
						lista.add(p.replace(".", ""));
					}

					//por fim o ponto e incerido logo na posicao seguinte
					lista.add(".");
				}

				//Caso nao houver . ou , a palvra e adiconada no else abaixo
			} else {

				lista.add(p);
			}

		}

		return lista;

	}

	//Metodo que ira filtrar as palavras a serem criptografadas - recebe como parametro a lista de palavras completa criado no metodo criaDicionario
	public ArrayList<String> filtraRepeticao(ArrayList<String> input) {

		//Foi usado um array SET para que nao permitisse adicionar palavras repetidas no array.
		//O LinkedHashSet para que nao removesse da ordem as palvras ja existentes
		Set<String> listaAux = new LinkedHashSet<String>();

		//Percorrendo o array de palavras, adicionando no ArraySet
		for (String p : input) {

			//Adiciona somente as palavras acima de 3 caraceters
			if (p.length() > 3) {

				listaAux.add(p);
			}

		}

		//Logo em seguida migramos para um arrayList por eu nao ter conseguido acessar a posicao do ArraySet
		ArrayList<String> lista = new ArrayList<String>();

		for (String p : listaAux) {

			lista.add(p);

		}

		return lista;
	}

	//Metodo utilizado por adicionar a devida criptografia as palavras - o paramento recebido e o array filtrado criado no metodo filtraRepeticao
	public ArrayList<Texto> criaVetorPosicaoDecimal(ArrayList<String> input) {

		ArrayList<Texto> lista = new ArrayList<Texto>();

		int x2 = 0, x3 = 0, count = 0;

		//While percorre ate a ultima posicao do array
		while (count < input.size()) {

			//Cria um novo obejto da classe Texto
			Texto t = new Texto();

			//E atribuido os bytes aos atributos e a palvra respectiva ao codigo
			t.p = input.get(count);
			t.x1 = Integer.toString(255);
			t.x2 = Integer.toString(x2);
			t.x3 = Integer.toString(x3);

			//Adiciona o objeto a lista
			lista.add(t);

			//Caso a ultima casa decimal ((255)(x2)=>(x3)) chegue ao maximo e zerado o valor e acrescentado a casa x2
			if (x3 == 255) {
				x3 = 0;
				x2++;
			} else {
				x3++;
			}

			//Caso o texto contenha 65025 palavras o numero maximo e atingido
			if (x2 == 255 && x3 == 255) {
				System.out.println("N� maximo de palavras atingido.");
				break;
			}

			count++;
		}

		return lista;
	}

	//Methodo utilizado para criar o cabecalho - recebe como parametro a lista de palavras filtradas criado no metodo filtraRepeticao
	public int[] criaCabecalho(ArrayList<String> input) {

		//Cria o vetor que representa os dois bytes do cabecalho
		//		c[0] c[1]
		int[] c = { 0, 0 };
		int count = 0;

		while (count < input.size()) {

			if (c[1] == 255) {

				c[0]++;

			} else if (c[0] == 255 && c[1] == 255) {

				System.out.println("N� maximo de palavras atingido");
				break;

			} else {

				c[1]++;
			}

			count++;
		}

		return c;

	}

	//Metodo utilizado por criar a String de comprimida
	//recebe como parametro cabecalho, dicionario com todas as palvras (metodo criaDicionario)
	//as palavras filtradas > 3 caracteres (metodo filtraRepeticao)
	//as palavras codificadas (metodo criaVetorPosicaoDecimal)
	public String comprimi(int[] cabecalho, ArrayList<String> dicionario, ArrayList<String> dicionarioFiltrado,
			ArrayList<Texto> filtroCodificado) {

		//criado a String final
		String textoComprimido = "";

		// adicionado o cabecalho a string final
		textoComprimido = textoComprimido + "(" + cabecalho[0] + ")(" + cabecalho[1] + ") ";

		for (String df : dicionarioFiltrado) {

			// adicionado as palavras que foram criptografadas a string final (nao criptografadas)
			textoComprimido = textoComprimido + df + ",";
		}

		// for responsavel por verificar as palavras do dicionario total a serem substituidas pelas palvras criptografadas
		for (String dc : dicionario) {

			boolean valida = false;

			for (Texto fc : filtroCodificado) {

				if (dc.equals(fc.p)) {
					
					valida = true;
					textoComprimido = textoComprimido + " (" + fc.x1 + ")(" + fc.x2 + ")(" + fc.x3 + ")";
				}

			}

			if (valida == false) {
				
				//Caso seja . ou , remove o espaco antes
				if(dc.equals(".") || dc.equals(",")) {
					textoComprimido = textoComprimido + dc;
				}else {

					textoComprimido = textoComprimido + " " + dc;
				}
				
			}

		}
		//remove espaco do comeco e fim
		textoComprimido = textoComprimido.trim();
				
		return textoComprimido;
	}

	//metodo que descomprimi a String comprimida - recebe como paramentro a string comprimida
	public String descomprimi(String input) {

		// Separa cabecalho, palavras e codigos
		String[] decode = input.split(" ");

		// decode[0] = contem o cabecalho
		// decode[1] = contem a lista de palavras
		// decode[2] ate [ultima posicao] = contem as palavras codificadas e as que nao foram codificadas

		//Cabecalho
		int[] cabecalho = decodificaCabecalho(decode[0]);

		//lista de palavras que foram codificadas
		ArrayList<Texto> listaPalavras = codificadPalavras(decode[1]);

		//lista de palavras codificadas e as que nao foram codificas por srem menor que 3 caracteres
		ArrayList<Texto> listaCodificada = estruturaCodificacao(decode);

		//String  com a  decodificacao pronta
		String textoDecodificado = montaStringFinal(listaPalavras, listaCodificada);

		return textoDecodificado;
	}

	//Ultimo metodo do processo de descompressao, recebe como parametro as listas de palavras que foram codificadas e a lista de palavras codificadas e que sao menores que 3 caracteres
	private String montaStringFinal(ArrayList<Texto> listaPalavras, ArrayList<Texto> listaCodificada) {

		//Criada a string final
		String textoDescomprimido = "";

		for (Texto lc : listaCodificada) {

			//valida se o valor da palavra nao esta nulo, caso nao seja nulo e pq a palavra e menor que 3 caracteres e nao foi criptografada
			if (lc.p != null) {
				
				//trata os espacos antes do . e , e adiciona na string final
				if(lc.p.equals(".") || lc.p.equals(",")) {
					
					textoDescomprimido = textoDescomprimido + lc.p;

					if(lc.pv != null && (lc.pv.equals(".") || lc.pv.equals(","))) {

						textoDescomprimido = textoDescomprimido + lc.pv;
						
					}
					
				}else {
					
					textoDescomprimido = textoDescomprimido + " " + lc.p;

					if(lc.pv != null && (lc.pv.equals(".") || lc.pv.equals(","))) {

						textoDescomprimido = textoDescomprimido + lc.pv;
						
					}
				}
				
				//Caso seja nulo e pq  a aplavra esta codificada e precis ser decodifica
			} else {

				for (Texto lp : listaPalavras) {
					
					//E realizado comparacao do x1, x2 e x3 das palvras entre os arrays de palavras codficadas(LC) e nao codificadas(LP)
					//Caso seja iguais e adicionado a string final a palavra do array nao codificado
					if (lc.x1.equals(lp.x1) && lc.x2.equals(lp.x2) && lc.x3.equals(lp.x3)) {

						textoDescomprimido = textoDescomprimido + " " + lp.p;


						if(lc.pv != null && (lc.pv.equals(".") || lc.pv.equals(","))) {

							textoDescomprimido = textoDescomprimido + lc.pv;
							
						}

					}

				}
			}

		}

		//remove espaco do comeco e fim
		textoDescomprimido = textoDescomprimido.trim();
				
		return textoDescomprimido;
	}

	//Metodo responsavel por organizar as posicoes das palavras em Objeto Texto depois que foi extraido com  numeros das posicoes do array criado no metodo descomprimi
	private ArrayList<Texto> estruturaCodificacao(String[] input) {

		ArrayList<Texto> lista = new ArrayList<Texto>();

		// pq 0 e a posicao do cabecalho e 1 e a posicao da lista de palvras
		int count = 2;

		while (count < input.length) {

			//remove todos os "(" ")" "  "
			String valor = input[count].replace("(", " ");
			valor = valor.replace(")", " ");
			valor = valor.replace("  ", "-");
			valor = valor.replace(" ", "");

			String[] aux = valor.split("-");

			Texto t = new Texto();

			//Valida se o valor que esta vindo e numero ou as palavras menor de 3 caraceteres  nao comprimidas.
			if (validaCaracter(aux[0]) == false) {

				//Monta o obj
				t.x1 = aux[0];
				t.x2 = aux[1];
				
				if(aux[2].contains(".")) {
					
					t.x3 = aux[2].replace(".", "");
					t.pv = ".";

				}else if(aux[2].contains(",")) {
					t.x3 = aux[2].replace(",", "");
					t.pv = ",";
				
				}else {
				
					t.x3 = aux[2];
				}

			} else {

				t.p = aux[0];
			}

			lista.add(t);

			count++;
		}

		return lista;
	}

	//Responsavel por decodificar o cabecalho, recebe como parametro a primeira posicao do array criado na funcao descomprimi
	private int[] decodificaCabecalho(String input) {

		//E retirado todos os "(" ")" "  " dos numeros
		String valor = input.replace("(", " ");
		valor = valor.replace(")", " ");
		valor = valor.replace("  ", "-");
		valor = valor.replace(" ", "");

		String[] aux = valor.split("-");

		//E adicionado ao vetor
		int[] cabecalho = { Integer.parseInt(aux[0]), Integer.parseInt(aux[1]) };

		return cabecalho;
	}

	//Rsponsavel por separar as palavras que foram criptogrfadas(lista de palvras nao criptografadas.
	private ArrayList<Texto> codificadPalavras(String input) {

		//Separa as palavras a cada "," encontrada
		String[] palavras = input.split(",");

		ArrayList<String> lista = new ArrayList<String>();

		for (String p : palavras) {

			lista.add(p);

		}

		ArrayList<Texto> listaCodificada = criaVetorPosicaoDecimal(lista);

		return listaCodificada;
	}

	//Valida se e caracter
	private boolean validaCaracter(String texto) {

		if (texto.contains(".") || texto.contains(",")) {
			return true;
		}

		for (int i = 0; i < texto.length(); i++) {
			if (!Character.isAlphabetic((texto.charAt(i)))) {
				return false;
			}
		}

		return true;
	}

	//Metodo responsavel por converter numeros decimal em binario
	public String binario(int numero) {

		int d = numero;

		StringBuffer binario = new StringBuffer();

		if (numero != 0) {

			while (d > 0) {
				int b = d % 2;
				binario.append(b);
				d = d >> 1;
			}

			// System.out.println(binario.reverse());
			String b = null;

			if (binario.reverse().toString().length() == 7) {
				b = "0" + binario.reverse();
			} else if (binario.reverse().toString().length() == 6) {
				b = "00" + binario.reverse();
			} else if (binario.reverse().toString().length() == 5) {
				b = "000" + binario.reverse();
			} else if (binario.reverse().toString().length() == 4) {
				b = "0000" + binario.reverse();
			} else if (binario.reverse().toString().length() == 3) {
				b = "00000" + binario.reverse();
			} else if (binario.reverse().toString().length() == 2) {
				b = "000000" + binario.reverse();
			} else if (binario.reverse().toString().length() == 1) {
				b = "0000000" + binario.reverse();
			} else {
				b = binario.reverse().toString();
			}

			return b;

		} else {
			return "00000000".toString();
		}
	}
}
