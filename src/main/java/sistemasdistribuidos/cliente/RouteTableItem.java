package sistemasdistribuidos.cliente;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class RouteTableItem {
    private SimpleIntegerProperty id;
    private SimpleStringProperty origem;
    private SimpleStringProperty destino;
    private SimpleIntegerProperty distancia;
    private SimpleStringProperty direcao;
    private SimpleStringProperty obs;

    public RouteTableItem(Segment segment) {
        this.id = new SimpleIntegerProperty(segment.getId());
        this.origem = new SimpleStringProperty(segment.getOrigin().getName());
        this.destino = new SimpleStringProperty(segment.getDestiny().getName());
        this.distancia = new SimpleIntegerProperty(segment.getDistance());
        this.direcao = new SimpleStringProperty(segment.getDirection());
        this.obs = new SimpleStringProperty(segment.getObs());
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getOrigem() {
        return origem.get();
    }

    public SimpleStringProperty origemProperty() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem.set(origem);
    }

    public String getDestino() {
        return destino.get();
    }

    public SimpleStringProperty destinoProperty() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino.set(destino);
    }

    public int getDistancia() {
        return distancia.get();
    }

    public SimpleIntegerProperty distanciaProperty() {
        return distancia;
    }

    public void setDistancia(int distancia) {
        this.distancia.set(distancia);
    }

    public String getDirecao() {
        return direcao.get();
    }

    public SimpleStringProperty direcaoProperty() {
        return direcao;
    }

    public void setDirecao(String direcao) {
        this.direcao.set(direcao);
    }

    public String getObs() {
        return obs.get();
    }

    public SimpleStringProperty obsProperty() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs.set(obs);
    }
}
