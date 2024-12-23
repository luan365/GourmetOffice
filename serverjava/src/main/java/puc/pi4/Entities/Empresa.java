package puc.pi4.Entities;

public class Empresa {
    private String nome;
    private String cnpj;
    private String email;
    private String senha;
    private String telefone;
    private String endereco;
    private String tipo;
    
    public Empresa(String nome,String cnpj,String email,String senha,String telefone, String endereco, String tipo) throws Exception{

        if(nome.length()<=0 || nome ==null)throw new Exception("Nome invalido");
        if(cnpj.length()!= 14 || !cnpj.matches("[0-9]+") || cnpj ==null) throw new Exception("CNPJ invalido");
        if(email.length()<=0 || email ==null) throw new Exception("Email invalido"); 
        if(senha.length()<=8 || senha == null) throw new Exception("Senha invalida");
        if(telefone.length()<=0 || telefone == null) throw new Exception("Telefone invalido");
        if(endereco.length()<=0 || endereco == null) throw new Exception("Endereco invalido");
        



        this.nome = nome;
        this.cnpj = cnpj;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
        this.endereco = endereco;
        this.tipo = tipo;
    }

    public String[] validate(){
        String[] ret = {"",""};

        if(this.nome ==null || this.nome.length()<=0 ){  ret[0]="Nome invalido"; ret[1]="false"; return ret;}
        if(this.cnpj ==null || !this.cnpj.matches("[0-9]+") || this.cnpj.length()!= 14  ){  ret[0]="CNPJ invalido"; ret[1]="false"; return ret;}
        if(this.email ==null ||this.email.length()<=0  ){  ret[0]="Email invalido"; ret[1]="false"; return ret;}
        if(this.senha ==null ||this.senha.length()<=8 ){  ret[0]="Senha invalida"; ret[1]="false"; return ret;}
        if(this.telefone ==null  || this.telefone.length()<=0 ){  ret[0]="Telefone invalido"; ret[1]="false"; return ret;}
        if(this.endereco ==null  || this.endereco.length()<=0 ){  ret[0]="Endereco invalido"; ret[1]="false"; return ret;}

        ret[0]="Tudo normal"; ret[1]="true"; return ret;
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
        ret += ("Endereco: "+this.endereco + "\n");
        ret += ("Tipo: "+this.tipo + "\n}");

        return ret;
    }


}
