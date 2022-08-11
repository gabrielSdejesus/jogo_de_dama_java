package dama_tabuleiro;

public class Tabuleiro {

	Campo[][] tabuleiros = new Campo[8][8];
	Integer pecasV = 0;
	Integer pecasB = 0;

	public Tabuleiro() {
	}

	public Campo[][] getTabuleiro() {
		return tabuleiros;
	}

	public Integer getPecasV() {
		return pecasV;
	}

	public Integer getPecasB() {
		return pecasB;
	}

	public void definirTabuleiro() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < tabuleiros.length; j++) {
				if (i % 2 == 0 && j % 2 != 0) {
					colocarPecas(i, j, new Campo('B'));
				}
				if (i % 2 != 0 && j % 2 == 0) {
					colocarPecas(i, j, new Campo('B'));
				}
			}
		}

		for (int i = 5; i < tabuleiros.length; i++) {
			for (int j = 0; j < tabuleiros.length; j++) {
				if (i % 2 == 0 && j % 2 != 0) {
					colocarPecas(i, j, new Campo('V'));
				}
				if (i % 2 != 0 && j % 2 == 0) {
					colocarPecas(i, j, new Campo('V'));
				}
			}
		}

		for (int i = 0; i < tabuleiros.length; i++) {
			for (int j = 0; j < tabuleiros.length; j++) {
				if (tabuleiros[i][j] == null) {
					tabuleiros[i][j] = new Campo();
				}
			}
		}
	}

	public void imprimirTabuleiro() {
		for (int i = 0; i < 8; i++)
			System.out.print("  " + i);
		for (int i = 0; i < 8; i++) {
			System.out.println();
			System.out.print(i + " ");
			for (int j = 0; j < 8; j++) {
				System.out.print(tabuleiros[i][j].getQuadrado() + "  ");
			}
		}
	}

	public boolean verificarVitoria() {
		if (this.pecasB == 12 || this.pecasV == 12) {
			return false;
		}
		return true;
	}

	public void colocarPecas(int linha, int coluna, Campo peca) {
		tabuleiros[linha][coluna] = peca;
	}

	public char turno(int turno) {
		if (turno % 2 != 0) {
			return 'B';
		}
		return 'V';
	}

	public void exception(int linhaOrigem, int colunaOrigem, int linhaDestino, int colunaDestino, int turno) {
		if (!(linhaOrigem > -1 && linhaOrigem < 8 && colunaOrigem > -1 && colunaOrigem < 8)) {
			throw new Dama_exceptions("Linha/Coluna de Origem incorreta!");
		}

		if (!(linhaDestino > -1 && linhaDestino < 8 && colunaDestino > -1 && colunaDestino < 8)) {
			throw new Dama_exceptions("Linha/Coluna de Destino incorreta!");
		}

		if (tabuleiros[linhaOrigem][colunaOrigem].getQuadrado() == '-') {
			throw new Dama_exceptions("Selecione uma peca: " + turno(turno));
		}

		if (!verificarJogador(linhaOrigem, colunaOrigem, turno)) {
			throw new Dama_exceptions("Erro, vez do jogador: " + turno(turno));
		}

	}

	//Verificar se o jogador atual corresponde ao seu turno
	public boolean verificarJogador(int linhaOrigem, int colunaOrigem, int turno) {
		if (tabuleiros[linhaOrigem][colunaOrigem].getQuadrado() == 'V' && turno(turno) == 'V'
				|| tabuleiros[linhaOrigem][colunaOrigem].getQuadrado() == 'B' && turno(turno) == 'B') {
			return true;
		} else {
			return false;
		}
	}

	//Altera na tela a peca de origem e destino
	public void moverPecas(int linhaOrigem, int colunaOrigem, int linhaDestino, int colunaDestino, int turno) {
		exception(linhaOrigem, colunaOrigem, linhaDestino, colunaDestino, turno);
		// Mover pecas V ou B
		if (validacaoDaPosicao(linhaOrigem, colunaOrigem, linhaDestino, colunaDestino, turno)) {
			colocarPecas(linhaDestino, colunaDestino, new Campo(turno(turno)));
			colocarPecas(linhaOrigem, colunaOrigem, new Campo());
		} else {
			throw new Dama_exceptions("Movimento não permitido!");
		}
	}

	//Valida o movimento das pecas V ou B
	public boolean validacaoDaPosicao(int linhaOrigem, int colunaOrigem, int linhaDestino, int colunaDestino,
			int turno) {
		if (movimentosPecasV(linhaOrigem, colunaOrigem, linhaDestino, colunaDestino, turno)
				|| movimentosPecasB(linhaOrigem, colunaOrigem, linhaDestino, colunaDestino, turno)) {
			return true;
		} else {
			return false;
		}
	}

	//Valida o movimento basico da dama
	public boolean validarMovimentoComum(int linhaOrigem, int colunaOrigem, int linhaDestino, int colunaDestino) {
		if (tabuleiros[linhaDestino][colunaDestino].getQuadrado() == 'V' && linhaDestino == (linhaOrigem - 1)
				&& (colunaDestino == (colunaOrigem + 1) || colunaDestino == (colunaOrigem - 1))) {
			return false;
		} else if (tabuleiros[linhaDestino][colunaDestino].getQuadrado() == 'B' && linhaDestino == (linhaOrigem + 1)
				&& (colunaDestino == (colunaOrigem + 1) || colunaDestino == (colunaOrigem - 1))) {
			return false;
		} else {
			return true;
		}
	}

	//Movimento da peca V
	public boolean movimentosPecasV(int linhaOrigem, int colunaOrigem, int linhaDestino, int colunaDestino, int turno) {

		if (removerObrigatorio(linhaOrigem, colunaOrigem, linhaDestino, colunaDestino, turno)) {
			// Peca V comer para esquerda
			if (linhaDestino == (linhaOrigem - 2) && colunaDestino == (colunaOrigem - 2)
					&& tabuleiros[linhaOrigem - 1][colunaOrigem - 1].getQuadrado() == 'B'
					&& tabuleiros[linhaDestino][colunaDestino].getQuadrado() == '-') {
				tabuleiros[linhaOrigem - 1][colunaOrigem - 1].setQuadrado('-');
				this.pecasB++;
				return true;
			} else
			// Peca V comer para direita
			if (linhaDestino == (linhaOrigem - 2) && colunaDestino == (colunaOrigem + 2)
					&& tabuleiros[linhaOrigem - 1][colunaOrigem + 1].getQuadrado() == 'B'
					&& tabuleiros[linhaDestino][colunaDestino].getQuadrado() == '-') {
				tabuleiros[linhaOrigem - 1][colunaOrigem + 1].setQuadrado('-');
				this.pecasB++;
				return true;
			} else
			// Movimento comum
			if (tabuleiros[linhaOrigem][colunaOrigem].getQuadrado() == 'V' && linhaDestino == (linhaOrigem - 1)
					&& (colunaDestino == (colunaOrigem + 1) || colunaDestino == (colunaOrigem - 1))
					&& tabuleiros[linhaDestino][colunaDestino].getQuadrado() == '-') {
				return true;
			} else {
				return false;
			}

		} else {
			throw new Dama_exceptions("Obrigatorio comer a peca adversaria.");
		}
	}

	//Movimento da peca B
	public boolean movimentosPecasB(int linhaOrigem, int colunaOrigem, int linhaDestino, int colunaDestino, int turno) {

		if (removerObrigatorio(linhaOrigem, colunaOrigem, linhaDestino, colunaDestino, turno)) {
			// Peca B comer para esquerda
			if (linhaDestino == (linhaOrigem + 2) && colunaDestino == (colunaOrigem - 2)
					&& tabuleiros[linhaOrigem + 1][colunaOrigem - 1].getQuadrado() == 'V'
					&& tabuleiros[linhaDestino][colunaDestino].getQuadrado() == '-') {
				tabuleiros[linhaOrigem + 1][colunaOrigem - 1].setQuadrado('-');
				this.pecasV++;
				return true;
			} else
			// Peca B comer para direita
			if (linhaDestino == (linhaOrigem + 2) && colunaDestino == (colunaOrigem + 2)
					&& tabuleiros[linhaOrigem + 1][colunaOrigem + 1].getQuadrado() == 'V'
					&& tabuleiros[linhaDestino][colunaDestino].getQuadrado() == '-') {
				tabuleiros[linhaOrigem + 1][colunaOrigem + 1].setQuadrado('-');
				this.pecasV++;
				return true;
			} else
			// Movimento comum
			if (tabuleiros[linhaOrigem][colunaOrigem].getQuadrado() == 'B' && linhaDestino == (linhaOrigem + 1)
					&& (colunaDestino == (colunaOrigem + 1) || colunaDestino == (colunaOrigem - 1))
					&& tabuleiros[linhaDestino][colunaDestino].getQuadrado() == '-') {
				return true;
			} else {
				return false;
			}

		} else {
			throw new Dama_exceptions("Obrigatorio comer a peça adversaria.");
		}
	}

	//Regra obrigatorio comer
	public boolean removerObrigatorio(int linhaOrigem, int colunaOrigem, int linhaDestino, int colunaDestino,
			int turno) {
		boolean v = true;
		if (turno(turno) == 'V') {
			// Verificar se V e obrigado a comer para esquerda
			for (int i = 7; i > 1; i--) {
				for (int j = 7; j > 1; j--) {
					if (tabuleiros[i][j].getQuadrado() == 'V') {
						if (tabuleiros[i - 1][j - 1].getQuadrado() == 'B'
								&& tabuleiros[i - 2][j - 2].getQuadrado() == '-') {
							if (linhaDestino == (linhaOrigem - 2) && colunaDestino == (colunaOrigem - 2)
									&& tabuleiros[linhaDestino][colunaDestino].getQuadrado() == '-') {
								return true;
							} else {
								v = false;
							}
						}
					}
				}
			}
			// Verificar se V e obrigado a comer para direita
			for (int i = 7; i > 1; i--) {
				for (int j = 0; j < 6; j++) {
					if (tabuleiros[i][j].getQuadrado() == 'V') {
						if (tabuleiros[i - 1][j + 1].getQuadrado() == 'B'
								&& tabuleiros[i - 2][j + 2].getQuadrado() == '-') {
							if (linhaDestino == (linhaOrigem - 2) && colunaDestino == (colunaOrigem + 2)
									&& tabuleiros[linhaDestino][colunaDestino].getQuadrado() == '-') {
								return true;
							} else {
								v = false;
							}
						}
					}
				}
			}
			return v;
		} else if (turno(turno) == 'B') {
			// Verificar se B e obrigado a comer para direita
			for (int i = 0; i < 6; i++) {
				for (int j = 0; j < 6; j++) {
					if (tabuleiros[i][j].getQuadrado() == 'B') {
						if (tabuleiros[i + 1][j + 1].getQuadrado() == 'V'
								&& tabuleiros[i + 2][j + 2].getQuadrado() == '-') {
							if (linhaDestino == (linhaOrigem + 2) && colunaDestino == (colunaOrigem + 2)
									&& tabuleiros[linhaDestino][colunaDestino].getQuadrado() == '-') {
								return true;
							} else {
								v = false;
							}
						}
					}
				}
			}
			// Verificar se B e obrigado a comer para esquerda
			for (int i = 0; i < 6; i++) {
				for (int j = 7; j > 1; j--) {
					if (tabuleiros[i][j].getQuadrado() == 'B') {
						if (tabuleiros[i + 1][j - 1].getQuadrado() == 'V'
								&& tabuleiros[i + 2][j - 2].getQuadrado() == '-') {
							if (linhaDestino == (linhaOrigem + 2) && colunaDestino == (colunaOrigem - 2)
									&& tabuleiros[linhaDestino][colunaDestino].getQuadrado() == '-') {
								return true;
							} else {
								v = false;
							}
						}
					}
				}
			}
			return v;
		}
		return true;
	}

}