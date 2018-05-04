package com.cff.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.cff.baidupcs.config.ConfigLoader;
import com.cff.ui.action.LoginActionListener;
import com.cff.ui.util.ResourceUtil;

/**
 * 仿QQ登录界面，仅供学习参考，涉及到的有窗口居中、JPanel、LayoutManager的使用
 * 
 * @author hhzxj2008
 */
public class LoginView extends JFrame {

	/** 
	 *  
	 */
	private static final long serialVersionUID = -5665975170821790753L;

	public LoginView() {
		initComponent();
	}

	private void initComponent() {
		setTitle("用户登录");
		// 设置LOGO
		URL image = ResourceUtil.getResource("ui/img/year.jpg");// 图片的位置
		JLabel imageLogo = new JLabel(new ImageIcon(image));
		add(imageLogo, BorderLayout.NORTH);
		URL iconImage = ResourceUtil.getResource("ui/img/icon.jpg");// 图片的位置
		this.setIconImage(new ImageIcon(iconImage).getImage());
		// QQ号和密码
		JPanel jp = new JPanel();
		JPanel jpAccount = new JPanel();
		jpAccount.add(new JLabel("帐号"));
		JTextField userTextField = new JTextField(15);
		jpAccount.add(userTextField);
		jpAccount.add(new JLabel("本地密钥"));
		jp.add(jpAccount);

		JPanel jpPass = new JPanel();
		jpPass.add(new JLabel("密码"));
		JPasswordField passTextField = new JPasswordField(15);
		jpPass.add(passTextField);
		jpPass.add(new JLabel("本地令牌"));
		jp.add(jpPass);

		// 登录设置
		JPanel jpstatus = new JPanel();
		jpstatus.add(new JLabel("状态"));
		JComboBox statusComboBox = new JComboBox();
		statusComboBox.addItem("Q我");
		statusComboBox.addItem("在线");
		statusComboBox.addItem("隐身");
		statusComboBox.addItem("离线");
		jpstatus.add(statusComboBox);
		JCheckBox cbKey = new JCheckBox("记住密码");
		JCheckBox cbToken = new JCheckBox("自动登录");
		jpstatus.add(cbKey);
		jpstatus.add(cbToken);
		jp.add(jpstatus);

		// 底部登录按钮
		JPanel bottomPanel = new JPanel();
		JButton loginBtn = new JButton("登录");
		bottomPanel.setLayout(new BorderLayout());
		bottomPanel.add(new JButton("设置"),BorderLayout.WEST);
		bottomPanel.add(new JLabel("                        "));
		bottomPanel.add(loginBtn,BorderLayout.EAST);
		jp.add(bottomPanel,BorderLayout.SOUTH);
		add(jp);

		JPanel tipPanel = new JPanel();
		tipPanel.setLayout(new BorderLayout());
		JLabel label = new JLabel();
		label.setText("");
		tipPanel.add(label);
		add(tipPanel, BorderLayout.SOUTH);
		setSize(324, 250);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		try {
			Map<String, String> map = ConfigLoader.loadUser();
			if (map.size() > 1) {
				userTextField.setText(map.get("userName"));
				passTextField.setText(map.get("passwd"));
			}
		} catch (IOException e) {
		}

		LoginActionListener loginActionListener = new LoginActionListener(userTextField, passTextField, cbKey, cbToken,label,this);
		actionEvent(loginBtn, loginActionListener);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				new LoginView().setVisible(true);

			}

		});

	}

	public static void run() {
		java.awt.EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				new LoginView().setVisible(true);

			}

		});
	}

	public static void actionEvent(AbstractButton component, ActionListener action) {
		component.addActionListener(action);
	}
}
