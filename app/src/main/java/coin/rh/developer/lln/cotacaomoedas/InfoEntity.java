package coin.rh.developer.lln.cotacaomoedas;

public class InfoEntity {

    private int Logo;
    private String Moeda;
    private String Data;
    private String Fonte;
    private int Position;

    public InfoEntity(int logo_src, String moeda, String data, String fonte, int position){
        this.Logo = logo_src;
        this.Moeda = moeda;
        this.Data = data;
        this.Fonte = fonte;
        this.Position = position;
    }


    public int getLogo() {
        return Logo;
    }

    public void setLogo(int logo) {
        Logo = logo;
    }

    public String getMoeda() {
        return Moeda;
    }

    public void setMoeda(String moeda) {
        Moeda = moeda;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

    public String getFonte() {
        return Fonte;
    }

    public void setFonte(String fonte) {
        Fonte = fonte;
    }

    public void setPosition(int position){
        Position = position;
    }

    public int getPosition(){
        return Position;
    }
}
