package windows;

import javax.swing.*;
import java.awt.*;

public class ProgressDialog extends JDialog {
    private final JLabel labelStatus;
    private final JProgressBar progressBar;

    public ProgressDialog (Frame parentFrame){
        super (parentFrame, "Games checking progress", false);
        setLayout(new BorderLayout(15, 15));
        setSize(400, 140);
        setLocationRelativeTo(parentFrame);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE); //prevents accidental closing.

        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        labelStatus = new JLabel("searching Steam...", SwingConstants.CENTER);
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);

        panel.add(labelStatus);
        panel.add(progressBar);

        add(panel, BorderLayout.CENTER);

    }

    public void updateProgress (String gameName, int currentGame, int total){
        labelStatus.setText("Checking (" + currentGame + "/" + total + "): " + gameName);
        progressBar.setMaximum(total);
        progressBar.setValue(currentGame);
        progressBar.setString(currentGame + " of " + total + " (" + (int)(((double)currentGame / total) * 100) + "%)");
}

}
