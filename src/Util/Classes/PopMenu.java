package Util.Classes;

import View.Home.Frm_Principal;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class PopMenu {

    //Cria��o da icone, instancia da classe java java.awt.TrayIcon
    static TrayIcon trayIcon = null;

    //Referencia � instancia "J" da classe Janelinha, para acesso da bandeja.
    static Frm_Principal pai =new Frm_Principal();

    public PopMenu() {
        super();
        criaAreaNotificacao();
        trayIcon.displayMessage("Support Manager", "Dê um Clique duplo para abrir!", TrayIcon.MessageType.INFO);
    }

    static void criaAreaNotificacao() {

        //Verifica se n�o � poss�vel trabalhar com "TrayIcon"
        if (!SystemTray.isSupported()) {
            System.out.println("Não da pra fazer, nem tenta!");
            return;
        }

        //Instancia��o de um objeto java.awt.PopupMenu
        final PopupMenu pop = new PopupMenu();
        //Instancia��o do objeto java.awt.SystemTray;
        final SystemTray tray = SystemTray.getSystemTray();
        //Cria��o do objeto TrayIcon, informando uma imagem e um t�tulo
        trayIcon = new TrayIcon(createImage("../Img/icone.png", ""));

        //set a frase que aparece quando você passa o mouse.
        trayIcon.setToolTip("Support Manager");

        //Cria��o dos itens do Menu Popup
        MenuItem menuAbrir = new MenuItem("Abrir");
        MenuItem menuFechar = new MenuItem("Fechar");

        //Adicionando os itens ao Menu Popup
        pop.add(menuAbrir);
        pop.addSeparator();
        pop.add(menuFechar);

        //Setando o menu padrao no TrayIcon, que por acaso a este logo acima.
        trayIcon.setPopupMenu(pop);
        trayIcon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pai.setVisible(true);
                //Esta linha deixa a janela sobre as outras, caso ela apare�a minimizada.
                pai.setExtendedState(JFrame.MAXIMIZED_BOTH);

                try {
                    //Agora basta remover (ou esconder) o icone da area de Notificao
                    
                    tray.remove(trayIcon);
                    trayIcon = null;
                    
                    //Limpando a referencia ao Systemtray da classe Janelinha
                    finalize();
                } catch (Throwable e1) {
                    e1.printStackTrace();
                }
            }
        });

        //Adicionando o Icone na Area de Notificacao, como o menu ja esta dentro do icone,
        //ira junto tambem.
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("Não consegui pra fazer isso...");
        }

        //opção do menu "abrir"
        menuAbrir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Simplesmente deixa-se a janela vis�vel novamente.
                pai.setVisible(true);
                //Esta linha deixa a janela sobre as outras, caso ela apare�a minimizada.
                pai.setExtendedState(JFrame.MAXIMIZED_BOTH);

                try {
                    //Agora basta remover (ou esconder) o �cone da �rea de Notifica��o
                    tray.remove(trayIcon);
                    trayIcon=null;
                    //Limpando a refer�ncia ao Systemtray da classe Janelinha
                    finalize();
                } catch (Throwable e1) {
                    e1.printStackTrace();
                }
            }
            
        });
        
        //opção do menu fechar
        menuFechar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    trayIcon.displayMessage(null, "Fechando...", TrayIcon.MessageType.NONE);
                    Thread.sleep(1000);
                    pai.dispose();
                    System.exit(0);
                } catch (InterruptedException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao fechar");
                }
            }
        });
    }

    protected static Image createImage(String path, String description) {
        URL imageURL = PopMenu.class.getResource(path);

        if (imageURL == null) {
            System.err.println("Caminho não encontrado: " + path);
            return null;
        } else {
            return (new ImageIcon(imageURL, description)).getImage();
        }
    }

}
