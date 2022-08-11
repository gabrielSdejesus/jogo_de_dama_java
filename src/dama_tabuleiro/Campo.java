package dama_tabuleiro;

public class Campo {
	
	private char quadrado;
	
	public Campo() {
		this.quadrado = '-';
	}

	public Campo(char jogador) {
		this.quadrado = jogador;
	}
	
	public char getQuadrado() {
		return quadrado;
	}
	
	public void setQuadrado(char jogador) {
		this.quadrado = jogador;
	}
}
