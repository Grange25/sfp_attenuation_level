package ru.level.attenuation.sfp.classes;

public class Result {

    private String bts_name;
    private int subRack_rru;
    private int slot_bbu;
    private int port_bbu;
    private double tx_bbu;
    private double rx_rru;
    private double rx_bbu;
    private double tx_rru;

    public Result(String bts_name, int slot_bbu, int port_bbu, double tx_bbu, double rx_bbu, int subRack_rru, double tx_rru, double rx_rru) {
        this.bts_name = bts_name;
        this.subRack_rru = subRack_rru;
        this.slot_bbu = slot_bbu;
        this.port_bbu = port_bbu;
        this.tx_bbu = tx_bbu;
        this.rx_rru = rx_rru;
        this.rx_bbu = rx_bbu;
        this.tx_rru = tx_rru;
    }

    public String getBts_name() {
        return bts_name;
    }

    public String res() {

        double difference1 = tx_bbu - rx_rru;
        double difference2 = rx_bbu - tx_rru;

        String s1, s2;
        if (difference1 > 4 || difference1 < -4) {
            difference1 = (difference1 < 0) ? difference1 * -1 : difference1;
            s1 = String.format("BBU(%d %d) Tx= %.2f dbm --(%.2f dbm)--> Rx= %.2f dbm RRU (%d) !NOK!%n", slot_bbu, port_bbu, tx_bbu, difference1, rx_rru, subRack_rru);
        } else {
            difference1 = (difference1 < 0) ? difference1 * -1 : difference1;
            s1 = String.format("BBU(%d %d) Tx= %.2f dbm --(%.2f dbm)--> Rx= %.2f dbm RRU (%d)%n", slot_bbu, port_bbu, tx_bbu, difference1, rx_rru, subRack_rru);
        }

        if (difference2 > 4 || difference2 < -4) {
            difference2 = (difference2 < 0) ? difference2 * -1 : difference2;
            s2 = String.format("BBU(%d %d) Rx= %.2f dbm <--(%.2f dbm)-- Tx= %.2f dbm RRU (%d) !NOK!%n", slot_bbu, port_bbu, rx_bbu, difference2, tx_rru, subRack_rru);

        } else {
            difference2 = (difference2 < 0) ? difference2 * -1 : difference2;
            s2 = String.format("    BBU(%d %d) Rx= %.2f dbm <--(%.2f dbm)-- Tx= %.2f dbm RRU (%d)%n", slot_bbu, port_bbu, rx_bbu, difference2, tx_rru, subRack_rru);
        }
        return s1 + s2;
    }
}
