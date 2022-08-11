package jogo_dama_java;

import dama_tabuleiro.Tabuleiro;

public class UI {
	
	Tabuleiro tabuleiro = new Tabuleiro();

	public void limparTela() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

}
