package windows;

import SteamActions.ProgressListener;
import SteamActions.SearchForGames;
import SteamActions.SearchForGamesTest;
import Utilities.HyperlinkRenderer;
import com.Objects.GameItem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static Utilities.Utility.getDesktopPath;

public class MainWindow extends JFrame {

    private JTextField txtFilePath;
    private JTextArea infoArea;
    private JButton searchButton;
    private JButton startScrapButton;

    private JTable resultsTable;
    private DefaultTableModel tableModel;



    public MainWindow(){
        setTitle("Steam Discount searcher");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));


        setMinimumSize(new Dimension(800, 600));
        pack();
        setLocationRelativeTo(null);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel fileLabel = new JLabel("gameList.txt");
        txtFilePath = new JTextField(30);
        txtFilePath.setEditable(false);

        searchButton = new JButton("Search...");
        startScrapButton = new JButton("Look for discounts");

        infoArea = new JTextArea();


        infoArea.setText("Welcome to the Steam Discount Scrapper. This program searches Steam for a series of inputed games, and returns the availability, price, discounts (if they were) and link. \n Please, select a .txt file from your computer with the games you want to search, separated by commas.");
        infoArea.setEditable(false);

        topPanel.add(infoArea);
        topPanel.add(fileLabel);
        topPanel.add(txtFilePath);
        topPanel.add(searchButton);
        topPanel.add(startScrapButton);

        add(topPanel, BorderLayout.NORTH);

        searchButton.addActionListener(this::selectFile);
        startScrapButton.addActionListener(e -> startScrapping());

        String[] columns = {"Game name", "Price", "Discounted?", "Link"};
        tableModel = new DefaultTableModel(columns, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        resultsTable = new JTable(tableModel);

        configureHyperlinkColumn();

        JScrollPane scrollPane = new JScrollPane(resultsTable);

        add(scrollPane, BorderLayout.CENTER);



    }

    private void selectFile(ActionEvent e){
        JFileChooser fileChooser = new JFileChooser();

        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION){
            File selectedFile = fileChooser.getSelectedFile();

            txtFilePath.setText(selectedFile.getAbsolutePath());

        }
    }

    private void startScrapping(){
        String path = txtFilePath.getText();
        if(path.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please, select a .txt file with game names, separated by commas, to search for them on Steam", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        } else {
            System.out.println("Initiating with file: " + path);
        }

        startScrapButton.setEnabled(false);

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setVisible(true);

        SwingWorker<List<GameItem>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<GameItem> doInBackground() throws Exception {
                       String csvOutput = getDesktopPath("gameList.csv");
                       SearchForGames searcher = new SearchForGames(csvOutput);

                return searcher.SelectGame(path, (currentGame, currentIndex, totalGames) -> {
                    SwingUtilities.invokeLater(() ->{
                        progressDialog.updateProgress(currentGame, currentIndex, totalGames);
                    });

                });
            }

            @Override
            protected void done(){
                progressDialog.dispose();
                try{
                    List<GameItem> results = get();
                    loadDataOnTable(results);
                    JOptionPane.showMessageDialog(MainWindow.this, "Search completed!");
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                    JOptionPane.showMessageDialog(MainWindow.this, "Error: " + e.getMessage());
                } finally {
                    startScrapButton.setEnabled(true);
                }
            }
        };

        worker.execute();
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
                    MainWindow window = new MainWindow();
                    window.setVisible(true);
                }
        );
    }

    private void configureHyperlinkColumn(){
        int linkColumnIndex = 3;
        resultsTable.getColumnModel().getColumn(linkColumnIndex).setCellRenderer(new HyperlinkRenderer());

        MouseAdapter linkMouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = resultsTable.rowAtPoint(e.getPoint());
                int col = resultsTable.columnAtPoint(e.getPoint());

                if (row != -1 && col == linkColumnIndex){
                    Object value = resultsTable.getValueAt(row, col);
                    if(value != null && !value.toString().isBlank()){
                        openInBrowser(value.toString().trim());
                    }
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                int row = resultsTable.rowAtPoint(e.getPoint());
                int col = resultsTable.columnAtPoint(e.getPoint());

                if (row != -1 && col == linkColumnIndex){
                    Object value = resultsTable.getValueAt(row, col);
                    if(value != null && !value.toString().isBlank()){
                        resultsTable.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    } else {
                        resultsTable.setCursor(Cursor.getDefaultCursor());
                    }
                }
            }
        };

        resultsTable.addMouseListener(linkMouseAdapter);
        resultsTable.addMouseMotionListener(linkMouseAdapter);
    }

    private void openInBrowser(String url){
        if(Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)){
            try {
                Desktop.getDesktop().browse(new URI(url));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
}

    public void loadDataOnTable(List<GameItem> gameList){
        tableModel.setRowCount(0);

        for(GameItem game : gameList){
            Object[] row = {
                    game.getGameName(),
                    game.getGameFinalPrice(),
                    game.getDiscounted(),
                    game.getGameLink()

            };

            tableModel.addRow(row);
        }
    }
}
