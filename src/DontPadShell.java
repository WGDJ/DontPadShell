import java.io.IOException;


public class DontPadShell {
	
	private String promptString = null;
	private DontPadPrinter dontPadPrinter = null;
	
	public DontPadShell(String promptString, DontPadPrinter dontPadPrinter) {
		this.promptString = promptString;
		this.dontPadPrinter = dontPadPrinter;
	}
	
	public void start(int delay, DontPadShell dontPadShell) throws IOException {

		Thread thread = new Thread() {
			public void run() {
				while (true) {
					try {
						
						String rawCommando = dontPadShell.getDontPadPrinter().get();
						String comando = rawCommando.substring(rawCommando.indexOf(dontPadShell.getPromptString().trim())+1, rawCommando.length());
						
						if(comando != null && comando.length() > 1){
							boolean hasEnter = comando.length() > 0 ? comando.substring(comando.length()-2, comando.length()).equals(dontPadShell.getPromptString().trim() + ">"): false;
							comando = comando.replace(promptString.trim() + ">", "");
							
							boolean isComando = comando != null && comando.length() > 0;
							if(isComando && hasEnter){
								String retornoComando = Util.executarComando(comando, dontPadShell);
								dontPadShell.getDontPadPrinter().put(retornoComando + DontPadPrinter.lineSeparatorBreakLine + dontPadShell.getPromptString());
							}
						}
						Thread.sleep(delay);
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println(e.getMessage());
					}
				}
			}

		};
		thread.start();
	}
	
	public String getPromptString() {
		return promptString;
	}
	
	public DontPadPrinter getDontPadPrinter() {
		return dontPadPrinter;
	}

	public static void main(String[] args) throws Exception {
		DontPadPrinter dontPadPrinter = new DontPadPrinter("http://dontpad.com/19830218");
		DontPadShell dontPadShell = new DontPadShell("# ", dontPadPrinter);
		dontPadShell.start(2000, dontPadShell);
		
		dontPadPrinter.put(dontPadShell.getPromptString());
	}
	
	

}
