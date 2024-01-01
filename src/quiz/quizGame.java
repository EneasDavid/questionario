package quiz;
import java.util.*;

public class quizGame {
	private static Map<String, Quiz> quizzes = new HashMap<>();
//  Run | Debug
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		int comando;
		do {
			System.out.printf(
					"\nEntre sua opção:\n1.Para criar um quiz\n2.Para fazer um quiz\n3.Para listar um quiz(Precisaremos do nome do quiz)\n4.Para listar todos os quizes\n0.Para sair\n");
			comando = input.nextInt();
			if (quizzes.isEmpty()) {
				switch (comando) {
				case 1:
					System.out.printf("\nRedirecionando para criação de quiz\n");
					criar(input);
					break;
				case 2:
				case 3:
				case 4:
					System.out.printf("\nVocê ainda não cadastrou nenhum quiz\n");
					break;
				case 0:
					System.out.printf("\nVocê opitou por sair\nAté mais.\n");
					System.exit(0);
				default:
					System.out.printf("\nOpção %d não encontrada", comando);
				}
			} else {
				switch (comando) {
				case 1:
					System.out.printf("\nRedirecionando para criação de quiz\n");
					criar(input);
					break;
				case 2:
					System.out.printf("\nRedirecionando para executar quiz\n");
					fazer(input);
					break;
				case 3:
					System.out.printf("\nRedirecionando para menu de quiz\n");
					ver(input);
					break;
				case 4:
					System.out.printf("\nRedirecionando para listar de quiz\n");
					listar();
					break;
				case 0:
					System.out.printf("\nVocê opitou por sair\nAté mais.\n");
					System.exit(0);
				default:
					System.out.printf("\nOpção %d não encontrada", comando);
				}
			}
		} while (comando != 0);
	}
//  Verifica se é vazio
	public static boolean verificaVazio(Quiz quiz) {
		if (quiz == null) {
			return true;
		} else {
			return false;
		}
	}

//  Criar Quiz	
	public static void criar(Scanner input) {
		System.out.printf("\nDigite o nome do seu quiz:\n");
		input.nextLine(); // Consumir quebra de linha pendente
		String nomeQuiz = input.nextLine();
		Quiz quiz = new Quiz(nomeQuiz);
		int quantidadeQuestao = 0;
		boolean erroEntrada = true;
		try {
			System.out.printf("\nDigite o número de questões do seu quiz:\n");
			quantidadeQuestao = input.nextInt();
			input.nextLine(); // Consumir quebra de linha pendente
			for (int i = 0; i < quantidadeQuestao; i++) {
				System.out.printf("\nDigite a questão %dº do seu quiz:\n", i + 1);
				String questao = input.nextLine();
				boolean erroOpcao = true;
				try {
					System.out.printf("\nDigite o número de opções da questão %dº:\n", i + 1);
					int opcoesQuestao = Integer.parseInt(input.nextLine());
					List<String> escolhas = new ArrayList<>();
					for (int d = 0; d < opcoesQuestao; d++) {
						System.out.printf("\nDigite a %dº opção:\n", d + 1);
						String opcao = input.nextLine();
						escolhas.add(opcao);
					}
					System.out.printf("\nDigite o índice da opção correta:\n");
					int respostaCorreta = Integer.parseInt(input.nextLine()) - 1;
					quiz.addQuestoes(new Questao(questao, escolhas, respostaCorreta));
					erroOpcao = false;
				} catch (InputMismatchException | NumberFormatException e) {
					System.out.printf("Erro ao processar opções da questão: %s\n", e.getMessage());
					input.nextLine(); // Consumir entrada inválida
				}
			}
			quizzes.put(nomeQuiz, quiz); // Adiciona o quiz ao mapa após o loop
			System.out.printf("\nQuiz criado com sucesso!\n");
		} catch (InputMismatchException | NumberFormatException e) {
			System.out.printf("Erro ao processar número de questões: %s\n", e.getMessage());
			input.nextLine(); // Consumir entrada inválida
		}
	}
//  Fazer Quiz
	public static void fazer(Scanner input) {
		System.out.printf("\nDigite o nome do quiz:\n");
		input.nextLine();
		String escolha = input.nextLine();
		Quiz quiz = quizzes.get(escolha);
		if (quiz == null) {
			System.out.printf("\nO quiz '%s' não existe.\n", escolha);
			return;
		}
		int pontos = 0;
		for (int i = 0; i < quiz.getNumeroQuestoes(); i++) {
			Questao questao = quiz.getQuestoes(i);
			System.out.printf("\nQuestão %dº:\n%s", i + 1, questao.getQuestao());
			List<String> opcoes = questao.getOpcoes();
			for (int d = 0; d < opcoes.size(); d++) {
				System.out.printf("\nOpção %dº: %s\n", d + 1, opcoes.get(d));
			}
			System.out.printf("\nDigite a resposta:\n");
			int resposta = Integer.parseInt(input.nextLine()) - 1;
			if (resposta == questao.getOpcaoCorreta()) {
				pontos++;
				System.out.printf("\nParabéns, a sua respostas está correta!\n");
			} else {
				System.out.printf("\nA sua respostas está incorreta!\n");
			}
		}
		int pontosFinais=(pontos/quiz.getNumeroQuestoes())*10;
		System.out.printf("\nSua pontuação final foi: %d.\n", pontosFinais);
	}

//  Verificar Quiz
	public static void ver(Scanner input) {
		System.out.printf("\nDigite o nome do quiz:\n");
		input.nextLine();
		String nomeQuiz = input.nextLine();
		Quiz quizResultado = quizzes.get(nomeQuiz);
		if (verificaVazio(quizResultado)) {
			System.out.printf("\nQuiz não encontrado!\n");
			return;
		} else {
			for (int i = 0; i < quizResultado.getNumeroQuestoes(); i++) {
				Questao questao = quizResultado.getQuestoes(i);
				System.out.printf("\nQuestão %d:\n%s\n", i + 1, questao.getQuestao());
				List<String> escolha = questao.getOpcoes();
				for (int d = 0; d < escolha.size(); d++) {
					System.out.printf("\nOpções %d: %s\n", d + 1, escolha.get(d));
				}
				System.out.printf("Resposta correta: OPÇÃO %d: %s", questao.getOpcaoCorreta()+1,
						escolha.get(questao.getOpcaoCorreta()));
			}

		}

	}

//  Listar todos os quizes por nome
//  Listar Quiz
	public static void listar() {
		System.out.printf("Quizes");
		int posicao = 1;
		for (String nomeQuiz : quizzes.keySet()) {
			System.out.printf("\n%d- %s\n", posicao, nomeQuiz);
			posicao++;
		}
	}
}
class Quiz {
	private String nome;
	private List<Questao> questoes = new ArrayList<>();
	public Quiz(String nome) {
		this.nome = nome;
	}
	public String getNome() {
		return nome;
	}
	//Metodos
	public Questao getQuestoes(int index) {
		return questoes.get(index);
	}
	public int getNumeroQuestoes() {
		return questoes.size();
	}
	public void addQuestoes(Questao questao) {
		questoes.add(questao);
	}
}
class Questao {
	private String questao;
	private List<String> opcoes;
	private int opcaoCorreta;
	public Questao(String questao, List<String> opcoes, int opcaoCorreta) {
		this.questao = questao;
		this.opcoes = opcoes;
		this.opcaoCorreta = opcaoCorreta;
	}
	public String getQuestao() {
		return questao;
	}
	public List<String> getOpcoes() {
		return opcoes;
	}
	public int getOpcaoCorreta() {
		return opcaoCorreta;
	}
}