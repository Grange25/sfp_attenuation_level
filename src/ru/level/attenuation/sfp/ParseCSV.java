package ru.level.attenuation.sfp;

import au.com.bytecode.opencsv.CSVReader;
import ru.level.attenuation.sfp.classes.BBU_Port;
import ru.level.attenuation.sfp.classes.RRU;
import ru.level.attenuation.sfp.classes.Result;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class ParseCSV {

    private static String Header;

    private static ArrayList<Result> arrResult = new ArrayList<>();
    private static ArrayList<String> arrScanDoc = new ArrayList<>();
    private static ArrayList<String> RESULT = new ArrayList<>();

    private static ArrayList<BBU_Port> arrBBUPorts = new ArrayList<>();
    private static ArrayList<RRU> arrRRU1 = new ArrayList<>();
    private static ArrayList<RRU> arrRRU2 = new ArrayList<>();


    @SuppressWarnings("resource")
    static void main(String args) throws Exception {
        CSVReader reader = new CSVReader(new FileReader(args), ',', '"', 1);

        RESULT.clear();
        arrResult.clear();
        arrScanDoc.clear();
        arrBBUPorts.clear();
        arrRRU1.clear();
        arrRRU2.clear();

        ScanDoc(reader);

    }

    private static void filt(String[] row) {
        if (row.length == 4) {
            Header = row[0];
        }
        if (row.length == 27 || row.length == 34) {
            if (!row[1].trim().equals("Subrack No.") && !row[26].trim().equals("NULL")) {

                if (row[0].trim().equals("0") && row[1].trim().equals("0") && row[5].trim().equals("In Position")) {
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
                if (row[0].trim().equals("0") && row[5].trim().equals("In Position") && Integer.parseInt(row[1].trim()) > 1) {
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

    private static void ScanDoc(CSVReader reader) {
        try {
            List<String[]> allRows = reader.readAll();
            for (String[] row : allRows) {

                if (Arrays.toString(row).trim().contains("BTS_")) {
                    String[] s = Arrays.toString(row).trim().split(",");
                    arrScanDoc.add(s[0]);
                }

                filt(row);
            }
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    private static void getArrString(ArrayList<BBU_Port> arrBBUPorts, ArrayList<RRU> arrRRU1) {

        for (int i = 0; i < arrRRU1.size(); i++) {
            for (RRU rru : arrRRU1) {
                if (arrBBUPorts.get(i).getSlot_BBU() == rru.getSlot_RRU()) {
                    if (arrBBUPorts.get(i).getPort_BBU() == rru.getPort_RRU()) {
                        if (arrBBUPorts.get(i).getBTS_name().equals(rru.getBTS_name())) {

                            String bts_name = arrBBUPorts.get(i).getBTS_name();
                            int subRack_rru = rru.getSubRack_RRU();
                            int slot_bbu = arrBBUPorts.get(i).getSlot_BBU();
                            int port_bbu = arrBBUPorts.get(i).getPort_BBU();
                            double tx_bbu = arrBBUPorts.get(i).getTx_BBU() * 0.01;
                            double rx_rru = rru.getRx_RRU() * 0.01;
                            double rx_bbu = arrBBUPorts.get(i).getRx_BBU() * 0.01;
                            double tx_rru = rru.getTx_RRU() * 0.01;

                            Result result = new Result(bts_name, subRack_rru, slot_bbu, port_bbu, tx_bbu, rx_rru, rx_bbu, tx_rru);
                            arrResult.add(result);
                        }
                    }
                }
            }
        }
    }

    static ArrayList<String> getRESULT() {

        for (int i = 0; i < arrRRU1.size(); i++) {
            arrRRU1.get(i).setSlot_RRU(arrRRU2.get(i).getSlot_RRU());
            arrRRU1.get(i).setPort_RRU(arrRRU2.get(i).getPort_RRU());
        }

        getArrString(arrBBUPorts, arrRRU1);

        List<String> set = arrScanDoc.stream().distinct().collect(Collectors.toList());

        for (String s : set) {
            if (s.contains("BTS_")) {
                if (s.contains("]")) {
                    String head = "\n" + s + "\n";
                    RESULT.add(head);
                }
            }

            for (Result result : arrResult) {
                String s2 = "[" + result.getBts_name() + "]";
                if (s2.equals(s)) {
                    RESULT.add(result.res());
                }
            }
        }
        return RESULT;
    }
}
