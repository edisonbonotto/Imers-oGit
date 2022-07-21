
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class App {
	
    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
    	
		Scanner ler = new Scanner(System.in);
		double valor;
		
        // fazer uma conexão HTTP e buscar os top 250 filmes
        // String url = "https://imdb-api.com/en/API/Top250Movies/k_0ojt0yvm";
//        String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java/api/TopMovies.json";
//        String url = "https://api.mocki.io/v2/549a5d8b";
        String url = "https://mocki.io/v1/9a7c1ca9-29b4-4eb3-8306-1adb9d159060";
        URI endereco = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        // extrair só os dados que interessam (titulo, poster, classificação)
        var parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);

        // exibir e manipular os dados 
        for (Map<String,String> filme : listaDeFilmes) {
            System.out.println(filme.get("title"));
            System.out.println(filme.get("image"));
			System.out.print("Qual sua avaliação do filme (0~10)? ");
			valor = ler.nextDouble();
			System.out.print("Avaliação Geral: ");
            for(int i=0;i<=(int) Math.round(Double.valueOf(filme.get("imDbRating")));i++) {
            	System.out.print("\u2605 ");          
            } 
            System.out.print(filme.get("imDbRating"));
			System.out.print("\nSua avaliação  : ");
            for(int i=0;i<=(int) Math.round(valor);i++) {
            	System.out.print("\u2605 ");          
            } 
            System.out.print(valor);
            System.out.println("\n"); 
        }
    }
}