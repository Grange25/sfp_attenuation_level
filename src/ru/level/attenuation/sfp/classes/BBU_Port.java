package ru.level.attenuation.sfp.classes;

public class BBU_Port extends BTS{

    private int SubRack_BBU;
    private int Slot_BBU;
    private int Port_BBU;
    private int Tx_BBU;
    private int Rx_BBU;

    public BBU_Port(String BTS_name, int subRack_BBU, int slot_BBU, int port_BBU, int tx_BBU, int rx_BBU) {
        super(BTS_name);
        SubRack_BBU = subRack_BBU;
        Slot_BBU = slot_BBU;
        Port_BBU = port_BBU;
        Tx_BBU = tx_BBU;
        Rx_BBU = rx_BBU;
    }

    public int getSubRack_BBU() {
        return SubRack_BBU;
    }

    public void setSubRack_BBU(int subRack_BBU) {
        SubRack_BBU = subRack_BBU;
    }

    public int getSlot_BBU() {
        return Slot_BBU;
    }

    public void setSlot_BBU(int slot_BBU) {
        Slot_BBU = slot_BBU;
    }

    public int getPort_BBU() {
        return Port_BBU;
    }

    public void setPort_BBU(int port_BBU) {
        Port_BBU = port_BBU;
    }

    public int getTx_BBU() {
        return Tx_BBU;
    }

    public void setTx_BBU(int tx_BBU) {
        Tx_BBU = tx_BBU;
    }

    public int getRx_BBU() {
        return Rx_BBU;
    }

    public void setRx_BBU(int rx_BBU) {
        Rx_BBU = rx_BBU;
    }

    @Override
    public String display() {

        return "BBU_Ports{" +
                "BTS_name=" + super.getBTS_name() +
                ", SubRack_BBU=" + SubRack_BBU +
                ", Slot_BBU=" + Slot_BBU +
                ", Port_BBU=" + Port_BBU +
                ", Tx_BBU=" + Tx_BBU +
                ", Rx_BBU=" + Rx_BBU +
                '}';
    }
}
