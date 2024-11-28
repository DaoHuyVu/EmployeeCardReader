


import java.awt.HeadlessException;
import javax.smartcardio.*;
import java.util.List;
import javax.swing.JOptionPane;

public class SmartCard {
    public static final byte[] AID_APPLET = { (byte) 0x99, (byte) 0x88, (byte) 0x77, (byte) 0x66, (byte) 0x55, (byte) 0x00};
    private Card card;
    private TerminalFactory factory;
    private CardChannel channel;
    private CardTerminal terminal;
    private List<CardTerminal> terminals;
    private ResponseAPDU response;

    public static void main(String[] args) {}

    public boolean connectCard () {
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
                case "6400" -> {
                    JOptionPane.showMessageDialog(null, "Thẻ đã bị vô hiệu hóa");
                    return true;
                }
                default -> {
                    return false;
                }
            }
        } catch (HeadlessException | CardException ex) {}
        
        return false;      
    }
    public boolean disconnect() {
        try {
            card.disconnect(false);
            return true;
        } catch (CardException e){
            System.out.println("Lỗi :" + e) ;
        }
        return false;
    }
}