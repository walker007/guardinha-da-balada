import enums.Genero;
import exceptions.ForbidenAccessException;
import modelos.Guarda;
import modelos.Usuario;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Guarda guarda;

        boolean sair = false;
        Usuario usuario;
        String nome;
        int anoNascimento;
        Genero genero;
        ArrayList<Usuario> pessoasNaBalada = new ArrayList<Usuario>();

        System.out.println("Bem-vindo(a) ao Sistema de Vigilante de Balada");
        System.out.println("Antes de começar, informe seu nome: ");
        guarda = new Guarda(scan.nextLine());

        while (!sair){
            System.out.println("Olá, " + guarda.getNome());
            System.out.println("Atualmente tem: " + pessoasNaBalada.size() + " pessoas na balada");
            System.out.println("------ Menu ------");
            System.out.println("1 ] Dar entrada em um usuário");
            System.out.println("2 ] Consultar Usuário");
            System.out.println("3 ] Remover Usuário");
            System.out.println("4 ] Fechar Balada");
            System.out.println("0 ] Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scan.nextInt();
            scan.nextLine();
            switch (opcao){
                case 0:
                    sair = true;
                    break;
                case 1:
                    Main.createUser(pessoasNaBalada,scan,guarda);
                    break;
                case 2:
                    Main.buscarUsuario(pessoasNaBalada, scan);
                    break;
                case 3:
                    Main.removerUsuario(pessoasNaBalada, scan);
                    break;
                case 4:
                    pessoasNaBalada.clear();
                    System.out.println("Balada Fechada");
                    break;
                default:
                    System.out.println("Opção inválida");
            }

        }

        scan.close();

    }

    private static void removerUsuario(ArrayList<Usuario> pessoasNaBalada, Scanner scan) {
        String userName;


        System.out.println("Digite o nome a ser Excluído: ");
        userName = scan.nextLine();
        ConsultaRetorno retorno = Main.findUserIndex(pessoasNaBalada, userName);

        if (retorno.isEncontrado()){
            pessoasNaBalada.remove(retorno.getIndex());
            System.out.println("O Usuário "+userName+" Saiu...");
            return;
        }

        System.out.println("Nenhum usuário corresponde a pesquisa de: " + userName);


    }

    public static ConsultaRetorno findUserIndex(ArrayList<Usuario> pessoasNaBalada,String nome){
        AtomicInteger indexUser = new AtomicInteger();
        AtomicBoolean encontrado = new AtomicBoolean(false);

        pessoasNaBalada.forEach(usuario -> {
            if(usuario.getNome().toLowerCase().equals(nome.trim().toLowerCase())){
                encontrado.set(true);
                indexUser.set(pessoasNaBalada.indexOf(usuario));
            }
        });

        return new ConsultaRetorno(encontrado.get(), indexUser.get());
    }
    private static void buscarUsuario(ArrayList<Usuario> pessoasNaBalada, Scanner scan) {
        String userName;


        System.out.println("Digite o nome a ser Consultado: ");
        userName = scan.nextLine();

        ConsultaRetorno retorno = Main.findUserIndex(pessoasNaBalada, userName);

        if(retorno.isEncontrado()){
            Usuario usuario = pessoasNaBalada.get(retorno.getIndex());
            System.out.println(usuario.getNome());
            System.out.println("Idade: " +usuario.getIdade());
            System.out.println("Gênero: " + usuario.getGenero());
            return;
        }

        System.out.println("Usuário não encontrado");

    }

    public static void createUser(ArrayList<Usuario> pessoasNaBalada, Scanner scan, Guarda guarda){
        Usuario usuario;
        String nome;
        int anoNascimento;
        Genero genero;

        System.out.println("Informe o nome do usuário");
        nome = scan.nextLine().trim();
        System.out.println("Informe o ano de Nascimento");
        anoNascimento = scan.nextInt();
        scan.nextLine();
        System.out.println("Escolha um: [MASCULINO, FEMININO, NAO_INFORMADO]");
        String generoValue = scan.nextLine().toUpperCase();
        try {
            genero = Genero.valueOf(generoValue);
        }catch (Exception e){
            genero = Genero.NAO_INFORMADO;
        }

        usuario = new Usuario(nome, genero, anoNascimento);
        System.out.println("Verificando dados do usuário...");
        String greeting = "Usuário: ";
        String nomeUser = usuario.getNome();

        System.out.println(greeting.concat(nomeUser) + ".");
        System.out.println("O usuário é ".concat(usuario.getPronome()).concat(" " + usuario.getGeneroString().toLowerCase()));
        System.out.println(usuario.getIdade());
        try{
            guarda.podeEntrar(usuario);
            pessoasNaBalada.add(usuario);
        }catch (ForbidenAccessException e){
            System.out.println(e.getMessage());
            System.out.println(e.getCodeError());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        System.out.println("------------------------");
        System.out.println("O nome '" + nomeUser + "' contém " + nomeUser.length() + " caracteres.");
        System.out.println("O nome '"+ nomeUser +"' contem a palvra Tigrão? " + nomeUser.contains("Tigrão"));
        System.out.println("Inicia com a Letra T? " + nomeUser.startsWith("Ti"));
        System.out.println("Termina com a Letra O: " + nomeUser.endsWith("ão"));
        System.out.println(nomeUser.replace('T','L'));
    }
}