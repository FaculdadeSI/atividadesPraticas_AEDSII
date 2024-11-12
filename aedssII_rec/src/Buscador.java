
import java.io.*;
import java.util.*;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Buscador {

    // seleciona o diretório raiz
    public static File selecionaDiretorioRaiz() {
        JFileChooser janelaSelecao = new JFileChooser(".");
        janelaSelecao.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int acao = janelaSelecao.showOpenDialog(null);

        if (acao == JFileChooser.APPROVE_OPTION) {
            return janelaSelecao.getSelectedFile();
        } else {
            return null;
        }
    }

    public static void main(String[] args) {
        File pastaInicial = selecionaDiretorioRaiz();

        // verifica se o diretório é válido
        if (pastaInicial == null) {
            System.out.println("Você deve selecionar uma pasta para o processamento.");
        } else {
            // cria a hashtable
            LinkedList<Palavra>[] hashtable = new LinkedList[calcularTamanhoHashtable(pastaInicial)];

            percorrerDiretorio(pastaInicial, hashtable);

            String[] palavrasChave = new String[8];

            for (int i = 0; i < 8; i++) {
                palavrasChave[i] = lerPalavraChave(i + 1);
            }

            double fatorCarga = calcularFatorCarga(hashtable);
            System.out.println("Fator de carga da Tabela Hash: " + fatorCarga);
            System.out.println(" ");

            // array para armazenar os tempos das buscas na Tabela Hash
            long[] temposBuscaHash = new long[8];
            // array para armazenar os tempos das buscas sequenciais simples
            long[] temposBuscaSequencial = new long[8];

            // HASH--------------------------------------------------------------------------------------------------------------------
            for (int i = 0; i < 8; i++) {
                final String palavraChave = palavrasChave[i];
                System.out.println("Buscando a palavra \"" + palavraChave + "\":");

                long totalTimeBuscaHash = buscaComTimer(() -> {
                    int totalOcorrenciasHash = buscarPalavraHash(hashtable, palavraChave);
                    System.out.println("Total de ocorrências encontradas usando a Tabela Hash: " + totalOcorrenciasHash);
                });
                temposBuscaHash[i] = totalTimeBuscaHash;
               // BUSCA SIMPLES--------------------------------------------------------------------------------------------------------------
                long totalTimeBuscaSequencial = buscaComTimer(() -> {
                    int totalOcorrenciasSequencial = buscarPalavraSequencial(pastaInicial, palavraChave);
                    System.out.println("Total de ocorrências encontradas na busca sequencial: " + totalOcorrenciasSequencial);
                });
                temposBuscaSequencial[i] = totalTimeBuscaSequencial;

                System.out.println("Tempo de busca sequencial simples (em milissegundos) para a palavra \"" + palavraChave + "\": " + totalTimeBuscaSequencial);
                System.out.println("Tempo de busca na Tabela Hash (em milissegundos) para a palavra \"" + palavraChave + "\": " + totalTimeBuscaHash);
                System.out.println("------------------------------------------");
            }
            //-------------------------------------------------------------------------------------------------------------------------

            long tempoTotalBuscasHash = Arrays.stream(temposBuscaHash).sum();
            System.out.println("Tempo total das buscas na Tabela Hash (em milissegundos): " + tempoTotalBuscasHash);
            
            long tempoTotalBuscasSequenciais = Arrays.stream(temposBuscaSequencial).sum();
            System.out.println("Tempo total das buscas sequenciais simples (em milissegundos): " + tempoTotalBuscasSequenciais);
        }
    }

    public static String lerPalavraChave(int numeroPalavra) {
        return JOptionPane.showInputDialog(null, "Digite a " + numeroPalavra + "ª palavra-chave que deseja buscar:");
    }

// PARTE HASH ------------------------------------------------------------------------------------------------------------------
    // calcula o fator de carga da Tabela Hash
    public static double calcularFatorCarga(LinkedList<Palavra>[] hashtable) {
        int numElementos = 0;
        for (LinkedList<Palavra> lista : hashtable) {
            if (lista != null) {
                numElementos += lista.size();
            }
        }

        return (double) numElementos / hashtable.length;
    }

    // calcula o tamanho da hashtable com base no número de arquivos
    public static int calcularTamanhoHashtable(File pasta) {
        int numArquivos = contarArquivos(pasta);
        // retorna um número maior ou igual ao número de arquivos
        return Math.max(numArquivos, 701); // valor mínimo 701 de acordo com a orientação
    }

    // conta o número de arquivos na pasta
    public static int contarArquivos(File pasta) {
        int count = 0;
        if (pasta.isDirectory()) {
            File[] arquivos = pasta.listFiles();
            if (arquivos != null) {
                for (File arquivo : arquivos) {
                    if (arquivo.isFile()) {
                        count++;
                    } else if (arquivo.isDirectory()) {
                        count += contarArquivos(arquivo);
                    }
                }
            }
        }
        return count;
    }

    // percorre os arquivos e insere os elementos na hashtable
    public static void percorrerDiretorio(File pasta, LinkedList<Palavra>[] hashtable) {
        if (pasta.isDirectory()) {
            // pega a lista de arquivos na pasta
            File[] arquivos = pasta.listFiles();

            if (arquivos != null) {
                for (File arquivo : arquivos) {
                    if (arquivo.isDirectory()) {
                        percorrerDiretorio(arquivo, hashtable);
                    } else {
                        inserirElementos(arquivo, hashtable);
                    }
                }
            }
        }
    }

    public static final Set<String> STOP_WORDS = new HashSet<>(Arrays.asList(
            "de", "da", "do", "para", "com", "em", "um", "uma", "os", "as", "e", "ou", "que", "no", "às",
            "nos", "o", "a", "das", "dos", "nas", "como", "seu", "sua", "é", "à", "ao", "se", "mas", "mais",
            "na"
    ));

    // lê os arquivos e insere as palavras na hashtable
    public static void inserirElementos(File arquivo, LinkedList<Palavra>[] hashtable) {
        try (Scanner scanner = new Scanner(arquivo)) {
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                // define a expressão regular para encontrar pontuação e espaços extras
                String regex = "[,.:;!?()]|\\s{2,}";
                // divide a linha em palavras separadas por espaços em branco, removendo a pontuação e convertendo para minúsculas
                String[] palavras = linha.replaceAll(regex, "").toLowerCase().split("\\s+");

                for (String palavra : palavras) {
                    // verifica se a palavra é uma "stop word" e a ignora
                    if (STOP_WORDS.contains(palavra.toLowerCase())) {
                        continue;
                    }

                    // calcula o valor de hash para a palavra
                    int valorHash = funcaoHash(palavra, hashtable.length);

                    if (hashtable[valorHash] == null) {
                        // se estiver vazia, cria uma nova lista encadeada nessa posição
                        hashtable[valorHash] = new LinkedList<>();
                    }

                    Palavra palavraObjeto = buscarPalavra(hashtable[valorHash], palavra);

                    if (palavraObjeto == null) {
                        // se não foi encontrada, cria um novo objeto Palavra com a palavra
                        palavraObjeto = new Palavra(palavra);

                        // adiciona o objeto Palavra à lista encadeada
                        hashtable[valorHash].add(palavraObjeto);
                    }

                    // adiciona a ocorrência do arquivo à palavra encontrada
                    palavraObjeto.adicionarOcorrencia(arquivo.getName());
                }
            }
        } catch (IOException e) {
        }
    }

    // busca uma palavra na lista encadeada de palavras da hashtable
    public static Palavra buscarPalavra(LinkedList<Palavra> lista, String palavraChave) {
        for (Palavra palavra : lista) {
            if (palavra.getPalavraChave().toLowerCase().equals(palavraChave.toLowerCase())) {
                return palavra;
            }
        }
        return null;
    }

// calcula o valor de hash para uma palavra
    public static int funcaoHash(String palavra, int tamanhoHashtable) {
        return Math.abs(palavra.hashCode()) % tamanhoHashtable;
    }

    // busca uma palavra na Tabela Hash e retorna as ocorrências
    public static int buscarPalavraHash(LinkedList<Palavra>[] hashtable, String palavraChave) {
        int totalOcorrencias = 0;
        int valorHash = funcaoHash(palavraChave, hashtable.length);
        LinkedList<Palavra> lista = hashtable[valorHash];
        if (lista != null) {
            for (Palavra palavra : lista) {
                if (palavra.getPalavraChave().equalsIgnoreCase(palavraChave)) {
                    // soma as ocorrências da palavra em todas as entradas
                    for (Ocorrencia ocorrencia : palavra.getOcorrencias()) {
                        totalOcorrencias += ocorrencia.getNumeroOcorrencias();
                    }
                }
            }
        }
        return totalOcorrencias;
    }

    // calcula o total de ocorrências da palavra em todos os arquivos
    public static int totalOcorrenciasPalavra(Palavra palavra) {
        int totalOcorrencias = 0;

        for (Ocorrencia ocorrencia : palavra.getOcorrencias()) {
            totalOcorrencias += ocorrencia.getNumeroOcorrencias();
        }

        return totalOcorrencias;
    }

//------------------------------------------------------------------------------------------------------------------------------
    
// BUSCADOR SIMPLES ------------------------------------------------------------------------------------------------------------
    // buscador sequencial simples de palavras
    public static int buscarPalavraSequencial(File pasta, String palavraChave) {
        int totalOcorrencias = 0;
        if (pasta.isDirectory()) {
            File[] arquivos = pasta.listFiles();

            if (arquivos != null) {
                for (File arquivo : arquivos) {
                    if (arquivo.isFile()) {
                        try {
                            totalOcorrencias += contarOcorrencias(arquivo, palavraChave);
                        } catch (FileNotFoundException e) {
                        }
                    } else if (arquivo.isDirectory()) {
                        totalOcorrencias += buscarPalavraSequencial(arquivo, palavraChave);
                    }
                }
            }
        }

        return totalOcorrencias;
    }

    // conta as ocorrências de uma palavra em um arquivo
    public static int contarOcorrencias(File arquivo, String palavraChave) throws FileNotFoundException {
        int count = 0;
        try (Scanner scanner = new Scanner(arquivo)) {
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                // divide a linha em palavras separadas por espaços em branco e remove a pontuação
                String regex = "[,.:;!?()]|\\s{2,}";
                String[] palavras = linha.replaceAll(regex, "").toLowerCase().split("\\s+");

                for (String palavra : palavras) {
                    // verifica se a palavra atual é igual à palavra-chave que estamos buscando
                    if (palavra.equals(palavraChave)) {
                        count++;
                    }
                }
            }
        }

        return count;
    }
//------------------------------------------------------------------------------------------------------------------------------

    // realiza a busca e mede o tempo
    public static long buscaComTimer(Runnable busca) {
        long startTime = System.currentTimeMillis();
        busca.run();
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }
}
