package Model;

import java.awt.HeadlessException;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import javax.smartcardio.*;
import java.util.List;

public class SmartCard {
    public static final byte[] AID_APPLET = { (byte) 0x11, (byte) 0x22, (byte) 0x33, (byte) 0x44, (byte) 0x55, (byte) 0x00};
    private Card card ;
    private TerminalFactory factory;
    private CardChannel channel;
    private CardTerminal terminal ;
    private List<CardTerminal> terminals;
    private ResponseAPDU response;
    private final SecureRandom random = new SecureRandom();
    public String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X ", b));
        }
        return sb.toString().trim();
    }

    public byte[] stringToByteArray(String s) {
        byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
        return bytes;
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
    
    // Mo khoa the
    public boolean unlockCard() {
        try {
            response = channel.transmit(new CommandAPDU(0xA0, 0x03, 0x00, 0x00));
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
    
    // Kiem tra tinh trang khoa the
    public boolean checkLocked() {
        try {
            response = channel.transmit(new CommandAPDU(0xA0, 0x04, 0x00, 0x00));
            byte[] data = response.getData();
            String d = bytesToHex(data);
            switch (d) {
                case "01" -> {     
                    return true;     //the bi khoa
                }   
                case "00" -> {
                    return false;   //the khong bi khoa
                }   
            }
        } catch (CardException e) {

        }
        return false;
    }

    // Doi PIN
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

    // Kiem tra PIN nhap vao
    public String checkPIN(byte[] PIN) {
        try {
            response = channel.transmit(new CommandAPDU(0xA0, 0x02, 0x00, 0x00, PIN));
            byte[] data = response.getData();
            String d = bytesToHex(data);
            String check = Integer.toHexString(response.getSW());
            String s2 = Integer.toHexString(response.getSW2());
            if(check.equals("9000")) {
                return d;
            } else {
                return s2;
            }
        } catch (CardException e) {

        }
        return "";
    }

    // Cap nhat thong tin 
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

    // Tao thong tin cho the moi
    public byte[] initInfo(byte[] info) {
        try {
            response = channel.transmit(new CommandAPDU(0xA0, 0x00, 0x00, 0x00, info));
            String check = Integer.toHexString(response.getSW());
            switch (check) {
                case "9000" -> {
                    return response.getData();
                }   
                default -> {
                    return null;
                }
            }
        } catch (CardException e) {
            
        }
        return null;
    }

    // Lay thong tin
    public String getInfo() {
        try {
            response = channel.transmit(new CommandAPDU(0xA0, 0x05, 0x00, 0x00));
            byte[] data = response.getData();
            String d = bytesToHex(data).replaceAll(" ", "");
            byte[] bytes = hexStringToByteArray(d);
            String info = new String(bytes, StandardCharsets.UTF_8);
            String check = Integer.toHexString(response.getSW());
            if(check.equals("9000")) {
                return info;
            } else {
                return check;
            }
        } catch (CardException e) {
            
        }
        return null;
    }

    // Doi thong tin anh 
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

    // Lay thong tin anh
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

    public boolean deposit(byte[] cash) {
        try {
            response = channel.transmit(new CommandAPDU(0xA0, 0x13, 0x00, 0x00, cash));
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
    public boolean withdrawal(byte[] toPay){
         try {
            response = channel.transmit(new CommandAPDU(0xA0, 0x16, 0x00, 0x00, toPay));
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
    public long getBalance() {
        try {
            response = channel.transmit(new CommandAPDU(0xA0, 0x14, 0x00, 0x00));
            byte[] data = response.getData();
            StringBuilder sb = new StringBuilder();
            for(byte i : data){
                sb.append(i);
                System.out.println(i + " ");
            }
            
            return (data[3] & 0xFF) | ((data[2] & 0xFF) << 8) | ((data[1] & 0xFF) << 16) | ((data[0] & 0xFF) << 24);
        } catch (CardException e) {
            
        }
        return 0;
    }
    public String getID(){
        try{
             response = channel.transmit(new CommandAPDU(0xA0, 0x12, 0x00, 0x00));
            byte[] data = response.getData();
            StringBuilder sb = new StringBuilder();
            for(byte i : data){
                sb.append((char)i);
            }
            return sb.toString();
        }catch(CardException e){
            e.printStackTrace();
        }
        return null;
    }
    public Boolean verifySignedData(byte[] p) {
        try{
            byte [] nonce = new byte[32];
            random.nextBytes(nonce);
            response = channel.transmit(new CommandAPDU(0xA0, 0x11, 0x00, 0x00,nonce));
            byte[] signedRandom = response.getData();
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(p);
            PublicKey publicKey = keyFactory.generatePublic(keySpec);
            
            Signature verifier = Signature.getInstance("SHA256withRSA");
            verifier.initVerify(publicKey);
            verifier.update(nonce);
            return verifier.verify(signedRandom);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public Boolean checkCardInit(){
        try{
            response = channel.transmit(new CommandAPDU(0xA0, 0x17, 0x00, 0x00));
            return Integer.toHexString(response.getSW()).equals("9000");
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    
}
