package com.livingguide.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class CodeController {
	private static int ZERO = 0;
	private static int CODE_Y = 16;
	private static int WIDTH = 90;// 定义图片的width
	private static int HEIGHT = 20;// 定义图片的height
	private static int CODE_COUNT = 4;// 定义图片上显示验证码的个数
	private static int FONT_HEIGHT = 18;
	private static Random random = new Random();
	private static String[] codeSequence = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O",
			"P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
	private static int LENGTH = codeSequence.length - 1;

	@RequestMapping("/code.do")
	public void getCode(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// 定义图像buffer
		BufferedImage buffImg = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		createGraphics(req, buffImg);
		setImageIO(resp, buffImg);
		setResponse(resp);
	}

	/**
	 * 生成验证码图像
	 * 
	 * @param req
	 * @param buffImg
	 */
	private void createGraphics(HttpServletRequest req, BufferedImage buffImg) {
		Graphics gd = buffImg.getGraphics();
		setGraphics(buffImg, gd);
		getRandomCode(req, gd);
		setDrawLine(gd);
	}

	/**
	 * 生成验证码背景边框
	 * 
	 * @param buffImg
	 * @param random
	 */
	private void setGraphics(BufferedImage buffImg, Graphics gd) {
		// 将图像填充为白色
		gd.setColor(Color.WHITE);
		gd.fillRect(ZERO, ZERO, WIDTH, HEIGHT);
		// 设置字体。
		gd.setFont(new Font("Fixedsys", Font.BOLD, FONT_HEIGHT));
		// 画边框。
		gd.setColor(Color.BLACK);
		gd.drawRect(ZERO, ZERO, WIDTH - 1, HEIGHT - 1);
		gd.setColor(Color.BLACK);
	}

	/**
	 * 在图像和缓存中加入随机验证码
	 * 
	 * @param random
	 * @param gd
	 * @return
	 */
	private void getRandomCode(HttpServletRequest req, Graphics gd) {
		StringBuffer sb = new StringBuffer();
		int red = ZERO;
		int green = ZERO;
		int blue = ZERO;
		// 随机产生codeCount数字的验证码。
		for (int i = ZERO; i < CODE_COUNT; i++) {
			// 得到随机产生的验证码数字。
			String code = codeSequence[getRandomInt(LENGTH)];
			// 将产生的四个随机数组合在一起。
			sb.append(code);
			// 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
			red = getRandomInt(255);
			green = getRandomInt(255);
			blue = getRandomInt(255);
			// 用随机产生的颜色将验证码绘制到图像中。
			gd.setColor(new Color(red, green, blue));
			gd.drawString(code, (i + 1) * 15, CODE_Y);
		}

		setSessionCode(req, sb.toString());
	}

	/**
	 * 添加干扰线
	 * 
	 * @param random
	 * @param gd
	 */
	private void setDrawLine(Graphics gd) {
		int x = ZERO;
		int y = ZERO;
		int xl = ZERO;
		int yl = ZERO;
		for (int i = ZERO; i < 40; i++) {
			x = getRandomInt(WIDTH);
			y = getRandomInt(HEIGHT);
			xl = getRandomInt(12);
			yl = getRandomInt(12);
			gd.drawLine(x, y, x + xl, y + yl);
		}
	}

	/**
	 * 将四位数字的验证码保存到Session中
	 * 
	 * @param random
	 */
	private void setSessionCode(HttpServletRequest req, String sb) {
		HttpSession session = req.getSession();
		session.setAttribute("code", sb);
	}

	/**
	 * 将图像输出到Servlet输出流中
	 * 
	 * @param resp
	 * @param buffImg
	 * @throws IOException
	 */
	private void setImageIO(HttpServletResponse resp, BufferedImage buffImg) throws IOException {
		ServletOutputStream sos = resp.getOutputStream();
		ImageIO.write(buffImg, "jpeg", sos);
		sos.close();
	}

	/**
	 * 禁用图像缓存
	 * 
	 * @param resp
	 */
	private void setResponse(HttpServletResponse resp) {
		// 禁止图像缓存。
		resp.setHeader("Pragma", "no-cache");
		resp.setHeader("Cache-Control", "no-cache");
		resp.setDateHeader("Expires", ZERO);
		resp.setContentType("image/jpeg");
	}

	private int getRandomInt(int num) {
		return random.nextInt(num);
	}
}