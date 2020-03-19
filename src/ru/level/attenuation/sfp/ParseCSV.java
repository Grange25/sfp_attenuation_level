package ru.level.attenuation.sfp;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import ru.level.attenuation.sfp.classes.BBU_Port;
import ru.level.attenuation.sfp.classes.RRU;

class ParseCSV {

    private static ArrayList<BBU_Port> arrBBUPorts = new ArrayList<>();
    private static ArrayList<RRU> arrRRU1 = new ArrayList<>();
    private static ArrayList<RRU> arrRRU2 = new ArrayList<>();
    private static String Header;

    @SuppressWarnings("resource")
    static void main(String args) throws Exception {
        CSVReader reader = new CSVReader(new FileReader(args), ',', '"', 1);

        arrBBUPorts.clear();
        arrRRU1.clear();
        arrRRU2.clear();

        ReadCSV(reader);

        for (int i = 0; i < arrRRU1.size(); i++) {
            arrRRU1.get(i).setSlot_RRU(arrRRU2.get(i).getSlot_RRU());
            arrRRU1.get(i).setPort_RRU(arrRRU2.get(i).getPort_RRU());
        }

        getArrString();
    }

    private static void ReadCSV(CSVReader reader) throws IOException {

        List<String[]> allRows = reader.readAll();
        for (String[] row : allRows) {
            if (row.length == 4) {
                Header = row[0];
            }
            if (row.length >= 27) {
                if (!row[1].trim().equals("Subrack No.") && !row[26].trim().equals("NULL")) {

                    if (!row[1].trim().equals("Topo Type") && !row[1].trim().equals("CHAIN") && Integer.parseInt(row[1].trim()) < 1) {
                        int subRack_BBU = Integer.parseInt(row[1].trim());
                        int slot_BBU = Integer.parseInt(row[2].trim());
                        int port_BBU = Integer.parseInt(row[4].trim());
                        int tx_BBU = Integer.parseInt(row[27].trim());
                        int rx_BBU = Integer.parseInt(row[28].trim());
                        BBU_Port bbuPorts = new BBU_Port(Header, subRack_BBU, slot_BBU, port_BBU, tx_BBU, rx_BBU);
                        arrBBUPorts.add(bbuPorts);
                    }

                    int subRack_RRU;
                    int slot_RRU;
                    int port_RRU;
                    if (!row[1].trim().equals("Topo Type") && !row[1].trim().equals("CHAIN") && Integer.parseInt(row[1].trim()) > 1) {
                        subRack_RRU = Integer.parseInt(row[1].trim());
                        slot_RRU = Integer.parseInt(row[2].trim());
                        port_RRU = Integer.parseInt(row[4].trim());
                        int tx_RRU = Integer.parseInt(row[27].trim());
                        int rx_RRU = Integer.parseInt(row[28].trim());
                        RRU rru = new RRU(Header, subRack_RRU, slot_RRU, port_RRU, tx_RRU, rx_RRU);
                        arrRRU1.add(rru);
                    }

                    if (!row[0].trim().equals("Chain No.") && Integer.parseInt(row[0].trim()) > 1) {
                        subRack_RRU = (Integer.parseInt(row[0].trim()));
                        slot_RRU = (Integer.parseInt(row[5].trim()));
                        port_RRU = (Integer.parseInt(row[6].trim()));
                        RRU rru = new RRU(Header, subRack_RRU, slot_RRU, port_RRU, 0, 0);
                        arrRRU2.add(rru);
                    }
                }
            }
        }
    }

    static ArrayList<String> getArrString() {

        ArrayList<String> arrString = new ArrayList<>();

        arrString.add("\n\n" + Header + "\n\n");

        for (int i = 0; i < arrRRU1.size(); i++) {
            for (RRU rru : arrRRU1) {
                if (arrBBUPorts.get(i).getSlot_BBU() == rru.getSlot_RRU() && arrBBUPorts.get(i).getPort_BBU() == rru.getPort_RRU()) {

                    int ia = rru.getSubRack_RRU();

                    int jb = arrBBUPorts.get(i).getSlot_BBU();
                    int jc = arrBBUPorts.get(i).getPort_BBU();
                    int jd = arrBBUPorts.get(i).getTx_BBU();
                    int ji = rru.getRx_RRU();
                    int jf = arrBBUPorts.get(i).getRx_BBU();
                    int jg = rru.getTx_RRU();

                    int la = jd - ji;
                    int lb = jf - jg;


                    String s1, s2;
                    if (la > 400 || la < -400) {
                        la = (la < 0) ? la * -1 : la;
                        s1 = String.format("BBU(%d %d) Tx= %.2f dbm --(%.2f dbm)--> Rx= %.2f dbm RRU (%d) !NOK!%n", jb, jc, (jd * 0.01), (la * 0.01), (ji * 0.01), ia);
                    } else {
                        la = (la < 0) ? la * -1 : la;
                        s1 = String.format("BBU(%d %d) Tx= %.2f dbm --(%.2f dbm)--> Rx= %.2f dbm RRU (%d)%n", jb, jc, (jd * 0.01), (la * 0.01), (ji * 0.01), ia);
                    }

                    if (lb > 400 || lb < -400) {
                        lb = (lb < 0) ? lb * -1 : lb;
                        s2 = String.format("BBU(%d %d) Rx= %.2f dbm <--(%.2f dbm)-- Tx= %.2f dbm RRU (%d) !NOK!%n%n", jb, jc, (jf * 0.01), (lb * 0.01), (jg * 0.01), ia);
                    } else {
                        lb = (lb < 0) ? lb * -1 : lb;
                        s2 = String.format("BBU(%d %d) Rx= %.2f dbm <--(%.2f dbm)-- Tx= %.2f dbm RRU (%d)%n%n", jb, jc, (jf * 0.01), (lb * 0.01), (jg * 0.01), ia);
                    }
                    arrString.add(s1 + s2);
                }
            }
        }
        return arrString;
    }
}
