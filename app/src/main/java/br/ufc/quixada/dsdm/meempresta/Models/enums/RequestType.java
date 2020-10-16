package br.ufc.quixada.dsdm.meempresta.Models.enums;

public enum RequestType {

    REQUEST_TYPE(0, "Request_Type"),

    REQUEST_BORROW_TOOL(1, "Empréstimo"),
    REQUEST_HELP(2, "Ajuda"),
    REQUEST_WORKOUT(3, "Exercício"),
    REQUEST_ASK_RIDE(4, "Carona"),
    REQUEST_SHARE_FOOD(5, "Comida"),
    REQUEST_INVITE_FRIENDS(6, "Vizinhos"),
    REQUEST_DONATE_GIFT(7, "Doar"),
    REQUEST_OTHER(8, "Outra");

    private final Integer code;
    private final String description;

    RequestType(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static RequestType toEnum(Integer code) {
        if (code == null) return null;

        for (RequestType x : RequestType.values())
            if (code.equals(x.getCode()))
                return x;

        throw new IllegalArgumentException("Código inválido: " + code);
    }
}
