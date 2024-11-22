package puc.pi4.Entities;

public class Cozinha {
    private String nome;
    private String cnpj;
    private String email;
    private String senha;
    private String telefone;
    private String endereco;
    private String descricao;
    private String tipo;

   
    
    public Cozinha(String nome,String cnpj,String email,String senha,String telefone, String endereco,String descricao, String tipo){
        this.nome = nome;
        this.cnpj = cnpj;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
        this.endereco = endereco;
        this.descricao = descricao;
        this.tipo = tipo;

        
    }

    public String getCNPJ(){
        return this.cnpj;
    }

    @Override
    public String toString(){
        String ret = "{ COZINHA\n";
        ret += ("Nome: "+this.nome + "\n");
        ret += ("CNPJ: "+this.cnpj + "\n");
        ret += ("Email: "+this.email + "\n");
        ret += ("Telefone: "+this.telefone + "\n");
        ret += ("Endereco: "+this.endereco + "\n");
        ret += ("Descrição: "+this.descricao + "\n");
        ret += ("Tipo: "+this.tipo + "\n}");
        return ret;
    }
}
