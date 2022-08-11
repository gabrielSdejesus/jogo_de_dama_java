package jogo_dama_java;

import java.util.Scanner;

import dama_tabuleiro.Dama_exceptions;
import dama_tabuleiro.Tabuleiro;

public class Programa {

	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);
		Tabuleiro tabuleiro = new Tabuleiro();
		tabuleiro.definirTabuleiro();
		int turno = 0;
		int linhaOrigem;
		int colunaOrigem;
		int linhaDestino;
		int colunaDestino;

		while (tabuleiro.verificarVitoria()) {
			try {
				do {
					new UI().limparTela();
					tabuleiro.imprimirTabuleiro();
					System.out.println("\n\nPecas V capturadas: " + tabuleiro.getPecasV());
					System.out.println("Pecas B capturadas: " + tabuleiro.getPecasB());
					System.out.println("\nVez do jogador: " + tabuleiro.turno(turno));
					System.out.println("\nOrigem:");
					System.out.print("Linha: ");
					linhaOrigem = scan.nextInt();
					scan.nextLine();
					System.out.print("Coluna: ");
					colunaOrigem = scan.nextInt();
					scan.nextLine();
					tabuleiro.exception(linhaOrigem, colunaOrigem, 0, 0, turno);
					System.out.println("\nDestino:");
					System.out.print("Linha: ");
					linhaDestino = scan.nextInt();
					scan.nextLine();
					System.out.print("Coluna: ");
					colunaDestino = scan.nextInt();
					scan.nextLine();
					tabuleiro.moverPecas(linhaOrigem, colunaOrigem, linhaDestino, colunaDestino, turno);
				} while (tabuleiro.validarMovimentoComum(linhaOrigem, colunaOrigem, linhaDestino, colunaDestino) && 
						!tabuleiro.removerObrigatorio(linhaOrigem, colunaOrigem, linhaDestino, colunaDestino, turno));
				turno++;
			} catch (Dama_exceptions e) {
				System.out.println("\n" + e.getMessage());
				scan.nextLine();
			} catch(RuntimeException e) {
				System.out.println(e.getMessage());
				scan.nextLine();
			}
		}
	}

}
