package com.cff.baidupcs;

import java.util.Scanner;

import com.cff.baidupcs.Service.BaiduHttpService;
import com.cff.baidupcs.Service.LsHttpService;
import com.cff.baidupcs.Service.PcsClientService;
import com.cff.baidupcs.client.BaiduClient;
import com.cff.baidupcs.client.BaiduLoginRes;
import com.cff.baidupcs.common.Constant;
import com.cff.baidupcs.model.BaiduDto;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println("请输入用户名");
        Scanner scan = new Scanner(System.in);
		String userName = scan.nextLine();
		Constant.userName = userName;
		System.out.println("请输入密码");
		String passwd = scan.nextLine();
		Constant.passwd = passwd;
		
    	BaiduClient bc = new BaiduClient();
        System.out.println(bc.getServerTime());
        BaiduDto baiduDto = BaiduHttpService.baiduLogin(bc);
        if(baiduDto != null){
        	System.out.println(baiduDto);
        	BaiduHttpService.setBduus(baiduDto);
        	System.out.println("百度账号登陆成功！");
        	PcsClientService pcsClientService = new PcsClientService();
        	pcsClientService.init(baiduDto);
        	LsHttpService lsHttpService = new LsHttpService(pcsClientService);
        	lsHttpService.runLs("/");
        } else {
        	System.out.println("Over !");
        }
    }
}
