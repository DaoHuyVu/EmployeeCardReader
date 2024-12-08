import java.awt.HeadlessException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

import javax.smartcardio.*;

import java.util.Arrays;
import java.util.HexFormat;
import java.util.List;
import javax.swing.JOptionPane;

public class SmartCard {
    public static final byte[] AID_APPLET = { (byte) 0x11, (byte) 0x22, (byte) 0x33, (byte) 0x44, (byte) 0x55, (byte) 0x00};
    public static final byte[] DEFAULT_PIN = { (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff};
    private Card card ;
    private TerminalFactory factory;
    private CardChannel channel;
    private CardTerminal terminal ;
    private List<CardTerminal> terminals;
    private ResponseAPDU response;
    
    public boolean connectCard() {
        try {
            factory = TerminalFactory.getDefault();
            terminals = factory.terminals().list();
            terminal = terminals.get(0);
            card = terminal.connect("T=0");
            channel = card.getBasicChannel();

            if (channel == null){
                return false;
            }   

            response = channel.transmit(new CommandAPDU (0x00, (byte) 0xA4, 0x04, 0x00, AID_APPLET));
            String check = Integer.toHexString(response.getSW());

            switch (check) {
                case "9000" -> {
                    return true;
                }   
                default -> {
                    return false;
                }
            }
        } catch (HeadlessException | CardException ex) {}
        return false;      
    }
    
    public boolean disconnectCard() {
        try {
            card.disconnect(false);
            return true;
        } catch (CardException e){
            System.out.println("Lỗi :" + e) ;
        }
        return false;
    }

    public boolean initPIN() {
        if(checkPIN(DEFAULT_PIN)){
            return true;
        }
        return false;
    }

    public boolean changePIN(byte[] PIN) {
        try {
            response = channel.transmit(new CommandAPDU(0xA0, 0x10, 0x00, 0x00, PIN));
            String check = Integer.toHexString(response.getSW());

            switch (check) {
                case "9000" -> {
                    return true;
                }   
                default -> {
                    return false;
                }
            }
        } catch (CardException e) {

        }
        return false;
    }

    public boolean checkPIN(byte[] PIN) {
        try {
            response = channel.transmit(new CommandAPDU(0xA0, 0x02, 0x00, 0x00, PIN));
            byte[] data = response.getData();
            String d = bytesToHex(data);
            String s = Integer.toHexString(response.getSW());
            String r = d.concat(s);

            switch (r) {
                case "009000" -> {
                    return true;
                }   
                default -> {
                    return false;
                }
            }
        } catch (CardException e) {

        }
        return false;
    }

    public String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X ", b));
        }
        return sb.toString().trim();
    }

    public byte[] stringToByteArray(String s) {
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            sb.append(String.format("%02x", (int) c));
        }

        String hexString = sb.toString();
        int length = hexString.length() / 2;
        byte[] byteArray = new byte[length];
        for (int i = 0; i < length; i++) {
            String hexPair = hexString.substring(i * 2, i * 2 + 2);
            byteArray[i] = (byte) Integer.parseInt(hexPair, 16); 
        }

        return byteArray;
    }
}