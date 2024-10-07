package zona_fit.presentacion;

import zona_fit.datos.ClienteDAO;
import zona_fit.datos.IclienteDAO;
import zona_fit.dominio.Cliente;

import java.util.Scanner;

public class ZonaFitApp {
    public static void main(String[] args) {
        zonaFitApp();
    }

    private static void zonaFitApp(){
        var salir = false;
        var consola = new Scanner(System.in);
        // Creamos un objeto de la clase clienteDao
        IclienteDAO clienteDao = new ClienteDAO();
        while(!salir){
            try {
                var opcion = mostrarMenu(consola);
                salir = ejecutarOpciones(consola, opcion, clienteDao);
            } catch (Exception e){
                System.out.println("Error al ejecutar opciones: " + e.getMessage());
            }
            System.out.println();
        }
    }

    private static int mostrarMenu(Scanner consola) {
        System.out.print("""
                *** Zona Fit (GYM)
                1. Listar Clientes
                2. Buscar Cliente
                3. Agregar Cliente
                4. Modificar Cliente
                5. Eliminar Cliente
                6. Salir
                Elija una opcion:\s""");
        return Integer.parseInt(consola.nextLine());
    }

    private static boolean ejecutarOpciones(Scanner consola, int opcion, IclienteDAO clienteDao) {
        var salir = false;
        switch (opcion){
            case 1 -> { //Listar clientes
                System.out.println("--- Listado de Clientes ---");
                var clientes = clienteDao.listarClientes();
                clientes.forEach(System.out::print);
            }
            case 2 -> { //Buscar cliente por ID
                System.out.print("Introduce el id del Cliente a buscar: ");
                var idCliente = Integer.parseInt(consola.nextLine());
                var cliente = new Cliente(idCliente);
                var encontrado = clienteDao.buscarClientePorId(cliente);
                if(encontrado){
                    System.out.println("Cliente encontrado: " + cliente);
                } else {
                    System.out.println("Cliente NO encontrado: " + cliente);
                }
            }
            case 3 -> { //Agregar cliente
                System.out.println("--- Agregar Cliente ---");
                System.out.print("Nombre: ");
                var nombre = consola.nextLine();
                System.out.print("Apellido: ");
                var apellido = consola.nextLine();
                System.out.print("Membresia: ");
                var membresia = Integer.parseInt(consola.nextLine());
                //Creamos el objeto cliente (sin el id)
                var cliente = new Cliente(nombre, apellido, membresia);
                var agregado = clienteDao.agregarCliente(cliente);
                if(agregado){
                    System.out.println("Cliente agregado: " + cliente);
                } else{
                    System.out.println("Cliente no agregado: " + cliente);
                }
            }
            case 4 ->{ //Modificar cliente
                System.out.println("--- Modificar Cliente ---");
                System.out.println("Id Cliente: ");
                var idCliente = Integer.parseInt(consola.nextLine());
                System.out.print("Nombre: ");
                var nombre = consola.nextLine();
                System.out.print("Apellido: ");
                var apellido = consola.nextLine();
                System.out.print("Membresia: ");
                var membresia = Integer.parseInt(consola.nextLine());
                //Creamos el objeto a modificar
                var cliente = new Cliente(idCliente, nombre, apellido, membresia);
                var modificado = clienteDao.modificarCliente(cliente);
                if(modificado){
                    System.out.println("Cliente modificado: " + cliente);
                }else{
                    System.out.println("Cliente NO modificado: " + cliente);
                }
            }
            case 5 -> { //Eliminar cliente
                System.out.println("--- Eliminar Cliente ---");
                System.out.print("Id Cliente: ");
                var idCliente = Integer.parseInt(consola.nextLine());
                var cliente = new Cliente(idCliente);
                var eliminado = clienteDao.eliminarCliente(cliente);
                if(eliminado){
                    System.out.println("Cliente eliminado: " + cliente);
                }else{
                    System.out.println("Cliente NO eliminado: " + cliente);
                }
            }
            case 6 -> { //Salir
                System.out.println("Hasta pronto!");
                salir = true;
            }
            default -> System.out.println("Opcion no reconocida: " + opcion);
        }
        return salir;
    }
}
