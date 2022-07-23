
import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {

    public static void main(String[] args) throws Exception {

        // fazer uma conexão HTTP e buscar os top 250 filmes
        //String url = "https://imdb-api.com/en/API/Top250Movies/k_0ojt0yvm";
//    	String url = "https://api.mocki.io/v2/549a5d8b/Top250Movies";
//    	String url = "https://api.mocki.io/v2/549a5d8b/Top250TVs";
        String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java/api/TopMovies.json";
        URI endereco = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        // extrair só os dados que interessam (titulo, poster, classificação)
        var parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);

        // exibir e manipular os dados 
        
        // Verifica se pasta já existe, senão, cria
        File diretorio = new File("figuras"); 
        if (!diretorio.exists()) { 
        	diretorio.mkdirs();  
        } 
        else { 
        	System.out.println("Diretório já existente");
        }
        var geradora = new GeradoraDeFigurinhas();
        for (Map<String,String> filme : listaDeFilmes) {

            String urlImagem = filme.get("image");
            String titulo = filme.get("title");

            String nota = "Nota: " + filme.get("imDbRating");
            
            InputStream inputStream = new URL(urlImagem).openStream();
                    
            String nomeArquivo = "figuras/" + titulo + ".png";

            geradora.cria(inputStream, nomeArquivo, nota);

            System.out.println(titulo);
            System.out.println();
        }
    }
}