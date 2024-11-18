package puc.pi4.Entities;

public class Empresa {
    private String nome;
    private String cnpj;
    private String email;
    private String senha;
    private String telefone;
    private String endereco;
    
    
    public Empresa(String nome,String cnpj,String email,String senha,String telefone, String endereco){
        this.nome = nome;
        this.cnpj = cnpj;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    public String getCNPJ(){
        return this.cnpj;
    }

    @Override
    public String toString(){
        String ret = "{ EMPRESA\n" ;
        ret += ("Nome: "+this.nome + "\n");
        ret += ("CNPJ: "+this.cnpj + "\n");
        ret += ("Email: "+this.email + "\n");
        ret += ("Telefone: "+this.telefone + "\n");
        ret += ("Endereco: "+this.endereco + "\n}");
        

        return ret;
    }
}
