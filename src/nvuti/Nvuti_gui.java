package nvuti;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;
import java.text.DecimalFormat;
import java.util.Random;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicLabelUI;

public class Nvuti_gui implements ActionListener {

	private JFrame frame;
	
	JTextField bet_amount = new JTextField("1");
	JTextField percent = new JTextField("90");

	JLabel tot_balance = new JLabel("100");
	JLabel win_amount = new JLabel("1.11");
	JLabel indicator = new JLabel("        ");
	JLabel border_less = new JLabel("0-899999");
	JLabel border_more = new JLabel("100000-999999");
	JLabel guess_label = new JLabel("\u0421\u043B\u0435\u0434. \u0437\u0430\u0433\u0430\u0434\u0430\u043D\u043E");
	JLabel bet_double = new JLabel("Удвоить"), bet_half = new JLabel("Половина"), bet_max = new JLabel("Макс"), bet_min = new JLabel("Мин");
	JLabel perc_double = new JLabel("Удвоить"), perc_half = new JLabel("Половина"), perc_max = new JLabel("Макс"), perc_min = new JLabel("Мин");
	JLabel guess_ghost = new JLabel("                         ");
	JLabel guess_label_ghost = new JLabel("              ?      ");
	
	JButton less_button = new JButton("Меньше");
	JButton more_button = new JButton("Больше");

	boolean guess_visible = false;
	boolean supply_visible = false;

	DecimalFormat df = new DecimalFormat("#.##");
	DecimalFormat df1 = new DecimalFormat("###,###.#");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Nvuti_gui window = new Nvuti_gui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Nvuti_gui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Nvuti");
		
		frame.setBounds(100, 100, 520, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		ImagePanel panel = new ImagePanel(new ImageIcon(getClass().getResource("/background.jpg")).getImage());
		panel.setBounds(0, 0, 700, 500);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon.png")));
		
		JLabel balance_label = new JLabel("Баланс");
		balance_label.setHorizontalAlignment(JLabel.CENTER);
		balance_label.setFont(new Font("Verdana", Font.BOLD, 16));
		balance_label.setForeground(Color.WHITE);
		balance_label.setBounds(22, 155, 70, 16);
		frame.getContentPane().add(balance_label);

		JLabel bet_label = new JLabel("Ставка");
//		bet_label.setHorizontalAlignment(JLabel.CENTER);
		bet_label.setFont(new Font("Verdana", Font.BOLD, 16));
		bet_label.setForeground(Color.WHITE);
		bet_label.setBounds(22, 13, 70, 16);
		frame.getContentPane().add(bet_label);

		JLabel percent_label = new JLabel("Процент");
//		percent_label.setHorizontalAlignment(JLabel.CENTER);
		percent_label.setFont(new Font("Verdana", Font.BOLD, 16));
		percent_label.setForeground(Color.WHITE);
		percent_label.setBounds(167, 13, 90, 16);
		frame.getContentPane().add(percent_label);

		tot_balance.setBounds(22, 180, 200, 16);
//		tot_balance.setHorizontalAlignment(JLabel.CENTER);
		tot_balance.setFont(new Font("Verdana", Font.BOLD, 16));
		tot_balance.setForeground(Color.WHITE);
		frame.getContentPane().add(tot_balance);

		win_amount.setBounds(270, 110, 200, 16);
		win_amount.setHorizontalAlignment(JLabel.CENTER);
		win_amount.setFont(new Font("Verdana", Font.BOLD, 20));
		win_amount.setForeground(Color.WHITE);
		frame.getContentPane().add(win_amount);

//		guess.setHorizontalAlignment(JLabel.CENTER);
		guess.setFont(new Font("Verdana", Font.BOLD, 12));
		guess.setForeground(Color.WHITE);
		guess.setBounds(400, 42, 70, 16);
		frame.getContentPane().add(guess);
		guess.setVisible(false);

//		guess_ghost.setHorizontalAlignment(JLabel.CENTER);
		guess_ghost.setBounds(415, 42, 70, 16);
		frame.getContentPane().add(guess_ghost);
		guess_ghost.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (!guess_visible) {
					guess_label.setVisible(true);
					guess.setVisible(true);
					guess_visible = true;
					guess_label_ghost.setText("                         ");
				} else {
					guess_label.setVisible(false);
					guess.setVisible(false);
					guess_visible = false;
					guess_label_ghost.setText("              ?           ");
				}
			}
		});

		border_less.setHorizontalAlignment(JLabel.CENTER);
		border_less.setBounds(210, 190, 200, 16);
		border_less.setFont(new Font("Verdana", Font.BOLD, 12));
		border_less.setForeground(Color.WHITE);
		frame.getContentPane().add(border_less);

		border_more.setHorizontalAlignment(JLabel.CENTER);
		border_more.setBounds(330, 190, 200, 16);
		border_more.setFont(new Font("Verdana", Font.BOLD, 12));
		border_more.setForeground(Color.WHITE);
		frame.getContentPane().add(border_more);

		bet_amount.setHorizontalAlignment(JLabel.CENTER);
		bet_amount.setBounds(22, 39, 116, 30);
		bet_amount.setFont(new Font("Calibri", Font.PLAIN, 24));
		frame.getContentPane().add(bet_amount);
		bet_amount.setColumns(10);
		
		bet_double.setBounds(22, 70, 50, 20);
		bet_double.setHorizontalAlignment(JLabel.CENTER);
		bet_double.setFont(new Font("Calibri", Font.PLAIN, 12));
		bet_double.setForeground(Color.BLACK);
		bet_double.setOpaque(true);
		bet_double.setBackground(new Color(0x1149ff));
		frame.getContentPane().add(bet_double);
		bet_double.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				bet_amount.setText(Double.toString(Double.parseDouble(bet_amount.getText())*2));
				updateBet();
			}
		});
		
		bet_half.setBounds(75, 70, 60, 20);
		bet_half.setHorizontalAlignment(JLabel.CENTER);
		bet_half.setFont(new Font("Calibri", Font.PLAIN, 12));
		bet_half.setForeground(Color.BLACK);
		bet_half.setOpaque(true);
		bet_half.setBackground(new Color(0x1149ff));
		frame.getContentPane().add(bet_half);
		bet_half.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(Double.parseDouble(bet_amount.getText())/2 >= 1) {
					bet_amount.setText(Double.toString(Double.parseDouble(bet_amount.getText())/2));
				}
				else {
					bet_amount.setText("1");
				}
				updateBet();
			}
		});
		
		bet_max.setBounds(42, 92, 30, 20);
		bet_max.setHorizontalAlignment(JLabel.CENTER);
		bet_max.setFont(new Font("Calibri", Font.PLAIN, 12));
		bet_max.setForeground(Color.BLACK);
		bet_max.setOpaque(true);
		bet_max.setBackground(new Color(0x1149ff));
		frame.getContentPane().add(bet_max);
		bet_max.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				bet_amount.setText(tot_balance.getText());
				updateBet();
			}
		});
		
		bet_min.setBounds(75, 92, 30, 20);
		bet_min.setHorizontalAlignment(JLabel.CENTER);
		bet_min.setFont(new Font("Calibri", Font.PLAIN, 12));
		bet_min.setForeground(Color.BLACK);
		bet_min.setOpaque(true);
		bet_min.setBackground(new Color(0x1149ff));
		frame.getContentPane().add(bet_min);
		bet_min.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				bet_amount.setText("1");
				updateBet();
			}
		});

		percent.setHorizontalAlignment(JLabel.CENTER);
		percent.setBounds(167, 39, 116, 30);
		percent.setFont(new Font("Calibri", Font.PLAIN, 24));
		frame.getContentPane().add(percent);
		percent.setColumns(5);
		
		perc_double.setBounds(167, 70, 50, 20);
		perc_double.setHorizontalAlignment(JLabel.CENTER);
		perc_double.setFont(new Font("Calibri", Font.PLAIN, 12));
		perc_double.setForeground(Color.BLACK);
		perc_double.setOpaque(true);
		perc_double.setBackground(new Color(0x1149ff));
		frame.getContentPane().add(perc_double);
		perc_double.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(Double.parseDouble(percent.getText())*2 <= 95) {
					percent.setText(Double.toString(Double.parseDouble(percent.getText())*2));
					updatePerc();
				}
			}
		});
		
		perc_half.setBounds(220, 70, 60, 20);
		perc_half.setHorizontalAlignment(JLabel.CENTER);
		perc_half.setFont(new Font("Calibri", Font.PLAIN, 12));
		perc_half.setForeground(Color.BLACK);
		perc_half.setOpaque(true);
		perc_half.setBackground(new Color(0x1149ff));
		frame.getContentPane().add(perc_half);
		perc_half.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(Double.parseDouble(percent.getText())/2 >= 1) {
					percent.setText(Double.toString(Double.parseDouble(percent.getText())/2));
				}
				else {
					percent.setText("1");
				}
				updatePerc();
			}
		});
		
		perc_max.setBounds(187, 92, 30, 20);
		perc_max.setHorizontalAlignment(JLabel.CENTER);
		perc_max.setFont(new Font("Calibri", Font.PLAIN, 12));
		perc_max.setForeground(Color.BLACK);
		perc_max.setOpaque(true);
		perc_max.setBackground(new Color(0x1149ff));
		frame.getContentPane().add(perc_max);
		perc_max.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				percent.setText("95");
				updatePerc();
			}
		});
		
		perc_min.setBounds(220, 92, 30, 20);
		perc_min.setHorizontalAlignment(JLabel.CENTER);
		perc_min.setFont(new Font("Calibri", Font.PLAIN, 12));
		perc_min.setForeground(Color.BLACK);
		perc_min.setOpaque(true);
		perc_min.setBackground(new Color(0x1149ff));
		frame.getContentPane().add(perc_min);
		perc_min.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				percent.setText("1");
				updatePerc();
			}
		});

		less_button.setBounds(250, 140, 120, 40);
		less_button.setFont(new Font("Calibri", Font.PLAIN, 18));
		less_button.setForeground(Color.BLACK);
		less_button.setBackground(new Color(0x1149ff));
		less_button.setUI(new StyledButtonUI());
		frame.getContentPane().add(less_button);

		more_button.setBounds(380, 140, 120, 40);
		more_button.setFont(new Font("Calibri", Font.PLAIN, 18));
		more_button.setForeground(Color.BLACK);
		more_button.setBackground(new Color(0x1149ff));
		more_button.setUI(new StyledButtonUI());
		frame.getContentPane().add(more_button);

		indicator.setBounds(293, 210, 160, 32);
		indicator.setFont(new Font("Calibri", Font.BOLD, 18));
		indicator.setHorizontalAlignment(JLabel.CENTER);
		indicator.setForeground(Color.WHITE);
		AbstractBorder brdrLeft = new TextBubbleBorder(Color.DARK_GRAY,0,15,0, false);
		indicator.setBorder(brdrLeft);
		frame.getContentPane().add(indicator);
		indicator.setOpaque(true);
		indicator.setVisible(false);
		
		JLabel supply_label = new JLabel("Пополнить");
		supply_label.setBounds(22, 210, 63, 16);
		supply_label.setHorizontalAlignment(SwingConstants.CENTER);
		supply_label.setFont(new Font("Calibri", Font.PLAIN, 12));
		supply_label.setForeground(Color.BLACK);
		supply_label.setOpaque(true);
		supply_label.setBackground(new Color(0x1149ff));
		frame.getContentPane().add(supply_label);
		
		supply_label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				
				if(!supply_visible) {
					supply_field.setVisible(true);
					supply_visible = true;
				}
				else {
					tot_balance.setText(Double.toString(Double.parseDouble(tot_balance.getText()) + Double.parseDouble(supply_field.getText())));
					supply_field.setVisible(false);
					supply_visible = false;
				}
			}
		});
		
		supply_field = new JTextField();
		supply_field.setBounds(100, 208, 53, 22);
		frame.getContentPane().add(supply_field);
		supply_field.setText("1");
		supply_field.setColumns(10);
		supply_field.setVisible(false);
		
		frame.getContentPane().setBackground(Color.BLUE);		
		frame.getContentPane().add(panel);
				guess_label.setBounds(373, 13, 110, 16);
				panel.add(guess_label);
		
		//		guess_label.setHorizontalAlignment(JLabel.CENTER);
				guess_label.setFont(new Font("Verdana", Font.BOLD, 12));
				guess_label.setForeground(Color.WHITE);
						guess_label_ghost.setBounds(417, 14, 90, 16);
						panel.add(guess_label_ghost);
				
				//		guess_label_ghost.setHorizontalAlignment(JLabel.CENTER);
						guess_label_ghost.setForeground(Color.WHITE);
						guess_label_ghost.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseReleased(MouseEvent e) {
								if (!guess_visible) {
									guess_label.setVisible(true);
									guess.setVisible(true);
									guess_visible = true;
									guess_label_ghost.setText("                         ");
								} else {
									guess_label.setVisible(false);
									guess.setVisible(false);
									guess_visible = false;
									guess_label_ghost.setText("              ?           ");
								}
							}
						});
				guess_label.setVisible(false);

		frame.setResizable(true);
		frame.setAlwaysOnTop(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		less_button.addActionListener(this);
		more_button.addActionListener(this);

		bet_amount.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() != KeyEvent.VK_PERIOD && e.getKeyCode() != KeyEvent.VK_LEFT
						&& e.getKeyCode() != KeyEvent.VK_RIGHT && e.getKeyCode() != KeyEvent.VK_SHIFT
						&& e.getKeyCode() != KeyEvent.VK_CONTROL && e.getKeyCode() != KeyEvent.VK_ALT
						&& e.getKeyCode() != KeyEvent.VK_HOME && e.getKeyCode() != KeyEvent.VK_END) {
					updateBet();
				}
			}
		});

		percent.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() != KeyEvent.VK_PERIOD && e.getKeyCode() != KeyEvent.VK_LEFT
						&& e.getKeyCode() != KeyEvent.VK_RIGHT && e.getKeyCode() != KeyEvent.VK_SHIFT
						&& e.getKeyCode() != KeyEvent.VK_CONTROL && e.getKeyCode() != KeyEvent.VK_ALT
						&& e.getKeyCode() != KeyEvent.VK_HOME && e.getKeyCode() != KeyEvent.VK_END) {
					updatePerc();
				}
			}
		});
	}

	void updateBet() {
		String str = bet_amount.getText().replaceAll(",", ".");
		str = str.replaceAll("-", "");
		int i = str.lastIndexOf(".");
		str = str.replaceAll("\\D+", "");
		String str1 = str;
		if (i > 0) {
			str1 = str.substring(0, i) + "." + str.substring(i);
		}
		bet_amount.setText(str1);

		if (!bet_amount.getText().equals("")) {
			bet = Double.parseDouble(str1);
			if (bet < 1) {
				bet_amount.setText("1");
				bet = 1;
			}
		}

		perc = Double.parseDouble(percent.getText()) / 100;

		diff = (int) (max * perc);
		less = min + diff;
		more = max - diff;

		border_less.setText("0-" + less);
		border_more.setText(more + "-999999");

		guessed = random.nextInt(max);
		guess.setText(Integer.toString(guessed));

		win_amount.setText(df.format(bet / perc));
	}
	
	void updatePerc() {
		String str = percent.getText().replaceAll(",", ".");
		str = str.replaceAll("-", "");
		int i = str.lastIndexOf(".");
		str = str.replaceAll("\\D+", "");
		String str1 = str;
		if (i > 0) {
			str1 = str.substring(0, i) + "." + str.substring(i);
		}
		percent.setText(str1);

		bet = Double.parseDouble(bet_amount.getText());

		if (!percent.getText().equals("")) {
			perc = Double.parseDouble(str1) / 100;
			if (perc > 0.95) {
				percent.setText("95");
				perc = 0.95;
			}
			else {
				if (perc < 0.01) {
					percent.setText("1");
					perc = 0.01;
				}
			}
		}

		diff = (int) (max * perc);
		less = min + diff;
		more = max - diff;

		border_less.setText("0-" + less);
		border_more.setText(more + "-999999");

		guessed = random.nextInt(max);
		guess.setText(Integer.toString(guessed));

		win_amount.setText(df.format(bet / perc));
	}

	Random random = new Random();
	int min = 0;
	int max = 999999;

	double bet;
	double perc;

	int diff;
	int less;
	int more;

	int guessed = random.nextInt(max);
	int prev_guessed = guessed;

	JLabel guess = new JLabel(Integer.toString(guessed));
	private JTextField supply_field;

	@Override
	public void actionPerformed(ActionEvent event) {
		try {
			bet = Double.parseDouble(bet_amount.getText());
			perc = Double.parseDouble(percent.getText()) / 100;

			diff = (int) (max * perc);
			less = min + diff;
			more = max - diff;

			double tot_amount = Double.parseDouble(tot_balance.getText());

			prev_guessed = guessed;
			if (event.getSource() == less_button) {
				if (tot_amount >= 1 && tot_amount - bet >= 0) {
					
					tot_amount -= bet;
					
					indicator.setVisible(false);
					MyThread myThread = new MyThread();
				    myThread.start();
					
					if ((min + guessed) <= less) {
						tot_amount += bet / perc;
						indicator.setBackground(Color.GREEN);
					} else {
						indicator.setBackground(Color.RED);
					}
					
					indicator.setText(Integer.toString(prev_guessed));

					tot_balance.setText(df.format(tot_amount).replace(",", "."));
				}
			} else {
				if (event.getSource() == more_button) {
					if (tot_amount >= 1 && tot_amount - bet >= 0) {

						tot_amount -= bet;
						
						indicator.setVisible(false);
						MyThread myThread = new MyThread();
					    myThread.start();
						
						if ((min + guessed) >= more) {
							tot_amount += bet / perc;
							indicator.setBackground(Color.GREEN);
						} else {
							indicator.setBackground(Color.RED);
						}
						
						indicator.setText(Integer.toString(prev_guessed));

						tot_balance.setText(df.format(tot_amount).replace(",", "."));
					}
				}
			}

			guessed = random.nextInt(max);
			guess.setText(Integer.toString(guessed));
		} catch (Exception e) {
		}
	}
	
	public class MyThread extends Thread {
	    public void run() {
	    	try {
				Thread.sleep(200);
				indicator.setVisible(true);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}
}

@SuppressWarnings("serial")
class TextBubbleBorder extends AbstractBorder {

    private Color color;
    private int thickness = 4;
    private int radii = 8;
    private int pointerSize = 7;
    private Insets insets = null;
    private BasicStroke stroke = null;
    private int strokePad;
    private int pointerPad = 4;
    private boolean left = true;
    RenderingHints hints;

    TextBubbleBorder(
            Color color) {
        this(color, 4, 8, 7);
    }

    TextBubbleBorder(
            Color color, int thickness, int radii, int pointerSize) {
        this.thickness = thickness;
        this.radii = radii;
        this.pointerSize = pointerSize;
        this.color = color;

        stroke = new BasicStroke(thickness);
        strokePad = thickness / 2;

        hints = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        int pad = radii + strokePad;
        int bottomPad = pad + pointerSize + strokePad;
        insets = new Insets(pad, pad, bottomPad, pad);
    }

    TextBubbleBorder(
            Color color, int thickness, int radii, int pointerSize, boolean left) {
        this(color, thickness, radii, pointerSize);
        this.left = left;
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return insets;
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        return getBorderInsets(c);
    }

    @Override
    public void paintBorder(
            Component c,
            Graphics g,
            int x, int y,
            int width, int height) {

        Graphics2D g2 = (Graphics2D) g;

        int bottomLineY = height - thickness - pointerSize;

        RoundRectangle2D.Double bubble = new RoundRectangle2D.Double(
                0 + strokePad,
                0 + strokePad,
                width - thickness,
                bottomLineY,
                radii,
                radii);

        Polygon pointer = new Polygon();

        if (left) {
            // left point
            pointer.addPoint(
                    strokePad + radii + pointerPad,
                    bottomLineY);
            // right point
            pointer.addPoint(
                    strokePad + radii + pointerPad + pointerSize,
                    bottomLineY);
            // bottom point
            pointer.addPoint(
                    strokePad + radii + pointerPad + (pointerSize / 2),
                    height - strokePad);
        } else {
            // left point
            pointer.addPoint(
                    width - (strokePad + radii + pointerPad),
                    bottomLineY);
            // right point
            pointer.addPoint(
                    width - (strokePad + radii + pointerPad + pointerSize),
                    bottomLineY);
            // bottom point
            pointer.addPoint(
                    width - (strokePad + radii + pointerPad + (pointerSize / 2)),
                    height - strokePad);
        }

        Area area = new Area(bubble);
        area.add(new Area(pointer));

        g2.setRenderingHints(hints);

        // Paint the BG color of the parent, everywhere outside the clip
        // of the text bubble.
        Component parent  = c.getParent();
        if (parent!=null) {
            Color bg = parent.getBackground();
            Rectangle rect = new Rectangle(0,0,width, height);
            Area borderRegion = new Area(rect);
            borderRegion.subtract(area);
            g2.setClip(borderRegion);
            g2.setColor(bg);
            g2.fillRect(0, 0, width, height);
            g2.setClip(null);
        }

        g2.setColor(color);
        g2.setStroke(stroke);
        g2.draw(area);
    }
}

@SuppressWarnings("serial")
class ImagePanel extends JPanel {

	private Image img;

	public ImagePanel(String img) {
		this(new ImageIcon(img).getImage());
	}

	public ImagePanel(Image img) {
		this.img = img;
		Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setSize(size);
		setLayout(null);
	}

	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, null);
	}

}

class StyledLabelUI extends BasicLabelUI {
	@Override
	public void installUI(JComponent c) {
		super.installUI(c);
		AbstractButton button = (AbstractButton) c;
		button.setOpaque(false);
		button.setBorder(new EmptyBorder(5, 15, 5, 15));
	}

	@Override
	public void paint(Graphics g, JComponent c) {
		AbstractButton b = (AbstractButton) c;
		paintBackground(g, b, b.getModel().isPressed() ? 2 : 0);
		super.paint(g, c);
	}

	private void paintBackground(Graphics g, JComponent c, int yOffset) {
		Dimension size = c.getSize();
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(c.getBackground().darker());
		g.fillRoundRect(0, yOffset, size.width, size.height - yOffset, 10, 10);
		g.setColor(c.getBackground());
		g.fillRoundRect(0, yOffset, size.width, size.height + yOffset - 5, 10, 10);
	}
}

class StyledButtonUI extends BasicButtonUI {

	@Override
	public void installUI(JComponent c) {
		super.installUI(c);
		AbstractButton button = (AbstractButton) c;
		button.setOpaque(false);
		button.setBorder(new EmptyBorder(5, 15, 5, 15));
	}

	@Override
	public void paint(Graphics g, JComponent c) {
		AbstractButton b = (AbstractButton) c;
		paintBackground(g, b, b.getModel().isPressed() ? 2 : 0);
		super.paint(g, c);
	}

	private void paintBackground(Graphics g, JComponent c, int yOffset) {
		Dimension size = c.getSize();
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(c.getBackground().darker());
		g.fillRoundRect(0, yOffset, size.width, size.height - yOffset, 10, 10);
		g.setColor(c.getBackground());
		g.fillRoundRect(0, yOffset, size.width, size.height + yOffset - 5, 10, 10);
	}
}