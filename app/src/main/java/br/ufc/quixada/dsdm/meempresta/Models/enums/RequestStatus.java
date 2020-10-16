package br.ufc.quixada.dsdm.meempresta.Models.enums;

public enum RequestStatus {

    NEW(0, "Novo"),
    DONE(1, "Concluído"),
    CANCELED(2, "Cancelado"),
    PENDING(3, "Pendente");

    Integer code;
    String description;

    RequestStatus(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static RequestStatus toEnum(Integer code) {
        if (code == null) return null;

        for (RequestStatus x : RequestStatus.values())
            if (code.equals(x.getCode()))
                return x;

        throw new IllegalArgumentException("Código inválido: " + code);
    }

}
