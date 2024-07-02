package GUI;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GUIUtils {
    public static int getScreenWidth()
    {
        return Toolkit.getDefaultToolkit().getScreenSize().width;
    }
    public static int getScreenHeight()
    {
        return Toolkit.getDefaultToolkit().getScreenSize().height;
    }
    @SafeVarargs
    public static <T extends Component> void addComponents(Box box, T... components) {
        for (T component : components) {
            box.add(component);
        }
    }
    public static void setWindowIcon(JFrame jFrame, String iconPath) {
        java.net.URL imgURL = GUIUtils.class.getResource(iconPath);
        if (imgURL != null) {
            ImageIcon arrowIcon = new ImageIcon(imgURL);
            jFrame.setIconImage(arrowIcon.getImage());
        } else {
            JOptionPane.showMessageDialog(jFrame, "Icon image not found.");
        }
    }
    public static ImageIcon getImageIcon(String imagePath) throws IOException {
        java.net.URL imgURL = GUIUtils.class.getResource(imagePath);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            JOptionPane.showMessageDialog(null, "Icon image not found.");
        }
        return null;
    }
}
