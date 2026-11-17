import java.util.ArrayList;
import  java.util.Scanner;

class Mesa{
    private int id;
    private String status;

    public Mesa(int id, String status){
        this.id = id;
        this.status = status;
    }

    public int getId(){
        return this.id;
    }

    public String getStatus(){
        return this.status;
    }

    public void setStatus(String status){
        this.status = status;
    }
}

class IdException extends Exception{
    public IdException(String mensagem){
        super(mensagem);
    }
}

class Main{

    static ArrayList<Mesa> mesas = new ArrayList<>();

    public static void adicionarMesa(Mesa mesa) throws IdException {
    for (Mesa m : mesas) {
        if (m.getId() == mesa.getId()) {
            throw new IdException("ID já está em uso.");
            }
        }

        mesas.add(mesa);
    }
    
    public static void listarMesas() {
        for (Mesa mesa : mesas) {
            System.out.println("ID: " + mesa.getId() + " | Status: " + mesa.getStatus());
        }
    }

    public static Mesa buscarMesa(int id) throws IdException {
        for (Mesa mesa : mesas) {
            if (mesa.getId() == id) {
            return mesa;
            }
        }
        throw new IdException("Mesa não encontrada.");
    }


    public static void removerMesa(int id) throws IdException {
        Mesa mesa = buscarMesa(id);
        mesas.remove(mesa);
    }

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        Mesa mesa1 = new Mesa(1, "Open");
        Mesa mesa2 = new Mesa(2, "Closed");

        mesas.add(mesa1);
        mesas.add(mesa2);

        System.out.println("Quantas mesas deseja adicionar?");
        int n = scanner.nextInt();
        int nid;
        String nstatus;

        for (int i = 0; i < n; i++){
            System.out.println("Digite o id:");
            nid = scanner.nextInt();
            System.out.println("Digite o status da mesa (Open or Closed):");
            nstatus = scanner.next();
            
            try{
                adicionarMesa(new Mesa(nid, nstatus));
                System.out.printf("%n%n");
            }
            catch(IdException e){
                System.out.println(e.getMessage());
                System.out.printf("%n%n");
            }
            
 
        }

        System.out.println("Mesas com id válido foram adicionadas.");
        System.out.printf("%n%n");

        System.out.println("Quantidade total de mesas: "+ mesas.size());
        System.out.printf("%n%n");

        System.out.println("Listando mesas: ");
        listarMesas();
        System.out.printf("%n%n");

        System.out.println("Quantas mesas deseja buscar?");
        int val = scanner.nextInt();
        int v;

        for (int j = 0; j < val; j++){
            System.out.println("Digite o Id da mesa:");
            v = scanner.nextInt();
            
            try{
                Mesa encontrada = buscarMesa(v);
                System.out.println("Mesa encontrada:");
                System.out.println("ID: " + encontrada.getId());
                System.out.println("Status: " + encontrada.getStatus());
                System.out.printf("%n%n");
            }
            catch(IdException e){
                System.out.println(e.getMessage());
                System.out.printf("%n%n");
            }
        }

        System.out.println("Quantas mesas deseja remover?");
        System.out.printf("%n");
        int  rem = scanner.nextInt();
        int r;

        for (int j = 0; j < rem; j++){
            System.out.println("Digite o Id da mesa:");
            r = scanner.nextInt();
            
            try{
                removerMesa(r);
                System.out.printf("Mesa %d removida.%n", r);
                System.out.printf("%n%n");
            }
            catch(IdException e){
                System.out.println(e.getMessage());
                System.out.printf("%n%n");
            }
        }


        System.out.println("Nova lista de masas após remoção: ");
        listarMesas();

        scanner.close();
    }
}