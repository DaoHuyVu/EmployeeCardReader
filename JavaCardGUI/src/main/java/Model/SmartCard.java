package Model;

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
    public byte[] hexStringToByteArray(String hex) {
        int l = hex.length();
        byte[] data = new byte[l / 2];
        for (int i = 0; i < l; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }

    public boolean updateInfo(byte[] info) {
        try {
            response = channel.transmit(new CommandAPDU(0xA0, 0x09, 0x00, 0x00, info));
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

    public boolean initInfo() {
        try {
            response = channel.transmit(new CommandAPDU(0xA0, 0x05, 0x00, 0x00));
            byte[] data = response.getData();
            String d = bytesToHex(data).replaceAll(" ", "");
            String s = Integer.toHexString(response.getSW());
            String r = d.concat(s);

            switch (r) {
                case "242424249000" -> {
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

    public String getInfo() {
        try {
            response = channel.transmit(new CommandAPDU(0xA0, 0x05, 0x00, 0x00));
            byte[] data = response.getData();
            String d = bytesToHex(data).replaceAll(" ", "");
            byte[] bytes = hexStringToByteArray(d);
            String info = new String(bytes, StandardCharsets.UTF_8);
            return info;
        } catch (CardException e) {
            
        }
        return null;
    }

    public void changeImage(byte[] image) {
        try {
            int blockSize = 255;
            int offset = 0;
            while (offset < image.length) {
                int length = Math.min(blockSize, image.length - offset);
    
                response = channel.transmit(new CommandAPDU(0xA0, 0x07, 0x00, offset == 0 ? 0x00 : 0x01, image, offset, length));
             offset += length;
            }
            // String check = Integer.toHexString(response.getSW());

            // switch (check) {
            //     case "9000" -> {
            //         return true;
            //     }   
            //     default -> {
            //         return false;
            //     }
            // }

        } catch (CardException e) {
            
        }
        // return false;
    }

    public String getImage() {
        try {
            response = channel.transmit(new CommandAPDU(0xA0, 0x08, 0x00, 0x00));
            byte[] data = response.getData();
            String d = bytesToHex(data).replaceAll(" ", "");
            return d;
        } catch (CardException e) {
            
        }
        return null;
    }

}
