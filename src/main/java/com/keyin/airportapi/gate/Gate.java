package com.keyin.airportapi.gate;

public class Gate {
    private Long gateId;
    private String gateName;
    private String terminal;

    public Gate() {}

    public Gate(Long gateId, String gateName, String terminal) {
        this.gateId = gateId;
        this.gateName = gateName;
        this.terminal = terminal;
    }

    public Long getGateId() {
        return gateId;
    }

    public void setGateId(Long gateId) {
        this.gateId = gateId;
    }

    public String getGateName() {
        return gateName;
    }

    public void setGateName(String gateName) {
        this.gateName = gateName;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }
}
