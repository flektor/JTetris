package tetris;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import shapes.Shape;
import shapes.Shape1;
import shapes.Shape2;
import shapes.Shape3;
import shapes.Shape4;
import shapes.Shape5;
import shapes.Shape6;
import shapes.Shape7;
 
public class MenuBar extends JMenuBar {

    public MenuBar(Panel panel) {
        this.panel = panel;
        JMenu jMenu1 = new JMenu("Game");
        JMenu jMenu2 = new JMenu("Options");
        JMenu jMenu3 = new JMenu("Exit");
        JMenuItem jMenuItem1 = new JMenuItem("New Game");
        jMenuItem1.setIcon(new ImageIcon( getClass().getResource("/material/icons/New16.png")) );
        JMenuItem jMenuItem2 = new JMenuItem("Load Game");
        jMenuItem2.setIcon(new ImageIcon( getClass().getResource("/material/icons/Open16.gif")) );
        JMenuItem jMenuItem3 = new JMenuItem("Save Game");
        jMenuItem3.setIcon(new ImageIcon( getClass().getResource("/material/icons/Save16.gif")) );
        JMenuItem jMenuItem4 = new JMenuItem("Save and Exit");
        JMenuItem jMenuItem5 = new JMenuItem("Exit without Saving");
        JMenuItem jMenuItem6 = new JMenuItem("Game Options");
        JMenuItem jMenuItem7 = new JMenuItem("Settings");
        settingsJDialog();
        jMenuItem1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                panel.newGame();     
            }
            
        });
        jMenuItem7.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (jDialog1.isVisible()) {
                    jDialog1.setVisible(false);
                } else {
                    jDialog1.setVisible(true);
                }
            }
        });

        jMenu1.add(jMenuItem1);
        jMenu1.add(jMenuItem2);
        jMenu1.add(jMenuItem3);
        jMenu2.add(jMenuItem6);
        jMenu2.add(jMenuItem7);
        jMenu3.add(jMenuItem4);
        jMenu3.add(jMenuItem5);
        add(jMenu1);
        add(jMenu2);
        add(jMenu3);
    }
    

    private static void settingsJDialog() {
        jDialog1 = new JDialog();
        jDialog1.setLocationRelativeTo(null);
        jDialog1.setTitle("Settings");
        JTabbedPane jTabbedPane1 = new JTabbedPane();
        JPanel jPanel1 = new JPanel();
        jPanel1.add(new JLabel("Sound"));
        JPanel jPanel2 = new JPanel();
        jPanel2.add(new JLabel("Window"));
        jTabbedPane1.addTab("Audio", jPanel1);
        jTabbedPane1.addTab("Window", jPanel2);
        jTabbedPane1.addTab("Appearance", appearance());
        jDialog1.add(jTabbedPane1);
        jDialog1.pack();
//        jDialog1.setResizable(false);
    }

    private static JPanel appearance() {
        JPanel jPanel4;
        JColorChooser colorChooser;

        JPanel jPanel3 = new JPanel();

        jPanel3.setLayout(
                new BoxLayout(jPanel3, BoxLayout.PAGE_AXIS));
        jPanel4 = new JPanel(new BorderLayout());
        shapePanel = new ShapePanel(160, 160, new Shape1());
        currentShape = new ShapePanel(40, 40, new Shape1());
        colorChooser = new JColorChooser();
        AbstractColorChooserPanel[] panels = colorChooser.getChooserPanels();
        for (AbstractColorChooserPanel accp : panels) {
            if (accp.getDisplayName().equals("RGB")) {
                jPanel3.add(accp);
                accp.getComponent(2).addPropertyChangeListener(new PropertyChangeListener() {

                    @Override
                    public void propertyChange(PropertyChangeEvent evt) {
                        Color c = colorChooser.getColor();
                        shapePanel.setColor(c);
                        currentShape.setColor(c);
                    }
                });
            }
        }

        jPanel4.add(shapePanel, BorderLayout.WEST);
        jPanel4.add(new JLabel(" "), BorderLayout.SOUTH);
        JPanel jPanel10 = new JPanel();
        jPanel10.setLayout(new BoxLayout(jPanel10, BoxLayout.LINE_AXIS));

        Shape shape1 = new Shape1();
        Shape shape2 = new Shape2();
        Shape shape3 = new Shape3();
        Shape shape4 = new Shape4();
        Shape shape5 = new Shape5();
        Shape shape6 = new Shape6();
        Shape shape7 = new Shape7();

        JPanel jp1 = new JPanel(new BorderLayout());
        JPanel jp2 = new JPanel(new BorderLayout());
        JPanel jp3 = new JPanel(new BorderLayout());
        JPanel jp4 = new JPanel(new BorderLayout());
        JPanel jp5 = new JPanel(new BorderLayout());
        JPanel jp6 = new JPanel(new BorderLayout());
        JPanel jp7 = new JPanel(new BorderLayout());

        jp1.add(new ShapePanel(40, 40, shape1));
        jp2.add(new ShapePanel(40, 40, shape2));
        jp3.add(new ShapePanel(40, 40, shape3));
        jp4.add(new ShapePanel(40, 40, shape4));
        jp5.add(new ShapePanel(40, 40, shape5));
        jp6.add(new ShapePanel(40, 40, shape6));
        jp7.add(new ShapePanel(40, 40, shape7));

        jPanel10.add(jp1);
        jPanel10.add(jp2);
        jPanel10.add(jp3);
        jPanel10.add(jp4);
        jPanel10.add(jp5);
        jPanel10.add(jp6);
        jPanel10.add(jp7);

        MouseListener ml = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ShapePanel sp;
                try {
                    JPanel panel = (JPanel) e.getSource();
                    sp = (ShapePanel) panel.getComponent(0);
                } catch (NullPointerException n) {
                    sp = new ShapePanel(40, 40, shape1);
                    System.out.println(jp1.getComponents());
                }
                currentShape = sp;
                colorChooser.setColor(sp.getColor());
                shapePanel.setShape(sp.getShape());
                shapePanel.setLabelVariables(sp.getSizee(), sp.getNumberOfLines(), sp.getFactor());

                jSlider1.setValue(sp.getFactor());
                jSlider2.setValue(sp.getSizee());
                jSlider3.setValue(sp.getNumberOfLines());

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                JPanel panel = (JPanel) e.getSource();
                ShapePanel sp = (ShapePanel) panel.getComponent(0);
                sp.setColor(sp.getColor().darker());

            }

            @Override
            public void mouseExited(MouseEvent e) {
                JPanel panel = (JPanel) e.getSource();
                ShapePanel sp = (ShapePanel) panel.getComponent(0);
                sp.setColor(sp.getColor().brighter());
            }
        };

        for (int i = 0; i < jPanel10.getComponentCount(); i++) {
            jPanel10.getComponent(i).addMouseListener(ml);
        }

        JPanel jPanel5 = new JPanel();
        jPanel5.setLayout(new BoxLayout(jPanel5, BoxLayout.Y_AXIS));
        jPanel5.add(jPanel10);
        jPanel3.add(jPanel4);

        ShapePanel s = (ShapePanel) jp1.getComponents()[0];
        int size = s.getSizee();
        int levels = s.getNumberOfLines();
        int factor = s.getFactor();

        jSlider1 = new JSlider(0, 70, factor);
        JSpinner jSpinner1 = new JSpinner(new SpinnerNumberModel(factor, 0, 70, 1));
        jSlider2 = new JSlider(1, 10, size);
        JSpinner jSpinner2 = new JSpinner(new SpinnerNumberModel(size, 1, 10, 1));
        jSlider3 = new JSlider(1, 25, levels);
        JSpinner jSpinner3 = new JSpinner(new SpinnerNumberModel(levels, 1, 25, 1));

        jPanel10.getComponents()[0].getMouseListeners()[0].mouseClicked(null);

        ChangeListener changeListenner1 = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {

                int factor = (int) jSpinner1.getValue();
                int size = (int) jSpinner2.getValue();
                int levels = (int) jSpinner3.getValue();
                jSlider1.setValue(factor);
                jSlider2.setValue(size);
                jSlider3.setValue(levels);
                shapePanel.setLabelVariables(size, levels, factor);
                currentShape.setLabelVariables(size, levels, factor);
            }
        };

        ChangeListener changeListenner2 = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int factor = (int) jSlider1.getValue();
                int size = (int) jSlider2.getValue();
                int levels = (int) jSlider3.getValue();
                jSpinner1.setValue(factor);
                jSpinner2.setValue(size);
                jSpinner3.setValue(levels);
                shapePanel.setLabelVariables(size, levels, factor);
                currentShape.setLabelVariables(size, levels, factor);

            }
        };

        jSpinner1.addChangeListener(changeListenner1);
        jSpinner2.addChangeListener(changeListenner1);
        jSpinner3.addChangeListener(changeListenner1);
        jSlider1.addChangeListener(changeListenner2);
        jSlider2.addChangeListener(changeListenner2);
        jSlider3.addChangeListener(changeListenner2);
        JPanel jPanel6 = new JPanel();

        jPanel6.add(new JLabel("Factor"));
        jPanel6.add(jSlider1);
        jPanel6.add(jSpinner1);

        JPanel jPanel7 = new JPanel();
        jPanel7.add(new JLabel("Size"));
        jPanel7.add(jSlider2);
        jPanel7.add(jSpinner2);

        JPanel jPanel8 = new JPanel();
        jPanel8.add(new JLabel("Level"));
        jPanel8.add(jSlider3);
        jPanel8.add(jSpinner3);

        JPanel jPanel9 = new JPanel();
        jPanel9.add(new JLabel("outline"));

        JCheckBox jCheckBox1 = new JCheckBox();
        jCheckBox1.addChangeListener(
                new ChangeListener() {

                    @Override
                    public void stateChanged(ChangeEvent e) {
                        if (jCheckBox1.isSelected()) {
                            jSpinner1.setEnabled(true);
                            jSpinner2.setEnabled(true);
                            jSpinner3.setEnabled(true);
                            jSlider1.setEnabled(true);
                            jSlider2.setEnabled(true);
                            jSlider3.setEnabled(true);
//                shapePanel.setLabelVariables(size, levels, factor);
//                currentShape.setLabelVariables(size, levels, factor);
                        } else {
                            jSpinner1.setEnabled(false);
                            jSpinner2.setEnabled(false);
                            jSpinner3.setEnabled(false);
                            jSlider1.setEnabled(false);
                            jSlider2.setEnabled(false);
                            jSlider3.setEnabled(false);
//                shapePanel.setLabelVariables(0, 0, 0);
//                currentShape.setLabelVariables(0, 0, 0);
                        }

                    }
                }
        );

        jCheckBox1.setSelected(true);
        jCheckBox1.getChangeListeners()[0].stateChanged(null);
        jPanel9.add(jCheckBox1);
        jPanel5.add(jPanel9);
        jPanel5.add(jPanel6);
        jPanel5.add(jPanel7);
        jPanel5.add(jPanel8);
        jPanel4.add(jPanel5, BorderLayout.EAST);

        return jPanel3;
    }
    
    private final  Panel panel;
    private static JDialog jDialog1;
    private static JSlider jSlider1, jSlider2, jSlider3;
    private static ShapePanel shapePanel, currentShape;

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setJMenuBar(new MenuBar(new Panel()));
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
