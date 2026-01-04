package io.github.pedrossjr;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.HashMap;

public class QRCodeGenerator extends JPanel {
    private static final String TEXTO_QR = "https://github.com/pedrossjr";
    private static final int LARGURA = 400;
    private static final int ALTURA = 400;

    private BufferedImage imagemQR;

    public QRCodeGenerator() {
        gerarQRCode();
    }

    private void gerarQRCode() {
        try {
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hints.put(EncodeHintType.ERROR_CORRECTION, com.google.zxing.qrcode.decoder.ErrorCorrectionLevel.H);

            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix bitMatrix = writer.encode(TEXTO_QR, BarcodeFormat.QR_CODE, LARGURA, ALTURA, hints);

            imagemQR = MatrixToImageWriter.toBufferedImage(bitMatrix);
        } catch (WriterException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao gerar QR Code: " + e.getMessage());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagemQR != null) {
            int x = (getWidth() - imagemQR.getWidth()) / 2;
            int y = (getHeight() - imagemQR.getHeight()) / 2;
            g.drawImage(imagemQR, x, y, this);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(LARGURA + 50, ALTURA + 50);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Gerador de QR Code");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            QRCodeGenerator painel = new QRCodeGenerator();
            frame.add(painel);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}