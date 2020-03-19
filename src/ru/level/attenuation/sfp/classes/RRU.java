package ru.level.attenuation.sfp.classes;

public class RRU extends BTS {

    private int SubRack_RRU;
    private int Slot_RRU;
    private int Port_RRU;
    private int Tx_RRU;
    private int Rx_RRU;

    public RRU(String BTS_name, int subRack_RRU, int slot_RRU, int port_RRU, int tx_RRU, int rx_RRU) {
        super(BTS_name);
        SubRack_RRU = subRack_RRU;
        Slot_RRU = slot_RRU;
        Port_RRU = port_RRU;
        Tx_RRU = tx_RRU;
        Rx_RRU = rx_RRU;
    }

    public int getSubRack_RRU() {
        return SubRack_RRU;
    }

    public void setSubRack_RRU(int subRack_RRU) {
        SubRack_RRU = subRack_RRU;
    }

    public int getSlot_RRU() {
        return Slot_RRU;
    }

    public void setSlot_RRU(int slot_RRU) {
        Slot_RRU = slot_RRU;
    }

    public int getPort_RRU() {
        return Port_RRU;
    }

    public void setPort_RRU(int port_RRU) {
        Port_RRU = port_RRU;
    }

    public int getTx_RRU() {
        return Tx_RRU;
    }

    public void setTx_RRU(int tx_RRU) {
        Tx_RRU = tx_RRU;
    }

    public int getRx_RRU() {
        return Rx_RRU;
    }

    public void setRx_RRU(int rx_RRU) {
        Rx_RRU = rx_RRU;
    }

    @Override
    public String display() {
        return "RRU{" +
                "BTS_name=" + super.getBTS_name() +
                ", SubRack_RRU=" + SubRack_RRU +
                ", Slot_RRU=" + Slot_RRU +
                ", Port_RRU=" + Port_RRU +
                ", Tx_RRU=" + Tx_RRU +
                ", Rx_RRU=" + Rx_RRU +
                "} ";
    }
}
