package exceptions;

public class ForbidenAccessException extends Exception{
    private int codeError = 203;
    public ForbidenAccessException(){
        super("Você não está autorizado a passar sequer na porta");
    }

    public int getCodeError() {
        return codeError;
    }
}
