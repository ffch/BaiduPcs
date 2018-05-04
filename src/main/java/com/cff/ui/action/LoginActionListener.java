package com.cff.ui.action;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.alibaba.fastjson.JSONObject;
import com.cff.baidupcs.api.LoginApi;
import com.cff.baidupcs.client.BaiduLoginRes;
import com.cff.baidupcs.util.StringUtil;
import com.cff.ui.CodeDialog;
import com.cff.ui.LoginView;
import com.cff.ui.util.ResourceUtil;

public class LoginActionListener implements ActionListener {
	JTextField userTextField;
	JPasswordField passTextField;
	JCheckBox cbKey;
	JCheckBox cbToken;
	JLabel label;

	public LoginActionListener(JTextField userTextField, JPasswordField passTextField, JCheckBox cbKey,
			JCheckBox cbToken, JLabel label) {
		this.userTextField = userTextField;
		this.passTextField = passTextField;
		this.cbKey = cbKey;
		this.cbToken = cbToken;
		this.label = label;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String userName = userTextField.getText().trim();
		String passwd = String.valueOf(passTextField.getPassword());
		if (StringUtil.isEmpty(userName) || StringUtil.isEmpty(passwd)) {
			return;
		}
		boolean isCbKeyCheck = false;
		boolean isCbTokenCheck = false;

		if (cbKey.isSelected())
			isCbKeyCheck =  true;
		if (cbToken.isSelected())
			isCbTokenCheck = true;
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("userName", userName);
		jsonobj.put("passwd", passwd);
		jsonobj.put("saveKey", isCbKeyCheck);
		jsonobj.put("autoLogin", isCbTokenCheck);
		doProcess(jsonobj);
	}

	public void doProcess(JSONObject jsonobj){
		LoginApi commonApi = new LoginApi();
		BaiduLoginRes baiduLoginRes = commonApi.login(jsonobj.toJSONString());
		System.out.println(baiduLoginRes);
		if ("0".equals(baiduLoginRes.getErrCode())) {
			label.setText("登录成功！");
		} else if ("500001".equals(baiduLoginRes.getErrCode()) || "500002".equals(baiduLoginRes.getErrCode())) {
			CodeDialog dialog = new CodeDialog(baiduLoginRes); // 生成小窗体并把数据对象传给小窗体
			dialog.setTitle("验证码");
			File imgFile;
			try {
				imgFile = ResourceUtil.downloadImg(baiduLoginRes.getGotoUrl());
			} catch (Exception e1) {
				return;
			}
			JPanel bottomPanel = new JPanel();
			bottomPanel.setLayout(new BorderLayout());
			Image img = Toolkit.getDefaultToolkit().createImage(imgFile.getAbsolutePath());
			JLabel imageLogo = new JLabel(new ImageIcon(img));
			JLabel imglabel = new JLabel("为保证您的账户，请输入以下验证码：");

			bottomPanel.add(imglabel, BorderLayout.NORTH);
			JPanel codePanel = new JPanel();
			JLabel imageCode = new JLabel(" ");
			codePanel.setLayout(new BorderLayout());
			codePanel.add(imageLogo, BorderLayout.NORTH);
			codePanel.add(imageCode, BorderLayout.SOUTH);
			bottomPanel.add(codePanel, BorderLayout.CENTER);
			JTextField codeTextField = new JTextField(6);
			bottomPanel.add(codeTextField, BorderLayout.SOUTH);
			dialog.add(bottomPanel);
			JPanel tipPanel = new JPanel();
			tipPanel.setLayout(new BorderLayout());
			JButton loginBtn = new JButton("确定");
			tipPanel.add(loginBtn, BorderLayout.EAST);
			dialog.add(tipPanel, BorderLayout.SOUTH);
			try {
				String code = ResourceUtil.analysisImage(imgFile);
				imageCode.setText(code);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			loginBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String code = codeTextField.getText();
					if(StringUtil.isEmpty(code))return;
					String codeUrl = baiduLoginRes.getCodeString();
					jsonobj.put("code", code);
					jsonobj.put("codeUrl", codeUrl);
					doProcess(jsonobj);
					dialog.dispose();
				}
			});
			imageLogo.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					File imgFileTmp;
					try {
						imgFileTmp = ResourceUtil.downloadImg(baiduLoginRes.getGotoUrl());
					} catch (Exception e1) {
						return;
					}
					Image img = Toolkit.getDefaultToolkit().createImage(imgFileTmp.getAbsolutePath());
					imageLogo.setIcon(new ImageIcon(img));
					try {
						String code = ResourceUtil.analysisImage(imgFile);
						imageCode.setText(code);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}		
			});
			
			imageCode.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //双击选中
                    if(e.getClickCount() == 2) {
                        String clipboardStr = imageCode.getText();
                        imageCode.setOpaque(true);
                        imageCode.setForeground(Color.BLUE);
                        imageCode.setFont(new Font("Helvetica", Font.BOLD, 14));
                        if(clipboardStr.trim().length() != 0){
                        	codeTextField.setText(clipboardStr);
                        }                 
                    }
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
                	imageCode.setOpaque(true);
                	imageCode.setForeground(imglabel.getForeground());
                	imageCode.setFont(new Font("Helvetica", Font.PLAIN, 14));
                }
            });
			dialog.setSize(250, 200);
			dialog.setLocationRelativeTo(null);
			dialog.setModal(true); // 用模态显示小窗体
			dialog.setVisible(true);

		} else {
			label.setText("错误码：" + baiduLoginRes.getErrCode() + ", " + baiduLoginRes.getErrMsg());
		}
	}
}
