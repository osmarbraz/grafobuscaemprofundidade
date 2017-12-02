/*
 * Universidade Federal de Santa Catarina - UFSC
 * Departamento de Informática e Estatística - INE
 * Programa de Pós-Graduação em Ciências da Computação - PROPG
 * Disciplina: Projeto e Análise de Algoritmos
 * Prof Alexandre Gonçalves da Silva 
 *
 * Baseado nos slides 95 da aula do dia 06/10/2017 
 * Página 440 Thomas H. Cormen 3a Ed
 *
 * Realiza a Busca em Profundidade ou Depht-first Search(DFS).
 */

/**
 * @author Osmar de Oliveira Braz Junior
 */
import java.util.Stack;

public class Principal {

    //A medida que o grafo é percorrido, os vértices visitados vão
    //sendo coloridos. Cada vértice tem uma das seguintes cores:
    final static int BRANCO = 0;//Vértce ainda não visitado
    final static int CINZA = 1; //Vértice visitado mas não finalizado
    final static int PRETO = 2; //Vértice visitado e finalizado

    //Vetor da situação vértice, armazena uma das cores
    static int cor[];
    //d[x] armazena o instante de descoberta de x.
    static int d[];
    //f[x] armazena o instante de finalização de x.
    static int f[];
    //Vetor dos pais de um vértice
    static int pi[];
    static int tempo;

    /**
     * Troca um número que representa a posição pela vértice do grafo.
     *
     * @param i Posição da letra
     * @return Uma String com a letra da posição i
     */
    public static String trocar(int i) {        
        String letras = "szyxwtvu";        
        if ((i >=0) && (i<=letras.length())) {
            return letras.charAt(i) + "";
        } else {
            return "-";
        }        
    }

    /**
     * Troca a letra pela posição na matriz de adjacência
     *
     * @param v Letra a ser troca pela posição
     * @return Um inteiro com a posição da letra no grafo
     */
    public static int destrocar(char v) {        
        String letras = "szyxwtvu";
        int pos = -1;
        for (int i = 0; i < letras.length(); i++) {
            if (letras.charAt(i) == v) {
                pos = i;
            }
        }
        return pos;
    }

    /**
     * Mostra o caminho de s até v no grafo G
     *
     * @param G Matriz de incidência do grafo
     * @param s Origem no grafo
     * @param v Destino no grafo
     */
    public static void mostrarCaminho(int[][] G, int s, int v) {
        if (v == s) {
            System.out.println("Cheguei em:" + trocar(s));
        } else {
            if (pi[v] == -1) {
                System.out.println("Não existe caminho de " + trocar(s) + " a " + trocar(v));
            } else {
                mostrarCaminho(G, s, pi[v]);
                System.out.println("Visitando:" + trocar(v));
            }
        }
    }

    /**
     * Busca em Profundidade (Breadth-first Search) Iterativo. 
     * Recebe um grafo G e devolve 
     * (i) os instantes d[v] e f[v] para cada v 
     * (ii) uma Floresta de Busca em Profundiade
     *
     * Consumo de tempo é O(V)+V chamadas 
     * Complexidade de tempo é O(V+E)
     *
     * @param G Grafo na forma de uma matriz de adjacência
     */
    public static void buscaEmProfundidadeIterativo(int[][] G) {
        //Quantidade vértices do grafo
        int n = G.length;

        //Inicializa os vetores
        cor = new int[n];
        d = new int[n];
        f = new int[n];
        pi = new int[n];

        //Inicialização dos vetores
        //Consome Theta(V)
        for (int u = 0; u < n; u++) {
            //Vértice i não foi visitado
            cor[u] = BRANCO;
            d[u] = -1;
            pi[u] = -1;
        }
        tempo = 0;

        //Inicializa a pilha vazia
        Stack q = new Stack();

        //Percorre todos os vértices do grafo
        for (int s = 0; s < n; s++) {
            //Somente vértices nao visitados
            if (cor[s] == BRANCO) {
                System.out.println("Visitando:" + trocar(s));
                cor[s] = CINZA;
                tempo = tempo + 1; //Vértice branco s acabou de ser descoberto
                d[s] = tempo;
                System.out.println("Empilhando:" + trocar(s));
                q.push(s);

                while (q.isEmpty() == false) {
                    //Desempilha o primeiro vértice
                    int u = (int) q.peek();
                    // Exporar as arestas (u,v)
                    for (int v = 0; v < n; v++) {
                        //Somente com os adjancentes ao vértice u
                        if (G[u][v] != 0) {
                            if (cor[v] == BRANCO) {
                                System.out.println("Adjacente de " + trocar(u) + " = " + trocar(v));
                                pi[v] = u;
                                System.out.println("Visitando:" + trocar(v));
                                cor[v] = CINZA;
                                tempo = tempo + 1; //Vértice branco v acabou de ser descoberto
                                d[v] = tempo;
                                System.out.println("Empilhando:" + trocar(v));
                                q.push(v);
                                u = v;
                            }
                        }
                    }
                    System.out.println("Desempilhando:" + trocar(u));
                    q.pop();
                    //Vértice u foi visitado e finalizado
                    cor[u] = PRETO;
                    tempo = tempo + 1;
                    f[u] = tempo;
                }
            }
        }
    }

    /**
     * Constrói recursivamente uma Árvore de Busca em profundidade. com raiz u.
     *
     * Consumo de tempo Adj[u] vezes
     *
     * Método DFS-Visit(G,u)
     * 
     * @param G Matriz de incidência do grafo
     * @param u Vértice raiz da árvore de busca
     */
    public static void buscaEmProfundidadeVisita(int[][] G, int u) {
        //Quantidade vértices do grafo
        int n = G.length;

        System.out.println("Visitando:" + trocar(u));
        cor[u] = CINZA;
        tempo = tempo + 1; //Vértice branco u acabou de ser descoberto
        d[u] = tempo;
        System.out.println("Empilhando:" + trocar(u));
        // Exporar as arestas (u,v)
        for (int v = 0; v < n; v++) {
            //Somente com os adjancentes ao vértice u
            if (G[u][v] != 0) {
                //Somente vértices nao visitados
                if (cor[v] == BRANCO) {
                    System.out.println("Adjacente de " + trocar(u) + " = " + trocar(v));
                    pi[v] = u;
                    buscaEmProfundidadeVisita(G, v);
                }
            }
        }
        System.out.println("Desempilhando:" + trocar(u));
        //Vértice u foi visitado e finalizado
        cor[u] = PRETO;
        tempo = tempo + 1;
        f[u] = tempo;
    }

    /**
     * Busca em Profundidade (Breadth-first Search) recursivo. 
     * 
     * Recebe um grafo G e devolve 
     * (i) os instantes d[v] e f[v] para cada v 
     * (ii) uma Floresta de Busca em Profundiade
     *
     * Consumo de tempo é O(V)+V chamadas 
     * Complexidade de tempo é O(V+E)
     *
     * Método DFS(G)
     * @param G Grafo na forma de uma matriz de adjacência
     */
    public static void buscaEmProfundidadeRecursivo(int[][] G) {
        //Quantidade vértices do grafo
        int n = G.length;

        //Inicializa os vetores
        cor = new int[n];
        d = new int[n];
        f = new int[n];
        pi = new int[n];

        //Inicialização dos vetores
        //Consome Theta(V)
        for (int u = 0; u < n; u++) {
            //Vértice i não foi visitado
            cor[u] = BRANCO;
            d[u] = -1;
            pi[u] = -1;
        }
        tempo = 0;

        //Percorre todos os vértices do grafo
        for (int u = 0; u < n; u++) {
            //Somente vértices nao visitados
            if (cor[u] == BRANCO) {
                buscaEmProfundidadeVisita(G, u);
            }
        }
    }

    public static void main(String args[]) {

        //Matriz de incidência para um grafo direcionado     
        //Grafo do slide 71
        int G[][] = 
                //s  z  y  x  w  t  v  u  
                {{0, 1, 0, 0, 1, 0, 0, 0},
                {0, 0, 1, 0, 1, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0},
                {0, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 1, 1},
                {1, 0, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 1, 1, 0}};

        System.out.println(">>> Realiza a Busca em Profundidade ou Depht-first Search(DFS) <<<");

        //Monta as árvores de busca
        buscaEmProfundidadeRecursivo(G);
        //buscaEmProfundidadeIterativo(G);

        //Mostra o caminho de s até t
        // inicio s=0
        int inicio = destrocar('s');
        // destino t = 5
        int destino = destrocar('t');
        System.out.println();
        System.out.println("Mostrando o caminho de " + trocar(inicio) + " até " + trocar(destino));
        mostrarCaminho(G, inicio, destino);

        //Mostra o caminho de s até x
        // destino v = 3
        destino = destrocar('x');
        System.out.println();
        System.out.println("Mostrando o caminho de " + trocar(inicio) + " até " + trocar(destino));
        mostrarCaminho(G, inicio, destino);

        System.out.println();
        System.out.println("Ordem de execução d[x]/f[x]");
        for (int i = 0; i < G.length; i++) {
            System.out.println(trocar(i) + "=" + d[i] + "/" + f[i]);
        }
    }
}
