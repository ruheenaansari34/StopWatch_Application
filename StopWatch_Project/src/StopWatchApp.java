import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class StopWatchApp extends JFrame {

    //List all the GUI Object References
    private final String applicationTitle = "Stop Watch Application";
    private JLabel title;
    private JTextField hrsField;
    private JTextField minField;
    private JTextField secField;
    private JButton startButton;
    private JButton stopButton;
    private JButton resetButton;
    private int hr;
    private int min;
    private int sec; 
    private boolean isWatchTicking = false;

    private StopWatch stopwatch;

    public static void main( String[] args ) {
        StopWatchApp stopwatchApp = new StopWatchApp();
        stopwatchApp.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        centreWindow( stopwatchApp );
        stopwatchApp.setVisible(true);

    }

    public StopWatchApp() {
        this.setTitle( applicationTitle);
        this.setSize( 800, 320 );
        initializeGUI();
        setTime();
    }

    public static void centreWindow(Window frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }

    private void initializeGUI() {
        JPanel content = (JPanel)this.getContentPane();
        content.setLayout( new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(2,2,2,2);
        c.gridx = 0;
        c.gridy = 0;
        title = new JLabel( "Stop Watch", SwingConstants.CENTER );
        content.add( title,c );
        addWindowListener( new ConfirmOnClose() );
        c.gridx = 0;
        c.gridy = 1;
        content.add( createDisplayPanel(),c );
        c.gridx = 0;
        c.gridy = 2;
        content.add( createButtonsPanel(), c );
    }

    private JPanel createDisplayPanel() {
        JPanel dispPanel = new JPanel();
        dispPanel.setLayout( new GridBagLayout() );
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(2,2,2,2);
        JLabel hrsLabel = new JLabel( "Hours", SwingConstants.CENTER );
        JLabel minLabel = new JLabel( "Minutes", SwingConstants.CENTER);
        JLabel secLabel = new JLabel( "Seconds", SwingConstants.CENTER );
        c.gridx = 0;
        c.gridy = 0;
        dispPanel.add( hrsLabel,c );
        c.gridx = 1;
        dispPanel.add( minLabel, c );
        c.gridx = 2;
        dispPanel.add( secLabel );
        hrsField = new JTextField("");
        Font bigFont = hrsField.getFont().deriveFont(Font.PLAIN, 150f);
        hrsField.setFont(bigFont);
        hrsField.setColumns(2);
        hrsField.setHorizontalAlignment(JTextField.CENTER);
        minField = new JTextField("");
        minField.setFont( bigFont );
        minField.setColumns(2);
        minField.setHorizontalAlignment(JTextField.CENTER);
        secField = new JTextField("");
        secField.setFont( bigFont );
        secField.setColumns(2);
        secField.setHorizontalAlignment(JTextField.CENTER);
        c.gridx = 0;
        c.gridy = 1;
        dispPanel.add( hrsField, c );
        c.gridx = 1;
        dispPanel.add( minField, c );
        c.gridx = 2;
        dispPanel.add( secField, c );
        return dispPanel;
    }

    private JPanel createButtonsPanel() {
        JPanel buttonsPanel = new JPanel();
        ButtonActions actionListener = new ButtonActions();
        buttonsPanel.setLayout( new GridLayout( 1, 3 ) );
        startButton = new JButton( "Start" );
        startButton.setSize(10, 60 );
        startButton.setActionCommand( "start" );
        startButton.addActionListener( actionListener );
        stopButton = new JButton( "Stop" );
        stopButton.setSize(10, 60 );
        stopButton.setActionCommand( "stop" );
        stopButton.addActionListener( actionListener );
        resetButton = new JButton( "Reset" );
        resetButton.setSize(10, 60 );
        resetButton.setActionCommand( "reset" );
        resetButton.addActionListener( actionListener );
        buttonsPanel.add( startButton );
        buttonsPanel.add( stopButton );
        buttonsPanel.add( resetButton );
        return buttonsPanel;
    }

    private void createStopWatch() {
        stopwatch = new StopWatch( "StopWatchApp" );
        stopwatch.setStopwatchapp( this );
    }

    public void incrementTime() {
        this.sec = this.sec + 1;
        if( this.sec == 60 ) {
            this.min = this.min + 1;
            this.sec = 0;
            if( this.min == 60 ) {
                this.hr = this.hr + 1;
                this.min = 0;
                if( this.hr == 24 ) {
                    this.hr = 0;
                }
            }
        }
    }

    public void setTime() {
        hrsField.setText( String.valueOf( hr ) );
        minField.setText( String.valueOf( min ) );
        secField.setText( String.valueOf( sec ));
    }



    class ConfirmOnClose extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            int confirm = JOptionPane.showConfirmDialog(e.getWindow(), "Are you sure you want to exit?");
            if( confirm == 0 ) {
                e.getWindow().dispose();
                System.exit(0);
            }
        }
    }

    class ButtonActions implements ActionListener {
        public void actionPerformed( ActionEvent evt ){
            String actCommand = evt.getActionCommand();
            if ( actCommand.equalsIgnoreCase( "reset" ) ){
                hr=0;
                min=0;
                sec=0;
                setTime();
            }
            else if( actCommand.equalsIgnoreCase( "start" ) ) {
                System.out.println( "Clicked start button" );
                if( !isWatchTicking ) {
                    createStopWatch();
                    stopwatch.setRunFlag(true);
                    stopwatch.start();
                }
                isWatchTicking = true;
            }
            else if( actCommand.equalsIgnoreCase( "stop" ) ) {
                isWatchTicking = false;
                stopwatch.setRunFlag( false );
            }
        }
    }


}
