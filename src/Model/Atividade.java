package Model;

public class Atividade {
    private int id;
    private String titulo;
    private String tipo;
    private double horasComplementares;
    private InstituicaoParceira instituicao;

    public Atividade() {
    }

    public Atividade(int id, String titulo, String tipo, double horasComplementares, InstituicaoParceira instituicao) {
        this.id = id;
        this.titulo = titulo;
        this.tipo = tipo;
        this.horasComplementares = horasComplementares;
        this.instituicao = instituicao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getHorasComplementares() {
        return horasComplementares;
    }

    public void setHorasComplementares(double horasComplementares) {
        this.horasComplementares = horasComplementares;
    }

    public InstituicaoParceira getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(InstituicaoParceira instituicao) {
        this.instituicao = instituicao;
    }
}