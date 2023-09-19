package ar.edu.unlam.dominio;

public class Nota {

	private Double valor;
	private TipoDeNota tipo;

	public Nota(Double valor, TipoDeNota tipo) {
		super();
		this.valor = valor;
		this.tipo = tipo;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public TipoDeNota getTipo() {
		return tipo;
	}

	public void setTipo(TipoDeNota tipo) {
		this.tipo = tipo;
	}

}
