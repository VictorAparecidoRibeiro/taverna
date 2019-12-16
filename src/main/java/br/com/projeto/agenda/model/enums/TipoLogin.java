package br.com.projeto.agenda.model.enums;

public enum TipoLogin {

    MASTER("SUPER ULTRA BLASTER MASTRE MAGO"),
    NORMAL("Normal");
        
    private String descricao;    

    TipoLogin(String descricao) {
        this.setDescricao(descricao);        
    }
    
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
