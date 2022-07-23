import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class GeradoraDeFigurinhas {
    


	private void printSimpleString(Graphics2D g2d, String s, int width, int XPos, int YPos){
        int stringLen = (int)
            g2d.getFontMetrics().getStringBounds(s, g2d).getWidth();
        int start = width/2 - stringLen/2;
        g2d.drawString(s, start + XPos, YPos);
	}        
        private void desenhaLinhas (Graphics2D g, int x0, int y0, int xf, int yf) { 
        	Graphics2D g2d = (Graphics2D) g;
        	g2d.setPaint(Color.orange); 
        	g2d.setStroke(new BasicStroke (40.0f)); 
        	g2d.draw(new Line2D.Double (x0, y0, x0, yf)); 
        	g2d.draw(new Line2D.Double (x0, y0, xf, y0)); 
        	g2d.draw(new Line2D.Double (xf, y0, xf, yf)); 
        	g2d.draw(new Line2D.Double (x0, yf, xf, yf)); 
       }

    public void cria(InputStream inputStream, String nomeArquivo, String nota) throws Exception {

        // leitura da imagem
        // InputStream inputStream = 
        //             new FileInputStream(new File("entrada/filme-maior.jpg"));
        // InputStream inputStream = 
        //                 new URL("https://m.media-amazon.com/images/M/MV5BMDFkYTc0MGEtZmNhMC00ZDIzLWFmNTEtODM1ZmRlYWMwMWFmXkEyXkFqcGdeQXVyMTMxODk2OTU@.jpg")
        //                 .openStream();
        BufferedImage imagemOriginal = ImageIO.read(inputStream);

        // cria nova imagem em memória com transparência e com tamanho novo
        int largura = imagemOriginal.getWidth();
        int altura = imagemOriginal.getHeight();
        int novaAltura = altura + 200;
        BufferedImage novaImagem = new BufferedImage(largura, novaAltura, BufferedImage.TRANSLUCENT);

        // copiar a imagem original pra novo imagem (em memória)
        Graphics2D graphics = (Graphics2D) novaImagem.getGraphics();
        graphics.drawImage(imagemOriginal, 0, 0, null);

        // Borda
        desenhaLinhas(graphics,0,0,largura,novaAltura);
        
 //       graphics.drawLine(0, largura + 2, 0, novaAltura + 2);
        
        // configurar a fonte
//        var fonte = new Font(Font.SANS_SERIF, Font.BOLD, 64);
        var fonte = new Font("Comic Sans MS",Font.BOLD,70);
        graphics.setColor(Color.RED);
        graphics.setFont(fonte);
 
        // escrever uma frase na nova imagem
        //Calcula centro do retangulo
        
        printSimpleString(graphics, nota, largura, 0, novaAltura - 100);
        
//        graphics.drawString(nota, 100, novaAltura - 100);
 
        // escrever a nova imagem em um arquivo
        ImageIO.write(novaImagem, "png", new File(nomeArquivo));

    }

}