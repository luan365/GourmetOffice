package puc.pi4.Entities;

public class Cozinha {
    private String nome;
    private String cnpj;
    private String email;
    private String senha;
    private String telefone;
    private String endereco;
    
    public Cozinha(String nome,String cnpj,String email,String senha,String telefone, String endereco){
        this.nome = nome;
        this.cnpj = cnpj;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
        this.endereco = endereco;
    }
}
