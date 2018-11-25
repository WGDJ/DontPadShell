import java.util.HashMap;


public class DontPadPrinter {
	
	private String url = null; 
	public static String lineSeparatorSplit = "\\n";
	public static String lineSeparatorBreakLine = "\n";
	private HashMap<String, String> parametros = new HashMap<String, String>();
	
	public DontPadPrinter(String url) {
		this.url = url; 
	}
	
	public void put(String msg) throws Exception{
		
		ComunicadorHTTP.sendPost(url, this.createParam("text", msg));
	}
	
	public void append(String msg) throws Exception{
		this.put(get() + lineSeparatorSplit + msg);
	
	}
	
	public String get() throws Exception{
		return ComunicadorHTTP.sendGetFomJson(url);
	}
	
	public String getIndex(int index) throws Exception{
		String[] linhas = getArrayResult(this.get());
		return linhas[index];
	}

	public String getFirst() throws Exception{
		String[] linhas = getArrayResult(this.get());
		return linhas[0];
	}
	
	public String getLast() throws Exception{
		String[] linhas = getArrayResult(this.get());
		return linhas[linhas.length -1];
	}
	
	private String[] getArrayResult(String result) throws Exception {
		return result.split(lineSeparatorSplit);
	}

	private HashMap<String, String> createParam(String chave, String valor) {
		getParametros().put(chave, valor);
		return getParametros();
	}
	
	private HashMap<String, StringBuilder> appendParam(String param, HashMap<String, StringBuilder> params) {
		params.get("text").append(param + lineSeparatorSplit);
		return params;
	}
	
	public HashMap<String, String> getParametros() {
		return parametros;
	}
	
	public void setParametros(HashMap<String, String> parametros) {
		this.parametros = parametros;
	}
	
	/**
	 * Testa a comunicação
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		DontPadPrinter donPad = new DontPadPrinter("http://dontpad.com/19830218");
		
		//Imprime o que já estava no DontPad http://dontpad.com/19830218
		System.out.println(donPad.get());
		
		//Troca todo o conteúdo por Msg - 1
		donPad.put("Msg - 1");
		
		//Adiciona ao final 
		donPad.append("Msg - 2");
		donPad.append("Msg - 3");
		donPad.append("Msg - 4");
		
		//Imprime o resultado da ações acima 
		System.out.println(donPad.get());
		
		System.out.println("---------------------");
		
		//Pega a linha de indice 1
		System.out.println(donPad.getIndex(1));
		
		//Pega a primeira linha
		System.out.println(donPad.getFirst());
		
		//Pega a ultima linha
		System.out.println(donPad.getLast());
	}

}
